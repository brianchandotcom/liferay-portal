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

package com.liferay.batch.engine.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ivica Cardic
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class BatchEngineExportTaskExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testExecute() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_BATCH_ENGINE_EXPORT_TASK_EXECUTOR_IMPL,
				LoggerTestUtil.ERROR)) {

			User user = UserTestUtil.addUser();

			BatchEngineExportTask batchEngineExportTask =
				_batchEngineExportTaskLocalService.addBatchEngineExportTask(
					null, user.getCompanyId(), user.getUserId(), null,
					BatchEngineImportTask.class.getName(), "CSV",
					BatchEngineTaskExecuteStatus.INITIAL.name(),
					Arrays.asList("field1", "field2", "field3", "field4"),
					Collections.emptyMap(), null);

			_batchEngineExportTaskExecutor.execute(batchEngineExportTask);

			batchEngineExportTask =
				_batchEngineExportTaskLocalService.getBatchEngineExportTask(
					batchEngineExportTask.getBatchEngineExportTaskId());

			Assert.assertEquals(
				"FAILED", batchEngineExportTask.getExecuteStatus());

			_assertLogErrors(logCapture);
		}
	}

	private void _assertLogErrors(LogCapture logCapture) {
		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.get(0);

		Assert.assertEquals(LoggerTestUtil.ERROR, logEntry.getPriority());

		String message = logEntry.getMessage();

		Assert.assertTrue(
			message.startsWith("Unable to update batch engine export task"));
	}

	private static final String
		_CLASS_NAME_BATCH_ENGINE_EXPORT_TASK_EXECUTOR_IMPL =
			"com.liferay.batch.engine.internal." +
				"BatchEngineExportTaskExecutorImpl";

	@Inject
	private BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;

	@Inject
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

}