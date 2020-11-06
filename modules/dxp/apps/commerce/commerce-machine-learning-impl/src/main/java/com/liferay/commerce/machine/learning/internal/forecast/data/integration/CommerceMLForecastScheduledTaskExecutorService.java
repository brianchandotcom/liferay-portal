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
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLocalService;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLogLocalService;
import com.liferay.commerce.machine.learning.internal.gateway.CommerceMLGatewayClient;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceMLForecastScheduledTaskExecutorService.class
)
public class CommerceMLForecastScheduledTaskExecutorService {

	public void executeScheduledTask(
			long userId, long commerceDataIntegrationProcessId,
			Map<String, String> scheduledTaskContext)
		throws PortalException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			_commerceDataIntegrationProcessLocalService.
				getCommerceDataIntegrationProcess(
					commerceDataIntegrationProcessId);

		Date startDate = new Date();

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			_commerceDataIntegrationProcessLogLocalService.
				addCommerceDataIntegrationProcessLog(
					userId,
					commerceDataIntegrationProcess.
						getCommerceDataIntegrationProcessId(),
					null, null, BackgroundTaskConstants.STATUS_IN_PROGRESS,
					startDate, null);

		try {
			executeProcess(
				commerceDataIntegrationProcess, scheduledTaskContext);

			commerceDataIntegrationProcessLog.setEndDate(new Date());

			commerceDataIntegrationProcessLog.setStatus(
				BackgroundTaskConstants.STATUS_SUCCESSFUL);

			_commerceDataIntegrationProcessLogLocalService.
				updateCommerceDataIntegrationProcessLog(
					commerceDataIntegrationProcessLog);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			_commerceDataIntegrationProcessLogLocalService.
				updateCommerceDataIntegrationProcessLog(
					commerceDataIntegrationProcessLog.
						getCommerceDataIntegrationProcessLogId(),
					exception.getMessage(), null,
					BackgroundTaskConstants.STATUS_FAILED, new Date());
		}
	}

	protected void executeProcess(
			CommerceDataIntegrationProcess commerceDataIntegrationProcess,
			Map<String, String> additionalProcessContextProperties)
		throws Exception {

		UnicodeProperties unicodeProperties =
			commerceDataIntegrationProcess.getTypeSettingsProperties();

		unicodeProperties.putAll(additionalProcessContextProperties);

		_commerceMLGatewayClient.startCommerceMLJob(unicodeProperties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLForecastScheduledTaskExecutorService.class);

	@Reference
	private CommerceDataIntegrationProcessLocalService
		_commerceDataIntegrationProcessLocalService;

	@Reference
	private CommerceDataIntegrationProcessLogLocalService
		_commerceDataIntegrationProcessLogLocalService;

	@Reference
	private CommerceMLGatewayClient _commerceMLGatewayClient;

}