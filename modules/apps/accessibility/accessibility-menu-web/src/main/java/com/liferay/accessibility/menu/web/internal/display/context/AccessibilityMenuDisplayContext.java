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

package com.liferay.accessibility.menu.web.internal.display.context;

import com.liferay.accessibility.menu.web.internal.util.AccessibilitySettingsUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Evan Thibodeau
 */
public class AccessibilityMenuDisplayContext {

	public AccessibilityMenuDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public JSONArray getAccessibilitySettingsJSONArray() throws Exception {
		return JSONUtil.toJSONArray(
			AccessibilitySettingsUtil.getAccessibilitySettings(
				_httpServletRequest),
			accessibilitySetting -> JSONUtil.put(
				"className", accessibilitySetting.getCssClass()
			).put(
				"defaultValue", accessibilitySetting.getDefaultValue()
			).put(
				"key", accessibilitySetting.getKey()
			).put(
				"label", accessibilitySetting.getLabel()
			).put(
				"sessionClicksValue",
				accessibilitySetting.getSessionClicksValue()
			));
	}

	private final HttpServletRequest _httpServletRequest;

}