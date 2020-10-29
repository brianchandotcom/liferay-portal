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

package com.liferay.dispatch.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matija Petanjek
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class DispatchMessageListenerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() {
		TestDispatchTaskExecutor.executionCounter.getAndSet(0);
	}

	@Test
	public void testDoReceiveOverlapAllowed() throws Exception {
		int executeCount = RandomTestUtil.randomInt(4, 10);

		DispatchTrigger dispatchTrigger = _executeDispatchTriggerTask(
			executeCount, 1000, true);

		Assert.assertEquals(
			executeCount, TestDispatchTaskExecutor.executionCounter.get());

		List<DispatchLog> dispatchLogs = _filter(
			_dispatchLogLocalService.getDispatchLogs(
				dispatchTrigger.getDispatchTriggerId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			DispatchTaskStatus.SUCCESSFUL);

		Assert.assertEquals(
			dispatchLogs.toString(), executeCount, dispatchLogs.size());
	}

	@Test
	public void testDoReceiveOverlapNotAllowed() throws Exception {
		DispatchTrigger dispatchTrigger = _executeDispatchTriggerTask(
			4, 1000, false);

		Assert.assertEquals(2, TestDispatchTaskExecutor.executionCounter.get());

		List<DispatchLog> dispatchLogs =
			_dispatchLogLocalService.getDispatchLogs(
				dispatchTrigger.getDispatchTriggerId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(dispatchLogs.toString(), 4, dispatchLogs.size());

		List<DispatchLog> statusCancelledDispatchLogs = _filter(
			dispatchLogs, DispatchTaskStatus.CANCELED);

		Assert.assertEquals(
			statusCancelledDispatchLogs.toString(), 2,
			statusCancelledDispatchLogs.size());

		List<DispatchLog> statusSuccessfulDispatchLogs = _filter(
			dispatchLogs, DispatchTaskStatus.SUCCESSFUL);

		Assert.assertEquals(
			statusSuccessfulDispatchLogs.toString(), 2,
			statusSuccessfulDispatchLogs.size());
	}

	private void _executeAndWaitFor(
			long dispatchTaskDuration, long dispatchTriggerId, int executeCount,
			long intervalMillis)
		throws Exception {

		Message message = _getMessage(dispatchTriggerId);

		for (int i = 0; i < (executeCount - 1); i++) {
			_destination.send(message);

			Thread.sleep(intervalMillis);
		}

		_destination.send(message);

		Thread.sleep(dispatchTaskDuration);
	}

	private DispatchTrigger _executeDispatchTriggerTask(
			int executeCount, long intervalMillis, boolean overlapAllowed)
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.addUser(company);

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.addDispatchTrigger(
				user.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomBoolean(),
				TestDispatchTaskExecutor.DISPATCH_TASK_EXECUTOR_TYPE_TEST,
				null);

		Date now = new Date();

		Calendar calendar = CalendarFactoryUtil.getCalendar(now.getTime());

		dispatchTrigger = _dispatchTriggerLocalService.updateDispatchTrigger(
			dispatchTrigger.getDispatchTriggerId(), false, _CRON_EXPRESSION, 0,
			0, 0, 0, 0, true, overlapAllowed, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DATE), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));

		_executeAndWaitFor(
			TestDispatchTaskExecutor.SLEEP_MILLIS + 1000,
			dispatchTrigger.getDispatchTriggerId(), executeCount,
			intervalMillis);

		return dispatchTrigger;
	}

	private List<DispatchLog> _filter(
		List<DispatchLog> dispatchLogs, DispatchTaskStatus dispatchTaskStatus) {

		List<DispatchLog> filteredDispatchLogs = new ArrayList<>();

		for (DispatchLog dispatchLog : dispatchLogs) {
			if (dispatchLog.getStatus() == dispatchTaskStatus.getStatus()) {
				filteredDispatchLogs.add(dispatchLog);
			}
		}

		return filteredDispatchLogs;
	}

	private Message _getMessage(long dispatchTriggerId) {
		Message message = new Message();

		message.setPayload(
			String.valueOf(
				JSONUtil.put("dispatchTriggerId", dispatchTriggerId)));

		return message;
	}

	private static final String _CRON_EXPRESSION = "0/1 * * * * ?";

	@Inject(
		filter = "destination.name=" + DispatchConstants.EXECUTOR_DESTINATION_NAME
	)
	private Destination _destination;

	@Inject
	private DispatchLogLocalService _dispatchLogLocalService;

	@Inject
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

}