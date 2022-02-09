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

package com.liferay.batch.planner.web.internal.helper;

import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerMappingService;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.BatchPlannerPolicyService;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = BatchPlannerPlanHelper.class)
public class BatchPlannerPlanHelper {

	public BatchPlannerPlan addExportBatchPlannerPlan(
			PortletRequest portletRequest)
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

		List<BatchPlannerMapping> batchPlannerMappings =
			_getExportBatchPlannerMappings(portletRequest);

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			_batchPlannerMappingService.addBatchPlannerMapping(
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerMapping.getExternalFieldName(), "String",
				batchPlannerMapping.getInternalFieldName(), "String",
				StringPool.BLANK);
		}

		return batchPlannerPlan;
	}

	public BatchPlannerPlan addImportBatchPlannerPlan(
			PortletRequest portletRequest, String importFileURI)
		throws PortalException {

		String externalType = ParamUtil.getString(
			portletRequest, "externalType", "CSV");
		String internalClassName = ParamUtil.getString(
			portletRequest, "internalClassName");
		String name = ParamUtil.getString(portletRequest, "name");
		String taskItemDelegateName = ParamUtil.getString(
			portletRequest, "taskItemDelegateName");
		boolean template = ParamUtil.getBoolean(portletRequest, "template");

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				false, externalType, importFileURI, internalClassName, name,
				taskItemDelegateName, template);

		_batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "containsHeaders",
			_getCheckboxValue(portletRequest, "containsHeaders"));
		_batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "headlessEndpoint",
			ParamUtil.getString(portletRequest, "headlessEndpoint"));
		_batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "importStrategy",
			ParamUtil.getString(
				portletRequest, "importStrategy",
				BatchEngineImportTaskConstants.
					IMPORT_STRATEGY_NAME_ON_ERROR_FAIL));

		List<BatchPlannerMapping> batchPlannerMappings =
			_getImportBatchPlannerMappings(portletRequest);

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			_batchPlannerMappingService.addBatchPlannerMapping(
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerMapping.getExternalFieldName(), "String",
				batchPlannerMapping.getInternalFieldName(), "String",
				StringPool.BLANK);
		}

		return batchPlannerPlan;
	}

	public BatchPlannerPlan updateExportBatchPlannerPlan(
			PortletRequest portletRequest)
		throws PortalException {

		return _updateBatchPlannerPlan(
			portletRequest, _getExportBatchPlannerMappings(portletRequest));
	}

	public BatchPlannerPlan updateImportBatchPlannerPlan(
			PortletRequest portletRequest)
		throws PortalException {

		return _updateBatchPlannerPlan(
			portletRequest, _getImportBatchPlannerMappings(portletRequest));
	}

	private String _getCheckboxValue(
		PortletRequest portletRequest, String name) {

		String value = portletRequest.getParameter(name);

		if (value == null) {
			return Boolean.FALSE.toString();
		}

		return Boolean.TRUE.toString();
	}

	private List<BatchPlannerMapping> _getExportBatchPlannerMappings(
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

	private List<BatchPlannerMapping> _getImportBatchPlannerMappings(
		PortletRequest portletRequest) {

		List<BatchPlannerMapping> batchPlannerMappings = new ArrayList<>();

		Enumeration<String> enumeration = portletRequest.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String parameterName = enumeration.nextElement();

			if (!parameterName.startsWith("externalFieldName_") ||
				Validator.isNull(
					ParamUtil.getString(portletRequest, parameterName))) {

				continue;
			}

			String suffix = StringUtil.extractLast(
				parameterName, StringPool.UNDERLINE);

			if (Validator.isNull(
					ParamUtil.getString(
						portletRequest, "internalFieldName_" + suffix))) {

				continue;
			}

			BatchPlannerMapping batchPlannerMapping =
				BatchPlannerMappingUtil.create(0);

			batchPlannerMapping.setExternalFieldName(
				ParamUtil.getString(portletRequest, parameterName));
			batchPlannerMapping.setInternalFieldName(
				ParamUtil.getString(
					portletRequest, "internalFieldName_" + suffix));

			batchPlannerMappings.add(batchPlannerMapping);
		}

		return batchPlannerMappings;
	}

	private BatchPlannerPlan _updateBatchPlannerPlan(
			PortletRequest portletRequest,
			List<BatchPlannerMapping> batchPlannerMappings)
		throws PortalException {

		long batchPlannerPlanId = ParamUtil.getLong(
			portletRequest, "batchPlannerPlanId");

		String externalType = ParamUtil.getString(
			portletRequest, "externalType");
		String internalClassName = ParamUtil.getString(
			portletRequest, "internalClassName");
		String name = ParamUtil.getString(portletRequest, "name");

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.updateBatchPlannerPlan(
				batchPlannerPlanId, externalType, internalClassName, name);

		_batchPlannerPolicyService.updateBatchPlannerPolicy(
			batchPlannerPlanId, "containsHeaders",
			_getCheckboxValue(portletRequest, "containsHeaders"));
		_batchPlannerPolicyService.updateBatchPlannerPolicy(
			batchPlannerPlanId, "headlessEndpoint",
			ParamUtil.getString(portletRequest, "headlessEndpoint"));

		_batchPlannerMappingService.deleteBatchPlannerMappings(
			batchPlannerPlanId);

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			_batchPlannerMappingService.addBatchPlannerMapping(
				batchPlannerPlanId, batchPlannerMapping.getExternalFieldName(),
				"String", batchPlannerMapping.getInternalFieldName(), "String",
				StringPool.BLANK);
		}

		return batchPlannerPlan;
	}

	@Reference
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Reference
	private BatchPlannerPolicyService _batchPlannerPolicyService;

}