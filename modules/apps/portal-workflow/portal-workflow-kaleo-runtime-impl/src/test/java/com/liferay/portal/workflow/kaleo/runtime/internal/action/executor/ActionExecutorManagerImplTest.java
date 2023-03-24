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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutorException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() {
		_actionExecutorManagerImpl = new ActionExecutorManagerImpl();

		_actionExecutorManagerImpl.activate(_bundleContext);

		_testJavaActionExecutorOneServiceRegistration =
			_bundleContext.registerService(
				ActionExecutor.class, new TestJavaActionExecutorOne(), null);
		_testJavaActionExecutorTwoServiceRegistration =
			_bundleContext.registerService(
				ActionExecutor.class, new TestJavaActionExecutorTwo(), null);
	}

	@AfterClass
	public static void tearDownClass() {
		if (_actionExecutorManagerImpl != null) {
			_actionExecutorManagerImpl.deactivate();
		}

		if (_testJavaActionExecutorOneServiceRegistration != null) {
			_testJavaActionExecutorOneServiceRegistration.unregister();
		}

		if (_testJavaActionExecutorTwoServiceRegistration != null) {
			_testJavaActionExecutorTwoServiceRegistration.unregister();
		}
	}

	@Test
	public void testMultipleJavaBasedActionExecutors() throws Exception {
		KaleoAction kaleoActionOne = Mockito.mock(KaleoAction.class);

		Mockito.when(
			kaleoActionOne.getScript()
		).thenReturn(
			TestJavaActionExecutorOne.class.getName()
		);
		Mockito.when(
			kaleoActionOne.getScriptLanguage()
		).thenReturn(
			"java"
		);
		Mockito.when(
			kaleoActionOne.getType()
		).thenReturn(
			"SCRIPT"
		);

		KaleoAction kaleoActionTwo = Mockito.mock(KaleoAction.class);

		Mockito.when(
			kaleoActionTwo.getScript()
		).thenReturn(
			TestJavaActionExecutorTwo.class.getName()
		);
		Mockito.when(
			kaleoActionTwo.getScriptLanguage()
		).thenReturn(
			"java"
		);
		Mockito.when(
			kaleoActionTwo.getType()
		).thenReturn(
			"SCRIPT"
		);

		_actionExecutorManagerImpl.executeKaleoAction(kaleoActionOne, null);

		Assert.assertEquals(
			TestJavaActionExecutorOne.class.getName(), _executeValue);

		_actionExecutorManagerImpl.executeKaleoAction(kaleoActionTwo, null);

		Assert.assertEquals(
			TestJavaActionExecutorTwo.class.getName(), _executeValue);
	}

	@Test
	public void testUseNonexistentJavaBasedActionExecutor() {
		KaleoAction kaleoAction = Mockito.mock(KaleoAction.class);

		Mockito.when(
			kaleoAction.getScript()
		).thenReturn(
			"test"
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

		try {
			_actionExecutorManagerImpl.executeKaleoAction(kaleoAction, null);

			Assert.fail();
		}
		catch (PortalException portalException) {
			Assert.assertEquals(
				"No action executor for java", portalException.getMessage());
		}
	}

	private static ActionExecutorManagerImpl _actionExecutorManagerImpl;
	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static String _executeValue = StringPool.BLANK;
	private static ServiceRegistration<ActionExecutor>
		_testJavaActionExecutorOneServiceRegistration;
	private static ServiceRegistration<ActionExecutor>
		_testJavaActionExecutorTwoServiceRegistration;

	private static class TestJavaActionExecutorOne implements ActionExecutor {

		@Override
		public void execute(
				KaleoAction kaleoAction, ExecutionContext executionContext)
			throws ActionExecutorException {

			_executeValue = TestJavaActionExecutorOne.class.getName();
		}

		@Override
		public String[] getActionExecutorKeys() {
			return new String[] {"java"};
		}

	}

	private static class TestJavaActionExecutorTwo implements ActionExecutor {

		@Override
		public void execute(
				KaleoAction kaleoAction, ExecutionContext executionContext)
			throws ActionExecutorException {

			_executeValue = TestJavaActionExecutorTwo.class.getName();
		}

		@Override
		public String[] getActionExecutorKeys() {
			return new String[] {"java"};
		}

	}

}