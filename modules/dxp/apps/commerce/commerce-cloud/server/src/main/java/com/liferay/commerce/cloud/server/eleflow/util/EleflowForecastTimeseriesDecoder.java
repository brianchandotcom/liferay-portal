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