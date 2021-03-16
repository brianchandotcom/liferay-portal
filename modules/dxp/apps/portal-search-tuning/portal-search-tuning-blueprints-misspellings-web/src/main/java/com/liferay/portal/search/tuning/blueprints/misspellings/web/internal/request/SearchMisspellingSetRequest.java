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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.request;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexName;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingSetFields;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class SearchMisspellingSetRequest {

	public SearchMisspellingSetRequest(
		MisspellingsIndexName misspellingsIndexName,
		HttpServletRequest httpServletRequest, Queries queries, Sorts sorts,
		SearchContainer<MisspellingSet> searchContainer,
		SearchEngineAdapter searchEngineAdapter) {

		_misspellingsIndexName = misspellingsIndexName;
		_httpServletRequest = httpServletRequest;
		_queries = queries;
		_sorts = sorts;
		_searchContainer = searchContainer;
		_searchEngineAdapter = searchEngineAdapter;

		_searchContext = SearchContextFactory.getInstance(httpServletRequest);
	}

	public SearchMisspellingSetResponse search() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setIndexNames(
			_misspellingsIndexName.getIndexName());
		searchSearchRequest.setPreferLocalCluster(false);
		searchSearchRequest.setQuery(_getQuery());
		searchSearchRequest.setSize(_searchContainer.getDelta());
		searchSearchRequest.setSorts(_getSorts());
		searchSearchRequest.setStart(_searchContainer.getStart());

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchMisspellingSetResponse searchMisspellingSetResponse =
			new SearchMisspellingSetResponse();

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		searchMisspellingSetResponse.setSearchHits(searchHits);
		searchMisspellingSetResponse.setTotalHits(
			(int)searchHits.getTotalHits());

		return searchMisspellingSetResponse;
	}

	private Query _getQuery() {
		String keywords = _searchContext.getKeywords();

		if (!Validator.isBlank(keywords)) {
			BooleanQuery booleanQuery = _queries.booleanQuery();

			BooleanQuery keywordsQuery = _queries.booleanQuery();

			keywordsQuery.addShouldQueryClauses(
				_queries.match(MisspellingSetFields.PHRASE, keywords));
			keywordsQuery.addShouldQueryClauses(
				_queries.match(MisspellingSetFields.MISSPELLINGS, keywords));

			booleanQuery.addMustQueryClauses(keywordsQuery);

			return booleanQuery;
		}

		return _queries.matchAll();
	}

	private Collection<Sort> _getSorts() {
		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", MisspellingSetFields.CREATED);
		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		SortOrder sortOrder = SortOrder.ASC;

		if (Objects.equals(orderByType, "desc")) {
			sortOrder = SortOrder.DESC;
		}

		return Arrays.asList(_sorts.field(orderByCol, sortOrder));
	}

	private final HttpServletRequest _httpServletRequest;
	private final MisspellingsIndexName _misspellingsIndexName;
	private final Queries _queries;
	private final SearchContainer<MisspellingSet> _searchContainer;
	private final SearchContext _searchContext;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final Sorts _sorts;

}