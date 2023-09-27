/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.notifications;

/**
 * @author Jonathan Lee
 */
public class UserNotificationDeliveryType {

	public UserNotificationDeliveryType(
		String name, int type, boolean defaultValue, boolean modifiable) {

		_name = name;
		_type = type;
		_defaultValue = defaultValue;
		_modifiable = modifiable;
	}

	public String getName() {
		return _name;
	}

	public int getType() {
		return _type;
	}

	public boolean isDefault() {
		return _defaultValue;
	}

	public boolean isModifiable() {
		return _modifiable;
	}

	private final boolean _defaultValue;
	private final boolean _modifiable;
	private final String _name;
	private final int _type;

}