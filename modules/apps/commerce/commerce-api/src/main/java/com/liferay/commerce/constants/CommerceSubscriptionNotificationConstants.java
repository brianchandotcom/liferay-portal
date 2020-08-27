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

package com.liferay.commerce.constants;

import com.liferay.petra.string.StringPool;

/**
 * @author Luca Pellizzon
 */
public class CommerceSubscriptionNotificationConstants {

	public static final String SUBSCRIPTION_ACTIVATED =
		"subscription-activated";

	public static final String SUBSCRIPTION_CANCELLED =
		"subscription-cancelled";

	public static final String SUBSCRIPTION_RENEWED = "subscription-renewed";

	public static final String SUBSCRIPTION_SUSPENDED =
		"subscription-suspended";

	public static String getNotificationKey(int subscriptionStatus) {
		if (subscriptionStatus ==
				CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE) {

			return SUBSCRIPTION_ACTIVATED;
		}
		else if (subscriptionStatus ==
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_CANCELLED) {

			return SUBSCRIPTION_CANCELLED;
		}
		else if (subscriptionStatus ==
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_SUSPENDED) {

			return SUBSCRIPTION_SUSPENDED;
		}

		return StringPool.BLANK;
	}

}