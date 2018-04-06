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

package com.liferay.portal.search.internal.hits;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.search.hits.HitsProcessor;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "sort.order=1", service = HitsProcessor.class
)
public class AlternateKeywordQueryHitsProcessor implements HitsProcessor {

	@Override
	public boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		if (hits.getLength() > 0) {
			return true;
		}

		Map<String, List<String>> spellCheckResults =
			hits.getSpellCheckResults();

		if (spellCheckResults == null) {
			return true;
		}

		FacetedSearcher facetedSearcher =
			facetedSearcherManager.createFacetedSearcher();

		String spellCheckedKeywords = hits.getCollatedSpellCheckResult();

		searchContext.overrideKeywords(spellCheckedKeywords);

		String[] querySuggestions =
			IndexSearcherHelperUtil.suggestKeywordQueries(searchContext, 5);

		if (ArrayUtil.isNotEmpty(querySuggestions)) {
			searchContext.setKeywords(querySuggestions[0]);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHitsProcessingEnabled(false);

		Hits alternateResults = facetedSearcher.search(searchContext);

		hits.copy(alternateResults);

		return true;
	}

	@Reference
	protected FacetedSearcherManager facetedSearcherManager;

}