/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ViewAllSpacesDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetCreationMenu() {
		ViewAllSpacesDisplayContext viewAllSpacesDisplayContext1 =
			_getViewAllSpacesDisplayContext(true);

		CreationMenu creationMenu1 =
			viewAllSpacesDisplayContext1.getCreationMenu();

		Assert.assertFalse(creationMenu1.isEmpty());

		ViewAllSpacesDisplayContext viewAllSpacesDisplayContext2 =
			_getViewAllSpacesDisplayContext(false);

		CreationMenu creationMenu2 =
			viewAllSpacesDisplayContext2.getCreationMenu();

		Assert.assertTrue(creationMenu2.isEmpty());
	}

	private ViewAllSpacesDisplayContext _getViewAllSpacesDisplayContext(
		boolean hasPermission) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			new ThemeDisplay() {
				{
					setPathFriendlyURLPublic(StringUtil.randomString());
					setURLCurrent(StringUtil.randomString());
				}
			});

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenReturn(
			StringUtil.randomString()
		);

		return new ViewAllSpacesDisplayContext(
			null, mockHttpServletRequest, language, null,
			new TestPortletResourcePermission(hasPermission));
	}

	private static class TestPortletResourcePermission
		implements PortletResourcePermission {

		public TestPortletResourcePermission(boolean hasPermission) {
			_hasPermission = hasPermission;
		}

		@Override
		public void check(
				PermissionChecker permissionChecker, Group group,
				String actionId)
			throws PrincipalException {

			if (!contains(permissionChecker, group, actionId)) {
				throw new PrincipalException();
			}
		}

		@Override
		public void check(
				PermissionChecker permissionChecker, long groupId,
				String actionId)
			throws PrincipalException {

			if (!contains(permissionChecker, groupId, actionId)) {
				throw new PrincipalException();
			}
		}

		@Override
		public boolean contains(
			PermissionChecker permissionChecker, Group group, String actionId) {

			return _hasPermission;
		}

		@Override
		public boolean contains(
			PermissionChecker permissionChecker, long groupId,
			String actionId) {

			return _hasPermission;
		}

		@Override
		public String getResourceName() {
			return StringUtil.randomString();
		}

		private final boolean _hasPermission;

	}

}