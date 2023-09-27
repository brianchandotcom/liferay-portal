/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.searcher.helper;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.filter.ComplexQueryPartBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingSearchRequestHelperTest extends BaseRankingsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestHelper, "complexQueryPartBuilderFactory",
			complexQueryPartBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestHelper, "queries", queries);
	}

	@Test
	public void testContribute() {
		setUpComplexQueryPartBuilderFactory(
			Mockito.mock(ComplexQueryPartBuilder.class));

		setUpQuery();

		SearchRequestBuilder searchRequestBuilder = Mockito.mock(
			SearchRequestBuilder.class);

		Mockito.doReturn(
			searchRequestBuilder
		).when(
			searchRequestBuilder
		).addComplexQueryPart(
			Mockito.any()
		);

		Ranking ranking = Mockito.mock(Ranking.class);

		Mockito.doReturn(
			Arrays.asList(
				new Ranking.Pin[] {
					new Ranking.Pin(123, "1"), new Ranking.Pin(456, "2")
				})
		).when(
			ranking
		).getPins();

		Mockito.doReturn(
			Arrays.asList("1", "2")
		).when(
			ranking
		).getHiddenDocumentIds();

		_rankingSearchRequestHelper.contribute(searchRequestBuilder, ranking);

		Mockito.verify(
			searchRequestBuilder, Mockito.times(3)
		).addComplexQueryPart(
			Mockito.any()
		);
	}

	private final RankingSearchRequestHelper _rankingSearchRequestHelper =
		new RankingSearchRequestHelper();

}