/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.mappings;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.mappings.BaseMaxExpansionsTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class MaxExpansionsTest extends BaseMaxExpansionsTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@After
	@Override
	public void tearDown() {
		try {
			super.tearDown();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	@Override
	@Test
	public void testPrefixWithNumberSpaceNumberSuffix() throws Exception {
		addDocuments("AlphaPrefix# #");

		assertSearch("AlphaPrefi", MAX_EXPANSIONS);
		assertSearchCount("AlphaPrefi", MAX_EXPANSIONS);
	}

	@Override
	@Test
	public void testPrefixWithNumberSuffix() throws Exception {
		addDocuments("BetaPrefix#");

		assertSearch("BetaPrefi", MAX_EXPANSIONS);
		assertSearchCount("BetaPrefi", MAX_EXPANSIONS);
	}

	@Override
	@Test
	public void testPrefixWithUnderscoreNumberSuffix() throws Exception {
		addDocuments("GammaPrefix_#");

		assertSearch("GammaPrefi", MAX_EXPANSIONS);
		assertSearchCount("GammaPrefi", MAX_EXPANSIONS);
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MaxExpansionsTest.class);

}