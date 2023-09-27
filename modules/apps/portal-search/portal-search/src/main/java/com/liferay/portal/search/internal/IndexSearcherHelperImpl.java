/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexSearcherHelper;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchException;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = IndexSearcherHelper.class)
public class IndexSearcherHelperImpl implements IndexSearcherHelper {

	@Override
	public String getQueryString(SearchContext searchContext, Query query) {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		try {
			return indexSearcher.getQueryString(searchContext, query);
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse query " + query, parseException);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.search(searchContext, query);
	}

	@Override
	public long searchCount(SearchContext searchContext, Query query)
		throws SearchException {

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.searchCount(searchContext, query);
	}

	@Override
	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Spell checking " + searchContext.getKeywords());
		}

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.spellCheckKeywords(searchContext);
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Spell checking " + searchContext.getKeywords());
		}

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.spellCheckKeywords(searchContext, max);
	}

	@Override
	public String[] suggestKeywordQueries(SearchContext searchContext, int max)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Suggesting keyword queries" + searchContext.getKeywords());
		}

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.suggestKeywordQueries(searchContext, max);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexSearcherHelperImpl.class);

	@Reference
	private SearchEngineHelper _searchEngineHelper;

}