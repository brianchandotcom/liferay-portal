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

package com.liferay.batch.engine.web.internal.portlet.action;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.constants.BatchEnginePortletKeys;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	property = {
		"javax.portlet.name=" + BatchEnginePortletKeys.BATCH,
		"mvc.command.name=/batch_engine/batch_engine_import_task_status"
	},
	service = MVCResourceCommand.class
)
public class BatchEngineImportTaskStatusMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long batchEngineImportTaskId = ParamUtil.getLong(
			resourceRequest, "batchEngineImportTaskId");

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.getBatchEngineImportTask(
				batchEngineImportTaskId);

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				batchEngineImportTask.getExecuteStatus());

		int percentage = 0;

		if (batchEngineImportTask.getTotalItemsCount() != 0) {
			percentage =
				(batchEngineImportTask.getProcessedItemsCount() * 100) /
					batchEngineImportTask.getTotalItemsCount();
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"executeStatus", batchEngineTaskExecuteStatus.toString()
			).put(
				"percentage", percentage
			));
	}

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

}