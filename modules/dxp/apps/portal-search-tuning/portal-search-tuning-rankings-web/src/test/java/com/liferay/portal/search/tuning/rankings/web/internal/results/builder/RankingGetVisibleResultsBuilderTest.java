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

package com.liferay.portal.search.tuning.rankings.web.internal.results.builder;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.searcher.helper.RankingSearchRequestHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingGetVisibleResultsBuilderTest
	extends BaseRankingResultsBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpRankingSearchRequestHelper();

		_rankingGetVisibleResultsBuilder = new RankingGetVisibleResultsBuilder(
			complexQueryPartBuilderFactory, dlAppLocalService,
			fastDateFormatFactory, rankingIndexName, rankingIndexReader,
			_rankingSearchRequestHelper, resourceActions, resourceRequest,
			resourceResponse, queries, searcher, searchRequestBuilderFactory);
	}

	@Test
	public void testBuild() throws Exception {
		setUpComplexQueryPartBuilderFactory(setUpComplexQueryPartBuilder());
		setUpDLAppLocalService();
		setUpFastDateFormatFactory();
		setUpQuery();
		setUpPropsUtil();

		Ranking ranking = Mockito.mock(Ranking.class);

		Mockito.doReturn(
			"defaultQueryString"
		).when(
			ranking
		).getQueryString();

		setUpRankingIndexReader(Optional.of(ranking));

		setUpRankingResultUtil();
		setUpResourceRequest();
		setUpSearchRequestBuilderFactory(setUpSearchRequestBuilder());
		setUpSearcher(setUpSearchResponse(setUpDocumentWithGetString()));

		Assert.assertEquals(
			mapper.readTree(_getExpectedDocumentsString()),
			mapper.readTree(
				_rankingGetVisibleResultsBuilder.build(
				).toJSONString()));
	}

	@Test
	public void testBuildWithOptionalRankingNotPresent() {
		setUpRankingIndexReader(Optional.empty());

		Assert.assertEquals(
			JSONUtil.put(
				"documents", JSONFactoryUtil.createJSONArray()
			).put(
				"total", 0
			).toString(),
			_rankingGetVisibleResultsBuilder.build(
			).toString());
	}

	private String _getExpectedDocumentsString() {
		return JSONUtil.put(
			"documents",
			JSONUtil.put(
				JSONUtil.put(
					"author", "theAuthor"
				).put(
					"clicks", "theClicks"
				).put(
					"date", "20021209000109"
				).put(
					"deleted", false
				).put(
					"description", "undefined"
				).put(
					"hidden", false
				).put(
					"icon", "document-image"
				).put(
					"id", "theUID"
				).put(
					"pinned", false
				).put(
					"title", "theTitle"
				).put(
					"viewURL", ""
				))
		).put(
			"total", 1
		).toJSONString();
	}

	private void _setUpRankingSearchRequestHelper() {
		Mockito.doNothing(
		).when(
			_rankingSearchRequestHelper
		).contribute(
			Mockito.anyObject(), Mockito.anyObject()
		);
	}

	private RankingGetVisibleResultsBuilder _rankingGetVisibleResultsBuilder;

	@Mock
	private RankingSearchRequestHelper _rankingSearchRequestHelper;

}