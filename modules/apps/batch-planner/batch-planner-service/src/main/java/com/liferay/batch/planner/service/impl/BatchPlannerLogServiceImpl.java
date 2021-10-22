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

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.base.BatchPlannerLogServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		"json.web.service.context.name=batchplanner",
		"json.web.service.context.path=BatchPlannerLog"
	},
	service = AopService.class
)
public class BatchPlannerLogServiceImpl extends BatchPlannerLogServiceBaseImpl {

	@Override
	public BatchPlannerLog addBatchPlannerLog(
			long batchPlannerPlanId, String batchEngineExportERC,
			String batchEngineImportERC, String dispatchTriggerERC, int size,
			int status)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.UPDATE);

		return batchPlannerLogLocalService.addBatchPlannerLog(
			getUserId(), batchPlannerPlanId, batchEngineExportERC,
			batchEngineImportERC, dispatchTriggerERC, size, status);
	}

	@Override
	public BatchPlannerLog deleteBatchPlannerLog(long batchPlannerLogId)
		throws PortalException {

		BatchPlannerLog batchPlannerLog =
			batchPlannerLogPersistence.findByPrimaryKey(batchPlannerLogId);

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerLog.getBatchPlannerPlanId(),
			ActionKeys.UPDATE);

		return batchPlannerLogLocalService.deleteBatchPlannerLog(
			batchPlannerLogId);
	}

	@Override
	public BatchPlannerLog getBatchPlannerLog(long batchPlannerLogId)
		throws PortalException {

		BatchPlannerLog batchPlannerLog =
			batchPlannerLogPersistence.fetchByPrimaryKey(batchPlannerLogId);

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerLog.getBatchPlannerPlanId(),
			ActionKeys.VIEW);

		return batchPlannerLog;
	}

	public int getBatchPlannerLogsCount(long batchPlannerPlanId)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerLogLocalService.getBatchPlannerLogsCount(
			batchPlannerPlanId);
	}

	@Override
	public BatchPlannerLog getBatchPlannerPlanBatchPlannerLog(
			long batchPlannerPlanId)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerLogLocalService.getBatchPlannerPlanBatchPlannerLog(
			batchPlannerPlanId);
	}

	@Override
	public List<BatchPlannerLog> getCompanyBatchPlannerLogs(
			long companyId, boolean export, int start, int end,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws PortalException {

		checkPermission(companyId, ActionKeys.VIEW);

		return batchPlannerLogLocalService.getCompanyBatchPlannerLogs(
			companyId, export, start, end, orderByComparator);
	}

	@Override
	public List<BatchPlannerLog> getCompanyBatchPlannerLogs(
			long companyId, int start, int end,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws PortalException {

		checkPermission(companyId, ActionKeys.VIEW);

		return batchPlannerLogLocalService.getCompanyBatchPlannerLogs(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(long companyId)
		throws PortalException {

		checkPermission(companyId, ActionKeys.VIEW);

		return batchPlannerLogLocalService.getCompanyBatchPlannerLogsCount(
			companyId);
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(long companyId, boolean export)
		throws PortalException {

		checkPermission(companyId, ActionKeys.VIEW);

		return batchPlannerLogLocalService.getCompanyBatchPlannerLogsCount(
			companyId, export);
	}

	protected void checkPermission(long companyId, String actionKey)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.hasPermission(
				GroupConstants.DEFAULT_LIVE_GROUP_ID,
				BatchPlannerPlan.class.getName(), companyId, actionKey)) {

			throw new PrincipalException.MustHavePermission(
				getUserId(), actionKey);
		}
	}

	private static volatile ModelResourcePermission<BatchPlannerPlan>
		_batchPlannerPlanModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				BatchPlannerPlanServiceImpl.class,
				"_batchPlannerPlanModelResourcePermission",
				BatchPlannerPlan.class);

}