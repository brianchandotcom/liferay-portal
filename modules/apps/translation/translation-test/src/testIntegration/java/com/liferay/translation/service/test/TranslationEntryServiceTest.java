/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.translation.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.translation.constants.TranslationActionKeys;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryService;
import com.liferay.translation.test.util.TranslationTestUtil;

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
							_infoItemServiceTracker.getFirstInfoItemService(
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

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddOrUpdateTranslationEntryWithoutTranlationPermission()
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
							_infoItemServiceTracker.getFirstInfoItemService(
								InfoItemFieldValuesProvider.class,
								JournalArticle.class.getName());

				InfoItemFieldValues infoItemFieldValues =
					infoItemFieldValuesProvider.getInfoItemFieldValues(
						journalArticle);

				_translationEntry =
					_translationEntryService.addOrUpdateTranslationEntry(
						_group.getGroupId(),
						LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
						infoItemReference, infoItemFieldValues,
						ServiceContextTestUtil.getServiceContext());
			});
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@DeleteAfterTestRun
	private TranslationEntry _translationEntry;

	@Inject
	private TranslationEntryService _translationEntryService;

}