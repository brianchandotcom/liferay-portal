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

package com.liferay.batch.planner.service.test;

import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.BatchPlannerPolicyService;
import com.liferay.batch.planner.service.test.util.BatchPlannerPlanTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Beslic
 */
public abstract class BaseBatchPlannerTestCase {

	protected BatchPlannerPlan addBatchPlannerPlan(User user, int nameSeed)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.randomBatchPlannerPlan(user, nameSeed);

		return _batchPlannerPlanService.addBatchPlannerPlan(
			batchPlannerPlan.getName(), batchPlannerPlan.getExternalType());
	}

	protected BatchPlannerPlan addBatchPlannerPlan(User user, String name)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.randomBatchPlannerPlan(user, name);

		return _batchPlannerPlanService.addBatchPlannerPlan(
			batchPlannerPlan.getName(), batchPlannerPlan.getExternalType());
	}

	protected List<BatchPlannerPolicy> addBatchPlannerPolicy(
			User user, String... nameValues)
		throws PortalException {

		if ((nameValues == null) || ((nameValues.length % 2) != 0)) {
			throw new IllegalArgumentException();
		}

		BatchPlannerPlan batchPlannerPlan = addBatchPlannerPlan(user, 300);

		List<BatchPlannerPolicy> batchPlannerPolicies = new ArrayList<>();

		for (int i = 0; (i + 1) <= nameValues.length;) {
			batchPlannerPolicies.add(
				_batchPlannerPolicyService.addBatchPlannerPolicy(
					batchPlannerPlan.getBatchPlannerPlanId(), nameValues[i],
					nameValues[i + 1]));
		}

		return batchPlannerPolicies;
	}

	@Inject
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Inject
	private BatchPlannerPolicyService _batchPlannerPolicyService;

}