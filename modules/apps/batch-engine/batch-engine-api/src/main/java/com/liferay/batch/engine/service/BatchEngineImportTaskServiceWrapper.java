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
 * Provides a wrapper for {@link BatchEngineImportTaskService}.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskService
 * @generated
 */
public class BatchEngineImportTaskServiceWrapper
	implements BatchEngineImportTaskService,
			   ServiceWrapper<BatchEngineImportTaskService> {

	public BatchEngineImportTaskServiceWrapper(
		BatchEngineImportTaskService batchEngineImportTaskService) {

		_batchEngineImportTaskService = batchEngineImportTaskService;
	}

	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchEngineImportTask>
			getBatchEngineImportTasks(long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.getBatchEngineImportTasks(
			companyId, start, end);
	}

	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchEngineImportTask>
			getBatchEngineImportTasks(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.engine.model.BatchEngineImportTask>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.getBatchEngineImportTasks(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getBatchEngineImportTasksCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.getBatchEngineImportTasksCount(
			companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchEngineImportTaskService.getOSGiServiceIdentifier();
	}

	@Override
	public BatchEngineImportTaskService getWrappedService() {
		return _batchEngineImportTaskService;
	}

	@Override
	public void setWrappedService(
		BatchEngineImportTaskService batchEngineImportTaskService) {

		_batchEngineImportTaskService = batchEngineImportTaskService;
	}

	private BatchEngineImportTaskService _batchEngineImportTaskService;

}