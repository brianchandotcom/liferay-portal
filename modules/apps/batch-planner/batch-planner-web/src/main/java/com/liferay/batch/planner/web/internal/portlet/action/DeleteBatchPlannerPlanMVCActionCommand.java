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
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER,
		"mvc.command.name=/batch_planner/delete_batch_planner_plan"
	},
	service = MVCActionCommand.class
)
public class DeleteBatchPlannerPlanMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long batchPlannerPlanId = ParamUtil.getLong(
			actionRequest, "batchPlannerPlanId");

		if (batchPlannerPlanId != 0) {
			_batchPlannerPlanService.deleteBatchPlannerPlan(batchPlannerPlanId);
		}
		else {
			_deleteBatchPlannerPlans(
				ParamUtil.getLongValues(actionRequest, "batchPlannerPlanIds"));
		}
	}

	private void _deleteBatchPlannerPlans(long[] batchPlannerPlanIds)
		throws Exception {

		for (long batchPlannerPlanId : batchPlannerPlanIds) {
			_batchPlannerPlanService.deleteBatchPlannerPlan(batchPlannerPlanId);
		}
	}

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

}