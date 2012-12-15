/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraBand;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.mpi.MPIUtil;
import com.liferay.portal.kernel.resiliency.spi.MockRemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.MockWelder;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.MockSPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgentFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPIStubHolder;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
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
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class BaseSPIProviderTest {

	@Before
	public void setUp() {
		System.setProperty(PropsKeys.INTRABAND_DEFAULT_TIMEOUT, "10000");
		System.setProperty(
			PropsKeys.INTRABAND_IMPL_CLASS, ExecutorIntraBand.class.getName());
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS, MockWelder.class.getName());
		System.setProperty(
			PropsKeys.LIFERAY_HOME, new File("").getAbsolutePath());
		SPIAgentFactoryUtil.registerAgentClass(MockSPIAgent.class);

		_testSPIProvider = new TestSPIProvider();

		MPIUtil.registerSPIProvider(_testSPIProvider);
	}

	@AdviseWith(adviceClasses = {ProcessExecutorAdvice.class})
	@Test
	public void testCreateSPI() throws PortalResiliencyException {
		JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		// 1) Register timeout

		try {
			_testSPIProvider.createSPI(_spiConfiguration);
		}
		catch (PortalResiliencyException pre) {
			Assert.assertEquals(
				"SPI synchronizer waiting timeout. Forcibly cancelled spi " +
					"process launching.", pre.getMessage());

			Assert.assertNull(pre.getCause());
		}

		// 2) Register sucess

		ProcessExecutorAdvice.setRegisterBack(true);

		SPI spi = _testSPIProvider.createSPI(_spiConfiguration);

		Assert.assertEquals(RemoteSPIStubHolder.class, spi.getClass());

		// 3) Register reject

		try {
			_testSPIProvider.createSPI(_spiConfiguration);
		}
		catch (PortalResiliencyException pre) {
			Assert.assertEquals(
				"Failed to register SPI instance " + spi +
					". Forcibly cancelled spi process launching.",
				pre.getMessage());

			Assert.assertNull(pre.getCause());
		}

		// 4) Interrupted on register waiting

		ProcessExecutorAdvice.setInterrupt(true);
		ProcessExecutorAdvice.setRegisterBack(false);

		try {
			_testSPIProvider.createSPI(_spiConfiguration);
		}
		catch (PortalResiliencyException pre) {
			Assert.assertEquals(
				"Interrupted while waiting on SPI process to register back " +
					"the RMI Stub", pre.getMessage());

			Throwable throwable = pre.getCause();

			Assert.assertEquals(
				InterruptedException.class, throwable.getClass());
		}

		// 5) ProcessExecutor failure

		ProcessExecutorAdvice.setInterrupt(false);
		ProcessExecutorAdvice.setRegisterBack(false);
		ProcessExecutorAdvice.setThrowException(true);

		try {
			_testSPIProvider.createSPI(_spiConfiguration);
		}
		catch (PortalResiliencyException pre) {
			Assert.assertEquals(
				"Failed to launch SPI process", pre.getMessage());

			Throwable throwable = pre.getCause();

			Assert.assertEquals(ProcessException.class, throwable.getClass());
			Assert.assertEquals(
				"Fake ProcessException", throwable.getMessage());
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
				Thread.currentThread().interrupt();
			}

			final MockSPI mockSPI = new MockSPI();

			if (_registerBack) {
				final RemoteSPI remoteSPI = (RemoteSPI)processCallable;

				new Thread() {

					public void run() {
						try {
							SPIRegisterSynchronizer.notifySynchronizer(
								remoteSPI.getUUID(), mockSPI);
						}
						catch (InterruptedException ie) {
							Assert.fail(ie.getMessage());
						}
					}

				}.start();
			}

			if (_throwException) {
				throw new ProcessException("Fake ProcessException");
			}

			_futureTask = new FutureTask<SPI>(new Callable<SPI>() {

				public SPI call() throws Exception {
					return mockSPI;
				}

			});

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
		public RemoteSPI createRemoteSPI(SPIConfiguration spiConfiguration)
			throws PortalResiliencyException {

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

		public String getName() {
			return TestSPIProvider.class.getName();
		}

	}

}