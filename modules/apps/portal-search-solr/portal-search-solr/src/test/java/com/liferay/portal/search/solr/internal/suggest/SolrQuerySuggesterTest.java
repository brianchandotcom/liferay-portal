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

package com.liferay.portal.search.solr.internal.suggest;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.solr.connection.TestSolrClientManager;
import com.liferay.portal.search.solr.internal.SolrQuerySuggester;
import com.liferay.portal.search.solr.internal.SolrUnitTestRequirements;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Wade Cao
 */
public class SolrQuerySuggesterTest {

	@Before
	public void setUp() throws Exception {
		Assume.assumeTrue(
			SolrUnitTestRequirements.isSolrExternallyStartedByDeveloper());

		_solrQuerySuggester = _createSolrQuerySuggester();
	}

	@Test
	public void testJapaneseIdeographicSpace() throws Exception {
		String ideographicSpace = "\u3000";

		spellCheckKeywords("あ" + ideographicSpace + "い");
		spellCheckKeywords("あ" + ideographicSpace + ideographicSpace + "い");
		spellCheckKeywords("A" + ideographicSpace + "B");
	}

	@Test
	public void testLuceneUnfriendlyTerms() throws Exception {
		spellCheckKeywords("+alpha AND -bravo");
	}

	@Test
	public void testShortTerms() throws Exception {
		spellCheckKeywords("1 2");
		spellCheckKeywords("A B");
		spellCheckKeywords("A  B");
	}

	@Test
	public void testWhitespace() throws Exception {
		spellCheckKeywords("Liferay Search");
		spellCheckKeywords(" Liferay Search   ");
		spellCheckKeywords("Liferay    Search");
		spellCheckKeywords("L ife  ray    Searc h");
	}

	protected Map<String, List<String>> spellCheckKeywords(String keywords)
		throws Exception {

		return _solrQuerySuggester.spellCheckKeywords(
			_createSearchContext(keywords), 1);
	}

	private NGramQueryBuilderImpl _createNGramQueryBuilder() {
		return new NGramQueryBuilderImpl() {
			{
				setNGramHolderBuilder(new NGramHolderBuilderImpl());
			}
		};
	}

	private SearchContext _createSearchContext(String keywords) {
		return new SearchContext() {
			{
				setKeywords(keywords);
			}
		};
	}

	private SolrQuerySuggester _createSolrQuerySuggester() throws Exception {
		return new SolrQuerySuggester() {
			{
				setNGramQueryBuilder(_createNGramQueryBuilder());
				setSolrClientManager(
					new TestSolrClientManager(Collections.emptyMap()));
			}
		};
	}

	private SolrQuerySuggester _solrQuerySuggester;

}