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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Jorge Ferrer
 * @author Iván Zaera
 */
public class PortletPreferencesSettings extends BaseModifiableSettings {

	public PortletPreferencesSettings(PortletPreferences portletPreferences) {
		this(portletPreferences, null);
	}

	public PortletPreferencesSettings(
		PortletPreferences portletPreferences, Settings parentSettings) {

		super(parentSettings);

		_portletPreferences = portletPreferences;
	}

	@Override
	public Collection<String> getModifiedKeys() {
		Set<String> keys = new HashSet<>();

		Enumeration<String> names = _portletPreferences.getNames();

		while (names.hasMoreElements()) {
			keys.add(names.nextElement());
		}

		return keys;
	}

	public PortletPreferences getPortletPreferences() {
		return _portletPreferences;
	}

	@Override
	public void reset(String key) {
		try {
			_portletPreferences.reset(key);
		}
		catch (ReadOnlyException roe) {
			_log.error(
				"Portlet preferences used to persist settings should never " +
					"be read only",
				roe);
		}
	}

	@Override
	public ModifiableSettings setValue(String key, String value) {
		try {
			_portletPreferences.setValue(key, value);
		}
		catch (ReadOnlyException roe) {
			_log.error(
				"Portlet preferences used to persist settings should never " +
					"be read only",
				roe);
		}

		return this;
	}

	@Override
	public ModifiableSettings setValues(String key, String[] values) {
		try {
			_portletPreferences.setValues(key, values);
		}
		catch (ReadOnlyException roe) {
			_log.error(
				"Portlet preferences used to persist settings should never " +
					"be read only",
				roe);
		}

		return this;
	}

	@Override
	public void store() throws IOException, ValidatorException {
		_portletPreferences.store();
	}

	@Override
	protected String doGetValue(String key) {
		return _portletPreferences.getValue(key, null);
	}

	@Override
	protected String[] doGetValues(String key) {
		return _portletPreferences.getValues(key, null);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesSettings.class);

	private final PortletPreferences _portletPreferences;

}