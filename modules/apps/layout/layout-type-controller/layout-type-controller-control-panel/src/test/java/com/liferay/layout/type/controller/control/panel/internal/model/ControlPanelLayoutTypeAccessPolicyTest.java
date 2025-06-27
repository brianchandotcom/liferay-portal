/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.control.panel.internal.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Thiago Buarque
 */
public class ControlPanelLayoutTypeAccessPolicyTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCheckAccessAllowedToPortlet() throws PortalException {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isDepot()
		).thenReturn(
			false
		);

		Mockito.when(
			group.isSite()
		).thenReturn(
			false
		);

		Mockito.when(
			group.isControlPanel()
		).thenReturn(
			false
		);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			group
		);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Portlet portlet = Mockito.mock(Portlet.class);

		portlet.setPortletId(RandomTestUtil.randomString());

		ControlPanelLayoutTypeAccessPolicy controlPanelLayoutTypeAccessPolicy =
			new ControlPanelLayoutTypeAccessPolicy();

		try {
			controlPanelLayoutTypeAccessPolicy.checkAccessAllowedToPortlet(
				httpServletRequest, null, portlet);

			Assert.fail();
		}
		catch (PortalException portalException) {
			Assert.assertEquals(
				"User does not have permission to access Control Panel " +
					"portlet " + portlet.getPortletId(),
				portalException.getMessage());
		}

		Mockito.when(
			group.isControlPanel()
		).thenReturn(
			true
		);

		MockedStatic<PortletPermissionUtil> portletPermissionUtilMockedStatic =
			Mockito.mockStatic(PortletPermissionUtil.class);

		portletPermissionUtilMockedStatic.when(
			() -> PortletPermissionUtil.hasControlPanelAccessPermission(
				PermissionThreadLocal.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), portlet)
		).thenReturn(
			true
		);

		controlPanelLayoutTypeAccessPolicy.checkAccessAllowedToPortlet(
			httpServletRequest, null, portlet);
	}

}