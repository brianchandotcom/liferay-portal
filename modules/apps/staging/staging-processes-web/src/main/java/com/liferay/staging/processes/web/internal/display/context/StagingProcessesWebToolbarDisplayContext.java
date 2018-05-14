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

package com.liferay.staging.processes.web.internal.display.context;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 */
public class StagingProcessesWebToolbarDisplayContext {

	public StagingProcessesWebToolbarDisplayContext(
		HttpServletRequest request, PageContext pageContext,
		LiferayPortletResponse portletResponse) {

		_request = request;

		_pageContext = pageContext;

		_portletResponse = portletResponse;

		Portlet portlet = portletResponse.getPortlet();

		_portletNamespace = PortalUtil.getPortletNamespace(
			portlet.getRootPortletId());
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							"javascript:" + _portletNamespace +
								"deleteEntries();");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
					});
			}
		};
	}

	public CreationMenu getCreationMenu(boolean hasPermission) {
		return new CreationMenu() {
			{
				int configurationType;
				String cmd;

				Group stagingGroup = (Group)_pageContext.getAttribute(
					"stagingGroup");

				long stagingGroupId = stagingGroup.getGroupId();

				if (stagingGroup.isStagedRemotely()) {
					configurationType =
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_REMOTE;
					cmd = Constants.PUBLISH_TO_REMOTE;
				}
				else {
					configurationType =
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_LOCAL;
					cmd = Constants.PUBLISH_TO_LIVE;
				}

				List<ExportImportConfiguration> exportImportConfigurations =
					ExportImportConfigurationLocalServiceUtil.getExportImportConfigurations(
						stagingGroupId, configurationType);

				if (hasPermission) {
					for (ExportImportConfiguration exportImportConfiguration :
							exportImportConfigurations) {

						addRestDropdownItem(
							dropdownItem -> {
								dropdownItem.setHref(getRenderURL(), "mvcRenderCommandName", "publishLayouts",
									Constants.CMD, cmd,
									"exportImportConfigurationId",
									String.valueOf(exportImportConfiguration.getExportImportConfigurationId()),
									"groupId", String.valueOf(stagingGroupId));
								dropdownItem.setLabel(
									exportImportConfiguration.getName());
							});
					}

					addPrimaryDropdownItem(
						dropdownItem -> {
							dropdownItem.setHref(getRenderURL(), "mvcRenderCommandName", "publishLayouts",
								Constants.CMD, cmd, "groupId",
								String.valueOf(stagingGroupId), "privateLayout",
								Boolean.FALSE.toString());
							dropdownItem.setLabel(
								LanguageUtil.get(
									_request, "custom-publication"));
						});
				}
			}
		};
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter"));
						dropdownGroupItem.setSeparator(true);
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropDownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public String getSortingOrder() {
		return ParamUtil.getString(_request, "orderByType", "asc");
	}

	public String getSortingURL() {
		PortletURL sortingURL = getRenderURL();

		sortingURL.setParameter(
			"groupId", String.valueOf(ParamUtil.getLong(_request, "groupId")));
		sortingURL.setParameter(
			"privateLayout",
			String.valueOf(ParamUtil.getBoolean(_request, "privateLayout")));
		sortingURL.setParameter(
			"displayStyle",
			ParamUtil.getString(_request, "displayStyle", "descriptive"));
		sortingURL.setParameter(
			"orderByCol", ParamUtil.getString(_request, "orderByCol"));

		String orderByType = ParamUtil.getString(_request, "orderByType");

		if (orderByType.equals("asc")) {
			sortingURL.setParameter("orderByType", "desc");
		}
		else {
			sortingURL.setParameter("orderByType", "asc");
		}

		sortingURL.setParameter(
			"navigation", ParamUtil.getString(_request, "navigation", "all"));
		sortingURL.setParameter(
			"searchContainerId",
			ParamUtil.getString(_request, "searchContainerId"));

		return sortingURL.toString();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getRenderURL(), getDisplayStyle()) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	protected String getDisplayStyle() {
		return ParamUtil.getString(_request, "displayStyle", "list");
	}

	protected PortletURL getRenderURL() {
		return _portletResponse.createRenderURL();
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							getRenderURL(), "groupId",
							String.valueOf(
								ParamUtil.getLong(_request, "groupId")),
							"privateLayout",
							String.valueOf(
								ParamUtil.getBoolean(
									_request, "privateLayout")),
							"displayStyle",
							ParamUtil.getString(
								_request, "displayStyle", "descriptive"),
							"orderByCol",
							ParamUtil.getString(_request, "orderByCol"),
							"orderByType",
							ParamUtil.getString(_request, "orderByType"),
							"navigation", "all", "searchContainerId",
							ParamUtil.getString(_request, "searchContainerId"));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref(
							getRenderURL(), "groupId",
							String.valueOf(
								ParamUtil.getLong(_request, "groupId")),
							"privateLayout",
							String.valueOf(
								ParamUtil.getBoolean(
									_request, "privateLayout")),
							"displayStyle",
							ParamUtil.getString(
								_request, "displayStyle", "descriptive"),
							"orderByCol",
							ParamUtil.getString(_request, "orderByCol"),
							"orderByType",
							ParamUtil.getString(_request, "orderByType"),
							"navigation", "completed", "searchContainerId",
							ParamUtil.getString(_request, "searchContainerId"));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "completed"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref(
							getRenderURL(), "groupId",
							String.valueOf(
								ParamUtil.getLong(_request, "groupId")),
							"privateLayout",
							String.valueOf(
								ParamUtil.getBoolean(
									_request, "privateLayout")),
							"displayStyle",
							ParamUtil.getString(
								_request, "displayStyle", "descriptive"),
							"orderByCol",
							ParamUtil.getString(_request, "orderByCol"),
							"orderByType",
							ParamUtil.getString(_request, "orderByType"),
							"navigation", "in-progress", "searchContainerId",
							ParamUtil.getString(_request, "searchContainerId"));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "in-progress"));
					});
			}
		};
	}

	private List<DropdownItem> _getOrderByDropDownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							getRenderURL(), "groupId",
							String.valueOf(
								ParamUtil.getLong(_request, "groupId")),
							"privateLayout",
							String.valueOf(
								ParamUtil.getBoolean(
									_request, "privateLayout")),
							"displayStyle",
							ParamUtil.getString(
								_request, "displayStyle", "descriptive"),
							"orderByCol", "name", "orderByType",
							ParamUtil.getString(_request, "orderByType"),
							"navigation",
							ParamUtil.getString(_request, "navigation", "all"),
							"searchContainerId",
							ParamUtil.getString(_request, "searchContainerId"));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "name"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref(
							getRenderURL(), "groupId",
							String.valueOf(
								ParamUtil.getLong(_request, "groupId")),
							"privateLayout",
							String.valueOf(
								ParamUtil.getBoolean(
									_request, "privateLayout")),
							"displayStyle",
							ParamUtil.getString(
								_request, "displayStyle", "descriptive"),
							"orderByCol", "create-date", "orderByType",
							ParamUtil.getString(_request, "orderByType"),
							"navigation",
							ParamUtil.getString(_request, "navigation", "all"),
							"searchContainerId",
							ParamUtil.getString(_request, "searchContainerId"));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "create-date"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref(
							getRenderURL(), "groupId",
							String.valueOf(
								ParamUtil.getLong(_request, "groupId")),
							"privateLayout",
							String.valueOf(
								ParamUtil.getBoolean(
									_request, "privateLayout")),
							"displayStyle",
							ParamUtil.getString(
								_request, "displayStyle", "descriptive"),
							"orderByCol", "completion-date", "orderByType",
							ParamUtil.getString(_request, "orderByType"),
							"navigation",
							ParamUtil.getString(_request, "navigation", "all"),
							"searchContainerId",
							ParamUtil.getString(_request, "searchContainerId"));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "completion-date"));
					});
			}
		};
	}

	private final PageContext _pageContext;
	private final String _portletNamespace;
	private final LiferayPortletResponse _portletResponse;
	private final HttpServletRequest _request;

}