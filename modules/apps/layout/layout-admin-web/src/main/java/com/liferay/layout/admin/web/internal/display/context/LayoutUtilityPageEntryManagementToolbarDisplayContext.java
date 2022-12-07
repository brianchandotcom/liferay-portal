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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.utility.page.constants.LayoutUtilityPageActionKeys;
import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRenderer;
import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRendererRegistryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class LayoutUtilityPageEntryManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public LayoutUtilityPageEntryManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LayoutUtilityPageEntryDisplayContext
			layoutUtilityPageEntryDisplayContext,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			layoutUtilityPageEntryDisplayContext.
				getLayoutUtilityPageEntrySearchContainer());

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "exportLayoutUtilityPageEntries");
							dropdownItem.putData(
								"exportLayoutUtilityPageEntriesURL",
								_getExportLayoutUtilityPageEntryURL());
							dropdownItem.setIcon("upload");
							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "export"));
							dropdownItem.setQuickAction(true);
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData(
								"action",
								"deleteSelectedLayoutUtilityPageEntries");
							dropdownItem.putData(
								"deleteSelectedLayoutUtilityPageEntriesURL",
								PortletURLBuilder.createActionURL(
									liferayPortletResponse
								).setActionName(
									"/layout_admin" +
										"/delete_layout_utility_page_entry"
								).setRedirect(
									_themeDisplay.getURLCurrent()
								).buildString());
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "delete"));
							dropdownItem.setQuickAction(true);
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				for (LayoutUtilityPageEntryViewRenderer
						layoutUtilityPageEntryViewRenderer :
							LayoutUtilityPageEntryViewRendererRegistryUtil.
								getLayoutUtilityPageEntryViewRenderers()) {

					addPrimaryDropdownItem(
						dropdownItem -> {
							dropdownItem.setHref(
								_getSelectMasterLayoutURL(
									layoutUtilityPageEntryViewRenderer.
										getType()));
							dropdownItem.setLabel(
								layoutUtilityPageEntryViewRenderer.getLabel(
									_themeDisplay.getLocale()));
						});
				}
			}
		};
	}

	@Override
	public String getSearchContainerId() {
		return "entries";
	}

	@Override
	public Boolean isShowCreationMenu() {
		Group group = _themeDisplay.getScopeGroup();

		if (group.hasLocalOrRemoteStagingGroup()) {
			return false;
		}

		try {
			if (GroupPermissionUtil.contains(
					_themeDisplay.getPermissionChecker(),
					_themeDisplay.getScopeGroup(),
					LayoutUtilityPageActionKeys.
						ADD_LAYOUT_UTILITY_PAGE_ENTRY)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name", "create-date"};
	}

	private String _getExportLayoutUtilityPageEntryURL() {
		ResourceURL exportLayoutUtilityPageEntryURL =
			liferayPortletResponse.createResourceURL();

		exportLayoutUtilityPageEntryURL.setResourceID(
			"/layout_admin/export_layout_utility_page_entries");

		return exportLayoutUtilityPageEntryURL.toString();
	}

	private String _getSelectMasterLayoutURL(String type) {
		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCPath(
			"/select_layout_utility_page_entry_master_layout.jsp"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"type", type
		).buildString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutUtilityPageEntryManagementToolbarDisplayContext.class);

	private final ThemeDisplay _themeDisplay;

}