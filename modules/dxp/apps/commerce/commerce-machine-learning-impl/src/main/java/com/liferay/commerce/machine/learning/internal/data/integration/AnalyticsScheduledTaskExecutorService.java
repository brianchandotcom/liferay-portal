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

import com.liferay.analytics.message.sender.client.AnalyticsBatchClient;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLocalService;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLogLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	service = AnalyticsScheduledTaskExecutorService.class
)
public class AnalyticsScheduledTaskExecutorService
	extends BaseScheduledTaskExecutorService {

	public void downloadResources(
			long commerceDataIntegrationProcessId,
			BatchEngineTaskItemDelegateResourceMapper[]
				batchEngineTaskItemDelegateResourceMappers)
		throws PortalException {

		_runScheduledTask(
			commerceDataIntegrationProcessId,
			batchEngineTaskItemDelegateResourceMappers, true);
	}

	public void uploadResources(
			long commerceDataIntegrationProcessId,
			BatchEngineTaskItemDelegateResourceMapper[]
				batchEngineTaskItemDelegateResourceMappers)
		throws PortalException {

		_runScheduledTask(
			commerceDataIntegrationProcessId,
			batchEngineTaskItemDelegateResourceMappers, false);
	}

	@Override
	protected void uploadExport(
			BatchEngineExportTask batchEngineExportTask,
			CommerceDataIntegrationProcess commerceDataIntegrationProcess)
		throws Exception {

		InputStream inputStream =
			batchEngineExportTaskLocalService.openContentInputStream(
				batchEngineExportTask.getBatchEngineExportTaskId());

		_analyticsBatchClient.uploadResource(
			commerceDataIntegrationProcess.getCompanyId(), inputStream,
			batchEngineExportTask.getClassName());

		inputStream.close();
	}

	private void _downloadResources(
			CommerceDataIntegrationProcess commerceDataIntegrationProcess,
			CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog,
			BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper)
		throws Exception {

		appendToLogOutput(
			commerceDataIntegrationProcessLog,
			"Checking updates for: " +
				batchEngineTaskItemDelegateResourceMapper.getResourceName());

		File resource = _analyticsBatchClient.downloadResource(
			commerceDataIntegrationProcess.getCompanyId(),
			commerceDataIntegrationProcess.getModifiedDate(),
			batchEngineTaskItemDelegateResourceMapper.getResourceName());

		if (resource == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"No resource update since: %s",
						commerceDataIntegrationProcess.getModifiedDate()));
			}

			appendToLogOutput(
				commerceDataIntegrationProcessLog,
				String.format(
					"No resource update since: %s",
					commerceDataIntegrationProcess.getModifiedDate()));
		}
		else {
			runImportTask(
				commerceDataIntegrationProcess,
				commerceDataIntegrationProcessLog,
				batchEngineTaskItemDelegateResourceMapper, resource);
		}
	}

	private void _runScheduledTask(
			long commerceDataIntegrationProcessId,
			BatchEngineTaskItemDelegateResourceMapper[]
				batchEngineTaskItemDelegateResourceMappers,
			boolean download)
		throws PortalException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			_commerceDataIntegrationProcessLocalService.
				getCommerceDataIntegrationProcess(
					commerceDataIntegrationProcessId);

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			_commerceDataIntegrationProcessLogLocalService.
				addCommerceDataIntegrationProcessLog(
					commerceDataIntegrationProcess.getUserId(),
					commerceDataIntegrationProcess.
						getCommerceDataIntegrationProcessId(),
					null, null, BackgroundTaskConstants.STATUS_IN_PROGRESS,
					new Date(), null);

		try {
			for (BatchEngineTaskItemDelegateResourceMapper
					batchEngineTaskItemDelegateResourceMapper :
						batchEngineTaskItemDelegateResourceMappers) {

				if (download) {
					_downloadResources(
						commerceDataIntegrationProcess,
						commerceDataIntegrationProcessLog,
						batchEngineTaskItemDelegateResourceMapper);
				}
				else {
					_uploadResources(
						commerceDataIntegrationProcess,
						commerceDataIntegrationProcessLog,
						batchEngineTaskItemDelegateResourceMapper);
				}
			}

			commerceDataIntegrationProcessLog.setEndDate(new Date());

			commerceDataIntegrationProcessLog.setStatus(
				BackgroundTaskConstants.STATUS_SUCCESSFUL);

			_commerceDataIntegrationProcessLocalService.
				updateCommerceDataIntegrationProcess(
					commerceDataIntegrationProcess);
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

		_commerceDataIntegrationProcessLogLocalService.
			updateCommerceDataIntegrationProcessLog(
				commerceDataIntegrationProcessLog);
	}

	private void _uploadResources(
			CommerceDataIntegrationProcess commerceDataIntegrationProcess,
			CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog,
			BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper)
		throws Exception {

		runExportTask(
			commerceDataIntegrationProcess, commerceDataIntegrationProcessLog,
			batchEngineTaskItemDelegateResourceMapper);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsScheduledTaskExecutorService.class);

	@Reference
	private AnalyticsBatchClient _analyticsBatchClient;

	@Reference
	private CommerceDataIntegrationProcessLocalService
		_commerceDataIntegrationProcessLocalService;

	@Reference
	private CommerceDataIntegrationProcessLogLocalService
		_commerceDataIntegrationProcessLogLocalService;

}