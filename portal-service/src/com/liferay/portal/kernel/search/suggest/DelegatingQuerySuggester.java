/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.suggest;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class DelegatingQuerySuggester implements QuerySuggester {

	public void setQuerySuggester(QuerySuggester querySuggester) {
		_querySuggester = querySuggester;
	}

	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		return _querySuggester.spellCheckKeywords(searchContext);
	}

	public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		return _querySuggester.spellCheckKeywords(searchContext, max);
	}

	public String[] suggestKeywordQueries(SearchContext searchContext, int max)
		throws SearchException {

		return _querySuggester.suggestKeywordQueries(searchContext, max);
	}

	private QuerySuggester _querySuggester;

}