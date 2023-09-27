/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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