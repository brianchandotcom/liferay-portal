/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;
import com.liferay.translation.test.util.TranslationTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class JournalArticleLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "test title", "test content");

		_translationEntryLocalService.addOrUpdateTranslationEntry(
			_group.getGroupId(), JournalArticle.class.getName(),
			_journalArticle.getResourcePrimKey(),
			StringUtil.replace(
				TranslationTestUtil.readFileToString(
					"test-journal-article-simple.xlf"),
				"[$JOURNAL_ARTICLE_ID$]",
				String.valueOf(_journalArticle.getResourcePrimKey())),
			"application/xliff+xml", LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertNotNull(_fetchTranslationEntry());
	}

	@Test
	public void testDeleteArticle() throws Exception {
		_journalArticleLocalService.deleteArticle(
			_group.getGroupId(), _journalArticle.getArticleId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertNull(_fetchTranslationEntry());
	}

	@Test
	public void testMoveArticleToTrash() throws Exception {
		_journalArticleLocalService.moveArticleToTrash(
			TestPropsValues.getUserId(), _journalArticle);

		Assert.assertNotNull(_fetchTranslationEntry());
	}

	private TranslationEntry _fetchTranslationEntry() {
		return _translationEntryLocalService.fetchTranslationEntry(
			JournalArticle.class.getName(),
			_journalArticle.getResourcePrimKey(),
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN));
	}

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticle _journalArticle;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private TranslationEntryLocalService _translationEntryLocalService;

}