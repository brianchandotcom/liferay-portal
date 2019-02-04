package ${configYAML.apiPackagePath}.internal.servlet;

import ${configYAML.apiPackagePath}.internal.mutation.Mutation;
import ${configYAML.apiPackagePath}.internal.query.Query;

import graphql.annotations.processor.GraphQLAnnotations;

import graphql.schema.GraphQLSchema;

import graphql.servlet.AbstractGraphQLHttpServlet;
import graphql.servlet.GraphQLInvocationInputFactory;
import graphql.servlet.GraphQLObjectMapper;
import graphql.servlet.GraphQLQueryInvoker;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=${configYAML.application.baseURI}-graphql/",
		"osgi.http.whiteboard.servlet.name=${configYAML.apiPackagePath}.internal.servlet.GraphQLServlet",
		"osgi.http.whiteboard.servlet.pattern=${configYAML.application.baseURI}-graphql/*"
	},
	service = Servlet.class
)
public class GraphQLServlet extends AbstractGraphQLHttpServlet {

	protected GraphQLSchema createGraphQLQuerySchema() {

		return GraphQLSchema.newSchema().query(
			GraphQLAnnotations.object(Query.class)
		).mutation(
			GraphQLAnnotations.object(Mutation.class)
		).build();
	}

	@Override
	protected GraphQLObjectMapper getGraphQLObjectMapper() {
		GraphQLObjectMapper.Builder builder = GraphQLObjectMapper.newBuilder();

		return builder.build();
	}

	@Override
	protected GraphQLInvocationInputFactory getInvocationInputFactory() {
		GraphQLInvocationInputFactory.Builder builder =
			GraphQLInvocationInputFactory.newBuilder(
				createGraphQLQuerySchema());

		return builder.build();
	}

	@Override
	protected GraphQLQueryInvoker getQueryInvoker() {
		GraphQLQueryInvoker.Builder builder = GraphQLQueryInvoker.newBuilder();

		return builder.build();
	}

	private static final long serialVersionUID = 1L;
}