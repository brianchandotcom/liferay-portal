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

package com.liferay.portal.search.elasticsearch6.internal.mappings;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.elasticsearch6.internal.ElasticsearchIndexingFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.internal.analysis.DescriptionFieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.query.FuzzyQuery;
import com.liferay.portal.search.query.MatchPhrasePrefixQuery;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.mappings.BaseFieldQueryBuilderTestCase;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class MaxExpansionsTest extends BaseFieldQueryBuilderTestCase {

	@Test
	public void testFuzzy65_40() throws Exception {
		_addDocuments("eclipse", 65);
		_assertFuzzyQuery("eclipsee", Integer.valueOf(40));
	}

	@Test
	public void testFuzzy65_50() throws Exception {
		_addDocuments("eclipse", 65);
		_assertFuzzyQuery("eclipsee", 50);
	}

	@Test
	public void testMatchPhrasePrefix_65_50() throws Exception {
		_addDocuments("Prefix_", 65);
		_assertMatchPhrasePrefixQuery("Prefi", 50);
	}

	@Test
	public void testMatchPhrasePrefix65_30() throws Exception {
		_addDocuments("Prefix", 65);
		_assertMatchPhrasePrefixQuery("Prefix", Integer.valueOf(30));
	}

	@Test
	public void testMatchPhrasePrefix65_50() throws Exception {
		_addDocuments("Prefix", 65);
		_assertMatchPhrasePrefixQuery("Prefi", 50);
	}

	@Test
	public void testMatchPhrasePrefixSpace65_11() throws Exception {
		_addDocuments("Prefix ", 65);
		_assertMatchPhrasePrefixQuery("Prefix 1", Integer.valueOf(9));
	}

	@Test
	public void testMatchPhrasePrefixSpace65_50() throws Exception {
		_addDocuments("Prefix phase", 65);
		_assertMatchPhrasePrefixQuery("Prefix p", 49);
	}

	@Override
	protected FieldQueryBuilder createFieldQueryBuilder() {
		return new DescriptionFieldQueryBuilder() {
			{
				keywordTokenizer = new SimpleKeywordTokenizer();
			}
		};
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new ElasticsearchIndexingFixture() {
			{
				setElasticsearchFixture(new ElasticsearchFixture(getClass()));
				setLiferayMappingsAddedToIndex(true);
			}
		};
	}

	@Override
	protected String getField() {
		return Field.TITLE;
	}

	private void _addDocuments(String prefix, int numDocs) throws Exception {
		for (int i = 1; i <= numDocs; i++) {
			addDocument(prefix + i);
		}
	}

	private void _assertFuzzyQuery(String fuzzyValue, int numDocs)
		throws Exception {

		FuzzyQuery fuzzyQuery = queries.fuzzy(getField(), fuzzyValue);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.query(
						fuzzyQuery));

				long ret = indexingTestHelper.searchCount();

				Assert.assertEquals(numDocs, ret);
			});
	}

	private void _assertFuzzyQuery(String fuzzyValue, Integer maxExpansions)
		throws Exception {

		FuzzyQuery fuzzyQuery = queries.fuzzy(getField(), fuzzyValue);

		fuzzyQuery.setMaxExpansions(maxExpansions);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.query(
						fuzzyQuery));

				long ret = indexingTestHelper.searchCount();

				Assert.assertEquals(maxExpansions.intValue(), ret);
			});
	}

	private void _assertMatchPhrasePrefixQuery(String prefix, int numDocs)
		throws Exception {

		MatchPhrasePrefixQuery matchPhrasePrefixQuery =
			queries.matchPhrasePrefix(getField(), prefix);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.query(
						matchPhrasePrefixQuery));

				long ret = indexingTestHelper.searchCount();

				Assert.assertEquals(numDocs, ret);
			});
	}

	private void _assertMatchPhrasePrefixQuery(
			String prefix, Integer maxExpansions)
		throws Exception {

		MatchPhrasePrefixQuery matchPhrasePrefixQuery =
			queries.matchPhrasePrefix(getField(), prefix);

		matchPhrasePrefixQuery.setMaxExpansions(maxExpansions);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.query(
						matchPhrasePrefixQuery));

				long ret = indexingTestHelper.searchCount();

				Assert.assertEquals(maxExpansions.intValue(), ret);
			});
	}

}