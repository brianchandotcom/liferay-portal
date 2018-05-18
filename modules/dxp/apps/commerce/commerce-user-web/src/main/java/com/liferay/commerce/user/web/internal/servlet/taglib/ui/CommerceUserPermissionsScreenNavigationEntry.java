/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.web.internal.servlet.taglib.ui;

import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.user.service.CommerceUserService;
import com.liferay.commerce.user.util.CommerceRoleRegistry;
import com.liferay.commerce.user.web.internal.display.context.CommerceUserPermissionsDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "screen.navigation.entry.order:Integer=30",
	service = ScreenNavigationEntry.class
)
public class CommerceUserPermissionsScreenNavigationEntry
	implements ScreenNavigationEntry<User> {

	@Override
	public String getCategoryKey() {
		return CommerceUserScreenNavigationConstants.CATEGORY_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return CommerceUserScreenNavigationConstants.ENTRY_KEY_USER_PERMISSIONS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "entitlements");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceUserScreenNavigationConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public boolean isVisible(User user, User selUser) {
		try {
			if (selUser.getUserId() == user.getUserId()) {
				return false;
			}

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker == null) {
				throw new PrincipalException(
					"PermissionChecker not initialized");
			}

			return UserPermissionUtil.contains(
				permissionChecker, user.getUserId(), ActionKeys.MANAGE_USERS);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceUserPermissionsDisplayContext
			commerceUserPermissionsDisplayContext =
				new CommerceUserPermissionsDisplayContext(
					_commerceOrganizationHelper, _commerceRoleRegistry,
					_commerceUserService, httpServletRequest, _portal,
					_userGroupRoleLocalService);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceUserPermissionsDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse, "/edit_permissions.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceUserPermissionsScreenNavigationEntry.class);

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceRoleRegistry _commerceRoleRegistry;

	@Reference
	private CommerceUserService _commerceUserService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}