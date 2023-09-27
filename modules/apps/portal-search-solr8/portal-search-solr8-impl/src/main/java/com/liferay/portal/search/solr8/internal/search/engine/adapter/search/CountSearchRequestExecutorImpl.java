/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = CountSearchRequestExecutor.class)
public class CountSearchRequestExecutorImpl
	implements CountSearchRequestExecutor {

	@Override
	public CountSearchResponse execute(CountSearchRequest countSearchRequest) {
		SolrQuery solrQuery = new SolrQuery();

		_baseSolrQueryAssembler.assemble(solrQuery, countSearchRequest);

		solrQuery.setRows(0);

		String requestString = solrQuery.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + requestString);
		}

		QueryResponse queryResponse = getQueryResponse(
			new QueryRequest(solrQuery), countSearchRequest.getIndexNames());

		CountSearchResponse countSearchResponse = new CountSearchResponse();

		_baseSearchResponseAssembler.assemble(
			countSearchResponse, solrQuery, queryResponse, countSearchRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"The search engine processed ",
					countSearchResponse.getSearchRequestString(), " in ",
					countSearchResponse.getExecutionTime(), " ms"));
		}

		SolrDocumentList solrDocumentList = queryResponse.getResults();

		countSearchResponse.setCount(solrDocumentList.getNumFound());

		return countSearchResponse;
	}

	protected QueryResponse getQueryResponse(
		QueryRequest queryRequest, String[] indexNames) {

		try {
			queryRequest.setMethod(SolrRequest.METHOD.POST);

			return queryRequest.process(
				_solrClientManager.getSolrClient(), indexNames[0]);
		}
		catch (Exception exception) {
			if (exception instanceof SolrException) {
				SolrException solrException = (SolrException)exception;

				throw solrException;
			}

			throw new RuntimeException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CountSearchRequestExecutorImpl.class);

	@Reference
	private BaseSearchResponseAssembler _baseSearchResponseAssembler;

	@Reference
	private BaseSolrQueryAssembler _baseSolrQueryAssembler;

	@Reference
	private SolrClientManager _solrClientManager;

}