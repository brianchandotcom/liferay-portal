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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.PortalPreferencesUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.simple.Element;

import java.io.IOException;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.StaleObjectStateException;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class PortalPreferencesImpl
	implements Cloneable, PortalPreferences, Serializable {

	public static final TransactionConfig SUPPORTS_TRANSACTION_CONFIG;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.SUPPORTS);
		builder.setReadOnly(true);
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		SUPPORTS_TRANSACTION_CONFIG = builder.build();
	}

	public PortalPreferencesImpl() {
		this(0, 0, null, Collections.emptyMap(), false);
	}

	public PortalPreferencesImpl(
		com.liferay.portal.kernel.model.PortalPreferences portalPreferences,
		boolean signedIn) {

		_ownerId = portalPreferences.getOwnerId();
		_ownerType = portalPreferences.getOwnerType();
		_originalXML = portalPreferences.getPreferences();
		_signedIn = signedIn;

		Map<String, Preference> preferencesMap =
			PortletPreferencesFactoryImpl.createPreferencesMap(
				portalPreferences.getPreferences());

		Map<String, String[]> preferences = new HashMap<>();

		for (Preference preference : preferencesMap.values()) {
			preferences.put(preference.getName(), preference.getValues());
		}

		_originalPreferences = preferences;

		_portalPreferences =
			(com.liferay.portal.kernel.model.PortalPreferences)
				portalPreferences.clone();
	}

	public PortalPreferencesImpl(
		long ownerId, int ownerType, String xml,
		Map<String, String[]> preferences, boolean signedIn) {

		_ownerId = ownerId;
		_ownerType = ownerType;
		_originalXML = xml;
		_originalPreferences = preferences;
		_signedIn = signedIn;
	}

	@Override
	public PortalPreferencesImpl clone() {
		if (_portalPreferences == null) {
			return new PortalPreferencesImpl(
				getOwnerId(), getOwnerType(), _originalXML,
				new HashMap<>(_getOriginalPreferences()), isSignedIn());
		}

		if (Objects.equals(_originalXML, _portalPreferences.getPreferences())) {
			PortalPreferencesImpl portalPreferencesImpl =
				new PortalPreferencesImpl(
					getOwnerId(), getOwnerType(), _originalXML,
					new HashMap<>(_getOriginalPreferences()), isSignedIn());

			portalPreferencesImpl._portalPreferences =
				(com.liferay.portal.kernel.model.PortalPreferences)
					_portalPreferences.clone();

			return portalPreferencesImpl;
		}

		return new PortalPreferencesImpl(_portalPreferences, isSignedIn());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortalPreferencesImpl)) {
			return false;
		}

		PortalPreferencesImpl portalPreferencesImpl =
			(PortalPreferencesImpl)object;

		if ((getOwnerId() == portalPreferencesImpl.getOwnerId()) &&
			(getOwnerType() == portalPreferencesImpl.getOwnerType()) &&
			Objects.equals(
				getPreferences(), portalPreferencesImpl.getPreferences())) {

			return true;
		}

		return false;
	}

	public Map<String, String[]> getMap(String namespace) {
		Map<String, String[]> preferences = getPreferences();

		if (preferences.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<String, String[]> preferenceMap = new HashMap<>();

		for (Map.Entry<String, String[]> entry : preferences.entrySet()) {
			String key = entry.getKey();

			if (Validator.isNull(namespace)) {
				if (key.indexOf(CharPool.POUND) < 0) {
					preferenceMap.put(key, entry.getValue());
				}
			}
			else if ((key.length() > namespace.length()) &&
					  key.startsWith(namespace) &&
					  (key.charAt(namespace.length()) == CharPool.POUND)) {

				preferenceMap.put(key, entry.getValue());
			}
		}

		return preferenceMap;
	}

	public long getMvccVersion() {
		if (_portalPreferences == null) {
			return -1;
		}

		return _portalPreferences.getMvccVersion();
	}

	public Enumeration<String> getNames(String namespace) {
		Map<String, String[]> preferences = getMap(namespace);

		return Collections.enumeration(preferences.keySet());
	}

	public long getOwnerId() {
		return _ownerId;
	}

	public int getOwnerType() {
		return _ownerType;
	}

	public Map<String, String[]> getPreferences() {
		if (_modifiedPreferences != null) {
			return _modifiedPreferences;
		}

		return _originalPreferences;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public String getValue(String namespace, String key) {
		return getValue(namespace, key, null);
	}

	@Override
	public String getValue(String namespace, String key, String defaultValue) {
		key = _encodeKey(namespace, key);

		if (key == null) {
			throw new IllegalArgumentException();
		}

		Map<String, String[]> preferences = getPreferences();

		String[] values = preferences.get(key);

		if (_isNull(values)) {
			return defaultValue;
		}

		return _getActualValue(values[0]);
	}

	@Override
	public String[] getValues(String namespace, String key) {
		return getValues(namespace, key, null);
	}

	@Override
	public String[] getValues(
		String namespace, String key, String[] defaultValue) {

		key = _encodeKey(namespace, key);

		return _getValues(key, defaultValue);
	}

	private String[] _getValues(String key, String[] def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Map<String, String[]> preferences = getPreferences();

		String[] values = preferences.get(key);

		if (_isNull(values)) {
			return def;
		}

		return _getActualValues(values);
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, getOwnerId());

		hashCode = HashUtil.hash(hashCode, getOwnerType());
		hashCode = HashUtil.hash(hashCode, getPreferences());

		return hashCode;
	}

	@Override
	public boolean isSignedIn() {
		return _signedIn;
	}

	public void reset(String namespace, String key) {
		String encodedKey = _encodeKey(namespace, key);

		String[] values = _getValues(encodedKey, null);

		if (values == null) {
			return;
		}

		Callable<Void> callable = new Callable<Void>() {

			@Override
			public Void call() {
				Map<String, String[]> modifiedPreferences =
					_getModifiedPreferences();

				modifiedPreferences.remove(encodedKey);

				return null;
			}

		};

		try {
			_retryableStore(callable, encodedKey);
		}
		catch (ConcurrentModificationException
					concurrentModificationException) {

			throw concurrentModificationException;
		}
		catch (Throwable throwable) {
			_log.error(throwable, throwable);
		}
	}

	@Override
	public void resetValues(String namespace) {
		Map<String, String[]> preferences = getPreferences();

		try {
			for (Map.Entry<String, String[]> entry : preferences.entrySet()) {
				String key = entry.getKey();

				if (Validator.isNull(namespace)) {
					if (key.indexOf(CharPool.POUND) < 0) {
						reset(null, key);
					}
				}
				else if ((key.length() > namespace.length()) &&
						 key.startsWith(namespace) &&
						 (key.charAt(namespace.length()) == CharPool.POUND)) {

					reset(namespace, key.substring(namespace.length() + 1));
				}
			}
		}
		catch (ConcurrentModificationException
					concurrentModificationException) {

			throw concurrentModificationException;
		}
		catch (Throwable throwable) {
			_log.error(throwable, throwable);
		}
	}

	@Override
	public void setSignedIn(boolean signedIn) {
		_signedIn = signedIn;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public void setValue(String namespace, String key, String value) {
		if (Validator.isNull(key) || key.equals(_RANDOM_KEY)) {
			return;
		}

		try {
			if (value == null) {
				reset(namespace, key);

				return;
			}

			String encodedKey = _encodeKey(namespace, key);

			String[] oldValues = _getValues(encodedKey, null);

			if ((oldValues != null) && (oldValues.length == 1) &&
				value.equals(oldValues[0])) {

				return;
			}

			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() {
					Map<String, String[]> modifiedPreferences =
						_getModifiedPreferences();

					modifiedPreferences.put(
						encodedKey, new String[] {_getXMLSafeValue(value)});

					return null;
				}

			};

			if (_signedIn) {
				_retryableStore(callable, encodedKey);
			}
			else {
				callable.call();
			}
		}
		catch (ConcurrentModificationException
					concurrentModificationException) {

			throw concurrentModificationException;
		}
		catch (Throwable throwable) {
			_log.error(throwable, throwable);
		}
	}

	@Override
	public void setValues(String namespace, String key, final String[] values) {
		if (Validator.isNull(key) || key.equals(_RANDOM_KEY)) {
			return;
		}

		try {
			if (values == null) {
				reset(namespace, key);

				return;
			}

			if (values.length == 1) {
				setValue(namespace, key, values[0]);

				return;
			}

			String encodedKey = _encodeKey(namespace, key);

			String[] oldValues = _getValues(encodedKey, null);

			if (oldValues != null) {
				Set<String> valuesSet = SetUtil.fromArray(values);
				Set<String> oldValuesSet = SetUtil.fromArray(oldValues);

				if (valuesSet.equals(oldValuesSet)) {
					return;
				}
			}

			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() {
					Map<String, String[]> modifiedPreferences =
						_getModifiedPreferences();

					modifiedPreferences.put(
						encodedKey, _getXMLSafeValues(values));

					return null;
				}

			};

			if (_signedIn) {
				_retryableStore(callable, encodedKey);
			}
			else {
				callable.call();
			}
		}
		catch (ConcurrentModificationException
					concurrentModificationException) {

			throw concurrentModificationException;
		}
		catch (Throwable throwable) {
			_log.error(throwable, throwable);
		}
	}

	@Override
	public int size() {
		Map<String, String[]> preferences = getPreferences();

		return preferences.size();
	}

	public void store() throws IOException {
		try {
			if (_portalPreferences == null) {
				_portalPreferences =
					PortalPreferencesLocalServiceUtil.updatePreferences(
						getOwnerId(), getOwnerType(), this);
			}
			else {
				PortalPreferencesWrapperCacheUtil.remove(
					getOwnerId(), getOwnerType());

				_portalPreferences.setPreferences(toXML());

				PortalPreferencesLocalServiceUtil.updatePortalPreferences(
					_portalPreferences);

				_portalPreferences = _reload(getOwnerId(), getOwnerType());
			}
		}
		catch (Throwable throwable) {
			throw new IOException(throwable);
		}
	}

	private String _getActualValue(String value) {
		if ((value == null) || value.equals(_NULL_VALUE)) {
			return null;
		}

		return XMLUtil.fromCompactSafe(value);
	}

	private String[] _getActualValues(String[] values) {
		if (values == null) {
			return null;
		}

		if (values.length == 1) {
			String actualValue = _getActualValue(values[0]);

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
			actualValues[i] = _getActualValue(values[i]);
		}

		return actualValues;
	}

	private Map<String, String[]> _getModifiedPreferences() {
		if (_modifiedPreferences == null) {
			_modifiedPreferences = new ConcurrentHashMap<>(
				_originalPreferences);
		}

		return _modifiedPreferences;
	}

	private Map<String, String[]> _getOriginalPreferences() {
		return _originalPreferences;
	}

	private String _getXMLSafeValue(String value) {
		if (value == null) {
			return _NULL_VALUE;
		}

		return XMLUtil.toCompactSafe(value);
	}

	private String[] _getXMLSafeValues(String[] values) {
		if (values == null) {
			return new String[] {_NULL_VALUE};
		}

		if ((values.length == 1) && (values[0] == null)) {
			return new String[] {_NULL_ELEMENT};
		}

		String[] xmlSafeValues = new String[values.length];

		for (int i = 0; i < xmlSafeValues.length; i++) {
			xmlSafeValues[i] = _getXMLSafeValue(values[i]);
		}

		return xmlSafeValues;
	}

	private boolean _isCausedByStaleObjectException(Throwable throwable) {
		Throwable causeThrowable = throwable.getCause();

		while (throwable != causeThrowable) {
			if (throwable instanceof StaleObjectStateException) {
				return true;
			}

			if (causeThrowable == null) {
				return false;
			}

			throwable = causeThrowable;

			causeThrowable = throwable.getCause();
		}

		return false;
	}

	private boolean _isNull(String[] values) {
		if (ArrayUtil.isEmpty(values) ||
			((values.length == 1) && (_getActualValue(values[0]) == null))) {

			return true;
		}

		return false;
	}

	private void _retryableStore(Callable<?> callable, String key)
		throws Throwable {

		String[] originalValues = _getValues(key, null);

		while (true) {
			try {
				callable.call();

				store();

				return;
			}
			catch (Exception exception) {
				if (_isCausedByStaleObjectException(exception)) {
					com.liferay.portal.kernel.model.PortalPreferences
						portalPreferences = _reload(
							getOwnerId(), getOwnerType());

					if (portalPreferences == null) {
						continue;
					}

					PortalPreferencesImpl portalPreferencesImpl =
						new PortalPreferencesImpl(
							portalPreferences, isSignedIn());

					if (!Arrays.equals(
							originalValues,
							portalPreferencesImpl._getValues(key, null))) {

						throw new ConcurrentModificationException();
					}

					_modifiedPreferences = null;

					_originalPreferences =
						portalPreferencesImpl._getOriginalPreferences();

					_originalXML = portalPreferences.getPreferences();

					_portalPreferences = portalPreferences;
				}
				else {
					throw exception;
				}
			}
		}
	}

	protected String toXML() {
		if ((_modifiedPreferences == null) && (_originalXML != null)) {
			return _originalXML;
		}

		Map<String, String[]> preferences = getPreferences();

		if ((preferences == null) || preferences.isEmpty()) {
			return PortletConstants.DEFAULT_PREFERENCES;
		}

		Element portletPreferencesElement = new Element(
			"portlet-preferences", false);

		for (Map.Entry<String, String[]> entry : preferences.entrySet()) {
			String[] values = entry.getValue();

			Element preferenceElement = portletPreferencesElement.addElement(
				"preference");

			preferenceElement.addElement("name", entry.getKey());

			for (String value : values) {
				preferenceElement.addElement("value", value);
			}
		}

		return portletPreferencesElement.toXMLString();
	}

	private String _encodeKey(String namespace, String key) {
		if (Validator.isNull(namespace)) {
			return key;
		}

		return StringBundler.concat(namespace, StringPool.POUND, key);
	}

	private com.liferay.portal.kernel.model.PortalPreferences _reload(
			final long ownerId, final int ownerType)
		throws Throwable {

		return TransactionInvokerUtil.invoke(
			SUPPORTS_TRANSACTION_CONFIG,
			new Callable<com.liferay.portal.kernel.model.PortalPreferences>() {

				@Override
				public com.liferay.portal.kernel.model.PortalPreferences
					call() {

					return PortalPreferencesUtil.fetchByO_O(
						ownerId, ownerType, false);
				}

			});
	}

	private static final String _NULL_ELEMENT = "NULL_ELEMENT";

	private static final String _NULL_VALUE = "NULL_VALUE";

	private static final String _RANDOM_KEY = "r";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalPreferencesImpl.class);

	private Map<String, String[]> _modifiedPreferences;
	private Map<String, String[]> _originalPreferences;
	private String _originalXML;
	private final long _ownerId;
	private final int _ownerType;
	private com.liferay.portal.kernel.model.PortalPreferences
		_portalPreferences;
	private boolean _signedIn;
	private long _userId;

}