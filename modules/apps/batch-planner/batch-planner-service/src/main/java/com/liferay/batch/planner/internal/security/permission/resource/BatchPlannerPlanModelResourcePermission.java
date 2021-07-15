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

package com.liferay.batch.planner.internal.security.permission.resource;

import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerPlanLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.batch.planner.model.BatchPlannerPlan",
	service = ModelResourcePermission.class
)
public class BatchPlannerPlanModelResourcePermission
	implements ModelResourcePermission<BatchPlannerPlan> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			BatchPlannerPlan batchPlannerPlan, String actionId)
		throws PortalException {

		if (contains(permissionChecker, batchPlannerPlan, actionId)) {
			return;
		}

		throw new PrincipalException.MustHavePermission(
			permissionChecker, BatchPlannerPlan.class.getName(),
			batchPlannerPlan.getBatchPlannerPlanId(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long batchPlannerPlanId,
			String actionId)
		throws PortalException {

		check(
			permissionChecker,
			_batchPlannerPlanLocalService.getBatchPlannerPlan(
				batchPlannerPlanId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			BatchPlannerPlan batchPlannerPlan, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(batchPlannerPlan.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				batchPlannerPlan.getCompanyId(),
				BatchPlannerPlan.class.getName(),
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerPlan.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			BatchPlannerPlan.class.getName(),
			batchPlannerPlan.getBatchPlannerPlanId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long batchPlannerPlanId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_batchPlannerPlanLocalService.getBatchPlannerPlan(
				batchPlannerPlanId),
			actionId);
	}

	@Override
	public String getModelName() {
		return BatchPlannerPlan.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

}