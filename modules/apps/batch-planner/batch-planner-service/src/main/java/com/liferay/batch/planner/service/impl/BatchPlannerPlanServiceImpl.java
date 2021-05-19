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

import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.base.BatchPlannerPlanServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		"json.web.service.context.name=batchplanner",
		"json.web.service.context.path=BatchPlannerPlan"
	},
	service = AopService.class
)
public class BatchPlannerPlanServiceImpl
	extends BatchPlannerPlanServiceBaseImpl {

	@Override
	public BatchPlannerPlan addBatchPlannerPlan(
			String name, String externalType)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		return batchPlannerPlanLocalService.addBatchPlannerPlan(
			permissionChecker.getUserId(), name, externalType);
	}

	@Override
	public BatchPlannerPlan updateBatchPlannerPlan(
			long batchPlannerPlanId, String name)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		return batchPlannerPlanLocalService.updateBatchPlannerPlan(
			batchPlannerPlanId, permissionChecker.getUserId(), name);
	}

}