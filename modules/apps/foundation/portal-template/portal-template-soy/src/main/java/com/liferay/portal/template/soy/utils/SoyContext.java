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

package com.liferay.portal.template.soy.utils;

import com.liferay.portal.template.soy.constants.SoyTemplateConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruno Basto
 */
public class SoyContext extends HashMap<String, Object> {

	public SoyContext() {
		_injectedData = new HashMap<>();

		put(SoyTemplateConstants.INJECTED_DATA, _injectedData);
	}

	public void clearInjectedData() {
		_injectedData.clear();
	}

	public void putHTML(String key, String value) {
		put(key, new SoyHTMLContextValue(value));
	}

	public void putInjectedData(String key, Object value) {
		_injectedData.put(key, value);
	}

	public void removeInjectedData(String key) {
		_injectedData.remove(key);
	}

	private final Map<String, Object> _injectedData;

}