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
import com.liferay.commerce.cloud.server.model.ForecastLevel;
import com.liferay.commerce.cloud.server.model.ForecastPeriod;
import com.liferay.commerce.cloud.server.model.ForecastTarget;

import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastSchedulerForecastsDecoder
	implements
		Function<EleflowForecastSchedulerForecasts, ForecastItemConfiguration> {

	@Override
	public ForecastItemConfiguration apply(
		EleflowForecastSchedulerForecasts eleflowForecastSchedulerForecasts) {

		ForecastItemConfiguration forecastItemConfiguration =
			new ForecastItemConfiguration();

		forecastItemConfiguration.setAhead(
			EleflowUtil.getInteger(
				eleflowForecastSchedulerForecasts.getAhead()));
		forecastItemConfiguration.setIds(
			eleflowForecastSchedulerForecasts.getIds());
		forecastItemConfiguration.setLevel(
			EleflowUtil.fromEleflow(
				eleflowForecastSchedulerForecasts.getLevel(),
				ForecastLevel.class));
		forecastItemConfiguration.setPeriod(
			EleflowUtil.fromEleflow(
				eleflowForecastSchedulerForecasts.getPeriod(),
				ForecastPeriod.class));
		forecastItemConfiguration.setTarget(
			EleflowUtil.fromEleflow(
				eleflowForecastSchedulerForecasts.getTarget(),
				ForecastTarget.class));

		return forecastItemConfiguration;
	}

}