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

package com.liferay.portal.search.internal.searcher;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;

import java.util.List;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public interface IndexSearcherHelper {

	public String getQueryString(SearchContext searchContext, Query query);

	public Hits search(SearchContext searchContext, Query query);

	public long searchCount(SearchContext searchContext, Query query);

	public String spellCheckKeywords(SearchContext searchContext);

	public Map<String, List<String>> spellCheckKeywords(
		SearchContext searchContext, int max);

	public String[] suggestKeywordQueries(SearchContext searchContext, int max);

}