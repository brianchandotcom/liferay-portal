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

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLocalService;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLogLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

import java.nio.file.Files;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseScheduledTaskExecutorService {

	public CommerceDataIntegrationProcessLog runExportTask(
			CommerceDataIntegrationProcess commerceDataIntegrationProcess,
			CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog,
			BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper)
		throws Exception {

		appendToLogOutput(
			commerceDataIntegrationProcessLog,
			"Start exporting: " +
				batchEngineTaskItemDelegateResourceMapper.getResourceName());

		BatchEngineExportTask batchEngineExportTask =
			batchEngineExportTaskLocalService.addBatchEngineExportTask(
				commerceDataIntegrationProcess.getCompanyId(),
				commerceDataIntegrationProcess.getUserId(), null,
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
				commerceDataIntegrationProcessLog,
				"Start uploading: " + resourceName);

			uploadExport(batchEngineExportTask, commerceDataIntegrationProcess);

			batchEngineExportTaskLocalService.deleteBatchEngineExportTask(
				batchEngineExportTask);
		}
		else {
			throw new PortalException("Error exporting: " + resourceName);
		}

		return commerceDataIntegrationProcessLog;
	}

	public CommerceDataIntegrationProcessLog runImportTask(
			CommerceDataIntegrationProcess commerceDataIntegrationProcess,
			CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog,
			BatchEngineTaskItemDelegateResourceMapper
				batchEngineTaskItemDelegateResourceMapper,
			File resourceFile)
		throws Exception {

		appendToLogOutput(
			commerceDataIntegrationProcessLog,
			"Start import task: " +
				batchEngineTaskItemDelegateResourceMapper.getResourceName());

		BatchEngineImportTask batchEngineImportTask =
			batchEngineImportTaskLocalService.addBatchEngineImportTask(
				commerceDataIntegrationProcess.getCompanyId(),
				commerceDataIntegrationProcess.getUserId(), 20, null,
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
				commerceDataIntegrationProcessLog,
				"Completed import task: " + resourceName);
		}
		else {
			throw new PortalException(
				"Error importing resource: " + resourceName);
		}

		return commerceDataIntegrationProcessLog;
	}

	protected CommerceDataIntegrationProcessLog appendToLogOutput(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog,
		String message) {

		StringBundler sb = new StringBundler(5);

		sb.append(commerceDataIntegrationProcessLog.getOutput());
		sb.append(dateFormat.format(new Date()));
		sb.append(StringPool.SPACE);
		sb.append(message);
		sb.append(StringPool.NEW_LINE);

		commerceDataIntegrationProcessLog.setOutput(sb.toString());

		commerceDataIntegrationProcessLog.setEndDate(new Date());

		return commerceDataIntegrationProcessLogLocalService.
			updateCommerceDataIntegrationProcessLog(
				commerceDataIntegrationProcessLog);
	}

	protected abstract void uploadExport(
			BatchEngineExportTask batchEngineExportTask,
			CommerceDataIntegrationProcess commerceDataIntegrationProcess)
		throws Exception;

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
	protected CommerceDataIntegrationProcessLocalService
		commerceDataIntegrationProcessLocalService;

	@Reference
	protected CommerceDataIntegrationProcessLogLocalService
		commerceDataIntegrationProcessLogLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseScheduledTaskExecutorService.class);

}