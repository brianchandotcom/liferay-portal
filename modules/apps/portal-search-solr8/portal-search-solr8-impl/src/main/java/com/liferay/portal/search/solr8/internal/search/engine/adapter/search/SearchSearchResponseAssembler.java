/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.solr8.internal.search.response.SearchSearchResponseAssemblerHelper;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * @author Bryan Engler
 */
public class SearchSearchResponseAssembler {

	public static final SearchSearchResponseAssembler INSTANCE =
		new SearchSearchResponseAssembler();

	public void assemble(
		SearchSearchResponse searchSearchResponse, SolrQuery solrQuery,
		QueryResponse queryResponse, SearchSearchRequest searchSearchRequest) {

		BaseSearchResponseAssembler.INSTANCE.assemble(
			searchSearchResponse, solrQuery, queryResponse,
			searchSearchRequest);

		_searchSearchResponseAssemblerHelper.populate(
			searchSearchResponse, queryResponse, searchSearchRequest);
	}

	private SearchSearchResponseAssembler() {
	}

	private final SearchSearchResponseAssemblerHelper
		_searchSearchResponseAssemblerHelper =
			new SearchSearchResponseAssemblerHelper();

}