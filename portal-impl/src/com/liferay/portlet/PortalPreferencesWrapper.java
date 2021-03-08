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

/**
 * @author Alexander Chow
 */
public class PortalPreferencesWrapper
	implements Cloneable, PortletPreferences, Serializable {

	public PortalPreferencesWrapper(
		PortalPreferencesImpl portalPreferencesImpl) {

		_portalPreferencesImpl = portalPreferencesImpl;
	}

	@Override
	public PortalPreferencesWrapper clone() {
		return new PortalPreferencesWrapper(_portalPreferencesImpl.clone());
	}

	@Override
	public Map<String, String[]> getMap() {
		return _portalPreferencesImpl.getMap(null);
	}

	@Override
	public Enumeration<String> getNames() {
		return _portalPreferencesImpl.getNames(null);
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
		return false;
	}

	@Override
	public void reset(String key) {
		_portalPreferencesImpl.reset(null, key);
	}

	@Override
	public void setValue(String key, String value) {
		_portalPreferencesImpl.setValue(null, key, value);
	}

	@Override
	public void setValues(String key, String... values) {
		_portalPreferencesImpl.setValues(null, key, values);
	}

	@Override
	public void store() throws IOException {
		_portalPreferencesImpl.store();
	}

	private final PortalPreferencesImpl _portalPreferencesImpl;

}