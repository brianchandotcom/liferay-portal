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
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;

import org.apache.log4j.Level;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowInstanceResourceTest
	extends BaseWorkflowInstanceResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseWorkflowInstanceResourceTestCase.setUpClass();

		_workflowDefinition =
			WorkflowDefinitionTestUtil.addWorkflowDefinition();
	}

	@Override
	@Test
	public void testDeleteWorkflowInstance() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					ProxyMessageListener.class.getName(), Level.OFF)) {

			super.testDeleteWorkflowInstance();
		}
	}

	@Override
	@Test
	public void testGraphQLDeleteWorkflowInstance() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					ProxyMessageListener.class.getName(), Level.OFF)) {

			super.testGraphQLDeleteWorkflowInstance();
		}
	}

	@Override
	@Test
	public void testGraphQLGetWorkflowInstanceNotFound() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					ProxyMessageListener.class.getName(), Level.OFF)) {

			super.testGraphQLGetWorkflowInstanceNotFound();
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

	private static WorkflowDefinition _workflowDefinition;

}