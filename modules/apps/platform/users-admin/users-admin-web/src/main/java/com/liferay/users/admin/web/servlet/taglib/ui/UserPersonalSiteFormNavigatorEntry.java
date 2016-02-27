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

package com.liferay.users.admin.web.servlet.taglib.ui;

import com.liferay.admin.kernel.util.PortalMyAccountApplicationType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {"service.ranking:Integer=20"},
	service = FormNavigatorEntry.class
)
public class UserPersonalSiteFormNavigatorEntry
	extends BaseUserFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_USER_USER_INFORMATION;
	}

	@Override
	public String getKey() {
		return "personal-site-template";
	}

	@Override
	public boolean isVisible(User user, User selUser) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = PortletProviderUtil.getPortletId(
			PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
			PortletProvider.Action.VIEW);

		if ((selUser != null) &&
			portletId.equals(portletDisplay.getPortletName())) {

			return false;
		}

		return true;
	}

	@Override
	protected String getJspPath() {
		return "/user/personal_site.jsp";
	}

}