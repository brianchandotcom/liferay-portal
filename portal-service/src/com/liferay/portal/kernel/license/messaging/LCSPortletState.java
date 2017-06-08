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

package com.liferay.portal.kernel.license.messaging;

/**
 * @author Igor Beslic
 */
public enum LCSPortletState {

	GOOD(1), NO_AVAILABLE_SERVERS(2), NO_CONNECTION(3), NOT_REGISTERED(5),
	NO_SUBSCRIPTION(4), PLUGIN_ABSENT(0), UNDEFINED(Integer.MAX_VALUE);

	public static LCSPortletState valueOf(int intValue) {
		if (intValue == 0) {
			return PLUGIN_ABSENT;
		}
		else if (intValue == 1) {
			return GOOD;
		}
		else if (intValue == 2) {
			return NO_AVAILABLE_SERVERS;
		}
		else if (intValue == 3) {
			return NO_CONNECTION;
		}
		else if (intValue == 4) {
			return NO_SUBSCRIPTION;
		}
		else if (intValue == 5) {
			return NOT_REGISTERED;
		}

		return UNDEFINED;
	}

	public int intValue() {
		return _state;
	}

	private LCSPortletState(int state) {
		_state = state;
	}

	private int _state;

}