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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.filter.SharedWithMeFilterItem;
import com.liferay.sharing.web.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.web.renderer.SharingEntryEditRenderer;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class SharedWithMeViewDisplayContext {

	public SharedWithMeViewDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request,
		SharingEntryLocalService sharingEntryLocalService,
		Function<SharingEntry, SharingEntryInterpreter<Object>>
			sharingEntryInterpreterFunction,
		List<SharedWithMeFilterItem> sharedWithMeFilterItems) {

		_liferayPortletResponse = liferayPortletResponse;
		_request = request;
		_sharingEntryLocalService = sharingEntryLocalService;
		_sharingEntryInterpreterFunction = sharingEntryInterpreterFunction;
		_sharedWithMeFilterItems = sharedWithMeFilterItems;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAssetTypeTitle(SharingEntry sharingEntry) {
		SharingEntryInterpreter<Object> sharingEntryInterpreter =
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
		SharingEntryInterpreter<Object> sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return StringPool.BLANK;
		}

		return sharingEntryInterpreter.getTitle(sharingEntry);
	}

	public PortletURL getURLEdit(
			SharingEntry sharingEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		SharingEntryInterpreter<Object> sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return null;
		}

		SharingEntryEditRenderer<Object> sharingEntryEditRenderer =
			sharingEntryInterpreter.getSharingEntryEditRenderer();

		return sharingEntryEditRenderer.getURLEdit(
			sharingEntryInterpreter.getEntry(sharingEntry),
			liferayPortletRequest, liferayPortletResponse);
	}

	public boolean hasEditPermission(SharingEntry sharingEntry) {
		return _sharingEntryLocalService.hasSharingPermission(
			sharingEntry, SharingEntryAction.UPDATE);
	}

	public void populateResults(SearchContainer<SharingEntry> searchContainer) {
		int total = _sharingEntryLocalService.countToUserSharingEntries(
			_themeDisplay.getUserId());

		searchContainer.setTotal(total);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(
				_themeDisplay.getUserId(), searchContainer.getStart(),
				searchContainer.getEnd());

		searchContainer.setResults(sharingEntries);
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

	private final PortletURL _currentURLObj;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final List<SharedWithMeFilterItem> _sharedWithMeFilterItems;
	private final Function<SharingEntry, SharingEntryInterpreter<Object>>
		_sharingEntryInterpreterFunction;
	private final SharingEntryLocalService _sharingEntryLocalService;
	private final ThemeDisplay _themeDisplay;

}