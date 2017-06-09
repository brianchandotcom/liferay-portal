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

package com.liferay.portal.search.elasticsearch.internal.mappings;

import com.liferay.portal.search.elasticsearch.internal.ElasticsearchIndexingFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.LiferayIndexCreator;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.mappings.BaseTitleFieldQueryBuilderTestCase;

import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
public class TitleFieldQueryBuilderTest
	extends BaseTitleFieldQueryBuilderTestCase {

	@Override
	@Test
	public void testBasicWordMatches() throws Exception {
		super.testBasicWordMatches();
	}

	@Override
	@Test
	public void testExactMatchBoost() throws Exception {
		super.testExactMatchBoost();
	}

	@Override
	public void testLuceneUnfriendlyTerms() throws Exception {
		super.testLuceneUnfriendlyTerms();
	}

	@Override
	@Test
	public void testMultiwordPhrasePrefixes() throws Exception {
		super.testMultiwordPhrasePrefixes();
	}

	@Override
	@Test
	public void testMultiwordPrefixes() throws Exception {
		super.testMultiwordPrefixes();
	}

	@Override
	@Test
	public void testNull() throws Exception {
		super.testNull();
	}

	@Override
	@Test
	public void testNumbers() throws Exception {
		super.testNumbers();
	}

	@Override
	@Test
	public void testPhrasePrefixes() throws Exception {
		super.testPhrasePrefixes();
	}

	@Override
	@Test
	public void testPhrases() throws Exception {
		super.testPhrases();
	}

	@Override
	@Test
	public void testStopwords() throws Exception {
		super.testStopwords();
	}

	@Override
	@Test
	public void testWhitespace() throws Exception {
		super.testWhitespace();
	}

	@Override
	@Test
	public void testWordPrefixes() throws Exception {
		super.testWordPrefixes();
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		ElasticsearchFixture elasticsearchFixture = new ElasticsearchFixture(
			TitleFieldQueryBuilderTest.class.getSimpleName());

		return new ElasticsearchIndexingFixture(
			elasticsearchFixture, BaseIndexingTestCase.COMPANY_ID,
			new LiferayIndexCreator(elasticsearchFixture));
	}

}