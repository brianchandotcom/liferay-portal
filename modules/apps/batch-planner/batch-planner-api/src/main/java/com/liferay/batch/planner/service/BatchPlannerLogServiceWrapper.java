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

	public BatchPlannerLogServiceWrapper() {
		this(null);
	}

	public BatchPlannerLogServiceWrapper(
		BatchPlannerLogService batchPlannerLogService) {

		_batchPlannerLogService = batchPlannerLogService;
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog getBatchPlannerLog(
			long batchPlannerLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getBatchPlannerLog(batchPlannerLogId);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog
			getBatchPlannerPlanBatchPlannerLog(long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getBatchPlannerPlanBatchPlannerLog(
			batchPlannerPlanId);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerLog>
			getCompanyBatchPlannerLogs(
				long companyId, boolean export, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerLog>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getCompanyBatchPlannerLogs(
			companyId, export, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerLog>
			getCompanyBatchPlannerLogs(
				long companyId, boolean export, String searchByField,
				String searchByKeyword, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerLog>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getCompanyBatchPlannerLogs(
			companyId, export, searchByField, searchByKeyword, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerLog>
			getCompanyBatchPlannerLogs(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerLog>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getCompanyBatchPlannerLogs(
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerLog>
			getCompanyBatchPlannerLogs(
				long companyId, String searchByField, String searchByKeyword,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerLog>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getCompanyBatchPlannerLogs(
			companyId, searchByField, searchByKeyword, start, end,
			orderByComparator);
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getCompanyBatchPlannerLogsCount(
			companyId);
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(long companyId, boolean export)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getCompanyBatchPlannerLogsCount(
			companyId, export);
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(
			long companyId, boolean export, String searchByField,
			String searchByKeyword)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getCompanyBatchPlannerLogsCount(
			companyId, export, searchByField, searchByKeyword);
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(
			long companyId, String searchByField, String searchByKeyword)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogService.getCompanyBatchPlannerLogsCount(
			companyId, searchByField, searchByKeyword);
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