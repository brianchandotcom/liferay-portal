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