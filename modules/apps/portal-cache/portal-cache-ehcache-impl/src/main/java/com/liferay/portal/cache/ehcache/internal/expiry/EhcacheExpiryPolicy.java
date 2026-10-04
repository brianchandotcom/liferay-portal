/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.expiry;

import java.time.Duration;

import java.util.function.Supplier;

import org.ehcache.expiry.ExpiryPolicy;

/**
 * @author Dante Wang
 */
public class EhcacheExpiryPolicy implements ExpiryPolicy<Object, Object> {

	public EhcacheExpiryPolicy(ExpiryPolicy<Object, Object> expiryPolicy) {
		_expiryPolicy = expiryPolicy;
	}

	@Override
	public Duration getExpiryForAccess(Object key, Supplier<?> value) {
		EhcacheExpiryValue ehcacheExpiryValue = (EhcacheExpiryValue)value.get();

		Duration timeToLive = ehcacheExpiryValue.getTimeToLive();

		if (timeToLive.equals(ExpiryPolicy.INFINITE)) {
			return _expiryPolicy.getExpiryForAccess(key, value);
		}

		return null;
	}

	@Override
	public Duration getExpiryForCreation(Object key, Object value) {
		EhcacheExpiryValue ehcacheExpiryValue = (EhcacheExpiryValue)value;

		Duration timeToLive = ehcacheExpiryValue.getTimeToLive();

		if (timeToLive.equals(ExpiryPolicy.INFINITE)) {
			return _expiryPolicy.getExpiryForCreation(key, value);
		}

		return timeToLive;
	}

	@Override
	public Duration getExpiryForUpdate(
		Object key, Supplier<?> oldValue, Object newValue) {

		EhcacheExpiryValue ehcacheExpiryValue = (EhcacheExpiryValue)newValue;

		Duration timeToLive = ehcacheExpiryValue.getTimeToLive();

		if (timeToLive.equals(ExpiryPolicy.INFINITE)) {
			return _expiryPolicy.getExpiryForCreation(key, newValue);
		}

		return timeToLive;
	}

	private final ExpiryPolicy<Object, Object> _expiryPolicy;

}