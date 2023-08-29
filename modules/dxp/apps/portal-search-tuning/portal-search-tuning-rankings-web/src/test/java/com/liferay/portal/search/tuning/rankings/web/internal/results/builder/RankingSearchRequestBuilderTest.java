/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.results.builder;

import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class RankingSearchRequestBuilderTest
	extends BaseRankingResultsBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingSearchRequestBuilder = new RankingSearchRequestBuilder(
			complexQueryPartBuilderFactory, groupLocalService, queries,
			searchRequestBuilderFactory);
	}

	@Test
	public void testBuild() throws Exception {
		setUpComplexQueryPartBuilderFactory(setUpComplexQueryPartBuilder());
		setUpQuery();

		SearchRequestBuilder searchRequestBuilder = setUpSearchRequestBuilder();

		setUpSearchRequestBuilderFactory(searchRequestBuilder);

		Assert.assertEquals(
			searchRequestBuilder, _rankingSearchRequestBuilder.build());
	}

	private RankingSearchRequestBuilder _rankingSearchRequestBuilder;

}