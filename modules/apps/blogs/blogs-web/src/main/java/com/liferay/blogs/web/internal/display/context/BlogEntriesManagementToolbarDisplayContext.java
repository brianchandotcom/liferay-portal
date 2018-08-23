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

package com.liferay.blogs.web.internal.display.context;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.web.internal.security.permission.resource.BlogsPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class BlogEntriesManagementToolbarDisplayContext {

	public BlogEntriesManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request, PortletURL currentURLObj,
		TrashHelper trashHelper) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_request = request;
		_currentURLObj = currentURLObj;
		_trashHelper = trashHelper;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			liferayPortletRequest);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
					WebKeys.THEME_DISPLAY);

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.putData("action", "deleteEntries");

							boolean trashEnabled = _trashHelper.isTrashEnabled(
								themeDisplay.getScopeGroupId());

							dropdownItem.setIcon(
								trashEnabled ? "trash" : "times");

							String label = "delete";

							if (trashEnabled) {
								label = "move-to-recycle-bin";
							}

							dropdownItem.setLabel(
								LanguageUtil.get(_request, label));

							dropdownItem.setQuickAction(true);
						}));
			}
		};
	}

	public CreationMenu getCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!BlogsPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {

			return null;
		}

		CreationMenu creationMenu = new CreationMenu();

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					_liferayPortletResponse.createRenderURL(),
					"mvcRenderCommandName", "/blogs/edit_entry", "redirect",
					_currentURLObj.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(_request, "add-blog-entry"));
			});

		return creationMenu;
	}

	public String getDisplayStyle() {
		String displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = _portalPreferences.getValue(
				BlogsPortletKeys.BLOGS_ADMIN, "entries-display-style", "icon");
		}
		else {
			_portalPreferences.setValue(
				BlogsPortletKeys.BLOGS_ADMIN, "entries-display-style",
				displayStyle);

			_request.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		return displayStyle;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					SafeConsumer.ignore(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getFilterNavigationDropdownItems());
							dropdownGroupItem.setLabel(
								LanguageUtil.get(
									_request, "filter-by-navigation"));
						}));
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public String getOrderByCol() {
		return ParamUtil.getString(_request, "orderByCol", "title");
	}

	public String getOrderByType() {
		return ParamUtil.getString(_request, "orderByType", "desc");
	}

	public String getSearchActionURL() {
		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter("mvcRenderCommandName", "/blogs/view");

		String navigation = ParamUtil.getString(
			_request, "navigation", "entries");

		searchURL.setParameter("navigation", navigation);

		searchURL.setParameter("orderByCol", getOrderByCol());
		searchURL.setParameter("orderByType", getOrderByType());

		return searchURL.toString();
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL sortingURL = _getCurrentSortingURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL;
	}

	public ViewTypeItemList getViewTypes() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

		int delta = ParamUtil.getInteger(
			_request, SearchContainer.DEFAULT_DELTA_PARAM);

		if (delta > 0) {
			portletURL.setParameter("delta", String.valueOf(delta));
		}

		String orderByCol = ParamUtil.getString(
			_request, "orderByCol", "title");
		String orderByType = ParamUtil.getString(
			_request, "orderByType", "asc");

		portletURL.setParameter("orderBycol", orderByCol);
		portletURL.setParameter("orderByType", orderByType);

		String entriesNavigation = ParamUtil.getString(
			_request, "entriesNavigation", "all");

		portletURL.setParameter("entriesNavigation", entriesNavigation);

		int cur = ParamUtil.getInteger(
			_request, SearchContainer.DEFAULT_CUR_PARAM);

		if (cur > 0) {
			portletURL.setParameter("cur", String.valueOf(cur));
		}

		return new ViewTypeItemList(portletURL, getDisplayStyle()) {
			{
				addCardViewTypeItem();

				addListViewTypeItem();

				addTableViewTypeItem();
			}
		};
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLUtil.clone(
			_currentURLObj, _liferayPortletResponse);

		sortingURL.setParameter("mvcRenderCommandName", "/blogs/view");

		sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");

		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			sortingURL.setParameter("keywords", keywords);
		}

		return sortingURL;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		final String entriesNavigation = ParamUtil.getString(
			_request, "entriesNavigation", "all");

		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(
								entriesNavigation.equals("all"));

							PortletURL navigationPortletURL =
								PortletURLUtil.clone(
									_currentURLObj, _liferayPortletResponse);

							dropdownItem.setHref(
								navigationPortletURL, "entriesNavigation",
								"all");

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "all"));
						}));
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(
								entriesNavigation.equals("mine"));

							PortletURL navigationPortletURL =
								PortletURLUtil.clone(
									_currentURLObj, _liferayPortletResponse);

							dropdownItem.setHref(
								navigationPortletURL, "entriesNavigation",
								"mine");

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "mine"));
						}));
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(
								"title".equals(getOrderByCol()));
							dropdownItem.setHref(
								_getCurrentSortingURL(), "orderByCol", "title");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "title"));
						}));

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(
								"display-date".equals(getOrderByCol()));
							dropdownItem.setHref(
								_getCurrentSortingURL(), "orderByCol",
								"display-date");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "display-date"));
						}));
			}
		};
	}

	private final PortletURL _currentURLObj;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortalPreferences _portalPreferences;
	private final HttpServletRequest _request;
	private final TrashHelper _trashHelper;

}