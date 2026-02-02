/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.facet;

import com.liferay.portal.search.elasticsearch8.internal.indexing.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.facet.BaseAggregationFilteringTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class AggregationFilteringTest extends BaseAggregationFilteringTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Ignore
	@Override
	@Test
	public void testAggregationUnfiltered() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testBasicFacetSelection() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSelectModified() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSelectSite() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSelectTag() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSelectUser() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testVariousSelectionsCombined() throws Exception {
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

}