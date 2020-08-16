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

package com.liferay.analytics.settings.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public class GroupManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public GroupManagementToolbarDisplayContext(
		GroupDisplayContext groupDisplayContext,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			groupDisplayContext.getGroupSearch());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "groupManagementToolbar";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "selectGroups";
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list"};
	}

	@Override
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
								LanguageUtil.get(
									_resourceBundle, entry.getKey()));
						});
				}
			}
		};
	}

	@Override
	protected String getFilterNavigationDropdownItemsLabel() {
		return LanguageUtil.get(request, "sites");
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"site-name"};
	}

	private final ResourceBundle _resourceBundle;

}