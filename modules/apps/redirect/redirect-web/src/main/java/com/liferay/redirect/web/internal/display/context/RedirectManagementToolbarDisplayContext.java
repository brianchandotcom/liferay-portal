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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.web.internal.security.permission.resource.RedirectEntryPermission;
import com.liferay.redirect.web.internal.security.permission.resource.RedirectPermission;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class RedirectManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public RedirectManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<RedirectEntry> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteSelectedRedirectEntries");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getAvailableActions(RedirectEntry redirectEntry)
		throws PortalException {

		if (RedirectEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), redirectEntry,
				ActionKeys.DELETE)) {

			return "deleteSelectedRedirectEntries";
		}

		return StringPool.BLANK;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("orderByCol", getOrderByCol());
		clearResultsURL.setParameter("orderByType", getOrderByType());

		return clearResultsURL.toString();
	}

	public Map<String, Object> getComponentContext() {
		return HashMapBuilder.<String, Object>put(
			"deleteRedirectEntriesURL",
			() -> {
				PortletURL deleteRedirectEntriesURL =
					liferayPortletResponse.createActionURL();

				deleteRedirectEntriesURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/redirect/delete_redirect_entry");

				return deleteRedirectEntriesURL.toString();
			}
		).build();
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!RedirectPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {

			return null;
		}

		try {
			return CreationMenuBuilder.addPrimaryDropdownItem(
				dropdownItem -> {
					RenderURL editRedirectEntryURL =
						liferayPortletResponse.createRenderURL();

					editRedirectEntryURL.setParameter(
						"mvcRenderCommandName",
						"/redirect/edit_redirect_entry");

					PortletURL portletURL = getPortletURL();

					editRedirectEntryURL.setParameter(
						"redirect", portletURL.toString());

					dropdownItem.setHref(editRedirectEntryURL);

					dropdownItem.setLabel(
						LanguageUtil.get(httpServletRequest, "add"));
				}
			).build();
		}
		catch (Exception exception) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return "redirectManagementToolbarDefaultEventHandler";
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		searchActionURL.setParameter("orderByCol", getOrderByCol());
		searchActionURL.setParameter("orderByType", getOrderByType());

		return searchActionURL.toString();
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {
			"create-date", "modified-date", "latest-occurrence", "source-url",
			"destination-url"
		};
	}

	private final ThemeDisplay _themeDisplay;

}