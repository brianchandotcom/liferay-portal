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
import com.liferay.batch.planner.constants.BatchPlannerConstants;
import com.liferay.batch.planner.exception.BatchPlannerPlanNameException;
import com.liferay.batch.planner.exception.DuplicateBatchPlannerPlanException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.test.util.BatchPlannerPlanTestUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
@RunWith(Arquillian.class)
public class BatchPlannerPlanServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddBatchPlannerPlan() throws Exception {
		Class<?> exceptionClass = Exception.class;

		try {
			BatchPlannerPlanTestUtil.addBatchPlannerPlan(null);
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Add batch planner plan with no name",
			BatchPlannerPlanNameException.class, exceptionClass);

		try {
			exceptionClass = Exception.class;

			BatchPlannerPlanTestUtil.addBatchPlannerPlan(
				RandomTestUtil.randomString(80));
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Add batch planner plan with too long name",
			BatchPlannerPlanNameException.class, exceptionClass);

		BatchPlannerPlan batchPlannerPlan1 =
			BatchPlannerPlanTestUtil.addBatchPlannerPlan(300);

		Assert.assertEquals(
			TestPropsValues.getCompanyId(), batchPlannerPlan1.getCompanyId());

		try {
			BatchPlannerPlanTestUtil.addBatchPlannerPlan(
				batchPlannerPlan1.getName());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Add batch planner plan with existing name",
			DuplicateBatchPlannerPlanException.class, exceptionClass);

		User omniAdminUser = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(omniAdminUser);

		Company company1 = CompanyTestUtil.addCompany();

		_addResourcePermission(company1.getCompanyId());

		User user2 = UserTestUtil.addCompanyAdminUser(company1);

		UserTestUtil.setUser(user2);

		BatchPlannerPlan batchPlannerPlan2 =
			BatchPlannerPlanTestUtil.addBatchPlannerPlan(
				batchPlannerPlan1.getName());

		Assert.assertEquals(
			user2.getCompanyId(), batchPlannerPlan2.getCompanyId());

		Assert.assertEquals(
			batchPlannerPlan1.getName(), batchPlannerPlan2.getName());
	}

	private ResourcePermission _addResourcePermission(long companyId) {
		long resourcePermissionId = CounterLocalServiceUtil.increment(
			ResourcePermission.class.getName());

		ResourcePermission resourcePermission =
			ResourcePermissionLocalServiceUtil.createResourcePermission(
				resourcePermissionId);

		resourcePermission.setCompanyId(companyId);
		resourcePermission.setName(BatchPlannerConstants.RESOURCE_NAME);
		resourcePermission.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermission.setPrimKey(BatchPlannerConstants.RESOURCE_NAME);
		resourcePermission.setRoleId(RandomTestUtil.randomInt());
		resourcePermission.setActionIds(RandomTestUtil.randomInt());
		resourcePermission.setViewActionId(true);

		return ResourcePermissionLocalServiceUtil.addResourcePermission(
			resourcePermission);
	}

}