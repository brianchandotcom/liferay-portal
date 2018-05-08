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
import com.liferay.portal.kernel.search.hits.HitsProcessor;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Josef Sustacek
 */
@Component(
	immediate = true, property = "sort.order=3", service = HitsProcessor.class
)
public class QuerySuggestionHitsProcessor implements HitsProcessor {

	@Override
	public boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (!queryConfig.isQuerySuggestionEnabled()) {
			return true;
		}

		if (hits.getLength() >=
				queryConfig.getQuerySuggestionScoresThreshold()) {

			return true;
		}

		String[] querySuggestions =
			IndexSearcherHelperUtil.suggestKeywordQueries(
				searchContext, queryConfig.getQuerySuggestionMax());

		querySuggestions = ArrayUtil.remove(
			querySuggestions, searchContext.getKeywords());

		hits.setQuerySuggestions(querySuggestions);

		return true;
	}

}