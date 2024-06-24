/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.constants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Crescenzo Rega
 */
public class CommerceReturnConstants {

	public static final String RETURN_ITEM_STATUS_AUTHORIZED = "authorized";

	public static final String RETURN_ITEM_STATUS_AWAITING_AUTHORIZATION =
		"awaitingAuthorization";

	public static final String RETURN_ITEM_STATUS_AWAITING_RECEIPT =
		"awaitingReceipt";

	public static final String RETURN_ITEM_STATUS_COMPLETED =
		CommerceReturnConstants.RETURN_STATUS_COMPLETED;

	public static final String RETURN_ITEM_STATUS_NOT_AUTHORIZED =
		"notAuthorized";

	public static final String RETURN_ITEM_STATUS_PARTIALLY_AUTHORIZED =
		"partiallyAuthorized";

	public static final String RETURN_ITEM_STATUS_PARTIALLY_RECEIVED =
		"partiallyReceived";

	public static final String RETURN_ITEM_STATUS_PROCESSED = "processed";

	public static final String RETURN_ITEM_STATUS_RECEIPT_REJECTED =
		"receiptRejected";

	public static final String RETURN_ITEM_STATUS_RECEIVED = "received";

	public static final String RETURN_ITEM_STATUS_TO_BE_PROCESSED =
		"toBeProcessed";

	public static final String[] RETURN_ITEM_STATUSES_AUTHORIZED = {
		RETURN_ITEM_STATUS_AUTHORIZED, RETURN_ITEM_STATUS_PARTIALLY_AUTHORIZED
	};

	public static final String[] RETURN_ITEM_STATUSES_RECEIVED = {
		RETURN_ITEM_STATUS_RECEIVED, RETURN_ITEM_STATUS_PARTIALLY_RECEIVED
	};

	public static final String RETURN_STATUS_AUTHORIZED = "authorized";

	public static final String RETURN_STATUS_CANCELLED = "cancelled";

	public static final String RETURN_STATUS_COMPLETED = "completed";

	public static final String RETURN_STATUS_DRAFT = "draft";

	public static final String RETURN_STATUS_PENDING = "pending";

	public static final String RETURN_STATUS_PROCESSING = "processing";

	public static final String RETURN_STATUS_REJECTED = "rejected";

	public static final String[] RETURN_STATUSES_LATEST = {
		RETURN_STATUS_COMPLETED, RETURN_STATUS_REJECTED
	};

	public static String getReturnStatusLabelStyle(String returnStatus) {
		if (StringUtil.equals(returnStatus, RETURN_STATUS_AUTHORIZED) ||
			StringUtil.equals(returnStatus, RETURN_STATUS_DRAFT)) {

			return "secondary";
		}
		else if (StringUtil.equals(returnStatus, RETURN_STATUS_CANCELLED) ||
				 StringUtil.equals(returnStatus, RETURN_STATUS_REJECTED)) {

			return "danger";
		}
		else if (StringUtil.equals(returnStatus, RETURN_STATUS_COMPLETED)) {
			return "success";
		}
		else if (StringUtil.equals(returnStatus, RETURN_STATUS_PENDING)) {
			return "warning";
		}
		else if (StringUtil.equals(returnStatus, RETURN_STATUS_PROCESSING)) {
			return "info";
		}

		return StringPool.BLANK;
	}

}