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

package com.liferay.portal.workflow.kaleo.runtime.internal.action.executor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Jiaxu Wei
 */
public class ActionExecutorManagerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_actionExecutorManagerImpl = new ActionExecutorManagerImpl();

		_actionExecutorManagerImpl.activate(_bundleContext);
	}

	@After
	public void tearDown() {
		if (_actionExecutorManagerImpl != null) {
			_actionExecutorManagerImpl.deactivate();
		}
	}

	@Test
	public void testMultipleJavaActionExecutors() throws Exception {
		TestJavaActionExecutor testJavaActionExecutor1 =
			new TestJavaActionExecutor() {
			};

		ServiceRegistration<ActionExecutor> serviceRegistration1 =
			_bundleContext.registerService(
				ActionExecutor.class, testJavaActionExecutor1, null);

		TestJavaActionExecutor testJavaActionExecutor2 =
			new TestJavaActionExecutor() {
			};

		ServiceRegistration<ActionExecutor> serviceRegistration2 =
			_bundleContext.registerService(
				ActionExecutor.class, testJavaActionExecutor2, null);

		try {
			_actionExecutorManagerImpl.executeKaleoAction(
				_createKaleoAction(
					ClassUtil.getClassName(testJavaActionExecutor1)),
				null);

			Assert.assertTrue(testJavaActionExecutor1.isExecuted());

			_actionExecutorManagerImpl.executeKaleoAction(
				_createKaleoAction(
					ClassUtil.getClassName(testJavaActionExecutor2)),
				null);

			Assert.assertTrue(testJavaActionExecutor2.isExecuted());
		}
		finally {
			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
		}
	}

	@Test
	public void testNonexistingJavaActionExecutor() {
		try {
			_actionExecutorManagerImpl.executeKaleoAction(
				_createKaleoAction(
					ClassUtil.getClassName(
						new TestJavaActionExecutor() {
						})),
				null);

			Assert.fail();
		}
		catch (PortalException portalException) {
			Assert.assertEquals(
				"No action executor for java", portalException.getMessage());
		}
	}

	private KaleoAction _createKaleoAction(String script) {
		KaleoAction kaleoAction = Mockito.mock(KaleoAction.class);

		Mockito.when(
			kaleoAction.getScript()
		).thenReturn(
			script
		);
		Mockito.when(
			kaleoAction.getScriptLanguage()
		).thenReturn(
			"java"
		);
		Mockito.when(
			kaleoAction.getType()
		).thenReturn(
			"SCRIPT"
		);

		return kaleoAction;
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private ActionExecutorManagerImpl _actionExecutorManagerImpl;

	private abstract static class TestJavaActionExecutor
		implements ActionExecutor {

		@Override
		public void execute(
			KaleoAction kaleoAction, ExecutionContext executionContext) {

			_executed = true;
		}

		@Override
		public String getActionExecutorKey() {
			return "java";
		}

		public boolean isExecuted() {
			return _executed;
		}

		private boolean _executed;

	}

}