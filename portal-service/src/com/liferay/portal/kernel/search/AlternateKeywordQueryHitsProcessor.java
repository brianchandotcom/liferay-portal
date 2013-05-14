/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
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

		String spellCheckedKeywords = hits.getCollatedSpellCheckResult();

		searchContext.overrideKeywords(spellCheckedKeywords);

		String[] additionalQuerySuggestions =
			SearchEngineUtil.suggestKeywordQueries(searchContext, 5);

		if ((additionalQuerySuggestions != null) &&
			(additionalQuerySuggestions.length > 0)) {

			searchContext.setKeywords(additionalQuerySuggestions[0]);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHitsProcessingEnabled(false);

		Indexer indexer = FacetedSearcher.getInstance();

		Hits alternateResults = indexer.search(searchContext);

		hits.copy(alternateResults);

		return true;
	}

}