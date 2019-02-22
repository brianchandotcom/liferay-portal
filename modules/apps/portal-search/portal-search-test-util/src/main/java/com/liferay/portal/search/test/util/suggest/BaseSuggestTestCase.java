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

package com.liferay.portal.search.test.util.suggest;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.kernel.search.suggest.SuggestionConstants;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public abstract class BaseSuggestTestCase extends BaseIndexingTestCase {

	@Test
	public void testMultipleWords() throws Exception {
		indexSuccessfulQuery("indexed this phrase");

		assertSuggest("[indexed this phrase]", "indexef   this   phrasd");
	}

	@Test
	public void testNothingToSuggest() throws Exception {
		assertSuggest("[]", "nothign");
	}

	@Test
	public void testNull() throws Exception {
		assertSuggest("[]", null);
	}

	protected void assertSuggest(String expectedSuggestions, String keywords)
		throws Exception {

		assertSuggest(expectedSuggestions, keywords, 1);
	}

	protected void assertSuggest(
			String expectedSuggestions, String keywords, int max)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				String actualSuggestions = String.valueOf(
					Arrays.asList(suggestKeywordQueries(keywords, max)));

				Assert.assertEquals(expectedSuggestions, actualSuggestions);

				return null;
			});
	}

	protected SearchContext createSearchContext(String keywords) {
		SearchContext searchContext = createSearchContext();

		searchContext.setKeywords(keywords);

		return searchContext;
	}

	protected void indexSuccessfulQuery(String value) throws Exception {
		SpellCheckIndexWriter spellCheckIndexWriter = getIndexWriter();

		spellCheckIndexWriter.indexKeyword(
			createSearchContext(value), 0,
			SuggestionConstants.TYPE_QUERY_SUGGESTION);
	}

	protected String[] suggestKeywordQueries(String keywords, int max)
		throws Exception {

		QuerySuggester querySuggester = getIndexSearcher();

		return querySuggester.suggestKeywordQueries(
			createSearchContext(keywords), max);
	}

}