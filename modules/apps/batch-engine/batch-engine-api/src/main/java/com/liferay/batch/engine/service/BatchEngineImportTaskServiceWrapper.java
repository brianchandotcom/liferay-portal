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
	public com.liferay.batch.engine.model.BatchEngineImportTask
			addBatchEngineImportTask(
				String externalReferenceCode, long companyId, long userId,
				long batchSize, String callbackURL, String className,
				byte[] content, String contentType, String executeStatus,
				java.util.Map<String, String> fieldNameMappingMap,
				int importStrategy, String operation,
				java.util.Map<String, java.io.Serializable> parameters,
				String taskItemDelegateName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.addBatchEngineImportTask(
			externalReferenceCode, companyId, userId, batchSize, callbackURL,
			className, content, contentType, executeStatus, fieldNameMappingMap,
			importStrategy, operation, parameters, taskItemDelegateName);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTask
			addBatchEngineImportTask(
				String externalReferenceCode, long companyId, long userId,
				long batchSize, String callbackURL, String className,
				byte[] content, String contentType, String executeStatus,
				java.util.Map<String, String> fieldNameMappingMap,
				int importStrategy, String operation,
				java.util.Map<String, java.io.Serializable> parameters,
				String taskItemDelegateName,
				com.liferay.batch.engine.BatchEngineTaskItemDelegate<?>
					batchEngineTaskItemDelegate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.addBatchEngineImportTask(
			externalReferenceCode, companyId, userId, batchSize, callbackURL,
			className, content, contentType, executeStatus, fieldNameMappingMap,
			importStrategy, operation, parameters, taskItemDelegateName,
			batchEngineTaskItemDelegate);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTask
			getBatchEngineImportTask(long batchEngineImportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.getBatchEngineImportTask(
			batchEngineImportTaskId);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTask
			getBatchEngineImportTaskByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.
			getBatchEngineImportTaskByExternalReferenceCode(
				externalReferenceCode, companyId);
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
	public java.io.InputStream openContentInputStream(
			long batchEngineImportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.openContentInputStream(
			batchEngineImportTaskId);
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