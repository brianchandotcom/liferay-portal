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

package com.liferay.portal.search.test.util.spellcheck;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.kernel.search.suggest.SuggestionConstants;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Wade Cao
 * @author Bryan Engler
 */
public abstract class BaseSpellCheckTestCase extends BaseIndexingTestCase {

	@Test
	public void testJapaneseIdeographicSpace() throws Exception {
		String ideographicSpace = "\u3000";

		spellCheckKeywords("あ" + ideographicSpace + "い");
		spellCheckKeywords(
			StringBundler.concat("あ", ideographicSpace, ideographicSpace, "い"));
		spellCheckKeywords("A" + ideographicSpace + "B");
	}

	@Test
	public void testLuceneUnfriendlyTerms() throws Exception {
		spellCheckKeywords("+alpha AND -bravo");
	}

	@Test
	public void testMultipleWords() throws Exception {
		indexSpellCheckWord("liferay");
		indexSpellCheckWord("search");

		assertSpellCheck("search liferay", "searc lifera");
	}

	@Test
	public void testMultipleWordsMap() throws Exception {
		indexSpellCheckWord("liferay");
		indexSpellCheckWord("search");

		assertSpellCheckMap(
			"{searc=[search], lifera=[liferay]}", "searc lifera");
	}

	@Test
	public void testQuotedWords() throws Exception {
		indexSpellCheckWord("liferay");
		indexSpellCheckWord("search");

		assertSpellCheck("liferay", "\"lifera\"");
		assertSpellCheck("liferay search", "\"lifera searc\"");
	}

	@Test
	public void testRepeated() throws Exception {
		indexSpellCheckWord("liferay");
		indexSpellCheckWord("search");

		assertSpellCheck(
			"liferay search liferay search", "lifera searc lifera searc");
	}

	@Test
	public void testRepeatedMap() throws Exception {
		indexSpellCheckWord("liferay");
		indexSpellCheckWord("search");

		assertSpellCheckMap(
			"{lifera=[liferay], searc=[search]}", "lifera searc lifera searc");
	}

	@Test
	public void testShortTerms() throws Exception {
		spellCheckKeywords("1 2");
		spellCheckKeywords("A B");
		spellCheckKeywords("A  B");
	}

	@Test
	public void testSpellCheck() throws Exception {
		indexSpellCheckWord("liferay");

		assertSpellCheck("liferay", "lifera");
	}

	@Test
	public void testSpellCheckMap() throws Exception {
		indexSpellCheckWord("liferay");

		assertSpellCheckMap("{lifera=[liferay]}", "lifera");
	}

	@Test
	public void testWhitespace() throws Exception {
		indexSpellCheckWord("liferay");
		indexSpellCheckWord("search");

		assertSpellCheck("liferay search", "Lifera Searc");
		assertSpellCheck("liferay search", " Lifera Searc   ");
		assertSpellCheck("liferay search", "Lifera    Searc");
	}

	@Test
	public void testWhitespaceMap() throws Exception {
		indexSpellCheckWord("liferay");
		indexSpellCheckWord("search");

		assertSpellCheckMap(
			"{l=[], ife=[], ray=[], searc=[search], h=[]}",
			"L ife  ray    Searc h");
	}

	protected void assertSpellCheck(String expectedSpelling, String keywords)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					expectedSpelling,
					spellCheckKeywords(createSearchContext(keywords)));

				return null;
			});
	}

	protected void assertSpellCheckMap(
			String expectedMapString, String keywords)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					expectedMapString,
					String.valueOf(spellCheckKeywords(keywords)));

				return null;
			});
	}

	protected SearchContext createSearchContext(String keywords) {
		SearchContext searchContext = createSearchContext();

		searchContext.setKeywords(keywords);

		return searchContext;
	}

	protected void indexSpellCheckWord(String value) throws Exception {
		indexSpellCheckWord(value, 0);
	}

	protected void indexSpellCheckWord(String value, float weight)
		throws SearchException {

		SpellCheckIndexWriter spellCheckIndexWriter = getIndexWriter();

		spellCheckIndexWriter.indexKeyword(
			createSearchContext(value), weight,
			SuggestionConstants.TYPE_SPELL_CHECKER);
	}

	protected String spellCheckKeywords(SearchContext searchContext)
		throws Exception {

		QuerySuggester querySuggester = getIndexSearcher();

		return querySuggester.spellCheckKeywords(searchContext);
	}

	protected Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws Exception {

		QuerySuggester querySuggester = getIndexSearcher();

		return querySuggester.spellCheckKeywords(searchContext, max);
	}

	protected Map<String, List<String>> spellCheckKeywords(String keywords)
		throws Exception {

		return spellCheckKeywords(createSearchContext(keywords), 2);
	}

}