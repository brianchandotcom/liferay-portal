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

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.ItemClassRegistry;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.headless.batch.engine.dto.v1_0.ExportTask;
import com.liferay.headless.batch.engine.internal.resource.v1_0.util.ParametersUtil;
import com.liferay.headless.batch.engine.resource.v1_0.ExportTaskResource;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Ivica Cardic
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/export-task.properties",
	property = "batch.engine=true", scope = ServiceScope.PROTOTYPE,
	service = ExportTaskResource.class
)
public class ExportTaskResourceImpl extends BaseExportTaskResourceImpl {

	@Override
	public ExportTask getExportTask(Long exportTaskId) throws Exception {
		return _toExportTask(
			_batchEngineExportTaskLocalService.getBatchEngineExportTask(
				exportTaskId));
	}

	@Override
	public Response getExportTaskContent(Long exportTaskId) throws Exception {
		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.getBatchEngineExportTask(
				exportTaskId);

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				batchEngineExportTask.getExecuteStatus());

		if (batchEngineTaskExecuteStatus ==
				BatchEngineTaskExecuteStatus.COMPLETED) {

			StreamingOutput streamingOutput =
				outputStream -> StreamUtil.transfer(
					_batchEngineExportTaskLocalService.openContentInputStream(
						exportTaskId),
					outputStream);

			return Response.ok(
				streamingOutput
			).header(
				"content-disposition", "attachment; filename=export.zip"
			).build();
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).build();
	}

	@Override
	public ExportTask postExportTask(
			String className, String contentType, String version,
			String callbackURL, String fieldNames)
		throws Exception {

		Class<?> clazz = _itemClassRegistry.getItemClass(className);

		if (clazz == null) {
			throw new IllegalArgumentException(
				"Unknown class name: " + className);
		}

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				ExportTaskResourceImpl.class.getName());

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.addBatchEngineExportTask(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				callbackURL, className, StringUtil.upperCase(contentType),
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				_toList(fieldNames),
				ParametersUtil.toParameters(contextUriInfo, _ignoredParameters),
				version);

		executorService.submit(
			() -> _batchEngineExportTaskExecutor.execute(
				batchEngineExportTask));

		return _toExportTask(batchEngineExportTask);
	}

	private ExportTask _toExportTask(
		BatchEngineExportTask batchEngineExportTask) {

		return new ExportTask() {
			{
				className = batchEngineExportTask.getClassName();
				contentType = batchEngineExportTask.getContentType();
				endTime = batchEngineExportTask.getEndTime();
				errorMessage = batchEngineExportTask.getErrorMessage();
				executeStatus = ExportTask.ExecuteStatus.valueOf(
					batchEngineExportTask.getExecuteStatus());
				id = batchEngineExportTask.getBatchEngineExportTaskId();
				startTime = batchEngineExportTask.getStartTime();
				version = batchEngineExportTask.getVersion();
			}
		};
	}

	private List<String> _toList(String fieldNamesString) {
		if (Validator.isNull(fieldNamesString)) {
			return Collections.emptyList();
		}

		return Arrays.asList(StringUtil.split(fieldNamesString, ','));
	}

	private static final Set<String> _ignoredParameters = new HashSet<>(
		Arrays.asList("callbackURL", "fieldNames"));

	@Reference
	private BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;

	@Reference
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	@Reference
	private ItemClassRegistry _itemClassRegistry;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

}