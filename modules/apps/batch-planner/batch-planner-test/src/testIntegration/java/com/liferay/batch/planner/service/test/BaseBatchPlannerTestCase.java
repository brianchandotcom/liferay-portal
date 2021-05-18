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
import com.liferay.batch.planner.plan.PlanExternalType;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.test.util.BatchPlannerPlanTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.test.rule.Inject;

/**
 * @author Igor Beslic
 */
public abstract class BaseBatchPlannerTestCase {

	protected BatchPlannerPlan addBatchPlannerPlan(User user, int nameSeed)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.randomBatchPlannerPlan(user, nameSeed);

		return _batchPlannerPlanService.addBatchPlannerPlan(
			batchPlannerPlan.getName(),
			PlanExternalType.valueOf(batchPlannerPlan.getExternalType()));
	}

	protected BatchPlannerPlan addBatchPlannerPlan(User user, String name)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.randomBatchPlannerPlan(user, name);

		return _batchPlannerPlanService.addBatchPlannerPlan(
			batchPlannerPlan.getName(),
			PlanExternalType.valueOf(batchPlannerPlan.getExternalType()));
	}

	@Inject
	private BatchPlannerPlanService _batchPlannerPlanService;

}