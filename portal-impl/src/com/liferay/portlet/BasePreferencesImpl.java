/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Alexander Chow
 * @author Shuyang Zhou
 */
public abstract class BasePreferencesImpl implements Serializable {

	public BasePreferencesImpl(
		long companyId, long ownerId, int ownerType, String xml,
		Map<String, Preference> preferences) {

		_companyId = companyId;
		_ownerId = ownerId;
		_ownerType = ownerType;
		_originalXML = xml;
		_originalPreferences = preferences;
	}

	public Map<String, String[]> getMap() {
		Map<String, String[]> map = new HashMap<String, String[]>();

		Map<String, Preference> preferences = getPreferences();

		for (Map.Entry<String, Preference> entry : preferences.entrySet()) {
			String key = entry.getKey();
			Preference preference = entry.getValue();

			String[] actualValues = getActualValues(preference.getValues());

			map.put(key, actualValues);
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
		_modifiedPreferences = new ConcurrentHashMap<String, Preference>();
	}

	public abstract void reset(String key) throws ReadOnlyException;

	public void setValue(String key, String value) throws ReadOnlyException {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		value = getXMLSafeValue(value);

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
			preference = (Preference)preference.clone();

			modifiedPreferences.put(key, preference);

			preference.setValues(new String[] {value});
		}
	}

	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		if (key == null) {
			throw new IllegalArgumentException();
		}

		values = getXMLSafeValues(values);

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
			preference = (Preference)preference.clone();

			modifiedPreferences.put(key, preference);

			preference.setValues(values);
		}
	}

	public int size() {
		Map<String, Preference> preferences = getPreferences();

		return preferences.size();
	}

	public abstract void store() throws IOException, ValidatorException;

	protected String getActualValue(String value) {
		if ((value == null) || value.equals(_NULL_VALUE)) {
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

		if (values.length == 1) {
			String actualValue = getActualValue(values[0]);

			if (actualValue == null) {
				return null;
			}
			else {
				return new String[] {actualValue};
			}
		}

		String[] actualValues = new String[values.length];

		for (int i = 0; i < actualValues.length; i++) {
			actualValues[i] = getActualValue(values[i]);
		}

		return actualValues;
	}

	protected long getCompanyId() {
		return _companyId;
	}

	protected Map<String, Preference> getModifiedPreferences() {
		if (_modifiedPreferences == null) {
			_modifiedPreferences = new ConcurrentHashMap<String, Preference>(
				_originalPreferences);
		}

		return _modifiedPreferences;
	}

	protected Map<String, Preference> getOriginalPreferences() {
		return _originalPreferences;
	}

	protected String getOriginalXML() {
		return _originalXML;
	}

	protected Map<String, Preference> getPreferences() {
		if (_modifiedPreferences != null) {
			return _modifiedPreferences;
		}

		return _originalPreferences;
	}

	protected String getXMLSafeValue(String value) {
		if (value == null) {
			return _NULL_VALUE;
		}
		else {
			return XMLFormatter.toCompactSafe(value);
		}
	}

	protected String[] getXMLSafeValues(String[] values) {
		if (values == null) {
			return new String[] {_NULL_VALUE};
		}

		String[] xmlSafeValues = new String[values.length];

		for (int i = 0; i < xmlSafeValues.length; i++) {
			xmlSafeValues[i] = getXMLSafeValue(values[i]);
		}

		return xmlSafeValues;
	}

	protected String toXML() {
		if ((_modifiedPreferences == null) && (_originalXML != null)) {
			return _originalXML;
		}

		Map<String, Preference> preferences = getPreferences();

		Element portletPreferencesElement = new Element(
			"portlet-preferences", false);

		for (Map.Entry<String, Preference> entry : preferences.entrySet()) {
			Preference preference = entry.getValue();

			Element preferenceElement = portletPreferencesElement.addElement(
				"preference");

			preferenceElement.addElement("name", preference.getName());

			for (String value : preference.getValues()) {
				preferenceElement.addElement("value", value);
			}

			if (preference.isReadOnly()) {
				preferenceElement.addElement("read-only", Boolean.TRUE);
			}
		}

		return portletPreferencesElement.toXMLString();
	}

	private static final String _NULL_VALUE = "NULL_VALUE";

	private long _companyId;
	private Map<String, Preference> _modifiedPreferences;
	private Map<String, Preference> _originalPreferences;
	private String _originalXML;
	private long _ownerId;
	private int _ownerType;

}