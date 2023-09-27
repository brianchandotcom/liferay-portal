/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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