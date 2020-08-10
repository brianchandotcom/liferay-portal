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

package com.liferay.commerce.organization.web.internal.servlet.taglib.ui;

import com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceOrganizationUsersScreenNavigationEntry
	implements ScreenNavigationCategory, ScreenNavigationEntry<Organization> {

	@Override
	public String getCategoryKey() {
		return CommerceOrganizationScreenNavigationConstants.
			CATEGORY_KEY_ORGANIZATION_USERS;
	}

	@Override
	public String getEntryKey() {
		return CommerceOrganizationScreenNavigationConstants.
			ENTRY_KEY_ORGANIZATION_USERS;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "users");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceOrganizationScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY;
	}

	@Override
	public boolean isVisible(User user, Organization organization) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			return OrganizationPermissionUtil.contains(
				permissionChecker, organization, ActionKeys.UPDATE);
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

		CommerceOrganizationDisplayContext commerceOrganizationDisplayContext =
			new CommerceOrganizationDisplayContext(
				httpServletRequest, _organizationService, null,
				_userLocalService);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceOrganizationDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse, "/organization/users.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrganizationUsersScreenNavigationEntry.class);

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private UserLocalService _userLocalService;

}