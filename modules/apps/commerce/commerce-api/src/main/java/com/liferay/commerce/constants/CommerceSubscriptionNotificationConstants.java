/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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