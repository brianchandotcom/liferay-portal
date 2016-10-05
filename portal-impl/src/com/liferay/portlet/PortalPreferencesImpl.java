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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.PortalPreferencesUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.portlet.ReadOnlyException;

import org.hibernate.StaleObjectStateException;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class PortalPreferencesImpl
	extends BasePreferencesImpl
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
		this(0, 0, null, Collections.<String, Preference>emptyMap(), false);
	}

	public PortalPreferencesImpl(
		com.liferay.portal.kernel.model.PortalPreferences portalPreferences,
		boolean signedIn) {

		this(
			portalPreferences.getOwnerId(), portalPreferences.getOwnerType(),
			portalPreferences.getPreferences(),
			PortletPreferencesFactoryImpl.createPreferencesMap(
				portalPreferences.getPreferences()),
			signedIn);

		_portalPreferences =
			(com.liferay.portal.kernel.model.PortalPreferences)
				portalPreferences.clone();
	}

	public PortalPreferencesImpl(
		long ownerId, int ownerType, String xml,
		Map<String, Preference> preferences, boolean signedIn) {

		super(ownerId, ownerType, xml, preferences);

		_signedIn = signedIn;
	}

	@Override
	public PortalPreferencesImpl clone() {
		if (_portalPreferences == null) {
			return new PortalPreferencesImpl(
				getOwnerId(), getOwnerType(), getOriginalXML(),
				new HashMap<>(getOriginalPreferences()), isSignedIn());
		}

		return new PortalPreferencesImpl(_portalPreferences, isSignedIn());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortalPreferencesImpl)) {
			return false;
		}

		PortalPreferencesImpl portalPreferences = (PortalPreferencesImpl)obj;

		if ((getOwnerId() == portalPreferences.getOwnerId()) &&
			(getOwnerType() == portalPreferences.getOwnerType()) &&
			getPreferences().equals(portalPreferences.getPreferences())) {

			return true;
		}
		else {
			return false;
		}
	}

	public long getMvccVersion() {
		if (_portalPreferences == null) {
			return -1;
		}

		return _portalPreferences.getMvccVersion();
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

		return super.getValue(key, defaultValue);
	}

	@Override
	public String[] getValues(String namespace, String key) {
		return getValues(namespace, key, null);
	}

	@Override
	public String[] getValues(
		String namespace, String key, String[] defaultValue) {

		key = _encodeKey(namespace, key);

		return super.getValues(key, defaultValue);
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

	@Override
	public void reset(final String key) throws ReadOnlyException {
		if (isReadOnly(key)) {
			throw new ReadOnlyException(key);
		}

		String[] values = super.getValues(key, null);

		if (values == null) {
			return;
		}

		Callable<Void> callable = new Callable<Void>() {

			@Override
			public Void call() {
				Map<String, Preference> modifiedPreferences =
					getModifiedPreferences();

				modifiedPreferences.remove(key);

				return null;
			}

		};

		try {
			retryableStore(callable, key);
		}
		catch (ConcurrentModificationException cme) {
			throw cme;
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	@Override
	public void resetValues(String namespace) {
		Map<String, Preference> preferences = getPreferences();

		try {
			for (Map.Entry<String, Preference> entry : preferences.entrySet()) {
				String key = entry.getKey();

				if (key.startsWith(namespace) && !isReadOnly(key)) {
					reset(key);
				}
			}
		}
		catch (ConcurrentModificationException cme) {
			throw cme;
		}
		catch (Throwable t) {
			_log.error(t, t);
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
	public void setValue(String namespace, String key, final String value) {
		if (Validator.isNull(key) || key.equals(_RANDOM_KEY)) {
			return;
		}

		final String encodedKey = _encodeKey(namespace, key);

		try {
			if (value == null) {
				reset(encodedKey);

				return;
			}

			String[] oldValues = super.getValues(encodedKey, null);

			if ((oldValues != null) && (oldValues.length == 1) &&
				value.equals(oldValues[0])) {

				return;
			}

			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() throws ReadOnlyException {
					PortalPreferencesImpl.super.setValue(encodedKey, value);

					return null;
				}

			};

			if (_signedIn) {
				retryableStore(callable, encodedKey);
			}
			else {
				callable.call();
			}
		}
		catch (ConcurrentModificationException cme) {
			throw cme;
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	@Override
	public void setValues(String namespace, String key, final String[] values) {
		if (Validator.isNull(key) || key.equals(_RANDOM_KEY)) {
			return;
		}

		final String encodedKey = _encodeKey(namespace, key);

		try {
			if (values == null) {
				reset(encodedKey);

				return;
			}

			if (values.length == 1) {
				setValue(namespace, key, values[0]);

				return;
			}

			String[] oldValues = super.getValues(encodedKey, null);

			if (oldValues != null) {
				Set<String> valuesSet = SetUtil.fromArray(values);
				Set<String> oldValuesSet = SetUtil.fromArray(oldValues);

				if (valuesSet.equals(oldValuesSet)) {
					return;
				}
			}

			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() throws ReadOnlyException {
					PortalPreferencesImpl.super.setValues(encodedKey, values);

					return null;
				}

			};

			if (_signedIn) {
				retryableStore(callable, encodedKey);
			}
			else {
				callable.call();
			}
		}
		catch (ConcurrentModificationException cme) {
			throw cme;
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	@Override
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
		catch (Throwable t) {
			throw new IOException(t);
		}
	}

	protected boolean isCausedByStaleObjectException(Throwable t) {
		Throwable cause = t.getCause();

		while (t != cause) {
			if (t instanceof StaleObjectStateException) {
				return true;
			}

			if (cause == null) {
				return false;
			}

			t = cause;

			cause = t.getCause();
		}

		return false;
	}

	protected void retryableStore(Callable<?> callable, String key)
		throws Throwable {

		String[] originalValues = super.getValues(key, null);

		while (true) {
			try {
				callable.call();

				store();

				return;
			}
			catch (Exception e) {
				if (isCausedByStaleObjectException(e)) {
					long ownerId = getOwnerId();
					int ownerType = getOwnerType();

					com.liferay.portal.kernel.model.PortalPreferences
						portalPreferences = _reload(ownerId, ownerType);

					if (portalPreferences == null) {
						continue;
					}

					PortalPreferencesImpl portalPreferencesImpl =
						new PortalPreferencesImpl(
							portalPreferences, isSignedIn());

					if (!Arrays.equals(
							originalValues,
							portalPreferencesImpl.getValues(
								key, (String[])null))) {

						throw new ConcurrentModificationException();
					}

					reset();

					setOriginalPreferences(
						portalPreferencesImpl.getOriginalPreferences());

					setOriginalXML(portalPreferences.getPreferences());

					_portalPreferences = portalPreferences;
				}
				else {
					throw e;
				}
			}
		}
	}

	private String _encodeKey(String namespace, String key) {
		if (Validator.isNull(namespace)) {
			return key;
		}
		else {
			return namespace.concat(StringPool.POUND).concat(key);
		}
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

	private static final String _RANDOM_KEY = "r";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalPreferencesImpl.class);

	private com.liferay.portal.kernel.model.PortalPreferences
		_portalPreferences;
	private boolean _signedIn;
	private long _userId;

}