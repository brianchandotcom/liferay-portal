/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class IndexSearcherHelperUtil {

	public static String getQueryString(
		SearchContext searchContext, Query query) {

		IndexSearcherHelper indexSearcherHelper =
			_indexSearcherHelperSnapshot.get();

		return indexSearcherHelper.getQueryString(searchContext, query);
	}

	public static Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		IndexSearcherHelper indexSearcherHelper =
			_indexSearcherHelperSnapshot.get();

		return indexSearcherHelper.search(searchContext, query);
	}

	public static long searchCount(SearchContext searchContext, Query query)
		throws SearchException {

		IndexSearcherHelper indexSearcherHelper =
			_indexSearcherHelperSnapshot.get();

		return indexSearcherHelper.searchCount(searchContext, query);
	}

	public static String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		IndexSearcherHelper indexSearcherHelper =
			_indexSearcherHelperSnapshot.get();

		return indexSearcherHelper.spellCheckKeywords(searchContext);
	}

	public static Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		IndexSearcherHelper indexSearcherHelper =
			_indexSearcherHelperSnapshot.get();

		return indexSearcherHelper.spellCheckKeywords(searchContext, max);
	}

	public static String[] suggestKeywordQueries(
			SearchContext searchContext, int max)
		throws SearchException {

		IndexSearcherHelper indexSearcherHelper =
			_indexSearcherHelperSnapshot.get();

		return indexSearcherHelper.suggestKeywordQueries(searchContext, max);
	}

	private static final Snapshot<IndexSearcherHelper>
		_indexSearcherHelperSnapshot = new Snapshot<>(
			IndexSearcherHelperUtil.class, IndexSearcherHelper.class);

}