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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = SearchSearchResponseAssembler.class)
public class SearchSearchResponseAssemblerImpl
	implements SearchSearchResponseAssembler {

	@Override
	public void assemble(
		SearchSearchResponse searchSearchResponse, SolrQuery solrQuery,
		QueryResponse queryResponse, SearchSearchRequest searchSearchRequest) {

		_baseSearchResponseAssembler.assemble(
			searchSearchResponse, solrQuery, queryResponse,
			searchSearchRequest);

		_searchSearchResponseAssemblerHelper.populate(
			searchSearchResponse, queryResponse, searchSearchRequest);
	}

	@Reference
	private BaseSearchResponseAssembler _baseSearchResponseAssembler;

	@Reference
	private SearchSearchResponseAssemblerHelper
		_searchSearchResponseAssemblerHelper;

}