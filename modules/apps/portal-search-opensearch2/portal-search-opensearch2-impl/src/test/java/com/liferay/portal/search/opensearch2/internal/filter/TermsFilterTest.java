/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.filter;

import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.opensearch2.internal.legacy.query.OpenSearchQueryTranslator;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.opensearch2.internal.util.QueryUtil;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.filter.BaseTermsFilterTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class TermsFilterTest extends BaseTermsFilterTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		OpenSearchFilterTranslatorFixture openSearchFilterTranslatorFixture =
			new OpenSearchFilterTranslatorFixture(
				new OpenSearchQueryTranslator());

		_openSearchFilterTranslator =
			openSearchFilterTranslatorFixture.getOpenSearchFilterTranslator();
	}

	@Test
	public void testTranslateTermsFilterExceedingMaxAllowedTerms()
		throws Exception {

		TermsFilter termsFilter = new TermsFilter("groupId");

		termsFilter.addValues("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

		_setMaxTermsCount(10);

		_assertTermsCount(1, termsFilter);

		_setMaxTermsCount(5);

		_assertTermsCount(2, termsFilter);

		_setMaxTermsCount(3);

		_assertTermsCount(4, termsFilter);
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

	private void _assertTermsCount(int expected, TermsFilter termsFilter)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				String jsonp = _toJSONP(termsFilter);

				Assert.assertEquals(
					jsonp, expected, StringUtil.count(jsonp, "terms"));
			});
	}

	private void _setMaxTermsCount(int maxTermsCount) {
		ReflectionTestUtil.setFieldValue(
			QueryUtil.class, "_MAX_TERMS_COUNT", maxTermsCount);
	}

	private String _toJSONP(TermsFilter termsFilter) {
		org.opensearch.client.opensearch._types.query_dsl.Query
			openSearchQuery =
				new org.opensearch.client.opensearch._types.query_dsl.Query(
					_openSearchFilterTranslator.visit(termsFilter));

		return JsonpUtil.toString(openSearchQuery);
	}

	private OpenSearchFilterTranslator _openSearchFilterTranslator;

}