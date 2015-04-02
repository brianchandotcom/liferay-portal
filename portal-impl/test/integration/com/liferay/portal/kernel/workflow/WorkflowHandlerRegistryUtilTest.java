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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.workflow.bundle.workflowhandlerregistryutil.TestWorkflowHandler;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class WorkflowHandlerRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.workflowhandlerregistryutil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testGetWorkflowHandler() {
		String className = TestWorkflowHandler.class.getName();

		WorkflowHandler<Object> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		Assert.assertEquals(className, workflowHandler.getClassName());
	}

	@Test
	public void testGetWorkflowHandlers() {
		boolean found = false;

		String className = TestWorkflowHandler.class.getName();

		List<WorkflowHandler<?>> workflowHandlers =
			WorkflowHandlerRegistryUtil.getWorkflowHandlers();

		for (WorkflowHandler workflowHandler : workflowHandlers) {
			if (className.equals(workflowHandler.getClassName())) {
				found = true;
			}
		}

		Assert.assertTrue(found);
	}

	@Test
	public void testStartWorkflowInstance() {
		_atomicState.reset();

		try {
			ServiceContext serviceContext = new ServiceContext();

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				1, 1, 1, TestWorkflowHandler.class.getName(), 1, null,
				serviceContext, new HashMap<String, Serializable>());
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testStartWorkflowInstance2() {
		_atomicState.reset();

		try {
			ServiceContext serviceContext = new ServiceContext();

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				1, 1, 1, TestWorkflowHandler.class.getName(), 1, null,
				serviceContext);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}