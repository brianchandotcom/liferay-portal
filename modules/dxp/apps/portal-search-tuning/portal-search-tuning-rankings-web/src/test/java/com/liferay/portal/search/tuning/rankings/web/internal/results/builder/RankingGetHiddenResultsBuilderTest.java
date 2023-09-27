/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.results.builder;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

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
		setUpPortalUtil();
		setUpResourceRequest();

		Ranking ranking = Mockito.mock(Ranking.class);

		Mockito.doReturn(
			Arrays.asList("1", "2")
		).when(
			ranking
		).getHiddenDocumentIds();

		setUpRankingIndexReader(ranking);

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
	public void testBuildWithRankingNotPresent() {
		setUpRankingIndexReader(null);

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