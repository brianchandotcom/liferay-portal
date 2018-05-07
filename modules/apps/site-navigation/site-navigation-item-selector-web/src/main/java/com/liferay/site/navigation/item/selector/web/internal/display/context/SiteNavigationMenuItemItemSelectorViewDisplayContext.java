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

package com.liferay.site.navigation.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteNavigationMenuItemItemSelectorViewDisplayContext {

	public SiteNavigationMenuItemItemSelectorViewDisplayContext(
		HttpServletRequest request, String itemSelectedEventName,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry) {

		_request = request;
		_itemSelectedEventName = itemSelectedEventName;
		_siteNavigationMenuItemTypeRegistry =
			siteNavigationMenuItemTypeRegistry;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public JSONArray getSiteNavigationMenuItemsJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		long siteNavigationMenuId = ParamUtil.getLong(
			_request, "siteNavigationMenuId");

		jsonObject.put(
			"children",
			_getSiteNavigationMenuItemsJSONArray(siteNavigationMenuId, 0));

		jsonObject.put("disabled", true);
		jsonObject.put("icon", "blogs");
		jsonObject.put("id", "0");

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
				siteNavigationMenuId);

		if (siteNavigationMenu != null) {
			jsonObject.put("name", siteNavigationMenu.getName());
		}

		jsonArray.put(jsonObject);

		return jsonArray;
	}

	private long _getSiteNavigationMenuItemId() {
		if (_siteNavigationMenuItemId != null) {
			return _siteNavigationMenuItemId;
		}

		_siteNavigationMenuItemId = ParamUtil.getLong(
			_request, "siteNavigationMenuItemId");

		return _siteNavigationMenuItemId;
	}

	private JSONArray _getSiteNavigationMenuItemsJSONArray(
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItems(
				siteNavigationMenuId, parentSiteNavigationMenuItemId);

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			JSONArray childrenJSONArray = _getSiteNavigationMenuItemsJSONArray(
				siteNavigationMenuId,
				siteNavigationMenuItem.getSiteNavigationMenuItemId());

			if (childrenJSONArray.length() > 0) {
				jsonObject.put("children", childrenJSONArray);
			}

			SiteNavigationMenuItemType siteNavigationMenuItemType =
				_siteNavigationMenuItemTypeRegistry.
					getSiteNavigationMenuItemType(
						siteNavigationMenuItem.getType());

			jsonObject.put("icon", siteNavigationMenuItemType.getIcon());

			jsonObject.put(
				"id",
				String.valueOf(
					siteNavigationMenuItem.getSiteNavigationMenuItemId()));

			jsonObject.put(
				"name",
				siteNavigationMenuItemType.getTitle(
					siteNavigationMenuItem, themeDisplay.getLocale()));

			if (siteNavigationMenuItem.getSiteNavigationMenuItemId() ==
					_getSiteNavigationMenuItemId()) {

				jsonObject.put("selected", true);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private final String _itemSelectedEventName;
	private final HttpServletRequest _request;
	private Long _siteNavigationMenuItemId;
	private final SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

}