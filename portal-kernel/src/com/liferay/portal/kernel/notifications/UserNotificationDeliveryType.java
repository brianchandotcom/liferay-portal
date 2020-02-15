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