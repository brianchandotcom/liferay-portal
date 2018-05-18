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

import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastSchedulerForecasts;
import com.liferay.commerce.cloud.server.model.ForecastConfiguration;
import com.liferay.commerce.cloud.server.model.ForecastItemConfiguration;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastSchedulerEncoder
	implements
		BiFunction<ForecastConfiguration, String, EleflowForecastScheduler> {

	@Override
	public EleflowForecastScheduler apply(
		ForecastConfiguration forecastConfiguration, String callbackURL) {

		EleflowForecastScheduler eleflowForecastScheduler =
			new EleflowForecastScheduler();

		eleflowForecastScheduler.setCallbackURL(callbackURL);
		eleflowForecastScheduler.setFrequency(
			EleflowUtil.toEleflow(
				forecastConfiguration.getFrequency(),
				EleflowForecastScheduler.FrequencyEnum::fromValue));
		eleflowForecastScheduler.setForecasts(
			EleflowUtil.map(
				forecastConfiguration.getItems(),
				_eleflowForecastSchedulerForecastsEncoder));
		eleflowForecastScheduler.setTimezone(
			forecastConfiguration.getTimeZoneOffset());

		return eleflowForecastScheduler;
	}

	private static final
		Function<ForecastItemConfiguration, EleflowForecastSchedulerForecasts>
			_eleflowForecastSchedulerForecastsEncoder =
				new EleflowForecastSchedulerForecastsEncoder();

}