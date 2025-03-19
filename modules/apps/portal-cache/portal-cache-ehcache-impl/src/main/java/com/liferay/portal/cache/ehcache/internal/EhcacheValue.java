/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal;

import java.io.Serializable;

import java.time.Duration;

import java.util.Objects;

/**
 * @author Dante Wang
 */
public class EhcacheValue implements Serializable {

	public EhcacheValue(Object value, Duration timeToLive) {
		_value = value;
		_timeToLive = timeToLive;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof EhcacheValue)) {
			return false;
		}

		EhcacheValue ehcacheValue = (EhcacheValue)other;

		return Objects.equals(_value, ehcacheValue._value);
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