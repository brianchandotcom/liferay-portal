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

package com.liferay.portlet;

import java.io.IOException;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Alexander Chow
 */
public class PortalPreferencesWrapper implements PortletPreferences {

	public PortalPreferencesWrapper(
		PortalPreferencesImpl portalPreferencesImpl) {

		_portalPreferencesImpl = portalPreferencesImpl;
	}

	@Override
	public Map<String, String[]> getMap() {
		return _portalPreferencesImpl.getMap();
	}

	@Override
	public Enumeration<String> getNames() {
		return _portalPreferencesImpl.getNames();
	}

	public PortalPreferencesImpl getPortalPreferencesImpl() {
		return _portalPreferencesImpl;
	}

	@Override
	public String getValue(String key, String def) {
		return _portalPreferencesImpl.getValue(null, key, def);
	}

	@Override
	public String[] getValues(String key, String[] def) {
		return _portalPreferencesImpl.getValues(null, key, def);
	}

	@Override
	public boolean isReadOnly(String key) {
		return _portalPreferencesImpl.isReadOnly(key);
	}

	@Override
	public void reset(String key) throws ReadOnlyException {
		_portalPreferencesImpl.reset(key);
	}

	@Override
	public void setValue(String key, String value) throws ReadOnlyException {
		_portalPreferencesImpl.setValue(key, value);
	}

	@Override
	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		_portalPreferencesImpl.setValues(key, values);
	}

	@Override
	public void store() throws IOException {
		_portalPreferencesImpl.store();
	}

	private PortalPreferencesImpl _portalPreferencesImpl;

}