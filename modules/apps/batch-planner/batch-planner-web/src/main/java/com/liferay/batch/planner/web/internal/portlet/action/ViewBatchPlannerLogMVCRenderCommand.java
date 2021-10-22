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

import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerLogService;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.web.internal.display.BatchPlannerLogDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joe Duffy
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER,
		"mvc.command.name=/batch_planner/view_batch_planner_log"
	},
	service = MVCRenderCommand.class
)
public class ViewBatchPlannerLogMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long batchPlannerLogId = ParamUtil.getLong(
			renderRequest, "batchPlannerLogId");

		try {
			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				_getBatchPlannerLogDisplay(batchPlannerLogId));

			return "/view_batch_planner_log.jsp";
		}
		catch (PortalException portalException) {
			SessionErrors.add(renderRequest, portalException.getClass());

			throw new PortletException(
				"Unable to render batch planner log " + batchPlannerLogId,
				portalException);
		}
	}

	private BatchPlannerLogDisplay _getBatchPlannerLogDisplay(
			long batchPlannerLogId)
		throws PortalException {

		BatchPlannerLog batchPlannerLog =
			_batchPlannerLogService.getBatchPlannerLog(batchPlannerLogId);

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.getBatchPlannerPlan(
				batchPlannerLog.getBatchPlannerPlanId());

		BatchPlannerLogDisplay.Builder builder =
			new BatchPlannerLogDisplay.Builder();

		builder.batchPlannerLogId(
			batchPlannerLogId
		).batchEngineExportTaskERC(
			batchPlannerLog.getBatchEngineExportTaskERC()
		).batchEngineImportTaskERC(
			batchPlannerLog.getBatchEngineImportTaskERC()
		).status(
			String.valueOf(batchPlannerLog.getStatus())
		).createDate(
			batchPlannerLog.getCreateDate()
		).export(
			batchPlannerPlan.isExport()
		).processedItemsCount(
			0
		).totalItemsCount(
			batchPlannerLog.getTotal()
		).title(
			batchPlannerPlan.getName()
		).modifiedDate(
			batchPlannerLog.getModifiedDate()
		);

		return builder.build();
	}

	@Reference
	private BatchPlannerLogService _batchPlannerLogService;

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

}