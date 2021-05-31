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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class BatchPlannerPolicyServiceTest extends BaseBatchPlannerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddBatchPlannerPolicy() {
		User user1 = UserTestUtil.addUser(CompanyTestUtil.addCompany());

		UserTestUtil.setUser(user1);
	}

	@Test
	public void testDeleteBatchPlannerPolicy() throws Exception {
		User omniAdminUser = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(omniAdminUser);

		User user1 = UserTestUtil.addUser(CompanyTestUtil.addCompany());

		UserTestUtil.setUser(user1);

		List<BatchPlannerPolicy> batchPlannerPolicies = addBatchPlannerPolicy(
			user1, "policy_1", "policy_2", "policy_3", "policy_4", "policy_5");

		Assert.assertEquals("policies count", 5, batchPlannerPolicies.size());

		BatchPlannerPolicy batchPlannerPolicy = batchPlannerPolicies.get(0);

		batchPlannerPolicyService.deleteBatchPlannerPolicy(
			batchPlannerPolicy.getBatchPlannerPlanId(),
			batchPlannerPolicy.getName());

		Assert.assertFalse(
			batchPlannerPolicyService.hasBatchPlannerPolicy(
				batchPlannerPolicy.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName()));

		batchPlannerPolicies =
			batchPlannerPolicyService.getBatchPlannerPolicies(
				batchPlannerPolicy.getBatchPlannerPlanId());

		Assert.assertEquals("policies count", 5, batchPlannerPolicies.size());

		batchPlannerPolicy = batchPlannerPolicies.get(0);

		Assert.assertTrue(
			batchPlannerPolicyService.hasBatchPlannerPolicy(
				batchPlannerPolicy.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName()));

		batchPlannerPlanService.deleteBatchPlannerPlan(
			batchPlannerPolicy.getBatchPlannerPlanId());

		batchPlannerPolicies =
			batchPlannerPolicyService.getBatchPlannerPolicies(
				batchPlannerPolicy.getBatchPlannerPlanId());

		Assert.assertTrue(batchPlannerPolicies.isEmpty());
	}

}