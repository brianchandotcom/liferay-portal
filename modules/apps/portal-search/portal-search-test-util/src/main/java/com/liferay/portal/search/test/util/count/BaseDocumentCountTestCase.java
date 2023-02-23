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

package com.liferay.portal.search.test.util.count;

import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.test.util.document.BaseDocumentTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public abstract class BaseDocumentCountTestCase extends BaseDocumentTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		addDocuments(
			screenName -> document -> populate(document, screenName),
			SCREEN_NAMES);
	}

	@Test
	public void testAllWordsInAllDocuments() throws Exception {
		assertCount("sixth fifth fourth third second first", 6);
	}

	@Test
	public void testOneWordInAllDocuments() throws Exception {
		assertCount("Smith", 6);
	}

	@Test
	public void testOneWordPerDocument() throws Exception {
		assertCount("first", 1);

		assertCount("second", 1);

		assertCount("third", 1);

		assertCount("fourth", 1);

		assertCount("fifth", 1);

		assertCount("sixth", 1);
	}

	protected void assertCount(String keywords, int expectedCount)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(
						new SearchSearchRequest() {
							{
								setIndexNames(getIndexName());
								setQuery(
									BaseDocumentTestCase.getQuery(keywords));
							}
						});

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals(
					"Total hits", expectedCount, searchHits.getTotalHits());
			});
	}

	protected abstract String getIndexName();

}