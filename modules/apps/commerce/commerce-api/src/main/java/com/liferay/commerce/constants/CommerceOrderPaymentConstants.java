/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.constants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderPaymentConstants {

	public static final int STATUS_AUTHORIZED = WorkflowConstants.STATUS_DRAFT;

	public static final int STATUS_CANCELLED =
		WorkflowConstants.STATUS_IN_TRASH;

	public static final int STATUS_COMPLETED =
		WorkflowConstants.STATUS_APPROVED;

	public static final int STATUS_FAILED = WorkflowConstants.STATUS_DENIED;

	public static final int STATUS_PENDING = WorkflowConstants.STATUS_PENDING;

	public static final int STATUS_REFUNDED = 17;

	public static final int[] STATUSES = {
		STATUS_AUTHORIZED, STATUS_CANCELLED, STATUS_COMPLETED, STATUS_FAILED,
		STATUS_PENDING
	};

	public static final int[] STATUSES_RETRY_PAYMENT = {
		STATUS_AUTHORIZED, STATUS_CANCELLED
	};

	public static String getOrderPaymentLabelStyle(int orderPaymentStatus) {
		if (orderPaymentStatus == STATUS_AUTHORIZED) {
			return "info";
		}
		else if (orderPaymentStatus == STATUS_COMPLETED) {
			return "success";
		}
		else if (orderPaymentStatus == STATUS_PENDING) {
			return "warning";
		}
		else if ((orderPaymentStatus == STATUS_FAILED) ||
				 (orderPaymentStatus == STATUS_CANCELLED)) {

			return "danger";
		}

		return StringPool.BLANK;
	}

	public static String getOrderPaymentStatusLabel(int orderPaymentStatus) {
		if (orderPaymentStatus == STATUS_AUTHORIZED) {
			return "authorized";
		}
		else if (orderPaymentStatus == STATUS_CANCELLED) {
			return "cancelled";
		}
		else if (orderPaymentStatus == STATUS_COMPLETED) {
			return "completed";
		}
		else if (orderPaymentStatus == STATUS_FAILED) {
			return "failed";
		}
		else if (orderPaymentStatus == STATUS_PENDING) {
			return WorkflowConstants.LABEL_PENDING;
		}

		return null;
	}

}