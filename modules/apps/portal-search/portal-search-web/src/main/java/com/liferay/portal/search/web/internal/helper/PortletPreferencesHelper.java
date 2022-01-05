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

package com.liferay.portal.search.web.internal.helper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class PortletPreferencesHelper {

	public PortletPreferencesHelper(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesOptional = portletPreferencesOptional;
	}

	public Optional<Boolean> getBoolean(String key) {
		Optional<String> valueOptional = _getValue(key);

		return valueOptional.map(GetterUtil::getBoolean);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		Optional<Boolean> valueOptional = getBoolean(key);

		return valueOptional.orElse(defaultValue);
	}

	public Optional<Integer> getInteger(String key) {
		Optional<String> valueOptional = _getValue(key);

		return valueOptional.map(GetterUtil::getInteger);
	}

	public int getInteger(String key, int defaultValue) {
		Optional<Integer> valueOptional = getInteger(key);

		return valueOptional.orElse(defaultValue);
	}

	public Optional<String> getString(String key) {
		return _getValue(key);
	}

	public String getString(String key, String defaultValue) {
		Optional<String> valueOptional = getString(key);

		return valueOptional.orElse(defaultValue);
	}

	private Optional<String> _getValue(String key) {
		return _portletPreferencesOptional.flatMap(
			portletPreferences -> SearchStringUtil.maybe(
				portletPreferences.getValue(key, StringPool.BLANK)));
	}

	private final Optional<PortletPreferences> _portletPreferencesOptional;

}