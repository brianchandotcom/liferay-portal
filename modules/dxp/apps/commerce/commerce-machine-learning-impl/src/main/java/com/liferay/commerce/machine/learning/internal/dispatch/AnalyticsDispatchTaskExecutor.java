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

package com.liferay.commerce.machine.learning.internal.dispatch;

import com.liferay.analytics.message.sender.client.AnalyticsBatchClient;
import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.commerce.machine.learning.internal.batch.engine.mapper.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	service = AnalyticsDispatchTaskExecutor.class
)
public class AnalyticsDispatchTaskExecutor {

	public void downloadResources(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput,
			BatchEngineTaskItemDelegateResourceMapper[]
				batchEngineTaskItemDelegateResourceMappers)
		throws IOException, PortalException {

		DispatchLog dispatchLog =
			dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTrigger.getDispatchTriggerId(),
				DispatchTaskStatus.IN_PROGRESS);

		for (BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper :
					batchEngineTaskItemDelegateResourceMappers) {

			appendToLogOutput(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				String.format(
					"Checking updates for: %s",
					batchEngineTaskItemDelegateResourceMapper.
						getResourceName()));

			Date resourceLastModifiedDate = dispatchTrigger.getCreateDate();

			DispatchLog latestSuccessfulDispatchLog =
				dispatchLogLocalService.fetchLatestDispatchLog(
					dispatchTrigger.getDispatchTriggerId(),
					DispatchTaskStatus.SUCCESSFUL);

			if (latestSuccessfulDispatchLog != null) {
				resourceLastModifiedDate =
					latestSuccessfulDispatchLog.getEndDate();
			}

			File resource = _analyticsBatchClient.downloadResource(
				dispatchTrigger.getCompanyId(), resourceLastModifiedDate,
				batchEngineTaskItemDelegateResourceMapper.getResourceName());

			if (resource == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						String.format(
							"No resource update since: %s",
							resourceLastModifiedDate));
				}

				appendToLogOutput(
					dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
					String.format(
						"No resource update since: %s",
						resourceLastModifiedDate));
			}
			else {
				runImportTask(
					dispatchTrigger, dispatchLog, dispatchTaskExecutorOutput,
					batchEngineTaskItemDelegateResourceMapper, resource);
			}
		}
	}

	public void uploadResources(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput,
			BatchEngineTaskItemDelegateResourceMapper[]
				batchEngineTaskItemDelegateResourceMappers)
		throws IOException, PortalException {

		DispatchLog dispatchLog =
			dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTrigger.getDispatchTriggerId(),
				DispatchTaskStatus.IN_PROGRESS);

		for (BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper :
					batchEngineTaskItemDelegateResourceMappers) {

			runExportTask(
				dispatchTrigger, dispatchLog, dispatchTaskExecutorOutput,
				batchEngineTaskItemDelegateResourceMapper);
		}
	}

	protected DispatchTaskExecutorOutput appendToLogOutput(
			long dispatchLogId,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput,
			String message)
		throws PortalException {

		StringBundler sb = new StringBundler(5);

		if (dispatchTaskExecutorOutput.getOutput() != null) {
			sb.append(dispatchTaskExecutorOutput.getOutput());
		}

		sb.append(dateFormat.format(new Date()));
		sb.append(StringPool.SPACE);
		sb.append(message);
		sb.append(StringPool.NEW_LINE);

		dispatchTaskExecutorOutput.setOutput(sb.toString());

		dispatchLogLocalService.updateDispatchLog(
			dispatchLogId, new Date(), dispatchTaskExecutorOutput.getError(),
			dispatchTaskExecutorOutput.getOutput(),
			DispatchTaskStatus.IN_PROGRESS);

		return dispatchTaskExecutorOutput;
	}

	protected DispatchTaskExecutorOutput runExportTask(
			DispatchTrigger dispatchTrigger, DispatchLog dispatchLog,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput,
			BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper)
		throws IOException, PortalException {

		appendToLogOutput(
			dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
			"Start exporting: " +
				batchEngineTaskItemDelegateResourceMapper.getResourceName());

		BatchEngineExportTask batchEngineExportTask =
			batchEngineExportTaskLocalService.addBatchEngineExportTask(
				dispatchTrigger.getCompanyId(), dispatchTrigger.getUserId(),
				null,
				batchEngineTaskItemDelegateResourceMapper.getResourceName(),
				BatchEngineTaskContentType.JSONL.name(),
				BatchEngineTaskExecuteStatus.INITIAL.name(), null,
				new HashMap<>(),
				batchEngineTaskItemDelegateResourceMapper.
					getBatchEngineTaskItemDelegate());

		batchEngineExportTaskExecutor.execute(batchEngineExportTask);

		String resourceName =
			batchEngineTaskItemDelegateResourceMapper.getResourceName();

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				batchEngineExportTask.getExecuteStatus());

		if (batchEngineTaskExecuteStatus.equals(
				BatchEngineTaskExecuteStatus.COMPLETED)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Batch Export process completed, uploading: " +
						batchEngineExportTask.getClassName());
			}

			appendToLogOutput(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				"Start uploading: " + resourceName);

			InputStream inputStream =
				batchEngineExportTaskLocalService.openContentInputStream(
					batchEngineExportTask.getBatchEngineExportTaskId());

			_analyticsBatchClient.uploadResource(
				dispatchTrigger.getCompanyId(), inputStream,
				batchEngineExportTask.getClassName());

			inputStream.close();

			batchEngineExportTaskLocalService.deleteBatchEngineExportTask(
				batchEngineExportTask);
		}
		else {
			throw new PortalException("Error exporting: " + resourceName);
		}

		return dispatchTaskExecutorOutput;
	}

	protected DispatchTaskExecutorOutput runImportTask(
			DispatchTrigger dispatchTrigger, DispatchLog dispatchLog,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput,
			BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper,
			File resourceFile)
		throws IOException, PortalException {

		appendToLogOutput(
			dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
			"Start import task: " +
				batchEngineTaskItemDelegateResourceMapper.getResourceName());

		BatchEngineImportTask batchEngineImportTask =
			batchEngineImportTaskLocalService.addBatchEngineImportTask(
				dispatchTrigger.getCompanyId(), dispatchTrigger.getUserId(), 20,
				null,
				batchEngineTaskItemDelegateResourceMapper.getResourceName(),
				Files.readAllBytes(resourceFile.toPath()),
				BatchEngineTaskContentType.JSONL.name(),
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				batchEngineTaskItemDelegateResourceMapper.getFieldMapping(),
				BatchEngineTaskOperation.CREATE.name(), null,
				batchEngineTaskItemDelegateResourceMapper.
					getBatchEngineTaskItemDelegate());

		batchEngineImportTaskExecutor.execute(batchEngineImportTask);

		String resourceName =
			batchEngineTaskItemDelegateResourceMapper.getResourceName();

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				batchEngineImportTask.getExecuteStatus());

		if (batchEngineTaskExecuteStatus.equals(
				BatchEngineTaskExecuteStatus.COMPLETED)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Batch Import process completed for entity: " +
						batchEngineImportTask.getClassName());
			}

			batchEngineImportTaskLocalService.deleteBatchEngineImportTask(
				batchEngineImportTask);

			appendToLogOutput(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				"Completed import task: " + resourceName);
		}
		else {
			throw new PortalException(
				"Error importing resource: " + resourceName);
		}

		return dispatchTaskExecutorOutput;
	}

	protected static final DateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@Reference
	protected BatchEngineExportTaskExecutor batchEngineExportTaskExecutor;

	@Reference
	protected BatchEngineExportTaskLocalService
		batchEngineExportTaskLocalService;

	@Reference
	protected BatchEngineImportTaskExecutor batchEngineImportTaskExecutor;

	@Reference
	protected BatchEngineImportTaskLocalService
		batchEngineImportTaskLocalService;

	@Reference
	protected DispatchLogLocalService dispatchLogLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsDispatchTaskExecutor.class);

	@Reference
	private AnalyticsBatchClient _analyticsBatchClient;

}