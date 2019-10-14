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

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplatePermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class MasterPageManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public MasterPageManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		MasterPageDisplayContext masterPageDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			masterPageDisplayContext.getMasterPagesSearchContainer());

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "masterPagesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		PortletURL addMasterPageURL = liferayPortletResponse.createActionURL();

		addMasterPageURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout_page_template/add_master_page");
		addMasterPageURL.setParameter("backURL", _themeDisplay.getURLCurrent());
		addMasterPageURL.setParameter(
			"type",
			String.valueOf(
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_PAGE));

		return new CreationMenu() {
			{
				addDropdownItem(
					dropdownItem -> {
						Map<String, Object> dropDownItemData = new HashMap<>();

						dropDownItemData.put("action", "addMasterPage");
						dropDownItemData.put(
							"addMasterPageURL", addMasterPageURL.toString());

						dropdownItem.setData(dropDownItemData);

						dropdownItem.setLabel(LanguageUtil.get(request, "add"));
					});
			}
		};
	}

	@Override
	public String getDefaultEventHandler() {
		return "MASTER_PAGE_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "masterPages";
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isShowCreationMenu() {
		if (LayoutPageTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY)) {

			return true;
		}

		return false;
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"create-date", "name"};
	}

	private final ThemeDisplay _themeDisplay;

}