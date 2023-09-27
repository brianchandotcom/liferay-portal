/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.suggest.QuerySuggester;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
public interface IndexSearcher extends QuerySuggester {

	public String getQueryString(SearchContext searchContext, Query query)
		throws ParseException;

	public Hits search(SearchContext searchContext, Query query)
		throws SearchException;

	public long searchCount(SearchContext searchContext, Query query)
		throws SearchException;

}