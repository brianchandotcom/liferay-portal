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
import com.liferay.commerce.cloud.server.model.ForecastConfiguration;
import com.liferay.commerce.cloud.server.model.ForecastConfiguration.Level;
import com.liferay.commerce.cloud.server.model.ForecastConfiguration.Period;
import com.liferay.commerce.cloud.server.model.ForecastConfiguration.Target;

import io.vertx.core.json.JsonObject;

import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastSchedulerDecoder
	implements Function<EleflowForecastScheduler, ForecastConfiguration> {

	@Override
	public ForecastConfiguration apply(
		EleflowForecastScheduler eleflowForecastScheduler) {

		ForecastConfiguration forecastScheduler = new ForecastConfiguration(
			new JsonObject());

		forecastScheduler.setAhead(
			EleflowUtil.getInteger(eleflowForecastScheduler.getAhead()));
		forecastScheduler.setFrequency(
			EleflowUtil.fromEleflow(
				eleflowForecastScheduler.getFrequency(),
				ForecastConfiguration.Frequency.class));
		forecastScheduler.setLevels(
			EleflowUtil.fromEleflow(
				eleflowForecastScheduler.getLevels(), Level.class));
		forecastScheduler.setPeriods(
			EleflowUtil.fromEleflow(
				eleflowForecastScheduler.getPeriods(), Period.class));
		forecastScheduler.setTargets(
			EleflowUtil.fromEleflow(
				eleflowForecastScheduler.getTargets(), Target.class));
		forecastScheduler.setTimeZoneOffset(
			eleflowForecastScheduler.getTimezone());

		return forecastScheduler;
	}

}