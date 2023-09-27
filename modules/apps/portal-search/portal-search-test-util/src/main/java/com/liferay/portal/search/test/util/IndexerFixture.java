/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Lucas Marques de Paula
 */
public class IndexerFixture<T> {

	public IndexerFixture(Class<T> clazz) {
		this(clazz, null);
	}

	public IndexerFixture(
		Class<T> clazz,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_searchRequestBuilderFactory = searchRequestBuilderFactory;

		_indexer = IndexerRegistryUtil.getIndexer(clazz);
	}

	public void deleteDocument(Document document) {
		try {
			IndexWriterHelperUtil.deleteDocument(
				TestPropsValues.getCompanyId(), document.getUID(), true);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public void deleteDocuments(Document[] docs) {
		try {
			IndexWriterHelperUtil.deleteDocuments(
				TestPropsValues.getCompanyId(),
				TransformUtil.transformToList(docs, Document::getUID), true);
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

	public SearchResponse searchOnlyOneSearchResponse(
		String keywords, Locale locale) {

		try {
			SearchContext searchContext =
				SearchContextTestUtil.getSearchContext(
					TestPropsValues.getUserId(), keywords, locale);

			_searchRequestBuilderFactory.builder(
				searchContext
			).fetchSourceIncludes(
				new String[] {"*_sortable"}
			).build();

			Hits hits = _indexer.search(searchContext);

			HitsAssert.assertOnlyOne(
				(String)searchContext.getAttribute("queryString"), hits);

			return (SearchResponse)searchContext.getAttribute(
				"search.response");
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private final Indexer<T> _indexer;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;

}