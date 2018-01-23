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

package com.liferay.users.admin.web.internal.servlet.taglib.ui;

import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorContextConstants;
import com.liferay.frontend.taglib.form.navigator.context.FormNavigatorContextProvider;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = FormNavigatorContextConstants.FORM_NAVIGATOR_ID + "=" + FormNavigatorConstants.FORM_NAVIGATOR_ID_USERS,
	service = FormNavigatorContextProvider.class
)
public class UsersFormNavigatorContextProvider
	implements FormNavigatorContextProvider<User> {

	@Override
	public String getContext(User selectedUser) {
		if (PortletKeys.MY_ACCOUNT.equals(_getPortletName())) {
			return "my.account";
		}

		if (selectedUser == null) {
			return FormNavigatorContextConstants.CONTEXT_ADD;
		}

		return FormNavigatorContextConstants.CONTEXT_UPDATE;
	}

	private String _getPortletName() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getPortletName();
	}

}