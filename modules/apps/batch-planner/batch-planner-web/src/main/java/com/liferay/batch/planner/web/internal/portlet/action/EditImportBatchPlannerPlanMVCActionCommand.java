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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER,
		"mvc.command.name=/batch_planner/edit_batch_planner_plan"
	},
	service = MVCActionCommand.class
)
public class EditImportBatchPlannerPlanMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			_addBatchPlannerPlan(actionRequest);
		}
		else if (cmd.equals(Constants.DELETE)) {
			_deleteBatchPlannerPlan(actionRequest);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			_updateBatchPlannerPlan(actionRequest);
		}
	}

	private void _addBatchPlannerPlan(ActionRequest actionRequest)
		throws Exception {

		String name = ParamUtil.getString(actionRequest, "name");
		boolean export = ParamUtil.getBoolean(actionRequest, "export");
		String externalType = ParamUtil.getString(
			actionRequest, "externalType");
		String externalURL = ParamUtil.getString(actionRequest, "externalURL");
		String internalClassName = ParamUtil.getString(
			actionRequest, "internalClassName");

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				export, externalType, externalURL, internalClassName, name,
				false);

		if (Validator.isNotNull(
				ParamUtil.getString(actionRequest, "policyName")) &&
			Validator.isNotNull(
				ParamUtil.getString(actionRequest, "policyValue"))) {

			_batchPlannerPolicyService.addBatchPlannerPolicy(
				batchPlannerPlan.getBatchPlannerPlanId(),
				ParamUtil.getString(actionRequest, "policyName"),
				ParamUtil.getString(actionRequest, "policyValue"));
		}

		List<BatchPlannerMapping> batchPlannerMappings =
			_getBatchPlannerMappings(actionRequest);

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			_batchPlannerMappingService.addBatchPlannerMapping(
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerMapping.getExternalFieldName(), "String",
				batchPlannerMapping.getInternalFieldName(), "String",
				StringPool.BLANK);
		}
	}

	private void _deleteBatchPlannerPlan(ActionRequest actionRequest)
		throws Exception {

		long batchPlannerPlanId = ParamUtil.getLong(
			actionRequest, "batchPlannerPlanId");

		_batchPlannerPlanService.deleteBatchPlannerPlan(batchPlannerPlanId);
	}

	private List<BatchPlannerMapping> _getBatchPlannerMappings(
		ActionRequest actionRequest) {

		List<BatchPlannerMapping> batchPlannerMappings = new ArrayList<>();

		Enumeration<String> enumeration = actionRequest.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String parameterName = enumeration.nextElement();

			if (!parameterName.startsWith("externalFieldName_") ||
				Validator.isNull(
					ParamUtil.getString(actionRequest, parameterName))) {

				continue;
			}

			String suffix = StringUtil.extractLast(
				parameterName, StringPool.UNDERLINE);

			if (Validator.isNull(
					ParamUtil.getString(
						actionRequest, "internalFieldName_" + suffix))) {

				continue;
			}

			BatchPlannerMapping batchPlannerMapping =
				BatchPlannerMappingUtil.create(0);

			batchPlannerMapping.setExternalFieldName(
				ParamUtil.getString(actionRequest, parameterName));
			batchPlannerMapping.setInternalFieldName(
				ParamUtil.getString(
					actionRequest, "internalFieldName_" + suffix));

			batchPlannerMappings.add(batchPlannerMapping);
		}

		return batchPlannerMappings;
	}

	private void _updateBatchPlannerPlan(ActionRequest actionRequest)
		throws Exception {

		long batchPlannerPlanId = ParamUtil.getLong(
			actionRequest, "batchPlannerPlanId");

		String name = ParamUtil.getString(actionRequest, "name");

		_batchPlannerPlanService.updateBatchPlannerPlan(
			batchPlannerPlanId, name);

		if (Validator.isNotNull(
				ParamUtil.getString(actionRequest, "policyName")) &&
			Validator.isNotNull(
				ParamUtil.getString(actionRequest, "policyValue"))) {

			_batchPlannerPolicyService.updateBatchPlannerPolicy(
				batchPlannerPlanId,
				ParamUtil.getString(actionRequest, "policyName"),
				ParamUtil.getString(actionRequest, "policyValue"));
		}

		List<BatchPlannerMapping> batchPlannerMappings =
			_getBatchPlannerMappings(actionRequest);

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			_batchPlannerMappingService.updateBatchPlannerMapping(
				batchPlannerPlanId, batchPlannerMapping.getExternalFieldName(),
				"String", StringPool.BLANK);
		}
	}

	@Reference
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Reference
	private BatchPlannerPolicyService _batchPlannerPolicyService;

}