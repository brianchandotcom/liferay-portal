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

package com.liferay.item.selector.taglib.internal.display.context;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.item.selector.taglib.servlet.taglib.RepositoryEntryBrowserTag;
import com.liferay.item.selector.taglib.servlet.taglib.util.RepositoryEntryBrowserTagUtil;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class ItemSelectorRepositoryEntryManagementToolbarDisplayContext {

	public ItemSelectorRepositoryEntryManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		RepositoryEntryBrowserDisplayContext
			repositoryEntryBrowserDisplayContext) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_repositoryEntryBrowserDisplayContext =
			repositoryEntryBrowserDisplayContext;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			liferayPortletRequest);
	}

	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			_getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	public CreationMenu getCreationMenu() {
		PortletToolbarContributor dlPortletToolbarContributor =
			_dlPortletToolbarContributorSnapshot.get();

		Folder folder = _getFolder();

		if (folder != null) {
			_liferayPortletRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FOLDER, folder);
		}

		List<Menu> menus = dlPortletToolbarContributor.getPortletTitleMenus(
			_liferayPortletRequest, _liferayPortletResponse);

		if (menus.isEmpty()) {
			return null;
		}

		CreationMenu creationMenu = new CreationMenu();

		creationMenu.setItemsIconAlignment("left");

		Set<String> allowedCreationMenuUIItemKeys =
			_getAllowedCreationMenuUIItemKeys();

		if (SetUtil.isEmpty(allowedCreationMenuUIItemKeys)) {
			return creationMenu;
		}

		for (Menu menu : menus) {
			List<URLMenuItem> urlMenuItems =
				(List<URLMenuItem>)(List<?>)menu.getMenuItems();

			for (URLMenuItem urlMenuItem : urlMenuItems) {
				if (allowedCreationMenuUIItemKeys.contains(
						urlMenuItem.getKey())) {

					creationMenu.addDropdownItem(
						dropdownItem -> {
							dropdownItem.setData(urlMenuItem.getData());
							dropdownItem.setHref(urlMenuItem.getURL());
							dropdownItem.setIcon(urlMenuItem.getIcon());
							dropdownItem.setLabel(urlMenuItem.getLabel());
							dropdownItem.setSeparator(
								urlMenuItem.hasSeparator());
						});
				}
			}
		}

		return creationMenu;
	}

	public PortletURL getCurrentSortingURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(_getPortletURL(), _liferayPortletResponse)
		).setParameter(
			"orderByCol", _getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).setParameter(
			"scope",
			() -> {
				if (_repositoryEntryBrowserDisplayContext.
						isSearchEverywhere()) {

					return "everywhere";
				}

				return null;
			}
		).buildPortletURL();
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			this::_isShowScopeFilter,
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.setActive(
								_repositoryEntryBrowserDisplayContext.
									isSearchEverywhere());
							dropdownItem.setHref(
								_getPortletURL(), "scope", "everywhere");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "everywhere"));
						}
					).add(
						dropdownItem -> {
							dropdownItem.setActive(
								!_repositoryEntryBrowserDisplayContext.
									isSearchEverywhere());
							dropdownItem.setHref(
								_getPortletURL(), "scope", "current");
							dropdownItem.setLabel(_getCurrentScopeLabel());
						}
					).build());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "filter-by-location"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public List<LabelItem> getFilterLabelItems() {
		String scope = ParamUtil.getString(_httpServletRequest, "scope");

		if (Validator.isNull(scope) || scope.equals("current") ||
			!_isShowScopeFilter()) {

			return null;
		}

		return LabelItemListBuilder.add(
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						getCurrentSortingURL()
					).setParameter(
						"scope", (String)null
					).buildString());

				labelItem.setCloseable(true);
				labelItem.setLabel(
					String.format(
						"%s: %s",
						LanguageUtil.get(_httpServletRequest, "scope"),
						_getScopeLabel(scope)));
			}
		).build();
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = RepositoryEntryBrowserTagUtil.getOrderByType(
			_httpServletRequest, _portalPreferences);

		return _orderByType;
	}

	public PortletURL getSearchURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(_currentURLObj, _liferayPortletResponse)
		).setKeywords(
			(String)null
		).setParameter(
			"resetCur", true
		).buildPortletURL();
	}

	public PortletURL getSortingURL() throws PortletException {
		return PortletURLBuilder.create(
			getCurrentSortingURL()
		).setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc"
		).buildPortletURL();
	}

	public ViewTypeItemList getViewTypes() throws PortletException {
		PortletURL displayStyleURL = PortletURLUtil.clone(
			getCurrentSortingURL(), _liferayPortletResponse);

		return new ViewTypeItemList(displayStyleURL, _getDisplayStyle()) {
			{
				if (ArrayUtil.contains(_getDisplayStyles(), "icon")) {
					addCardViewTypeItem();
				}

				if (ArrayUtil.contains(_getDisplayStyles(), "descriptive")) {
					addListViewTypeItem();
				}

				if (ArrayUtil.contains(_getDisplayStyles(), "list")) {
					addTableViewTypeItem();
				}
			}
		};
	}

	public boolean isDisabled() {
		return false;
	}

	public boolean isShowCreationMenu() {
		Set<String> allowedCreationMenuUIItemKeys =
			_getAllowedCreationMenuUIItemKeys();

		if ((allowedCreationMenuUIItemKeys == null) ||
			!allowedCreationMenuUIItemKeys.isEmpty()) {

			return true;
		}

		return false;
	}

	private Set<String> _getAllowedCreationMenuUIItemKeys() {
		Set<String> allowedCreationMenuUIItemKeys =
			(Set)_httpServletRequest.getAttribute(
				"liferay-item-selector:repository-entry-browser:" +
					"allowedCreationMenuUIItemKeys");

		if (allowedCreationMenuUIItemKeys == null) {
			return SetUtil.fromArray(
				DLUIItemKeys.ADD_FOLDER, DLUIItemKeys.UPLOAD);
		}

		return allowedCreationMenuUIItemKeys;
	}

	private String _getCurrentScopeLabel() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (group.isSite()) {
			return LanguageUtil.get(_httpServletRequest, "current-site");
		}

		if (group.isOrganization()) {
			return LanguageUtil.get(
				_httpServletRequest, "current-organization");
		}

		if (group.isDepot()) {
			return LanguageUtil.get(
				_httpServletRequest, "current-asset-library");
		}

		return LanguageUtil.get(_httpServletRequest, "current-scope");
	}

	private String _getDisplayStyle() {
		return GetterUtil.getString(
			_httpServletRequest.getAttribute(
				"liferay-item-selector:repository-entry-browser:displayStyle"));
	}

	private String[] _getDisplayStyles() {
		return RepositoryEntryBrowserTag.DISPLAY_STYLES;
	}

	private Folder _getFolder() {
		long folderId = GetterUtil.getLong(
			_httpServletRequest.getAttribute(
				"liferay-item-selector:repository-entry-browser:folderId"));

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return null;
		}

		Folder folder = null;

		try {
			folder = DLAppLocalServiceUtil.getFolder(folderId);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return folder;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = RepositoryEntryBrowserTagUtil.getOrderByCol(
			_httpServletRequest, _portalPreferences);

		return _orderByCol;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				Map<String, String> orderColumnsMap = HashMapBuilder.put(
					"modifiedDate", "modified-date"
				).put(
					"size", "size"
				).put(
					"title", "title"
				).build();

				for (Map.Entry<String, String> orderByColEntry :
						orderColumnsMap.entrySet()) {

					add(
						dropdownItem -> {
							String orderByCol = orderByColEntry.getKey();

							dropdownItem.setActive(
								orderByCol.equals(_getOrderByCol()));
							dropdownItem.setHref(
								getCurrentSortingURL(), "orderByCol",
								orderByCol);

							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									orderByColEntry.getValue()));
						});
				}
			}
		};
	}

	private PortletURL _getPortletURL() {
		return (PortletURL)_httpServletRequest.getAttribute(
			"liferay-item-selector:repository-entry-browser:portletURL");
	}

	private String _getScopeLabel(String scope) {
		if (scope.equals("everywhere")) {
			return LanguageUtil.get(_httpServletRequest, "everywhere");
		}

		return _getCurrentScopeLabel();
	}

	private boolean _isShowScopeFilter() {
		if (_showScopeFilter != null) {
			return _showScopeFilter;
		}

		_showScopeFilter = GetterUtil.getBoolean(
			_httpServletRequest.getAttribute(
				"liferay-item-selector:repository-entry-browser:" +
					"showBreadcrumb"));

		return _showScopeFilter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ItemSelectorRepositoryEntryManagementToolbarDisplayContext.class);

	private static final Snapshot<PortletToolbarContributor>
		_dlPortletToolbarContributorSnapshot = new Snapshot<>(
			ItemSelectorRepositoryEntryManagementToolbarDisplayContext.class,
			PortletToolbarContributor.class,
			"(javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY + ")");

	private final PortletURL _currentURLObj;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final RepositoryEntryBrowserDisplayContext
		_repositoryEntryBrowserDisplayContext;
	private Boolean _showScopeFilter;

}