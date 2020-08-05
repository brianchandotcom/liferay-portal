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

package com.liferay.portal.search.elasticsearch6.internal.query;

import com.liferay.portal.search.internal.query.CommonTermsQueryImpl;
import com.liferay.portal.search.internal.query.FuzzyQueryImpl;
import com.liferay.portal.search.internal.query.MatchAllQueryImpl;
import com.liferay.portal.search.internal.query.MoreLikeThisQueryImpl;
import com.liferay.portal.search.internal.query.TermQueryImpl;
import com.liferay.portal.search.internal.query.WildcardQueryImpl;
import com.liferay.portal.search.query.Query;

import java.util.Collections;

import org.elasticsearch.index.query.QueryBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class ElasticsearchQueryTranslatorTest {

	@Before
	public void setUp() throws Exception {
		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture =
				new ElasticsearchQueryTranslatorFixture();

		_elasticsearchQueryTranslator =
			elasticsearchQueryTranslatorFixture.
				getElasticsearchQueryTranslator();
	}

	@Test
	public void testTranslateBoostCommonTermsQuery() {
		assertBoost(new CommonTermsQueryImpl("test", "test"));
	}

	@Test
	public void testTranslateBoostFuzzyQuery() {
		assertBoost(new FuzzyQueryImpl("test", "test"));
	}

	@Test
	public void testTranslateBoostMatchAllQuery() {
		assertBoost(new MatchAllQueryImpl());
	}

	@Test
	public void testTranslateBoostMoreLikeThisQueryStringQuery() {
		assertBoost(new MoreLikeThisQueryImpl(Collections.emptyList(), "test"));
	}

	@Test
	public void testTranslateBoostTermQuery() {
		assertBoost(new TermQueryImpl("test", "test"));
	}

	@Test
	public void testTranslateBoostWildcardQuery() {
		assertBoost(new WildcardQueryImpl("test", "test"));
	}

	protected void assertBoost(Query query) {
		query.setBoost(_BOOST);

		QueryBuilder queryBuilder = _elasticsearchQueryTranslator.translate(
			query);

		Assert.assertEquals(
			queryBuilder.toString(), String.valueOf(_BOOST),
			String.valueOf(queryBuilder.boost()));
	}

	private static final Float _BOOST = 1.5F;

	private ElasticsearchQueryTranslator _elasticsearchQueryTranslator;

}