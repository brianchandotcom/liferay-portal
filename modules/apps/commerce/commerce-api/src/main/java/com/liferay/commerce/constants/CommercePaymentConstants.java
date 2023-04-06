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
 * @author Luca Pellizzon
 */
public class CommercePaymentConstants {

	public static final String DEFAULT_PAYMENT_REQUEST_PROVIDER_KEY = "default";

	public static final int METHOD_TYPE_OFFLINE = 2;

	public static final int METHOD_TYPE_ONLINE_REDIRECT = 1;

	public static final int METHOD_TYPE_ONLINE_STANDARD = 0;

	public static final int[] METHOD_TYPES_ONLINE = {
		METHOD_TYPE_ONLINE_STANDARD, METHOD_TYPE_ONLINE_REDIRECT
	};

	public static final String SERVLET_PATH = "commerce-payment";

	public static final int STATUS_AUTHORIZED = WorkflowConstants.STATUS_DRAFT;

	public static final int STATUS_CANCELLED =
		WorkflowConstants.STATUS_IN_TRASH;

	public static final int STATUS_DENIED = WorkflowConstants.STATUS_DENIED;

	public static final int STATUS_PAID = WorkflowConstants.STATUS_APPROVED;

	public static final int STATUS_PENDING = WorkflowConstants.STATUS_PENDING;

	public static String getCommercePaymentStatusLabel(int paymentStatus) {
		if (paymentStatus == STATUS_AUTHORIZED) {
			return "authorized";
		}
		else if (paymentStatus == STATUS_CANCELLED) {
			return "cancelled";
		}
		else if (paymentStatus == STATUS_DENIED) {
			return WorkflowConstants.LABEL_DENIED;
		}
		else if (paymentStatus == STATUS_PAID) {
			return "paid";
		}
		else if (paymentStatus == STATUS_PENDING) {
			return WorkflowConstants.LABEL_PENDING;
		}

		return null;
	}

}