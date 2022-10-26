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

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.UserPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = CTDisplayRenderer.class)
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

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, UsersAdminPortletKeys.USERS_ADMIN,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/users_admin/edit_user"
		).setParameter(
			"p_u_i_d", user.getUserId()
		).buildPortletURL();

		String currentURL = _portal.getCurrentURL(httpServletRequest);

		portletURL.setParameter("redirect", currentURL);
		portletURL.setParameter("backURL", currentURL);

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
	protected void buildDisplay(DisplayBuilder<User> displayBuilder) {
		User user = displayBuilder.getModel();

		displayBuilder.display(
			"full-name", user.getFullName()
		).display(
			"screen-name", user.getScreenName()
		).display(
			"email-address", user.getEmailAddress()
		).display(
			"create-date", user.getCreateDate()
		).display(
			"last-modified", user.getModifiedDate()
		).display(
			"job-title", user.getJobTitle()
		).display(
			"greeting", user.getGreeting()
		).display(
			"comments", user.getComments()
		).display(
			"facebook-id", user.getFacebookId()
		).display(
			"google-user-id", user.getGoogleUserId()
		).display(
			"open-id", user.getOpenId()
		).display(
			"language-id", user.getLanguageId()
		).display(
			"time-zone", user.getTimeZoneId()
		).display(
			"login-date", user.getLoginDate()
		).display(
			"login-ip", user.getLoginIP()
		).display(
			"last-login-date", user.getLastLoginDate()
		).display(
			"last-login-ip", user.getLastLoginIP()
		);
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserPermission _userPermission;

}