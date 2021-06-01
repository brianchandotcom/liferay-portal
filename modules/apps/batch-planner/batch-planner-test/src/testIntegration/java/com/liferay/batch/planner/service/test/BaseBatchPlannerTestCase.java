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
import com.liferay.batch.planner.service.test.util.BatchPlannerPolicyTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Beslic
 */
public abstract class BaseBatchPlannerTestCase {

	protected BatchPlannerPlan addBatchPlannerPlan(int nameSeed)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.randomBatchPlannerPlan(nameSeed);

		return batchPlannerPlanService.addBatchPlannerPlan(
			batchPlannerPlan.getExternalType(), batchPlannerPlan.getName());
	}

	protected BatchPlannerPlan addBatchPlannerPlan(String name)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.randomBatchPlannerPlan(name);

		return batchPlannerPlanService.addBatchPlannerPlan(
			batchPlannerPlan.getExternalType(), batchPlannerPlan.getName());
	}

	protected List<BatchPlannerPolicy> addBatchPlannerPolicy(String... names)
		throws PortalException {

		if ((names == null) || (names.length == 0)) {
			throw new IllegalArgumentException();
		}

		BatchPlannerPlan batchPlannerPlan = addBatchPlannerPlan(300);

		List<BatchPlannerPolicy> batchPlannerPolicies = new ArrayList<>();

		for (String name : names) {
			BatchPlannerPolicy batchPlannerPolicy =
				BatchPlannerPolicyTestUtil.randomBatchPlannerPolicy(name, name);

			batchPlannerPolicies.add(
				batchPlannerPolicyService.addBatchPlannerPolicy(
					batchPlannerPlan.getBatchPlannerPlanId(),
					batchPlannerPolicy.getName(),
					batchPlannerPolicy.getValue()));
		}

		return batchPlannerPolicies;
	}

	@Inject
	protected BatchPlannerPlanService batchPlannerPlanService;

	@Inject
	protected BatchPlannerPolicyService batchPlannerPolicyService;

}