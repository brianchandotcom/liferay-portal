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

	public boolean getBoolean(String key, boolean defaultValue) {
		Optional<String> valueOptional = _getValue(key);

		return valueOptional.map(
			GetterUtil::getBoolean
		).orElse(
			defaultValue
		);
	}

	public int getInteger(String key, int defaultValue) {
		Optional<String> valueOptional = _getValue(key);

		return valueOptional.map(
			GetterUtil::getInteger
		).orElse(
			defaultValue
		);
	}

	public String getString(String key, String defaultValue) {
		Optional<String> valueOptional = _getValue(key);

		return valueOptional.orElse(defaultValue);
	}

	private Optional<String> _getValue(String key) {
		return _portletPreferencesOptional.flatMap(
			portletPreferences -> SearchStringUtil.maybe(
				portletPreferences.getValue(key, StringPool.BLANK)));
	}

	private final Optional<PortletPreferences> _portletPreferencesOptional;

}