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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.search.elasticsearch6.internal.search.response.SearchResponseTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SearchSearchResponseAssembler.class)
public class SearchSearchResponseAssemblerImpl
	implements SearchSearchResponseAssembler {

	@Override
	public void assemble(
		SearchResponse searchResponse,
		SearchSearchResponse searchSearchResponse,
		SearchSearchRequest searchSearchRequest,
		String searchRequestBuilderString) {

		commonSearchResponseAssembler.assemble(
			searchResponse, searchSearchResponse, searchRequestBuilderString);

		SearchHits searchHits = searchResponse.getHits();

		searchSearchResponse.setCount(searchHits.totalHits);

		Hits hits = searchResponseTranslator.translate(
			searchResponse, searchSearchRequest.getFacets(),
			searchSearchRequest.getGroupBy(), searchSearchRequest.getStats(),
			searchSearchRequest.getAlternateUidFieldName(),
			searchSearchRequest.getHighlightFieldNames(),
			searchSearchRequest.getLocale());

		searchSearchResponse.setHits(hits);
	}

	@Reference
	protected CommonSearchResponseAssembler commonSearchResponseAssembler;

	@Reference
	protected SearchResponseTranslator searchResponseTranslator;

}