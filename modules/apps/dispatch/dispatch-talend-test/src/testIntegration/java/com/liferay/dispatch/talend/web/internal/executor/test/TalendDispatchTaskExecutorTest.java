/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.talend.web.internal.executor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Calendar;
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
public class TalendDispatchTaskExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testExecute() throws Exception {
		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.addDispatchTrigger(
				null, TestPropsValues.getUserId(), "talend",
				new UnicodeProperties(), "TalendDispatchTrigger", false);

		_dispatchFileRepository.addFileEntry(
			dispatchTrigger.getUserId(), dispatchTrigger.getDispatchTriggerId(),
			_TALEND_CONTEXT_PRINTER_SAMPLE_ZIP, 0, "application/zip",
			TalendDispatchTaskExecutorTest.class.getResourceAsStream(
				"/" + _TALEND_CONTEXT_PRINTER_SAMPLE_ZIP));

		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR) + 1;

		dispatchTrigger = _dispatchTriggerLocalService.updateDispatchTrigger(
			dispatchTrigger.getDispatchTriggerId(), false, "* * * * * *",
			DispatchTaskClusterMode.SINGLE_NODE_PERSISTED, 5, 5, year, 11, 11,
			false, false, 4, 4, year, 0, 0, "UTC");

		_simulateSchedulerEvent(dispatchTrigger.getDispatchTriggerId());

		List<DispatchLog> dispatchLogs =
			_dispatchLogLocalService.getDispatchLogs(
				dispatchTrigger.getDispatchTriggerId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		DispatchLog dispatchLog = dispatchLogs.get(0);

		Assert.assertEquals(
			DispatchTaskStatus.SUCCESSFUL,
			DispatchTaskStatus.valueOf(dispatchLog.getStatus()));
	}

	private void _simulateSchedulerEvent(long dispatchTriggerId)
		throws Exception {

		Message message = new Message();

		message.setPayload(
			String.format("{\"dispatchTriggerId\": %d}", dispatchTriggerId));

		_messageListener.receive(message);
	}

	private static final String _TALEND_CONTEXT_PRINTER_SAMPLE_ZIP =
		"etl-talend-context-printer-sample-1.0.zip";

	@Inject
	private DispatchFileRepository _dispatchFileRepository;

	@Inject
	private DispatchLogLocalService _dispatchLogLocalService;

	@Inject
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Inject(
		filter = "destination.name=" + DispatchConstants.EXECUTOR_DESTINATION_NAME
	)
	private MessageListener _messageListener;

}