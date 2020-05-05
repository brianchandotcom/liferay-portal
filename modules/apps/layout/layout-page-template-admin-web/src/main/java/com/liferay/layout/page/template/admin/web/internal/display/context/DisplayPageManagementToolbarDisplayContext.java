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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.page.template.admin.web.internal.configuration.util.ExportImportDisplayPageConfigurationUtil;
import com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplatePermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DisplayPageManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public DisplayPageManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		DisplayPageDisplayContext displayPageDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			displayPageDisplayContext.getDisplayPagesSearchContainer());

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteSelectedDisplayPages");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(LanguageUtil.get(request, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			ExportImportDisplayPageConfigurationUtil::enabled,
			dropdownItem -> {
				dropdownItem.putData("action", "exportDisplayPages");
				dropdownItem.putData(
					"exportDisplayPageURL", _getExportDisplayPageURL());
				dropdownItem.setIcon("download");
				dropdownItem.setLabel(LanguageUtil.get(request, "export"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getAvailableActions(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (LayoutPageTemplateEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), layoutPageTemplateEntry,
				ActionKeys.DELETE)) {

			availableActions.add("deleteSelectedDisplayPages");
		}

		if (ExportImportDisplayPageConfigurationUtil.enabled() &&
			(layoutPageTemplateEntry.getLayoutPrototypeId() == 0) &&
			!layoutPageTemplateEntry.isDraft()) {

			availableActions.add("exportDisplayPages");
		}

		return StringUtil.merge(availableActions, StringPool.COMMA);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "displayPagesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				PortletURL selectMasterLayoutURL =
					liferayPortletResponse.createRenderURL();

				selectMasterLayoutURL.setParameter(
					"mvcPath", "/select_display_page_master_layout.jsp");
				selectMasterLayoutURL.setParameter(
					"redirect", _themeDisplay.getURLCurrent());

				dropdownItem.setHref(selectMasterLayoutURL.toString());

				dropdownItem.setLabel(LanguageUtil.get(request, "add"));
			}
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		return "DISPLAY_PAGE_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "displayPages";
	}

	@Override
	public Boolean isShowCreationMenu() {
		if (LayoutPageTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getSiteGroupId(),
				LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY)) {

			return true;
		}

		return false;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"create-date", "name"};
	}

	private String _getExportDisplayPageURL() {
		ResourceURL exportDisplayPageURL =
			liferayPortletResponse.createResourceURL();

		exportDisplayPageURL.setResourceID(
			"/layout_page_template/export_display_page");

		return exportDisplayPageURL.toString();
	}

	private final ThemeDisplay _themeDisplay;

}