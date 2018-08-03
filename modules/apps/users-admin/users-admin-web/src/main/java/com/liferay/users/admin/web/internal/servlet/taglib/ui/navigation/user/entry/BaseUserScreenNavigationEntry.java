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

package com.liferay.users.admin.web.internal.servlet.taglib.ui.navigation.user.entry;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.users.admin.constants.UserFormConstants;
import com.liferay.users.admin.web.internal.constants.UsersAdminWebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
public abstract class BaseUserScreenNavigationEntry
	implements ScreenNavigationEntry<User> {

	public abstract String getActionCommandName();

	public abstract String getJspPath();

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), getEntryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return UserFormConstants.SCREEN_NAVIGATION_KEY_USERS;
	}

	public boolean isEditable(
		HttpServletRequest request, HttpServletResponse response) {

		return true;
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		if (getJspPath() == null) {
			return;
		}

		request.setAttribute(
			UsersAdminWebKeys.ACTION_COMMAND_NAME, getActionCommandName());
		request.setAttribute(
			UsersAdminWebKeys.EDITABLE, isEditable(request, response));
		request.setAttribute(
			UsersAdminWebKeys.FORM_LABEL, getLabel(request.getLocale()));
		request.setAttribute(UsersAdminWebKeys.JSP_PATH, getJspPath());

		jspRenderer.renderJSP(request, response, "/edit_user_navigation.jsp");
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		return resourceBundleLoader.loadResourceBundle(locale);
	}

	@Reference
	protected JSPRenderer jspRenderer;

	@Reference
	protected Portal portal;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.users.admin.web)"
	)
	protected volatile ResourceBundleLoader resourceBundleLoader;

}