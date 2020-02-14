/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.request;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetFields;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adam Brandizzi
 */
public class SearchSynonymSetRequest {

	public SearchSynonymSetRequest(
		SynonymSetIndexName synonymSetIndexName,
		HttpServletRequest httpServletRequest, Queries queries, Sorts sorts,
		SearchContainer searchContainer,
		SearchEngineAdapter searchEngineAdapter) {

		_synonymSetIndexName = synonymSetIndexName;
		_httpServletRequest = httpServletRequest;
		_queries = queries;
		_sorts = sorts;
		_searchContainer = searchContainer;
		_searchEngineAdapter = searchEngineAdapter;

		_searchContext = SearchContextFactory.getInstance(httpServletRequest);
	}

	public SearchSynonymSetResponse search() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setIndexNames(_synonymSetIndexName.getIndexName());
		searchSearchRequest.setQuery(_getQuery());
		searchSearchRequest.setSize(_searchContainer.getDelta());
		searchSearchRequest.setSorts(_getSorts());
		searchSearchRequest.setStart(_searchContainer.getStart());

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchSynonymSetResponse searchRankingResponse =
			new SearchSynonymSetResponse();

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		searchRankingResponse.setSearchHits(searchHits);
		searchRankingResponse.setTotalHits((int)searchHits.getTotalHits());

		return searchRankingResponse;
	}

	private Query _getQuery() {
		String keywords = _searchContext.getKeywords();

		if (!Validator.isBlank(keywords)) {
			return _queries.match(SynonymSetFields.SYNONYMS, keywords);
		}

		return _queries.matchAll();
	}

	private Collection<Sort> _getSorts() {
		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol",
			SynonymSetFields.SYNONYMS_KEYWORD);
		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		SortOrder sortOrder = SortOrder.ASC;

		if (Objects.equals(orderByType, "desc")) {
			sortOrder = SortOrder.DESC;
		}

		return Arrays.asList(_sorts.field(orderByCol, sortOrder));
	}

	private final HttpServletRequest _httpServletRequest;
	private final Queries _queries;
	private final SearchContainer _searchContainer;
	private final SearchContext _searchContext;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final Sorts _sorts;
	private final SynonymSetIndexName _synonymSetIndexName;

}