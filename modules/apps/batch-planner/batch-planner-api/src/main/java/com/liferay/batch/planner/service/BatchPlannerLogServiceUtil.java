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
import com.liferay.portal.kernel.util.OrderByComparator;

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
	public static BatchPlannerLog getBatchPlannerLog(long batchPlannerLogId)
		throws PortalException {

		return getService().getBatchPlannerLog(batchPlannerLogId);
	}

	public static BatchPlannerLog getBatchPlannerPlanBatchPlannerLog(
			long batchPlannerPlanId)
		throws PortalException {

		return getService().getBatchPlannerPlanBatchPlannerLog(
			batchPlannerPlanId);
	}

	public static List<BatchPlannerLog> getCompanyBatchPlannerLogs(
			long companyId, boolean export, int start, int end,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws PortalException {

		return getService().getCompanyBatchPlannerLogs(
			companyId, export, start, end, orderByComparator);
	}

	public static List<BatchPlannerLog> getCompanyBatchPlannerLogs(
			long companyId, boolean export, int start, int end,
			OrderByComparator<BatchPlannerLog> orderByComparator,
			String searchByField, String searchByKeyword)
		throws PortalException {

		return getService().getCompanyBatchPlannerLogs(
			companyId, export, start, end, orderByComparator, searchByField,
			searchByKeyword);
	}

	public static List<BatchPlannerLog> getCompanyBatchPlannerLogs(
			long companyId, int start, int end,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws PortalException {

		return getService().getCompanyBatchPlannerLogs(
			companyId, start, end, orderByComparator);
	}

	public static List<BatchPlannerLog> getCompanyBatchPlannerLogs(
			long companyId, int start, int end,
			OrderByComparator<BatchPlannerLog> orderByComparator,
			String searchByField, String searchByKeyword)
		throws PortalException {

		return getService().getCompanyBatchPlannerLogs(
			companyId, start, end, orderByComparator, searchByField,
			searchByKeyword);
	}

	public static int getCompanyBatchPlannerLogsCount(long companyId)
		throws PortalException {

		return getService().getCompanyBatchPlannerLogsCount(companyId);
	}

	public static int getCompanyBatchPlannerLogsCount(
			long companyId, boolean export)
		throws PortalException {

		return getService().getCompanyBatchPlannerLogsCount(companyId, export);
	}

	public static int getCompanyBatchPlannerLogsCount(
			long companyId, boolean export, String searchByField,
			String searchByKeyword)
		throws PortalException {

		return getService().getCompanyBatchPlannerLogsCount(
			companyId, export, searchByField, searchByKeyword);
	}

	public static int getCompanyBatchPlannerLogsCount(
			long companyId, String searchByField, String searchByKeyword)
		throws PortalException {

		return getService().getCompanyBatchPlannerLogsCount(
			companyId, searchByField, searchByKeyword);
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