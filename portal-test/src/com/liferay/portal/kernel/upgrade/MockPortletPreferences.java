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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Iván Zaera
 */
public class MockPortletPreferences implements PortletPreferences {

	@Override
	public Map<String, String[]> getMap() {
		return _map;
	}

	@Override
	public Enumeration<String> getNames() {
		return Collections.enumeration(_map.keySet());
	}

	@Override
	public String getValue(String key, String defaultValue) {
		String[] values = _map.get(key);

		if (ArrayUtil.isNotEmpty(values)) {
			return values[0];
		}

		return defaultValue;
	}

	@Override
	public String[] getValues(String key, String[] defaultValues) {
		String[] values = _map.get(key);

		if (ArrayUtil.isNotEmpty(values)) {
			return values;
		}

		return defaultValues;
	}

	@Override
	public boolean isReadOnly(String key) {
		return false;
	}

	@Override
	public void reset(String key) {
		_map.remove(key);
	}

	@Override
	public void setValue(String key, String value) {
		_map.put(key, new String[] { value });
	}

	@Override
	public void setValues(String key, String[] values) {
		_map.put(key, values);
	}

	@Override
	public void store() {
	}

	private final Map<String, String[]> _map = new HashMap<String, String[]>();

}