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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class RedirectNotFoundEntriesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public RedirectNotFoundEntriesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<RedirectNotFoundEntry> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_redirectNotFoundEntryLocalService =
			(RedirectNotFoundEntryLocalService)httpServletRequest.getAttribute(
				RedirectNotFoundEntryLocalService.class.getName());
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData(
					"action", "ignoreSelectedRedirectNotFoundEntries");
				dropdownItem.setIcon("hidden");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "ignore"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData(
					"action", "unignoreSelectedRedirectNotFoundEntries");
				dropdownItem.setIcon("view");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "unignore"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getAvailableActions(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		if (redirectNotFoundEntry.isIgnored()) {
			return "unignoreSelectedRedirectNotFoundEntries";
		}

		return "ignoreSelectedRedirectNotFoundEntries";
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = liferayPortletResponse.createRenderURL();

		clearResultsURL.setParameter("navigation", "404-urls");

		return clearResultsURL.toString();
	}

	public Map<String, Object> getComponentContext() {
		return HashMapBuilder.<String, Object>put(
			"editRedirectNotFoundEntriesURL",
			() -> {
				ActionURL editRedirectNotFoundEntriesURL =
					liferayPortletResponse.createActionURL();

				editRedirectNotFoundEntriesURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/redirect/edit_redirect_not_found_entry");

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				editRedirectNotFoundEntriesURL.setParameter(
					"redirect", themeDisplay.getURLCurrent());

				return editRedirectNotFoundEntriesURL.toString();
			}
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		return "redirectNotFoundEntriesManagementToolbarDefaultEventHandler";
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
					LanguageUtil.get(httpServletRequest, "filter-by-date"));
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
	public List<LabelItem> getFilterLabelItems() {
		return LabelItemListBuilder.add(
			() -> !StringUtil.equals(getNavigation(), "active-urls"),
			labelItem -> {
				PortletURL removeLabelURL = getPortletURL();

				removeLabelURL.setParameter(getNavigationParam(), (String)null);

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);

				String label = String.format(
					"%s: %s", LanguageUtil.get(httpServletRequest, "type"),
					LanguageUtil.get(httpServletRequest, getNavigation()));

				labelItem.setLabel(label);
			}
		).add(
			() -> _getFilterDate() != 0,
			labelItem -> {
				PortletURL removeLabelURL = getPortletURL();

				removeLabelURL.setParameter("filterDate", (String)null);

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);

				String label = String.format(
					"%s: %s", LanguageUtil.get(httpServletRequest, "date"),
					_getFilterDateLabel(_getFilterDate()));

				labelItem.setLabel(label);
			}
		).build();
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
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		int redirectNotFoundEntriesCount =
			_redirectNotFoundEntryLocalService.getRedirectNotFoundEntriesCount(
				themeDisplay.getScopeGroupId(), null, null);

		return redirectNotFoundEntriesCount == 0;
	}

	@Override
	protected String getFilterNavigationDropdownItemsLabel() {
		return LanguageUtil.get(httpServletRequest, "filter-by-type");
	}

	@Override
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

	private int _getFilterDate() {
		return ParamUtil.getInteger(httpServletRequest, "filterDate");
	}

	private List<DropdownItem> _getFilterDateDropdownItems() {
		return DropdownItemListBuilder.add(
			_getFilterDateDropdownItemUnsafeConsumer(0)
		).add(
			_getFilterDateDropdownItemUnsafeConsumer(1)
		).add(
			_getFilterDateDropdownItemUnsafeConsumer(7)
		).add(
			_getFilterDateDropdownItemUnsafeConsumer(30)
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getFilterDateDropdownItemUnsafeConsumer(int days) {

		return dropdownItem -> {
			dropdownItem.setActive(days == _getFilterDate());

			PortletURL portletURL = getPortletURL();

			portletURL.setParameter("filterDate", String.valueOf(days));

			dropdownItem.setHref(portletURL);

			dropdownItem.setLabel(_getFilterDateLabel(days));
		};
	}

	private String _getFilterDateLabel(int days) {
		if (days == 0) {
			return LanguageUtil.get(httpServletRequest, "all");
		}

		if (days == 1) {
			return LanguageUtil.format(httpServletRequest, "x-day", days);
		}

		return LanguageUtil.format(httpServletRequest, "x-days", days);
	}

	private final RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}