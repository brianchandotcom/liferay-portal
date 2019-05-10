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

package com.liferay.portal.workflow.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
@RunWith(Arquillian.class)
public class WorkflowHandlerRegistryUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowHandlerRegistryUtilTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Before
	public void setUp() {
		_workflowHandler = (WorkflowHandler)ProxyUtil.newProxyInstance(
			WorkflowHandler.class.getClassLoader(),
			new Class<?>[] {WorkflowHandler.class},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "getClassName")) {
					return _CLASS_NAME;
				}

				if (Objects.equals(method.getName(), "updateStatus")) {
					_calledWorkflowHandler = true;
				}

				if (Objects.equals(method.getName(), "isScopeable")) {
					return false;
				}

				return null;
			});

		_serviceRegistration = _bundleContext.registerService(
			WorkflowHandler.class, _workflowHandler,
			MapUtil.singletonDictionary("service.ranking", Integer.MAX_VALUE));
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetWorkflowHandler() {
		Assert.assertSame(
			_workflowHandler,
			WorkflowHandlerRegistryUtil.getWorkflowHandler(_CLASS_NAME));
	}

	@Test
	public void testGetWorkflowHandlers() {
		List<WorkflowHandler<?>> workflowHandlers =
			WorkflowHandlerRegistryUtil.getWorkflowHandlers();

		Assert.assertTrue(
			_CLASS_NAME + " not found in " + workflowHandlers,
			workflowHandlers.removeIf(
				workflowHandler -> _workflowHandler == workflowHandler));
	}

	@Test
	public void testStartWorkflowInstance() throws PortalException {
		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			1, 1, 1, _CLASS_NAME, 1, null, new ServiceContext(),
			new HashMap<>());

		Assert.assertTrue(_calledWorkflowHandler);

		_calledWorkflowHandler = false;

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			1, 1, 1, _CLASS_NAME, 1, null, new ServiceContext());

		Assert.assertTrue(_calledWorkflowHandler);
	}

	private static final String _CLASS_NAME = "TestWorkflowHandler";

	private static BundleContext _bundleContext;
	private static boolean _calledWorkflowHandler;
	private static WorkflowHandler<?> _workflowHandler;

	private ServiceRegistration<WorkflowHandler> _serviceRegistration;

}