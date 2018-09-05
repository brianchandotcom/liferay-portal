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

package com.liferay.portlet;

import java.io.IOException;
import java.io.Serializable;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletPreferencesWrapper
	implements PortletPreferences, Serializable {

	public PortletPreferencesWrapper(PortletPreferences portletPreferences) {
		_portletPreferences = portletPreferences;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortletPreferencesWrapper)) {
			return false;
		}

		PortletPreferencesWrapper portletPreferencesWrapper =
			(PortletPreferencesWrapper)obj;

		if (getPortletPreferencesImpl().equals(
				portletPreferencesWrapper.getPortletPreferencesImpl())) {

			return true;
		}

		return false;
	}

	@Override
	public Map<String, String[]> getMap() {
		return _portletPreferences.getMap();
	}

	@Override
	public Enumeration<String> getNames() {
		return _portletPreferences.getNames();
	}

	public PortletPreferencesImpl getPortletPreferencesImpl() {
		return (PortletPreferencesImpl)_portletPreferences;
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
	public int hashCode() {
		return _portletPreferences.hashCode();
	}

	@Override
	public boolean isReadOnly(String key) {
		return _portletPreferences.isReadOnly(key);
	}

	@Override
	public void reset(String key) throws ReadOnlyException {
		_portletPreferences.reset(key);
	}

	@Override
	public void setValue(String key, String value) throws ReadOnlyException {
		_portletPreferences.setValue(key, value);
	}

	@Override
	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		_portletPreferences.setValues(key, values);
	}

	@Override
	public void store() throws IOException, ValidatorException {

		// PLT.17.1, clv

		throw new IllegalStateException(
			"Preferences cannot be stored inside a render call");
	}

	private final PortletPreferences _portletPreferences;

}