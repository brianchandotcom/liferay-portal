/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.translation.constants.TranslationActionKeys;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.importer.TranslationInfoItemFieldValuesImporter;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;
import com.liferay.translation.service.TranslationEntryService;
import com.liferay.translation.test.util.TranslationTestUtil;

import java.io.ByteArrayInputStream;

import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia Garcia
 */
@RunWith(Arquillian.class)
public class TranslationEntryServiceTest {

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

	@Test(expected = IllegalArgumentException.class)
	public void testAddOrUpdateTranslationEntryFailsIfBCP47LanguageId()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		TranslationTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role,
					TranslationConstants.RESOURCE_NAME + "." +
						LocaleUtil.toLanguageId(LocaleUtil.SIMPLIFIED_CHINESE),
					ResourceConstants.SCOPE_GROUP,
					String.valueOf(_group.getGroupId()),
					TranslationActionKeys.TRANSLATE);

				InfoItemReference infoItemReference = new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());

				InfoItemFieldValuesProvider<JournalArticle>
					infoItemFieldValuesProvider =
						(InfoItemFieldValuesProvider<JournalArticle>)
							_infoItemServiceRegistry.getFirstInfoItemService(
								InfoItemFieldValuesProvider.class,
								JournalArticle.class.getName());

				_translationEntry =
					_translationEntryService.addOrUpdateTranslationEntry(
						_group.getGroupId(),
						LocaleUtil.toBCP47LanguageId(
							LocaleUtil.SIMPLIFIED_CHINESE),
						infoItemReference,
						infoItemFieldValuesProvider.getInfoItemFieldValues(
							journalArticle),
						ServiceContextTestUtil.getServiceContext());
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddOrUpdateTranslationEntryFailsWithoutTranslationPermission()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		TranslationTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role,
					TranslationConstants.RESOURCE_NAME + "." +
						LocaleUtil.toLanguageId(LocaleUtil.US),
					ResourceConstants.SCOPE_GROUP,
					String.valueOf(_group.getGroupId()),
					TranslationActionKeys.TRANSLATE);

				InfoItemReference infoItemReference = new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());

				InfoItemFieldValuesProvider<JournalArticle>
					infoItemFieldValuesProvider =
						(InfoItemFieldValuesProvider<JournalArticle>)
							_infoItemServiceRegistry.getFirstInfoItemService(
								InfoItemFieldValuesProvider.class,
								JournalArticle.class.getName());

				_translationEntry =
					_translationEntryService.addOrUpdateTranslationEntry(
						_group.getGroupId(),
						LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
						infoItemReference,
						infoItemFieldValuesProvider.getInfoItemFieldValues(
							journalArticle),
						ServiceContextTestUtil.getServiceContext());
			});
	}

	@Test
	public void testAddOrUpdateTranslationEntryPendingJournalArticleApprovedTranslation()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_addDraftTranslation(journalArticle, LocaleUtil.SPAIN);

		Map<Locale, String> titleMap = journalArticle.getTitleMap();

		titleMap.put(LocaleUtil.US, "newTitle");

		JournalTestUtil.updateArticle(
			journalArticle, titleMap, journalArticle.getContent(), true, false,
			ServiceContextTestUtil.getServiceContext());

		JournalArticle latestJournalArticle =
			_journalArticleService.getLatestArticle(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.US),
			latestJournalArticle.getTitle(LocaleUtil.US));

		_translationEntryLocalService.updateStatus(
			TestPropsValues.getUserId(),
			_translationEntry.getTranslationEntryId(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(), new HashMap<>());

		latestJournalArticle = _journalArticleService.getLatestArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			"Este es el titulo",
			latestJournalArticle.getTitle(LocaleUtil.SPAIN));
	}

	@Test
	public void testAddOrUpdateTranslationEntryPendingJournalArticleRejectedTranslation()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_addDraftTranslation(journalArticle, LocaleUtil.SPAIN);

		Map<Locale, String> titleMap = journalArticle.getTitleMap();

		titleMap.put(LocaleUtil.US, "newTitle");

		JournalTestUtil.updateArticle(
			journalArticle, titleMap, journalArticle.getContent(), true, false,
			ServiceContextTestUtil.getServiceContext());

		JournalArticle latestJournalArticle =
			_journalArticleService.getLatestArticle(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.SPAIN),
			latestJournalArticle.getTitle(LocaleUtil.SPAIN));

		_translationEntryLocalService.updateStatus(
			TestPropsValues.getUserId(),
			_translationEntry.getTranslationEntryId(),
			WorkflowConstants.STATUS_DENIED,
			ServiceContextTestUtil.getServiceContext(), new HashMap<>());

		latestJournalArticle = _journalArticleService.getLatestArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.SPAIN),
			latestJournalArticle.getTitle(LocaleUtil.SPAIN));
	}

	@Test
	public void testAddOrUpdateTranslationEntrySaveDraft() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		TranslationTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role,
					TranslationConstants.RESOURCE_NAME + "." +
						LocaleUtil.toLanguageId(LocaleUtil.US),
					ResourceConstants.SCOPE_GROUP,
					String.valueOf(_group.getGroupId()),
					TranslationActionKeys.TRANSLATE);

				InfoItemReference infoItemReference = new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());

				InfoItemFieldValuesProvider<JournalArticle>
					infoItemFieldValuesProvider =
						(InfoItemFieldValuesProvider<JournalArticle>)
							_infoItemServiceRegistry.getFirstInfoItemService(
								InfoItemFieldValuesProvider.class,
								JournalArticle.class.getName());

				InfoItemFieldValues infoItemFieldValues =
					infoItemFieldValuesProvider.getInfoItemFieldValues(
						journalArticle);

				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext();

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);

				_translationEntry =
					_translationEntryService.addOrUpdateTranslationEntry(
						_group.getGroupId(),
						LocaleUtil.toLanguageId(LocaleUtil.US),
						infoItemReference, infoItemFieldValues, serviceContext);

				Assert.assertNotNull(_translationEntry);
			});
	}

	@Test
	public void testAddOrUpdateTranslationEntryUpdateJournalArticle()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		TranslationTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role,
					TranslationConstants.RESOURCE_NAME + "." +
						LocaleUtil.toLanguageId(LocaleUtil.US),
					ResourceConstants.SCOPE_GROUP,
					String.valueOf(_group.getGroupId()),
					TranslationActionKeys.TRANSLATE);

				RoleTestUtil.addResourcePermission(
					role, JournalArticle.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.UPDATE);

				InfoItemReference infoItemReference = new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());

				InfoItemFieldValuesProvider<JournalArticle>
					infoItemFieldValuesProvider =
						(InfoItemFieldValuesProvider<JournalArticle>)
							_infoItemServiceRegistry.getFirstInfoItemService(
								InfoItemFieldValuesProvider.class,
								JournalArticle.class.getName());

				InfoItemFieldValues infoItemFieldValues =
					infoItemFieldValuesProvider.getInfoItemFieldValues(
						journalArticle);

				StringUtil.replace(
					TranslationTestUtil.readFileToString(
						"test-journal-article.xlf"),
					"[$JOURNAL_ARTICLE_ID$]",
					String.valueOf(journalArticle.getResourcePrimKey()));

				_translationEntry =
					_translationEntryLocalService.addOrUpdateTranslationEntry(
						_group.getGroupId(),
						LocaleUtil.toLanguageId(LocaleUtil.US),
						infoItemReference, infoItemFieldValues,
						ServiceContextTestUtil.getServiceContext());

				JournalArticle latestJournalArticle =
					_journalArticleService.getLatestArticle(
						journalArticle.getGroupId(),
						journalArticle.getArticleId(),
						WorkflowConstants.STATUS_ANY);

				Assert.assertTrue(
					journalArticle.getVersion() <
						latestJournalArticle.getVersion());
			});
	}

	@Test
	public void testAddOrUpdateTranslationEntryUpdateJournalArticleApprovedTranslation()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_addDraftTranslation(journalArticle, LocaleUtil.SPAIN);

		JournalTestUtil.updateArticle(journalArticle, "newTitle");

		JournalArticle latestJournalArticle =
			_journalArticleService.getLatestArticle(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			"newTitle", latestJournalArticle.getTitle(LocaleUtil.SPAIN));

		_translationEntryLocalService.updateStatus(
			TestPropsValues.getUserId(),
			_translationEntry.getTranslationEntryId(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(), new HashMap<>());

		latestJournalArticle = _journalArticleService.getLatestArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			"Este es el titulo",
			latestJournalArticle.getTitle(LocaleUtil.SPAIN));
	}

	@Test
	public void testAddOrUpdateTranslationEntryUpdateJournalArticleRejectedTranslation()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_addDraftTranslation(journalArticle, LocaleUtil.SPAIN);

		JournalTestUtil.updateArticle(journalArticle, "newTitle");

		JournalArticle latestJournalArticle =
			_journalArticleService.getLatestArticle(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			"newTitle", latestJournalArticle.getTitle(LocaleUtil.SPAIN));

		_translationEntryLocalService.updateStatus(
			TestPropsValues.getUserId(),
			_translationEntry.getTranslationEntryId(),
			WorkflowConstants.STATUS_DENIED,
			ServiceContextTestUtil.getServiceContext(), new HashMap<>());

		latestJournalArticle = _journalArticleService.getLatestArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			"newTitle", latestJournalArticle.getTitle(LocaleUtil.SPAIN));
	}

	private void _addDraftTranslation(
			JournalArticle journalArticle, Locale locale)
		throws Exception {

		TranslationTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role,
					TranslationConstants.RESOURCE_NAME + "." +
						LocaleUtil.toLanguageId(locale),
					ResourceConstants.SCOPE_GROUP,
					String.valueOf(_group.getGroupId()),
					TranslationActionKeys.TRANSLATE);

				InfoItemReference infoItemReference = new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());

				String stringFile = StringUtil.replace(
					TranslationTestUtil.readFileToString(
						"test-journal-article-only-title.xlf"),
					"[$JOURNAL_ARTICLE_ID$]",
					String.valueOf(journalArticle.getResourcePrimKey()));

				InfoItemFieldValues infoItemFieldValues =
					_xliffTranslationInfoItemFieldValuesImporter.
						importInfoItemFieldValues(
							_group.getGroupId(),
							new InfoItemReference(
								JournalArticle.class.getName(),
								journalArticle.getResourcePrimKey()),
							new ByteArrayInputStream(
								stringFile.getBytes(StandardCharsets.UTF_8)));

				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext();

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);

				_translationEntry =
					_translationEntryService.addOrUpdateTranslationEntry(
						_group.getGroupId(), LocaleUtil.toLanguageId(locale),
						infoItemReference, infoItemFieldValues, serviceContext);
			});
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private JournalArticleService _journalArticleService;

	@Inject
	private Language _language;

	@DeleteAfterTestRun
	private TranslationEntry _translationEntry;

	@Inject
	private TranslationEntryLocalService _translationEntryLocalService;

	@Inject
	private TranslationEntryService _translationEntryService;

	@Inject(filter = "content.type=application/xliff+xml")
	private TranslationInfoItemFieldValuesImporter
		_xliffTranslationInfoItemFieldValuesImporter;

}