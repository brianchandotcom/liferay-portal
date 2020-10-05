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

package com.liferay.dispatch.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dispatch.exception.DispatchTriggerNameException;
import com.liferay.dispatch.exception.DispatchTriggerSchedulerException;
import com.liferay.dispatch.exception.DuplicateDispatchTriggerException;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class DispatchTriggerLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddDispatchTriggerExceptions() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.addUser(company);

		_addDispatchTrigger(
			DispatchTriggerValues.randomDispatchTriggerValues(user, 1));

		Class<?> exceptionClass = Exception.class;

		try {
			_addDispatchTrigger(
				DispatchTriggerValues.randomDispatchTriggerValues(user, 1));
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Add dispatch trigger with existing name",
			DuplicateDispatchTriggerException.class, exceptionClass);

		try {
			_addDispatchTrigger(
				DispatchTriggerValues.randomDispatchTriggerValues(user, -1));
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Add dispatch trigger with no name",
			DispatchTriggerNameException.class, exceptionClass);
	}

	@Test
	public void testGetUserDispatchTriggers() throws Exception {
		int userCount = RandomTestUtil.randomInt(4, 10);

		Map<User, Integer> userDispatchTriggersCounts = new HashMap<>();

		while (userCount-- > 0) {
			Company company = CompanyTestUtil.addCompany();

			User user = UserTestUtil.addUser(company);

			int dispatchTriggersCount = RandomTestUtil.randomInt(10, 20);

			userDispatchTriggersCounts.put(user, dispatchTriggersCount);

			while (dispatchTriggersCount-- > 0) {
				_addDispatchTrigger(
					DispatchTriggerValues.randomDispatchTriggerValues(
						user, dispatchTriggersCount));
			}
		}

		for (Map.Entry<User, Integer> userDispatchTriggersCountEntry :
				userDispatchTriggersCounts.entrySet()) {

			User user = userDispatchTriggersCountEntry.getKey();
			Integer count = userDispatchTriggersCountEntry.getValue();

			Assert.assertEquals(
				"User dispatch triggers count value", count.intValue(),
				_dispatchTriggerLocalService.getUserDispatchTriggersCount(
					user.getCompanyId(), user.getUserId()));

			List<DispatchTrigger> userDispatchTriggers =
				_dispatchTriggerLocalService.getUserDispatchTriggers(
					user.getCompanyId(), user.getUserId(), -1, -1);

			for (DispatchTrigger dispatchTrigger : userDispatchTriggers) {
				Assert.assertEquals(
					"Dispatch trigger user ID", user.getUserId(),
					dispatchTrigger.getUserId());
			}
		}
	}

	@Test
	public void testUpdateDispatchTrigger() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.addUser(company);

		DispatchTriggerValues dispatchTriggerValues =
			DispatchTriggerValues.randomDispatchTriggerValues(user, 1);

		DispatchTrigger dispatchTrigger = _addDispatchTrigger(
			dispatchTriggerValues);

		_basicAssertEquals(dispatchTrigger, dispatchTriggerValues);

		dispatchTriggerValues =
			DispatchTriggerValues.randomDispatchTriggerValues(
				dispatchTriggerValues, 1);

		try {
			dispatchTrigger =
				_dispatchTriggerLocalService.updateDispatchTrigger(
					dispatchTrigger.getDispatchTriggerId(),
					dispatchTriggerValues.isActive(),
					dispatchTriggerValues.getCronExpression(), 5, 5, 2024, 11,
					11, false, 4, 4, 2024, 12, 0);

			_basicAssertEquals(dispatchTrigger, dispatchTriggerValues);

			_advancedAssertEquals(dispatchTrigger, dispatchTriggerValues);
		}
		catch (Exception exception) {
			if (!(exception instanceof DispatchTriggerSchedulerException)) {
				throw exception;
			}

			TriggerState jobState = _schedulerEngineHelper.getJobState(
				String.format(
					"DISPATCH_JOB_%07d",
					dispatchTrigger.getDispatchTriggerId()),
				String.format(
					"DISPATCH_GROUP_%07d",
					dispatchTrigger.getDispatchTriggerId()),
				StorageType.PERSISTED);

			Assert.assertNull(jobState);
		}
	}

	@Test
	public void testUpdateDispatchTriggerExceptions() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.addUser(company);

		DispatchTrigger dispatchTrigger1 = _addDispatchTrigger(
			DispatchTriggerValues.randomDispatchTriggerValues(user, 1));
		DispatchTrigger dispatchTrigger2 = _addDispatchTrigger(
			DispatchTriggerValues.randomDispatchTriggerValues(user, 2));

		Class<?> exceptionClass = Exception.class;

		try {
			_dispatchTriggerLocalService.updateDispatchTrigger(
				dispatchTrigger1.getDispatchTriggerId(),
				dispatchTrigger2.getName(),
				dispatchTrigger1.getTaskSettingsUnicodeProperties());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Update dispatch trigger with existing name",
			DuplicateDispatchTriggerException.class, exceptionClass);

		try {
			_dispatchTriggerLocalService.updateDispatchTrigger(
				dispatchTrigger1.getDispatchTriggerId(), null,
				dispatchTrigger1.getTaskSettingsUnicodeProperties());
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			"Update dispatch trigger with no name",
			DispatchTriggerNameException.class, exceptionClass);
	}

	private DispatchTrigger _addDispatchTrigger(
			DispatchTriggerValues dispatchTriggerValues)
		throws Exception {

		return _dispatchTriggerLocalService.addDispatchTrigger(
			dispatchTriggerValues.getUserId(), dispatchTriggerValues.getName(),
			dispatchTriggerValues.isSystem(),
			dispatchTriggerValues.getTaskSettingsUnicodeProperties(),
			dispatchTriggerValues.getTaskType());
	}

	private void _advancedAssertEquals(
			DispatchTrigger actual, DispatchTriggerValues expected)
		throws Exception {

		Assert.assertEquals(
			"Dispatch trigger active value", expected.isActive(),
			actual.isActive());

		Assert.assertEquals(
			"Dispatch trigger cron expression value",
			expected.getCronExpression(), actual.getCronExpression());

		Assert.assertNotNull(
			"Dispatch trigger start date value", actual.getStartDate());
	}

	private void _basicAssertEquals(
		DispatchTrigger actual, DispatchTriggerValues expected) {

		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getUserId(), actual.getUserId());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.isSystem(), actual.isSystem());
		Assert.assertEquals(expected.getTaskType(), actual.getTaskType());

		UnicodeProperties actualTaskSettingsUnicodeProperties =
			actual.getTaskSettingsUnicodeProperties();

		if (expected.getTaskSettingsUnicodeProperties() == null) {
			Assert.assertNull(
				"Dispatch trigger job properties",
				actualTaskSettingsUnicodeProperties);

			return;
		}

		Assert.assertNotNull(
			"Dispatch trigger object", actualTaskSettingsUnicodeProperties);

		Assert.assertEquals(
			"Dispatch trigger job properties size",
			expected.getTaskSettingsUnicodePropertiesSize(),
			actualTaskSettingsUnicodeProperties.size());

		actualTaskSettingsUnicodeProperties.forEach(
			(key, value) -> Assert.assertEquals(
				String.format("Dispatch trigger job property for key %s", key),
				expected.getTaskSettingsProperty(key), value));
	}

	@Inject
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Inject
	private SchedulerEngineHelper _schedulerEngineHelper;

}