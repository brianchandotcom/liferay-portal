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

	public static final String RETURN_FIELD_ACCOUNT_ENTRY_ID =
		"r_accountToCommerceReturns_accountEntryId";

	public static final String RETURN_FIELD_CHANNEL_GROUP_ID = "channelGroupId";

	public static final String RETURN_FIELD_CHANNEL_ID = "channelId";

	public static final String RETURN_FIELD_CHANNEL_NAME = "channelName";

	public static final String RETURN_FIELD_COMMERCE_ORDER_ID =
		"r_commerceOrderToCommerceReturns_commerceOrderId";

	public static final String RETURN_FIELD_COMMERCE_RETURN_ID =
		"c_commerceReturnId";

	public static final String RETURN_FIELD_EXTERNAL_REFERENCE_CODE =
		"externalReferenceCode";

	public static final String RETURN_FIELD_NOTE = "note";

	public static final String RETURN_FIELD_REQUESTED_ITEMS = "requestedItems";

	public static final String RETURN_FIELD_RETURN_STATUS = "returnStatus";

	public static final String RETURN_FIELD_TOTAL_AMOUNT = "totalAmount";

	public static final String RETURN_ITEM_FIELD_ACCOUNT_ENTRY_ID =
		"r_accountToCommerceReturnItems_accountEntryId";

	public static final String RETURN_ITEM_FIELD_AMOUNT = "amount";

	public static final String
		RETURN_ITEM_FIELD_AUTHORIZE_RETURN_WITHOUT_RETURNING_PRODUCTS =
			"authorizeReturnWithoutReturningProducts";

	public static final String RETURN_ITEM_FIELD_AUTHORIZED = "authorized";

	public static final String RETURN_ITEM_FIELD_COMMERCE_ORDER_ITEM_ID =
		"r_commerceOrderItemToCommerceReturnItems_commerceOrderItemId";

	public static final String RETURN_ITEM_FIELD_COMMERCE_RETURN_ERC =
		"r_commerceReturnToCommerceReturnItems_c_commerceReturnERC";

	public static final String RETURN_ITEM_FIELD_COMMERCE_RETURN_ID =
		"r_commerceReturnToCommerceReturnItems_c_commerceReturnId";

	public static final String RETURN_ITEM_FIELD_QUANTITY = "quantity";

	public static final String RETURN_ITEM_FIELD_RECEIVED = "received";

	public static final String RETURN_ITEM_FIELD_RETURN_ITEM_STATUS =
		"returnItemStatus";

	public static final String RETURN_ITEM_FIELD_RETURN_REASON = "returnReason";

	public static final String RETURN_ITEM_FIELD_RETURN_RESOLUTION_METHOD =
		"returnResolutionMethod";

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