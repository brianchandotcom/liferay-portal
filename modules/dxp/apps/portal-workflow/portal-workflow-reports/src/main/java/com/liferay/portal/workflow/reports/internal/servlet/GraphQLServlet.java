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

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.workflow.reports.internal.servlet.data.fetcher.WorkflowProcessBagDataFetcher;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import graphql.servlet.AbstractGraphQLHttpServlet;
import graphql.servlet.GraphQLInvocationInputFactory;
import graphql.servlet.GraphQLObjectMapper;
import graphql.servlet.GraphQLQueryInvoker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.Charset;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
public class GraphQLServlet extends AbstractGraphQLHttpServlet {

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

	protected GraphQLSchema createGraphQLQuerySchema() {
		SchemaGenerator schemaGenerator = new SchemaGenerator();

		return schemaGenerator.makeExecutableSchema(
			createTypeDefinitionRegistry(), createRuntimeWiring());
	}

	protected RuntimeWiring createRuntimeWiring() {
		RuntimeWiring.Builder runtimeWiringBuilder =
			RuntimeWiring.newRuntimeWiring();

		runtimeWiringBuilder.type(
			"QueryType",
			typeWiring -> typeWiring.dataFetcher(
				"processes", _workflowProcessBagDataFetcher));

		return runtimeWiringBuilder.build();
	}

	protected TypeDefinitionRegistry createTypeDefinitionRegistry() {
		SchemaParser schemaParser = new SchemaParser();

		return schemaParser.parse(
			new InputStreamReader(
				getSchemaResourceAsStream(), Charset.defaultCharset()));
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

	protected InputStream getSchemaResourceAsStream() {
		Class<?> clazz = getClass();

		return clazz.getResourceAsStream("/workflow_reports.graphqls");
	}

	private static final Log _log = LogFactoryUtil.getLog(GraphQLServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private WorkflowProcessBagDataFetcher _workflowProcessBagDataFetcher;

}