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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.xml.simple.Element;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class PortletPreferencesImpl
	implements Cloneable, PortletPreferences, Serializable {

	public PortletPreferencesImpl() {
		this(
			0, 0, 0, 0, null, null, Collections.<String, Preference>emptyMap());
	}

	public PortletPreferencesImpl(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String xml, Map<String, Preference> preferences) {

		this.companyId = companyId;
		_ownerId = ownerId;
		_ownerType = ownerType;
		_plid = plid;
		_portletId = portletId;
		_originalXML = xml;
		_originalPreferences = preferences;
	}

	public PortletPreferencesImpl(
		String xml, Map<String, Preference> preferences) {

		this(0, 0, 0, 0, null, xml, preferences);
	}

	@Override
	public Object clone() {
		return new PortletPreferencesImpl(
			companyId, getOwnerId(), getOwnerType(), _plid, _portletId,
			getOriginalXML(), getOriginalPreferences());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortletPreferencesImpl)) {
			return false;
		}

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)object;

		if ((companyId == portletPreferencesImpl.companyId) &&
			(getOwnerId() == portletPreferencesImpl.getOwnerId()) &&
			(getOwnerType() == portletPreferencesImpl.getOwnerType()) &&
			(getPlid() == portletPreferencesImpl.getPlid()) &&
			getPortletId().equals(portletPreferencesImpl.getPortletId()) &&
			getPreferences().equals(portletPreferencesImpl.getPreferences())) {

			return true;
		}

		return false;
	}

	@Override
	public Map<String, String[]> getMap() {
		Map<String, Preference> preferences = getPreferences();

		if (preferences.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<String, String[]> map = new HashMap<>();

		for (Map.Entry<String, Preference> entry : preferences.entrySet()) {
			String key = entry.getKey();

			Preference preference = entry.getValue();

			map.put(key, getActualValues(preference.getValues()));
		}

		return map;
	}

	@Override
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

	public long getPlid() {
		return _plid;
	}

	public Map<String, Preference> getPreferences() {
		if (_modifiedPreferences != null) {
			return _modifiedPreferences;
		}

		return _originalPreferences;
	}

	@Override
	public String getValue(String key, String def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Preference> preferences = getPreferences();

		Preference preference = preferences.get(key);

		if (preference == null) {
			return def;
		}

		String[] values = preference.getValues();

		if (isNull(values)) {
			return def;
		}

		return getActualValue(values[0]);
	}

	@Override
	public String[] getValues(String key, String[] def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Preference> preferences = getPreferences();

		Preference preference = preferences.get(key);

		if (preference == null) {
			return def;
		}

		String[] values = preference.getValues();

		if (isNull(values)) {
			return def;
		}

		return getActualValues(values);
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, companyId);

		hashCode = HashUtil.hash(hashCode, getOwnerId());
		hashCode = HashUtil.hash(hashCode, getOwnerType());
		hashCode = HashUtil.hash(hashCode, _plid);
		hashCode = HashUtil.hash(hashCode, _portletId);
		hashCode = HashUtil.hash(hashCode, getPreferences());

		return hashCode;
	}

	@Override
	public boolean isReadOnly(String key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Preference> preferences = getPreferences();

		Preference preference = preferences.get(key);

		if ((preference != null) && preference.isReadOnly()) {
			return true;
		}

		return false;
	}

	public void reset() {
		_modifiedPreferences = null;
	}

	@Override
	public void reset(String key) throws ReadOnlyException {
		if (isReadOnly(key)) {
			throw new ReadOnlyException(key);
		}

		if ((_defaultPreferences == null) && (_portletId != null)) {
			try {
				_defaultPreferences =
					PortletPreferencesLocalServiceUtil.getDefaultPreferences(
						companyId, _portletId);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}
			}
		}

		String[] defaultValues = null;

		if (_defaultPreferences != null) {
			defaultValues = _defaultPreferences.getValues(key, defaultValues);
		}

		if (defaultValues != null) {
			setValues(key, defaultValues);
		}
		else {
			Map<String, Preference> modifiedPreferences =
				getModifiedPreferences();

			modifiedPreferences.remove(key);
		}
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	@Override
	public void setValue(String key, String value) throws ReadOnlyException {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		value = getXMLSafeValue(value);

		Map<String, Preference> modifiedPreferences = getModifiedPreferences();

		Preference preference = modifiedPreferences.get(key);

		if ((preference != null) && preference.isReadOnly()) {
			throw new ReadOnlyException(key);
		}

		modifiedPreferences.put(key, new Preference(key, value));
	}

	@Override
	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		if (key == null) {
			throw new IllegalArgumentException();
		}

		values = getXMLSafeValues(values);

		Map<String, Preference> modifiedPreferences = getModifiedPreferences();

		Preference preference = modifiedPreferences.get(key);

		if ((preference != null) && preference.isReadOnly()) {
			throw new ReadOnlyException(key);
		}

		modifiedPreferences.put(key, new Preference(key, values));
	}

	public int size() {
		Map<String, Preference> preferences = getPreferences();

		return preferences.size();
	}

	@Override
	public void store() throws IOException, ValidatorException {
		if (_portletId == null) {
			throw new UnsupportedOperationException();
		}

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, _portletId);

			if (portlet != null) {
				PreferencesValidator preferencesValidator =
					PortalUtil.getPreferencesValidator(portlet);

				if (preferencesValidator != null) {
					preferencesValidator.validate(this);
				}
			}

			PortletPreferencesLocalServiceUtil.updatePreferences(
				getOwnerId(), getOwnerType(), _plid, _portletId, this);
		}
		catch (SystemException systemException) {
			throw new IOException(systemException);
		}
	}

	protected String getActualValue(String value) {
		if ((value == null) || value.equals(_NULL_VALUE)) {
			return null;
		}

		return XMLUtil.fromCompactSafe(value);
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
			else if (actualValue.equals(_NULL_ELEMENT)) {
				return new String[] {null};
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

	protected Map<String, Preference> getModifiedPreferences() {
		if (_modifiedPreferences == null) {
			_modifiedPreferences = new ConcurrentHashMap<>(
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

	protected String getPortletId() {
		return _portletId;
	}

	protected String getXMLSafeValue(String value) {
		if (value == null) {
			return _NULL_VALUE;
		}

		return XMLUtil.toCompactSafe(value);
	}

	protected String[] getXMLSafeValues(String[] values) {
		if (values == null) {
			return new String[] {_NULL_VALUE};
		}

		if ((values.length == 1) && (values[0] == null)) {
			return new String[] {_NULL_ELEMENT};
		}

		String[] xmlSafeValues = new String[values.length];

		for (int i = 0; i < xmlSafeValues.length; i++) {
			xmlSafeValues[i] = getXMLSafeValue(values[i]);
		}

		return xmlSafeValues;
	}

	protected boolean isNull(String[] values) {
		if (ArrayUtil.isEmpty(values) ||
			((values.length == 1) && (getActualValue(values[0]) == null))) {

			return true;
		}

		return false;
	}

	protected void setOriginalPreferences(
		Map<String, Preference> originalPreferences) {

		_originalPreferences = originalPreferences;
	}

	protected void setOriginalXML(String originalXML) {
		_originalXML = originalXML;
	}

	protected String toXML() {
		if ((_modifiedPreferences == null) && (_originalXML != null)) {
			return _originalXML;
		}

		Map<String, Preference> preferences = getPreferences();

		if ((preferences == null) || preferences.isEmpty()) {
			return PortletConstants.DEFAULT_PREFERENCES;
		}

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

	protected long companyId;

	private static final String _NULL_ELEMENT = "NULL_ELEMENT";

	private static final String _NULL_VALUE = "NULL_VALUE";

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesImpl.class);

	private PortletPreferences _defaultPreferences;
	private Map<String, Preference> _modifiedPreferences;
	private Map<String, Preference> _originalPreferences;
	private String _originalXML;
	private final long _ownerId;
	private final int _ownerType;
	private long _plid;
	private String _portletId;

}