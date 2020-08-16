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

package com.liferay.users.admin.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.base.BaseCTDisplayRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.permission.UserPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class UserCTDisplayRenderer extends BaseCTDisplayRenderer<User> {

	@Override
	public String getEditURL(HttpServletRequest httpServletRequest, User user) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_userPermission.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getUserId(),
				ActionKeys.UPDATE)) {

			return null;
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, UsersAdminPortletKeys.USERS_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/users_admin/edit_user");
		portletURL.setParameter("p_u_i_d", String.valueOf(user.getUserId()));

		String currentURL = _portal.getCurrentURL(httpServletRequest);

		portletURL.setParameter("backURL", currentURL);
		portletURL.setParameter("redirect", currentURL);

		return portletURL.toString();
	}

	@Override
	public Class<User> getModelClass() {
		return User.class;
	}

	@Override
	public String getTitle(Locale locale, User user) {
		String title = user.getFullName();

		if (Validator.isNotNull(title)) {
			return title;
		}

		return user.getScreenName();
	}

	@Override
	protected CTService<User> getCTService() {
		return _userLocalService;
	}

	@Override
	protected String[] getDisplayAttributeNames() {
		return _DISPLAY_ATTRIBUTE_NAMES;
	}

	@Override
	protected Map<String, Object> getDisplayAttributes(
		Locale locale, User user) {

		return LinkedHashMapBuilder.<String, Object>put(
			"fullName", user.getFullName()
		).put(
			"screenName", user.getScreenName()
		).put(
			"emailAddress", user.getEmailAddress()
		).putAll(
			super.getDisplayAttributes(locale, user)
		).build();
	}

	private static final String[] _DISPLAY_ATTRIBUTE_NAMES = {
		"comments", "createDate", "facebookId", "greeting", "googleUserId",
		"jobTitle", "languageId", "lastLoginDate", "lastLoginIP", "loginDate",
		"loginIP", "modifiedDate", "openId", "timeZoneId"
	};

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserPermission _userPermission;

}