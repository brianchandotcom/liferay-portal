/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Alexander Chow
 */
public abstract class BasePreferencesImpl implements Serializable {

	public BasePreferencesImpl(
		long companyId, long ownerId, int ownerType,
		Map<String, Preference> preferences) {

		_companyId = companyId;
		_ownerId = ownerId;
		_ownerType = ownerType;
		_originalPreferences = preferences;
	}

	public Map<String, String[]> getMap() {
		Map<String, String[]> map = new ConcurrentHashMap<String, String[]>();

		Map<String, Preference> preferences = getPreferences();

		for (Map.Entry<String, Preference> entry : preferences.entrySet()) {
			String key = entry.getKey();
			Preference preference = entry.getValue();

			String[] actualValues = getActualValues(preference.getValues());

			if (actualValues != null) {
				map.put(key, actualValues);
			}
		}

		return Collections.unmodifiableMap(map);
	}

	public Enumeration<String> getNames() {
		Map<String, Preference> preferences = getPreferences();

		return Collections.enumeration(preferences.keySet());
	}

	public long getOwnerId() {
		return _ownerId;
	}

	public int getOwnerType() {
		return _ownerType;
	}

	public String getValue(String key, String def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Preference> preferences = getPreferences();

		Preference preference = preferences.get(key);

		String[] values = null;

		if (preference != null) {
			values = preference.getValues();
		}

		if ((values != null) && (values.length > 0)) {
			return getActualValue(values[0]);
		}
		else {
			return getActualValue(def);
		}
	}

	public String[] getValues(String key, String[] def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Preference> preferences = getPreferences();

		Preference preference = preferences.get(key);

		String[] values = null;

		if (preference != null) {
			values = preference.getValues();
		}

		if ((values != null) && (values.length > 0)) {
			return getActualValues(values);
		}
		else {
			return getActualValues(def);
		}
	}

	public boolean isReadOnly(String key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Preference> preferences = getPreferences();

		Preference preference = preferences.get(key);

		if ((preference != null) && preference.isReadOnly()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void reset() {
		Map<String, Preference> modifiedPreferences = getModifiedPreferences();

		modifiedPreferences.clear();
	}

	public abstract void reset(String key) throws ReadOnlyException;

	public void setValue(String key, String value) throws ReadOnlyException {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		value = getXmlSafeValue(value);

		Map<String, Preference> modifiedPreferences = getModifiedPreferences();

		Preference preference = modifiedPreferences.get(key);

		if (preference == null) {
			preference = new Preference(key, value);

			modifiedPreferences.put(key, preference);
		}

		if (preference.isReadOnly()) {
			throw new ReadOnlyException(key);
		}
		else {
			preference.setValues(new String[] {value});
		}
	}

	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		if (key == null) {
			throw new IllegalArgumentException();
		}

		values = getXmlSafeValues(values);

		Map<String, Preference> modifiedPreferences = getModifiedPreferences();

		Preference preference = modifiedPreferences.get(key);

		if (preference == null) {
			preference = new Preference(key, values);

			modifiedPreferences.put(key, preference);
		}

		if (preference.isReadOnly()) {
			throw new ReadOnlyException(key);
		}
		else {
			preference.setValues(values);
		}
	}

	public abstract void store() throws IOException, ValidatorException;

	protected String getActualValue(String value) {
		if ((value == null) || (value.equals(_NULL_VALUE))) {
			return null;
		}
		else {
			return XMLFormatter.fromCompactSafe(value);
		}
	}

	protected String[] getActualValues(String[] values) {
		if (values == null) {
			return null;
		}

		if ((values.length == 1) && (getActualValue(values[0]) == null)) {
			return null;
		}

		String[] actualValues = new String[values.length];

		System.arraycopy(values, 0, actualValues, 0, values.length);

		for (int i = 0; i < actualValues.length; i++) {
			actualValues[i] = getActualValue(actualValues[i]);
		}

		return actualValues;
	}

	protected long getCompanyId() {
		return _companyId;
	}

	protected Map<String, Preference> getModifiedPreferences() {
		if (_modifiedPreferences == null) {
			_modifiedPreferences = new ConcurrentHashMap<String, Preference>();

			for (Map.Entry<String, Preference> entry :
					_originalPreferences.entrySet()) {

				String key = entry.getKey();
				Preference preference = entry.getValue();

				_modifiedPreferences.put(key, (Preference)preference.clone());
			}
		}

		return _modifiedPreferences;
	}

	protected Map<String, Preference> getOriginalPreferences() {
		return _originalPreferences;
	}

	protected Map<String, Preference> getPreferences() {
		if (_modifiedPreferences == null) {
			if (_originalPreferences ==
					Collections.<String, Preference>emptyMap()) {

				_originalPreferences =
					new ConcurrentHashMap<String, Preference>();
			}

			return _originalPreferences;
		}
		else {
			return _modifiedPreferences;
		}
	}

	protected String getXmlSafeValue(String value) {
		if (value == null) {
			return _NULL_VALUE;
		}
		else {
			return XMLFormatter.toCompactSafe(value);
		}
	}

	protected String[] getXmlSafeValues(String[] values) {
		if (values == null) {
			return new String[] {getXmlSafeValue(null)};
		}

		String[] xmlSafeValues = new String[values.length];

		System.arraycopy(values, 0, xmlSafeValues, 0, values.length);

		for (int i = 0; i < xmlSafeValues.length; i++) {
			if (xmlSafeValues[i] == null) {
				xmlSafeValues[i] = getXmlSafeValue(xmlSafeValues[i]);
			}
		}

		return xmlSafeValues;
	}

	private static final String _NULL_VALUE = "NULL_VALUE";

	private long _companyId;
	private Map<String, Preference> _modifiedPreferences;
	private Map<String, Preference> _originalPreferences;
	private long _ownerId;
	private int _ownerType;

}