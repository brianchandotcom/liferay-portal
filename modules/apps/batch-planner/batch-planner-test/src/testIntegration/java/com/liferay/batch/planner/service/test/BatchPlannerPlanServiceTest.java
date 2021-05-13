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
import com.liferay.batch.planner.exception.BatchPlannerPlanNameException;
import com.liferay.batch.planner.exception.DuplicateBatchPlannerPlanException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
public class BatchPlannerPlanServiceTest extends BatchPlannerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddBatchPlannerPlanExceptions() throws Exception {
		User omniAdminUser = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(omniAdminUser);

		User user1 = UserTestUtil.addUser(CompanyTestUtil.addCompany());

		UserTestUtil.setUser(user1);

		Class<?> exceptionClass = Exception.class;

		try {
			addBatchPlannerPlan(user1, -1);
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Add batch planner plan with no name",
			BatchPlannerPlanNameException.class, exceptionClass);

		try {
			exceptionClass = Exception.class;

			addBatchPlannerPlan(user1, RandomTestUtil.randomString(80));
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Add batch planner plan with too long name",
			BatchPlannerPlanNameException.class, exceptionClass);

		BatchPlannerPlan batchPlannerPlan1 = addBatchPlannerPlan(user1, 300);

		try {
			addBatchPlannerPlan(user1, batchPlannerPlan1.getName());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Add batch planner plan with existing name",
			DuplicateBatchPlannerPlanException.class, exceptionClass);

		UserTestUtil.setUser(omniAdminUser);

		User user2 = UserTestUtil.addUser(CompanyTestUtil.addCompany());

		UserTestUtil.setUser(user2);

		BatchPlannerPlan batchPlannerPlan2 = addBatchPlannerPlan(
			user2, batchPlannerPlan1.getName());

		Assert.assertEquals(
			batchPlannerPlan1.getName(), batchPlannerPlan2.getName());
	}

}