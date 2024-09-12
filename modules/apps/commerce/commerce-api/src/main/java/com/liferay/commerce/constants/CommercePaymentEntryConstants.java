/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.constants;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommercePaymentEntryConstants {

	public static final String RESOURCE_NAME = "com.liferay.commerce.payment";

	public static final int STATUS_AUTHORIZED =
		CommerceOrderPaymentConstants.STATUS_AUTHORIZED;

	public static final int STATUS_CANCELLED =
		CommerceOrderPaymentConstants.STATUS_CANCELLED;

	public static final int STATUS_COMPLETED =
		CommerceOrderPaymentConstants.STATUS_COMPLETED;

	public static final int STATUS_CREATED = 18;

	public static final int STATUS_FAILED =
		CommerceOrderPaymentConstants.STATUS_FAILED;

	public static final int STATUS_PENDING =
		CommerceOrderPaymentConstants.STATUS_PENDING;

	public static final int STATUS_REFUNDED =
		CommerceOrderPaymentConstants.STATUS_REFUNDED;

	public static final int[] STATUSES = {
		STATUS_AUTHORIZED, STATUS_CANCELLED, STATUS_COMPLETED, STATUS_CREATED,
		STATUS_FAILED, STATUS_PENDING, STATUS_REFUNDED
	};

	public static final int[] STATUSES_REFUND = {
		STATUS_CREATED, STATUS_FAILED, STATUS_PENDING, STATUS_REFUNDED
	};

	public static final int TYPE_PAYMENT = 0;

	public static final int TYPE_REFUND = 1;

	public static final int[] TYPES = {TYPE_PAYMENT, TYPE_REFUND};

	public static String getPaymentLabelStyle(int paymentStatus) {
		return CommerceOrderPaymentConstants.getOrderPaymentLabelStyle(
			paymentStatus);
	}

	public static String getPaymentStatusLabel(int paymentStatus) {
		if (paymentStatus == STATUS_CREATED) {
			return "created";
		}
		else if (paymentStatus == STATUS_REFUNDED) {
			return "refunded";
		}

		return CommerceOrderPaymentConstants.getOrderPaymentStatusLabel(
			paymentStatus);
	}

	public static String getTypeLabel(int type) {
		if (type == TYPE_PAYMENT) {
			return "payment";
		}
		else if (type == TYPE_REFUND) {
			return "refund";
		}

		return null;
	}

}