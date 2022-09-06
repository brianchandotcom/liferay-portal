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

package com.liferay.portal.vulcan.internal.graphql.servlet.data.fetcher;

import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOProperty;
import com.liferay.portal.vulcan.internal.graphql.servlet.data.processor.GraphQLDTOContributorDataFetchingProcessor;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import graphql.servlet.GraphQLContext;

import java.io.Serializable;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Correa
 */
public class GraphQLDTOContributorDataFetcher implements DataFetcher<Object> {

	public GraphQLDTOContributorDataFetcher(
		GraphQLDTOContributor graphQLDTOContributor,
		GraphQLDTOContributorDataFetchingProcessor
			graphQLDTOContributorDataFetchingProcessor,
		GraphQLDTOProperty graphQLDTOProperty, Operation operation) {

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
		Operation operation) {

		this(
			graphQLDTOContributor, graphQLDTOContributorDataFetchingProcessor,
			null, operation);
	}

	@Override
	public Object get(DataFetchingEnvironment dataFetchingEnvironment)
		throws Exception {

		if (_operation == Operation.CREATE) {
			return _graphQLDTOContributorDataFetchingProcessor.create(
				dataFetchingEnvironment.getArgument(
					_graphQLDTOContributor.getResourceName()),
				_graphQLDTOContributor,
				_getHttpServletRequest(dataFetchingEnvironment),
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
				_graphQLDTOContributor,
				_getHttpServletRequest(dataFetchingEnvironment),
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
				_graphQLDTOContributor, _graphQLDTOProperty,
				_getHttpServletRequest(dataFetchingEnvironment), (long)id);
		}
		else if (_operation == Operation.LIST) {
			return _graphQLDTOContributorDataFetchingProcessor.list(
				dataFetchingEnvironment.getArgument("aggregation"),
				(String)dataFetchingEnvironment.getArgument("filter"),
				_graphQLDTOContributor,
				_getHttpServletRequest(dataFetchingEnvironment),
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
				_graphQLDTOContributor,
				_getHttpServletRequest(dataFetchingEnvironment),
				dataFetchingEnvironment.getArgument(
					_graphQLDTOContributor.getIdName()));
		}

		throw new UnsupportedOperationException(
			"Operation not supported: " + _operation);
	}

	public enum Operation {

		CREATE, DELETE, GET, GET_RELATIONSHIP, LIST, UPDATE

	}

	private HttpServletRequest _getHttpServletRequest(
		DataFetchingEnvironment dataFetchingEnvironment) {

		GraphQLContext graphQLContext = dataFetchingEnvironment.getContext();

		Optional<HttpServletRequest> httpServletRequestOptional =
			graphQLContext.getHttpServletRequest();

		return httpServletRequestOptional.orElse(null);
	}

	private final GraphQLDTOContributor _graphQLDTOContributor;
	private final GraphQLDTOContributorDataFetchingProcessor
		_graphQLDTOContributorDataFetchingProcessor;
	private final GraphQLDTOProperty _graphQLDTOProperty;
	private final Operation _operation;

}