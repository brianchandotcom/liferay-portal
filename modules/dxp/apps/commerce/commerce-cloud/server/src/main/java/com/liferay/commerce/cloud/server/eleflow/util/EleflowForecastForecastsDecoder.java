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

import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastForecasts;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastTimeseries;
import com.liferay.commerce.cloud.server.model.Forecast;
import com.liferay.commerce.cloud.server.model.ForecastPeriod;
import com.liferay.commerce.cloud.server.model.ForecastTarget;
import com.liferay.commerce.cloud.server.model.ForecastValue;

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
		forecast.setValues(
			EleflowUtil.map(
				eleflowForecastForecasts.getTimeseries(),
				_eleflowForecastTimeseriesDecoder));

		return forecast;
	}

	private static final Function<EleflowForecastTimeseries, ForecastValue>
		_eleflowForecastTimeseriesDecoder =
			new EleflowForecastTimeseriesDecoder();

}