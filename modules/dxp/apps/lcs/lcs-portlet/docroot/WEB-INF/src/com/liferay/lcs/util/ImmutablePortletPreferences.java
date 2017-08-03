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

package com.liferay.lcs.util;

import java.io.IOException;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Ivica Cardic
 */
public class ImmutablePortletPreferences implements PortletPreferences {

	public ImmutablePortletPreferences(PortletPreferences portletPreferences) {
		_portletPreferences = portletPreferences;
	}

	@Override
	public Map<String, String[]> getMap() {
		return _portletPreferences.getMap();
	}

	@Override
	public Enumeration<String> getNames() {
		return _portletPreferences.getNames();
	}

	@Override
	public String getValue(String key, String def) {
		return _portletPreferences.getValue(key, def);
	}

	@Override
	public String[] getValues(String key, String[] def) {
		return _portletPreferences.getValues(key, def);
	}

	@Override
	public boolean isReadOnly(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reset(String key) throws ReadOnlyException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue(String key, String value) throws ReadOnlyException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void store() throws IOException, ValidatorException {
		throw new UnsupportedOperationException();
	}

	private final PortletPreferences _portletPreferences;

}