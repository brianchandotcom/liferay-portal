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

package com.liferay.portal.search.solr7.internal.search.engine.adapter.search;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = SearchSearchRequestExecutor.class)
public class SearchSearchRequestExecutorImpl
	implements SearchSearchRequestExecutor {

	@Override
	public SearchSearchResponse execute(
		SearchSearchRequest searchSearchRequest) {

		SolrQuery solrQuery = new SolrQuery();

		_searchSolrQueryAssembler.assemble(solrQuery, searchSearchRequest);

		String requestString = solrQuery.toString();

		String debugRequestString = _getDebugRequestString(requestString);

		if (_log.isDebugEnabled()) {
			_log.debug("Search query: " + debugRequestString);
		}

		QueryResponse queryResponse = getQueryResponse(
			new QueryRequest(solrQuery), searchSearchRequest.getIndexNames());

		SearchSearchResponse searchSearchResponse = new SearchSearchResponse();

		_searchSearchResponseAssembler.assemble(
			searchSearchResponse, solrQuery, queryResponse,
			searchSearchRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"The search engine processed ",
					searchSearchResponse.getSearchRequestString(), " in ",
					searchSearchResponse.getExecutionTime(), " ms"));
		}

		return searchSearchResponse;
	}

	protected QueryResponse getQueryResponse(
		QueryRequest queryRequest, String[] indexNames) {

		try {
			queryRequest.setMethod(SolrRequest.METHOD.POST);

			return queryRequest.process(
				_solrClientManager.getSolrClient(), indexNames[0]);
		}
		catch (Exception e) {
			if (e instanceof SolrException) {
				SolrException se = (SolrException)e;

				throw se;
			}

			throw new RuntimeException(e);
		}
	}

	@Reference(unbind = "-")
	protected void setSearchSearchResponseAssembler(
		SearchSearchResponseAssembler searchSearchResponseAssembler) {

		_searchSearchResponseAssembler = searchSearchResponseAssembler;
	}

	@Reference(unbind = "-")
	protected void setSearchSolrQueryAssembler(
		SearchSolrQueryAssembler searchSolrQueryAssembler) {

		_searchSolrQueryAssembler = searchSolrQueryAssembler;
	}

	@Reference(unbind = "-")
	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	private String _getDebugRequestString(String requestString) {
		requestString = URLCodec.decodeURL(requestString);

		requestString = StringUtil.replace(
			requestString, CharPool.AMPERSAND,
			StringPool.NEW_LINE + StringPool.AMPERSAND);

		return requestString;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchSearchRequestExecutorImpl.class);

	private SearchSearchResponseAssembler _searchSearchResponseAssembler;
	private SearchSolrQueryAssembler _searchSolrQueryAssembler;
	private SolrClientManager _solrClientManager;

}