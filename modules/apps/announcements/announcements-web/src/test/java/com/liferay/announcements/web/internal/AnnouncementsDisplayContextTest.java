/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal;

import com.liferay.announcements.constants.AnnouncementsPortletKeys;
import com.liferay.announcements.web.internal.display.context.AnnouncementsDisplayContext;
import com.liferay.announcements.web.internal.display.context.helper.AnnouncementsRequestHelper;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.test.portlet.MockRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockRenderResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.context.RequestContextMapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AnnouncementsDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_groupPermissionUtilMockedStatic = Mockito.mockStatic(
			GroupPermissionUtil.class);
		_portletPermissionUtilMockedStatic = Mockito.mockStatic(
			PortletPermissionUtil.class);
	}

	@After
	public void tearDown() {
		_groupPermissionUtilMockedStatic.close();
		_portletPermissionUtilMockedStatic.close();
	}

	@Test
	public void testHasAddAnnouncementsEntryPermission() {
		_testHasAddAnnouncementsEntryPermission(false, false, false);
		_testHasAddAnnouncementsEntryPermission(true, false, true);
		_testHasAddAnnouncementsEntryPermission(true, true, false);
	}

	private void _testHasAddAnnouncementsEntryPermission(
		boolean hasAddAnnouncementsEntryPermission, boolean hasGroupPermission,
		boolean hasPortletPermission) {

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		long scopeGroupId = RandomTestUtil.randomLong();

		_groupPermissionUtilMockedStatic.when(
			() -> GroupPermissionUtil.contains(
				permissionChecker, scopeGroupId,
				ActionKeys.MANAGE_ANNOUNCEMENTS)
		).thenReturn(
			hasGroupPermission
		);

		_portletPermissionUtilMockedStatic.when(
			() -> PortletPermissionUtil.hasControlPanelAccessPermission(
				permissionChecker, scopeGroupId,
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)
		).thenReturn(
			hasPortletPermission
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getPermissionChecker()
		).thenReturn(
			permissionChecker
		);

		Mockito.when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			scopeGroupId
		);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		AnnouncementsDisplayContext announcementsDisplayContext =
			new AnnouncementsDisplayContext(
				Mockito.mock(AnnouncementsRequestHelper.class),
				mockHttpServletRequest, RandomTestUtil.randomString(),
				new MockRenderRequest(), new MockRenderResponse(),
				Mockito.mock(RequestContextMapper.class),
				Mockito.mock(SegmentsEntryRetriever.class),
				Mockito.mock(SegmentsConfigurationProvider.class));

		Assert.assertEquals(
			hasAddAnnouncementsEntryPermission,
			announcementsDisplayContext.hasAddAnnouncementsEntryPermission());
	}

	private MockedStatic<GroupPermissionUtil> _groupPermissionUtilMockedStatic;
	private MockedStatic<PortletPermissionUtil>
		_portletPermissionUtilMockedStatic;

}