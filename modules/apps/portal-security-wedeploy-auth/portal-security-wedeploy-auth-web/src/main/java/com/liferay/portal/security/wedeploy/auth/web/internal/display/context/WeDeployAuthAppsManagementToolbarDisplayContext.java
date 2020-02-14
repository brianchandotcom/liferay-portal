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

package com.liferay.portal.security.wedeploy.auth.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.wedeploy.auth.constants.WeDeployAuthActionKeys;
import com.liferay.portal.security.wedeploy.auth.web.internal.security.permission.resource.WeDeployAuthPermission;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class WeDeployAuthAppsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public WeDeployAuthAppsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer weDeployAuthAppsSearchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			weDeployAuthAppsSearchContainer);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public CreationMenu getCreationMenu() {
		PortletURL editWeDeployAuthAppURL =
			liferayPortletResponse.createRenderURL();

		editWeDeployAuthAppURL.setParameter(
			"mvcRenderCommandName",
			"/wedeploy_auth_admin/edit_wedeploy_auth_app");
		editWeDeployAuthAppURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(editWeDeployAuthAppURL);
				dropdownItem.setLabel(
					LanguageUtil.get(request, "add-wedeploy-app"));
			}
		).build();
	}

	@Override
	public String getSearchContainerId() {
		return "weDeployAuthApps";
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isShowCreationMenu() {
		if (WeDeployAuthPermission.contains(
				_themeDisplay.getPermissionChecker(),
				WeDeployAuthActionKeys.ADD_APP)) {

			return true;
		}

		return false;
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list"};
	}

	private final ThemeDisplay _themeDisplay;

}