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

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class RankingGetSearchResultsBuilderTest
	extends BaseRankingResultsBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingGetSearchResultsBuilder = new RankingGetSearchResultsBuilder(
			complexQueryPartBuilderFactory, dlAppLocalService,
			fastDateFormatFactory, queries, resourceActions, resourceRequest,
			resourceResponse, searcher, searchRequestBuilderFactory);
	}

	@Test
	public void testBuild() throws Exception {
		setUpComplexQueryPartBuilderFactory(setUpComplexQueryPartBuilder());
		setUpDLAppLocalService();
		setUpFastDateFormatFactory();
		setUpRankingResultUtil();
		setUpQuery();
		setUpResourceRequest();
		setUpSearcher(setUpSearchResponse(setUpDocumentWithGetString()));
		setUpSearchRequestBuilderFactory(setUpSearchRequestBuilder());

		Assert.assertEquals(
			mapper.readTree(_getExpectedDocumentsString()),
			mapper.readTree(
				_rankingGetSearchResultsBuilder.build(
				).toJSONString()));
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
		).toString();
	}

	private RankingGetSearchResultsBuilder _rankingGetSearchResultsBuilder;

}