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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public LayoutPageTemplateManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			layoutPageTemplateDisplayContext.
				getLayoutPageTemplateEntriesSearchContainer());

		_layoutPageTemplateDisplayContext = layoutPageTemplateDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteLayoutPageTemplateEntries");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getAvailableActions(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		if (LayoutPageTemplateEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), layoutPageTemplateEntry,
				ActionKeys.DELETE)) {

			return "deleteLayoutPageTemplateEntries";
		}

		return StringPool.BLANK;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "layoutPageTemplateEntriesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "addLayoutPageTemplateEntry");
						dropdownItem.putData(
							"addPageTemplateURL",
							_getAddLayoutPageTemplateEntryURL());
						dropdownItem.setLabel(
							LanguageUtil.get(request, "content-page-template"));
					});

				Group scopeGroup = _themeDisplay.getScopeGroup();

				if (!scopeGroup.isLayoutSetPrototype()) {
					addPrimaryDropdownItem(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "addLayoutPageTemplateEntry");
							dropdownItem.putData(
								"addPageTemplateURL",
								_getAddLayoutPrototypeURL());
							dropdownItem.setLabel(
								LanguageUtil.get(
									request, "widget-page-template"));
						});
				}
			}
		};
	}

	@Override
	public String getDefaultEventHandler() {
		return "LAYOUT_PAGE_TEMPLATE_ENTRY_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_" +
			"HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "layoutPageTemplateEntries";
	}

	@Override
	public Boolean isShowCreationMenu() {
		return _layoutPageTemplateDisplayContext.isShowAddButton(
			LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY);
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"create-date", "name"};
	}

	private String _getAddLayoutPageTemplateEntryURL() {
		PortletURL actionURL = liferayPortletResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/add_layout_page_template_entry");
		actionURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		actionURL.setParameter("backURL", _themeDisplay.getURLCurrent());
		actionURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				_layoutPageTemplateDisplayContext.
					getLayoutPageTemplateCollectionId()));

		return actionURL.toString();
	}

	private String _getAddLayoutPrototypeURL() {
		PortletURL actionURL = liferayPortletResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_prototype/add_layout_prototype");
		actionURL.setParameter("backURL", _themeDisplay.getURLCurrent());
		actionURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				_layoutPageTemplateDisplayContext.
					getLayoutPageTemplateCollectionId()));

		return actionURL.toString();
	}

	private final LayoutPageTemplateDisplayContext
		_layoutPageTemplateDisplayContext;
	private final ThemeDisplay _themeDisplay;

}