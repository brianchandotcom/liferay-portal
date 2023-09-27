/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.search.request;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface SearchSettings {

	public void addCondition(BooleanClause<Query> booleanClause);

	public void addFacet(Facet facet);

	public SearchRequestBuilder getFederatedSearchRequestBuilder(
		String federatedSearchKey);

	public String getKeywordsParameterName();

	public Integer getPaginationDelta();

	public String getPaginationDeltaParameterName();

	public Integer getPaginationStart();

	public String getPaginationStartParameterName();

	public QueryConfig getQueryConfig();

	public String getScope();

	public String getScopeParameterName();

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

	public void setScope(String scope);

	public void setScopeParameterName(String scopeParameterName);

}