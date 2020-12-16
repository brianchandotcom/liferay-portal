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

import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.staging.constants.StagingProcessesPortletKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 */
public class StagingProcessesWebToolbarDisplayContext {

	public StagingProcessesWebToolbarDisplayContext(
		HttpServletRequest httpServletRequest, PageContext pageContext,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_pageContext = pageContext;

		_liferayPortletResponse = liferayPortletResponse;

		Portlet portlet = liferayPortletResponse.getPortlet();

		_portletNamespace = PortalUtil.getPortletNamespace(
			portlet.getRootPortletId());
	}

	public List<DropdownItem> getActionDropdownItems(boolean hasPermission) {
		if (!hasPermission) {
			return null;
		}

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					"javascript:" + _portletNamespace + "deleteEntries();");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
			}
		).build();
	}

	public CreationMenu getCreationMenu(boolean hasPermission) {
		if (!hasPermission) {
			return null;
		}

		return new CreationMenu() {
			{
				int configurationType = 0;

				Group stagingGroup = (Group)_pageContext.getAttribute(
					"stagingGroup");

				long stagingGroupId = stagingGroup.getGroupId();

				if (stagingGroup.isStagedRemotely()) {
					configurationType =
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_REMOTE;
				}
				else {
					configurationType =
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_LOCAL;
				}

				List<ExportImportConfiguration> exportImportConfigurations =
					ExportImportConfigurationLocalServiceUtil.
						getExportImportConfigurations(
							stagingGroupId, configurationType);

				for (ExportImportConfiguration exportImportConfiguration :
						exportImportConfigurations) {

					addRestDropdownItem(
						dropdownItem -> {
							String cmd = Constants.PUBLISH_TO_LIVE;

							if (stagingGroup.isStagedRemotely()) {
								cmd = Constants.PUBLISH_TO_REMOTE;
							}

							dropdownItem.setHref(
								_liferayPortletResponse.createRenderURL(),
								"mvcRenderCommandName",
								"/staging_processes/publish_layouts",
								Constants.CMD, cmd,
								"exportImportConfigurationId",
								String.valueOf(
									exportImportConfiguration.
										getExportImportConfigurationId()),
								"groupId", String.valueOf(stagingGroupId));
							dropdownItem.setLabel(
								exportImportConfiguration.getName());
						});
				}

				addPrimaryDropdownItem(
					dropdownItem -> {
						String cmd = Constants.PUBLISH_TO_LIVE;

						if (stagingGroup.isStagedRemotely()) {
							cmd = Constants.PUBLISH_TO_REMOTE;
						}

						dropdownItem.setHref(
							_liferayPortletResponse.createRenderURL(),
							"mvcRenderCommandName",
							"/staging_processes/publish_layouts", Constants.CMD,
							cmd, "groupId", String.valueOf(stagingGroupId),
							"privateLayout", Boolean.FALSE.toString());
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "custom-publish-process"));
					});
			}
		};
	}

	public String getDisplayStyle() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_httpServletRequest);

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		String displayPreferences = portalPreferences.getValue(
			StagingProcessesPortletKeys.STAGING_PROCESSES, "display-style",
			"descriptive");

		if (Validator.isNull(displayStyle)) {
			displayStyle = displayPreferences;
		}

		if (displayStyle != displayPreferences) {
			portalPreferences.setValue(
				StagingProcessesPortletKeys.STAGING_PROCESSES, "display-style",
				displayStyle);
		}

		return displayStyle;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "filter"));
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropDownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public String getSortingOrder() {
		return ParamUtil.getString(_httpServletRequest, "orderByType", "asc");
	}

	public String getSortingURL() {
		PortletURL sortingURL = _getStagingRenderURL();

		if (getSortingOrder().equals("asc")) {
			sortingURL.setParameter("orderByType", "desc");
		}
		else {
			sortingURL.setParameter("orderByType", "asc");
		}

		return sortingURL.toString();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		return new ViewTypeItemList(portletURL, getDisplayStyle()) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(_getNavigationURL("all"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "all"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(_getNavigationURL("completed"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "completed"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(_getNavigationURL("in-progress"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "in-progress"));
			}
		).build();
	}

	private PortletURL _getNavigationURL(String navigation) {
		PortletURL url = _getStagingRenderURL();

		url.setParameter("navigation", navigation);

		return url;
	}

	private List<DropdownItem> _getOrderByDropDownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(_getOrderByURL("name"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "name"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(_getOrderByURL("create-date"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "create-date"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(_getOrderByURL("completion-date"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "completion-date"));
			}
		).build();
	}

	private PortletURL _getOrderByURL(String orderByColumnName) {
		PortletURL url = _getStagingRenderURL();

		url.setParameter("orderByCol", orderByColumnName);

		return url;
	}

	private PortletURL _getStagingRenderURL() {
		PortletURL renderURL = _liferayPortletResponse.createRenderURL();

		renderURL.setParameter(
			"navigation",
			ParamUtil.getString(_httpServletRequest, "navigation", "all"));
		renderURL.setParameter(
			"groupId",
			String.valueOf(ParamUtil.getLong(_httpServletRequest, "groupId")));
		renderURL.setParameter(
			"privateLayout",
			String.valueOf(
				ParamUtil.getBoolean(_httpServletRequest, "privateLayout")));
		renderURL.setParameter("displayStyle", getDisplayStyle());
		renderURL.setParameter(
			"orderByCol",
			ParamUtil.getString(_httpServletRequest, "orderByCol"));
		renderURL.setParameter(
			"orderByType",
			ParamUtil.getString(_httpServletRequest, "orderByType", "asc"));
		renderURL.setParameter(
			"searchContainerId",
			ParamUtil.getString(_httpServletRequest, "searchContainerId"));

		return renderURL;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PageContext _pageContext;
	private final String _portletNamespace;

}