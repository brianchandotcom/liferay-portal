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

package com.liferay.batch.planner.service.test.util;

import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.BatchPlannerPolicyServiceUtil;
import com.liferay.batch.planner.service.persistence.BatchPlannerPolicyPersistence;
import com.liferay.batch.planner.service.persistence.BatchPlannerPolicyUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Beslic
 */
public class BatchPlannerPolicyTestUtil {

	public static List<BatchPlannerPolicy> addBatchPlannerPolicy(
			String... names)
		throws PortalException {

		if ((names == null) || (names.length == 0)) {
			throw new IllegalArgumentException();
		}

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.addBatchPlannerPlan(true, 300);

		List<BatchPlannerPolicy> batchPlannerPolicies = new ArrayList<>();

		for (String name : names) {
			BatchPlannerPolicy batchPlannerPolicy = randomBatchPlannerPolicy(
				name, name);

			batchPlannerPolicies.add(
				BatchPlannerPolicyServiceUtil.addBatchPlannerPolicy(
					batchPlannerPlan.getBatchPlannerPlanId(),
					batchPlannerPolicy.getName(),
					batchPlannerPolicy.getValue()));
		}

		return batchPlannerPolicies;
	}

	public static BatchPlannerPolicy randomBatchPlannerPolicy(
		String name, String value) {

		return _randomBatchPlannerPolicy(name, value);
	}

	private static BatchPlannerPolicy _randomBatchPlannerPolicy(
		String name, String value) {

		BatchPlannerPolicyPersistence batchPlannerPolicyPersistence =
			BatchPlannerPolicyUtil.getPersistence();

		BatchPlannerPolicy batchPlannerPolicy =
			batchPlannerPolicyPersistence.create(RandomTestUtil.nextLong());

		batchPlannerPolicy.setName(name);
		batchPlannerPolicy.setValue(value);

		return batchPlannerPolicy;
	}

}