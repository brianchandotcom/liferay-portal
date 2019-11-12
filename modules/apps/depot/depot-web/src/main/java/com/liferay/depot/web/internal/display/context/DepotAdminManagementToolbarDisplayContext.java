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

package com.liferay.depot.web.internal.display.context;

import com.liferay.depot.web.internal.util.DepotEntryURLUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DepotAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public DepotAdminManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		DepotAdminDisplayContext depotAdminDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			depotAdminDisplayContext.getGroupSearch());

		_depotAdminDisplayContext = depotAdminDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteSelectedDepotEntries");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("orderByCol", getOrderByCol());
		clearResultsURL.setParameter("orderByType", getOrderByType());

		return clearResultsURL.toString();
	}

	public Map<String, Object> getComponentContext() throws PortalException {
		PortletURL deleteDepotEntries =
			liferayPortletResponse.createActionURL();

		deleteDepotEntries.setParameter(
			ActionRequest.ACTION_NAME, "/depot_entry/delete");

		return HashMapBuilder.<String, Object>put(
			"deleteDepotEntriesURL", deleteDepotEntries.toString()
		).build();
	}

	@Override
	public String getComponentId() {
		return "depotAdminManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!PortalPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				ActionKeys.ADD_COMMUNITY)) {

			return null;
		}

		try {
			PortletURL addDepotEntryURL =
				DepotEntryURLUtil.getAddDepotEntryActionURL(
					themeDisplay.getURLCurrent(), liferayPortletResponse);

			return new CreationMenu() {
				{
					addPrimaryDropdownItem(
						dropdownItem -> {
							dropdownItem.putData("action", "addDepotEntry");
							dropdownItem.putData(
								"addDepotEntryURL",
								addDepotEntryURL.toString());
							dropdownItem.setLabel(
								LanguageUtil.get(request, "add"));
						});
				}
			};
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return "depotAdminManagementToolbarDefaultEventHandler";
	}

	public Map<String, Object> getRowData(Group curGroup)
		throws PortalException {

		return HashMapBuilder.put(
			"actions", (Object)StringUtil.merge(_getAvailableActions(curGroup))
		).build();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchTagURL = getPortletURL();

		searchTagURL.setParameter("orderByCol", getOrderByCol());
		searchTagURL.setParameter("orderByType", getOrderByType());

		return searchTagURL.toString();
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (PortalPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				ActionKeys.ADD_COMMUNITY)) {

			return true;
		}

		return false;
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return _depotAdminDisplayContext.getDefaultDisplayStyle();
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive", "icon"};
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"descriptive-name"};
	}

	private List<String> _getAvailableActions(Group group)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (_hasDeleteGroupPermission(group)) {
			availableActions.add("deleteSelectedDepotEntries");
		}

		return availableActions;
	}

	private boolean _hasDeleteGroupPermission(Group group)
		throws PortalException {

		if (group.isCompany()) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), group,
				ActionKeys.DELETE)) {

			return false;
		}

		if (PortalUtil.isSystemGroup(group.getGroupKey())) {
			return false;
		}

		return true;
	}

	private final DepotAdminDisplayContext _depotAdminDisplayContext;

}