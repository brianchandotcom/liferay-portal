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