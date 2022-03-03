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

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.service.BatchPlannerLogLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	property = "model.class.name=com.liferay.batch.planner.model.BatchPlannerLog",
	service = ModelResourcePermission.class
)
public class BatchPlannerLogModelResourcePermission
	implements ModelResourcePermission<BatchPlannerLog> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			BatchPlannerLog batchPlannerLog, String actionId)
		throws PortalException {

		if (contains(permissionChecker, batchPlannerLog, actionId)) {
			return;
		}

		throw new PrincipalException.MustHavePermission(
			permissionChecker, BatchPlannerLog.class.getName(),
			batchPlannerLog.getBatchPlannerLogId(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long batchPlannerLogId,
			String actionId)
		throws PortalException {

		check(
			permissionChecker,
			_batchPlannerLogLocalService.getBatchPlannerLog(batchPlannerLogId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			BatchPlannerLog batchPlannerLog, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(batchPlannerLog.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				batchPlannerLog.getCompanyId(), BatchPlannerLog.class.getName(),
				batchPlannerLog.getBatchPlannerPlanId(),
				batchPlannerLog.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			BatchPlannerLog.class.getName(),
			batchPlannerLog.getBatchPlannerPlanId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long batchPlannerLogId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_batchPlannerLogLocalService.getBatchPlannerLog(batchPlannerLogId),
			actionId);
	}

	@Override
	public String getModelName() {
		return BatchPlannerLog.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private BatchPlannerLogLocalService _batchPlannerLogLocalService;

}