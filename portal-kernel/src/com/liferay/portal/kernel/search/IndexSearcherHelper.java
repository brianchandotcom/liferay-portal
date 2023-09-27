/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public interface IndexSearcherHelper {

	public String getQueryString(SearchContext searchContext, Query query);

	public Hits search(SearchContext searchContext, Query query)
		throws SearchException;

	public long searchCount(SearchContext searchContext, Query query)
		throws SearchException;

	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException;

	public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException;

	public String[] suggestKeywordQueries(SearchContext searchContext, int max)
		throws SearchException;

}