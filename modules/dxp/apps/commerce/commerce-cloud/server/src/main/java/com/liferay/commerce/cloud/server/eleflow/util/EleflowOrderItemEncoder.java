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

package com.liferay.commerce.cloud.server.eleflow.util;

import com.liferay.commerce.cloud.server.eleflow.model.EleflowOrderItems;
import com.liferay.commerce.cloud.server.model.ForecastOrderItem;

import java.math.BigDecimal;

import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowOrderItemEncoder
	implements Function<ForecastOrderItem, EleflowOrderItems> {

	@Override
	public EleflowOrderItems apply(ForecastOrderItem forecastOrderItem) {
		EleflowOrderItems eleflowOrderItems = new EleflowOrderItems();

		eleflowOrderItems.setPrice(
			new BigDecimal(forecastOrderItem.getPrice()));
		eleflowOrderItems.setQuantity(
			BigDecimal.valueOf(forecastOrderItem.getQuantity()));
		eleflowOrderItems.setSku(forecastOrderItem.getSku());

		return eleflowOrderItems;
	}

}