/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.model.impl;

import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.BatchPlannerMappingLocalServiceUtil;
import com.liferay.batch.planner.service.BatchPlannerPolicyLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Igor Beslic
 */
public class BatchPlannerPlanImpl extends BatchPlannerPlanBaseImpl {

	@Override
	public BatchPlannerPolicy fetchBatchPlannerPolicy(String name) {
		return BatchPlannerPolicyLocalServiceUtil.fetchBatchPlannerPolicy(
			getBatchPlannerPlanId(), name);
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