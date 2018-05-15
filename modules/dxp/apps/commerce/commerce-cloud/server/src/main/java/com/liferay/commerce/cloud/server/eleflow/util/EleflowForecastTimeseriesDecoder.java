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

import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastTimeseries;
import com.liferay.commerce.cloud.server.model.ForecastValue;

import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastTimeseriesDecoder
	implements Function<EleflowForecastTimeseries, ForecastValue> {

	@Override
	public ForecastValue apply(
		EleflowForecastTimeseries eleflowForecastTimeseries) {

		ForecastValue forecastValue = new ForecastValue();

		forecastValue.setLowerValue(
			EleflowUtil.getString(eleflowForecastTimeseries.getLower()));
		forecastValue.setTime(
			EleflowUtil.getTime(eleflowForecastTimeseries.getDate()));
		forecastValue.setUpperValue(
			EleflowUtil.getString(eleflowForecastTimeseries.getUpper()));
		forecastValue.setValue(
			EleflowUtil.getString(eleflowForecastTimeseries.getValue()));

		return forecastValue;
	}

}