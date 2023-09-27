/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
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
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class TranslationEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddOrUpdateTranslationEntryDoesNotDeleteTranslationEntryOnPublish()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "test title", "test content");

		_translationEntryLocalService.addOrUpdateTranslationEntry(
			_group.getGroupId(), JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey(),
			StringUtil.replace(
				TranslationTestUtil.readFileToString(
					"test-journal-article-simple.xlf"),
				"[$JOURNAL_ARTICLE_ID$]",
				String.valueOf(journalArticle.getResourcePrimKey())),
			"application/xliff+xml", LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertNotNull(
			_translationEntryLocalService.fetchTranslationEntry(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey(),
				LocaleUtil.toLanguageId(LocaleUtil.SPAIN)));
	}

	@Test
	public void testAddOrUpdateTranslationEntryDoesNotWriteToTheJournalArticleOnDraft()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "test title", "test content");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		_translationEntryLocalService.addOrUpdateTranslationEntry(
			_group.getGroupId(), JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey(),
			StringUtil.replace(
				TranslationTestUtil.readFileToString(
					"test-journal-article-simple.xlf"),
				"[$JOURNAL_ARTICLE_ID$]",
				String.valueOf(journalArticle.getResourcePrimKey())),
			"application/xliff+xml", LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			serviceContext);

		journalArticle = _journalArticleLocalService.fetchLatestArticle(
			journalArticle.getResourcePrimKey());

		Assert.assertEquals(
			"test title", journalArticle.getTitle(LocaleUtil.SPAIN));

		Assert.assertNotNull(
			_translationEntryLocalService.fetchTranslationEntry(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey(),
				LocaleUtil.toLanguageId(LocaleUtil.SPAIN)));
	}

	@Test
	public void testAddOrUpdateTranslationEntryWritesToTheJournalArticleOnPublish()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "test title", "test content");

		_translationEntryLocalService.addOrUpdateTranslationEntry(
			_group.getGroupId(), JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey(),
			StringUtil.replace(
				TranslationTestUtil.readFileToString(
					"test-journal-article-simple.xlf"),
				"[$JOURNAL_ARTICLE_ID$]",
				String.valueOf(journalArticle.getResourcePrimKey())),
			"application/xliff+xml", LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		journalArticle = _journalArticleLocalService.fetchLatestArticle(
			journalArticle.getResourcePrimKey());

		Assert.assertEquals(
			"título de pruebas", journalArticle.getTitle(LocaleUtil.SPAIN));
	}

	@Test
	public void testDeleteTranslationEntryDeletesWorkflowLink()
		throws Exception {

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			_group.getGroupId(), TranslationEntry.class.getName(), 0, 0,
			"Single Approver@1");

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "test title", "test content");

		TranslationEntry translationEntry =
			_translationEntryLocalService.addOrUpdateTranslationEntry(
				_group.getGroupId(), JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey(),
				StringUtil.replace(
					TranslationTestUtil.readFileToString(
						"test-journal-article-simple.xlf"),
					"[$JOURNAL_ARTICLE_ID$]",
					String.valueOf(journalArticle.getResourcePrimKey())),
				"application/xliff+xml",
				LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			TestPropsValues.getCompanyId(), translationEntry.getGroupId(),
			TestPropsValues.getUserId(), TranslationEntry.class.getName(),
			translationEntry.getTranslationEntryId(), translationEntry,
			new ServiceContext());

		translationEntry = _translationEntryLocalService.fetchTranslationEntry(
			JournalArticle.class.getName(), journalArticle.getResourcePrimKey(),
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN));

		Assert.assertNotNull(
			_workflowInstanceLinkLocalService.getWorkflowInstanceLink(
				TestPropsValues.getCompanyId(), translationEntry.getGroupId(),
				TranslationEntry.class.getName(),
				translationEntry.getTranslationEntryId()));

		_translationEntryLocalService.deleteTranslationEntry(
			translationEntry.getTranslationEntryId());

		Assert.assertNull(
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				TestPropsValues.getCompanyId(), translationEntry.getGroupId(),
				TranslationEntry.class.getName(),
				translationEntry.getTranslationEntryId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private TranslationEntryLocalService _translationEntryLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}