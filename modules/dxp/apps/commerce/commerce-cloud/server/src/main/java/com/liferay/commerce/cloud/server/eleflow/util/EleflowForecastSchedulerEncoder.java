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