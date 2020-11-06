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

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLocalService;
import com.liferay.commerce.data.integration.service.ScheduledTaskExecutorService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseCommerceMLForecastScheduledTaskExecutorService
	implements ScheduledTaskExecutorService {

	@Override
	public void runProcess(long commerceDataIntegrationProcessId)
		throws IOException, PortalException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			commerceDataIntegrationProcessLocalService.
				getCommerceDataIntegrationProcess(
					commerceDataIntegrationProcessId);

		commerceMLForecastScheduledTaskExecutorService.executeScheduledTask(
			commerceDataIntegrationProcess.getUserId(),
			commerceDataIntegrationProcess.
				getCommerceDataIntegrationProcessId(),
			getContextProperties(commerceDataIntegrationProcess));
	}

	protected Map<String, String> getContextProperties(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		UnicodeProperties typeSettingsUnicodeProperties =
			commerceDataIntegrationProcess.getTypeSettingsProperties();

		return HashMapBuilder.put(
			"COMMERCE_ML_FORECAST_PERIOD",
			typeSettingsUnicodeProperties.getProperty(
				COMMERCE_ML_FORECAST_PERIOD, getPeriod())
		).put(
			"COMMERCE_ML_FORECAST_SCOPE", getScope()
		).put(
			"COMMERCE_ML_FORECAST_TARGET",
			typeSettingsUnicodeProperties.getProperty(
				COMMERCE_ML_FORECAST_TARGET, getTarget())
		).put(
			"COMMERCE_ML_PROCESS_TYPE", getName()
		).build();
	}

	protected abstract String getPeriod();

	protected abstract String getScope();

	protected abstract String getTarget();

	protected static final String COMMERCE_ML_FORECAST_PERIOD =
		"commerce.ml.forecast.period";

	protected static final String COMMERCE_ML_FORECAST_TARGET =
		"commerce.ml.forecast.target";

	@Reference
	protected CommerceDataIntegrationProcessLocalService
		commerceDataIntegrationProcessLocalService;

	@Reference
	protected CommerceMLForecastScheduledTaskExecutorService
		commerceMLForecastScheduledTaskExecutorService;

}