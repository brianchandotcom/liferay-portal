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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
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
import com.liferay.sharing.web.internal.filter.SharedAssetsFilterItemTracker;
import com.liferay.sharing.web.internal.item.selector.SharedAssetsFilterItemItemSelectorCriterion;
import com.liferay.sharing.web.internal.servlet.taglib.ui.SharingEntryMenuItemContributorRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class ViewSharedAssetsDisplayContext {

	public ViewSharedAssetsDisplayContext(
		GroupLocalService groupLocalService, ItemSelector itemSelector,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SharedAssetsFilterItemTracker sharedAssetsFilterItemTracker,
		SharingConfigurationFactory sharingConfigurationFactory,
		Function<SharingEntry, SharingEntryInterpreter>
			sharingEntryInterpreterFunction,
		SharingEntryLocalService sharingEntryLocalService,
		SharingEntryMenuItemContributorRegistry
			sharingEntryMenuItemContributorRegistry,
		SharingMenuItemFactory sharingMenuItemFactory,
		SharingPermission sharingPermission) {

		_groupLocalService = groupLocalService;
		_itemSelector = itemSelector;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_sharedAssetsFilterItemTracker = sharedAssetsFilterItemTracker;
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
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public NavigationItemList getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(_isIncoming());

				navigationItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setParameter(
						"incoming", true
					).buildPortletURL());

				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "shared-with-me"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(!_isIncoming());

				navigationItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setParameter(
						"incoming", false
					).buildPortletURL());

				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "shared-by-me"));
			}
		).build();
	}

	public PortletURL getSelectAssetTypeURL() {
		return _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest),
			_liferayPortletResponse.getNamespace() + "selectAssetType",
			new SharedAssetsFilterItemItemSelectorCriterion());
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
		return PortletURLBuilder.create(
			_getCurrentSortingURL()
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = getSortingOrder();

				if (Objects.equals(orderByType, "asc")) {
					return "desc";
				}

				return "asc";
			}
		).buildPortletURL();
	}

	public String getTitle(SharingEntry sharingEntry) {
		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return StringPool.BLANK;
		}

		return HtmlUtil.escape(sharingEntryInterpreter.getTitle(sharingEntry));
	}

	public boolean isVisible(SharingEntry sharingEntry) throws PortalException {
		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if ((sharingEntryInterpreter == null) ||
			!sharingEntryInterpreter.isVisible(sharingEntry)) {

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

		PortletURL editPortletURL = _getURLEdit(
			sharingEntry, _liferayPortletRequest, _liferayPortletResponse);

		if (editPortletURL == null) {
			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(LanguageUtil.get(_httpServletRequest, "edit"));
		urlMenuItem.setMethod("get");

		urlMenuItem.setURL(editPortletURL.toString());

		return urlMenuItem;
	}

	private String _getClassNameLabel(String className) {
		if (Validator.isNotNull(className)) {
			SharedAssetsFilterItem sharedAssetsFilterItem =
				_sharedAssetsFilterItemTracker.getSharedAssetsFilterItem(
					className);

			if (sharedAssetsFilterItem != null) {
				return sharedAssetsFilterItem.getLabel(
					_themeDisplay.getLocale());
			}
		}

		return LanguageUtil.get(_httpServletRequest, "asset-types");
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		return PortletURLUtil.clone(_currentURLObj, _liferayPortletResponse);
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		String className = ParamUtil.getString(
			_httpServletRequest, "className");

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(Validator.isNull(className));

				dropdownItem.setHref(
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse)
					).setParameter(
						"className", (String)null
					).buildPortletURL());

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "all"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "openAssetTypesSelector");
				dropdownItem.setActive(Validator.isNotNull(className));
				dropdownItem.setLabel(_getClassNameLabel(className));
			}
		).build();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				String orderByCol = ParamUtil.getString(
					_httpServletRequest, "orderByCol", "sharedDate");

				dropdownItem.setActive(
					Objects.equals(orderByCol, "sharedDate"));

				dropdownItem.setHref(
					_getCurrentSortingURL(), "orderByCol", "sharedDate");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "shared-date"));
			}
		).build();
	}

	private PortletURL _getURLEdit(
			SharingEntry sharingEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

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
			"redirect", PortalUtil.getCurrentURL(_liferayPortletRequest));

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
	private final ItemSelector _itemSelector;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SharedAssetsFilterItemTracker _sharedAssetsFilterItemTracker;
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