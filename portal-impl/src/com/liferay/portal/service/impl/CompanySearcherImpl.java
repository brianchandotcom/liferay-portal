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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.FacetBuilder;
import com.liferay.portal.kernel.search.facet.FacetConstants;
import com.liferay.portal.kernel.search.facet.FacetManager;
import com.liferay.portal.kernel.search.facet.FacetManagerUtil;
import com.liferay.portal.kernel.search.facet.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.search.facet.searcher.FacetedSearcherManagerUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author André de Oliveira
 */
public class CompanySearcherImpl {

	public Hits search(
		long companyId, long userId, String portletId, long groupId,
		String keywords, int start, int end) {

		SearchContext searchContext = createSearchContext(
			companyId, userId, portletId, groupId, keywords, start, end);

		addAssetEntriesFacet(searchContext);

		addScopeFacet(searchContext);

		FacetedSearcherManager facetedSearcherManager =
			FacetedSearcherManagerUtil.getInstance();

		FacetedSearcher facetedSearcher =
			facetedSearcherManager.createFacetedSearcher();

		try {
			return facetedSearcher.search(searchContext);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	protected void addAssetEntriesFacet(SearchContext searchContext) {
		FacetBuilder facetBuilder = facetManager.createFacetBuilder(
			FacetConstants.ASSET_ENTRIES_FACET, searchContext);

		facetBuilder.setStatic(true);

		searchContext.addFacet(facetBuilder.build());
	}

	protected void addScopeFacet(SearchContext searchContext) {
		FacetBuilder facetBuilder = facetManager.createFacetBuilder(
			FacetConstants.SCOPE_FACET, searchContext);

		facetBuilder.setStatic(true);

		searchContext.addFacet(facetBuilder.build());
	}

	protected SearchContext createSearchContext(
		long companyId, long userId, String portletId, long groupId,
		String keywords, int start, int end) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setEntryClassNames(
			SearchEngineHelperUtil.getEntryClassNames());

		if (groupId > 0) {
			searchContext.setGroupIds(new long[] {groupId});
		}

		searchContext.setKeywords(keywords);

		if (Validator.isNotNull(portletId)) {
			searchContext.setPortletIds(new String[] {portletId});
		}

		searchContext.setStart(start);
		searchContext.setUserId(userId);

		return searchContext;
	}

	protected FacetManager facetManager = FacetManagerUtil.getInstance();

}