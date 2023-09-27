/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.query.string;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.function.Consumer;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public abstract class BaseQueryStringTestCase extends BaseIndexingTestCase {

	@Test
	public void testPresentAfterSearch() throws Exception {
		doTestPresentAfter(IndexingTestHelper::search);
	}

	@Test
	public void testPresentAfterSearchCount() throws Exception {
		doTestPresentAfter(IndexingTestHelper::searchCount);
	}

	@Test
	public void testResponseBlankByDefaultButNeverNull() throws Exception {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> Assert.assertEquals(
						StringPool.BLANK, searchResponse.getResponseString()));
			});
	}

	protected void doTestPresentAfter(Consumer<IndexingTestHelper> consumer) {
		addDocument(
			document -> {
			});

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder ->
						searchRequestBuilder.addSelectedFieldNames(
							"_source"
						).includeResponseString(
							true
						));

				consumer.accept(indexingTestHelper);

				indexingTestHelper.verifyContext(
					searchContext -> Assert.assertThat(
						(String)searchContext.getAttribute("queryString"),
						CoreMatchers.containsString(
							getExpectedPartOfRequestString())));

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						Assert.assertThat(
							searchResponse.getRequestString(),
							CoreMatchers.containsString(
								getExpectedPartOfRequestString()));
						Assert.assertThat(
							searchResponse.getResponseString(),
							CoreMatchers.containsString(
								getExpectedPartOfResponseString()));
					});
			});
	}

	protected String getExpectedPartOfRequestString() {
		return Field.ENTRY_CLASS_NAME;
	}

	protected abstract String getExpectedPartOfResponseString();

}