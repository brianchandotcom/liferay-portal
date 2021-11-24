/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	@Override
	public void setUp() throws Exception {
		super.setUp();

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
			Mockito.anyObject()
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
			Mockito.anyObject()
		);
	}

	private final RankingSearchRequestHelper _rankingSearchRequestHelper =
		new RankingSearchRequestHelper();

}