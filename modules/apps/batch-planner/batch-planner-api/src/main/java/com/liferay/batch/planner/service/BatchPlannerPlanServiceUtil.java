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

import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for BatchPlannerPlan. This utility wraps
 * <code>com.liferay.batch.planner.service.impl.BatchPlannerPlanServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Igor Beslic
 * @see BatchPlannerPlanService
 * @generated
 */
public class BatchPlannerPlanServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.planner.service.impl.BatchPlannerPlanServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static BatchPlannerPlan addBatchPlannerPlan(
			boolean export, String externalType, String externalURL,
			String internalClassName, String name, String taskItemDelegateName,
			boolean template)
		throws PortalException {

		return getService().addBatchPlannerPlan(
			export, externalType, externalURL, internalClassName, name,
			taskItemDelegateName, template);
	}

	public static BatchPlannerPlan deleteBatchPlannerPlan(
			long batchPlannerPlanId)
		throws PortalException {

		return getService().deleteBatchPlannerPlan(batchPlannerPlanId);
	}

	public static BatchPlannerPlan fetchBatchPlannerPlan(
			long batchPlannerPlanId)
		throws PortalException {

		return getService().fetchBatchPlannerPlan(batchPlannerPlanId);
	}

	public static BatchPlannerPlan getBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		return getService().getBatchPlannerPlan(batchPlannerPlanId);
	}

	public static List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, boolean export, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getService().getBatchPlannerPlans(
			companyId, export, template, start, end, orderByComparator);
	}

	public static List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getService().getBatchPlannerPlans(
			companyId, template, start, end, orderByComparator);
	}

	public static List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, int start, int end) {

		return getService().getBatchPlannerPlans(companyId, start, end);
	}

	public static List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getService().getBatchPlannerPlans(
			companyId, start, end, orderByComparator);
	}

	public static int getBatchPlannerPlansCount(long companyId) {
		return getService().getBatchPlannerPlansCount(companyId);
	}

	public static int getBatchPlannerPlansCount(
		long companyId, boolean template) {

		return getService().getBatchPlannerPlansCount(companyId, template);
	}

	public static int getBatchPlannerPlansCount(
		long companyId, boolean export, boolean template) {

		return getService().getBatchPlannerPlansCount(
			companyId, export, template);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static BatchPlannerPlan updateBatchPlannerPlan(
			long batchPlannerPlanId, String name)
		throws PortalException {

		return getService().updateBatchPlannerPlan(batchPlannerPlanId, name);
	}

	public static BatchPlannerPlanService getService() {
		return _service;
	}

	private static volatile BatchPlannerPlanService _service;

}