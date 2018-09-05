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

package com.liferay.bookmarks.web.internal.display.context;

import com.liferay.bookmarks.configuration.BookmarksGroupServiceOverriddenConfiguration;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.constants.BookmarksWebKeys;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.bookmarks.web.internal.portlet.toolbar.contributor.BookmarksPortletToolbarContributor;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class BookmarksManagementToolbarDisplayContext {

	public BookmarksManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request,
		BookmarksGroupServiceOverriddenConfiguration
			bookmarksGroupServiceOverriddenConfiguration,
		PortalPreferences portalPreferences, TrashHelper trashHelper) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_request = request;
		_bookmarksGroupServiceOverriddenConfiguration =
			bookmarksGroupServiceOverriddenConfiguration;
		_portalPreferences = portalPreferences;
		_trashHelper = trashHelper;

		_folderId = GetterUtil.getLong(
			(String)_request.getAttribute("view.jsp-folderId"));

		_searchContainer = (SearchContainer)_request.getAttribute(
			"view.jsp-bookmarksSearchContainer");

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.putData("action", "deleteEntries");

							if (_trashHelper.isTrashEnabled(
									_themeDisplay.getScopeGroupId())) {

								dropdownItem.setIcon("trash");
								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, "move-to-recycle-bin"));
							}
							else {
								dropdownItem.setIcon("times");
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "delete"));
							}

							dropdownItem.setQuickAction(true);
						}));
			}
		};
	}

	public String getClearResultsURL() {
		return String.valueOf(_getPortletURL());
	}

	public CreationMenu getCreationMenu() {
		String portletName = _liferayPortletRequest.getPortletName();

		if (!portletName.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN)) {
			return null;
		}

		BookmarksPortletToolbarContributor bookmarksPortletToolbarContributor =
			(BookmarksPortletToolbarContributor)_request.getAttribute(
				BookmarksWebKeys.BOOKMARKS_PORTLET_TOOLBAR_CONTRIBUTOR);

		List<Menu> menus =
			bookmarksPortletToolbarContributor.getPortletTitleMenus(
				_liferayPortletRequest, _liferayPortletResponse);

		if (menus.isEmpty()) {
			return null;
		}

		CreationMenu creationMenu = new CreationMenu();

		for (Menu menu : menus) {
			List<URLMenuItem> urlMenuItems =
				(List<URLMenuItem>)(List<?>)menu.getMenuItems();

			for (URLMenuItem urlMenuItem : urlMenuItems) {
				creationMenu.addDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(urlMenuItem.getURL());
						dropdownItem.setLabel(urlMenuItem.getLabel());
					});
			}
		}

		return creationMenu;
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
			}
		};
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = _liferayPortletResponse.createRenderURL();

		searchActionURL.setParameter("mvcRenderCommandName", "/bookmarks/view");
		searchActionURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(_request));
		searchActionURL.setParameter("folderId", String.valueOf(_folderId));

		return searchActionURL.toString();
	}

	public String getSearchContainerId() {
		return ParamUtil.getString(_request, "searchContainerId");
	}

	public int getTotalItems() {
		return _searchContainer.getTotal();
	}

	public ViewTypeItemList getViewTypes() {
		int curEntry = ParamUtil.getInteger(_request, "curEntry");
		int deltaEntry = ParamUtil.getInteger(_request, "deltaEntry");

		String displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = _portalPreferences.getValue(
				BookmarksPortletKeys.BOOKMARKS, "display-style", "descriptive");
		}

		String keywords = ParamUtil.getString(_request, "keywords");

		PortletURL displayStyleURL = _liferayPortletResponse.createRenderURL();

		if (Validator.isNull(keywords)) {
			if (_folderId ==
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				displayStyleURL.setParameter(
					"mvcRenderCommandName", "/bookmarks/view");
			}
			else {
				displayStyleURL.setParameter(
					"mvcRenderCommandName", "/bookmarks/view_folder");
				displayStyleURL.setParameter(
					"folderId", String.valueOf(_folderId));
			}
		}
		else {
			displayStyleURL.setParameter(
				"mvcRenderCommandName", "/bookmarks/view");
			displayStyleURL.setParameter("folderId", String.valueOf(_folderId));
		}

		displayStyleURL.setParameter(
			"navigation", HtmlUtil.escapeJS(_getNavigation()));

		if (curEntry > 0) {
			displayStyleURL.setParameter("curEntry", String.valueOf(curEntry));
		}

		if (deltaEntry > 0) {
			displayStyleURL.setParameter(
				"deltaEntry", String.valueOf(deltaEntry));
		}

		return new ViewTypeItemList(displayStyleURL, displayStyle) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean isDisabled() {
		int foldersAndEntriesCount =
			BookmarksFolderServiceUtil.getFoldersAndEntriesCount(
				_themeDisplay.getScopeGroupId(), _folderId);

		String navigation = _getNavigation();

		if ((foldersAndEntriesCount == 0) && navigation.equals("all")) {
			return true;
		}

		return false;
	}

	public boolean isSelectable() {
		return true;
	}

	public boolean isShowSearch() {
		return _bookmarksGroupServiceOverriddenConfiguration.
			showFoldersSearch();
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				String[] navigationKeys = null;

				if (_themeDisplay.isSignedIn()) {
					navigationKeys = new String[] {"all", "recent", "mine"};
				}
				else {
					navigationKeys = new String[] {"all", "recent"};
				}

				PortletURL portletURL = _getPortletURL();

				portletURL.setParameter(
					"folderId",
					String.valueOf(
						BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID));

				for (String navigationKey : navigationKeys) {
					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setActive(
									navigationKey.equals(_getNavigation()));

								PortletURL navigationURL = PortletURLUtil.clone(
									portletURL, _liferayPortletResponse);

								dropdownItem.setHref(
									navigationURL, "navigation", navigationKey);

								dropdownItem.setLabel(
									LanguageUtil.get(_request, navigationKey));
							}));
				}
			}
		};
	}

	private String _getNavigation() {
		return ParamUtil.getString(_request, "navigation", "all");
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("categoryId", StringPool.BLANK);

		int deltaEntry = ParamUtil.getInteger(_request, "deltaEntry");

		if (deltaEntry > 0) {
			portletURL.setParameter("deltaEntry", String.valueOf(deltaEntry));
		}

		portletURL.setParameter("folderId", String.valueOf(_folderId));
		portletURL.setParameter("tag", StringPool.BLANK);

		return portletURL;
	}

	private final BookmarksGroupServiceOverriddenConfiguration
		_bookmarksGroupServiceOverriddenConfiguration;
	private final long _folderId;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortalPreferences _portalPreferences;
	private final HttpServletRequest _request;
	private final SearchContainer _searchContainer;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}