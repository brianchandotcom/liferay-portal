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

package com.liferay.site.memberships.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class OrganizationsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public OrganizationsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request,
		OrganizationsDisplayContext organizationsDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			organizationsDisplayContext.getOrganizationSearchContainer());

		_request = request;
		_organizationsDisplayContext = organizationsDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteSelectedOrganizations");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public Map<String, Object> getComponentContext() throws Exception {
		Map<String, Object> componentContext = new HashMap<>();

		PortletURL selectOrganizationsURL =
			liferayPortletResponse.createRenderURL();

		selectOrganizationsURL.setParameter(
			"mvcPath", "/select_organizations.jsp");
		selectOrganizationsURL.setWindowState(LiferayWindowState.POP_UP);

		componentContext.put(
			"selectOrganizationsURL", selectOrganizationsURL.toString());

		return componentContext;
	}

	@Override
	public String getComponentId() {
		return "organizationsManagementToolbar";
	}

	@Override
	public String getDefaultEventHandler() {
		return "organizationsManagementToolbarDefaultEventHandler";
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "organizations";
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (GroupPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					_organizationsDisplayContext.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public Boolean isShowInfoButton() {
		return true;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name", "type"};
	}

	private final OrganizationsDisplayContext _organizationsDisplayContext;
	private final HttpServletRequest _request;

}