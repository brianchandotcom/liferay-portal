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