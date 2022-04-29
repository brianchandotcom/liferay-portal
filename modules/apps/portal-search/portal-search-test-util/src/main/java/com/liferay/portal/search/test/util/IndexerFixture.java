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
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Lucas Marques de Paula
 */
public class IndexerFixture<T> {

	public IndexerFixture(Class<T> clazz) {
		_indexer = IndexerRegistryUtil.getIndexer(clazz);
	}

	public void deleteDocument(Document document) {
		try {
			IndexWriterHelperUtil.deleteDocument(
				_indexer.getSearchEngineId(), TestPropsValues.getCompanyId(),
				document.getUID(), true);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public void deleteDocuments(Document[] docs) {
		try {
			Stream<Document> stream = Arrays.stream(docs);

			IndexWriterHelperUtil.deleteDocuments(
				_indexer.getSearchEngineId(), TestPropsValues.getCompanyId(),
				stream.map(
					document -> document.getUID()
				).collect(
					Collectors.toList()
				),
				true);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public void reindex(long companyId) throws Exception {
		_indexer.reindex(new String[] {String.valueOf(companyId)});
	}

	public Document[] search(long userId, String keywords, Locale locale) {
		try {
			Hits hits = _indexer.search(
				SearchContextTestUtil.getSearchContext(
					userId, keywords, locale));

			return hits.getDocs();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public Document[] search(String keywords) {
		try {
			return search(TestPropsValues.getUserId(), keywords, null);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public void searchNoOne(long userId, String keywords, Locale locale) {
		searchNoOne(userId, keywords, locale, null);
	}

	public void searchNoOne(
		long userId, String keywords, Locale locale,
		Map<String, Serializable> attributes) {

		try {
			Hits hits = _indexer.search(
				SearchContextTestUtil.getSearchContext(
					userId, null, keywords, locale, attributes));

			HitsAssert.assertNoHits(hits);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public void searchNoOne(String keywords) {
		searchNoOne(keywords, null);
	}

	public void searchNoOne(String keywords, Locale locale) {
		searchNoOne(keywords, locale, null);
	}

	public void searchNoOne(
		String keywords, Locale locale, Map<String, Serializable> attributes) {

		try {
			searchNoOne(
				TestPropsValues.getUserId(), keywords, locale, attributes);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public Document searchOnlyOne(long userId, String keywords, Locale locale) {
		return searchOnlyOne(userId, keywords, locale, null);
	}

	public Document searchOnlyOne(
		long userId, String keywords, Locale locale,
		Map<String, Serializable> attributes) {

		try {
			SearchContext searchContext =
				SearchContextTestUtil.getSearchContext(
					userId, null, keywords, locale, attributes);

			Hits hits = _indexer.search(searchContext);

			return HitsAssert.assertOnlyOne(
				(String)searchContext.getAttribute("queryString"), hits);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public Document searchOnlyOne(String keywords) {
		return searchOnlyOne(keywords, null, null);
	}

	public Document searchOnlyOne(String keywords, Locale locale) {
		return searchOnlyOne(keywords, locale, null);
	}

	public Document searchOnlyOne(
		String keywords, Locale locale, Map<String, Serializable> attributes) {

		try {
			return searchOnlyOne(
				TestPropsValues.getUserId(), keywords, locale, attributes);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public Document searchOnlyOne(
		String keywords, Map<String, Serializable> attributes) {

		return searchOnlyOne(keywords, null, attributes);
	}

	private final Indexer<T> _indexer;

}