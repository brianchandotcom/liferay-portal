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

import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastSchedulerForecasts;
import com.liferay.commerce.cloud.server.model.ForecastItemConfiguration;

import java.math.BigDecimal;

import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastSchedulerForecastsEncoder
	implements
		Function<ForecastItemConfiguration, EleflowForecastSchedulerForecasts> {

	@Override
	public EleflowForecastSchedulerForecasts apply(
		ForecastItemConfiguration forecastItemConfiguration) {

		EleflowForecastSchedulerForecasts eleflowForecastSchedulerForecasts =
			new EleflowForecastSchedulerForecasts();

		eleflowForecastSchedulerForecasts.setAhead(
			BigDecimal.valueOf(forecastItemConfiguration.getAhead()));
		eleflowForecastSchedulerForecasts.setIds(
			forecastItemConfiguration.getIds());
		eleflowForecastSchedulerForecasts.setLevel(
			EleflowUtil.toEleflow(
				forecastItemConfiguration.getLevel(),
				EleflowForecastSchedulerForecasts.LevelEnum::fromValue));
		eleflowForecastSchedulerForecasts.setPeriod(
			EleflowUtil.toEleflow(
				forecastItemConfiguration.getPeriod(),
				EleflowForecastSchedulerForecasts.PeriodEnum::fromValue));
		eleflowForecastSchedulerForecasts.setTarget(
			EleflowUtil.toEleflow(
				forecastItemConfiguration.getTarget(),
				EleflowForecastSchedulerForecasts.TargetEnum::fromValue));

		return eleflowForecastSchedulerForecasts;
	}

}