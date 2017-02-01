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

import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.util.Optional;

/**
 * @author André de Oliveira
 */
public class SearchSettingsImpl implements SearchSettings {

	public SearchSettingsImpl(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	@Override
	public void addFacet(Facet facet) {
		_searchContext.addFacet(facet);
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
	public void setKeywords(String keywords) {
		_searchContext.setKeywords(keywords);
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

	private Integer _paginationDelta;
	private String _paginationDeltaParameterName;
	private Integer _paginationStart;
	private String _paginationStartParameterName;
	private final SearchContext _searchContext;

}