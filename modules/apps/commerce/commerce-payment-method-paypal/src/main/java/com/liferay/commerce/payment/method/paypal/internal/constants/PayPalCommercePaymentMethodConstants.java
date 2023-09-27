/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.paypal.internal.constants;

/**
 * @author Luca Pellizzon
 */
public class PayPalCommercePaymentMethodConstants {

	public static final String ACTIVE = "Active";

	public static final String APPROVAL_URL = "approval_url";

	public static final String APPROVE_URL = "approve";

	public static final String AUTHORIZATION_STATE_COMPLETED = "completed";

	public static final String AUTHORIZATION_STATE_CREATED = "created";

	public static final String AUTHORIZATION_STATE_VOIDED = "voided";

	public static final String AUTO_BILLING_AMOUNT_ENABLED = "YES";

	public static final String CANCELLED = "Cancelled";

	public static final String DAY = "day";

	public static final String INITIAL_FAIL_AMOUNT_ACTION = "CONTINUE";

	public static final String INTENT_AUTHORIZE = "AUTHORIZE";

	public static final String INTENT_CAPTURE = "CAPTURE";

	public static final String INTENT_SALE = "sale";

	public static final String MODE_LIVE = "live";

	public static final String MODE_SANDBOX = "sandbox";

	public static final String[] MODES = {MODE_LIVE, MODE_SANDBOX};

	public static final String MONTH = "month";

	public static final String OPERATION_REPLACE = "replace";

	public static final String PAYMENT_DEFINITION_REGULAR = "REGULAR";

	public static final String PAYMENT_STATE_FAILED = "failed";

	public static final String PLAN_FIXED = "FIXED";

	public static final String PLAN_INFINITE = "INFINITE";

	public static final String REQUEST_DETAILS_FULL = "request-details-full";

	public static final String REQUEST_DETAILS_MINIMAL =
		"request-details-minimal";

	public static final String[] REQUEST_DETAILS_OPTIONS = {
		REQUEST_DETAILS_FULL, REQUEST_DETAILS_MINIMAL
	};

	public static final String SERVICE_NAME =
		"com.liferay.commerce.payment.engine.method.paypal";

	public static final String SERVLET_PATH = "commerce-paypal-payment";

	public static final String STATE = "state";

	public static final String SUSPENDED = "Suspended";

	public static final String USER_ACTION = "useraction";

	public static final String USER_ACTION_COMMIT = "commit";

	public static final String WEEK = "week";

	public static final String YEAR = "year";

}