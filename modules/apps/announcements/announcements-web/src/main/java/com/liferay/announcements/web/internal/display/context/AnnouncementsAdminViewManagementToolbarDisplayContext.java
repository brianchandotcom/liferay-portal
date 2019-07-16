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

package com.liferay.announcements.web.internal.display.context;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.announcements.service.permission.AnnouncementsEntryPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class AnnouncementsAdminViewManagementToolbarDisplayContext {

	public AnnouncementsAdminViewManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		SearchContainer searchContainer) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_httpServletRequest = httpServletRequest;
		_searchContainer = searchContainer;

		_announcementsAdminViewDisplayContext =
			new DefaultAnnouncementsAdminViewDisplayContext(
				_httpServletRequest);
		_currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteEntries");
						dropdownItem.setIcon("times");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public List<String> getAvailableActions(
			AnnouncementsEntry announcementsEntry)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (AnnouncementsEntryPermission.contains(
				themeDisplay.getPermissionChecker(), announcementsEntry,
				ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		return availableActions;
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _liferayPortletResponse.createRenderURL();

		clearResultsURL.setParameter("navigation", _getNavigation());

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addDropdownItem(
					dropdownItem -> {
						PortletURL addEntryURL =
							_liferayPortletResponse.createRenderURL();

						addEntryURL.setParameter(
							"mvcRenderCommandName",
							"/announcements/edit_entry");
						addEntryURL.setParameter(
							"redirect",
							PortalUtil.getCurrentURL(_httpServletRequest));

						String navigation = _getNavigation();

						addEntryURL.setParameter(
							"alert",
							String.valueOf(
								String.valueOf(navigation.equals("alerts"))));

						addEntryURL.setParameter(
							"distributionScope", _getDistributionScope());

						dropdownItem.setHref(addEntryURL);

						String label = null;

						if (navigation.equals("alerts")) {
							label = "add-alert";
						}
						else {
							label = "add-announcement";
						}

						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, label));
					});
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
							LanguageUtil.get(
								_httpServletRequest, "filter-by-navigation"));
					});
			}
		};
	}

	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				String distributionScope = _getDistributionScope();

				if (Validator.isNotNull(distributionScope)) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								_currentURLObj, _liferayPortletResponse);

							removeLabelURL.setParameter(
								"distributionScope", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							labelItem.setLabel(
								_announcementsAdminViewDisplayContext.
									getCurrentDistributionScopeLabel());
						});
				}
			}
		};
	}

	public int getTotal() {
		return _searchContainer.getTotal();
	}

	public boolean isDisabled() {
		return false;
	}

	private String _getDistributionScope() {
		return ParamUtil.getString(_httpServletRequest, "distributionScope");
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems()
		throws Exception {

		return new DropdownItemList() {
			{
				PortletURL navigationURL = PortletURLUtil.clone(
					_currentURLObj, _liferayPortletResponse);

				String currentDistributionScopeLabel =
					_announcementsAdminViewDisplayContext.
						getCurrentDistributionScopeLabel();

				Map<String, String> distributionScopes =
					_announcementsAdminViewDisplayContext.
						getDistributionScopes();

				for (Map.Entry<String, String> distributionScopeEntry :
						distributionScopes.entrySet()) {

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								currentDistributionScopeLabel.equals(
									distributionScopeEntry.getKey()));
							dropdownItem.setHref(
								navigationURL, "distributionScope",
								distributionScopeEntry.getValue());
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									distributionScopeEntry.getKey()));
						});
				}
			}
		};
	}

	private String _getNavigation() {
		return ParamUtil.getString(
			_httpServletRequest, "navigation", "announcements");
	}

	private final AnnouncementsAdminViewDisplayContext
		_announcementsAdminViewDisplayContext;
	private final PortletURL _currentURLObj;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SearchContainer _searchContainer;

}