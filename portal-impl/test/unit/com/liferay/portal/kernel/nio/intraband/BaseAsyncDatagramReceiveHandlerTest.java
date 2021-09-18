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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
public class BaseAsyncDatagramReceiveHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Before
	public void setUp() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			PortalExecutorManager.class,
			(PortalExecutorManager)ProxyUtil.newProxyInstance(
				BaseAsyncDatagramReceiveHandlerTest.class.getClassLoader(),
				new Class<?>[] {PortalExecutorManager.class},
				new PortalExecutorManagerInvocationHandler()),
			null);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testErrorDispatch() {
		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				BaseAsyncDatagramReceiveHandler.class.getName(),
				Level.SEVERE)) {

			List<LogEntry> logEntries = logCapture.getLogEntries();

			ErrorAsyncDatagramReceiveHandler errorAsyncDatagramReceiveHandler =
				new ErrorAsyncDatagramReceiveHandler();

			errorAsyncDatagramReceiveHandler.receive(null, null);

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals("Unable to dispatch", logEntry.getMessage());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertEquals(Exception.class, throwable.getClass());
		}
	}

	@Test
	public void testNormalDispatch() {
		DummyAsyncDatagramReceiveHandler dummyAsyncDatagramReceiveHandler =
			new DummyAsyncDatagramReceiveHandler();

		dummyAsyncDatagramReceiveHandler.receive(null, null);

		Assert.assertTrue(dummyAsyncDatagramReceiveHandler._received);
	}

	private ServiceRegistration<?> _serviceRegistration;

	private static class DummyAsyncDatagramReceiveHandler
		extends BaseAsyncDatagramReceiveHandler {

		@Override
		protected void doReceive(
			RegistrationReference registrationReference, Datagram datagram) {

			_received = true;
		}

		private boolean _received;

	}

	private static class ErrorAsyncDatagramReceiveHandler
		extends BaseAsyncDatagramReceiveHandler {

		@Override
		protected void doReceive(
				RegistrationReference registrationReference, Datagram datagram)
			throws Exception {

			throw new Exception();
		}

	}

}