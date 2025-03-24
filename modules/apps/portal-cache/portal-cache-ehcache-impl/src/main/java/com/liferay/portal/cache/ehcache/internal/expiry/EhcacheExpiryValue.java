/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.expiry;

import java.io.Serializable;

import java.time.Duration;

import java.util.Objects;

/**
 * @author Dante Wang
 */
public class EhcacheExpiryValue implements Serializable {

	public EhcacheExpiryValue(Object value, Duration timeToLive) {
		_value = value;
		_timeToLive = timeToLive;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof EhcacheExpiryValue ehcacheExpiryValue)) {
			return false;
		}

		return Objects.equals(_value, ehcacheExpiryValue._value);
	}

	public Duration getTimeToLive() {
		return _timeToLive;
	}

	public Object getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return _value.hashCode();
	}

	private final Duration _timeToLive;
	private final Object _value;

}