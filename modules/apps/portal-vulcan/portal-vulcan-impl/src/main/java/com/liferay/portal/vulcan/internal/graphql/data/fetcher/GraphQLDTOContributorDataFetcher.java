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

package com.liferay.portal.vulcan.internal.graphql.data.fetcher;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOProperty;
import com.liferay.portal.vulcan.graphql.validation.GraphQLRequestContext;
import com.liferay.portal.vulcan.graphql.validation.GraphQLRequestContextValidator;
import com.liferay.portal.vulcan.internal.graphql.data.processor.GraphQLDTOContributorDataFetchingProcessor;

import graphql.schema.DataFetchingEnvironment;

import java.io.Serializable;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Carlos Correa
 */
public class GraphQLDTOContributorDataFetcher extends BaseDataFetcher {

	public GraphQLDTOContributorDataFetcher(
		GraphQLDTOContributor graphQLDTOContributor,
		GraphQLDTOContributorDataFetchingProcessor
			graphQLDTOContributorDataFetchingProcessor,
		GraphQLDTOProperty graphQLDTOProperty,
		GraphQLRequestContext graphQLRequestContext,
		ServiceTrackerList<GraphQLRequestContextValidator>
			graphQLRequestContextValidators,
		Operation operation) {

		super(graphQLRequestContext, graphQLRequestContextValidators);

		_graphQLDTOContributor = graphQLDTOContributor;
		_graphQLDTOContributorDataFetchingProcessor =
			graphQLDTOContributorDataFetchingProcessor;
		_graphQLDTOProperty = graphQLDTOProperty;
		_operation = operation;
	}

	public GraphQLDTOContributorDataFetcher(
		GraphQLDTOContributor graphQLDTOContributor,
		GraphQLDTOContributorDataFetchingProcessor
			graphQLDTOContributorDataFetchingProcessor,
		GraphQLRequestContext graphQLRequestContext,
		ServiceTrackerList<GraphQLRequestContextValidator>
			graphQLRequestContextValidators,
		Operation operation) {

		this(
			graphQLDTOContributor, graphQLDTOContributorDataFetchingProcessor,
			null, graphQLRequestContext, graphQLRequestContextValidators,
			operation);
	}

	@Override
	public Object get(
			DataFetchingEnvironment dataFetchingEnvironment,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (_operation == Operation.CREATE) {
			return _graphQLDTOContributorDataFetchingProcessor.create(
				dataFetchingEnvironment.getArgument(
					_graphQLDTOContributor.getResourceName()),
				_graphQLDTOContributor, httpServletRequest,
				(String)dataFetchingEnvironment.getArgument("scopeKey"));
		}
		else if (_operation == Operation.DELETE) {
			return _graphQLDTOContributorDataFetchingProcessor.delete(
				_graphQLDTOContributor,
				dataFetchingEnvironment.<Long>getArgument(
					_graphQLDTOContributor.getIdName()));
		}
		else if (_operation == Operation.GET) {
			return _graphQLDTOContributorDataFetchingProcessor.get(
				_graphQLDTOContributor, httpServletRequest,
				dataFetchingEnvironment.getArgument(
					_graphQLDTOContributor.getIdName()));
		}
		else if (_operation == Operation.GET_RELATIONSHIP) {
			Map<String, Object> source = dataFetchingEnvironment.getSource();

			Object id = source.get(_graphQLDTOContributor.getIdName());

			if (!(id instanceof Long)) {
				return null;
			}

			return _graphQLDTOContributorDataFetchingProcessor.getRelationship(
				_graphQLDTOContributor, _graphQLDTOProperty, httpServletRequest,
				(long)id);
		}
		else if (_operation == Operation.LIST) {
			return _graphQLDTOContributorDataFetchingProcessor.list(
				dataFetchingEnvironment.getArgument("aggregation"),
				(String)dataFetchingEnvironment.getArgument("filter"),
				_graphQLDTOContributor, httpServletRequest,
				dataFetchingEnvironment.getArgument("page"),
				dataFetchingEnvironment.getArgument("pageSize"),
				dataFetchingEnvironment.getArgument("scopeKey"),
				dataFetchingEnvironment.getArgument("search"),
				dataFetchingEnvironment.getArgument("sort"));
		}
		else if (_operation == Operation.UPDATE) {
			return _graphQLDTOContributorDataFetchingProcessor.update(
				dataFetchingEnvironment.<Map<String, Serializable>>getArgument(
					_graphQLDTOContributor.getResourceName()),
				_graphQLDTOContributor, httpServletRequest,
				dataFetchingEnvironment.getArgument(
					_graphQLDTOContributor.getIdName()));
		}

		throw new UnsupportedOperationException(
			"Operation not supported: " + _operation);
	}

	public enum Operation {

		CREATE, DELETE, GET, GET_RELATIONSHIP, LIST, UPDATE

	}

	private final GraphQLDTOContributor _graphQLDTOContributor;
	private final GraphQLDTOContributorDataFetchingProcessor
		_graphQLDTOContributorDataFetchingProcessor;
	private final GraphQLDTOProperty _graphQLDTOProperty;
	private final Operation _operation;

}