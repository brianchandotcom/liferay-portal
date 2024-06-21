/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.query;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.internal.query.BooleanQueryImpl;
import com.liferay.portal.search.internal.query.CommonTermsQueryImpl;
import com.liferay.portal.search.internal.query.FuzzyQueryImpl;
import com.liferay.portal.search.internal.query.MatchAllQueryImpl;
import com.liferay.portal.search.internal.query.MoreLikeThisQueryImpl;
import com.liferay.portal.search.internal.query.MultiMatchQueryImpl;
import com.liferay.portal.search.internal.query.TermQueryImpl;
import com.liferay.portal.search.internal.query.TermsQueryImpl;
import com.liferay.portal.search.internal.query.WildcardQueryImpl;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.opensearch2.internal.util.QueryUtil;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;

/**
 * @author Bryan Engler
 */
public class OpenSearchQueryTranslatorTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		OpenSearchQueryTranslatorFixture openSearchQueryTranslatorFixture =
			new OpenSearchQueryTranslatorFixture();

		_openSearchQueryTranslator =
			openSearchQueryTranslatorFixture.getOpenSearchQueryTranslator();
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
	public void testTranslateBoostMultiMatchQuery() {
		_assertBoost(new MultiMatchQueryImpl("test", new HashMap<>()));
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

		org.opensearch.client.opensearch._types.query_dsl.Query
			openSearchQuery =
				new org.opensearch.client.opensearch._types.query_dsl.Query(
					_openSearchQueryTranslator.translate(booleanQuery));

		BoolQuery boolQuery = openSearchQuery.bool();

		List<org.opensearch.client.opensearch._types.query_dsl.Query>
			mustQueries = boolQuery.must();

		org.opensearch.client.opensearch._types.query_dsl.Query
			innerOpenSearchQuery = mustQueries.get(0);

		String jsonp = JsonpUtil.toString(innerOpenSearchQuery);

		Assert.assertTrue(
			jsonp, jsonp.contains("\"boost\":" + String.valueOf(_BOOST)));
	}

	@Test
	public void testTranslateTermsQueryExceedingMaxAllowedTerms()
		throws Exception {

		TermsQuery termsQuery = new TermsQueryImpl("groupId");

		termsQuery.addValues("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

		_setMaxTermsCount(10);

		_assertTermsCount(1, termsQuery);

		_setMaxTermsCount(5);

		_assertTermsCount(2, termsQuery);

		_setMaxTermsCount(3);

		_assertTermsCount(4, termsQuery);
	}

	private void _assertBoost(Query query) {
		query.setBoost(_BOOST);

		String jsonp = _toJSONP(query);

		Assert.assertTrue(
			jsonp, jsonp.contains("\"boost\":" + String.valueOf(_BOOST)));
	}

	private void _assertTermsCount(int expected, TermsQuery termsQuery)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				String jsonp = _toJSONP(termsQuery);

				Assert.assertEquals(
					jsonp, expected, StringUtil.count(jsonp, "terms"));
			});
	}

	private void _setMaxTermsCount(int maxTermsCount) {
		ReflectionTestUtil.setFieldValue(
			QueryUtil.class, "_MAX_TERMS_COUNT", maxTermsCount);
	}

	private String _toJSONP(Query query) {
		org.opensearch.client.opensearch._types.query_dsl.Query
			openSearchQuery =
				new org.opensearch.client.opensearch._types.query_dsl.Query(
					_openSearchQueryTranslator.translate(query));

		return JsonpUtil.toString(openSearchQuery);
	}

	private static final Float _BOOST = 1.5F;

	private OpenSearchQueryTranslator _openSearchQueryTranslator;

}