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

package com.liferay.account.admin.web.internal.util;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.application.list.display.context.logic.PersonalMenuEntryHelper;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.roles.admin.constants.RolesAdminWebKeys;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class AccountRoleRequestHelperUtil {

	public static void setRequestAttributes(
		HttpServletRequest httpServletRequest,
		RoleTypeContributor accountRoleTypeContributor,
		PanelAppRegistry panelAppRegistry,
		PanelCategoryRegistry panelCategoryRegistry,
		List<PersonalMenuEntry> personalMenuEntries) {

		httpServletRequest.setAttribute(
			RolesAdminWebKeys.CURRENT_ROLE_TYPE, accountRoleTypeContributor);
		httpServletRequest.setAttribute(
			RolesAdminWebKeys.SHOW_NAV_TABS, Boolean.FALSE);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY, panelAppRegistry);

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			panelAppRegistry, panelCategoryRegistry);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_HELPER, panelCategoryHelper);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY,
			panelCategoryRegistry);

		PersonalMenuEntryHelper personalMenuEntryHelper =
			new PersonalMenuEntryHelper(personalMenuEntries);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PERSONAL_MENU_ENTRY_HELPER,
			personalMenuEntryHelper);
	}

	public static void setRequestAttributes(
		PortletRequest portletRequest,
		RoleTypeContributor accountRoleTypeContributor,
		PanelAppRegistry panelAppRegistry,
		PanelCategoryRegistry panelCategoryRegistry,
		List<PersonalMenuEntry> personalMenuEntries) {

		setRequestAttributes(
			PortalUtil.getHttpServletRequest(portletRequest),
			accountRoleTypeContributor, panelAppRegistry, panelCategoryRegistry,
			personalMenuEntries);
	}

}