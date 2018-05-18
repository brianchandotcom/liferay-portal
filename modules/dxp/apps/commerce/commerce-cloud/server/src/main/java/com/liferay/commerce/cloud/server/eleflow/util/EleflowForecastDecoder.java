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

import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecast;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastForecasts;
import com.liferay.commerce.cloud.server.model.Forecast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastDecoder
	implements Function<EleflowForecast, List<Forecast>> {

	@Override
	public List<Forecast> apply(EleflowForecast eleflowForecast) {
		List<EleflowForecastForecasts> eleflowForecastForecastsList =
			eleflowForecast.getForecasts();

		long time = EleflowUtil.getTime(eleflowForecast.getDate());

		List<Forecast> forecasts = new ArrayList<>(
			eleflowForecastForecastsList.size());

		for (EleflowForecastForecasts eleflowForecastForecasts :
				eleflowForecastForecastsList) {

			forecasts.add(
				_eleflowForecastForecastsDecoder.apply(
					eleflowForecastForecasts, time));
		}

		return forecasts;
	}

	private static final BiFunction<EleflowForecastForecasts, Long, Forecast>
		_eleflowForecastForecastsDecoder =
			new EleflowForecastForecastsDecoder();

}