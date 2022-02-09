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

package com.liferay.portal.search.elasticsearch7.internal.mappings;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.ElasticsearchIndexingFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.mappings.BaseMaxExpansionsTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class MaxExpansionsTest extends BaseMaxExpansionsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
		return new ElasticsearchIndexingFixture() {
			{
				ElasticsearchFixture elasticsearchFixture =
					new ElasticsearchFixture(getClass());

				Map<String, Object> elasticsearchConfigurationProperties =
					elasticsearchFixture.
						getElasticsearchConfigurationProperties();

				elasticsearchConfigurationProperties.put(
					"sidecarJVMOptions", "-Xms512m|-Xmx512m");

				setElasticsearchFixture(elasticsearchFixture);

				setLiferayMappingsAddedToIndex(true);
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MaxExpansionsTest.class);

}