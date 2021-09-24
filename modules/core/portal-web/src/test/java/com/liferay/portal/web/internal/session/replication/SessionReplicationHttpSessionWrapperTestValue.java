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

package com.liferay.portal.web.internal.session.replication;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Tina Tian
 */
public class SessionReplicationHttpSessionWrapperTestValue
	implements Serializable {

	public SessionReplicationHttpSessionWrapperTestValue(String value) {
		_value = value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				SessionReplicationHttpSessionWrapperTestValue)) {

			return false;
		}

		SessionReplicationHttpSessionWrapperTestValue
			sessionReplicationHttpSessionWrapperTestValue =
				(SessionReplicationHttpSessionWrapperTestValue)object;

		if (Objects.equals(
				_value, sessionReplicationHttpSessionWrapperTestValue._value)) {

			return true;
		}

		return false;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return _value.hashCode();
	}

	private final String _value;

}