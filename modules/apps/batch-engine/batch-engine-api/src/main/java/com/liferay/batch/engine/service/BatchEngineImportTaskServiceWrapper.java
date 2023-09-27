/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public BatchEngineImportTaskServiceWrapper() {
		this(null);
	}

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