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
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerLogService;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.test.util.BatchPlannerLogTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
public class BatchPlannerLogServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testBatchPlannerLogExceptions() throws Exception {
		/*
		- dodaj service implementacije
		- dodaj ovaj test
		- vidi je li trebas sa required anotirati portlet model hints
		- vidi je li trebas uvesti exceptione za Type polja (i validatore)
		 */
	}

	@Test
	public void testBatchPlannerLogPermissions() throws Exception {
		BatchPlannerLog batchPlannerLog1 =
			BatchPlannerLogTestUtil.addBatchPlannerLog(
				RandomTestUtil.randomBoolean(), RandomTestUtil.randomInt());

		_assertBatchPlannerLogFields(batchPlannerLog1);

		Assert.assertEquals(
			TestPropsValues.getCompanyId(), batchPlannerLog1.getCompanyId());

		User user2 = UserTestUtil.addUser(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		UserTestUtil.setUser(user2);

		Class<?> exceptionClass = Exception.class;

		try {
			_batchPlannerLogService.getBatchPlannerLogs(
				batchPlannerLog1.getBatchPlannerPlanId());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			PrincipalException.MustHavePermission.class, exceptionClass);

		BatchPlannerLog batchPlannerLog2 =
			BatchPlannerLogTestUtil.addBatchPlannerLog(
				RandomTestUtil.randomBoolean(), RandomTestUtil.randomInt());

		_assertBatchPlannerLogFields(batchPlannerLog2);

		Assert.assertEquals(user2.getUserId(), batchPlannerLog2.getUserId());

		User omniAdminUser = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(omniAdminUser);

		List<BatchPlannerLog> batchPlannerLogs =
			_batchPlannerLogService.getBatchPlannerLogs(
				batchPlannerLog1.getBatchPlannerPlanId());

		Assert.assertFalse(
			"Omni Admin sees user1 logs", batchPlannerLogs.isEmpty());

		batchPlannerLogs = _batchPlannerLogService.getBatchPlannerLogs(
			batchPlannerLog2.getBatchPlannerPlanId());

		Assert.assertFalse(
			"Omni Admin sees user2 logs", batchPlannerLogs.isEmpty());
	}

	@Test
	public void testDeleteBatchPlannerLog() throws Exception {
		BatchPlannerLog batchPlannerLog =
			BatchPlannerLogTestUtil.addBatchPlannerLog(
				RandomTestUtil.randomBoolean(), RandomTestUtil.randomInt());

		_assertBatchPlannerLogFields(batchPlannerLog);

		_batchPlannerLogService.deleteBatchPlannerLog(
			batchPlannerLog.getBatchPlannerLogId());

		List<BatchPlannerLog> batchPlannerLogs =
			_batchPlannerLogService.getBatchPlannerLogs(
				batchPlannerLog.getBatchPlannerPlanId());

		Assert.assertTrue(batchPlannerLogs.isEmpty());

		_batchPlannerPlanService.deleteBatchPlannerPlan(
			batchPlannerLog.getBatchPlannerPlanId());

		Class<?> exceptionClass = Exception.class;

		try {
			_batchPlannerLogService.getBatchPlannerLogs(
				batchPlannerLog.getBatchPlannerPlanId());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(NoSuchPlanException.class, exceptionClass);
	}

	private void _assertBatchPlannerLogFields(BatchPlannerLog batchPlannerLog) {
		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.getBatchPlannerPlan(
				batchPlannerLog.getBatchPlannerPlanId());

		if (batchPlannerPlan.isExport()) {
			Assert.assertNotNull(batchPlannerLog.getBatchEngineExportTaskERC());
			Assert.assertEquals(
				StringPool.BLANK,
				batchPlannerLog.getBatchEngineImportTaskERC());
		}
		else {
			Assert.assertEquals(
				StringPool.BLANK,
				batchPlannerLog.getBatchEngineExportTaskERC());
			Assert.assertNotNull(batchPlannerLog.getBatchEngineImportTaskERC());
		}

		Assert.assertTrue(batchPlannerLog.getStatus() > 0);
	}

	@Inject
	private BatchPlannerLogService _batchPlannerLogService;

	@Inject
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Inject
	private CompanyLocalService _companyLocalService;

}