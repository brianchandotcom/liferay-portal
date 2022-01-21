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

package com.liferay.batch.planner.web.internal.portlet.action;

import com.liferay.batch.planner.batch.engine.broker.BatchEngineBroker;
import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.web.internal.helper.BatchPlannerPlanHelper;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.nio.file.Files;

import java.util.UUID;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER,
		"mvc.command.name=/batch_planner/submit_batch_planner_plan"
	},
	service = MVCResourceCommand.class
)
public class SubmitBatchPlannerPlanMVCResourceCommand
	extends BaseTransactionalMVCResourceCommand {

	@Override
	protected void doTransactionalCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (cmd.equals(Constants.EXPORT)) {
			_submitExportBatchPlannerPlan(resourceRequest, resourceResponse);
		}
		else if (cmd.equals(Constants.IMPORT)) {
			_submitImportBatchPlannerPlan(resourceRequest, resourceResponse);
		}
	}

	private void _submitExportBatchPlannerPlan(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanHelper.addExportBatchPlannerPlan(resourceRequest);

		if (!batchPlannerPlan.isTemplate()) {
			_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

			BatchPlannerLog batchPlannerLog =
				batchPlannerPlan.getBatchPlannerLog();

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"exportTaskId",
					batchPlannerLog.getBatchEngineExportTaskERC()));
		}
	}

	private void _submitImportBatchPlannerPlan(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String externalType = ParamUtil.getString(
			resourceRequest, "externalType", "CSV");

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(resourceRequest);

		File importFile = _toBatchPlannerFile(
			externalType, uploadPortletRequest.getFileAsStream("importFile"));

		try {
			URI importFileURI = importFile.toURI();

			BatchPlannerPlan batchPlannerPlan =
				_batchPlannerPlanHelper.addImportBatchPlannerPlan(
					resourceRequest, importFileURI.toString());

			_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

			BatchPlannerLog batchPlannerLog =
				batchPlannerPlan.getBatchPlannerLog();

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"importTaskId",
					batchPlannerLog.getBatchEngineImportTaskERC()));
		}
		finally {
			FileUtil.delete(importFile);
		}
	}

	private File _toBatchPlannerFile(
			String externalType, InputStream inputStream)
		throws Exception {

		UUID uuid = UUID.randomUUID();

		File file = FileUtil.createTempFile(uuid.toString(), externalType);

		try {
			Files.copy(inputStream, file.toPath());

			return file;
		}
		catch (IOException ioException) {
			if (file.exists()) {
				file.delete();
			}

			throw ioException;
		}
	}

	@Reference
	private BatchEngineBroker _batchEngineBroker;

	@Reference
	private BatchPlannerPlanHelper _batchPlannerPlanHelper;

	@Reference
	private Portal _portal;

}