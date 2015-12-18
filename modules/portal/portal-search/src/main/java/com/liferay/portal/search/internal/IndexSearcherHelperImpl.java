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

package com.liferay.portal.search.internal;

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
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = IndexSearcherHelper.class)
public class IndexSearcherHelperImpl implements IndexSearcherHelper {

	@Override
	public String getQueryString(SearchContext searchContext, Query query) {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		try {
			return indexSearcher.getQueryString(searchContext, query);
		}
		catch (ParseException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse query " + query, pe);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + getQueryString(searchContext, query));
		}

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.search(searchContext, query);
	}

	@Override
	public long searchCount(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + getQueryString(searchContext, query));
		}

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.searchCount(searchContext, query);
	}

	@Override
	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Spell checking " + searchContext.getKeywords());
		}

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			searchContext.getSearchEngineId());

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

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			searchContext.getSearchEngineId());

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

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.suggestKeywordQueries(searchContext, max);
	}

	@Reference(unbind = "-")
	protected void setSearchEngineHelper(
		SearchEngineHelper searchEngineHelper) {

		_searchEngineHelper = searchEngineHelper;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexSearcherHelperImpl.class);

	private volatile SearchEngineHelper _searchEngineHelper;

}