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
import javax.portlet.ReadOnlyException;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public abstract class BasePortletSettings implements PortletSettings {

	@Override
	public String getValue(String key, String defaultValue) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		String value = null;

		if (_instancePortletPreferences != null) {
			value = _instancePortletPreferences.getValue(key, null);
		}

		if (isNull(value) && (_sitePortletPreferences != null)) {
			value = _sitePortletPreferences.getValue(key, null);
		}

		if (isNull(value) && (_companyPortletPreferences != null)) {
			value = _companyPortletPreferences.getValue(key, null);
		}

		if (isNull(value) && (_portalProperties != null)) {
			value = _portalProperties.getProperty(key, null);
		}

		if (!isNull(value)) {
			return normalizeValue(value);
		}

		return normalizeValue(defaultValue);
	}

	@Override
	public String[] getValues(String key, String[] defaultValue) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		String[] values = _sitePortletPreferences.getValues(key, defaultValue);

		if (_instancePortletPreferences != null) {
			values = _instancePortletPreferences.getValues(key, null);
		}

		if (ArrayUtil.isEmpty(values) && (_sitePortletPreferences != null)) {
			values = _sitePortletPreferences.getValues(key, null);
		}

		if (ArrayUtil.isEmpty(values) && (_companyPortletPreferences != null)) {
			values = _companyPortletPreferences.getValues(key, null);
		}

		if (ArrayUtil.isEmpty(values) && (_portalProperties != null)) {
			values = StringUtil.split(_portalProperties.getProperty(key));
		}

		if (ArrayUtil.isNotEmpty(values)) {
			return normalizeValues(values);
		}

		return normalizeValues(defaultValue);
	}

	protected BasePortletSettings() {
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

	public PortletSettings setValue(String key, String value) {
		try {
			getWriteablePortletPreferences().setValue(key, value);
		}
		catch (ReadOnlyException roe) {

			// This should never occur

		}

		return this;
	}

	public PortletSettings setValues(String key, String[] values) {
		try {
			getWriteablePortletPreferences().setValues(key, values);
		}
		catch (ReadOnlyException roe) {

			// This should never occur

		}

		return this;
	}

	protected abstract PortletPreferences getWriteablePortletPreferences();

	protected PortletPreferences _companyPortletPreferences;
	protected PortletPreferences _instancePortletPreferences;
	protected Properties _portalProperties;
	protected PortletPreferences _sitePortletPreferences;

	private boolean isNull(String value) {
		return (value == null) || value.equals(_NULL_VALUE);
	}

	private static final String _NULL_VALUE = "NULL_VALUE";

}