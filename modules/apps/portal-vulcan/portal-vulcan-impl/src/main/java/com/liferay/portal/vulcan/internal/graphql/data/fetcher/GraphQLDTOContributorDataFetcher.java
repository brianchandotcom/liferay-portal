/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		GraphQLDTOContributor.Operation operation) {

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
		GraphQLDTOContributor.Operation operation) {

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

		if (_operation == GraphQLDTOContributor.Operation.CREATE) {
			return _graphQLDTOContributorDataFetchingProcessor.create(
				dataFetchingEnvironment.getArgument(
					_graphQLDTOContributor.getResourceName()),
				_graphQLDTOContributor, httpServletRequest,
				(String)dataFetchingEnvironment.getArgument("scopeKey"));
		}
		else if (_operation == GraphQLDTOContributor.Operation.DELETE) {
			return _graphQLDTOContributorDataFetchingProcessor.delete(
				_graphQLDTOContributor,
				dataFetchingEnvironment.<Long>getArgument(
					_graphQLDTOContributor.getIdName()));
		}
		else if (_operation == GraphQLDTOContributor.Operation.GET) {
			return _graphQLDTOContributorDataFetchingProcessor.get(
				_graphQLDTOContributor, httpServletRequest,
				dataFetchingEnvironment.getArgument(
					_graphQLDTOContributor.getIdName()));
		}
		else if (_operation ==
					GraphQLDTOContributor.Operation.GET_RELATIONSHIP) {

			Map<String, Object> source = dataFetchingEnvironment.getSource();

			Object id = source.get(_graphQLDTOContributor.getIdName());

			if (!(id instanceof Long)) {
				return null;
			}

			return _graphQLDTOContributorDataFetchingProcessor.getRelationship(
				_graphQLDTOContributor, _graphQLDTOProperty, httpServletRequest,
				(long)id);
		}
		else if (_operation == GraphQLDTOContributor.Operation.LIST) {
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
		else if (_operation == GraphQLDTOContributor.Operation.UPDATE) {
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

	private final GraphQLDTOContributor _graphQLDTOContributor;
	private final GraphQLDTOContributorDataFetchingProcessor
		_graphQLDTOContributorDataFetchingProcessor;
	private final GraphQLDTOProperty _graphQLDTOProperty;
	private final GraphQLDTOContributor.Operation _operation;

}