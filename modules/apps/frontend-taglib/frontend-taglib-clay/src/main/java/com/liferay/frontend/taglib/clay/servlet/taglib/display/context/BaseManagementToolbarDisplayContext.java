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

package com.liferay.frontend.taglib.clay.servlet.taglib.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class BaseManagementToolbarDisplayContext
	implements ManagementToolbarDisplayContext {

	public BaseManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest) {

		this.liferayPortletRequest = liferayPortletRequest;
		this.liferayPortletResponse = liferayPortletResponse;
		request = httpServletRequest;

		currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		DropdownItemList filterDropdownItems = new DropdownItemList() {
			{
				List<DropdownItem> filterNavigationDropdownItems =
					getFilterNavigationDropdownItems();

				if (filterNavigationDropdownItems != null) {
					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								filterNavigationDropdownItems);
							dropdownGroupItem.setLabel(
								getFilterNavigationDropdownItemsLabel());
						});
				}

				List<DropdownItem> orderByDropdownItems =
					getOrderByDropdownItems();

				if (orderByDropdownItems != null) {
					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								orderByDropdownItems);
							dropdownGroupItem.setLabel(
								getOrderByDropdownItemsLabel());
						});
				}
			}
		};

		if (filterDropdownItems.isEmpty()) {
			return null;
		}

		return filterDropdownItems;
	}

	@Override
	public String getNamespace() {
		return liferayPortletResponse.getNamespace();
	}

	@Override
	public String getSortingOrder() {
		return getOrderByType();
	}

	@Override
	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			sortingURL.setParameter(getOrderByColParam(), orderByCol);
		}

		sortingURL.setParameter(
			getOrderByTypeParam(),
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		if (ArrayUtil.isEmpty(getDisplayViews())) {
			return null;
		}

		return new ViewTypeItemList(getPortletURL(), getDisplayStyle()) {
			{
				if (ArrayUtil.contains(getDisplayViews(), "icon")) {
					addCardViewTypeItem();
				}

				if (ArrayUtil.contains(getDisplayViews(), "descriptive")) {
					addListViewTypeItem();
				}

				if (ArrayUtil.contains(getDisplayViews(), "list")) {
					addTableViewTypeItem();
				}
			}
		};
	}

	protected String getDefaultDisplayStyle() {
		return "list";
	}

	protected Map<String, String> getDefaultEntriesMap(String[] entryKeys) {
		if ((entryKeys == null) || (entryKeys.length == 0)) {
			return null;
		}

		Map<String, String> entriesMap = new LinkedHashMap<>();

		for (String entryKey : entryKeys) {
			entriesMap.put(entryKey, entryKey);
		}

		return entriesMap;
	}

	protected String getDisplayStyle() {
		return ParamUtil.getString(
			request, "displayStyle", getDefaultDisplayStyle());
	}

	protected String[] getDisplayViews() {
		return new String[0];
	}

	protected List<DropdownItem> getDropdownItems(
		Map<String, String> entriesMap, PortletURL entryURL,
		String parameterName, String parameterValue) {

		if ((entriesMap == null) || entriesMap.isEmpty()) {
			return null;
		}

		return new DropdownItemList() {
			{
				for (Map.Entry<String, String> entry : entriesMap.entrySet()) {
					add(
						dropdownItem -> {
							if (parameterValue != null) {
								dropdownItem.setActive(
									parameterValue.equals(entry.getValue()));
							}

							dropdownItem.setHref(
								entryURL, parameterName, entry.getValue());
							dropdownItem.setLabel(
								LanguageUtil.get(request, entry.getKey()));
						});
				}
			}
		};
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return getDropdownItems(
			getNavigationEntriesMap(), getPortletURL(), getNavigationParam(),
			getNavigation());
	}

	protected String getFilterNavigationDropdownItemsLabel() {
		return LanguageUtil.get(request, "filter-by-navigation");
	}

	protected String getNavigation() {
		return ParamUtil.getString(
			liferayPortletRequest, getNavigationParam(), "all");
	}

	protected Map<String, String> getNavigationEntriesMap() {
		return getDefaultEntriesMap(getNavigationKeys());
	}

	protected String[] getNavigationKeys() {
		return null;
	}

	protected String getNavigationParam() {
		return "navigation";
	}

	protected String getOrderByCol() {
		return ParamUtil.getString(liferayPortletRequest, getOrderByColParam());
	}

	protected String getOrderByColParam() {
		return "orderByCol";
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return getDropdownItems(
			getOrderByEntriesMap(), getPortletURL(), getOrderByColParam(),
			getOrderByCol());
	}

	protected String getOrderByDropdownItemsLabel() {
		return LanguageUtil.get(request, "order-by");
	}

	protected Map<String, String> getOrderByEntriesMap() {
		return getDefaultEntriesMap(getOrderByKeys());
	}

	protected String[] getOrderByKeys() {
		return null;
	}

	protected String getOrderByType() {
		return ParamUtil.getString(
			liferayPortletRequest, getOrderByTypeParam(), "asc");
	}

	protected String getOrderByTypeParam() {
		return "orderByType";
	}

	protected PortletURL getPortletURL() {
		try {
			return PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
		}
		catch (PortletException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameters(currentURLObj.getParameterMap());

			return portletURL;
		}
	}

	protected final PortletURL currentURLObj;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final HttpServletRequest request;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseManagementToolbarDisplayContext.class);

}