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

package com.liferay.portal.kernel.notifications;

/**
 * @author Jonathan Lee
 */
public class UserNotificationDeliveryType {

	public UserNotificationDeliveryType(
		String name, int type, boolean defualtValue, boolean modifiable) {

		_default = defualtValue;
		_modifiable = modifiable;
		_name = name;
		_type = type;
	}

	public String getName() {
		return _name;
	}

	public int getType() {
		return _type;
	}

	public boolean isDefault() {
		return _default;
	}

	public boolean isModifiable() {
		return _modifiable;
	}

	private static boolean _default;
	private static boolean _modifiable;
	private static String _name;
	private static int _type;

}