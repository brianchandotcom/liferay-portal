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

package com.liferay.redirect.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class RedirectNotFountEntriesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public RedirectNotFountEntriesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("orderByCol", getOrderByCol());
		clearResultsURL.setParameter("orderByType", getOrderByType());

		return clearResultsURL.toString();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		List<DropdownItem> filterNavigationDropdownItems =
			getFilterNavigationDropdownItems();
		List<DropdownItem> orderByDropdownItems = getOrderByDropdownItems();

		DropdownItemList filterDropdownItems = DropdownItemListBuilder.addGroup(
			() -> filterNavigationDropdownItems != null,
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					filterNavigationDropdownItems);
				dropdownGroupItem.setLabel(
					getFilterNavigationDropdownItemsLabel());
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterDateDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(request, "filter-by-date"));
			}
		).addGroup(
			() -> orderByDropdownItems != null,
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(orderByDropdownItems);
				dropdownGroupItem.setLabel(getOrderByDropdownItemsLabel());
			}
		).build();

		if (filterDropdownItems.isEmpty()) {
			return null;
		}

		return filterDropdownItems;
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		searchActionURL.setParameter("orderByCol", getOrderByCol());
		searchActionURL.setParameter("orderByType", getOrderByType());

		return searchActionURL.toString();
	}

	@Override
	public Boolean isDisabled() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		int redirectNotFoundEntriesCount =
			RedirectNotFoundEntryLocalServiceUtil.
				getRedirectNotFoundEntriesCount(
					themeDisplay.getScopeGroupId(), null, null);

		return redirectNotFoundEntriesCount == 0;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String getFilterNavigationDropdownItemsLabel() {
		return LanguageUtil.get(request, "filter-by-type");
	}

	protected String getNavigation() {
		return ParamUtil.getString(
			liferayPortletRequest, getNavigationParam(), "active-urls");
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all", "active-urls", "ignored-urls"};
	}

	@Override
	protected String getNavigationParam() {
		return "filterType";
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"modified-date", "requests"};
	}

	private List<DropdownItem> _getFilterDateDropdownItems() {
		return DropdownItemListBuilder.add(
			_getNavigationDropdownItemUnsafeConsumer("day")
		).add(
			_getNavigationDropdownItemUnsafeConsumer("week")
		).add(
			_getNavigationDropdownItemUnsafeConsumer("month")
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getNavigationDropdownItemUnsafeConsumer(String key) {

		return dropdownItem -> {
			dropdownItem.setActive(
				key.equals(ParamUtil.getString(request, "filterDate")));

			PortletURL portletURL = PortletURLUtil.clone(
				currentURLObj, liferayPortletResponse);

			portletURL.setParameter("filterDate", key);

			dropdownItem.setHref(portletURL);

			dropdownItem.setLabel(LanguageUtil.get(request, key));
		};
	}

}