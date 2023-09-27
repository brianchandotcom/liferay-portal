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
import com.liferay.portal.kernel.search.hits.HitsProcessor;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Josef Sustacek
 */
@Component(property = "sort.order=3", service = HitsProcessor.class)
public class QuerySuggestionHitsProcessor implements HitsProcessor {

	@Override
	public boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (!queryConfig.isQuerySuggestionEnabled() ||
			(hits.getLength() >=
				queryConfig.getQuerySuggestionScoresThreshold())) {

			return true;
		}

		String[] querySuggestions =
			IndexSearcherHelperUtil.suggestKeywordQueries(
				searchContext, queryConfig.getQuerySuggestionMax());

		hits.setQuerySuggestions(
			ArrayUtil.remove(querySuggestions, searchContext.getKeywords()));

		return true;
	}

}