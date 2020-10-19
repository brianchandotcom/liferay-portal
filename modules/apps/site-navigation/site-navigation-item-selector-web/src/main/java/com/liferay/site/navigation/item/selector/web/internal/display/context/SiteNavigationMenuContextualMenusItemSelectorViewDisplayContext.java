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
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class SiteNavigationMenuContextualMenusItemSelectorViewDisplayContext {

	public SiteNavigationMenuContextualMenusItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, String itemSelectedEventName) {

		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public JSONArray getLevelsJSONArray() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(), getClass());

		return JSONUtil.putAll(
			JSONUtil.put(
				"description",
				LanguageUtil.get(resourceBundle, "second-level-description")
			).put(
				"imageURL", ""
			).put(
				"title", LanguageUtil.get(resourceBundle, "second-level")
			).put(
				"value", "second-level"
			),
			JSONUtil.put(
				"description",
				LanguageUtil.get(resourceBundle, "same-level-description")
			).put(
				"imageURL", ""
			).put(
				"title", LanguageUtil.get(resourceBundle, "same-level")
			).put(
				"value", "same-level"
			),
			JSONUtil.put(
				"description",
				LanguageUtil.get(resourceBundle, "upper-level-description")
			).put(
				"imageURL", ""
			).put(
				"title", LanguageUtil.get(resourceBundle, "upper-level")
			).put(
				"value", "upper-level"
			));
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;

}