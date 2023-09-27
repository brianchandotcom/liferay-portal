/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.license.messaging;

/**
 * @author Igor Beslic
 */
public enum LCSPortletState {

	GOOD(1), NO_AVAILABLE_SERVERS(2), NO_CONNECTION(3), NO_SUBSCRIPTION(4),
	NOT_REGISTERED(5), PLUGIN_ABSENT(0), UNDEFINED(Integer.MAX_VALUE);

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

	private final int _state;

}