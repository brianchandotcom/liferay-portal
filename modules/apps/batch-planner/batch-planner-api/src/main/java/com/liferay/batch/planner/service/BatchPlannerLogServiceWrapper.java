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
 * Provides a wrapper for {@link BatchPlannerLogService}.
 *
 * @author Igor Beslic
 * @see BatchPlannerLogService
 * @generated
 */
public class BatchPlannerLogServiceWrapper
	implements BatchPlannerLogService, ServiceWrapper<BatchPlannerLogService> {

	public BatchPlannerLogServiceWrapper(
		BatchPlannerLogService batchPlannerLogService) {

		_batchPlannerLogService = batchPlannerLogService;
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog addBatchPlannerLog(
			long batchPlannerPlanId, String batchEngineExportERC,
			String batchEngineImportERC, String dispatchTriggerERC, int size,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.addBatchPlannerLog(
			batchPlannerPlanId, batchEngineExportERC, batchEngineImportERC,
			dispatchTriggerERC, size, status);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog
			deleteBatchPlannerLog(long batchPlannerLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.deleteBatchPlannerLog(batchPlannerLogId);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerLog>
			getBatchPlannerLogs(long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getBatchPlannerLogs(batchPlannerPlanId);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerLog>
			getBatchPlannerLogs(long batchPlannerPlanId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getBatchPlannerLogs(
			batchPlannerPlanId, start, end);
	}

	@Override
	public int getBatchPlannerLogsCount(long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getBatchPlannerLogsCount(
			batchPlannerPlanId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchPlannerLogService.getOSGiServiceIdentifier();
	}

	@Override
	public BatchPlannerLogService getWrappedService() {
		return _batchPlannerLogService;
	}

	@Override
	public void setWrappedService(
		BatchPlannerLogService batchPlannerLogService) {

		_batchPlannerLogService = batchPlannerLogService;
	}

	private BatchPlannerLogService _batchPlannerLogService;

}