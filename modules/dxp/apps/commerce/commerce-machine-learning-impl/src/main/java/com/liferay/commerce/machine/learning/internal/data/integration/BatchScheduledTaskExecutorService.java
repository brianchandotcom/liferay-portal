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

package com.liferay.commerce.machine.learning.internal.data.integration;

import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
import com.liferay.commerce.machine.learning.internal.gateway.CommerceMLGatewayClient;
import com.liferay.commerce.machine.learning.internal.gateway.CommerceMLJobState;
import com.liferay.commerce.machine.learning.internal.gateway.constants.CommerceMLJobStateConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	service = BatchScheduledTaskExecutorService.class
)
public class BatchScheduledTaskExecutorService
	extends BaseScheduledTaskExecutorService {

	public void executeScheduledTask(
			long commerceDataIntegrationProcessId,
			BatchEngineTaskItemDelegateResourceMapper[] exportResourceNameList,
			Map<String, String> contextProperties,
			BatchEngineTaskItemDelegateResourceMapper[] importResources)
		throws PortalException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			commerceDataIntegrationProcessLocalService.
				getCommerceDataIntegrationProcess(
					commerceDataIntegrationProcessId);

		Date startDate = new Date();

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			commerceDataIntegrationProcessLogLocalService.
				addCommerceDataIntegrationProcessLog(
					commerceDataIntegrationProcess.getUserId(),
					commerceDataIntegrationProcess.
						getCommerceDataIntegrationProcessId(),
					null, null, BackgroundTaskConstants.STATUS_IN_PROGRESS,
					startDate, null);

		try {
			for (BatchEngineTaskItemDelegateResourceMapper exportResourceName :
					exportResourceNameList) {

				commerceDataIntegrationProcessLog = runExportTask(
					commerceDataIntegrationProcess,
					commerceDataIntegrationProcessLog, exportResourceName);
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				commerceDataIntegrationProcess.getTypeSettingsProperties();

			typeSettingsUnicodeProperties.putAll(contextProperties);

			CommerceMLJobState commerceMLJobState =
				_commerceMLGatewayClient.startCommerceMLJob(
					typeSettingsUnicodeProperties);

			commerceDataIntegrationProcessLog = appendToLogOutput(
				commerceDataIntegrationProcessLog,
				"Starting job: " + commerceMLJobState.getApplicationId());

			if (commerceMLJobState.getApplicationId() != null) {
				_pollAndWait(
					commerceMLJobState.getApplicationId(),
					commerceDataIntegrationProcess.getTypeSettingsProperties());

				commerceDataIntegrationProcessLog = appendToLogOutput(
					commerceDataIntegrationProcessLog,
					"Completed job: " + commerceMLJobState.getApplicationId());

				for (BatchEngineTaskItemDelegateResourceMapper importResource :
						importResources) {

					File file =
						_commerceMLGatewayClient.downloadCommerceMLJobResult(
							commerceMLJobState.getApplicationId(),
							importResource.getResourceName(),
							typeSettingsUnicodeProperties);

					commerceDataIntegrationProcessLog = runImportTask(
						commerceDataIntegrationProcess,
						commerceDataIntegrationProcessLog, importResource,
						file);
				}
			}

			commerceDataIntegrationProcessLog.setEndDate(new Date());

			commerceDataIntegrationProcessLog.setStatus(
				BackgroundTaskConstants.STATUS_SUCCESSFUL);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			commerceDataIntegrationProcessLog.setError(exception.getMessage());

			commerceDataIntegrationProcessLog.setEndDate(new Date());

			commerceDataIntegrationProcessLog.setStatus(
				BackgroundTaskConstants.STATUS_FAILED);
		}

		commerceDataIntegrationProcessLogLocalService.
			updateCommerceDataIntegrationProcessLog(
				commerceDataIntegrationProcessLog);
	}

	@Override
	protected void uploadExport(
			BatchEngineExportTask batchEngineExportTask,
			CommerceDataIntegrationProcess commerceDataIntegrationProcess)
		throws Exception {

		InputStream inputStream =
			batchEngineExportTaskLocalService.openContentInputStream(
				batchEngineExportTask.getBatchEngineExportTaskId());

		_commerceMLGatewayClient.uploadCommerceMLJobResource(
			batchEngineExportTask.getClassName(), inputStream,
			commerceDataIntegrationProcess.getTypeSettingsProperties());

		inputStream.close();
	}

	private void _pollAndWait(
			String applicationId, UnicodeProperties unicodeProperties)
		throws Exception {

		int pollCount = 0;

		while (pollCount < _MAX_POLL_COUNT) {
			CommerceMLJobState commerceMLJobState =
				_commerceMLGatewayClient.getCommerceMLJobState(
					applicationId, unicodeProperties);

			String state = commerceMLJobState.getState();

			if (StringUtil.equalsIgnoreCase(
					state, CommerceMLJobStateConstants.COMPLETE)) {

				return;
			}
			else if (StringUtil.equalsIgnoreCase(
						state, CommerceMLJobStateConstants.ERROR)) {

				_log.error("Application failed");

				throw new Exception("ML Job failed with an error");
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Remote application status: " + state);
				}
			}

			pollCount++;

			Thread.sleep(60 * 1000);
		}

		throw new Exception("Timeout waiting for ML Job completion");
	}

	private static final int _MAX_POLL_COUNT = 180;

	private static final Log _log = LogFactoryUtil.getLog(
		BatchScheduledTaskExecutorService.class);

	@Reference
	private CommerceMLGatewayClient _commerceMLGatewayClient;

}