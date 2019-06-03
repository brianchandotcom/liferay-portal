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

import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.solr7.internal.search.response.SearchSearchResponseAssemblerHelper;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = SearchSearchResponseAssembler.class)
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

	@Reference(unbind = "-")
	protected void setBaseSearchResponseAssembler(
		BaseSearchResponseAssembler baseSearchResponseAssembler) {

		_baseSearchResponseAssembler = baseSearchResponseAssembler;
	}

	@Reference(unbind = "-")
	protected void setSearchSearchResponseAssemblerHelper(
		SearchSearchResponseAssemblerHelper
			searchSearchResponseAssemblerHelper) {

		_searchSearchResponseAssemblerHelper =
			searchSearchResponseAssemblerHelper;
	}

	private BaseSearchResponseAssembler _baseSearchResponseAssembler;
	private SearchSearchResponseAssemblerHelper
		_searchSearchResponseAssemblerHelper;

}