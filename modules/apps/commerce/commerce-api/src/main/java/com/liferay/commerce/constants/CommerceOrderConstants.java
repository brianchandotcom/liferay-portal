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
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderConstants {

	public static final String COMMERCE_ORDER = "commerce-order";

	public static final String DECIMAL_FORMAT_PATTERN = "###,##0.00";

	public static final String ORDER_NOTIFICATION_AWAITING_SHIPMENT =
		"order-awaiting-shipment";

	public static final String ORDER_NOTIFICATION_COMPLETED = "order-completed";

	public static final String ORDER_NOTIFICATION_PARTIALLY_SHIPPED =
		"order-partially-shipped";

	public static final String ORDER_NOTIFICATION_PLACED = "order-placed";

	public static final String ORDER_NOTIFICATION_PROCESSING =
		"order-processing";

	public static final String ORDER_NOTIFICATION_SHIPPED = "order-shipped";

	public static final int ORDER_STATUS_ANY = WorkflowConstants.STATUS_ANY;

	public static final int ORDER_STATUS_AWAITING_PICKUP = 13;

	public static final int ORDER_STATUS_CANCELLED =
		WorkflowConstants.STATUS_IN_TRASH;

	public static final int ORDER_STATUS_COMPLETED =
		WorkflowConstants.STATUS_APPROVED;

	public static final int ORDER_STATUS_DECLINED = 16;

	public static final int ORDER_STATUS_DISPUTED = 18;

	public static final int ORDER_STATUS_IN_PROGRESS =
		WorkflowConstants.STATUS_INCOMPLETE;

	public static final int ORDER_STATUS_ON_HOLD = 20;

	public static final int ORDER_STATUS_OPEN = WorkflowConstants.STATUS_DRAFT;

	public static final int ORDER_STATUS_PARTIALLY_REFUNDED = 19;

	public static final int ORDER_STATUS_PARTIALLY_SHIPPED = 14;

	public static final int ORDER_STATUS_PENDING =
		WorkflowConstants.STATUS_PENDING;

	public static final int ORDER_STATUS_PROCESSING = 10;

	public static final int ORDER_STATUS_QUOTE_REQUESTED = 21;

	public static final int ORDER_STATUS_REFUNDED = 17;

	public static final int ORDER_STATUS_SHIPPED = 15;

	public static final int ORDER_STATUS_SUBSCRIPTION = 9;

	public static final int[] ORDER_STATUSES_COMPLETED = {
		ORDER_STATUS_COMPLETED, ORDER_STATUS_CANCELLED, ORDER_STATUS_DECLINED
	};

	public static final int[] ORDER_STATUSES_OPEN = {
		ORDER_STATUS_IN_PROGRESS, ORDER_STATUS_OPEN
	};

	public static final int[] ORDER_STATUSES_PENDING = {
		ORDER_STATUS_PENDING, ORDER_STATUS_ON_HOLD
	};

	public static final int[] ORDER_STATUSES_PROCESSING = {
		ORDER_STATUS_PROCESSING, ORDER_STATUS_PARTIALLY_SHIPPED,
		ORDER_STATUS_SHIPPED
	};

	public static final int[] ORDER_STATUSES_SHIPPING = {
		ORDER_STATUS_PARTIALLY_SHIPPED, ORDER_STATUS_SHIPPED
	};

	public static final String RESOURCE_NAME = "com.liferay.commerce.order";

	public static final long TYPE_PK_APPROVAL = 0;

	public static final long TYPE_PK_FULFILLMENT = 1;

	public static String getNotificationKey(int orderStatus) {
		if (orderStatus == CommerceOrderConstants.ORDER_STATUS_PENDING) {
			return ORDER_NOTIFICATION_PLACED;
		}
		else if (orderStatus ==
					CommerceOrderConstants.ORDER_STATUS_PROCESSING) {

			return ORDER_NOTIFICATION_PROCESSING;
		}
		else if (orderStatus ==
					CommerceOrderConstants.ORDER_STATUS_PROCESSING) {

			// TODO check correct status

			return ORDER_NOTIFICATION_AWAITING_SHIPMENT;
		}
		else if (orderStatus ==
					CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED) {

			return ORDER_NOTIFICATION_PARTIALLY_SHIPPED;
		}
		else if (orderStatus == CommerceOrderConstants.ORDER_STATUS_SHIPPED) {
			return ORDER_NOTIFICATION_SHIPPED;
		}
		else if (orderStatus == CommerceOrderConstants.ORDER_STATUS_COMPLETED) {
			return ORDER_NOTIFICATION_COMPLETED;
		}

		return StringPool.BLANK;
	}

	public static String getOrderStatusLabel(int orderStatus) {
		if (orderStatus == ORDER_STATUS_ANY) {
			return WorkflowConstants.LABEL_ANY;
		}
		else if (orderStatus == ORDER_STATUS_AWAITING_PICKUP) {
			return "awaiting-pickup";
		}
		else if (orderStatus == ORDER_STATUS_ON_HOLD) {
			return "on-hold";
		}
		else if (orderStatus == ORDER_STATUS_CANCELLED) {
			return "cancelled";
		}
		else if (orderStatus == ORDER_STATUS_COMPLETED) {
			return "completed";
		}
		else if (orderStatus == ORDER_STATUS_DECLINED) {
			return "declined";
		}
		else if (orderStatus == ORDER_STATUS_DISPUTED) {
			return "disputed";
		}
		else if (orderStatus == ORDER_STATUS_PROCESSING) {
			return "processing";
		}
		else if (orderStatus == ORDER_STATUS_IN_PROGRESS) {
			return "in-progress";
		}
		else if (orderStatus == ORDER_STATUS_OPEN) {
			return "open";
		}
		else if (orderStatus == ORDER_STATUS_PARTIALLY_REFUNDED) {
			return "partially-refunded";
		}
		else if (orderStatus == ORDER_STATUS_PARTIALLY_SHIPPED) {
			return "partially-shipped";
		}
		else if (orderStatus == ORDER_STATUS_QUOTE_REQUESTED) {
			return "quote-requested";
		}
		else if (orderStatus == ORDER_STATUS_REFUNDED) {
			return "refunded";
		}
		else if (orderStatus == ORDER_STATUS_SHIPPED) {
			return "shipped";
		}
		else if (orderStatus == ORDER_STATUS_SUBSCRIPTION) {
			return "subscription";
		}
		else if (orderStatus == ORDER_STATUS_PENDING) {
			return "pending";
		}

		return null;
	}

	public static String getOrderStatusLabelStyle(int orderStatus) {
		if ((orderStatus == ORDER_STATUS_CANCELLED) ||
			(orderStatus == ORDER_STATUS_DECLINED)) {

			return "danger";
		}
		else if (orderStatus == ORDER_STATUS_COMPLETED) {
			return "success";
		}
		else if ((orderStatus == ORDER_STATUS_ON_HOLD) ||
				 (orderStatus == ORDER_STATUS_PARTIALLY_SHIPPED) ||
				 (orderStatus == ORDER_STATUS_PENDING) ||
				 (orderStatus == ORDER_STATUS_PROCESSING) ||
				 (orderStatus == ORDER_STATUS_SHIPPED)) {

			return "warning";
		}

		return "info";
	}

	public static String getStatusLabelStyle(int status) {
		if (status == WorkflowConstants.STATUS_DENIED) {
			return "danger";
		}
		else if (status == WorkflowConstants.STATUS_APPROVED) {
			return "success";
		}

		return "info";
	}

}