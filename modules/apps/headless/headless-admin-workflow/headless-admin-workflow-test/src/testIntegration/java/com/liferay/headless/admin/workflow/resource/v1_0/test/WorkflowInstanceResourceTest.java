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

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.ObjectReviewedTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowInstanceTestUtil;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.util.List;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowInstanceResourceTest
	extends BaseWorkflowInstanceResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_workflowDefinition =
			WorkflowDefinitionTestUtil.addWorkflowDefinition();
	}

	@Override
	@Test
	public void testDeleteWorkflowInstance() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.WARN)) {

			super.testDeleteWorkflowInstance();

			_assertNoSuchInstanceLoggingEvents(logCapture, 2);
		}
	}

	@Override
	@Test
	public void testGetWorkflowInstance() throws Exception {
		WorkflowInstance postWorkflowInstance =
			testGetWorkflowInstance_addWorkflowInstance();

		WorkflowInstance getWorkflowInstance =
			workflowInstanceResource.getWorkflowInstance(
				postWorkflowInstance.getId());

		assertEquals(postWorkflowInstance, getWorkflowInstance);
		assertValid(getWorkflowInstance);
		Assert.assertThat(
			getWorkflowInstance.getCurrentNodeNames(),
			CoreMatchers.is(new String[] {"review"}));
	}

	@Override
	@Test
	public void testGraphQLDeleteWorkflowInstance() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.WARN)) {

			super.testGraphQLDeleteWorkflowInstance();

			_assertNoSuchInstanceLoggingEvents(logCapture, 1);
		}
	}

	@Override
	@Test
	public void testGraphQLGetWorkflowInstanceNotFound() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.WARN)) {

			super.testGraphQLGetWorkflowInstanceNotFound();

			_assertNoSuchInstanceLoggingEvents(logCapture, 1);
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"completed", "objectReviewed", "workflowDefinitionName",
			"workflowDefinitionVersion"
		};
	}

	@Override
	protected WorkflowInstance randomWorkflowInstance() throws Exception {
		WorkflowInstance workflowInstance = super.randomWorkflowInstance();

		workflowInstance.setCompleted(false);

		workflowInstance.setObjectReviewed(
			ObjectReviewedTestUtil.addObjectReviewed());

		workflowInstance.setWorkflowDefinitionName(
			_workflowDefinition.getName());
		workflowInstance.setWorkflowDefinitionVersion(
			_workflowDefinition.getVersion());

		return workflowInstance;
	}

	@Override
	protected WorkflowInstance testDeleteWorkflowInstance_addWorkflowInstance()
		throws Exception {

		return testGetWorkflowInstance_addWorkflowInstance();
	}

	@Override
	protected WorkflowInstance testGetWorkflowInstance_addWorkflowInstance()
		throws Exception {

		return testGetWorkflowInstancesPage_addWorkflowInstance(
			randomWorkflowInstance());
	}

	@Override
	protected WorkflowInstance testGetWorkflowInstancesPage_addWorkflowInstance(
			WorkflowInstance workflowInstance)
		throws Exception {

		return WorkflowInstanceTestUtil.addWorkflowInstance(
			testGroup.getGroupId(), workflowInstance.getObjectReviewed(),
			_workflowDefinition);
	}

	@Override
	protected WorkflowInstance testGraphQLWorkflowInstance_addWorkflowInstance()
		throws Exception {

		return testGetWorkflowInstance_addWorkflowInstance();
	}

	@Override
	protected WorkflowInstance
			testPostWorkflowInstanceChangeTransition_addWorkflowInstance(
				WorkflowInstance workflowInstance)
		throws Exception {

		return testGetWorkflowInstancesPage_addWorkflowInstance(
			workflowInstance);
	}

	@Override
	protected WorkflowInstance
			testPostWorkflowInstanceSubmit_addWorkflowInstance(
				WorkflowInstance workflowInstance)
		throws Exception {

		return testGetWorkflowInstancesPage_addWorkflowInstance(
			workflowInstance);
	}

	private void _assertNoSuchInstanceLoggingEvents(
		LogCapture logCapture, int totallogEntries) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(
			logEntries.toString(), totallogEntries, logEntries.size());

		for (LogEntry logEntry : logEntries) {
			Throwable throwable = logEntry.getThrowable();

			Assert.assertNotNull(throwable);

			Assert.assertSame(WorkflowException.class, throwable.getClass());

			throwable = throwable.getCause();

			Class<? extends Throwable> throwableClass = throwable.getClass();

			Assert.assertEquals(
				"NoSuchInstanceException", throwableClass.getSimpleName());

			String message = throwable.toString();

			Assert.assertTrue(
				message,
				message.contains(
					"No KaleoInstance exists with the primary key"));
		}
	}

	private WorkflowDefinition _workflowDefinition;

}