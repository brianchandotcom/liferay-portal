/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.util;

import com.liferay.diff.DiffVersion;
import com.liferay.diff.DiffVersionsInfo;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Gergely Szalay
 */
public class JournalUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpJournalArticleServiceUtil();
	}

	@After
	public void tearDown() {
		_journalArticleServiceUtilMockedStatic.close();
	}

	@Test
	public void testGetDiffVersionsInfo() {
		DiffVersionsInfo diffVersionsInfo = JournalUtil.getDiffVersionsInfo(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(), 1.0,
			1.3);

		List<DiffVersion> diffVersions = diffVersionsInfo.getDiffVersions();

		for (DiffVersion diffVersion : diffVersions) {
			if (diffVersion.getVersion() == 1.1) {
				Assert.assertEquals(1002, diffVersion.getUserId());
			}
			else {
				Assert.assertEquals(1001, diffVersion.getUserId());
			}
		}
	}

	private JournalArticle _getJournalArticle(
		double version, Date modifiedDate, long userId) {

		JournalArticle article = Mockito.mock(JournalArticle.class);

		Mockito.when(
			article.getVersion()
		).thenReturn(
			version
		);

		Mockito.when(
			article.getModifiedDate()
		).thenReturn(
			modifiedDate
		);

		Mockito.when(
			article.getStatusByUserId()
		).thenReturn(
			userId
		);

		return article;
	}

	private List<JournalArticle> _getJournalArticles() {
		return Arrays.asList(
			_getJournalArticle(1.0, new Date(), 1001),
			_getJournalArticle(1.1, new Date(), 1002),
			_getJournalArticle(1.2, new Date(), 1001),
			_getJournalArticle(1.3, new Date(), 1001));
	}

	private void _setUpJournalArticleServiceUtil() {
		_journalArticleServiceUtilMockedStatic = Mockito.mockStatic(
			JournalArticleServiceUtil.class);
		Mockito.when(
			JournalArticleServiceUtil.getArticlesByArticleId(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.any(OrderByComparator.class))
		).thenAnswer(
			invocation -> _getJournalArticles()
		);
	}

	private static MockedStatic<JournalArticleServiceUtil>
		_journalArticleServiceUtilMockedStatic;

}