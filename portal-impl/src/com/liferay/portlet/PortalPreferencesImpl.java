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

import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.persistence.PortalPreferencesPersistence;
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

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class PortalPreferencesImpl
	extends BasePreferencesImpl
	implements Cloneable, PortalPreferences, Serializable {

	public static final TransactionAttribute TRANSACTION_ATTRIBUTE;

	static {
		TransactionAttribute.Builder builder =
			new TransactionAttribute.Builder();

		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		TRANSACTION_ATTRIBUTE = builder.build();
	}

	public PortalPreferencesImpl() {
		this(0, 0, null, Collections.<String, Preference>emptyMap(), false);
	}

	public PortalPreferencesImpl(
		long ownerId, int ownerType, String xml,
		Map<String, Preference> preferences, boolean signedIn) {

		super(ownerId, ownerType, xml, preferences);

		_signedIn = signedIn;
	}

	@Override
	public PortalPreferencesImpl clone() {
		return new PortalPreferencesImpl(
			getOwnerId(), getOwnerType(), getOriginalXML(),
			new HashMap<String, Preference>(
				getOriginalPreferences()), isSignedIn());
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

		try {
			validateStore(
				new Callable<Void>() {

					@Override
					public Void call() {
						Map<String, Preference> modifiedPreferences =
							getModifiedPreferences();

						modifiedPreferences.remove(key);

						return null;
					}

				}, key);
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
		catch (Exception e) {
			_log.error(e, e);
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

					if (value == null) {
						reset(encodedKey);
					}
					else {
						PortalPreferencesImpl.super.setValue(encodedKey, value);
					}

					return null;
				}

			};

			if (_signedIn) {
				validateStore(callable, _encodeKey(namespace, key));
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

					if (values == null) {
						reset(encodedKey);
					}
					else {
						PortalPreferencesImpl.super.setValues(
							encodedKey, values);
					}

					return null;
				}

			};

			if (_signedIn) {
				validateStore(callable, _encodeKey(namespace, key));
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

	private Callable<Boolean> _createValidateCallable(
		final String[] originalValues, final String key) {

		return new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				com.liferay.portal.model.PortalPreferences
					preferences = PortalPreferencesUtil.fetchByO_O(
						getOwnerId(), getOwnerType(), false);

				PortalPreferencesPersistence portalPreferencesPersistence =
					PortalPreferencesUtil.getPersistence();

				Session session =
					portalPreferencesPersistence.getCurrentSession();

				session.evict(preferences);

				preferences = (com.liferay.portal.model.PortalPreferences)
					session.get(
						com.liferay.portal.model.impl.
							PortalPreferencesImpl.class,
						preferences.getPrimaryKey(), LockMode.UPGRADE);

				PortalPreferencesImpl portalPreferencesImpl =
					(PortalPreferencesImpl)
						PortletPreferencesFactoryUtil.fromXML(
							getOwnerId(), getOwnerType(),
							preferences.getPreferences());

				String originalXML = getOriginalXML();

				if (originalXML.equals(preferences.getPreferences())) {
					store();

					return true;
				}

				if (!Arrays.equals(
						originalValues,
						portalPreferencesImpl.getValues(key, (String[])null))) {

					return false;
				}

				reset();

				setOriginalPreferences(
					portalPreferencesImpl.getOriginalPreferences());

				setOriginalXML(preferences.getPreferences());

				return null;
			}

		};
	}

	protected void validateStore(final Callable<?> callable, final String key)
		throws Throwable {

		while (true) {
			String[] originalValues = super.getValues(key, null);

			callable.call();

			Boolean result = TransactionInvokerUtil.invoke(
				TRANSACTION_ATTRIBUTE,
				_createValidateCallable(originalValues, key));

			if (result == null) {
				continue;
			}

			if (!result) {
				PortalPreferencesWrapperCacheUtil.remove(
					getOwnerId(), getOwnerType());

				throw new ConcurrentModificationException();
			}

			break;
		}
	}

	@Override
	public void store() throws IOException {
		try {
			PortalPreferencesLocalServiceUtil.updatePreferences(
				getOwnerId(), getOwnerType(), this);
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
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

	private static final String _RANDOM_KEY = "r";

	private static Log _log = LogFactoryUtil.getLog(
		PortalPreferencesImpl.class);

	private boolean _signedIn;
	private long _userId;

}