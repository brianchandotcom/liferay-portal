/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.searcher.helper;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = IndexSearcherHelper.class)
public class IndexSearcherHelperImpl implements IndexSearcherHelper {

	@Override
	public String getQueryString(SearchContext searchContext, Query query) {
		return _indexSearcherHelper.getQueryString(searchContext, query);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query) {
		try {
			return _indexSearcherHelper.search(searchContext, query);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	@Override
	public long searchCount(SearchContext searchContext, Query query) {
		try {
			return _indexSearcherHelper.searchCount(searchContext, query);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	@Override
	public String spellCheckKeywords(SearchContext searchContext) {
		try {
			return _indexSearcherHelper.spellCheckKeywords(searchContext);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
		SearchContext searchContext, int max) {

		try {
			return _indexSearcherHelper.spellCheckKeywords(searchContext, max);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	@Override
	public String[] suggestKeywordQueries(
		SearchContext searchContext, int max) {

		try {
			return _indexSearcherHelper.suggestKeywordQueries(
				searchContext, max);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	private RuntimeException _uncheck(SearchException searchException) {
		if (searchException.getCause() instanceof RuntimeException) {
			return (RuntimeException)searchException.getCause();
		}

		if (searchException.getCause() != null) {
			return new RuntimeException(searchException.getCause());
		}

		return new RuntimeException(searchException);
	}

	@Reference
	private com.liferay.portal.kernel.search.IndexSearcherHelper
		_indexSearcherHelper;

}