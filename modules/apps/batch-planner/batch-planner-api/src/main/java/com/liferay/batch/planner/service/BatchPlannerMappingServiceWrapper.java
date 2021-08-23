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
 * Provides a wrapper for {@link BatchPlannerMappingService}.
 *
 * @author Igor Beslic
 * @see BatchPlannerMappingService
 * @generated
 */
public class BatchPlannerMappingServiceWrapper
	implements BatchPlannerMappingService,
			   ServiceWrapper<BatchPlannerMappingService> {

	public BatchPlannerMappingServiceWrapper(
		BatchPlannerMappingService batchPlannerMappingService) {

		_batchPlannerMappingService = batchPlannerMappingService;
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
			addBatchPlannerMapping(
				long batchPlannerPlanId, String externalFieldName,
				String externalFieldType, String internalFieldName,
				String internalFieldType, String script)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerMappingService.addBatchPlannerMapping(
			batchPlannerPlanId, externalFieldName, externalFieldType,
			internalFieldName, internalFieldType, script);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
			deleteBatchPlannerMapping(
				long batchPlannerPlanId, String externalFieldName,
				String internalFieldName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerMappingService.deleteBatchPlannerMapping(
			batchPlannerPlanId, externalFieldName, internalFieldName);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerMapping>
			getBatchPlannerMappings(long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerMappingService.getBatchPlannerMappings(
			batchPlannerPlanId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchPlannerMappingService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
			updateBatchPlannerMapping(
				long batchPlannerMappingId, String externalFieldName,
				String externalFieldType, String script)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerMappingService.updateBatchPlannerMapping(
			batchPlannerMappingId, externalFieldName, externalFieldType,
			script);
	}

	@Override
	public BatchPlannerMappingService getWrappedService() {
		return _batchPlannerMappingService;
	}

	@Override
	public void setWrappedService(
		BatchPlannerMappingService batchPlannerMappingService) {

		_batchPlannerMappingService = batchPlannerMappingService;
	}

	private BatchPlannerMappingService _batchPlannerMappingService;

}