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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.item.selector.taglib.servlet.taglib.RepositoryEntryBrowserTag;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class ItemSelectorRepositoryEntryManagementToolbarDisplayContext {

	public ItemSelectorRepositoryEntryManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_request = request;

		_currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					SafeConsumer.ignore(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getOrderByDropdownItems());
							dropdownGroupItem.setLabel(
								LanguageUtil.get(_request, "order-by"));
						}));
			}
		};
	}

	public String getOrderByType() {
		return ParamUtil.getString(_request, "orderByType", "asc");
	}

	public PortletURL getSearchURL() throws PortletException {
		PortletURL searchURL = PortletURLUtil.clone(
			_currentURLObj, _liferayPortletResponse);

		searchURL.setParameter("keywords", (String)null);
		searchURL.setParameter("resetCur", Boolean.TRUE.toString());

		return searchURL;
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL sortingURL = _getCurrentSortingURL();

		String orderByType = getOrderByType();

		sortingURL.setParameter(
			"orderByType", Objects.equals(orderByType, "asc") ? "desc" : "asc");

		return sortingURL;
	}

	public ViewTypeItemList getViewTypes() throws PortletException {
		PortletURL displayStyleURL = PortletURLUtil.clone(
			_getPortletURL(), _liferayPortletResponse);

		return new ViewTypeItemList(displayStyleURL, _getDisplayStyle()) {
			{
				if (ArrayUtil.contains(_getDisplayViews(), "icon")) {
					addCardViewTypeItem();
				}

				if (ArrayUtil.contains(_getDisplayViews(), "descriptive")) {
					addListViewTypeItem();
				}

				if (ArrayUtil.contains(_getDisplayViews(), "list")) {
					addTableViewTypeItem();
				}
			}
		};
	}

	public boolean isDisabled() {
		return false;
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		PortletURL currentSortingURL = PortletURLUtil.clone(
			_getPortletURL(), _liferayPortletResponse);

		currentSortingURL.setParameter("orderByType", getOrderByType());
		currentSortingURL.setParameter("orderByCol", _getOrderByCol());

		return currentSortingURL;
	}

	private String _getDisplayStyle() {
		return GetterUtil.getString(
			_request.getAttribute(
				"liferay-item-selector:repository-entry-browser:displayStyle"));
	}

	private String[] _getDisplayViews() {
		return RepositoryEntryBrowserTag.DISPLAY_STYLES;
	}

	private String _getOrderByCol() {
		return ParamUtil.getString(_request, "orderByCol", "title");
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				Map<String, String> orderColumnsMap = new HashMap<>();

				orderColumnsMap.put("modifiedDate", "modified-date");
				orderColumnsMap.put("size", "size");
				orderColumnsMap.put("title", "title");

				for (Map.Entry<String, String> orderByColEntry :
						orderColumnsMap.entrySet()) {

					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								String orderByCol = orderByColEntry.getKey();

								dropdownItem.setActive(
									orderByCol.equals(_getOrderByCol()));

								dropdownItem.setHref(
									_getCurrentSortingURL(), "orderByCol",
									orderByCol);

								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, orderByColEntry.getValue()));
							}));
				}
			}
		};
	}

	private PortletURL _getPortletURL() {
		return (PortletURL)_request.getAttribute(
			"liferay-item-selector:repository-entry-browser:portletURL");
	}

	private final PortletURL _currentURLObj;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;

}