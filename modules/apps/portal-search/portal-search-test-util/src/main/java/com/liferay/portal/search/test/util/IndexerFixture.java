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

package com.liferay.portal.search.test.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Locale;

/**
 * @author Lucas Marques de Paula
 * @autor Adam Brandizzi
 */
public class IndexerFixture<T> {

	public IndexerFixture(Class<T> clazz) {
		_indexer = IndexerRegistryUtil.getIndexer(clazz);
	}

	public void deleteDocument(Document document)
		throws PortalException, SearchException {

		IndexWriterHelperUtil.deleteDocument(
			_indexer.getSearchEngineId(), TestPropsValues.getCompanyId(),
			document.getUID(), true);
	}

	public void reindex(long companyId) throws Exception {
		_indexer.reindex(new String[] {String.valueOf(companyId)});
	}

	public void searchNoOne(long userId, String keywords, Locale locale)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			userId, keywords, locale);

		Hits hits = _indexer.search(searchContext);

		HitsAssert.assertNoHits(hits);
	}

	public void searchNoOne(String keywords) throws Exception {
		searchNoOne(TestPropsValues.getUserId(), keywords, null);
	}

	public void searchNoOne(String keywords, Locale locale) throws Exception {
		searchNoOne(TestPropsValues.getUserId(), keywords, locale);
	}

	public Document searchOnlyOne(long userId, String keywords, Locale locale)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			userId, keywords, locale);

		Hits hits = _indexer.search(searchContext);

		return HitsAssert.assertOnlyOne(hits);
	}

	public Document searchOnlyOne(String keywords) throws Exception {
		return searchOnlyOne(TestPropsValues.getUserId(), keywords, null);
	}

	public Document searchOnlyOne(String keywords, Locale locale)
		throws Exception {

		return searchOnlyOne(TestPropsValues.getUserId(), keywords, locale);
	}

	private final Indexer<T> _indexer;

}