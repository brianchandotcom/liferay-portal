/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.internal.indexing.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.elasticsearch7.internal.legacy.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.util.QueryUtil;
import com.liferay.portal.search.test.util.filter.BaseTermsFilterTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class TermsFilterTest extends BaseTermsFilterTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		ElasticsearchFilterTranslatorFixture
			elasticsearchFilterTranslatorFixture =
				new ElasticsearchFilterTranslatorFixture(
					new ElasticsearchQueryTranslatorFixture(
					).getElasticsearchQueryTranslator());

		_elasticsearchFilterTranslator =
			elasticsearchFilterTranslatorFixture.
				getElasticsearchFilterTranslator();
	}

	@Test
	public void testTranslateTermsFilterExceedingMaxAllowedTerms() {
		TermsFilter termsFilter = new TermsFilter("groupId");

		ReflectionTestUtil.setFieldValue(
			_elasticsearchFilterTranslator, "termsFilterTranslator",
			new TermsFilterTranslatorImpl());

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
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	private void _assertTermsCount(int expected, TermsFilter termsFilter) {
		String queryString = _elasticsearchFilterTranslator.visit(
			termsFilter
		).toString();

		Assert.assertEquals(
			queryString, expected, StringUtil.count(queryString, "terms"));
	}

	private void _setMaxTermsCount(int maxTermsCount) {
		ReflectionTestUtil.setFieldValue(
			QueryUtil.class, "_MAX_TERMS_COUNT", maxTermsCount);
	}

	private ElasticsearchFilterTranslator _elasticsearchFilterTranslator;

}