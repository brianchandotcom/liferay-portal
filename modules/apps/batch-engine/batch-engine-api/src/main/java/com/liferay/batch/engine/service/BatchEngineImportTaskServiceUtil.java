/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service;

import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for BatchEngineImportTask. This utility wraps
 * <code>com.liferay.batch.engine.service.impl.BatchEngineImportTaskServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskService
 * @generated
 */
public class BatchEngineImportTaskServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchEngineImportTaskServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static BatchEngineImportTask addBatchEngineImportTask(
			String externalReferenceCode, long companyId, long userId,
			long batchSize, String callbackURL, String className,
			byte[] content, String contentType, String executeStatus,
			Map<String, String> fieldNameMappingMap, int importStrategy,
			String operation, Map<String, Serializable> parameters,
			String taskItemDelegateName)
		throws PortalException {

		return getService().addBatchEngineImportTask(
			externalReferenceCode, companyId, userId, batchSize, callbackURL,
			className, content, contentType, executeStatus, fieldNameMappingMap,
			importStrategy, operation, parameters, taskItemDelegateName);
	}

	public static BatchEngineImportTask addBatchEngineImportTask(
			String externalReferenceCode, long companyId, long userId,
			long batchSize, String callbackURL, String className,
			byte[] content, String contentType, String executeStatus,
			Map<String, String> fieldNameMappingMap, int importStrategy,
			String operation, Map<String, Serializable> parameters,
			String taskItemDelegateName,
			com.liferay.batch.engine.BatchEngineTaskItemDelegate<?>
				batchEngineTaskItemDelegate)
		throws PortalException {

		return getService().addBatchEngineImportTask(
			externalReferenceCode, companyId, userId, batchSize, callbackURL,
			className, content, contentType, executeStatus, fieldNameMappingMap,
			importStrategy, operation, parameters, taskItemDelegateName,
			batchEngineTaskItemDelegate);
	}

	public static BatchEngineImportTask getBatchEngineImportTask(
			long batchEngineImportTaskId)
		throws PortalException {

		return getService().getBatchEngineImportTask(batchEngineImportTaskId);
	}

	public static BatchEngineImportTask
			getBatchEngineImportTaskByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().getBatchEngineImportTaskByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static List<BatchEngineImportTask> getBatchEngineImportTasks(
			long companyId, int start, int end)
		throws PortalException {

		return getService().getBatchEngineImportTasks(companyId, start, end);
	}

	public static List<BatchEngineImportTask> getBatchEngineImportTasks(
			long companyId, int start, int end,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws PortalException {

		return getService().getBatchEngineImportTasks(
			companyId, start, end, orderByComparator);
	}

	public static int getBatchEngineImportTasksCount(long companyId)
		throws PortalException {

		return getService().getBatchEngineImportTasksCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static InputStream openContentInputStream(
			long batchEngineImportTaskId)
		throws PortalException {

		return getService().openContentInputStream(batchEngineImportTaskId);
	}

	public static BatchEngineImportTaskService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<BatchEngineImportTaskService>
		_serviceSnapshot = new Snapshot<>(
			BatchEngineImportTaskServiceUtil.class,
			BatchEngineImportTaskService.class);

}