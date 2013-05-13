/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.resiliency.spi.provider;

import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraband;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.MockRemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.MockWelder;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.MockSPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgentFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPIProxy;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.resiliency.spi.SPIRegistryImpl;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.io.File;
import java.io.Serializable;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class BaseSPIProviderTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		System.setProperty(
			PropsKeys.INTRABAND_IMPL, ExecutorIntraband.class.getName());
		System.setProperty(PropsKeys.INTRABAND_TIMEOUT_DEFAULT, "10000");
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL, MockWelder.class.getName());

		File currentDir = new File(".");

		System.setProperty(
			PropsKeys.LIFERAY_HOME, currentDir.getAbsolutePath());

		SPIAgentFactoryUtil.registerSPIAgentClass(MockSPIAgent.class);

		_testSPIProvider = new TestSPIProvider();

		MPIHelperUtil.registerSPIProvider(_testSPIProvider);

		SPIRegistryUtil spiRegistryUtil = new SPIRegistryUtil();

		spiRegistryUtil.setSPIRegistry(new SPIRegistryImpl());
	}

	@AdviseWith(adviceClasses = {ProcessExecutorAdvice.class})
	@Test
	public void testCreateSPI() throws PortalResiliencyException {

		// Timeout

		JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		try {
			_testSPIProvider.createSPI(_spiConfiguration);
		}
		catch (PortalResiliencyException pre) {
			Assert.assertEquals(
				"SPI synchronous queue waiting timeout. Forcibly cancelled " +
					"SPI process launch.", pre.getMessage());

			Assert.assertNull(pre.getCause());
		}

		// Sucess

		ProcessExecutorAdvice.setRegisterBack(true);

		SPI spi = _testSPIProvider.createSPI(_spiConfiguration);

		Assert.assertSame(RemoteSPIProxy.class, spi.getClass());

		// Reject

		try {
			_testSPIProvider.createSPI(_spiConfiguration);
		}
		catch (PortalResiliencyException pre) {
			Assert.assertEquals(
				"Unable to register SPI " + spi +
					". Forcibly cancelled SPI process launch.",
				pre.getMessage());

			Assert.assertNull(pre.getCause());
		}

		// Interrupt

		ProcessExecutorAdvice.setInterrupt(true);
		ProcessExecutorAdvice.setRegisterBack(false);

		try {
			_testSPIProvider.createSPI(_spiConfiguration);
		}
		catch (PortalResiliencyException pre) {
			Assert.assertEquals(
				"Interrupted on waiting SPI process, registering back RMI stub",
				pre.getMessage());

			Throwable throwable = pre.getCause();

			Assert.assertSame(InterruptedException.class, throwable.getClass());
		}

		// ProcessExecutor failure

		ProcessExecutorAdvice.setInterrupt(false);
		ProcessExecutorAdvice.setRegisterBack(false);
		ProcessExecutorAdvice.setThrowException(true);

		try {
			_testSPIProvider.createSPI(_spiConfiguration);
		}
		catch (PortalResiliencyException pre) {
			Assert.assertEquals(
				"Unable to launch SPI process", pre.getMessage());

			Throwable throwable = pre.getCause();

			Assert.assertSame(ProcessException.class, throwable.getClass());
			Assert.assertEquals("ProcessException", throwable.getMessage());
		}
	}

	@Aspect
	public static class ProcessExecutorAdvice {

		public static FutureTask<SPI> getFutureTask() {
			return _futureTask;
		}

		public static void setInterrupt(boolean interrupt) {
			_interrupt = interrupt;
		}

		public static void setRegisterBack(boolean registerBack) {
			_registerBack = registerBack;
		}

		public static void setThrowException(boolean throwException) {
			_throwException = throwException;
		}

		@Around(
			"execution(* com.liferay.portal.kernel.process.ProcessExecutor." +
				"execute(String, String, java.util.List, " +
					"com.liferay.portal.kernel.process.ProcessCallable)) && " +
						"args(java, classPath, arguments, processCallable)")
		public Object execute(
				String java, String classPath, List<String> arguments,
				ProcessCallable<? extends Serializable> processCallable)
			throws ProcessException {

			if (_interrupt) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}

			final MockSPI mockSPI = new MockSPI();

			if (_registerBack) {
				final String spiUUID = ((RemoteSPI)processCallable).getUUID();

				Thread notifyThread = new Thread() {

					@Override
					public void run() {
						try {
							SPISynchronousQueueUtil.notifySynchronousQueue(
								spiUUID, mockSPI);
						}
						catch (InterruptedException ie) {
							Assert.fail(ie.getMessage());
						}
					}

				};

				notifyThread.start();
			}

			if (_throwException) {
				throw new ProcessException("ProcessException");
			}

			_futureTask = new FutureTask<SPI>(
				new Callable<SPI>() {

					@Override
					public SPI call() throws Exception {
						return mockSPI;
					}

				}
			);

			return _futureTask;
		}

		private static FutureTask<SPI> _futureTask;
		private static boolean _interrupt;
		private static boolean _registerBack;
		private static boolean _throwException;

	}

	private SPIConfiguration _spiConfiguration = new SPIConfiguration(
		"testId", "java", "", MockSPIAgent.class.getName(), 8081, "",
		new String[0], new String[0], 10, 10, 10);

	private TestSPIProvider _testSPIProvider;

	private static class TestSPIProvider extends BaseSPIProvider {

		@Override
		public RemoteSPI createRemoteSPI(SPIConfiguration spiConfiguration) {
			return new MockRemoteSPI(spiConfiguration) {

				@Override
				public String getUUID() {
					return MockRemoteSPI.class.getName();
				}
			};
		}

		@Override
		public String getClassPath() {
			return StringPool.BLANK;
		}

		@Override
		public String getName() {
			return TestSPIProvider.class.getName();
		}

	}

}