/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public BatchEngineExportTaskServiceWrapper() {
		this(null);
	}

	public BatchEngineExportTaskServiceWrapper(
		BatchEngineExportTaskService batchEngineExportTaskService) {

		_batchEngineExportTaskService = batchEngineExportTaskService;
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineExportTask
			addBatchEngineExportTask(
				String externalReferenceCode, long companyId, long userId,
				String callbackURL, String className, String contentType,
				String executeStatus, java.util.List<String> fieldNamesList,
				java.util.Map<String, java.io.Serializable> parameters,
				String taskItemDelegateName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineExportTaskService.addBatchEngineExportTask(
			externalReferenceCode, companyId, userId, callbackURL, className,
			contentType, executeStatus, fieldNamesList, parameters,
			taskItemDelegateName);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineExportTask
			getBatchEngineExportTask(long batchEngineExportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineExportTaskService.getBatchEngineExportTask(
			batchEngineExportTaskId);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineExportTask
			getBatchEngineExportTaskByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineExportTaskService.
			getBatchEngineExportTaskByExternalReferenceCode(
				externalReferenceCode, companyId);
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
	public java.io.InputStream openContentInputStream(
			long batchEngineExportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineExportTaskService.openContentInputStream(
			batchEngineExportTaskId);
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