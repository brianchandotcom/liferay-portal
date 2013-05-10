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

package com.liferay.portal.kernel.resiliency.spi.remote;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraband;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.MockRemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.MockWelder;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.MockSPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgentFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPISynchronousQueueUtil;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI.RegisterCallback;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI.SPIShutdownHook;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.rmi.RemoteException;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.LogRecord;

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
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@BeforeClass
	public static void setUpClass() {
		System.setProperty(
			PropsKeys.INTRABAND_IMPL, ExecutorIntraband.class.getName());
		System.setProperty(PropsKeys.INTRABAND_TIMEOUT_DEFAULT, "10000");
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL, MockWelder.class.getName());
		System.setProperty(
			PropsKeys.LIFERAY_HOME, _currentDir.getAbsolutePath());

		SPIAgentFactoryUtil.registerSPIAgentClass(MockSPIAgent.class);
	}

	@Before
	public void setUp() {
		_spiConfiguration = new SPIConfiguration(
			"spiId", MockSPIAgent.class.getName(), 8081, "", new String[0],
			new String[0]);

		_mockRemoteSPI = new MockRemoteSPI(_spiConfiguration);
	}

	@Test
	public void testCall() throws Exception {
		final AtomicBoolean throwIOException = new AtomicBoolean();

		ProcessOutputStream processOutputStream = new ProcessOutputStream(
			new ObjectOutputStream(new UnsyncByteArrayOutputStream())) {

				@Override
				public void writeProcessCallable(
						ProcessCallable<?> processCallable)
					throws IOException {

					if (throwIOException.get()) {
						throw new IOException();
					}

					super.writeProcessCallable(processCallable);
				}

			};

		_setProcessOutputStream(processOutputStream);

		ConcurrentMap<String, Object> attributes =
			ProcessExecutor.ProcessContext.getAttributes();

		// Sucess

		Method bridgeCallMethod = ReflectionUtil.getBridgeMethod(
			RemoteSPI.class, "call");

		SPI spi = (SPI)bridgeCallMethod.invoke(_mockRemoteSPI);

		Assert.assertSame(spi, UnicastRemoteObject.toStub(_mockRemoteSPI));

		Assert.assertTrue(ProcessExecutor.ProcessContext.isAttached());

		ProcessExecutor.ProcessContext.detach();

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

		Assert.assertTrue(ProcessExecutor.ProcessContext.isAttached());

		ProcessExecutor.ProcessContext.detach();

		Assert.assertNull(attributes.remove(SPI.SPI_INSTANCE_PUBLICATION_KEY));

		// Unable to write ProcessCallable

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

		Assert.assertTrue(ProcessExecutor.ProcessContext.isAttached());

		ProcessExecutor.ProcessContext.detach();

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
	public void testRegisterCallback() throws Exception {

		// Success transfer

		String uuid = "uuid";

		final SynchronousQueue<SPI> synchronousQueue =
			SPISynchronousQueueUtil.createSynchronousQueue(uuid);

		FutureTask<SPI> takeSPIFutureTask = new FutureTask<SPI>(
			new Callable<SPI>() {

				@Override
				public SPI call() throws Exception {
					return synchronousQueue.take();
				}

			});

		Thread takeSPIThread = new Thread(takeSPIFutureTask);

		takeSPIThread.start();

		RegisterCallback registerCallback = new RegisterCallback(
			uuid, _mockRemoteSPI);

		Method bridgeCallMethod = ReflectionUtil.getBridgeMethod(
			RegisterCallback.class, "call");

		Assert.assertSame(
			_mockRemoteSPI, bridgeCallMethod.invoke(registerCallback));

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

		// Clear out system properties

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			unsyncByteArrayOutputStream);

		objectOutputStream.writeObject(_mockRemoteSPI);

		objectOutputStream.close();

		byte[] data = unsyncByteArrayOutputStream.toByteArray();

		System.clearProperty(PropsKeys.INTRABAND_IMPL);
		System.clearProperty(PropsKeys.INTRABAND_TIMEOUT_DEFAULT);
		System.clearProperty(PropsKeys.INTRABAND_WELDER_IMPL);
		System.clearProperty(PropsKeys.LIFERAY_HOME);

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(data);

		ObjectInputStream objectInputStream = new ObjectInputStream(
			unsyncByteArrayInputStream);

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
			_currentDir.getAbsolutePath(),
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
	}

	@Test
	public void testSerializationSignature() throws Exception {

		// readObject

		Method readObjectMethod = RemoteSPI.class.getDeclaredMethod(
			"readObject", ObjectInputStream.class);

		Assert.assertNotNull(readObjectMethod);
		Assert.assertEquals(Modifier.PRIVATE, readObjectMethod.getModifiers());
		Assert.assertSame(void.class, readObjectMethod.getReturnType());

		Class<?>[] parameterTypes = readObjectMethod.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertSame(ObjectInputStream.class, parameterTypes[0]);

		List<Class<?>> exceptionTypes = Arrays.asList(
			readObjectMethod.getExceptionTypes());

		Assert.assertEquals(2, exceptionTypes.size());
		Assert.assertTrue(
			exceptionTypes.contains(ClassNotFoundException.class));
		Assert.assertTrue(exceptionTypes.contains(IOException.class));

		// writeObject

		Method writeObjectMethod = RemoteSPI.class.getDeclaredMethod(
			"writeObject", ObjectOutputStream.class);

		Assert.assertNotNull(writeObjectMethod);
		Assert.assertEquals(Modifier.PRIVATE, writeObjectMethod.getModifiers());
		Assert.assertSame(void.class, writeObjectMethod.getReturnType());

		parameterTypes = writeObjectMethod.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertSame(ObjectOutputStream.class, parameterTypes[0]);

		Class<?>[] exceptionTypeArray = writeObjectMethod.getExceptionTypes();

		Assert.assertEquals(1, exceptionTypeArray.length);
		Assert.assertSame(IOException.class, exceptionTypeArray[0]);
	}

	@Test
	public void testSPIShutdownHook() {

		// Peaceful shutdown

		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		Assert.assertTrue(spiShutdownHook.shutdown(0, null));

		// Unable to stop with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			RemoteSPI.class.getName(), Level.SEVERE);

		_mockRemoteSPI.setFailOnStop(true);

		Assert.assertTrue(spiShutdownHook.shutdown(0, null));

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Unable to stop SPI", logRecord.getMessage());

		Throwable throwable = logRecord.getThrown();

		Assert.assertSame(RemoteException.class, throwable.getClass());

		// Unable to stop without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			RemoteSPI.class.getName(), Level.OFF);

		_mockRemoteSPI.setFailOnStop(true);

		Assert.assertTrue(spiShutdownHook.shutdown(0, null));

		Assert.assertTrue(logRecords.isEmpty());

		// Unable to destroy with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			RemoteSPI.class.getName(), Level.SEVERE);

		_mockRemoteSPI.setFailOnStop(false);
		_mockRemoteSPI.setFailOnDestroy(true);

		Assert.assertTrue(spiShutdownHook.shutdown(0, null));

		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals("Unable to destroy SPI", logRecord.getMessage());

		throwable = logRecord.getThrown();

		Assert.assertSame(RemoteException.class, throwable.getClass());

		// Unable to destroy without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			RemoteSPI.class.getName(), Level.OFF);

		_mockRemoteSPI.setFailOnStop(false);
		_mockRemoteSPI.setFailOnDestroy(true);

		Assert.assertTrue(spiShutdownHook.shutdown(0, null));

		Assert.assertTrue(logRecords.isEmpty());
	}

	private void _setProcessOutputStream(
			ProcessOutputStream processOutputStream)
		throws Exception {

		Field processOutputStreamField = ReflectionUtil.getDeclaredField(
			ProcessExecutor.ProcessContext.class, "_processOutputStream");

		processOutputStreamField.set(null, processOutputStream);
	}

	private static final File _currentDir = new File(".");

	private MockRemoteSPI _mockRemoteSPI;
	private SPIConfiguration _spiConfiguration;

}