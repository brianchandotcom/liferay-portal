/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.reports.internal.servlet;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.GraphqlErrorHelper;

import graphql.introspection.IntrospectionQuery;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import java.nio.charset.Charset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/workflow-reports/graphql",
		"osgi.http.whiteboard.servlet.name=com.liferay.portal.workflow.reports.web.internal.servlet.GraphQLServlet",
		"osgi.http.whiteboard.servlet.pattern=/workflow-reports/graphql/*"
	},
	service = Servlet.class
)
public class GraphQLServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, request, response);
		}
		catch (ActionException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae, ae);
			}
		}

		super.service(request, response);
	}

	@Activate
	protected void activate() {
		GraphQL.Builder graphQLBuilder = GraphQL.newGraphQL(
			createGraphQLQuerySchema());

		_graphQL = graphQLBuilder.build();
	}

	protected GraphQLSchema createGraphQLQuerySchema() {
		SchemaGenerator schemaGenerator = new SchemaGenerator();

		return schemaGenerator.makeExecutableSchema(
			createTypeDefinitionRegistry(), createRuntimeWiring());
	}

	protected Map<String, Object> createResultFromDataAndErrors(
		Object data, List<GraphQLError> errors) {

		Map<String, Object> result = new HashMap<>();

		result.put("data", data);

		if ((errors != null) && !errors.isEmpty()) {
			Stream<GraphQLError> errorStream = errors.stream();

			List<Map<String, Object>> translatedErrors = errorStream.map(
				GraphqlErrorHelper::toSpecification
			).collect(
				Collectors.toList()
			);

			result.put("errors", translatedErrors);
		}

		return result;
	}

	protected RuntimeWiring createRuntimeWiring() {
		RuntimeWiring.Builder runtimeWiringBuilder =
			RuntimeWiring.newRuntimeWiring();

		return runtimeWiringBuilder.build();
	}

	protected TypeDefinitionRegistry createTypeDefinitionRegistry() {
		SchemaParser schemaParser = new SchemaParser();

		return schemaParser.parse(
			new InputStreamReader(
				getSchemaResourceAsStream(), Charset.defaultCharset()));
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String path = request.getPathInfo();

		if (path == null) {
			path = request.getServletPath();
		}

		if (!path.contentEquals("/schema.json")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		ExecutionResult executionResult = _graphQL.execute(
			IntrospectionQuery.INTROSPECTION_QUERY);

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(response, serialize(executionResult));
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String body = StringUtil.read(request.getInputStream());

		if (isBatchedRequest(body)) {
			doProcessBatchedRequest(body, response);
		}
		else {
			doProcessRequest(body, response);
		}
	}

	protected void doProcessBatchedRequest(
			String body, HttpServletResponse response)
		throws IOException, ServletException {

		GraphQLRequest[] graphQLRequests = _objectMapper.readValue(
			body, GraphQLRequest[].class);

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		Writer writer = response.getWriter();

		writer.write(CharPool.OPEN_BRACKET);

		for (int i = 0; i < graphQLRequests.length; i++) {
			ExecutionResult executionResult = doProcessGraphQLRequest(
				graphQLRequests[i]);

			writer.write(serialize(executionResult));

			if ((i + 1) < graphQLRequests.length) {
				writer.write(CharPool.COMMA);
			}
		}

		writer.write(CharPool.CLOSE_BRACKET);
	}

	protected ExecutionResult doProcessGraphQLRequest(
		GraphQLRequest graphQLRequest) {

		ExecutionInput.Builder executionInputBuilder =
			ExecutionInput.newExecutionInput();

		ExecutionInput executionInput = executionInputBuilder.query(
			graphQLRequest.getQuery()
		).context(
			new HashMap<String, Object>()
		).variables(
			graphQLRequest.getVariables()
		).operationName(
			graphQLRequest.getOperationName()
		).build();

		return _graphQL.execute(executionInput);
	}

	protected void doProcessRequest(String body, HttpServletResponse response)
		throws IOException, ServletException {

		GraphQLRequest graphQLRequest = _objectMapper.readValue(
			body, GraphQLRequest.class);

		ExecutionResult executionResult = doProcessGraphQLRequest(
			graphQLRequest);

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(response, serialize(executionResult));
	}

	protected InputStream getSchemaResourceAsStream() {
		Class<?> clazz = getClass();

		return clazz.getResourceAsStream("/workflow_reports.graphqls");
	}

	protected boolean isArrayStart(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			if (!Character.isWhitespace(ch)) {
				if (ch == '[') {
					return true;
				}

				return false;
			}
		}

		return false;
	}

	protected boolean isBatchedRequest(String body) throws IOException {
		if (isArrayStart(body)) {
			return true;
		}

		return false;
	}

	protected String serialize(ExecutionResult executionResult)
		throws IOException {

		Map<String, Object> result = createResultFromDataAndErrors(
			executionResult.getData(), executionResult.getErrors());

		return _objectMapper.writeValueAsString(result);
	}

	private static final Module _JDK8_MODULE = new Jdk8Module();

	private static final Log _log = LogFactoryUtil.getLog(GraphQLServlet.class);

	private static final long serialVersionUID = 1L;

	private transient GraphQL _graphQL;
	private final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			registerModule(_JDK8_MODULE);
		}
	};

	private static class GraphQLRequest {

		public String getOperationName() {
			return _operationName;
		}

		public String getQuery() {
			return _query;
		}

		public Map<String, Object> getVariables() {
			return _variables;
		}

		public void setOperationName(String operationName) {
			_operationName = operationName;
		}

		public void setQuery(String query) {
			_query = query;
		}

		public void setVariables(Map<String, Object> variables) {
			_variables = variables;
		}

		private String _operationName;
		private String _query;
		private Map<String, Object> _variables = new HashMap<>();

	}

}