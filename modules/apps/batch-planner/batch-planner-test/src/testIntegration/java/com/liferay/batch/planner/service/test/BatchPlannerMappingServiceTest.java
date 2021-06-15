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
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.service.BatchPlannerMappingService;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.test.util.BatchPlannerMappingTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
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
@RunWith(Arquillian.class)
public class BatchPlannerMappingServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testBatchPlannerMappingExceptions() throws Exception {
		/*
		- dodaj service implementacije
		- dodaj ovaj test
		- vidi je li trebas sa required anotirati portlet model hints
		- vidi je li trebas uvesti exceptione za Type polja (i validatore)
		 */
	}

	@Test
	public void testBatchPlannerMappingPermissions() throws Exception {
		List<BatchPlannerMapping> batchPlannerMappings =
			BatchPlannerMappingTestUtil.addBatchPlannerMapping(
				"external_field_1", "internal_field_1", "external_field_2",
				"internal_field_2", "external_field_3", "internal_field_3");

		BatchPlannerMapping batchPlannerMapping = batchPlannerMappings.get(0);

		Assert.assertEquals(
			TestPropsValues.getCompanyId(), batchPlannerMapping.getCompanyId());

		long batchPlannerPlanId1 = batchPlannerMapping.getBatchPlannerPlanId();

		int batchPlannerMappingsSize1 = batchPlannerMappings.size();

		List<BatchPlannerMapping> batchPlannerPlanBatchPlannerMappings =
			_batchPlannerMappingService.getBatchPlannerMappings(
				batchPlannerPlanId1);

		Assert.assertEquals(
			batchPlannerPlanBatchPlannerMappings.toString(),
			batchPlannerMappingsSize1,
			batchPlannerPlanBatchPlannerMappings.size());

		User user2 = UserTestUtil.addUser(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		UserTestUtil.setUser(user2);

		Class<?> exceptionClass = Exception.class;

		try {
			_batchPlannerMappingService.getBatchPlannerMappings(
				batchPlannerMapping.getBatchPlannerPlanId());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			PrincipalException.MustHavePermission.class, exceptionClass);

		batchPlannerMappings =
			BatchPlannerMappingTestUtil.addBatchPlannerMapping(
				"external_field_1", "internal_field_1", "external_field_2",
				"internal_field_2", "external_field_3", "internal_field_3");

		batchPlannerMapping = batchPlannerMappings.get(0);

		Assert.assertEquals(user2.getUserId(), batchPlannerMapping.getUserId());

		User omniAdminUser = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(omniAdminUser);

		batchPlannerPlanBatchPlannerMappings =
			_batchPlannerMappingService.getBatchPlannerMappings(
				batchPlannerPlanId1);

		Assert.assertEquals(
			"Omni Admin sees user1 mappings", batchPlannerMappingsSize1,
			batchPlannerPlanBatchPlannerMappings.size());

		batchPlannerPlanBatchPlannerMappings =
			_batchPlannerMappingService.getBatchPlannerMappings(
				batchPlannerMapping.getBatchPlannerPlanId());

		Assert.assertEquals(
			"Omni Admin sees user2 mappings", batchPlannerMappings.size(),
			batchPlannerPlanBatchPlannerMappings.size());
	}

	@Test
	public void testDeleteBatchPlannerPolicy() throws Exception {
		List<BatchPlannerMapping> batchPlannerMappings =
			BatchPlannerMappingTestUtil.addBatchPlannerMapping(
				"external_field_1", "internal_field_1", "external_field_2",
				"internal_field_2", "external_field_3", "internal_field_3");

		Assert.assertEquals("mappings count", 3, batchPlannerMappings.size());

		BatchPlannerMapping batchPlannerMapping = batchPlannerMappings.get(0);

		_batchPlannerMappingService.deleteBatchPlannerMapping(
			batchPlannerMapping.getBatchPlannerPlanId(),
			batchPlannerMapping.getExternalFieldName(),
			batchPlannerMapping.getInternalFieldName());

		batchPlannerMappings =
			_batchPlannerMappingService.getBatchPlannerMappings(
				batchPlannerMapping.getBatchPlannerPlanId());

		Assert.assertEquals("policies count", 2, batchPlannerMappings.size());

		_batchPlannerPlanService.deleteBatchPlannerPlan(
			batchPlannerMapping.getBatchPlannerPlanId());

		Class<?> exceptionClass = Exception.class;

		try {
			_batchPlannerMappingService.getBatchPlannerMappings(
				batchPlannerMapping.getBatchPlannerPlanId());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(NoSuchPlanException.class, exceptionClass);
	}

	@Inject
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Inject
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Inject
	private CompanyLocalService _companyLocalService;

}