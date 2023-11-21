/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.translation.service.TranslationEntryLocalService;
import com.liferay.translation.test.util.TranslationTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class TranslationEntryTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_journalArticle = JournalTestUtil.addArticle(
			TestPropsValues.getUserId(), group.getGroupId(), 0);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		_translationEntryLocalService.addOrUpdateTranslationEntry(
			group.getGroupId(), JournalArticle.class.getName(),
			_journalArticle.getResourcePrimKey(),
			StringUtil.replace(
				TranslationTestUtil.readFileToString(
					"test-journal-article-simple.xlf"),
				"[$JOURNAL_ARTICLE_ID$]",
				String.valueOf(_journalArticle.getResourcePrimKey())),
			"application/xliff+xml", LocaleUtil.toLanguageId(LocaleUtil.BRAZIL),
			ServiceContextTestUtil.getServiceContext());

		return _journalArticleLocalService.fetchLatestArticle(
			_journalArticle.getResourcePrimKey());
	}

	private JournalArticle _journalArticle;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private TranslationEntryLocalService _translationEntryLocalService;

}