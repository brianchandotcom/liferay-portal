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
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerMappingService;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.BatchPlannerPolicyService;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER,
		"mvc.command.name=/batch_planner/edit_export_batch_planner_plan"
	},
	service = MVCActionCommand.class
)
public class EditExportBatchPlannerPlanMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	public BatchPlannerPlan addBatchPlannerPlan(PortletRequest portletRequest)
		throws Exception {

		String externalType = ParamUtil.getString(
			portletRequest, "externalType");
		String internalClassName = ParamUtil.getString(
			portletRequest, "internalClassName");
		String name = ParamUtil.getString(portletRequest, "name");
		String taskItemDelegateName = ParamUtil.getString(
			portletRequest, "taskItemDelegateName");
		boolean template = ParamUtil.getBoolean(portletRequest, "template");

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, externalType, StringPool.SLASH, internalClassName, name,
				taskItemDelegateName, template);

		_batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "containsHeaders",
			_getCheckboxValue(portletRequest, "containsHeaders"));

		_batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "saveExport",
			_getCheckboxValue(portletRequest, "saveExport"));

		List<BatchPlannerMapping> batchPlannerMappings =
			_getBatchPlannerMappings(portletRequest);

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			_batchPlannerMappingService.addBatchPlannerMapping(
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerMapping.getExternalFieldName(), "String",
				batchPlannerMapping.getInternalFieldName(), "String",
				StringPool.BLANK);
		}

		return batchPlannerPlan;
	}

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.EXPORT)) {
			BatchPlannerPlan batchPlannerPlan = addBatchPlannerPlan(
				actionRequest);

			if (!batchPlannerPlan.isTemplate()) {
				_batchEngineBroker.submit(
					batchPlannerPlan.getBatchPlannerPlanId());
			}
		}
	}

	private List<BatchPlannerMapping> _getBatchPlannerMappings(
		PortletRequest portletRequest) {

		String[] fieldNames = portletRequest.getParameterValues("fieldName");

		if (fieldNames == null) {
			return Collections.emptyList();
		}

		List<BatchPlannerMapping> batchPlannerMappings = new ArrayList<>();

		for (String fieldName : fieldNames) {
			BatchPlannerMapping batchPlannerMapping =
				BatchPlannerMappingUtil.create(0);

			batchPlannerMapping.setExternalFieldName(fieldName);
			batchPlannerMapping.setInternalFieldName(fieldName);

			batchPlannerMappings.add(batchPlannerMapping);
		}

		return batchPlannerMappings;
	}

	private String _getCheckboxValue(
		PortletRequest portletRequest, String name) {

		String value = portletRequest.getParameter(name);

		if (value == null) {
			return Boolean.FALSE.toString();
		}

		return Boolean.TRUE.toString();
	}

	@Reference
	private BatchEngineBroker _batchEngineBroker;

	@Reference
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Reference
	private BatchPlannerPolicyService _batchPlannerPolicyService;

}