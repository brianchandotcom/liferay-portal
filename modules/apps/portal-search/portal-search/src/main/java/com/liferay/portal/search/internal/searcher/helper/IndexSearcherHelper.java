/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.searcher.helper;

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