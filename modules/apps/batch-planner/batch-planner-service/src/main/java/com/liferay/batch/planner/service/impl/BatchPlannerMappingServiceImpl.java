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

package com.liferay.batch.planner.service.impl;

import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.base.BatchPlannerMappingServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		"json.web.service.context.name=batchplanner",
		"json.web.service.context.path=BatchPlannerMapping"
	},
	service = AopService.class
)
public class BatchPlannerMappingServiceImpl
	extends BatchPlannerMappingServiceBaseImpl {

	@Override
	public BatchPlannerMapping addBatchPlannerMapping(
			long batchPlannerPlanId, String externalFieldName,
			String externalFieldType, String internalFieldName,
			String internalFieldType, String script)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.UPDATE);

		return batchPlannerMappingLocalService.addBatchPlannerMapping(
			getUserId(), batchPlannerPlanId, externalFieldName,
			externalFieldType, internalFieldName, internalFieldType, script);
	}

	@Override
	public BatchPlannerMapping deleteBatchPlannerMapping(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.UPDATE);

		return batchPlannerMappingLocalService.deleteBatchPlannerMapping(
			batchPlannerPlanId, externalFieldName, internalFieldName);
	}

	@Override
	public List<BatchPlannerMapping> getBatchPlannerMappings(
			long batchPlannerPlanId)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerMappingLocalService.getBatchPlannerMappings(
			batchPlannerPlanId);
	}

	@Override
	public BatchPlannerMapping updateBatchPlannerMapping(
			long batchPlannerMappingId, String externalFieldName,
			String externalFieldType, String script)
		throws PortalException {

		BatchPlannerMapping batchPlannerMapping =
			batchPlannerMappingPersistence.findByPrimaryKey(
				batchPlannerMappingId);

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerMapping.getBatchPlannerPlanId(),
			ActionKeys.UPDATE);

		return batchPlannerMappingLocalService.updateBatchPlannerMapping(
			batchPlannerMappingId, externalFieldName, externalFieldType,
			script);
	}

	private static volatile ModelResourcePermission<BatchPlannerPlan>
		_batchPlannerPlanModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				BatchPlannerPlanServiceImpl.class,
				"_batchPlannerPlanModelResourcePermission",
				BatchPlannerPlan.class);

}