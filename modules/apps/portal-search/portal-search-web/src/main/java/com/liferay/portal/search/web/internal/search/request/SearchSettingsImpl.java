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

package com.liferay.portal.search.web.internal.search.request;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.util.Map;
import java.util.Optional;

/**
 * @author André de Oliveira
 */
public class SearchSettingsImpl implements SearchSettings {

	public SearchSettingsImpl(
		SearchRequestBuilder searchRequestBuilder,
		SearchContext searchContext) {

		_searchRequestBuilder = searchRequestBuilder;
		_searchContext = searchContext;
	}

	@Override
	public void addCondition(BooleanClause<Query> booleanClause) {
		BooleanClause<Query>[] booleanClauses =
			_searchContext.getBooleanClauses();

		if (booleanClauses == null) {
			booleanClauses = new BooleanClause[] {booleanClause};
		}
		else {
			booleanClauses = ArrayUtil.append(booleanClauses, booleanClause);
		}

		_searchContext.setBooleanClauses(booleanClauses);
	}

	@Override
	public void addFacet(Facet facet) {
		Map<String, Facet> facets = _searchContext.getFacets();

		facets.put(getAggregationName(facet), facet);
	}

	@Override
	public SearchRequestBuilder getFederatedSearchRequestBuilder(
		Optional<String> federatedSearchKeyOptional) {

		return _searchRequestBuilder.getFederatedSearchRequestBuilder(
			federatedSearchKeyOptional.orElse(StringPool.BLANK));
	}

	@Override
	public Optional<String> getKeywordsParameterName() {
		return Optional.ofNullable(_keywordsParameterName);
	}

	@Override
	public Optional<Integer> getPaginationDelta() {
		return Optional.ofNullable(_paginationDelta);
	}

	@Override
	public Optional<String> getPaginationDeltaParameterName() {
		return Optional.ofNullable(_paginationDeltaParameterName);
	}

	@Override
	public Optional<Integer> getPaginationStart() {
		return Optional.ofNullable(_paginationStart);
	}

	@Override
	public Optional<String> getPaginationStartParameterName() {
		return Optional.ofNullable(_paginationStartParameterName);
	}

	@Override
	public QueryConfig getQueryConfig() {
		return _searchContext.getQueryConfig();
	}

	@Override
	public SearchContext getSearchContext() {
		return _searchContext;
	}

	@Override
	public SearchRequestBuilder getSearchRequestBuilder() {
		return _searchRequestBuilder;
	}

	@Override
	public void setKeywords(String keywords) {
		_searchContext.setKeywords(keywords);
	}

	@Override
	public void setKeywordsParameterName(String keywordsParameterName) {
		_keywordsParameterName = keywordsParameterName;
	}

	@Override
	public void setPaginationDelta(int paginationDelta) {
		_paginationDelta = paginationDelta;
	}

	@Override
	public void setPaginationDeltaParameterName(
		String paginationDeltaParameterName) {

		_paginationDeltaParameterName = paginationDeltaParameterName;
	}

	@Override
	public void setPaginationStart(int paginationStart) {
		_paginationStart = paginationStart;
	}

	@Override
	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	protected String getAggregationName(Facet facet) {
		if (facet instanceof com.liferay.portal.search.facet.Facet) {
			com.liferay.portal.search.facet.Facet osgiFacet =
				(com.liferay.portal.search.facet.Facet)facet;

			return osgiFacet.getAggregationName();
		}

		return facet.getFieldName();
	}

	private String _keywordsParameterName;
	private Integer _paginationDelta;
	private String _paginationDeltaParameterName;
	private Integer _paginationStart;
	private String _paginationStartParameterName;
	private final SearchContext _searchContext;
	private final SearchRequestBuilder _searchRequestBuilder;

}