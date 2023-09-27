/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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