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

package com.liferay.batch.planner.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BatchPlannerPlanService}.
 *
 * @author Igor Beslic
 * @see BatchPlannerPlanService
 * @generated
 */
public class BatchPlannerPlanServiceWrapper
	implements BatchPlannerPlanService,
			   ServiceWrapper<BatchPlannerPlanService> {

	public BatchPlannerPlanServiceWrapper(
		BatchPlannerPlanService batchPlannerPlanService) {

		_batchPlannerPlanService = batchPlannerPlanService;
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan addBatchPlannerPlan(
			String name,
			com.liferay.batch.planner.plan.PlanExternalType planExternalType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.addBatchPlannerPlan(
			name, planExternalType);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchPlannerPlanService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan
			updateBatchPlannerPlan(long batchPlannerPlanId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.updateBatchPlannerPlan(
			batchPlannerPlanId, name);
	}

	@Override
	public BatchPlannerPlanService getWrappedService() {
		return _batchPlannerPlanService;
	}

	@Override
	public void setWrappedService(
		BatchPlannerPlanService batchPlannerPlanService) {

		_batchPlannerPlanService = batchPlannerPlanService;
	}

	private BatchPlannerPlanService _batchPlannerPlanService;

}