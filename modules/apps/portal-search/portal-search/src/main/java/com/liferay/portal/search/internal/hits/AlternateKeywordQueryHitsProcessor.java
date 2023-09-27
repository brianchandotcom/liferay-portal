/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(property = "sort.order=1", service = HitsProcessor.class)
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