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

import com.liferay.commerce.cloud.server.eleflow.model.EleflowOrder;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowOrderItems;
import com.liferay.commerce.cloud.server.model.ForecastOrder;
import com.liferay.commerce.cloud.server.model.ForecastOrderItem;

import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowOrderEncoder
	implements Function<ForecastOrder, EleflowOrder> {

	@Override
	public EleflowOrder apply(ForecastOrder forecastOrder) {
		EleflowOrder eleflowOrder = new EleflowOrder();

		eleflowOrder.setCompanyId(String.valueOf(forecastOrder.getCompanyId()));
		eleflowOrder.setCreateDate(
			EleflowUtil.getOffsetDateTime(forecastOrder.getCreateTime()));
		eleflowOrder.setCustomerId(
			String.valueOf(forecastOrder.getCustomerId()));
		eleflowOrder.setItems(
			EleflowUtil.map(
				forecastOrder.getOrderItems(), _eleflowOrderItemEncoder));
		eleflowOrder.setOrderId(String.valueOf(forecastOrder.getOrderId()));

		return eleflowOrder;
	}

	private static final Function<ForecastOrderItem, EleflowOrderItems>
		_eleflowOrderItemEncoder = new EleflowOrderItemEncoder();

}