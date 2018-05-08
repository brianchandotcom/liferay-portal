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

	public static final String[] ORDER_FORECAST_PERIODS;

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

	public static final String[] ORDER_FORECAST_TARGETS;

	private static String[] _fixLabels(String... labels) {
		for (int i = 0; i < labels.length; i++) {
			labels[i] = StringUtil.toUpperCase(labels[i]);
		}

		return labels;
	}

	static {
		ORDER_FORECAST_PERIODS = _fixLabels(
			CommerceForecastEntryConstants.PERIOD_LABEL_WEEKLY,
			CommerceForecastEntryConstants.PERIOD_LABEL_MONTHLY);
		ORDER_FORECAST_TARGETS = _fixLabels(
			CommerceForecastEntryConstants.TARGET_LABEL_QUANTITY,
			CommerceForecastEntryConstants.TARGET_LABEL_REVENUE);
	}

}