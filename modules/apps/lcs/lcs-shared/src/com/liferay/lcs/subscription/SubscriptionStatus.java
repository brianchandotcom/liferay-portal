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

package com.liferay.lcs.subscription;

/**
 * @author  Igor Beslic
 * @version LCS 1.7.1
 * @since   LCS 1.3
 */
public enum SubscriptionStatus {

	ACTIVE("subscription-status-active", 1),
	CLOSED("subscription-status-closed", 2),
	ON_HOLD("subscription-status-on-hold", 3),
	UNDEFINED("subscription-status-undefined", 0);

	public static SubscriptionStatus valueOf(int status) {
		if (status == 1) {
			return ACTIVE;
		}
		else if (status == 2) {
			return CLOSED;
		}
		else if (status == 3) {
			return ON_HOLD;
		}

		return UNDEFINED;
	}

	public String getLabel() {
		return _label;
	}

	public int getStatus() {
		return _status;
	}

	private SubscriptionStatus(String label, int status) {
		_label = label;
		_status = status;
	}

	private String _label;
	private int _status;

}