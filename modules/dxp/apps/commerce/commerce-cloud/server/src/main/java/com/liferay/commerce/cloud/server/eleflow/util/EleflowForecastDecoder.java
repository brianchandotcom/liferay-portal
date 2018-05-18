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