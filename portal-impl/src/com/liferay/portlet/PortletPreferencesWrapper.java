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

import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.Serializable;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletPreferencesWrapper
	implements PortletPreferences, Serializable {

	public PortletPreferencesWrapper(
		PortletPreferences portletPreferences, String lifecycle) {

		_portletPreferences = portletPreferences;
		_lifecycle = lifecycle;
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
		else {
			return false;
		}
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

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getPortletPreferencesImpl}
	 */
	@Deprecated
	public PortletPreferencesImpl getPreferencesImpl() {
		return getPortletPreferencesImpl();
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
		if (PropsValues.TCK_URL) {

			// Be strict to pass the TCK

			if (_lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				_portletPreferences.store();
			}
			else {
				throw new IllegalStateException(
					"Preferences cannot be stored inside a render call");
			}
		}
		else {

			// Relax so that poorly written portlets can still work

			_portletPreferences.store();
		}
	}

	private final String _lifecycle;
	private final PortletPreferences _portletPreferences;

}