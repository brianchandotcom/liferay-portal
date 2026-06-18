/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.application.list;

import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.staging.StagingGroupHelper;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Georgel Pop
 */
public class LayoutPageTemplatesPanelAppTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsShow() throws Exception {
		_testIsShowWhenFeatureFlagIsEnabled();
		_testIsShowWithCompanyGroupAndNoWidgetPageTemplates();
		_testIsShowWithCompanyGroupAndWidgetPageTemplates();
		_testIsShowWithLocalLiveGroup();
		_testIsShowWithRemoteLiveGroup();
		_testIsShowWithSiteGroup();
	}

	private LayoutPageTemplatesPanelApp _createLayoutPageTemplatesPanelApp(
			LayoutPageTemplateEntryService layoutPageTemplateEntryService,
			StagingGroupHelper stagingGroupHelper)
		throws Exception {

		LayoutPageTemplatesPanelApp layoutPageTemplatesPanelApp =
			new LayoutPageTemplatesPanelApp();

		Portlet portlet = Mockito.mock(Portlet.class);

		Mockito.when(
			portlet.isActive()
		).thenReturn(
			true
		);

		PortletLocalService portletLocalService = Mockito.mock(
			PortletLocalService.class);

		Mockito.when(
			portletLocalService.getPortletById(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			portlet
		);

		ReflectionTestUtil.setFieldValue(
			layoutPageTemplatesPanelApp, "_layoutPageTemplateEntryService",
			layoutPageTemplateEntryService);
		ReflectionTestUtil.setFieldValue(
			layoutPageTemplatesPanelApp, "_portletLocalService",
			portletLocalService);
		ReflectionTestUtil.setFieldValue(
			layoutPageTemplatesPanelApp, "_stagingGroupHelper",
			stagingGroupHelper);

		return layoutPageTemplatesPanelApp;
	}

	private void _testIsShowWhenFeatureFlagIsEnabled() throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isCompany()
		).thenReturn(
			true
		);

		LayoutPageTemplatesPanelApp layoutPageTemplatesPanelApp =
			_createLayoutPageTemplatesPanelApp(
				Mockito.mock(LayoutPageTemplateEntryService.class),
				Mockito.mock(StagingGroupHelper.class));

		try (MockedStatic<FeatureFlagManagerUtil>
				featureFlagManagerUtilMockedStatic = Mockito.mockStatic(
					FeatureFlagManagerUtil.class)) {

			featureFlagManagerUtilMockedStatic.when(
				() -> FeatureFlagManagerUtil.isEnabled(
					Mockito.anyLong(), Mockito.eq("LPD-76864"))
			).thenReturn(
				true
			);

			Assert.assertTrue(
				layoutPageTemplatesPanelApp.isShow(
					Mockito.mock(PermissionChecker.class), group));
		}
	}

	private void _testIsShowWithCompanyGroupAndNoWidgetPageTemplates()
		throws Exception {

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isCompany()
		).thenReturn(
			true
		);

		LayoutPageTemplatesPanelApp layoutPageTemplatesPanelApp =
			_createLayoutPageTemplatesPanelApp(
				Mockito.mock(LayoutPageTemplateEntryService.class),
				Mockito.mock(StagingGroupHelper.class));

		try (MockedStatic<FeatureFlagManagerUtil>
				featureFlagManagerUtilMockedStatic = Mockito.mockStatic(
					FeatureFlagManagerUtil.class)) {

			Assert.assertFalse(
				layoutPageTemplatesPanelApp.isShow(
					Mockito.mock(PermissionChecker.class), group));
		}
	}

	private void _testIsShowWithCompanyGroupAndWidgetPageTemplates()
		throws Exception {

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isCompany()
		).thenReturn(
			true
		);

		LayoutPageTemplateEntryService layoutPageTemplateEntryService =
			Mockito.mock(LayoutPageTemplateEntryService.class);

		Mockito.when(
			layoutPageTemplateEntryService.
				getLayoutPageTemplateEntriesCountByType(
					Mockito.anyLong(),
					Mockito.eq(
						LayoutPageTemplateConstants.
							PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT),
					Mockito.eq(
						LayoutPageTemplateEntryTypeConstants.WIDGET_PAGE))
		).thenReturn(
			RandomTestUtil.randomInt()
		);

		LayoutPageTemplatesPanelApp layoutPageTemplatesPanelApp =
			_createLayoutPageTemplatesPanelApp(
				layoutPageTemplateEntryService,
				Mockito.mock(StagingGroupHelper.class));

		try (MockedStatic<FeatureFlagManagerUtil>
				featureFlagManagerUtilMockedStatic = Mockito.mockStatic(
					FeatureFlagManagerUtil.class)) {

			Assert.assertTrue(
				layoutPageTemplatesPanelApp.isShow(
					Mockito.mock(PermissionChecker.class), group));
		}
	}

	private void _testIsShowWithLocalLiveGroup() throws Exception {
		Group group = Mockito.mock(Group.class);

		StagingGroupHelper stagingGroupHelper = Mockito.mock(
			StagingGroupHelper.class);

		Mockito.when(
			stagingGroupHelper.isLocalLiveGroup(group)
		).thenReturn(
			true
		);

		LayoutPageTemplatesPanelApp layoutPageTemplatesPanelApp =
			_createLayoutPageTemplatesPanelApp(
				Mockito.mock(LayoutPageTemplateEntryService.class),
				stagingGroupHelper);

		Assert.assertFalse(
			layoutPageTemplatesPanelApp.isShow(
				Mockito.mock(PermissionChecker.class), group));
	}

	private void _testIsShowWithRemoteLiveGroup() throws Exception {
		Group group = Mockito.mock(Group.class);

		StagingGroupHelper stagingGroupHelper = Mockito.mock(
			StagingGroupHelper.class);

		Mockito.when(
			stagingGroupHelper.isRemoteLiveGroup(group)
		).thenReturn(
			true
		);

		LayoutPageTemplatesPanelApp layoutPageTemplatesPanelApp =
			_createLayoutPageTemplatesPanelApp(
				Mockito.mock(LayoutPageTemplateEntryService.class),
				stagingGroupHelper);

		Assert.assertFalse(
			layoutPageTemplatesPanelApp.isShow(
				Mockito.mock(PermissionChecker.class), group));
	}

	private void _testIsShowWithSiteGroup() throws Exception {
		LayoutPageTemplatesPanelApp layoutPageTemplatesPanelApp =
			_createLayoutPageTemplatesPanelApp(
				Mockito.mock(LayoutPageTemplateEntryService.class),
				Mockito.mock(StagingGroupHelper.class));

		Assert.assertTrue(
			layoutPageTemplatesPanelApp.isShow(
				Mockito.mock(PermissionChecker.class),
				Mockito.mock(Group.class)));
	}

}