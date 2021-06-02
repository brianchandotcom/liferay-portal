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
 * Provides a wrapper for {@link BatchPlannerPolicyService}.
 *
 * @author Igor Beslic
 * @see BatchPlannerPolicyService
 * @generated
 */
public class BatchPlannerPolicyServiceWrapper
	implements BatchPlannerPolicyService,
			   ServiceWrapper<BatchPlannerPolicyService> {

	public BatchPlannerPolicyServiceWrapper(
		BatchPlannerPolicyService batchPlannerPolicyService) {

		_batchPlannerPolicyService = batchPlannerPolicyService;
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
			addBatchPlannerPolicy(
				long batchPlannerPlanId, String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyService.addBatchPlannerPolicy(
			batchPlannerPlanId, name, value);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
			deleteBatchPlannerPolicy(long batchPlannerPlanId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyService.deleteBatchPlannerPolicy(
			batchPlannerPlanId, name);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerPolicy>
			getBatchPlannerPolicies(long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyService.getBatchPlannerPolicies(
			batchPlannerPlanId);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
			getBatchPlannerPolicy(long batchPlannerPlanId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyService.getBatchPlannerPolicy(
			batchPlannerPlanId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchPlannerPolicyService.getOSGiServiceIdentifier();
	}

	@Override
	public boolean hasBatchPlannerPolicy(long batchPlannerPlanId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyService.hasBatchPlannerPolicy(
			batchPlannerPlanId, name);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
			updateBatchPlannerPolicy(
				long batchPlannerPlanId, String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyService.updateBatchPlannerPolicy(
			batchPlannerPlanId, name, value);
	}

	@Override
	public BatchPlannerPolicyService getWrappedService() {
		return _batchPlannerPolicyService;
	}

	@Override
	public void setWrappedService(
		BatchPlannerPolicyService batchPlannerPolicyService) {

		_batchPlannerPolicyService = batchPlannerPolicyService;
	}

	private BatchPlannerPolicyService _batchPlannerPolicyService;

}