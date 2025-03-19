/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal;

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
		return _expiryPolicy.getExpiryForAccess(key, value);
	}

	@Override
	public Duration getExpiryForCreation(Object key, Object value) {
		EhcacheValue ehcacheValue = (EhcacheValue)value;

		return ehcacheValue.getTimeToLive();
	}

	@Override
	public Duration getExpiryForUpdate(
		Object key, Supplier<?> oldValue, Object newValue) {

		EhcacheValue ehcacheValue = (EhcacheValue)newValue;

		return ehcacheValue.getTimeToLive();
	}

	private final ExpiryPolicy<Object, Object> _expiryPolicy;

}