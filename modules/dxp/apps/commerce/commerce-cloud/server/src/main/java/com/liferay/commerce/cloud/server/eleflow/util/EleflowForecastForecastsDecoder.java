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

import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastForecasts;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastTimeseries;
import com.liferay.commerce.cloud.server.model.Forecast;
import com.liferay.commerce.cloud.server.model.ForecastPeriod;
import com.liferay.commerce.cloud.server.model.ForecastTarget;
import com.liferay.commerce.cloud.server.model.ForecastTimeSeries;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastForecastsDecoder
	implements BiFunction<EleflowForecastForecasts, Long, Forecast> {

	@Override
	public Forecast apply(
		EleflowForecastForecasts eleflowForecastForecasts, Long time) {

		Forecast forecast = new Forecast();

		forecast.setAssertivity(
			EleflowUtil.getString(eleflowForecastForecasts.getAssertivity()));
		forecast.setCompanyId(
			EleflowUtil.getLong(eleflowForecastForecasts.getCompanyId()));
		forecast.setCustomerId(
			EleflowUtil.getLong(eleflowForecastForecasts.getCustomerId()));
		forecast.setPeriod(
			EleflowUtil.fromEleflow(
				eleflowForecastForecasts.getPeriod(), ForecastPeriod.class));
		forecast.setSKU(eleflowForecastForecasts.getSku());
		forecast.setTarget(
			EleflowUtil.fromEleflow(
				eleflowForecastForecasts.getTarget(), ForecastTarget.class));
		forecast.setTime(time);
		forecast.setTimeSeries(
			EleflowUtil.map(
				eleflowForecastForecasts.getTimeseries(),
				_eleflowForecastTimeseriesDecoder));

		return forecast;
	}

	private static final Function<EleflowForecastTimeseries, ForecastTimeSeries>
		_eleflowForecastTimeseriesDecoder =
			new EleflowForecastTimeseriesDecoder();

}