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

package com.liferay.sharing.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.display.context.util.SharingMenuItemFactory;
import com.liferay.sharing.filter.SharedAssetsFilterItem;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.servlet.taglib.ui.SharingEntryMenuItemContributor;
import com.liferay.sharing.util.comparator.SharingEntryModifiedDateComparator;
import com.liferay.sharing.web.internal.servlet.taglib.ui.SharingEntryMenuItemContributorRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class SharedAssetsViewDisplayContext {

	public SharedAssetsViewDisplayContext(
		GroupLocalService groupLocalService,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		List<SharedAssetsFilterItem> sharedAssetsFilterItems,
		SharingConfigurationFactory sharingConfigurationFactory,
		Function<SharingEntry, SharingEntryInterpreter>
			sharingEntryInterpreterFunction,
		SharingEntryLocalService sharingEntryLocalService,
		SharingEntryMenuItemContributorRegistry
			sharingEntryMenuItemContributorRegistry,
		SharingMenuItemFactory sharingMenuItemFactory,
		SharingPermission sharingPermission) {

		_groupLocalService = groupLocalService;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_sharedAssetsFilterItems = sharedAssetsFilterItems;
		_sharingConfigurationFactory = sharingConfigurationFactory;
		_sharingEntryInterpreterFunction = sharingEntryInterpreterFunction;
		_sharingEntryLocalService = sharingEntryLocalService;
		_sharingEntryMenuItemContributorRegistry =
			sharingEntryMenuItemContributorRegistry;
		_sharingMenuItemFactory = sharingMenuItemFactory;
		_sharingPermission = sharingPermission;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAssetTypeTitle(SharingEntry sharingEntry)
		throws PortalException {

		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return StringPool.BLANK;
		}

		return sharingEntryInterpreter.getAssetTypeTitle(
			sharingEntry, _themeDisplay.getLocale());
	}

	public String getClassName() {
		return ParamUtil.getString(_httpServletRequest, "className");
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
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "order-by"));
					});
			}
		};
	}

	public NavigationItemList getNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(_isIncoming());

						PortletURL sharedWithMeURL =
							_liferayPortletResponse.createRenderURL();

						sharedWithMeURL.setParameter(
							"incoming", Boolean.TRUE.toString());

						navigationItem.setHref(sharedWithMeURL);

						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "shared-with-me"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(!_isIncoming());

						PortletURL sharedByMeURL =
							_liferayPortletResponse.createRenderURL();

						sharedByMeURL.setParameter(
							"incoming", Boolean.FALSE.toString());

						navigationItem.setHref(sharedByMeURL);

						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "shared-by-me"));
					});
			}
		};
	}

	public List<SharedAssetsFilterItem> getSharedAssetsFilterItems() {
		return _sharedAssetsFilterItems;
	}

	public Menu getSharingEntryMenu(SharingEntry sharingEntry)
		throws PortalException {

		Menu menu = new Menu();

		menu.setDirection("left-side");
		menu.setMarkupView("lexicon");
		menu.setMessage(LanguageUtil.get(_httpServletRequest, "actions"));
		menu.setScroll(false);
		menu.setShowWhenSingleIcon(true);

		if (!isVisible(sharingEntry)) {
			menu.setMenuItems(Collections.emptyList());

			return menu;
		}

		List<MenuItem> menuItems = new ArrayList<>();

		if (_hasEditPermission(
				sharingEntry.getClassNameId(), sharingEntry.getClassPK())) {

			MenuItem menuItem = _createEditMenuItem(sharingEntry);

			if (menuItem != null) {
				menuItems.add(menuItem);
			}
		}

		if (sharingEntry.isShareable()) {
			menuItems.add(
				_sharingMenuItemFactory.createShareMenuItem(
					sharingEntry.getClassName(), sharingEntry.getClassPK(),
					_httpServletRequest));
		}

		boolean containsManageCollaboratorsPermission =
			_sharingPermission.containsManageCollaboratorsPermission(
				_themeDisplay.getPermissionChecker(),
				sharingEntry.getClassNameId(), sharingEntry.getClassPK(),
				_themeDisplay.getScopeGroupId());

		if (containsManageCollaboratorsPermission) {
			menuItems.add(
				_sharingMenuItemFactory.createManageCollaboratorsMenuItem(
					sharingEntry.getClassName(), sharingEntry.getClassPK(),
					_httpServletRequest));
		}

		SharingEntryMenuItemContributor sharingEntryMenuItemContributor =
			_sharingEntryMenuItemContributorRegistry.
				getSharingEntryMenuItemContributor(
					sharingEntry.getClassNameId());

		menuItems.addAll(
			sharingEntryMenuItemContributor.getSharingEntryMenuItems(
				sharingEntry, _themeDisplay));

		menu.setMenuItems(menuItems);

		return menu;
	}

	public String getSortingOrder() {
		return ParamUtil.getString(_httpServletRequest, "orderByType", "asc");
	}

	public PortletURL getSortingURL() throws PortletException {
		String orderByType = getSortingOrder();

		PortletURL sortingURL = _getCurrentSortingURL();

		sortingURL.setParameter(
			"orderByType", Objects.equals(orderByType, "asc") ? "desc" : "asc");

		return sortingURL;
	}

	public String getTitle(SharingEntry sharingEntry) {
		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return StringPool.BLANK;
		}

		return sharingEntryInterpreter.getTitle(sharingEntry);
	}

	public boolean isVisible(SharingEntry sharingEntry) throws PortalException {
		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return false;
		}

		if (!sharingEntryInterpreter.isVisible(sharingEntry)) {
			return false;
		}

		SharingConfiguration groupSharingConfiguration =
			_sharingConfigurationFactory.getGroupSharingConfiguration(
				_groupLocalService.getGroup(sharingEntry.getGroupId()));

		if (!groupSharingConfiguration.isEnabled()) {
			return false;
		}

		return true;
	}

	public void populateResults(SearchContainer<SharingEntry> searchContainer) {
		long classNameId = 0;

		String className = ParamUtil.getString(
			_httpServletRequest, "className");

		if (Validator.isNotNull(className)) {
			classNameId = ClassNameLocalServiceUtil.getClassNameId(className);
		}

		if (_isIncoming()) {
			int total = _sharingEntryLocalService.getToUserSharingEntriesCount(
				_themeDisplay.getUserId(), classNameId);

			searchContainer.setTotal(total);

			List<SharingEntry> sharingEntries =
				_sharingEntryLocalService.getToUserSharingEntries(
					_themeDisplay.getUserId(), classNameId,
					searchContainer.getStart(), searchContainer.getEnd(),
					new SharingEntryModifiedDateComparator(
						Objects.equals(getSortingOrder(), "asc")));

			searchContainer.setResults(sharingEntries);
		}
		else {
			int total =
				_sharingEntryLocalService.getFromUserSharingEntriesCount(
					_themeDisplay.getUserId(), classNameId);

			searchContainer.setTotal(total);

			List<SharingEntry> sharingEntries =
				_sharingEntryLocalService.getFromUserSharingEntries(
					_themeDisplay.getUserId(), classNameId,
					searchContainer.getStart(), searchContainer.getEnd(),
					new SharingEntryModifiedDateComparator(
						Objects.equals(getSortingOrder(), "asc")));

			searchContainer.setResults(sharingEntries);
		}
	}

	private MenuItem _createEditMenuItem(SharingEntry sharingEntry)
		throws PortalException {

		try {
			PortletURL editPortletURL = _getURLEdit(
				sharingEntry, _liferayPortletRequest, _liferayPortletResponse);

			if (editPortletURL == null) {
				return null;
			}

			URLMenuItem urlMenuItem = new URLMenuItem();

			Map<String, Object> data = new HashMap<>();

			data.put("destroyOnHide", true);
			data.put(
				"id",
				HtmlUtil.escape(_liferayPortletResponse.getNamespace()) +
					"editAsset");
			data.put(
				"title",
				LanguageUtil.format(
					_httpServletRequest, "edit-x",
					HtmlUtil.escape(getTitle(sharingEntry)), false));

			urlMenuItem.setData(data);

			urlMenuItem.setLabel(LanguageUtil.get(_httpServletRequest, "edit"));
			urlMenuItem.setMethod("get");

			urlMenuItem.setURL(editPortletURL.toString());

			urlMenuItem.setUseDialog(true);

			return urlMenuItem;
		}
		catch (WindowStateException wse) {
			throw new SystemException(wse);
		}
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		return PortletURLUtil.clone(_currentURLObj, _liferayPortletResponse);
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				String className = ParamUtil.getString(
					_httpServletRequest, "className");

				add(
					dropdownItem -> {
						dropdownItem.setActive(Validator.isNull(className));

						PortletURL viewAllClassNamesURL = PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse);

						viewAllClassNamesURL.setParameter(
							"mvcRenderCommandName", "/shared_assets/view");
						viewAllClassNamesURL.setParameter(
							"className", (String)null);

						dropdownItem.setHref(viewAllClassNamesURL);

						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "all"));
					});
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "openAssetTypesSelector");
						dropdownItem.setActive(Validator.isNotNull(className));
						dropdownItem.setLabel(_getClassNameLabel(className));
					});
			}

			private String _getClassNameLabel(String className) {
				String label = LanguageUtil.get(
					_httpServletRequest, "asset-types");

				if (Validator.isNotNull(className)) {
					for (SharedAssetsFilterItem sharedAssetsFilterItem :
							_sharedAssetsFilterItems) {

						if (className.equals(
								sharedAssetsFilterItem.getClassName())) {

							label = sharedAssetsFilterItem.getLabel(
								_themeDisplay.getLocale());

							break;
						}
					}
				}

				return label;
			}

		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "sharedDate");

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(orderByCol, "sharedDate"));
						dropdownItem.setHref(
							_getCurrentSortingURL(), "orderByCol",
							"sharedDate");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "shared-date"));
					});
			}
		};
	}

	private PortletURL _getURLEdit(
			SharingEntry sharingEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException, WindowStateException {

		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return null;
		}

		SharingEntryEditRenderer sharingEntryEditRenderer =
			sharingEntryInterpreter.getSharingEntryEditRenderer();

		PortletURL portletURL = sharingEntryEditRenderer.getURLEdit(
			sharingEntry, liferayPortletRequest, liferayPortletResponse);

		if (portletURL == null) {
			return null;
		}

		portletURL.setParameter(
			"hideDefaultSuccessMessage", Boolean.TRUE.toString());

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		PortletURL redirectURL =
			_liferayPortletResponse.createLiferayPortletURL(
				_themeDisplay.getPlid(), portletDisplay.getId(),
				PortletRequest.RENDER_PHASE, false);

		redirectURL.setParameter(
			"mvcRenderCommandName",
			"/shared_assets/close_sharing_entry_edit_dialog");

		portletURL.setParameter("redirect", redirectURL.toString());

		portletURL.setParameter("showHeader", Boolean.FALSE.toString());

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL;
	}

	private boolean _hasEditPermission(long classNameId, long classPK) {
		SharingEntry sharingEntry = _sharingEntryLocalService.fetchSharingEntry(
			_themeDisplay.getUserId(), classNameId, classPK);

		if ((sharingEntry != null) &&
			sharingEntry.hasSharingPermission(SharingEntryAction.UPDATE)) {

			return true;
		}

		return false;
	}

	private boolean _isIncoming() {
		return ParamUtil.getBoolean(_httpServletRequest, "incoming", true);
	}

	private final PortletURL _currentURLObj;
	private final GroupLocalService _groupLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final List<SharedAssetsFilterItem> _sharedAssetsFilterItems;
	private final SharingConfigurationFactory _sharingConfigurationFactory;
	private final Function<SharingEntry, SharingEntryInterpreter>
		_sharingEntryInterpreterFunction;
	private final SharingEntryLocalService _sharingEntryLocalService;
	private final SharingEntryMenuItemContributorRegistry
		_sharingEntryMenuItemContributorRegistry;
	private final SharingMenuItemFactory _sharingMenuItemFactory;
	private final SharingPermission _sharingPermission;
	private final ThemeDisplay _themeDisplay;

}