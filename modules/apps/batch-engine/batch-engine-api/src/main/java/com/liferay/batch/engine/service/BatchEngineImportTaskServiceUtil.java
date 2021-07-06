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

import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

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

	public static BatchEngineImportTaskService getService() {
		return _service;
	}

	private static volatile BatchEngineImportTaskService _service;

}