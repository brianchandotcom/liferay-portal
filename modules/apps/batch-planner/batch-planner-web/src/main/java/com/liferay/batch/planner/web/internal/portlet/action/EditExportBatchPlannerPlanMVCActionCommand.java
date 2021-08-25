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
		"mvc.command.name=/batch_planner/edit_export_batch_planner_plan"
	},
	service = MVCActionCommand.class
)
public class EditExportBatchPlannerPlanMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String name = ParamUtil.getString(
			actionRequest, "name", "Plan " + System.currentTimeMillis());
		String externalType = ParamUtil.getString(
			actionRequest, "externalType");
		String internalClassName = ParamUtil.getString(
			actionRequest, "internalClassName");

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, externalType, StringPool.SLASH, internalClassName, name,
				false);

		boolean hasColumnHeaders = ParamUtil.getBoolean(
			actionRequest, "policyHasColumnHeaders");

		_batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "hasColumnHeaders",
			String.valueOf(hasColumnHeaders));

		boolean saveForLaterDownload = ParamUtil.getBoolean(
			actionRequest, "policySaveForLaterDownload");

		_batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "saveForLaterDownload",
			String.valueOf(saveForLaterDownload));

		List<BatchPlannerMapping> batchPlannerMappings =
			_getBatchPlannerMappings(actionRequest);

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			_batchPlannerMappingService.addBatchPlannerMapping(
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerMapping.getExternalFieldName(), "String",
				batchPlannerMapping.getInternalFieldName(), "String",
				StringPool.BLANK);
		}

		_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());
	}

	private List<BatchPlannerMapping> _getBatchPlannerMappings(
		ActionRequest actionRequest) {

		List<BatchPlannerMapping> batchPlannerMappings = new ArrayList<>();

		Enumeration<String> enumeration = actionRequest.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String parameterName = enumeration.nextElement();

			if (!parameterName.startsWith("internalFieldName_") ||
				Validator.isNull(
					ParamUtil.getString(actionRequest, parameterName))) {

				continue;
			}

			String suffix = StringUtil.extractLast(
				parameterName, StringPool.UNDERLINE);

			String externalFieldNameParamValue = ParamUtil.getString(
				actionRequest, "externalFieldName_" + suffix);

			if (Validator.isNull(externalFieldNameParamValue)) {
				continue;
			}

			String internalFieldNameParamValue = ParamUtil.getString(
				actionRequest, parameterName);

			if (_isCheckboxValue(externalFieldNameParamValue)) {
				if (Boolean.parseBoolean(externalFieldNameParamValue)) {
					externalFieldNameParamValue = internalFieldNameParamValue;
				}
				else {
					continue;
				}
			}

			BatchPlannerMapping batchPlannerMapping =
				BatchPlannerMappingUtil.create(0);

			batchPlannerMapping.setExternalFieldName(
				externalFieldNameParamValue);
			batchPlannerMapping.setInternalFieldName(
				internalFieldNameParamValue);

			batchPlannerMappings.add(batchPlannerMapping);
		}

		return batchPlannerMappings;
	}

	private boolean _isCheckboxValue(String value) {
		if (StringUtil.equalsIgnoreCase(StringPool.FALSE, value) ||
			StringUtil.equalsIgnoreCase(StringPool.TRUE, value)) {

			return true;
		}

		return false;
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