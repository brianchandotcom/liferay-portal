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
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

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

		_getBatchPlannerPlan(batchPlannerPlanId);

		return batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			getUserId(), batchPlannerPlanId, name, value);
	}

	@Override
	public BatchPlannerPolicy deleteBatchPlannerPolicy(
			long batchPlannerPlanId, String name)
		throws PortalException {

		BatchPlannerPolicy batchPlannerPolicy = getBatchPlannerPolicy(
			batchPlannerPlanId, name);

		return batchPlannerPolicyLocalService.deleteBatchPlannerPolicy(
			batchPlannerPolicy.getBatchPlannerPlanId(), name);
	}

	@Override
	public List<BatchPlannerPolicy> getBatchPlannerPolicies(
			long batchPlannerPlanId)
		throws PortalException {

		_getBatchPlannerPlan(batchPlannerPlanId);

		return batchPlannerPolicyLocalService.getBatchPlannerPolicies(
			batchPlannerPlanId);
	}

	@Override
	public BatchPlannerPolicy getBatchPlannerPolicy(
			long batchPlannerPlanId, String name)
		throws PortalException {

		_getBatchPlannerPlan(batchPlannerPlanId);

		return batchPlannerPolicyLocalService.getBatchPlannerPolicy(
			batchPlannerPlanId, name);
	}

	@Override
	public boolean hasBatchPlannerPolicy(long batchPlannerPlanId, String name)
		throws PortalException {

		_getBatchPlannerPlan(batchPlannerPlanId);

		return batchPlannerPolicyLocalService.hasBatchPlannerPolicy(
			batchPlannerPlanId, name);
	}

	@Override
	public BatchPlannerPolicy updateBatchPlannerPolicy(
			long batchPlannerPlanId, String name, String value)
		throws PortalException {

		BatchPlannerPolicy batchPlannerPolicy = getBatchPlannerPolicy(
			batchPlannerPlanId, name);

		return batchPlannerPolicyLocalService.updateBatchPlannerPolicy(
			batchPlannerPolicy.getBatchPlannerPlanId(), name, value);
	}

	private BatchPlannerPlan _getBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isSignedIn()) {
			throw new PrincipalException();
		}

		return batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);
	}

}