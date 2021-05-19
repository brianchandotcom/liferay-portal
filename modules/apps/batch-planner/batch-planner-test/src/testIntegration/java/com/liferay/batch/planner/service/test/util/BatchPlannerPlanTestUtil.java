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
import com.liferay.batch.planner.plan.PlanExternalType;
import com.liferay.batch.planner.service.persistence.BatchPlannerPlanPersistence;
import com.liferay.batch.planner.service.persistence.BatchPlannerPlanUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Igor Beslic
 */
public class BatchPlannerPlanTestUtil {

	public static BatchPlannerPlan randomBatchPlannerPlan(
		User user, int nameSalt) {

		return randomBatchPlannerPlan(user, _randomName(nameSalt));
	}

	public static BatchPlannerPlan randomBatchPlannerPlan(
		User user, String name) {

		PlanExternalType planExternalType = _randomPlanExternalType();

		return _randomBatchPlannerPlan(
			RandomTestUtil.randomBoolean(), user.getCompanyId(),
			RandomTestUtil.randomBoolean(), planExternalType.name(),
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			name, user.getUserId());
	}

	private static BatchPlannerPlan _randomBatchPlannerPlan(
		boolean active, long companyId, boolean export, String externalType,
		String externalURL, String internalClassName, String name,
		long userId) {

		BatchPlannerPlanPersistence batchPlannerPlanPersistence =
			BatchPlannerPlanUtil.getPersistence();

		BatchPlannerPlan batchPlannerPlan = batchPlannerPlanPersistence.create(
			RandomTestUtil.nextLong());

		batchPlannerPlan.setActive(active);
		batchPlannerPlan.setCompanyId(companyId);
		batchPlannerPlan.setExport(export);
		batchPlannerPlan.setExternalType(externalType);
		batchPlannerPlan.setExternalURL(externalURL);
		batchPlannerPlan.setInternalClassName(internalClassName);
		batchPlannerPlan.setName(name);
		batchPlannerPlan.setUserId(userId);

		return batchPlannerPlan;
	}

	private static String _randomName(int nameSalt) {
		if (nameSalt < 0) {
			return null;
		}

		return String.format(
			"TEST-PLAN-%06d-%s", nameSalt % 999999,
			RandomTestUtil.randomString(RandomTestUtil.randomInt(20, 57)));
	}

	private static PlanExternalType _randomPlanExternalType() {
		final PlanExternalType[] planExternalTypes = PlanExternalType.values();

		return planExternalTypes
			[RandomTestUtil.randomInt(0, planExternalTypes.length - 1)];
	}

}