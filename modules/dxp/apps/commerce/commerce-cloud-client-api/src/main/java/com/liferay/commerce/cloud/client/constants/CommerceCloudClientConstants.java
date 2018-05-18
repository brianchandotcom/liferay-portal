/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cloud.client.constants;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.forecast.model.CommerceForecastEntryConstants;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCloudClientConstants {

	public static final String CONFIGURATION_PID =
		"com.liferay.commerce.cloud.client.configuration." +
			"CommerceCloudClientConfiguration";

	public static final String[] ORDER_FORECAST_FREQUENCIES =
		{"DAILY", "WEEKLY", "MONTHLY"};

	public static final String[] ORDER_FORECAST_LEVELS =
		{"COMPANY", "CUSTOMER", "CUSTOMER_SKU", "SKU"};

	public static final String ORDER_FORECAST_PERIOD_MONTHLY =
		StringUtil.toUpperCase(
			CommerceForecastEntryConstants.PERIOD_LABEL_MONTHLY);

	public static final String ORDER_FORECAST_PERIOD_WEEKLY =
		StringUtil.toUpperCase(
			CommerceForecastEntryConstants.PERIOD_LABEL_WEEKLY);

	public static final int[] ORDER_FORECAST_SYNC_STATUSES = {
		CommerceOrderConstants.ORDER_STATUS_AWAITING_FULFILLMENT,
		CommerceOrderConstants.ORDER_STATUS_AWAITING_PICKUP,
		CommerceOrderConstants.ORDER_STATUS_AWAITING_SHIPMENT,
		CommerceOrderConstants.ORDER_STATUS_COMPLETED,
		CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED,
		CommerceOrderConstants.ORDER_STATUS_SHIPPED,
		CommerceOrderConstants.ORDER_STATUS_TO_TRANSMIT,
		CommerceOrderConstants.ORDER_STATUS_TRANSMITTED
	};

	public static final String[] ORDER_FORECAST_TARGETS = {
		StringUtil.toUpperCase(
			CommerceForecastEntryConstants.TARGET_LABEL_QUANTITY),
		StringUtil.toUpperCase(
			CommerceForecastEntryConstants.TARGET_LABEL_REVENUE)
	};

}