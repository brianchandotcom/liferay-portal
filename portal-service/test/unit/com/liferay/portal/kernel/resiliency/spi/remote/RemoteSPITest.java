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

package com.liferay.portal.kernel.resiliency.spi.remote;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraBand;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.resiliency.mpi.MPIUtil;
import com.liferay.portal.kernel.resiliency.spi.MockRemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.MockWelder;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.MockSPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgentFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIRegisterSynchronizer;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI.RegisterCallback;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI.SPIShutdownHook;
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
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RemoteSPITest {

	@BeforeClass
	public static void setUpClass() {
		System.setProperty(PropsKeys.INTRABAND_DEFAULT_TIMEOUT, "10000");
		System.setProperty(
			PropsKeys.INTRABAND_IMPL_CLASS, ExecutorIntraBand.class.getName());
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS, MockWelder.class.getName());
		System.setProperty(
			PropsKeys.LIFERAY_HOME, new File("").getAbsolutePath());
		SPIAgentFactoryUtil.registerAgentClass(MockSPIAgent.class);
	}

	@Test
	public void testCall() throws Exception {
		final AtomicBoolean throwIOException = new AtomicBoolean();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			unsyncByteArrayOutputStream);
		ProcessOutputStream processOutputStream = new ProcessOutputStream(
			objectOutputStream) {

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

		// 1) Sucess

		Method bridgeCallMethod = ReflectionUtil.getBridgeMethod(
			RemoteSPI.class, "call");

		SPI spi = (SPI)bridgeCallMethod.invoke(_mockRemoteSPI);

		Assert.assertSame(spi, UnicastRemoteObject.toStub(_mockRemoteSPI));

		Assert.assertTrue(ProcessExecutor.ProcessContext.isAttached());
		ProcessExecutor.ProcessContext.detach();
		Assert.assertSame(
			_mockRemoteSPI,
			attributes.remove(SPI.SPI_INSTANCE_PUBLICATION_KEY));

		// 2) Duplicate export

		try {
			_mockRemoteSPI.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Assert.assertTrue(pe.getCause() instanceof RemoteException);
		}

		Assert.assertTrue(ProcessExecutor.ProcessContext.isAttached());
		ProcessExecutor.ProcessContext.detach();
		Assert.assertNull(attributes.remove(SPI.SPI_INSTANCE_PUBLICATION_KEY));

		// 3) Failed to write ProcessCallable

		UnicastRemoteObject.unexportObject(_mockRemoteSPI, true);
		throwIOException.set(true);

		try {
			_mockRemoteSPI.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Assert.assertEquals(IOException.class, pe.getCause().getClass());
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
		Assert.assertSame(MPIUtil.getMPIStub(), _mockRemoteSPI.getMPI());
		Assert.assertEquals(
			MockWelder.class, _mockRemoteSPI.getWelder().getClass());
		Assert.assertNotNull(_mockRemoteSPI.getUUID());
		Assert.assertNull(_mockRemoteSPI.getRegistrationReference());
		Assert.assertTrue(_mockRemoteSPI.isAlive());

		SPIAgent spiAgent1 = _mockRemoteSPI.getSPIAgent();
		SPIAgent spiAgent2 = _mockRemoteSPI.getSPIAgent();

		Assert.assertEquals(MockSPIAgent.class, spiAgent1.getClass());
		Assert.assertSame(spiAgent1, spiAgent2);
	}

	@Test
	public void testRegisterCallback() throws Exception {
		String testUUID = "testUUID";

		// 1) Success transfer

		final SynchronousQueue<SPI> synchronousQueue =
			SPIRegisterSynchronizer.createSynchronizer(testUUID);

		FutureTask<SPI> takeSPIFutureTask = new FutureTask<SPI>(
			new Callable<SPI>() {

				public SPI call() throws Exception {
					return synchronousQueue.take();
				}
			});

		new Thread(takeSPIFutureTask).start();

		RegisterCallback registerCallback = new RegisterCallback(
			testUUID, _mockRemoteSPI);

		Method bridgeCallMethod = ReflectionUtil.getBridgeMethod(
			RegisterCallback.class, "call");

		Assert.assertSame(
			_mockRemoteSPI, bridgeCallMethod.invoke(registerCallback));
		Assert.assertSame(_mockRemoteSPI, takeSPIFutureTask.get());

		// 2) Interrupted on notify waiting

		SPIRegisterSynchronizer.createSynchronizer(testUUID);

		registerCallback = new RegisterCallback(testUUID, _mockRemoteSPI);

		Thread.currentThread().interrupt();

		try {
			registerCallback.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Assert.assertEquals(
				InterruptedException.class, pe.getCause().getClass());
		}
	}

	@Test
	public void testSerialization() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			unsyncByteArrayOutputStream);

		objectOutputStream.writeObject(_mockRemoteSPI);

		objectOutputStream.close();

		byte[] data = unsyncByteArrayOutputStream.toByteArray();

		// Clear out system properties

		System.clearProperty(PropsKeys.INTRABAND_DEFAULT_TIMEOUT);
		System.clearProperty(PropsKeys.INTRABAND_IMPL_CLASS);
		System.clearProperty(PropsKeys.INTRABAND_WELDER_IMPL_CLASS);
		System.clearProperty(PropsKeys.LIFERAY_HOME);

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(data);

		ObjectInputStream objectInputStream = new ObjectInputStream(
			unsyncByteArrayInputStream);

		Object object = objectInputStream.readObject();

		Assert.assertEquals(MockRemoteSPI.class, object.getClass());

		Assert.assertEquals(
			"10000", System.getProperty(PropsKeys.INTRABAND_DEFAULT_TIMEOUT));
		Assert.assertEquals(
			ExecutorIntraBand.class.getName(),
			System.getProperty(PropsKeys.INTRABAND_IMPL_CLASS));
		Assert.assertEquals(
			MockWelder.class.getName(),
			System.getProperty(PropsKeys.INTRABAND_WELDER_IMPL_CLASS));
		Assert.assertEquals(
			new File("").getAbsolutePath(),
			System.getProperty("portal:" + PropsKeys.LIFERAY_HOME));
		Assert.assertEquals(
			"-".concat(_spiConfiguration.getId()),
			System.getProperty("spi.name"));
		Assert.assertEquals(
			"false",
			System.getProperty("portal:" + PropsKeys.AUTO_DEPLOY_ENABLED));
		Assert.assertEquals(
			"false",
			System.getProperty("portal:" + PropsKeys.CLUSTER_LINK_ENABLED));
	}

	@Test
	public void testSerializationSignature() throws Exception {

		// 1) readObject

		Method readObjectMethod = RemoteSPI.class.getDeclaredMethod(
			"readObject", ObjectInputStream.class);

		Assert.assertNotNull(readObjectMethod);
		Assert.assertEquals(Modifier.PRIVATE, readObjectMethod.getModifiers());
		Assert.assertEquals(void.class, readObjectMethod.getReturnType());

		Class<?>[] parameterTypes = readObjectMethod.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(ObjectInputStream.class, parameterTypes[0]);

		List<Class<?>> exceptionTypes = Arrays.asList(
			readObjectMethod.getExceptionTypes());

		Assert.assertEquals(2, exceptionTypes.size());
		Assert.assertTrue(
			exceptionTypes.contains(ClassNotFoundException.class));
		Assert.assertTrue(exceptionTypes.contains(IOException.class));

		// 2) writeObject

		Method writeObjectMethod = RemoteSPI.class.getDeclaredMethod(
			"writeObject", ObjectOutputStream.class);

		Assert.assertNotNull(writeObjectMethod);
		Assert.assertEquals(Modifier.PRIVATE, writeObjectMethod.getModifiers());
		Assert.assertEquals(void.class, writeObjectMethod.getReturnType());

		parameterTypes = writeObjectMethod.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(ObjectOutputStream.class, parameterTypes[0]);

		Class<?>[] exceptionTypeArray = writeObjectMethod.getExceptionTypes();

		Assert.assertEquals(1, exceptionTypeArray.length);
		Assert.assertEquals(IOException.class, exceptionTypeArray[0]);
	}

	@Test
	public void testSPIShutdownHook() {
		SPIShutdownHook spiShutdownHook = _mockRemoteSPI.new SPIShutdownHook();

		// 1) Peaceful shutdown

		Assert.assertTrue(spiShutdownHook.shutdown(0, null));

		// 2) Failed stop with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			RemoteSPI.class.getName(), Level.SEVERE);

		_mockRemoteSPI.setFailOnStop(true);

		Assert.assertTrue(spiShutdownHook.shutdown(0, null));

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Failed stop SPI.", logRecord.getMessage());
		Assert.assertEquals(
			RemoteException.class, logRecord.getThrown().getClass());

		// 3) Failed stop without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			RemoteSPI.class.getName(), Level.OFF);

		_mockRemoteSPI.setFailOnStop(true);

		Assert.assertTrue(spiShutdownHook.shutdown(0, null));

		Assert.assertTrue(logRecords.isEmpty());

		// 4) Failed destroy with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			RemoteSPI.class.getName(), Level.SEVERE);

		_mockRemoteSPI.setFailOnStop(false);
		_mockRemoteSPI.setFailOnDestroy(true);

		Assert.assertTrue(spiShutdownHook.shutdown(0, null));

		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals("Failed destroy SPI.", logRecord.getMessage());
		Assert.assertEquals(
			RemoteException.class, logRecord.getThrown().getClass());

		// 5) Failed destroy without log

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

	private SPIConfiguration _spiConfiguration = new SPIConfiguration(
		"testId", MockSPIAgent.class.getName(), 8081, "", new String[0],
		new String[0]);
	private MockRemoteSPI _mockRemoteSPI = new MockRemoteSPI(_spiConfiguration);

}