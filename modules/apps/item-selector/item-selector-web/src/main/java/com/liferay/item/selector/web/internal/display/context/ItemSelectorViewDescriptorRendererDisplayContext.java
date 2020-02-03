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

package com.liferay.item.selector.web.internal.display.context;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class ItemSelectorViewDescriptorRendererDisplayContext {

	public ItemSelectorViewDescriptorRendererDisplayContext(
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		ItemSelectorViewDescriptor itemSelectorViewDescriptor,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
		_itemSelectorViewDescriptor = itemSelectorViewDescriptor;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public List<BreadcrumbEntry> getBreadcrumbEntries(PortletURL currentURL)
		throws PortalException, PortletException {

		return Arrays.asList(
			_getGroupSelectorBreadcrumbEntry(currentURL),
			_getCurrentGroupBreadcrumbEntry(currentURL));
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public ItemSelectorViewDescriptor getItemSelectorViewDescriptor() {
		return _itemSelectorViewDescriptor;
	}

	public String getReturnType() {
		ItemSelectorReturnType itemSelectorReturnType =
			_itemSelectorViewDescriptor.getItemSelectorReturnType();

		Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass =
			itemSelectorReturnType.getClass();

		return itemSelectorReturnTypeClass.getName();
	}

	public boolean isIconDisplayStyle() {
		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		if (Validator.isNull(displayStyle) || displayStyle.equals("icon")) {
			return true;
		}

		return false;
	}

	private BreadcrumbEntry _getCurrentGroupBreadcrumbEntry(
			PortletURL currentURL)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			scopeGroup.getDescriptiveName(_httpServletRequest.getLocale()));
		breadcrumbEntry.setURL(currentURL.toString());

		return breadcrumbEntry;
	}

	private BreadcrumbEntry _getGroupSelectorBreadcrumbEntry(
			PortletURL currentURL)
		throws PortletException {

		PortletURL viewGroupSelectorURL = PortletURLUtil.clone(
			currentURL, _liferayPortletResponse);

		viewGroupSelectorURL.setParameter("groupType", "site");
		viewGroupSelectorURL.setParameter(
			"showGroupSelector", Boolean.TRUE.toString());

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			LanguageUtil.get(_httpServletRequest, "sites"));
		breadcrumbEntry.setURL(viewGroupSelectorURL.toString());

		return breadcrumbEntry;
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final ItemSelectorViewDescriptor _itemSelectorViewDescriptor;
	private final LiferayPortletResponse _liferayPortletResponse;

}