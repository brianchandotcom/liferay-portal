/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class RankingJSONBuilderTest extends BaseRankingResultsBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		setUpResourceRequest();

		_rankingJSONBuilder = new RankingJSONBuilder(
			dlAppLocalService, fastDateFormatFactory, resourceActions,
			resourceRequest);

		_rankingJSONBuilder.document(setUpDocumentWithGetString());
	}

	@Test
	public void testBuild() throws Exception {
		setUpDLAppLocalService();
		setUpFastDateFormatFactory();

		Assert.assertEquals(
			mapper.readTree(_getExpectedDocumentsString()),
			mapper.readTree(
				_rankingJSONBuilder.build(
				).toJSONString()));
	}

	private String _getExpectedDocumentsString() {
		return JSONUtil.put(
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
		).toString();
	}

	private RankingJSONBuilder _rankingJSONBuilder;

}