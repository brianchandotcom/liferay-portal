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

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for BatchPlannerLog. This utility wraps
 * <code>com.liferay.batch.planner.service.impl.BatchPlannerLogServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Igor Beslic
 * @see BatchPlannerLogService
 * @generated
 */
public class BatchPlannerLogServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.planner.service.impl.BatchPlannerLogServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static BatchPlannerLog addBatchPlannerLog(
			long batchPlannerPlanId, String batchEngineExportERC,
			String batchEngineImportERC, String dispatchTriggerERC, int size,
			int status)
		throws PortalException {

		return getService().addBatchPlannerLog(
			batchPlannerPlanId, batchEngineExportERC, batchEngineImportERC,
			dispatchTriggerERC, size, status);
	}

	public static BatchPlannerLog deleteBatchPlannerLog(long batchPlannerLogId)
		throws PortalException {

		return getService().deleteBatchPlannerLog(batchPlannerLogId);
	}

	public static List<BatchPlannerLog> getBatchPlannerLogs(
			long batchPlannerPlanId)
		throws PortalException {

		return getService().getBatchPlannerLogs(batchPlannerPlanId);
	}

	public static List<BatchPlannerLog> getBatchPlannerLogs(
			long batchPlannerPlanId, int start, int end)
		throws PortalException {

		return getService().getBatchPlannerLogs(batchPlannerPlanId, start, end);
	}

	public static int getBatchPlannerLogsCount(long batchPlannerPlanId)
		throws PortalException {

		return getService().getBatchPlannerLogsCount(batchPlannerPlanId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static BatchPlannerLogService getService() {
		return _service;
	}

	private static volatile BatchPlannerLogService _service;

}