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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
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
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.display.context.util.SharingMenuItemFactory;
import com.liferay.sharing.filter.SharedWithMeFilterItem;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.util.comparator.SharingEntryModifiedDateComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class SharedWithMeViewDisplayContext {

	public SharedWithMeViewDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request, ResourceBundle resourceBundle,
		SharingEntryLocalService sharingEntryLocalService,
		Function<SharingEntry, SharingEntryInterpreter>
			sharingEntryInterpreterFunction,
		List<SharedWithMeFilterItem> sharedWithMeFilterItems,
		SharingMenuItemFactory sharingMenuItemFactory) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_request = request;
		_sharingEntryLocalService = sharingEntryLocalService;
		_sharingEntryInterpreterFunction = sharingEntryInterpreterFunction;
		_sharedWithMeFilterItems = sharedWithMeFilterItems;
		_sharingMenuItemFactory = sharingMenuItemFactory;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);
		_themeDisplay = (ThemeDisplay)request.getAttribute(
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
		return ParamUtil.getString(_request, "className");
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

	public List<SharedWithMeFilterItem> getSharedWithMeFilterItems() {
		return _sharedWithMeFilterItems;
	}

	public Menu getSharingEntryMenu(SharingEntry sharingEntry)
		throws PortalException {

		Menu menu = new Menu();

		menu.setDirection("left-side");
		menu.setMarkupView("lexicon");

		List<MenuItem> menuItems = new ArrayList<>(2);

		if (hasEditPermission(
				sharingEntry.getClassNameId(), sharingEntry.getClassPK())) {

			menuItems.add(_createEditMenuItem(sharingEntry));
		}

		if (sharingEntry.isShareable()) {
			menuItems.add(
				_sharingMenuItemFactory.createShareMenuItem(
					sharingEntry.getClassName(), sharingEntry.getClassPK(),
					_request));
		}

		menu.setMenuItems(menuItems);
		menu.setScroll(false);
		menu.setShowWhenSingleIcon(true);

		return menu;
	}

	public String getSortingOrder() {
		return ParamUtil.getString(_request, "orderByType", "asc");
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

	public boolean hasEditPermission(long classNameId, long classPK) {
		List<SharingEntry> toUserSharingEntries =
			_sharingEntryLocalService.getToUserClassPKSharingEntries(
				_themeDisplay.getUserId(), classNameId, classPK);

		for (SharingEntry sharingEntry : toUserSharingEntries) {
			if (sharingEntry.hasSharingPermission(SharingEntryAction.UPDATE)) {
				return true;
			}
		}

		return false;
	}

	public void populateResults(SearchContainer<SharingEntry> searchContainer) {
		long classNameId = 0;

		String className = ParamUtil.getString(_request, "className");

		if (Validator.isNotNull(className)) {
			classNameId = ClassNameLocalServiceUtil.getClassNameId(className);
		}

		int total =
			_sharingEntryLocalService.getUniqueToUserSharingEntriesCount(
				_themeDisplay.getUserId(), classNameId);

		searchContainer.setTotal(total);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getUniqueToUserSharingEntries(
				_themeDisplay.getUserId(), classNameId,
				searchContainer.getStart(), searchContainer.getEnd(),
				new SharingEntryModifiedDateComparator(
					Objects.equals(getSortingOrder(), "asc")));

		searchContainer.setResults(sharingEntries);
	}

	private MenuItem _createEditMenuItem(SharingEntry sharingEntry)
		throws PortalException {

		try {
			URLMenuItem urlMenuItem = new URLMenuItem();

			Map<String, Object> data = new HashMap<>(3);

			data.put("destroyOnHide", true);
			data.put(
				"id",
				HtmlUtil.escape(_liferayPortletResponse.getNamespace()) +
					"editAsset");
			data.put(
				"title",
				LanguageUtil.format(
					_request, "edit-x", HtmlUtil.escape(getTitle(sharingEntry)),
					false));

			urlMenuItem.setData(data);

			urlMenuItem.setLabel(LanguageUtil.get(_request, "edit"));
			urlMenuItem.setMethod("get");

			PortletURL editPortletURL = _getURLEdit(
				sharingEntry, _liferayPortletRequest, _liferayPortletResponse);

			editPortletURL.setWindowState(LiferayWindowState.POP_UP);

			editPortletURL.setParameter(
				"hideDefaultSuccessMessage", Boolean.TRUE.toString());
			editPortletURL.setParameter("showHeader", Boolean.FALSE.toString());

			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			PortletURL redirectURL =
				_liferayPortletResponse.createLiferayPortletURL(
					_themeDisplay.getPlid(), portletDisplay.getId(),
					PortletRequest.RENDER_PHASE, false);

			editPortletURL.setParameter("redirect", redirectURL.toString());

			urlMenuItem.setURL(editPortletURL.toString());

			urlMenuItem.setUseDialog(true);

			return urlMenuItem;
		}
		catch (WindowStateException wse) {
			throw new SystemException(wse);
		}
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLUtil.clone(
			_currentURLObj, _liferayPortletResponse);

		return sortingURL;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {

			{
				String className = ParamUtil.getString(_request, "className");

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(Validator.isNull(className));

							PortletURL viewAllClassNamesURL =
								PortletURLUtil.clone(
									_currentURLObj, _liferayPortletResponse);

							viewAllClassNamesURL.setParameter(
								"mvcRenderCommandName", "/shared_with_me/view");
							viewAllClassNamesURL.setParameter(
								"className", (String)null);

							dropdownItem.setHref(viewAllClassNamesURL);

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "all"));
						}));
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "openAssetTypesSelector");
							dropdownItem.setActive(
								Validator.isNotNull(className));
							dropdownItem.setLabel(
								_getClassNameLabel(className));
						}));
			}

			private String _getClassNameLabel(String className) {
				String label = LanguageUtil.get(_request, "asset-types");

				if (Validator.isNotNull(className)) {
					for (SharedWithMeFilterItem sharedWithMeFilterItem :
							_sharedWithMeFilterItems) {

						if (className.equals(
								sharedWithMeFilterItem.getClassName())) {

							label = sharedWithMeFilterItem.getLabel(
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
			_request, "orderByCol", "sharedDate");

		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(
								"sharedDate".equals(orderByCol));
							dropdownItem.setHref(
								_getCurrentSortingURL(), "orderByCol",
								"sharedDate");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "shared-date"));
						}));
			}
		};
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

		return sharingEntryEditRenderer.getURLEdit(
			sharingEntry, liferayPortletRequest, liferayPortletResponse);
	}

	private final PortletURL _currentURLObj;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final List<SharedWithMeFilterItem> _sharedWithMeFilterItems;
	private final Function<SharingEntry, SharingEntryInterpreter>
		_sharingEntryInterpreterFunction;
	private final SharingEntryLocalService _sharingEntryLocalService;
	private final SharingMenuItemFactory _sharingMenuItemFactory;
	private final ThemeDisplay _themeDisplay;

}