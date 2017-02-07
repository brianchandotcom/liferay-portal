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

package com.liferay.portal.kernel.resiliency.spi.remote;

import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraband;
import com.liferay.portal.kernel.nio.intraband.rpc.RPCResponse;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher.ProcessContext;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtilTestUtil;
import com.liferay.portal.kernel.resiliency.spi.MockRemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.MockSPIProvider;
import com.liferay.portal.kernel.resiliency.spi.MockWelder;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistry;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.MockSPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgentFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.resiliency.spi.provider.SPISynchronousQueueUtil;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI.RegisterCallback;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI.SPIShutdownHook;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI.UnregisterSPIProcessCallable;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RemoteSPITest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		System.setProperty(
			PropsKeys.INTRABAND_IMPL, ExecutorIntraband.class.getName());
		System.setProperty(PropsKeys.INTRABAND_TIMEOUT_DEFAULT, "10000");
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL, MockWelder.class.getName());
		System.setProperty(
			PropsKeys.LIFERAY_HOME, System.getProperty("user.dir"));

		SPIAgentFactoryUtil.registerSPIAgentClass(MockSPIAgent.class);
	}

	@Before
	public void setUp() {
		_spiConfiguration = new SPIConfiguration(
			"spiId", null, null, MockSPIAgent.class.getName(), 8081, null,
			new String[0], new String[0], 5000, 0, 0, null);

		_mockRemoteSPI = new MockRemoteSPI(_spiConfiguration);

		_mockRemoteSPI.countDownLatch = new CountDownLatch(1);

		SPIRegistryUtil spiRegistryUtil = new SPIRegistryUtil();

		spiRegistryUtil.setSPIRegistry(
			(SPIRegistry)ProxyUtil.newProxyInstance(
				RemoteSPITest.class.getClassLoader(),
				new Class<?>[] {SPIRegistry.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						return null;
					}

				}));
	}

	@After
	public void tearDown() {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					MPIHelperUtil.class.getName(), Level.OFF)) {

			for (SPIProvider spiProvider : MPIHelperUtil.getSPIProviders()) {
				MPIHelperUtil.unregisterSPIProvider(spiProvider);
			}
		}
	}

	@Test
	public void testCall() throws Exception {
		final AtomicBoolean throwIOException = new AtomicBoolean();

		// Success

		ProcessOutputStream processOutputStream = new ProcessOutputStream(
			new ObjectOutputStream(new UnsyncByteArrayOutputStream())) {

			@Override
			public void writeProcessCallable(ProcessCallable<?> processCallable)
				throws IOException {

				if (throwIOException.get()) {
					throw new IOException();
				}

				super.writeProcessCallable(processCallable);
			}

		};

		ReflectionTestUtil.setFieldValue(
			ProcessContext.class, "_processOutputStream", processOutputStream);

		ConcurrentMap<String, Object> attributes =
			ProcessContext.getAttributes();

		SPI spi = _mockRemoteSPI.call();

		Assert.assertSame(spi, UnicastRemoteObject.toStub(_mockRemoteSPI));

		Assert.assertTrue(ProcessContext.isAttached());

		ProcessContext.detach();

		Assert.assertSame(
			_mockRemoteSPI,
			attributes.remove(SPI.SPI_INSTANCE_PUBLICATION_KEY));

		// Duplicate export

		try {
			_mockRemoteSPI.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertSame(ExportException.class, throwable.getClass());
		}

		Assert.assertTrue(ProcessContext.isAttached());

		ProcessContext.detach();

		Assert.assertNull(attributes.remove(SPI.SPI_INSTANCE_PUBLICATION_KEY));

		// Unable to write process callable

		UnicastRemoteObject.unexportObject(_mockRemoteSPI, true);

		throwIOException.set(true);

		try {
			_mockRemoteSPI.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertSame(IOException.class, throwable.getClass());
		}

		Assert.assertTrue(ProcessContext.isAttached());

		ProcessContext.detach();

		Assert.assertNull(attributes.remove(SPI.SPI_INSTANCE_PUBLICATION_KEY));

		UnicastRemoteObject.unexportObject(_mockRemoteSPI, true);
	}

	@Test
	public void testConstructor() {
		Assert.assertSame(
			_spiConfiguration, _mockRemoteSPI.getSPIConfiguration());
		Assert.assertSame(MPIHelperUtil.getMPI(), _mockRemoteSPI.getMPI());

		Welder welder = _mockRemoteSPI.getWelder();

		Assert.assertSame(MockWelder.class, welder.getClass());

		Assert.assertNotNull(_mockRemoteSPI.getUUID());
		Assert.assertNull(_mockRemoteSPI.getRegistrationReference());
		Assert.assertTrue(_mockRemoteSPI.isAlive());

		SPIAgent spiAgent = _mockRemoteSPI.getSPIAgent();

		Assert.assertSame(MockSPIAgent.class, spiAgent.getClass());
		Assert.assertSame(spiAgent, _mockRemoteSPI.getSPIAgent());
	}

	@Test
	public void testDestroy() throws RemoteException {

		// Unable to unexport

		try {
			_mockRemoteSPI.destroy();

			Assert.fail();
		}
		catch (RemoteException re) {
			Assert.assertSame(NoSuchObjectException.class, re.getClass());
		}

		CountDownLatch countDownLatch = _mockRemoteSPI.countDownLatch;

		Assert.assertEquals(0, countDownLatch.getCount());

		// Unable to destroy

		_mockRemoteSPI.setFailOnDestroy(true);

		UnicastRemoteObject.exportObject(_mockRemoteSPI, 0);

		try {
			_mockRemoteSPI.destroy();

			Assert.fail();
		}
		catch (RemoteException re) {
			Assert.assertSame(RemoteException.class, re.getClass());
		}

		unexported();

		// Successfully destroy

		_mockRemoteSPI.countDownLatch = null;

		_mockRemoteSPI.setFailOnDestroy(false);

		UnicastRemoteObject.exportObject(_mockRemoteSPI, 0);

		_mockRemoteSPI.destroy();

		unexported();
	}

	@Test
	public void testRegisterCallback() throws Exception {

		// Successfully register callback

		String uuid = "uuid";

		final SynchronousQueue<SPI> synchronousQueue =
			SPISynchronousQueueUtil.createSynchronousQueue(uuid);

		FutureTask<SPI> takeSPIFutureTask = new FutureTask<>(
			new Callable<SPI>() {

				@Override
				public SPI call() throws InterruptedException {
					return synchronousQueue.take();
				}

			});

		Thread takeSPIThread = new Thread(takeSPIFutureTask);

		takeSPIThread.start();

		RegisterCallback registerCallback = new RegisterCallback(
			uuid, _mockRemoteSPI);

		Assert.assertSame(_mockRemoteSPI, registerCallback.call());

		Assert.assertSame(_mockRemoteSPI, takeSPIFutureTask.get());

		// Interrupted on notify waiting

		SPISynchronousQueueUtil.createSynchronousQueue(uuid);

		registerCallback = new RegisterCallback(uuid, _mockRemoteSPI);

		Thread currentThread = Thread.currentThread();

		currentThread.interrupt();

		try {
			registerCallback.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertSame(InterruptedException.class, throwable.getClass());
		}
	}

	@Test
	public void testSerialization() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream)) {

			objectOutputStream.writeObject(_mockRemoteSPI);
		}

		byte[] data = unsyncByteArrayOutputStream.toByteArray();

		System.clearProperty(PropsKeys.INTRABAND_IMPL);
		System.clearProperty(PropsKeys.INTRABAND_TIMEOUT_DEFAULT);
		System.clearProperty(PropsKeys.INTRABAND_WELDER_IMPL);
		System.clearProperty(PropsKeys.LIFERAY_HOME);

		ObjectInputStream objectInputStream = new ObjectInputStream(
			new UnsyncByteArrayInputStream(data));

		Object object = objectInputStream.readObject();

		Assert.assertSame(MockRemoteSPI.class, object.getClass());

		Assert.assertEquals(
			ExecutorIntraband.class.getName(),
			System.getProperty(PropsKeys.INTRABAND_IMPL));
		Assert.assertEquals(
			"10000", System.getProperty(PropsKeys.INTRABAND_TIMEOUT_DEFAULT));
		Assert.assertEquals(
			MockWelder.class.getName(),
			System.getProperty(PropsKeys.INTRABAND_WELDER_IMPL));
		Assert.assertEquals(
			System.getProperty("user.dir"),
			System.getProperty("portal:" + PropsKeys.LIFERAY_HOME));
		Assert.assertEquals(
			"-".concat(_spiConfiguration.getSPIId()),
			System.getProperty("spi.id"));
		Assert.assertEquals(
			"false",
			System.getProperty("portal:" + PropsKeys.AUTO_DEPLOY_ENABLED));
		Assert.assertEquals(
			"false",
			System.getProperty("portal:" + PropsKeys.CLUSTER_LINK_ENABLED));
		Assert.assertEquals(
			"false",
			System.getProperty(
				"portal:" +
					PropsKeys.HOT_DEPLOY_DEPENDENCY_MANAGEMENT_ENABLED));
	}

	@Test
	public void testSerializationSignature() throws Exception {

		// Read object

		Method readObjectMethod = RemoteSPI.class.getDeclaredMethod(
			"readObject", ObjectInputStream.class);

		Assert.assertNotNull(readObjectMethod);
		Assert.assertEquals(Modifier.PRIVATE, readObjectMethod.getModifiers());
		Assert.assertSame(void.class, readObjectMethod.getReturnType());

		Class<?>[] parameterTypes = readObjectMethod.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 1, parameterTypes.length);
		Assert.assertSame(ObjectInputStream.class, parameterTypes[0]);

		List<Class<?>> exceptionTypes = Arrays.asList(
			readObjectMethod.getExceptionTypes());

		Assert.assertEquals(
			exceptionTypes.toString(), 2, exceptionTypes.size());
		Assert.assertTrue(
			exceptionTypes.contains(ClassNotFoundException.class));
		Assert.assertTrue(exceptionTypes.contains(IOException.class));

		// Write object

		Method writeObjectMethod = RemoteSPI.class.getDeclaredMethod(
			"writeObject", ObjectOutputStream.class);

		Assert.assertNotNull(writeObjectMethod);
		Assert.assertEquals(Modifier.PRIVATE, writeObjectMethod.getModifiers());
		Assert.assertSame(void.class, writeObjectMethod.getReturnType());

		parameterTypes = writeObjectMethod.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 1, parameterTypes.length);
		Assert.assertSame(ObjectOutputStream.class, parameterTypes[0]);

		Class<?>[] exceptionTypeArray = writeObjectMethod.getExceptionTypes();

		Assert.assertEquals(
			Arrays.toString(exceptionTypeArray), 1, exceptionTypeArray.length);
		Assert.assertSame(IOException.class, exceptionTypeArray[0]);
	}

	@Test
	public void testSPIShutdownHookRun1() {

		// Shortcut

		_mockRemoteSPI.countDownLatch = new CountDownLatch(0);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.ALL)) {

			spiShutdownHook.run();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}
	}

	@Test
	public void testSPIShutdownHookRun2() throws RemoteException {

		// Unable to unregister, MPI waiting timed out, with log

		String spiProviderName = "spiProviderName";

		MockSPIProvider mockSPIProvider = new MockSPIProvider(spiProviderName);

		MPIHelperUtil.registerSPIProvider(mockSPIProvider);

		_mockRemoteSPI.setFailOnStop(false);
		_mockRemoteSPI.setSpiProviderName(spiProviderName);

		UnicastRemoteObject.exportObject(_mockRemoteSPI, 0);

		MPIHelperUtil.registerSPI(_mockRemoteSPI);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.ALL)) {

			spiShutdownHook.run();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 3, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to unregister SPI from MPI", logRecord.getMessage());

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(NullPointerException.class, throwable.getClass());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"Wait up to " + _spiConfiguration.getShutdownTimeout() +
					" ms for MPI shutdown request",
				logRecord.getMessage());

			logRecord = logRecords.get(2);

			Assert.assertEquals(
				"Proceed with SPI shutdown", logRecord.getMessage());
		}

		unexported();
	}

	@Test
	public void testSPIShutdownHookRun3() throws RemoteException {

		// Unable to unregister, MPI waiting timed out, without log

		String spiProviderName = "spiProviderName";

		MockSPIProvider mockSPIProvider = new MockSPIProvider(spiProviderName);

		MPIHelperUtil.registerSPIProvider(mockSPIProvider);

		_mockRemoteSPI.setFailOnStop(false);
		_mockRemoteSPI.setSpiProviderName(spiProviderName);

		UnicastRemoteObject.exportObject(_mockRemoteSPI, 0);

		MPIHelperUtil.registerSPI(_mockRemoteSPI);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.OFF)) {

			spiShutdownHook.run();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}

		unexported();
	}

	@Test
	public void testSPIShutdownHookRun4() throws Exception {

		// Unable to unregister, MPI shutdown request received, without log

		String spiProviderName = "spiProviderName";

		MockSPIProvider mockSPIProvider = new MockSPIProvider(spiProviderName);

		MPIHelperUtil.registerSPIProvider(mockSPIProvider);

		_mockRemoteSPI = new MockRemoteSPI(
			new SPIConfiguration(
				"spiId", null, null, MockSPIAgent.class.getName(), 8081, null,
				new String[0], new String[0], 5000, 0, Long.MAX_VALUE, null));

		_mockRemoteSPI.countDownLatch = new CountDownLatch(1);

		_mockRemoteSPI.setFailOnStop(false);
		_mockRemoteSPI.setSpiProviderName(spiProviderName);

		UnicastRemoteObject.exportObject(_mockRemoteSPI, 0);

		MPIHelperUtil.registerSPI(_mockRemoteSPI);

		Future<?> future = actionOnMPIWaiting(true);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.OFF)) {

			spiShutdownHook.run();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}

		Assert.assertNull(future.get());

		unexported();
	}

	@Test
	public void testSPIShutdownHookRun5() throws Exception {

		// Unregister returns false, MPI waiting interrupts, with log

		_mockRemoteSPI = new MockRemoteSPI(
			new SPIConfiguration(
				"spiId", null, null, MockSPIAgent.class.getName(), 8081, null,
				new String[0], new String[0], 5000, 0, Long.MAX_VALUE, null));

		_mockRemoteSPI.countDownLatch = new CountDownLatch(1);
		_mockRemoteSPI.registrationReference = mockRegistrationReference(false);

		UnicastRemoteObject.exportObject(_mockRemoteSPI, 0);

		Future<?> future = actionOnMPIWaiting(false);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.ALL)) {

			spiShutdownHook.run();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Wait up to " + Long.MAX_VALUE + " ms for MPI shutdown request",
				logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"Proceed with SPI shutdown", logRecord.getMessage());
		}

		Assert.assertNull(future.get());

		unexported();
	}

	@Test
	public void testSPIShutdownHookRun6() throws Exception {

		// Unregister returns true, MPI shutdown request received, with log

		_mockRemoteSPI = new MockRemoteSPI(
			new SPIConfiguration(
				"spiId", null, null, MockSPIAgent.class.getName(), 8081, null,
				new String[0], new String[0], 5000, 0, Long.MAX_VALUE, null));

		_mockRemoteSPI.countDownLatch = new CountDownLatch(1);
		_mockRemoteSPI.registrationReference = mockRegistrationReference(false);

		UnicastRemoteObject.exportObject(_mockRemoteSPI, 0);

		Future<?> future = actionOnMPIWaiting(true);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.ALL)) {

			spiShutdownHook.run();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Wait up to " + Long.MAX_VALUE + " ms for MPI shutdown request",
				logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"MPI shutdown request received", logRecord.getMessage());
		}

		Assert.assertNull(future.get());

		unexported();
	}

	@Test
	public void testSPIShutdownHookRun7() throws RemoteException {

		// Unregister returns true, MPI waiting timed out, with log

		_mockRemoteSPI.registrationReference = mockRegistrationReference(true);

		UnicastRemoteObject.exportObject(_mockRemoteSPI, 0);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.ALL)) {

			spiShutdownHook.run();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}

		unexported();
	}

	@Test
	public void testSPIShutdownHookShutdown1() throws RemoteException {

		// Peacefully

		UnicastRemoteObject.exportObject(_mockRemoteSPI, 0);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.ALL)) {

			Assert.assertTrue(spiShutdownHook.shutdown(0, null));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}

		unexported();
	}

	@Test
	public void testSPIShutdownHookShutdown2() {

		// Unable to stop and destroy, with log

		_mockRemoteSPI.setFailOnStop(true);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.SEVERE)) {

			Assert.assertTrue(spiShutdownHook.shutdown(0, null));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals("Unable to stop SPI", logRecord.getMessage());

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(RemoteException.class, throwable.getClass());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"Unable to destroy SPI", logRecord.getMessage());

			throwable = logRecord.getThrown();

			Assert.assertSame(
				NoSuchObjectException.class, throwable.getClass());
		}
	}

	@Test
	public void testSPIShutdownHookShutdown3() {

		// Unable to stop and destroy, without log

		_mockRemoteSPI.setFailOnStop(true);

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RemoteSPI.class.getName(), Level.OFF)) {

			Assert.assertTrue(spiShutdownHook.shutdown(0, null));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}
	}

	@Test
	public void testUnregisterSPIProcessCallable() throws Exception {

		// No such SPI

		String spiProviderName = "spiProviderName";
		String spiId = "spiId";

		ProcessCallable<Boolean> processCallable =
			new UnregisterSPIProcessCallable(spiProviderName, spiId);

		Assert.assertFalse(processCallable.call());

		// Unable to unregister SPI due to MPI mismatch

		MPIHelperUtil.registerSPIProvider(new MockSPIProvider(spiProviderName));

		MockSPI mockSPI = new MockSPI();

		mockSPI.spiProviderName = spiProviderName;

		MPIHelperUtilTestUtil.directResigterSPI(spiId, mockSPI);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					MPIHelperUtil.class.getName(), Level.WARNING)) {

			Assert.assertFalse(processCallable.call());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Not unregistering SPI " + mockSPI + " with foreign MPI null " +
					"versus " + MPIHelperUtil.getMPI(),
				logRecord.getMessage());
		}

		// Successfully unregister SPI

		mockSPI.mpi = MPIHelperUtil.getMPI();

		mockSPI.spiConfiguration = new SPIConfiguration(
			spiId, null, 0, null, null, new String[0], null);

		Assert.assertTrue(processCallable.call());
	}

	protected Future<?> actionOnMPIWaiting(final boolean countDownOrInterrupt) {
		final Thread currentThread = Thread.currentThread();

		FutureTask<?> futureTask = new FutureTask<>(
			new Callable<Object>() {

				@Override
				public Object call() {
					AbstractQueuedSynchronizer abstractQueuedSynchronizer =
						ReflectionTestUtil.getFieldValue(
							_mockRemoteSPI.countDownLatch, "sync");

					while (true) {
						Collection<Thread> threads =
							abstractQueuedSynchronizer.getQueuedThreads();

						if (threads.contains(currentThread)) {
							if (countDownOrInterrupt) {
								CountDownLatch countDownLatch =
									_mockRemoteSPI.countDownLatch;

								countDownLatch.countDown();
							}
							else {
								currentThread.interrupt();
							}

							break;
						}
					}

					return null;
				}

			});

		Thread thread = new Thread(futureTask);

		thread.start();

		return futureTask;
	}

	protected RegistrationReference mockRegistrationReference(
		final boolean unregistered) {

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				try {
					Serializer serializer = new Serializer();

					serializer.writeObject(new RPCResponse(unregistered));

					return Datagram.createResponseDatagram(
						datagram, serializer.toByteBuffer());
				}
				catch (Exception e) {
					throw new RuntimeException();
				}
			}

		};

		return new MockRegistrationReference(mockIntraband);
	}

	protected void unexported() {
		try {
			UnicastRemoteObject.unexportObject(_mockRemoteSPI, true);
		}
		catch (NoSuchObjectException nsoe) {
		}
	}

	private MockRemoteSPI _mockRemoteSPI;
	private SPIConfiguration _spiConfiguration;

}