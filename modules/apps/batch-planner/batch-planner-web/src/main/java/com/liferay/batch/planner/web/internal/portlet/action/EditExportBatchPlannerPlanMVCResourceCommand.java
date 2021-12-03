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
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerMappingService;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.BatchPlannerPolicyService;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
	service = MVCResourceCommand.class
)
public class EditExportBatchPlannerPlanMVCResourceCommand
	extends BaseTransactionalMVCResourceCommand {

	@Override
	protected void doTransactionalCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (cmd.equals(Constants.EXPORT)) {
			_submitBatchPlannerPlan(resourceRequest, resourceResponse);
		}
		else if (cmd.equals(Constants.SAVE)) {
			_addBatchPlannerPlan(resourceRequest, resourceResponse);
		}
	}

	private BatchPlannerPlan _addBatchPlannerPlan(PortletRequest portletRequest)
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
			batchPlannerPlan.getBatchPlannerPlanId(), "headlessEndpoint",
			ParamUtil.getString(portletRequest, "headlessEndpoint"));

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

	private void _addBatchPlannerPlan(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			BatchPlannerPlan batchPlannerPlan = _addBatchPlannerPlan(
				resourceRequest);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"batchPlannerPlanId",
					batchPlannerPlan.getBatchPlannerPlanId()
				).put(
					"name", batchPlannerPlan.getName()
				).put(
					"success", Boolean.TRUE
				));
		}
		catch (Exception exception) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put("error", exception.getMessage()));
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

	private void _submitBatchPlannerPlan(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		BatchPlannerPlan batchPlannerPlan = _addBatchPlannerPlan(
			resourceRequest);

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

	@Reference
	private BatchEngineBroker _batchEngineBroker;

	@Reference
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Reference
	private BatchPlannerPolicyService _batchPlannerPolicyService;

}