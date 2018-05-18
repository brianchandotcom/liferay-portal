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