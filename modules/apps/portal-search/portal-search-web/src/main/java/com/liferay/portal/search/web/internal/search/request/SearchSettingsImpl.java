/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.search.request;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.util.Map;

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

		facets.put(_getAggregationName(facet), facet);
	}

	@Override
	public SearchRequestBuilder getFederatedSearchRequestBuilder(
		String federatedSearchKey) {

		return _searchRequestBuilder.getFederatedSearchRequestBuilder(
			GetterUtil.getString(federatedSearchKey));
	}

	@Override
	public String getKeywordsParameterName() {
		return _keywordsParameterName;
	}

	@Override
	public Integer getPaginationDelta() {
		return _paginationDelta;
	}

	@Override
	public String getPaginationDeltaParameterName() {
		return _paginationDeltaParameterName;
	}

	@Override
	public Integer getPaginationStart() {
		return _paginationStart;
	}

	@Override
	public String getPaginationStartParameterName() {
		return _paginationStartParameterName;
	}

	@Override
	public QueryConfig getQueryConfig() {
		return _searchContext.getQueryConfig();
	}

	@Override
	public String getScope() {
		return _scope;
	}

	@Override
	public String getScopeParameterName() {
		return _scopeParameterName;
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

	@Override
	public void setScope(String scope) {
		_scope = scope;
	}

	@Override
	public void setScopeParameterName(String scopeParameterName) {
		_scopeParameterName = scopeParameterName;
	}

	private String _getAggregationName(Facet facet) {
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
	private String _scope;
	private String _scopeParameterName;
	private final SearchContext _searchContext;
	private final SearchRequestBuilder _searchRequestBuilder;

}