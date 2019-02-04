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

package com.liferay.headless.workflow.internal.servlet;

import com.liferay.headless.workflow.internal.mutation.Mutation;
import com.liferay.headless.workflow.internal.query.Query;

import graphql.annotations.processor.GraphQLAnnotations;

import graphql.schema.GraphQLSchema;

import graphql.servlet.AbstractGraphQLHttpServlet;
import graphql.servlet.GraphQLInvocationInputFactory;
import graphql.servlet.GraphQLObjectMapper;
import graphql.servlet.GraphQLQueryInvoker;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/headless-workflow-graphql/",
		"osgi.http.whiteboard.servlet.name=com.liferay.headless.workflow.internal.servlet.GraphQLServlet",
		"osgi.http.whiteboard.servlet.pattern=/headless-workflow-graphql/*"
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