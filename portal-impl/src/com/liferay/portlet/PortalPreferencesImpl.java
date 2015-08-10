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
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.persistence.PortalPreferencesUtil;

import java.io.IOException;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
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

	public static final TransactionAttribute SUPPORTS_TRANSACTION_ATTRIBUTE;

	static {
		TransactionAttribute.Builder builder =
			new TransactionAttribute.Builder();

		builder.setPropagation(Propagation.SUPPORTS);
		builder.setReadOnly(true);
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		SUPPORTS_TRANSACTION_ATTRIBUTE = builder.build();
	}

	public PortalPreferencesImpl() {
		this(0, 0, null, Collections.<String, Preference>emptyMap(), false);
	}

	public PortalPreferencesImpl(
		com.liferay.portal.model.PortalPreferences portalPreferences,
		boolean signedIn) {

		super(
			portalPreferences.getOwnerId(), portalPreferences.getOwnerType(),
			portalPreferences.getPreferences(), null);

		setOriginalPreferences(
			PortletPreferencesFactoryImpl.createPreferencesMap(
				portalPreferences.getPreferences()));

		_portalPreferences = (com.liferay.portal.model.PortalPreferences)
			portalPreferences.clone();

		_signedIn = signedIn;
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
		else {
			return new PortalPreferencesImpl(_portalPreferences, isSignedIn());
		}
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
	public void setValue(
		final String namespace, final String key, final String value) {

		if (Validator.isNull(key) || key.equals(_RANDOM_KEY)) {
			return;
		}

		try {
			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() throws ReadOnlyException {
					String encodedKey = _encodeKey(namespace, key);

					if (value != null) {
						PortalPreferencesImpl.super.setValue(encodedKey, value);
					}
					else {
						reset(encodedKey);
					}

					return null;
				}

			};

			if (_signedIn) {
				retryableStore(callable, _encodeKey(namespace, key));
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
	public void setValues(
		final String namespace, final String key, final String[] values) {

		if (Validator.isNull(key) || key.equals(_RANDOM_KEY)) {
			return;
		}

		try {
			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() throws ReadOnlyException {
					String encodedKey = _encodeKey(namespace, key);

					if (values != null) {
						PortalPreferencesImpl.super.setValues(
							encodedKey, values);
					}
					else {
						reset(encodedKey);
					}

					return null;
				}

			};

			if (_signedIn) {
				retryableStore(callable, _encodeKey(namespace, key));
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

				_portalPreferences = reload(getOwnerId(), getOwnerType());
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

					com.liferay.portal.model.PortalPreferences
						portalPreferences = reload(ownerId, ownerType);

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

					setPortalPreferences(portalPreferences);
				}
				else {
					throw e;
				}
			}
		}
	}

	protected void setPortalPreferences(
		com.liferay.portal.model.PortalPreferences portalPreferences) {

		_portalPreferences = portalPreferences;
	}

	private String _encodeKey(String namespace, String key) {
		if (Validator.isNull(namespace)) {
			return key;
		}
		else {
			return namespace.concat(StringPool.POUND).concat(key);
		}
	}

	private com.liferay.portal.model.PortalPreferences reload(
			final long ownerId, final int ownerType)
		throws Throwable {

		return TransactionInvokerUtil.invoke(
			SUPPORTS_TRANSACTION_ATTRIBUTE,
			new Callable<com.liferay.portal.model.PortalPreferences>() {

				@Override
				public com.liferay.portal.model.PortalPreferences call() {
					return PortalPreferencesUtil.fetchByO_O(
						ownerId, ownerType, false);
				}

			});
	}

	private static final String _RANDOM_KEY = "r";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalPreferencesImpl.class);

	private com.liferay.portal.model.PortalPreferences _portalPreferences;
	private boolean _signedIn;
	private long _userId;

}