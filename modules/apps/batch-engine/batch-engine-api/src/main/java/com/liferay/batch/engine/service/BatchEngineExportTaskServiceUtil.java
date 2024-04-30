/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service;

import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for BatchEngineExportTask. This utility wraps
 * <code>com.liferay.batch.engine.service.impl.BatchEngineExportTaskServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTaskService
 * @generated
 */
public class BatchEngineExportTaskServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchEngineExportTaskServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static BatchEngineExportTask addBatchEngineExportTask(
			String externalReferenceCode, long companyId, long userId,
			String callbackURL, String className, String contentType,
			String executeStatus, List<String> fieldNamesList,
			Map<String, Serializable> parameters, String taskItemDelegateName)
		throws PortalException {

		return getService().addBatchEngineExportTask(
			externalReferenceCode, companyId, userId, callbackURL, className,
			contentType, executeStatus, fieldNamesList, parameters,
			taskItemDelegateName);
	}

	public static BatchEngineExportTask getBatchEngineExportTask(
			long batchEngineExportTaskId)
		throws PortalException {

		return getService().getBatchEngineExportTask(batchEngineExportTaskId);
	}

	public static BatchEngineExportTask
			getBatchEngineExportTaskByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().getBatchEngineExportTaskByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static List<BatchEngineExportTask> getBatchEngineExportTasks(
			long companyId, int start, int end)
		throws PortalException {

		return getService().getBatchEngineExportTasks(companyId, start, end);
	}

	public static List<BatchEngineExportTask> getBatchEngineExportTasks(
			long companyId, int start, int end,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws PortalException {

		return getService().getBatchEngineExportTasks(
			companyId, start, end, orderByComparator);
	}

	public static int getBatchEngineExportTasksCount(long companyId)
		throws PortalException {

		return getService().getBatchEngineExportTasksCount(companyId);
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
			long batchEngineExportTaskId)
		throws PortalException {

		return getService().openContentInputStream(batchEngineExportTaskId);
	}

	public static BatchEngineExportTaskService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<BatchEngineExportTaskService>
		_serviceSnapshot = new Snapshot<>(
			BatchEngineExportTaskServiceUtil.class,
			BatchEngineExportTaskService.class);

}