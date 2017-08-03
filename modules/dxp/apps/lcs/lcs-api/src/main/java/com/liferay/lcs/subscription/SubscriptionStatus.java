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
 * @version 2.1.1
 * @since   LCS 1.3
 */
public enum SubscriptionStatus {

	ACTIVE("subscription-status-active", 1),
	CLOSED("subscription-status-closed", 2),
	ON_HOLD("subscription-status-on-hold", 3),
	UNDEFINED("subscription-status-undefined", 0);

	public static SubscriptionStatus valueOf(int status) {
		for (SubscriptionStatus subscriptionStatus : values()) {
			if (status == subscriptionStatus.getStatus()) {
				return subscriptionStatus;
			}
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

	private final String _label;
	private final int _status;

}