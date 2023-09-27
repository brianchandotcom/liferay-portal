/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.planner.batch.engine.broker.BatchEngineBroker;
import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;
import com.liferay.batch.planner.exception.BatchPlannerPlanExternalTypeException;
import com.liferay.batch.planner.exception.BatchPlannerPlanInternalClassNameException;
import com.liferay.batch.planner.exception.BatchPlannerPlanNameException;
import com.liferay.batch.planner.exception.DuplicateBatchPlannerPlanException;
import com.liferay.batch.planner.exception.RequiredBatchPlannerPlanException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerMappingService;
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

import java.util.List;

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
				RandomTestUtil.randomString(), name, 0, null, false);

		Assert.assertEquals(
			BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
			batchPlannerPlan.getExternalType());
		Assert.assertEquals(name, batchPlannerPlan.getName());

		try {
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, "", "/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
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
				"/" + RandomTestUtil.randomString(), null, name, 0, null,
				false);

			Assert.fail();
		}
		catch (BatchPlannerPlanInternalClassNameException
					batchPlannerPlanInternalClassNameException) {

			Assert.assertNotNull(batchPlannerPlanInternalClassNameException);
		}

		try {
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "", 0, null, true);

			Assert.fail();
		}
		catch (BatchPlannerPlanNameException batchPlannerPlanNameException) {
			Assert.assertEquals(
				"Batch planner plan name is null",
				batchPlannerPlanNameException.getMessage());
		}

		int maxLength = 75;

		try {
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomString(maxLength + 1), 0, null, false);

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
				RandomTestUtil.randomString(), name, 0, null, true);

			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), name, 0, null, true);

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

		_testUpdateBatchPlannerPlanStatusFailed(
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				null, false));
		_testUpdateBatchPlannerPlanStatusFailed(
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 0, null, false));
	}

	@Test
	public void testSearchBatchPlannerPlan() throws Exception {
		for (int i = 0; i < 60; i++) {
			boolean export = false;

			if ((i % 2) == 0) {
				export = true;
			}

			_submitBatchPlannerPlan(
				export, _INTERNAL_CLASS_NAME_CHANNEL,
				RandomTestUtil.randomString());
		}

		_submitBatchPlannerPlan(true, _INTERNAL_CLASS_NAME_CHANNEL, "name1");
		_submitBatchPlannerPlan(true, _INTERNAL_CLASS_NAME_CHANNEL, "name2");
		_submitBatchPlannerPlan(true, _INTERNAL_CLASS_NAME_CHANNEL, "name3");
		_submitBatchPlannerPlan(false, _INTERNAL_CLASS_NAME_CHANNEL, "name4");
		_submitBatchPlannerPlan(false, _INTERNAL_CLASS_NAME_CHANNEL, "name5");

		BatchPlannerPlan batchPlannerPlan = _submitBatchPlannerPlan(
			false, _INTERNAL_CLASS_NAME_CHANNEL, "name6");

		_testSearchExportBatchPlannerPlans(batchPlannerPlan.getCompanyId());
		_testSearchImportBatchPlannerPlans(batchPlannerPlan.getCompanyId());
	}

	@Test
	public void testUpdateBatchPlannerPlan() throws Exception {
		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				true, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				null, false);

		try {
			_batchPlannerPlanService.updateBatchPlannerPlan(
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerPlan.getExternalType(),
				batchPlannerPlan.getInternalClassName(),
				RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (RequiredBatchPlannerPlanException
					requiredBatchPlannerPlanException) {

			Assert.assertEquals(
				"Batch planner plan is not a template",
				requiredBatchPlannerPlanException.getMessage());
		}
	}

	private BatchPlannerPlan _submitBatchPlannerPlan(
			boolean export, String internalClassName, String name)
		throws Exception {

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				export, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				"/" + RandomTestUtil.randomString(), internalClassName, name, 0,
				null, false);

		_batchPlannerMappingService.addBatchPlannerMapping(
			batchPlannerPlan.getBatchPlannerPlanId(), "name", "String", "name",
			"String", StringPool.BLANK);

		try {
			_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());
		}
		catch (Exception exception) {
			Assert.assertEquals(
				IllegalArgumentException.class, exception.getClass());
		}

		return batchPlannerPlan;
	}

	private void _testSearchExportBatchPlannerPlans(long companyId)
		throws Exception {

		List<BatchPlannerPlan> batchPlannerPlans =
			_batchPlannerPlanService.getBatchPlannerPlans(
				companyId, true, false, "name3", 0, Integer.MAX_VALUE, null);

		Assert.assertEquals(
			batchPlannerPlans.toString(), 1, batchPlannerPlans.size());

		batchPlannerPlans = _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, true, false, _INTERNAL_CLASS_NAME_CHANNEL, 0,
			Integer.MAX_VALUE, null);

		Assert.assertEquals(
			batchPlannerPlans.toString(), 33, batchPlannerPlans.size());

		batchPlannerPlans = _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, true, false, RandomTestUtil.randomString(), 0,
			Integer.MAX_VALUE, null);

		Assert.assertEquals(
			batchPlannerPlans.toString(), 0, batchPlannerPlans.size());
	}

	private void _testSearchImportBatchPlannerPlans(long companyId)
		throws Exception {

		List<BatchPlannerPlan> batchPlannerPlans =
			_batchPlannerPlanService.getBatchPlannerPlans(
				companyId, false, false, "name5", 0, Integer.MAX_VALUE, null);

		Assert.assertEquals(
			batchPlannerPlans.toString(), 1, batchPlannerPlans.size());

		batchPlannerPlans = _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, false, false, _INTERNAL_CLASS_NAME_CHANNEL, 0,
			Integer.MAX_VALUE, null);

		Assert.assertEquals(
			batchPlannerPlans.toString(), 33, batchPlannerPlans.size());

		for (BatchPlannerPlan batchPlannerPlan : batchPlannerPlans) {
			Assert.assertEquals(
				BatchPlannerPlanConstants.STATUS_FAILED,
				batchPlannerPlan.getStatus());
		}

		batchPlannerPlans = _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, false, false, RandomTestUtil.randomString(), 0,
			Integer.MAX_VALUE, null);

		Assert.assertEquals(
			batchPlannerPlans.toString(), 0, batchPlannerPlans.size());
	}

	private void _testUpdateBatchPlannerPlanStatusFailed(
			BatchPlannerPlan batchPlannerPlan)
		throws Exception {

		try {
			_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				IllegalStateException.class, exception.getClass());
		}

		batchPlannerPlan = _batchPlannerPlanService.getBatchPlannerPlan(
			batchPlannerPlan.getBatchPlannerPlanId());

		Assert.assertEquals(
			BatchPlannerPlanConstants.STATUS_FAILED,
			batchPlannerPlan.getStatus());
	}

	private static final String _INTERNAL_CLASS_NAME_CHANNEL =
		"com.liferay.headless.commerce.admin.channel.dto.v1_0.Channel";

	@Inject
	private BatchEngineBroker _batchEngineBroker;

	@Inject
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Inject
	private BatchPlannerPlanService _batchPlannerPlanService;

}