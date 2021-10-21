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

import com.liferay.batch.planner.constants.BatchPlannerActionKeys;
import com.liferay.batch.planner.constants.BatchPlannerConstants;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.base.BatchPlannerPlanServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
			boolean export, String externalType, String externalURL,
			String internalClassName, String name, String taskItemDelegateName,
			boolean template)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		_portletResourcePermission.check(
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			BatchPlannerActionKeys.ADD_BATCH_PLANNER_PLAN);

		return batchPlannerPlanLocalService.addBatchPlannerPlan(
			permissionChecker.getUserId(), export, externalType, externalURL,
			internalClassName, name, taskItemDelegateName, template);
	}

	@Override
	public BatchPlannerPlan deleteBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.DELETE);

		return batchPlannerPlanLocalService.deleteBatchPlannerPlan(
			batchPlannerPlanId);
	}

	@Override
	public BatchPlannerPlan fetchBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.fetchByPrimaryKey(batchPlannerPlanId);

		if (batchPlannerPlan == null) {
			return null;
		}

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerPlan;
	}

	@Override
	public BatchPlannerPlan getBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, boolean export, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return batchPlannerPlanPersistence.filterFindByC_E_T(
			companyId, export, template, start, end, orderByComparator);
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return batchPlannerPlanPersistence.filterFindByC_T(
			companyId, template, start, end, orderByComparator);
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, int start, int end) {

		return batchPlannerPlanPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return batchPlannerPlanPersistence.filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getBatchPlannerPlansCount(long companyId) {
		return batchPlannerPlanPersistence.filterCountByCompanyId(companyId);
	}

	@Override
	public int getBatchPlannerPlansCount(long companyId, boolean template) {
		return batchPlannerPlanPersistence.filterCountByC_T(
			companyId, template);
	}

	@Override
	public int getBatchPlannerPlansCount(
		long companyId, boolean export, boolean template) {

		return batchPlannerPlanPersistence.filterCountByC_E_T(
			companyId, export, template);
	}

	@Override
	public BatchPlannerPlan updateBatchPlannerPlan(
			long batchPlannerPlanId, String name)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.UPDATE);

		return batchPlannerPlanLocalService.updateBatchPlannerPlan(
			getUserId(), batchPlannerPlanId, name);
	}

	private static volatile ModelResourcePermission<BatchPlannerPlan>
		_batchPlannerPlanModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				BatchPlannerPlanServiceImpl.class,
				"_batchPlannerPlanModelResourcePermission",
				BatchPlannerPlan.class);

	@Reference(
		target = "(resource.name=" + BatchPlannerConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}