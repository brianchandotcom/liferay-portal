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

package com.liferay.batch.planner.model.impl;

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.BatchPlannerLogLocalServiceUtil;
import com.liferay.batch.planner.service.BatchPlannerMappingLocalServiceUtil;
import com.liferay.batch.planner.service.BatchPlannerPolicyLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Igor Beslic
 */
public class BatchPlannerPlanImpl extends BatchPlannerPlanBaseImpl {

	@Override
	public BatchPlannerLog fetchBatchPlannerLog() {
		return BatchPlannerLogLocalServiceUtil.
			fetchBatchPlannerPlanBatchPlannerLog(getBatchPlannerPlanId());
	}

	@Override
	public BatchPlannerPolicy fetchBatchPlannerPolicy(String name) {
		return BatchPlannerPolicyLocalServiceUtil.fetchBatchPlannerPolicy(
			getBatchPlannerPlanId(), name);
	}

	@Override
	public BatchPlannerLog getBatchPlannerLog() throws PortalException {
		return BatchPlannerLogLocalServiceUtil.
			getBatchPlannerPlanBatchPlannerLog(getBatchPlannerPlanId());
	}

	@Override
	public List<BatchPlannerMapping> getBatchPlannerMappings() {
		return BatchPlannerMappingLocalServiceUtil.getBatchPlannerMappings(
			getBatchPlannerPlanId());
	}

	@Override
	public List<BatchPlannerPolicy> getBatchPlannerPolicies() {
		return BatchPlannerPolicyLocalServiceUtil.getBatchPlannerPolicies(
			getBatchPlannerPlanId());
	}

	@Override
	public BatchPlannerPolicy getBatchPlannerPolicy(String name)
		throws PortalException {

		return BatchPlannerPolicyLocalServiceUtil.getBatchPlannerPolicy(
			getBatchPlannerPlanId(), name);
	}

}