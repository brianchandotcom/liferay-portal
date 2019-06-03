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

package com.liferay.portal.kernel.resiliency.spi.provider;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.io.DummyOutputStream;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraband;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.process.local.LocalProcessChannel;
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
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.SyncThrowableThread;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.resiliency.spi.SPIRegistryImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.concurrent.Callable;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BaseSPIProviderTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

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

		Registry registry = new BasicRegistryImpl();

		RegistryUtil.setRegistry(registry);

		registry.registerService(ProcessExecutor.class, _mockProcessExecutor);

		_testSPIProvider = new TestSPIProvider();

		MPIHelperUtil.registerSPIProvider(_testSPIProvider);

		SPIRegistryUtil spiRegistryUtil = new SPIRegistryUtil();

		spiRegistryUtil.setSPIRegistry(new SPIRegistryImpl());
	}

	@Test
	public void testCreateSPI() throws PortalResiliencyException {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					MPIHelperUtil.class.getName(), Level.OFF)) {

			// Timeout

			try {
				_testSPIProvider.createSPI(_spiConfiguration);
			}
			catch (PortalResiliencyException pre) {
				Assert.assertEquals(
					"SPI synchronous queue waiting timeout. Forcibly " +
						"cancelled SPI process launch.",
					pre.getMessage());

				Assert.assertNull(pre.getCause());
			}

			// Sucess

			_mockProcessExecutor.setRegisterBack(true);

			SPI spi = _testSPIProvider.createSPI(_spiConfiguration);

			Assert.assertSame(RemoteSPIProxy.class, spi.getClass());

			_mockProcessExecutor.sync();

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

			_mockProcessExecutor.setInterrupt(true);
			_mockProcessExecutor.setRegisterBack(false);

			try {
				_testSPIProvider.createSPI(_spiConfiguration);
			}
			catch (PortalResiliencyException pre) {
				Assert.assertEquals(
					"Interrupted on waiting SPI process, registering back " +
						"RMI stub",
					pre.getMessage());

				Throwable throwable = pre.getCause();

				Assert.assertSame(
					InterruptedException.class, throwable.getClass());
			}

			// Process executor failure

			_mockProcessExecutor.setInterrupt(false);
			_mockProcessExecutor.setRegisterBack(false);
			_mockProcessExecutor.setThrowException(true);

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
	}

	private final MockProcessExecutor _mockProcessExecutor =
		new MockProcessExecutor();
	private final SPIConfiguration _spiConfiguration = new SPIConfiguration(
		"testId", "java", "", MockSPIAgent.class.getName(), 8081, "",
		new String[0], new String[0], 10, 10, 10, "");
	private TestSPIProvider _testSPIProvider;

	private static class MockProcessExecutor implements ProcessExecutor {

		@Override
		@SuppressWarnings("unchecked")
		public <T extends Serializable> ProcessChannel<T> execute(
				ProcessConfig processConfig, ProcessCallable<T> processCallable)
			throws ProcessException {

			if (_interrupt) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}

			final MockSPI mockSPI = new MockSPI();

			if (_registerBack) {
				RemoteSPI remoteSPI = (RemoteSPI)processCallable;

				final String spiUUID = remoteSPI.getUUID();

				_syncThrowableThread = new SyncThrowableThread<>(
					new Callable<Void>() {

						@Override
						public Void call() throws InterruptedException {
							SPISynchronousQueueUtil.notifySynchronousQueue(
								spiUUID, mockSPI);

							return null;
						}

					});

				_syncThrowableThread.start();
			}

			if (_throwException) {
				throw new ProcessException("ProcessException");
			}

			DefaultNoticeableFuture<T> defaultNoticeableFuture =
				new DefaultNoticeableFuture<>();

			defaultNoticeableFuture.set((T)mockSPI);

			try {
				return new LocalProcessChannel<>(
					defaultNoticeableFuture,
					new ObjectOutputStream(new DummyOutputStream()),
					new AsyncBroker<Long, Serializable>());
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		public void setInterrupt(boolean interrupt) {
			_interrupt = interrupt;
		}

		public void setRegisterBack(boolean registerBack) {
			_registerBack = registerBack;
		}

		public void setThrowException(boolean throwException) {
			_throwException = throwException;
		}

		public void sync() {
			_syncThrowableThread.sync();

			_syncThrowableThread = null;
		}

		private boolean _interrupt;
		private boolean _registerBack;
		private SyncThrowableThread<Void> _syncThrowableThread;
		private boolean _throwException;

	}

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