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

package com.liferay.commerce.cloud.server.eleflow.util;

import com.liferay.commerce.cloud.server.eleflow.model.EleflowOrder;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowOrderItems;
import com.liferay.commerce.cloud.server.model.ForecastOrder;
import com.liferay.commerce.cloud.server.model.ForecastOrderItem;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		eleflowOrder.setItems(_encodeEleflowOrderItems(forecastOrder));
		eleflowOrder.setOrderId(String.valueOf(forecastOrder.getOrderId()));

		return eleflowOrder;
	}

	private List<EleflowOrderItems> _encodeEleflowOrderItems(
		ForecastOrder forecastOrder) {

		List<ForecastOrderItem> forecastOrderItems =
			forecastOrder.getOrderItems();

		Stream<ForecastOrderItem> stream = forecastOrderItems.stream();

		return stream.map(
			_eleflowOrderItemEncoder
		).collect(
			Collectors.toList()
		);
	}

	private static final Function<ForecastOrderItem, EleflowOrderItems>
		_eleflowOrderItemEncoder = new EleflowOrderItemEncoder();

}