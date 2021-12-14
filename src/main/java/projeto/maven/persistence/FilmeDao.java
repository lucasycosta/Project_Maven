
package projeto.maven.persistence;

import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import projeto.maven.model.Filme;

public class FilmeDao {
	
	final static Logger logger = Logger.getLogger(FilmeDao.class);
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Filme f1 = new Filme();
		char resp = 0;
		
		// CONEXÃO COM O BANCO DE DADOS
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("filme3");
		EntityManager em = emf.createEntityManager();
		
		// INICIA UMA TRANSANÇÃO COM O BANCO DE DADOS
		em.getTransaction().begin();

		do {
			System.out.print("\n O que você deseja fazer?");
			System.out.println("\n |1 - incluir novo filme" + 
							   "\n |2 - consultar filme existente" + 
							   "\n |3 - alterar os dados de um filme" + 
							   "\n |4 - deletar o cadastro de um filme");
			System.out.print("Opção desejada: ");
			int opcao = input.nextInt();

			switch (opcao) {
			case 1: {
				// ENTRADA DE DADOS
				System.out.println("Digite um nome: ");
				f1.setNome(input.next());
				System.out.println("Digite um genero: ");
				f1.setGenero(input.next());
				em.persist(f1);

				// CONFIRMA AS ALTERAÇÕES
				em.getTransaction().commit();
				
				System.out.println("Filme incluido com sucesso!!");
				break;
			}

			case 2: {
				try {
					System.out.print("Digite o ID do filme: ");
					int id = input.nextInt();
					// FUNÇÃO PARA BUSCAR OBJETO PELO ID
					Filme f = em.find(Filme.class, id);
					System.out.println("Nome do Filme: " + f.getNome());
					System.out.println("Genero do Filme: " + f.getGenero());
				} catch (Exception e) {
					logger.error("ID ivalido", e);
				}
				break;
			}
			case 3: {
				try {
					System.out.print("Digite o ID do filme: ");
					int id = input.nextInt();
					Filme f3 = em.find(Filme.class, id);
					System.out.println("\n Nome atual do Filme: " + f3.getNome());
					System.out.println("\n Genero atual do Filme: " + f3.getGenero());
					System.out.println("\n Digite o novo nome do filme: ");
					String nome = input.next();
					System.out.println("\n Digite o novo genero do filme: ");
					String genero = input.next();

					f3.setNome(nome);
					f3.setGenero(genero);

					em.merge(f3);
					em.getTransaction().commit();
					System.out.println("Dados atualizados!!!");
					
				} catch (Exception e) {
					logger.error("ID ivalido", e);
				}
				break;
			}

			case 4:
				try {
					System.out.print("Digite o ID do filme: ");
					int id = input.nextInt();
					Filme f2 = em.find(Filme.class, id);
					em.remove(f2);
					em.getTransaction().commit();
					System.out.println("Filme Excluido!!!!");
					
				} catch (Exception e) {
					logger.error("ID ivalido", e);
				}
				break;
			}

			System.out.println("\n Deseja continuar? s ou n");
			resp = input.next().charAt(0);
			
		} while (resp != 'n');
		
		if(resp == 'n') {
			System.out.println("Operação finalizada!!");
		}
		
		input.close();
	}
}
