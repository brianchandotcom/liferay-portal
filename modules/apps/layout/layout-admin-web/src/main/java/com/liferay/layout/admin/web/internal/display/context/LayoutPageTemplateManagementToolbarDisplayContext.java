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
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
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
		HttpServletRequest request,
		LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			layoutPageTemplateDisplayContext.
				getLayoutPageTemplateEntriesSearchContainer());

		_request = request;
		_layoutPageTemplateDisplayContext = layoutPageTemplateDisplayContext;

		_themeDisplay = (ThemeDisplay)request.getAttribute(
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
						dropdownItem.setHref("#");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_request, "content-page-template"));
					});

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "addLayoutPageTemplateEntry");
						dropdownItem.putData(
							"addPageTemplateURL", _getAddLayoutPrototypeURL());
						dropdownItem.setHref("#");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "widget-page-template"));
					});
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
		actionURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout_page_template_entry");
		actionURL.setParameter("redirect", _themeDisplay.getURLCurrent());
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
		actionURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				_layoutPageTemplateDisplayContext.
					getLayoutPageTemplateCollectionId()));

		return actionURL.toString();
	}

	private final LayoutPageTemplateDisplayContext
		_layoutPageTemplateDisplayContext;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}