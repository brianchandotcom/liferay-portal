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
import com.liferay.portal.search.test.util.filter.BaseTermsFilterTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
	@Override
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

		_assertTermsCount(1, 10, termsFilter);

		_assertTermsCount(2, 5, termsFilter);

		_assertTermsCount(4, 3, termsFilter);
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

	private void _assertTermsCount(
			int expected, int maxTermsCount, TermsFilter termsFilter)
		throws Exception {

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					QueryUtil.class, "_MAX_TERMS_COUNT", maxTermsCount)) {

			String jsonp = _toJSONP(termsFilter);

			Assert.assertEquals(
				jsonp, expected, StringUtil.count(jsonp, "terms"));
		}
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