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
import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;
import com.liferay.batch.planner.exception.BatchPlannerPlanExternalTypeException;
import com.liferay.batch.planner.exception.BatchPlannerPlanNameException;
import com.liferay.batch.planner.exception.DuplicateBatchPlannerPlanException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class BatchPlannerPlanServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAddBatchPlannerPlan() throws Exception {
		String name = RandomTestUtil.randomString();

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), name, null, false);

		Assert.assertEquals(
			BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
			batchPlannerPlan.getExternalType());
		Assert.assertEquals(name, batchPlannerPlan.getName());

		try {
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, "", "/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				null, false);

			Assert.fail();
		}
		catch (BatchPlannerPlanExternalTypeException
					batchPlannerPlanExternalTypeException) {

			String externalTypesString = StringUtil.merge(
				BatchPlannerPlanConstants.EXTERNAL_TYPES, StringPool.COMMA);

			Assert.assertEquals(
				"Batch planner plan external type must be one of following: " +
					externalTypesString,
				batchPlannerPlanExternalTypeException.getMessage());
		}

		try {
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "", null, false);

			Assert.fail();
		}
		catch (BatchPlannerPlanNameException batchPlannerPlanNameException) {
			Assert.assertEquals(
				"Batch planner plan name is null for company " +
					TestPropsValues.getCompanyId(),
				batchPlannerPlanNameException.getMessage());
		}

		int maxLength = 75;

		try {
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomString(maxLength + 1), null, false);

			Assert.fail();
		}
		catch (BatchPlannerPlanNameException batchPlannerPlanNameException) {
			Assert.assertEquals(
				"Batch planner plan name must not be longer than " + maxLength,
				batchPlannerPlanNameException.getMessage());
		}

		try {
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), name, null, false);

			Assert.fail();
		}
		catch (DuplicateBatchPlannerPlanException
					duplicateBatchPlannerPlanException) {

			Assert.assertEquals(
				StringBundler.concat(
					"Batch planner plan name \"", name,
					"\" already exists for company ",
					TestPropsValues.getCompanyId()),
				duplicateBatchPlannerPlanException.getMessage());
		}

		_batchPlannerPlanService.deleteBatchPlannerPlan(
			batchPlannerPlan.getBatchPlannerPlanId());
	}

	@Inject
	private BatchPlannerPlanService _batchPlannerPlanService;

}