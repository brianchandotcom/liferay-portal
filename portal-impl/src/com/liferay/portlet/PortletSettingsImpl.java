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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.xml.XMLFormatter;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 */
public class PortletSettingsImpl implements PortletSettings {

	public PortletSettingsImpl(
		PortletPreferences instancePreferences,
		PortletPreferences sitePreferences,
		PortletPreferences companyPreferences, Properties portalProperties) {

		_instancePreferences = instancePreferences;
		_sitePreferences = sitePreferences;
		_companyPreferences = companyPreferences;
		_portalProperties = portalProperties;
	}

	public PortletSettingsImpl(
		PortletPreferences sitePreferences,
		PortletPreferences companyPreferences, Properties portalProperties) {

		this(null, sitePreferences, companyPreferences, portalProperties);
	}

	public PortletSettingsImpl(
		PortletPreferences companyPreferences, Properties portalProperties) {

		this(null, null, companyPreferences, portalProperties);
	}

	@Override
	public String getValue(String key, String def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		String value = null;

		if (_instancePreferences != null) {
			value = _instancePreferences.getValue(key, null);
		}

		if (isNull(value) && (_sitePreferences != null)) {
			value = _sitePreferences.getValue(key, null);
		}

		if (isNull(value) && (_companyPreferences != null)) {
			value = _companyPreferences.getValue(key, null);
		}

		if (isNull(value) && (_portalProperties != null)) {
			value = _portalProperties.getProperty(key, null);
		}

		if (!isNull(value)) {
			return normalizeValue(value);
		}

		return normalizeValue(def);
	}

	@Override
	public String[] getValues(String key, String[] def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		String[] values = _sitePreferences.getValues(key, def);

		if (_instancePreferences != null) {
			values = _instancePreferences.getValues(key, null);
		}

		if (ArrayUtil.isEmpty(values) && (_sitePreferences != null)) {
			values = _sitePreferences.getValues(key, null);
		}

		if (ArrayUtil.isEmpty(values) && (_companyPreferences != null)) {
			values = _companyPreferences.getValues(key, null);
		}

		if (ArrayUtil.isEmpty(values) && (_portalProperties != null)) {
			values = StringUtil.split(_portalProperties.getProperty(key));
		}

		if (ArrayUtil.isNotEmpty(values)) {
			return normalizeValues(values);
		}

		return normalizeValues(def);
	}

	protected String normalizeValue(String value) {
		if (isNull(value)) {
			return null;
		}

		return XMLFormatter.fromCompactSafe(value);
	}

	protected String[] normalizeValues(String[] values) {
		if (values == null) {
			return null;
		}

		if (values.length == 1) {
			String actualValue = normalizeValue(values[0]);

			if (actualValue == null) {
				return null;
			}

			return new String[] {actualValue};
		}

		String[] actualValues = new String[values.length];

		for (int i = 0; i < actualValues.length; i++) {
			actualValues[i] = normalizeValue(values[i]);
		}

		return actualValues;
	}

	private boolean isNull(String value) {
		return (value == null) || value.equals(_NULL_VALUE);
	}

	private static final String _NULL_VALUE = "NULL_VALUE";

	private PortletPreferences _companyPreferences;
	private PortletPreferences _instancePreferences;
	private Properties _portalProperties;
	private PortletPreferences _sitePreferences;

}