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
import com.liferay.batch.planner.exception.NoSuchPlanException;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.BatchPlannerPolicyService;
import com.liferay.batch.planner.service.test.util.BatchPlannerPolicyTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
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
public class BatchPlannerPolicyServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testBatchPlannerPolicyPermissions() throws Exception {
		List<BatchPlannerPolicy> batchPlannerPolicies =
			BatchPlannerPolicyTestUtil.addBatchPlannerPolicy(
				"policy_1", "policy_2", "policy_3", "policy_4", "policy_5");

		BatchPlannerPolicy batchPlannerPolicy = batchPlannerPolicies.get(0);

		Assert.assertEquals(
			TestPropsValues.getCompanyId(), batchPlannerPolicy.getCompanyId());

		Assert.assertTrue(
			_batchPlannerPolicyService.hasBatchPlannerPolicy(
				batchPlannerPolicy.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName()));

		User user2 = UserTestUtil.addUser(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		UserTestUtil.setUser(user2);

		Class<?> exceptionClass = Exception.class;

		try {
			_batchPlannerPolicyService.hasBatchPlannerPolicy(
				batchPlannerPolicy.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			PrincipalException.MustHavePermission.class, exceptionClass);

		User omniAdminUser = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(omniAdminUser);

		Assert.assertTrue(
			_batchPlannerPolicyService.hasBatchPlannerPolicy(
				batchPlannerPolicy.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName()));

		User user3 = UserTestUtil.addUser(CompanyTestUtil.addCompany());

		UserTestUtil.setUser(user3);

		try {
			_batchPlannerPolicyService.hasBatchPlannerPolicy(
				batchPlannerPolicy.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(IllegalArgumentException.class, exceptionClass);
	}

	@Test
	public void testDeleteBatchPlannerPolicy() throws Exception {
		List<BatchPlannerPolicy> batchPlannerPolicies =
			BatchPlannerPolicyTestUtil.addBatchPlannerPolicy(
				"policy_1", "policy_2", "policy_3", "policy_4", "policy_5");

		Assert.assertEquals("policies count", 5, batchPlannerPolicies.size());

		BatchPlannerPolicy batchPlannerPolicy = batchPlannerPolicies.get(0);

		_batchPlannerPolicyService.deleteBatchPlannerPolicy(
			batchPlannerPolicy.getBatchPlannerPlanId(),
			batchPlannerPolicy.getName());

		Assert.assertFalse(
			_batchPlannerPolicyService.hasBatchPlannerPolicy(
				batchPlannerPolicy.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName()));

		batchPlannerPolicies =
			_batchPlannerPolicyService.getBatchPlannerPolicies(
				batchPlannerPolicy.getBatchPlannerPlanId());

		Assert.assertEquals("policies count", 4, batchPlannerPolicies.size());

		batchPlannerPolicy = batchPlannerPolicies.get(0);

		Assert.assertTrue(
			_batchPlannerPolicyService.hasBatchPlannerPolicy(
				batchPlannerPolicy.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName()));

		_batchPlannerPlanService.deleteBatchPlannerPlan(
			batchPlannerPolicy.getBatchPlannerPlanId());

		Class<?> exceptionClass = Exception.class;

		try {
			_batchPlannerPolicyService.getBatchPlannerPolicies(
				batchPlannerPolicy.getBatchPlannerPlanId());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(NoSuchPlanException.class, exceptionClass);
	}

	@Inject
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Inject
	private BatchPlannerPolicyService _batchPlannerPolicyService;

	@Inject
	private CompanyLocalService _companyLocalService;

}