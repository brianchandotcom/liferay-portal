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