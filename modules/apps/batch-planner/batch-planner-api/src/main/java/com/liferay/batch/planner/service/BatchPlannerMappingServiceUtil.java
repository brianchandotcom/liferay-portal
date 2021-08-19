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

	private static volatile BatchPlannerMappingService _service;

}