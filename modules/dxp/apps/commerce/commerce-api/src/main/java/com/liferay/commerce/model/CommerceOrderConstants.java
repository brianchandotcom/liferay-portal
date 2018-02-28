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

package com.liferay.commerce.model;

import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderConstants {

	public static final int ORDER_STATUS_ANY = WorkflowConstants.STATUS_ANY;

	public static final int ORDER_STATUS_CANCELLED =
		WorkflowConstants.STATUS_IN_TRASH;

	public static final int ORDER_STATUS_COMPLETED =
		WorkflowConstants.STATUS_APPROVED;

	public static final int ORDER_STATUS_OPEN = WorkflowConstants.STATUS_DRAFT;

	public static final int ORDER_STATUS_TO_TRANSMIT =
		WorkflowConstants.STATUS_PENDING;

	public static final int ORDER_STATUS_TRANSMITTED =
		WorkflowConstants.STATUS_INCOMPLETE;

	public static final int[] ORDER_STATUSES = {
		ORDER_STATUS_OPEN, ORDER_STATUS_TO_TRANSMIT, ORDER_STATUS_TRANSMITTED,
		ORDER_STATUS_COMPLETED, ORDER_STATUS_CANCELLED
	};

	public static final int PAYMENT_STATUS_AUTHORIZED =
		WorkflowConstants.STATUS_DRAFT;

	public static final int PAYMENT_STATUS_PAID =
		WorkflowConstants.STATUS_APPROVED;

	public static final int PAYMENT_STATUS_PENDING =
		WorkflowConstants.STATUS_PENDING;

	public static final int[] PAYMENT_STATUSES = {
		PAYMENT_STATUS_AUTHORIZED, PAYMENT_STATUS_PAID, PAYMENT_STATUS_PENDING
	};

	public static final String RESOURCE_NAME = "com.liferay.commerce.order";

	public static final int SHIPPING_STATUS_NOT_SHIPPED =
		WorkflowConstants.STATUS_ANY;

	public static final long TYPE_PK_APPROVAL = 0;

	public static final long TYPE_PK_TRANSMISSION = 1;

	public static String getOrderStatusLabel(int status) {
		if (status == ORDER_STATUS_CANCELLED) {
			return "cancelled";
		}
		else if (status == ORDER_STATUS_COMPLETED) {
			return "completed";
		}
		else if (status == ORDER_STATUS_OPEN) {
			return "open";
		}
		else if (status == ORDER_STATUS_TO_TRANSMIT) {
			return "to-transmit";
		}
		else if (status == ORDER_STATUS_TRANSMITTED) {
			return "transmitted";
		}
		else {
			return null;
		}
	}

	public static String getPaymentStatusLabel(int paymentStatus) {
		if (paymentStatus == PAYMENT_STATUS_AUTHORIZED) {
			return "authorized";
		}
		else if (paymentStatus == PAYMENT_STATUS_PAID) {
			return "paid";
		}
		else if (paymentStatus == PAYMENT_STATUS_PENDING) {
			return WorkflowConstants.LABEL_PENDING;
		}
		else {
			return null;
		}
	}

}