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

package com.liferay.portal.search.web.search.request;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface SearchSettings {

	public void addCondition(BooleanClause<Query> booleanClause);

	public void addFacet(Facet facet);

	public SearchRequestBuilder getFederatedSearchRequestBuilder(
		Optional<String> federatedSearchKeyOptional);

	public Optional<String> getKeywordsParameterName();

	public Optional<Integer> getPaginationDelta();

	public Optional<String> getPaginationDeltaParameterName();

	public Optional<Integer> getPaginationStart();

	public Optional<String> getPaginationStartParameterName();

	public QueryConfig getQueryConfig();

	public SearchContext getSearchContext();

	public SearchRequestBuilder getSearchRequestBuilder();

	public void setKeywords(String keywords);

	public void setKeywordsParameterName(String keywordsParameterName);

	public void setPaginationDelta(int paginationDelta);

	public void setPaginationDeltaParameterName(
		String paginationDeltaParameterName);

	public void setPaginationStart(int paginationStart);

	public void setPaginationStartParameterName(
		String paginationStartParameterName);

}