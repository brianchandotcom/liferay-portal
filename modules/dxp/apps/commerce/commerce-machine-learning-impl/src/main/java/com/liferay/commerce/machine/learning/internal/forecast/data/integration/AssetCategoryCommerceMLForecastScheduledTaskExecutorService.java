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

package com.liferay.commerce.machine.learning.internal.forecast.data.integration;

import com.liferay.commerce.data.integration.service.ScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastPeriod;
import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastScope;
import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastTarget;
import com.liferay.commerce.machine.learning.internal.forecast.data.integration.process.type.AssetCategoryCommerceMLForecastProcessType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	property = "data.integration.service.executor.key=" + AssetCategoryCommerceMLForecastProcessType.KEY,
	service = ScheduledTaskExecutorService.class
)
public class AssetCategoryCommerceMLForecastScheduledTaskExecutorService
	extends BaseCommerceMLForecastScheduledTaskExecutorService {

	@Override
	public String getName() {
		return AssetCategoryCommerceMLForecastProcessType.KEY;
	}

	@Override
	protected String getPeriod() {
		return _COMMERCE_ML_FORECAST_PERIOD.getLabel();
	}

	@Override
	protected String getScope() {
		return _COMMERCE_ML_FORECAST_SCOPE.getLabel();
	}

	@Override
	protected String getTarget() {
		return _COMMERCE_ML_FORECAST_TARGET.getLabel();
	}

	private static final CommerceMLForecastPeriod _COMMERCE_ML_FORECAST_PERIOD =
		CommerceMLForecastPeriod.MONTH;

	private static final CommerceMLForecastScope _COMMERCE_ML_FORECAST_SCOPE =
		CommerceMLForecastScope.ASSET_CATEGORY;

	private static final CommerceMLForecastTarget _COMMERCE_ML_FORECAST_TARGET =
		CommerceMLForecastTarget.REVENUE;

}