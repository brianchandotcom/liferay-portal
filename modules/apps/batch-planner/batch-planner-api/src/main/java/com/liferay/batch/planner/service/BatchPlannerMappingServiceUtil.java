/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service;

import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for BatchPlannerMapping. This utility wraps
 * <code>com.liferay.batch.planner.service.impl.BatchPlannerMappingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Igor Beslic
 * @see BatchPlannerMappingService
 * @generated
 */
public class BatchPlannerMappingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.planner.service.impl.BatchPlannerMappingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static BatchPlannerMapping addBatchPlannerMapping(
			long batchPlannerPlanId, String externalFieldName,
			String externalFieldType, String internalFieldName,
			String internalFieldType, String script)
		throws PortalException {

		return getService().addBatchPlannerMapping(
			batchPlannerPlanId, externalFieldName, externalFieldType,
			internalFieldName, internalFieldType, script);
	}

	public static BatchPlannerMapping deleteBatchPlannerMapping(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws PortalException {

		return getService().deleteBatchPlannerMapping(
			batchPlannerPlanId, externalFieldName, internalFieldName);
	}

	public static void deleteBatchPlannerMappings(long batchPlannerPlanId)
		throws PortalException {

		getService().deleteBatchPlannerMappings(batchPlannerPlanId);
	}

	public static List<BatchPlannerMapping> getBatchPlannerMappings(
			long batchPlannerPlanId)
		throws PortalException {

		return getService().getBatchPlannerMappings(batchPlannerPlanId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static BatchPlannerMapping updateBatchPlannerMapping(
			long batchPlannerMappingId, String externalFieldName,
			String externalFieldType, String script)
		throws PortalException {

		return getService().updateBatchPlannerMapping(
			batchPlannerMappingId, externalFieldName, externalFieldType,
			script);
	}

	public static BatchPlannerMappingService getService() {
		return _service;
	}

	public static void setService(BatchPlannerMappingService service) {
		_service = service;
	}

	private static volatile BatchPlannerMappingService _service;

}