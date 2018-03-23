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
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler.FrequencyEnum;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler.LevelsEnum;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler.PeriodsEnum;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler.TargetsEnum;
import com.liferay.commerce.cloud.server.model.ForecastConfiguration;

import java.math.BigDecimal;

import java.util.function.BiFunction;

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

		eleflowForecastScheduler.setAhead(
			BigDecimal.valueOf(forecastConfiguration.getAhead()));
		eleflowForecastScheduler.setCallbackURL(callbackURL);
		eleflowForecastScheduler.setFrequency(
			EleflowUtil.toEleflow(
				forecastConfiguration.getFrequency(),
				FrequencyEnum::fromValue));
		eleflowForecastScheduler.setLevels(
			EleflowUtil.toEleflow(
				forecastConfiguration.getLevels(), LevelsEnum::fromValue));
		eleflowForecastScheduler.setPeriods(
			EleflowUtil.toEleflow(
				forecastConfiguration.getPeriods(), PeriodsEnum::fromValue));
		eleflowForecastScheduler.setTargets(
			EleflowUtil.toEleflow(
				forecastConfiguration.getTargets(), TargetsEnum::fromValue));
		eleflowForecastScheduler.setTimezone(
			forecastConfiguration.getTimeZoneId());

		return eleflowForecastScheduler;
	}

}