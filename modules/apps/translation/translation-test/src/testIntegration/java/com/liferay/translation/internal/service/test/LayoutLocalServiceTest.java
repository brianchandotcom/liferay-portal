/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;
import com.liferay.translation.test.util.TranslationTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class LayoutLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testDeleteLayout() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_addTranslationEntry(
			Layout.class.getName(), layout.getPlid(), serviceContext);

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				null, TestPropsValues.getUserId(), _group.getGroupId(), null,
				null, layout.getPlid(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				true, new UnicodeProperties(true), serviceContext);

		_addTranslationEntry(
			SegmentsExperience.class.getName(),
			segmentsExperience.getSegmentsExperienceId(), serviceContext);

		Assert.assertNotNull(
			_fetchTranslationEntry(Layout.class.getName(), layout.getPlid()));
		Assert.assertNotNull(
			_fetchTranslationEntry(
				SegmentsExperience.class.getName(),
				segmentsExperience.getSegmentsExperienceId()));

		_layoutLocalService.deleteLayout(layout);

		Assert.assertNull(
			_fetchTranslationEntry(Layout.class.getName(), layout.getPlid()));
		Assert.assertNull(
			_fetchTranslationEntry(
				SegmentsExperience.class.getName(),
				segmentsExperience.getSegmentsExperienceId()));
	}

	private void _addTranslationEntry(
			String className, long classPK, ServiceContext serviceContext)
		throws Exception {

		_translationEntryLocalService.addOrUpdateTranslationEntry(
			_group.getGroupId(), className, classPK,
			TranslationTestUtil.getXLIFF(className, classPK),
			"application/xliff+xml", LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			serviceContext);
	}

	private TranslationEntry _fetchTranslationEntry(
		String className, long classPK) {

		return _translationEntryLocalService.fetchTranslationEntry(
			className, classPK, LocaleUtil.toLanguageId(LocaleUtil.SPAIN));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private TranslationEntryLocalService _translationEntryLocalService;

}