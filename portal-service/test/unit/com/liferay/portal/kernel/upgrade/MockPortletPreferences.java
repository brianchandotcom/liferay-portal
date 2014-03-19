/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.IOException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

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
		return new Enumeration<String>() {

			private Iterator<String> _iterator = _map.keySet().iterator();

			@Override
			public boolean hasMoreElements() {
				return _iterator.hasNext();
			}

			@Override
			public String nextElement() {
				return _iterator.next();
			}

		};
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
	public void reset(String key) throws ReadOnlyException {
		_map.remove(key);
	}

	@Override
	public void setValue(String key, String value) throws ReadOnlyException {
		_map.put(key, new String[] { value });
	}

	@Override
	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		_map.put(key, values);
	}

	@Override
	public void store() throws IOException, ValidatorException {
	}

	private Map<String, String[]> _map = new HashMap<String, String[]>();

}