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

package com.liferay.commerce.user.web.internal.servlet.taglib.ui;

import com.liferay.commerce.user.service.CommerceUserService;
import com.liferay.commerce.user.web.internal.display.context.CommerceUserDetailDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.user.admin.configuration.UserFileUploadsConfiguration",
	property = "screen.navigation.entry.order:Integer=10",
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceUserDetailsScreenNavigationEntry
	implements ScreenNavigationEntry<User>, ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceUserScreenNavigationConstants.CATEGORY_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return CommerceUserScreenNavigationConstants.ENTRY_KEY_USER_DETAIL;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "user-detail");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceUserScreenNavigationConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceUserDetailDisplayContext commerceUserDetailDisplayContext =
			new CommerceUserDetailDisplayContext(
				_commerceUserService, httpServletRequest, _portal,
				_userFileUploadsConfiguration);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceUserDetailDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse, "/edit_user_detail.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	@Reference
	private CommerceUserService _commerceUserService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	private volatile UserFileUploadsConfiguration _userFileUploadsConfiguration;

}