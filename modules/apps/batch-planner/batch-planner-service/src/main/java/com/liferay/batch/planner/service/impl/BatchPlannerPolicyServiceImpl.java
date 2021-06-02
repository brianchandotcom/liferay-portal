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
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.base.BatchPlannerPolicyServiceBaseImpl;
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
		"json.web.service.context.path=BatchPlannerPolicy"
	},
	service = AopService.class
)
public class BatchPlannerPolicyServiceImpl
	extends BatchPlannerPolicyServiceBaseImpl {

	@Override
	public BatchPlannerPolicy addBatchPlannerPolicy(
			long batchPlannerPlanId, String name, String value)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.UPDATE);

		return batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			getUserId(), batchPlannerPlanId, name, value);
	}

	@Override
	public BatchPlannerPolicy deleteBatchPlannerPolicy(
			long batchPlannerPlanId, String name)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.UPDATE);

		return batchPlannerPolicyLocalService.deleteBatchPlannerPolicy(
			batchPlannerPlanId, name);
	}

	@Override
	public List<BatchPlannerPolicy> getBatchPlannerPolicies(
			long batchPlannerPlanId)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerPolicyLocalService.getBatchPlannerPolicies(
			batchPlannerPlanId);
	}

	@Override
	public BatchPlannerPolicy getBatchPlannerPolicy(
			long batchPlannerPlanId, String name)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerPolicyLocalService.getBatchPlannerPolicy(
			batchPlannerPlanId, name);
	}

	@Override
	public boolean hasBatchPlannerPolicy(long batchPlannerPlanId, String name)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerPolicyLocalService.hasBatchPlannerPolicy(
			batchPlannerPlanId, name);
	}

	@Override
	public BatchPlannerPolicy updateBatchPlannerPolicy(
			long batchPlannerPlanId, String name, String value)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.UPDATE);

		return batchPlannerPolicyLocalService.updateBatchPlannerPolicy(
			batchPlannerPlanId, name, value);
	}

	private static volatile ModelResourcePermission<BatchPlannerPlan>
		_batchPlannerPlanModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				BatchPlannerPlanServiceImpl.class,
				"_batchPlannerPlanModelResourcePermission",
				BatchPlannerPlan.class);

}