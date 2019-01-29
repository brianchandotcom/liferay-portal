/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.engine.web.internal.servlet;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import com.liferay.data.engine.web.internal.servlet.data.fetcher.DECountDataDefinitionDataFetcher;
import com.liferay.data.engine.web.internal.servlet.data.fetcher.DEDeleteDataDefinitionDataFetcher;
import com.liferay.data.engine.web.internal.servlet.data.fetcher.DEGetDataDefinitionDataFetcher;
import com.liferay.data.engine.web.internal.servlet.data.fetcher.DEListDataDefinitionDataFetcher;
import com.liferay.data.engine.web.internal.servlet.data.fetcher.DESaveDataDefinitionDataFetcher;
import com.liferay.data.engine.web.internal.servlet.data.fetcher.DESaveDataRecordCollectionDataFetcher;
import com.liferay.data.engine.web.internal.servlet.data.fetcher.DESaveModelPermissionsDataDefinitionDataFetcher;
import com.liferay.data.engine.web.internal.servlet.data.fetcher.DESavePermissionsDataDefinitionDataFetcher;
import com.liferay.data.engine.web.internal.servlet.data.fetcher.DESearchCountDataDefinitionFetcher;
import com.liferay.data.engine.web.internal.servlet.data.fetcher.DESearchDataDefinitionFetcher;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;

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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/data-engine/graphql",
		"osgi.http.whiteboard.servlet.name=com.liferay.data.engine.web.internal.servlet.GraphQLServlet",
		"osgi.http.whiteboard.servlet.pattern=/data-engine/graphql/*"
	},
	service = Servlet.class
)
public class DEGraphQLServlet extends HttpServlet {

	public static Map<String, Object> mapGraphQLError(GraphQLError error) {
		Map<String, Object> errorMap = new LinkedHashMap<>();

		String errorMessage = error.getMessage();

		if (errorMessage.contains(StringPool.DOUBLE_DOLLAR)) {
			String[] strings = StringUtil.split(
				error.getMessage(), StringPool.DOUBLE_DOLLAR);

			errorMap.put("message", strings[1]);
		}
		else {
			errorMap.put("message", errorMessage);
		}

		return errorMap;
	}

	@Activate
	protected void activate() {
		GraphQL.Builder builder = GraphQL.newGraphQL(
			createGraphQLQuerySchema());

		_graphQL = builder.build();
	}

	protected GraphQLSchema createGraphQLQuerySchema() {
		SchemaGenerator schemaGenerator = new SchemaGenerator();

		return schemaGenerator.makeExecutableSchema(
			createTypeDefinitionRegistry(), createRuntimeWiring());
	}

	protected Map<String, Object> createResultFromDataAndErrors(
		Map<String, Object> data, List<GraphQLError> errors) {

		Map<String, Object> result = new HashMap<>();

		if ((data != null) && !data.isEmpty()) {
			Collection<Object> values = data.values();

			Object[] objects = values.toArray();

			result.put("data", objects[0]);
		}

		if ((errors != null) && !errors.isEmpty()) {
			Stream<GraphQLError> errorStream = errors.stream();

			List<Map<String, Object>> translatedErrors = errorStream.map(
				DEGraphQLServlet::mapGraphQLError
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

		runtimeWiringBuilder.type(
			"MutationType",
			typeWiring -> typeWiring.dataFetcher(
				"deleteDataDefinition", _deDeleteDataDefinitionDataFetcher));
		runtimeWiringBuilder.type(
			"MutationType",
			typeWiring -> typeWiring.dataFetcher(
				"saveDataDefinition", _deSaveDataDefinitionDataFetcher));
		runtimeWiringBuilder.type(
			"MutationType",
			typeWiring -> typeWiring.dataFetcher(
				"saveDataDefinitionModelPermissions",
				_deSaveModelPermissionsDataDefinitionDataFetcher));
		runtimeWiringBuilder.type(
			"MutationType",
			typeWiring -> typeWiring.dataFetcher(
				"saveDataDefinitionPermissions",
				_deSavePermissionsDataDefinitionDataFetcher));
		runtimeWiringBuilder.type(
			"QueryType",
			typeWiring -> typeWiring.dataFetcher(
				"countDataDefinition", _deCountDataDefinitionDataFetcher));
		runtimeWiringBuilder.type(
			"QueryType",
			typeWiring -> typeWiring.dataFetcher(
				"getDataDefinition", _deGetDataDefinitionDataFetcher));
		runtimeWiringBuilder.type(
			"QueryType",
			typeWiring -> typeWiring.dataFetcher(
				"listDataDefinition", _deListDataDefinitionDataFetcher));
		runtimeWiringBuilder.type(
			"QueryType",
			typeWiring -> typeWiring.dataFetcher(
				"searchCountDataDefinition",
				_deSearchCountDataDefinitionFetcher));
		runtimeWiringBuilder.type(
			"QueryType",
			typeWiring -> typeWiring.dataFetcher(
				"searchDataDefinition", _deSearchDataDefinitionFetcher));
		runtimeWiringBuilder.type(
			"MutationType",
			typeWiring -> typeWiring.dataFetcher(
				"saveDataRecordCollection",
				_deSaveDataRecordCollectionDataFetcher));

		return runtimeWiringBuilder.build();
	}

	protected TypeDefinitionRegistry createTypeDefinitionRegistry() {
		SchemaParser schemaParser = new SchemaParser();

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream("/schema.graphqls");

		return schemaParser.parse(
			new InputStreamReader(inputStream, Charset.defaultCharset()));
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

	protected boolean isArrayStart(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (!Character.isWhitespace(c)) {
				if (c == '[') {
					return true;
				}

				return false;
			}
		}

		return false;
	}

	protected boolean isBatchedRequest(String body) {
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

	@Reference
	private DECountDataDefinitionDataFetcher _deCountDataDefinitionDataFetcher;

	@Reference
	private DEDeleteDataDefinitionDataFetcher
		_deDeleteDataDefinitionDataFetcher;

	@Reference
	private DEGetDataDefinitionDataFetcher _deGetDataDefinitionDataFetcher;

	@Reference
	private DEListDataDefinitionDataFetcher _deListDataDefinitionDataFetcher;

	@Reference
	private DESaveDataDefinitionDataFetcher _deSaveDataDefinitionDataFetcher;

	@Reference
	private DESaveDataRecordCollectionDataFetcher
		_deSaveDataRecordCollectionDataFetcher;

	@Reference
	private DESaveModelPermissionsDataDefinitionDataFetcher
		_deSaveModelPermissionsDataDefinitionDataFetcher;

	@Reference
	private DESavePermissionsDataDefinitionDataFetcher
		_deSavePermissionsDataDefinitionDataFetcher;

	@Reference
	private DESearchCountDataDefinitionFetcher
		_deSearchCountDataDefinitionFetcher;

	@Reference
	private DESearchDataDefinitionFetcher _deSearchDataDefinitionFetcher;

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