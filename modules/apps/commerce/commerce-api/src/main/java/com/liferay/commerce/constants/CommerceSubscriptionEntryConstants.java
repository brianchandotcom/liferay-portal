/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.constants;

import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceSubscriptionEntryConstants {

	public static final int SUBSCRIPTION_STATUS_ACTIVE =
		WorkflowConstants.STATUS_APPROVED;

	public static final int SUBSCRIPTION_STATUS_CANCELLED =
		WorkflowConstants.STATUS_DRAFT;

	public static final int SUBSCRIPTION_STATUS_COMPLETED =
		WorkflowConstants.STATUS_EXPIRED;

	public static final int SUBSCRIPTION_STATUS_INACTIVE =
		WorkflowConstants.STATUS_ANY;

	public static final int SUBSCRIPTION_STATUS_SUSPENDED =
		WorkflowConstants.STATUS_PENDING;

	public static final int[] SUBSCRIPTION_STATUSES = {
		SUBSCRIPTION_STATUS_INACTIVE, SUBSCRIPTION_STATUS_ACTIVE,
		SUBSCRIPTION_STATUS_SUSPENDED, SUBSCRIPTION_STATUS_CANCELLED,
		SUBSCRIPTION_STATUS_COMPLETED
	};

	public static String getSubscriptionStatusLabel(int subscriptionStatus) {
		if (subscriptionStatus == SUBSCRIPTION_STATUS_INACTIVE) {
			return "inactive";
		}
		else if (subscriptionStatus == SUBSCRIPTION_STATUS_ACTIVE) {
			return "active";
		}
		else if (subscriptionStatus == SUBSCRIPTION_STATUS_SUSPENDED) {
			return "suspended";
		}
		else if (subscriptionStatus == SUBSCRIPTION_STATUS_CANCELLED) {
			return "cancelled";
		}
		else if (subscriptionStatus == SUBSCRIPTION_STATUS_COMPLETED) {
			return "completed";
		}

		return null;
	}

}