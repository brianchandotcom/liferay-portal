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
import com.liferay.commerce.cloud.server.model.ForecastFrequency;
import com.liferay.commerce.cloud.server.model.ForecastItemConfiguration;

import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastSchedulerDecoder
	implements Function<EleflowForecastScheduler, ForecastConfiguration> {

	@Override
	public ForecastConfiguration apply(
		EleflowForecastScheduler eleflowForecastScheduler) {

		ForecastConfiguration forecastScheduler = new ForecastConfiguration();

		forecastScheduler.setFrequency(
			EleflowUtil.fromEleflow(
				eleflowForecastScheduler.getFrequency(),
				ForecastFrequency.class));
		forecastScheduler.setItems(
			EleflowUtil.map(
				eleflowForecastScheduler.getForecasts(),
				_eleflowForecastSchedulerForecastsDecoder));
		forecastScheduler.setTimeZoneOffset(
			eleflowForecastScheduler.getTimezone());

		return forecastScheduler;
	}

	private static final
		Function<EleflowForecastSchedulerForecasts, ForecastItemConfiguration>
			_eleflowForecastSchedulerForecastsDecoder =
				new EleflowForecastSchedulerForecastsDecoder();

}