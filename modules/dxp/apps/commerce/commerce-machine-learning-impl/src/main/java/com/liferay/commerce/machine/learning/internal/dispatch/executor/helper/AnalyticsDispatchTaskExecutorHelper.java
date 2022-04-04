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

package com.liferay.commerce.machine.learning.internal.dispatch.executor.helper;

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
	service = AnalyticsDispatchTaskExecutorHelper.class
)
public class AnalyticsDispatchTaskExecutorHelper {

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

			_updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				String.format(
					"Checking updates for resource %s",
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

			File resourceFile = _analyticsBatchClient.downloadResource(
				dispatchTrigger.getCompanyId(), resourceLastModifiedDate,
				batchEngineTaskItemDelegateResourceMapper.getResourceName());

			if (resourceFile == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						String.format(
							"No resource was updated since: %s",
							resourceLastModifiedDate));
				}

				_updateDispatchLog(
					dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
					String.format(
						"No resource was updated since: %s",
						resourceLastModifiedDate));
			}
			else {
				_import(
					dispatchTrigger, dispatchLog, dispatchTaskExecutorOutput,
					batchEngineTaskItemDelegateResourceMapper, resourceFile);
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

			_export(
				dispatchTrigger, dispatchLog, dispatchTaskExecutorOutput,
				batchEngineTaskItemDelegateResourceMapper);
		}
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

	private DispatchTaskExecutorOutput _export(
			DispatchTrigger dispatchTrigger, DispatchLog dispatchLog,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput,
			BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper)
		throws IOException, PortalException {

		_updateDispatchLog(
			dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
			"Start exporting resource " +
				batchEngineTaskItemDelegateResourceMapper.getResourceName());

		BatchEngineExportTask batchEngineExportTask =
			batchEngineExportTaskLocalService.addBatchEngineExportTask(
				String.valueOf(dispatchLog.getDispatchLogId()),
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
					"Completed batch engine export task for " +
						batchEngineExportTask.getClassName());
			}

			_updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				"Uploading resource " + resourceName);

			InputStream inputStream =
				batchEngineExportTaskLocalService.openContentInputStream(
					batchEngineExportTask.getBatchEngineExportTaskId());

			_analyticsBatchClient.uploadResource(
				dispatchTrigger.getCompanyId(), inputStream,
				batchEngineExportTask.getClassName());

			inputStream.close();

			batchEngineExportTaskLocalService.deleteBatchEngineExportTask(
				batchEngineExportTask);

			_updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				"Finished exporting resource " + resourceName);
		}
		else {
			throw new PortalException(
				"Unable to export resource " + resourceName);
		}

		return dispatchTaskExecutorOutput;
	}

	private DispatchTaskExecutorOutput _import(
			DispatchTrigger dispatchTrigger, DispatchLog dispatchLog,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput,
			BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper,
			File resourceFile)
		throws IOException, PortalException {

		_updateDispatchLog(
			dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
			"Start importing resource " +
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
					"Completed batch engine import task for " +
						batchEngineImportTask.getClassName());
			}

			batchEngineImportTaskLocalService.deleteBatchEngineImportTask(
				batchEngineImportTask);

			_updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				"Finished importing resource " + resourceName);
		}
		else {
			throw new PortalException(
				"Unable to import resource " + resourceName);
		}

		return dispatchTaskExecutorOutput;
	}

	private DispatchTaskExecutorOutput _updateDispatchLog(
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

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsDispatchTaskExecutorHelper.class);

	@Reference
	private AnalyticsBatchClient _analyticsBatchClient;

}