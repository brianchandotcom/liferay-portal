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

import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.BatchPlannerMappingLocalService;
import com.liferay.batch.planner.service.BatchPlannerMappingService;
import com.liferay.batch.planner.service.BatchPlannerPlanLocalService;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.BatchPlannerPolicyLocalService;
import com.liferay.batch.planner.service.BatchPlannerPolicyService;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
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

		if (externalType.equals("CSV")) {
			_batchPlannerPolicyService.addBatchPlannerPolicy(
				batchPlannerPlan.getBatchPlannerPlanId(), "csvSeparator",
				ParamUtil.getString(portletRequest, "csvSeparator"));
		}

		_batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "headlessEndpoint",
			ParamUtil.getString(portletRequest, "headlessEndpoint"));
		_batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "onErrorFail",
			_getCheckboxValue(portletRequest, "onErrorFail"));

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

	public BatchPlannerPlan copyBatchPlannerPlan(
			long userId, long batchPlannerPlanId, String externalURL,
			String name)
		throws PortalException {

		User user = _userLocalService.fetchUser(userId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		try {
			return _copyBatchPlannerPlan(batchPlannerPlanId, externalURL, name);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
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

	private BatchPlannerPlan _copyBatchPlannerPlan(
			long batchPlannerPlanId, String externalURL, String name)
		throws PortalException {

		BatchPlannerPlan templateBatchPlannerPlan =
			_batchPlannerPlanService.fetchBatchPlannerPlan(batchPlannerPlanId);

		if ((templateBatchPlannerPlan == null) ||
			!templateBatchPlannerPlan.isTemplate()) {

			throw new IllegalArgumentException(
				"Bbatch planner plan " + batchPlannerPlanId +
					" is not a template");
		}

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				templateBatchPlannerPlan.isExport(),
				templateBatchPlannerPlan.getExternalType(),
				"file://" + externalURL,
				templateBatchPlannerPlan.getInternalClassName(), name, null,
				false);

		List<BatchPlannerMapping> batchPlannerMappings =
			_batchPlannerMappingService.getBatchPlannerMappings(
				templateBatchPlannerPlan.getBatchPlannerPlanId());

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			_batchPlannerMappingLocalService.addBatchPlannerMapping(
				batchPlannerPlan.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerMapping.getExternalFieldName(),
				batchPlannerMapping.getExternalFieldType(),
				batchPlannerMapping.getInternalFieldName(),
				batchPlannerMapping.getInternalFieldType(),
				batchPlannerMapping.getScript());
		}

		List<BatchPlannerPolicy> batchPlannerPolicies =
			_batchPlannerPolicyService.getBatchPlannerPolicies(
				templateBatchPlannerPlan.getBatchPlannerPlanId());

		for (BatchPlannerPolicy batchPlannerPolicy : batchPlannerPolicies) {
			_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
				batchPlannerPlan.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName(), batchPlannerPolicy.getValue());
		}

		return batchPlannerPlan;
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
	private BatchPlannerMappingLocalService _batchPlannerMappingLocalService;

	@Reference
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Reference
	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Reference
	private BatchPlannerPolicyLocalService _batchPlannerPolicyLocalService;

	@Reference
	private BatchPlannerPolicyService _batchPlannerPolicyService;

	@Reference
	private UserLocalService _userLocalService;

}