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
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingGetHiddenResultsBuilderTest
	extends BaseRankingResultsBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingGetHiddenResultsBuilder = new RankingGetHiddenResultsBuilder(
			dlAppLocalService, fastDateFormatFactory, queries, rankingIndexName,
			rankingIndexReader, resourceActions, resourceRequest,
			resourceResponse, searchEngineAdapter);

		_rankingGetHiddenResultsBuilder.from(
			0
		).size(
			2
		);
	}

	@Test
	public void testBuild() throws Exception {
		setUpDLAppLocalService();
		setUpFastDateFormatFactory();
		setUpRankingResultUtil();
		setUpResourceRequest();

		Ranking ranking = Mockito.mock(Ranking.class);

		Mockito.doReturn(
			Arrays.asList("1", "2")
		).when(
			ranking
		).getHiddenDocumentIds();

		setUpRankingIndexReader(Optional.of(ranking));

		setUpSearchEngineAdapter(
			setUpGetDocumentResponseGetDocument(
				setUpDocumentWithGetString(), setUpGetDocumentResponse()));

		Assert.assertEquals(
			mapper.readTree(_getExpectedDocumentsString()),
			mapper.readTree(
				_rankingGetHiddenResultsBuilder.build(
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
			_rankingGetHiddenResultsBuilder.build(
			).toString());
	}

	private String _getExpectedDocumentsString() {
		return JSONUtil.put(
			"documents",
			JSONUtil.putAll(
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
					"hidden", true
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
				),
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
					"hidden", true
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
			"total", 2
		).toString();
	}

	private RankingGetHiddenResultsBuilder _rankingGetHiddenResultsBuilder;

}