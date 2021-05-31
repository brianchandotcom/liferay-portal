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

import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.persistence.BatchPlannerPolicyPersistence;
import com.liferay.batch.planner.service.persistence.BatchPlannerPolicyUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Igor Beslic
 */
public class BatchPlannerPolicyTestUtil {

	public static BatchPlannerPolicy randomBatchPlannerPolicy(
		User user, int nameSalt) {

		return randomBatchPlannerPolicy(
			user, _randomName(nameSalt), _randomName(nameSalt));
	}

	public static BatchPlannerPolicy randomBatchPlannerPolicy(
		User user, String name, String value) {

		return _randomBatchPlannerPolicy(
			user.getCompanyId(), name, value, user.getUserId());
	}

	private static BatchPlannerPolicy _randomBatchPlannerPolicy(
		long companyId, String name, String value, long userId) {

		BatchPlannerPolicyPersistence batchPlannerPolicyPersistence =
			BatchPlannerPolicyUtil.getPersistence();

		BatchPlannerPolicy batchPlannerPolicy =
			batchPlannerPolicyPersistence.create(RandomTestUtil.nextLong());

		batchPlannerPolicy.setCompanyId(companyId);
		batchPlannerPolicy.setUserId(userId);
		batchPlannerPolicy.setName(name);
		batchPlannerPolicy.setValue(value);

		return batchPlannerPolicy;
	}

	private static String _randomName(int nameSalt) {
		if (nameSalt < 0) {
			return null;
		}

		return String.format(
			"TEST-PLAN-%06d-%s", nameSalt % 999999,
			RandomTestUtil.randomString(RandomTestUtil.randomInt(20, 57)));
	}

}