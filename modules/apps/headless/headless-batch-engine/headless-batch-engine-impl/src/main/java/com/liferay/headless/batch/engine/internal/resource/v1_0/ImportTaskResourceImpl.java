/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.batch.engine.internal.resource.v1_0;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.ItemClassRegistry;
import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.batch.engine.service.BatchEngineTaskLocalService;
import com.liferay.headless.batch.engine.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.resource.v1_0.ImportTaskResource;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Ivica Cardic
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/import-task.properties",
	property = {"batch.engine=true", "batch.size=100"},
	scope = ServiceScope.PROTOTYPE, service = ImportTaskResource.class
)
public class ImportTaskResourceImpl extends BaseImportTaskResourceImpl {

	@Activate
	public void activate(Map<String, Object> properties) {
		_batchSize = GetterUtil.getLong(properties.get("batch.size"));

		if (_batchSize <= 0) {
			_batchSize = 1;
		}
	}

	@Override
	public ImportTask deleteImportTask(
			String className, String version, String callbackURL,
			MultipartBody multipartBody)
		throws Exception {

		return _importFile(
			BatchEngineTaskOperation.DELETE,
			multipartBody.getBinaryFile("file"), callbackURL, className,
			version);
	}

	@Override
	public ImportTask getImportTask(Long importTaskId) throws Exception {
		return _toImportTask(
			_batchEngineTaskLocalService.getBatchEngineTask(importTaskId));
	}

	@Override
	public ImportTask postImportTask(
			String className, String version, String callbackURL,
			MultipartBody multipartBody)
		throws Exception {

		return _importFile(
			BatchEngineTaskOperation.CREATE,
			multipartBody.getBinaryFile("file"), callbackURL, className,
			version);
	}

	@Override
	public ImportTask putImportTask(
			String className, String version, String callbackURL,
			MultipartBody multipartBody)
		throws Exception {

		return _importFile(
			BatchEngineTaskOperation.UPDATE,
			multipartBody.getBinaryFile("file"), callbackURL, className,
			version);
	}

	private ImportTask _importFile(
			BatchEngineTaskOperation batchEngineTaskOperation,
			BinaryFile binaryFile, String callbackURL, String className,
			String version)
		throws Exception {

		Class<?> clazz = _itemClassRegistry.getItemClass(className);

		if (clazz == null) {
			throw new IllegalArgumentException(
				"Unknown class name: " + className);
		}

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				ImportTaskResourceImpl.class.getName());

		BatchEngineTask batchEngineTask =
			_batchEngineTaskLocalService.addBatchEngineTask(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				_batchSize, callbackURL, className,
				StreamUtil.toByteArray(binaryFile.getInputStream()),
				StringUtil.upperCase(
					_file.getExtension(binaryFile.getFileName())),
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				batchEngineTaskOperation.name(), version);

		executorService.submit(
			() -> _batchEngineTaskExecutor.execute(batchEngineTask));

		return _toImportTask(batchEngineTask);
	}

	private ImportTask _toImportTask(BatchEngineTask batchEngineTask) {
		return new ImportTask() {
			{
				className = batchEngineTask.getClassName();
				endTime = batchEngineTask.getEndTime();
				errorMessage = batchEngineTask.getErrorMessage();
				executeStatus = ImportTask.ExecuteStatus.valueOf(
					batchEngineTask.getExecuteStatus());
				id = batchEngineTask.getBatchEngineTaskId();
				operation = ImportTask.Operation.valueOf(
					batchEngineTask.getOperation());
				startTime = batchEngineTask.getStartTime();
				version = batchEngineTask.getVersion();
			}
		};
	}

	@Reference
	private BatchEngineTaskExecutor _batchEngineTaskExecutor;

	@Reference
	private BatchEngineTaskLocalService _batchEngineTaskLocalService;

	private long _batchSize;

	@Reference
	private File _file;

	@Reference
	private ItemClassRegistry _itemClassRegistry;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

}