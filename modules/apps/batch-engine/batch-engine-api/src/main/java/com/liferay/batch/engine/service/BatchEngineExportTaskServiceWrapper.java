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

package com.liferay.batch.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BatchEngineExportTaskService}.
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTaskService
 * @generated
 */
public class BatchEngineExportTaskServiceWrapper
	implements BatchEngineExportTaskService,
			   ServiceWrapper<BatchEngineExportTaskService> {

	public BatchEngineExportTaskServiceWrapper(
		BatchEngineExportTaskService batchEngineExportTaskService) {

		_batchEngineExportTaskService = batchEngineExportTaskService;
	}

	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchEngineExportTask>
			getBatchEngineExportTasks(long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineExportTaskService.getBatchEngineExportTasks(
			companyId, start, end);
	}

	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchEngineExportTask>
			getBatchEngineExportTasks(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.engine.model.BatchEngineExportTask>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineExportTaskService.getBatchEngineExportTasks(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getBatchEngineExportTasksCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineExportTaskService.getBatchEngineExportTasksCount(
			companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchEngineExportTaskService.getOSGiServiceIdentifier();
	}

	@Override
	public BatchEngineExportTaskService getWrappedService() {
		return _batchEngineExportTaskService;
	}

	@Override
	public void setWrappedService(
		BatchEngineExportTaskService batchEngineExportTaskService) {

		_batchEngineExportTaskService = batchEngineExportTaskService;
	}

	private BatchEngineExportTaskService _batchEngineExportTaskService;

}