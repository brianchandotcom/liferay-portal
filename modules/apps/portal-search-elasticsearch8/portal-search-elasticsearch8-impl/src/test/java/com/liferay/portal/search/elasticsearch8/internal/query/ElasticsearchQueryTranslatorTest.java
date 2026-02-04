/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.query;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;

import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch8.internal.filter.ElasticsearchFilterVisitor;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.QueryUtil;
import com.liferay.portal.search.internal.query.BooleanQueryImpl;
import com.liferay.portal.search.internal.query.CommonTermsQueryImpl;
import com.liferay.portal.search.internal.query.FuzzyQueryImpl;
import com.liferay.portal.search.internal.query.MatchAllQueryImpl;
import com.liferay.portal.search.internal.query.MoreLikeThisQueryImpl;
import com.liferay.portal.search.internal.query.TermQueryImpl;
import com.liferay.portal.search.internal.query.TermsQueryImpl;
import com.liferay.portal.search.internal.query.WildcardQueryImpl;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class ElasticsearchQueryTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_elasticsearchQueryTranslator = new ElasticsearchQueryTranslator();
	}

	@Test
	public void testTranslateBoostCommonTermsQuery() {
		_assertBoost(new CommonTermsQueryImpl("test", "test"));
	}

	@Test
	public void testTranslateBoostFuzzyQuery() {
		_assertBoost(new FuzzyQueryImpl("test", "test"));
	}

	@Test
	public void testTranslateBoostMatchAllQuery() {
		_assertBoost(new MatchAllQueryImpl());
	}

	@Test
	public void testTranslateBoostMoreLikeThisQueryStringQuery() {
		_assertBoost(
			new MoreLikeThisQueryImpl(Collections.emptyList(), "test"));
	}

	@Test
	public void testTranslateBoostTermQuery() {
		_assertBoost(new TermQueryImpl("test", "test"));
	}

	@Test
	public void testTranslateBoostWildcardQuery() {
		_assertBoost(new WildcardQueryImpl("test", "test"));
	}

	@Test
	public void testTranslateInnerBoostBooleanQuery() {
		BooleanQuery booleanQuery = new BooleanQueryImpl();

		Query query = new MatchAllQueryImpl();

		query.setBoost(_BOOST);

		booleanQuery.addMustQueryClauses(query);

		co.elastic.clients.elasticsearch._types.query_dsl.Query
			elasticsearchQuery =
				new co.elastic.clients.elasticsearch._types.query_dsl.Query(
					_elasticsearchQueryTranslator.translate(booleanQuery));

		BoolQuery boolQuery = elasticsearchQuery.bool();

		List<co.elastic.clients.elasticsearch._types.query_dsl.Query>
			mustQueries = boolQuery.must();

		co.elastic.clients.elasticsearch._types.query_dsl.Query
			innerElasticsearchQuery = mustQueries.get(0);

		String jsonp = JsonpUtil.toString(innerElasticsearchQuery);

		Assert.assertTrue(
			jsonp, jsonp.contains("\"boost\":" + String.valueOf(_BOOST)));
	}

	@Ignore
	@Test
	public void testTranslateTermsFilterExceedingMaxAllowedTerms() {
		TermsFilter termsFilter = new TermsFilter("groupId");

		termsFilter.addValues("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

		Integer maxTermsCount = QueryUtil.maxTermsCount;

		QueryUtil.maxTermsCount = 10;

		_assertTermsCount(1, termsFilter);

		QueryUtil.maxTermsCount = 5;

		_assertTermsCount(2, termsFilter);

		QueryUtil.maxTermsCount = 3;

		_assertTermsCount(4, termsFilter);

		QueryUtil.maxTermsCount = maxTermsCount;
	}

	@Ignore
	@Test
	public void testTranslateTermsQueryExceedingMaxAllowedTerms() {
		TermsQuery termsQuery = new TermsQueryImpl("groupId");

		termsQuery.addValues("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

		Integer maxTermsCount = QueryUtil.maxTermsCount;

		QueryUtil.maxTermsCount = 10;

		_assertTermsCount(1, termsQuery);

		QueryUtil.maxTermsCount = 5;

		_assertTermsCount(2, termsQuery);

		QueryUtil.maxTermsCount = 3;

		_assertTermsCount(4, termsQuery);

		QueryUtil.maxTermsCount = maxTermsCount;
	}

	private void _assertBoost(Query query) {
		query.setBoost(_BOOST);

		co.elastic.clients.elasticsearch._types.query_dsl.Query
			elasticsearchQuery =
				new co.elastic.clients.elasticsearch._types.query_dsl.Query(
					_elasticsearchQueryTranslator.translate(query));

		String jsonp = JsonpUtil.toString(elasticsearchQuery);

		Assert.assertTrue(
			jsonp, jsonp.contains("\"boost\":" + String.valueOf(_BOOST)));
	}

	private void _assertTermsCount(int expected, TermsFilter termsFilter) {
		String queryString = termsFilter.accept(
			ElasticsearchFilterVisitor.INSTANCE
		).toString();

		Assert.assertEquals(
			queryString, expected, StringUtil.count(queryString, "terms"));
	}

	private void _assertTermsCount(int expected, TermsQuery termsQuery) {
		String queryString = _elasticsearchQueryTranslator.visit(
			termsQuery
		).toString();

		Assert.assertEquals(
			queryString, expected, StringUtil.count(queryString, "terms"));
	}

	private static final Float _BOOST = 1.5F;

	private ElasticsearchQueryTranslator _elasticsearchQueryTranslator;

}