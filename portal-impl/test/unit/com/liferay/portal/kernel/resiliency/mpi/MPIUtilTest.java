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

package com.liferay.portal.kernel.resiliency.mpi;

import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraBand;
import com.liferay.portal.kernel.nio.intraband.nonblocking.SelectorIntraBand;
import com.liferay.portal.kernel.nio.intraband.welder.socket.SocketWelder;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.PropsUtilAdvice;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class MPIUtilTest {

	@Before
	public void setUp() {
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_DEFAULT_TIMEOUT, Long.toString(10000));
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_IMPL_CLASS, ExecutorIntraBand.class.getName());
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS,
			SocketWelder.class.getName());
	}

	@After
	public void tearDown() {
		try {
			MPIUtil.shutdown();
		}
		catch (Throwable t) {
		}
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testClassInitializationFailed() throws Exception {
		System.setProperty(PropsKeys.INTRABAND_IMPL_CLASS, "No Such Class");

		try {
			MPIUtil.getMPIStub();

			Assert.fail();
		}
		catch (ExceptionInInitializerError eiie) {
			Assert.assertEquals(
				RuntimeException.class, eiie.getCause().getClass());
		}
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testClassInitializationMPI() throws Exception {
		PropsUtil.setProps(
			(Props)ProxyUtil.newProxyInstance(
				getClass().getClassLoader(), new Class<?>[] {Props.class},
				new InvocationHandler() {

					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						throw new UnsupportedOperationException();
					}
				}));

		MPI mpiImpi = _getMPIImpl();

		Assert.assertNotNull(mpiImpi);
		Assert.assertTrue(mpiImpi.isAlive());

		MPI mpiStub = MPIUtil.getMPIStub();

		MPI lookupMPIStub = (MPI)UnicastRemoteObject.toStub(mpiImpi);

		Assert.assertSame(mpiStub, lookupMPIStub);
		Assert.assertTrue(mpiStub.isAlive());

		IntraBand intraBand = MPIUtil.getIntraBand();

		Assert.assertEquals(ExecutorIntraBand.class, intraBand.getClass());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testClassInitializationSPI() throws Exception {
		System.setProperty(
			PropsKeys.INTRABAND_DEFAULT_TIMEOUT, Long.toString(10000));
		System.setProperty(
			PropsKeys.INTRABAND_IMPL_CLASS, SelectorIntraBand.class.getName());
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS,
			SocketWelder.class.getName());

		MPI mpiImpi = _getMPIImpl();

		Assert.assertNotNull(mpiImpi);
		Assert.assertTrue(mpiImpi.isAlive());

		MPI mpiStub = MPIUtil.getMPIStub();

		MPI lookupMPIStub = (MPI)UnicastRemoteObject.toStub(mpiImpi);

		Assert.assertSame(mpiStub, lookupMPIStub);
		Assert.assertTrue(mpiStub.isAlive());

		IntraBand intraBand = MPIUtil.getIntraBand();

		Assert.assertEquals(SelectorIntraBand.class, intraBand.getClass());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testShutdownFail() throws Exception {
		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.WARNING);

		MPIUtil.shutdown();

		// 1) with log

		MPIUtil.shutdown();

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Failed to unexport MPIImpl", logRecord.getMessage());
		Assert.assertEquals(
			NoSuchObjectException.class, logRecord.getThrown().getClass());

		// 2) without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		MPIUtil.shutdown();

		Assert.assertTrue(logRecords.isEmpty());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testSPIKeyEquals() throws Exception {

		// Satisfy code coverage

		// 1) spiProvider name does not match

		Object spiKey1 = _createSPIKey("name1", "id1");
		Object spiKey2 = _createSPIKey("name2", "id1");

		Assert.assertFalse(spiKey1.equals(spiKey2));

		// 2) spiId does not match

		spiKey1 = _createSPIKey("name1", "id1");
		spiKey2 = _createSPIKey("name1", "id2");

		Assert.assertFalse(spiKey1.equals(spiKey2));
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testSPIProviderRegistration() throws Exception {

		// 1) register SPIProvider with null name

		MockSPIProvider mockSPIProvider1 = new MockSPIProvider(null);

		try {
			MPIUtil.registerSPIProvider(mockSPIProvider1);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// 2) register SPIProvider with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.INFO);

		String name1 = "spiProvider1";

		mockSPIProvider1 = new MockSPIProvider(name1);

		Assert.assertTrue(MPIUtil.registerSPIProvider(mockSPIProvider1));

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Registered new SPIProvider " + mockSPIProvider1 + " with name " +
				name1, logRecord1.getMessage());

		// 3) register SPIProvider without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		String name2 = "spiProvider2";

		MockSPIProvider mockSPIProvider2 = new MockSPIProvider(name2);

		Assert.assertTrue(MPIUtil.registerSPIProvider(mockSPIProvider2));

		Assert.assertTrue(logRecords.isEmpty());

		// 4) register SPIProvider with exist name, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.WARNING);

		MockSPIProvider mockSPIProvider3 = new MockSPIProvider(name1);

		Assert.assertFalse(MPIUtil.registerSPIProvider(mockSPIProvider3));

		Assert.assertEquals(1, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Can not register SPIProvider " + mockSPIProvider3 + " with name " +
				name1 + ". There is already a SPIProvider with the same name " +
					"registered " + mockSPIProvider1, logRecord1.getMessage());

		// 5) register SPIProvider with exist name, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		mockSPIProvider3 = new MockSPIProvider(name1);

		Assert.assertFalse(MPIUtil.registerSPIProvider(mockSPIProvider3));

		Assert.assertTrue(logRecords.isEmpty());

		// 6) get SPIProvider

		String name3 = "spiProvider3";

		Assert.assertSame(mockSPIProvider1, MPIUtil.getSPIProvider(name1));
		Assert.assertSame(mockSPIProvider2, MPIUtil.getSPIProvider(name2));
		Assert.assertNull(MPIUtil.getSPIProvider(name3));

		List<SPIProvider> spiProviders = MPIUtil.getSPIProviders();

		Assert.assertEquals(2, spiProviders.size());
		Assert.assertTrue(spiProviders.contains(mockSPIProvider1));
		Assert.assertTrue(spiProviders.contains(mockSPIProvider2));

		// 7) unregister SPIProvider with null name

		mockSPIProvider3 = new MockSPIProvider(null);

		try {
			MPIUtil.unregisterSPIProvider(mockSPIProvider3);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// 8) unregister SPIProvider, non-exist, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.WARNING);

		mockSPIProvider3 = new MockSPIProvider(name3);

		Assert.assertFalse(MPIUtil.unregisterSPIProvider(mockSPIProvider3));

		Assert.assertEquals(1, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Can not unregister SPIProvider " + mockSPIProvider3 +
				" with name " + name3 + ". It is not registered",
		logRecord1.getMessage());

		// 9) unregister SPIProvider, non-exist, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		mockSPIProvider3 = new MockSPIProvider(name3);

		Assert.assertFalse(MPIUtil.unregisterSPIProvider(mockSPIProvider3));

		Assert.assertTrue(logRecords.isEmpty());

		// 10) unregister SPIProvider, with no SPI, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.INFO);

		Assert.assertTrue(MPIUtil.unregisterSPIProvider(mockSPIProvider2));

		Assert.assertEquals(1, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Unregistered SPIProvider " + mockSPIProvider2 + " with name " +
				name2, logRecord1.getMessage());

		// 11) unregister SPIProvider, with no SPI, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		Assert.assertTrue(MPIUtil.unregisterSPIProvider(mockSPIProvider1));

		Assert.assertTrue(logRecords.isEmpty());

		// 12) unregister SPIProvider, with SPI, fail on destroy, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.SEVERE);

		mockSPIProvider1 = new MockSPIProvider(name1);
		mockSPIProvider2 = new MockSPIProvider(name2);

		Assert.assertTrue(MPIUtil.registerSPIProvider(mockSPIProvider1));
		Assert.assertTrue(MPIUtil.registerSPIProvider(mockSPIProvider2));

		MockSPI mockSPI1 = new MockSPI();
		mockSPI1.spiProviderName = name1;
		mockSPI1.failOnDestroy = true;

		MockSPI mockSPI2 = new MockSPI();
		mockSPI2.spiProviderName = name2;
		mockSPI2.failOnDestroy = true;

		Object spiKey1 = _directResigterSPI("spi1", mockSPI1);
		_directResigterSPI("spi2", mockSPI2);

		Assert.assertTrue(MPIUtil.unregisterSPIProvider(mockSPIProvider1));

		Assert.assertEquals(1, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Failed cascaded unregister SPI " + mockSPI1 + " with key " +
				spiKey1, logRecord1.getMessage());
		Assert.assertEquals(
			RemoteException.class, logRecord1.getThrown().getClass());

		// 13) unregister SPIProvider, with SPI, fail on destroy, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		Assert.assertTrue(MPIUtil.unregisterSPIProvider(mockSPIProvider2));

		Assert.assertTrue(logRecords.isEmpty());

		// 14) unregister SPIProvider, with SPI, success, with log

		mockSPIProvider1 = new MockSPIProvider(name1);
		mockSPIProvider2 = new MockSPIProvider(name2);

		Assert.assertTrue(MPIUtil.registerSPIProvider(mockSPIProvider1));
		Assert.assertTrue(MPIUtil.registerSPIProvider(mockSPIProvider2));

		mockSPI1 = new MockSPI();
		mockSPI1.spiProviderName = name1;
		mockSPI1.failOnDestroy = false;

		mockSPI2 = new MockSPI();
		mockSPI2.spiProviderName = name2;
		mockSPI2.failOnDestroy = false;

		spiKey1 = _directResigterSPI("spi1", mockSPI1);
		_directResigterSPI("spi2", mockSPI2);

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.INFO);

		Assert.assertTrue(MPIUtil.unregisterSPIProvider(mockSPIProvider1));

		Assert.assertEquals(2, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Cascaded unregistered SPI " + mockSPI1 + " with key " + spiKey1,
			logRecord1.getMessage());

		LogRecord logRecord2 = logRecords.get(1);

		Assert.assertEquals(
			"Unregistered SPIProvider " + mockSPIProvider1 + " with name " +
				name1, logRecord2.getMessage());

		// 15) unregister SPIProvider, with SPI, success, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		Assert.assertTrue(MPIUtil.unregisterSPIProvider(mockSPIProvider2));

		Assert.assertTrue(logRecords.isEmpty());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testSPIRegistration() throws Exception {

		// 1) Mismatch MPI, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.WARNING);

		MockSPI mockSPI1 = new MockSPI();
		mockSPI1.mpi = new MockMPI();

		Assert.assertFalse(MPIUtil.registerSPI(mockSPI1));

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Can not register SPI instance " + mockSPI1 +
				". It is created by a foreign MPI " + mockSPI1.mpi +
					", not by us " + MPIUtil.getMPIStub(),
			logRecord.getMessage());

		// 2) Mismatch MPI, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		Assert.assertFalse(MPIUtil.registerSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// 3) Null SPIProvider name

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = MPIUtil.getMPIStub();
		mockSPI1.spiProviderName = null;

		try {
			MPIUtil.registerSPI(mockSPI1);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// 4) No such SPIProvider, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.WARNING);

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = MPIUtil.getMPIStub();
		mockSPI1.spiProviderName = "name1";

		Assert.assertFalse(MPIUtil.registerSPI(mockSPI1));

		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Can not register SPI instance " + mockSPI1 +
				". No such SPIProvider registered with name " +
					mockSPI1.spiProviderName, logRecord.getMessage());

		// 5) No such SPIProvider, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = MPIUtil.getMPIStub();
		mockSPI1.spiProviderName = "name1";

		Assert.assertFalse(MPIUtil.registerSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// 6) Success register, with log

		String name = "name1";

		MockSPIProvider mockSPIProvider = new MockSPIProvider(name);

		Assert.assertTrue(MPIUtil.registerSPIProvider(mockSPIProvider));

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = MPIUtil.getMPIStub();
		mockSPI1.spiProviderName = name;
		mockSPI1.spiConfiguration = new SPIConfiguration(
			"testId1", "", 8081, "", new String[0], new String[0]);

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.INFO);

		Assert.assertTrue(MPIUtil.registerSPI(mockSPI1));

		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertTrue(logRecord.getMessage().startsWith(
			"Registered new SPI " + mockSPI1 + " with key "));

		// 7) Success register, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		MockSPI mockSPI2 = new MockSPI();
		mockSPI2.mpi = MPIUtil.getMPIStub();
		mockSPI2.spiProviderName = name;
		mockSPI2.spiConfiguration = new SPIConfiguration(
			"testId2", "", 8082, "", new String[0], new String[0]);

		Assert.assertTrue(MPIUtil.registerSPI(mockSPI2));

		Assert.assertTrue(logRecords.isEmpty());

		// 8) Duplicate register, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.WARNING);

		Assert.assertFalse(MPIUtil.registerSPI(mockSPI1));

		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertTrue(logRecord.getMessage().startsWith(
			"Can not register SPI instance " + mockSPI1 + " with key "));

		// 9) Duplicate register, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		Assert.assertFalse(MPIUtil.registerSPI(mockSPI2));

		Assert.assertTrue(logRecords.isEmpty());

		// 10) Bad SPI impl

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = MPIUtil.getMPIStub();
		mockSPI1.spiProviderName = name;
		mockSPI1.failOnGetConfiguration = true;

		try {
			MPIUtil.registerSPI(mockSPI1);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				RemoteException.class, re.getCause().getClass());
		}

		// 11) Get SPI, exist

		Assert.assertNotNull(MPIUtil.getSPI(name, "testId1"));

		// 12) Get SPI, not exist

		Assert.assertNull(MPIUtil.getSPI(name, "testId3"));

		// 13) Get SPIs

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.SEVERE);

		mockSPI2.failOnIsAlive = true;

		List<SPI> spis = MPIUtil.getSPIs();

		Assert.assertEquals(1, spis.size());

		mockSPI1 = (MockSPI)spis.get(0);

		Assert.assertEquals(name, mockSPI1.spiProviderName);
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			RemoteException.class, logRecord.getThrown().getClass());

		// 14) Get SPIs by SPIProvider, exist

		mockSPI2 = new MockSPI();
		mockSPI2.mpi = MPIUtil.getMPIStub();
		mockSPI2.spiProviderName = name;
		mockSPI2.spiConfiguration = new SPIConfiguration(
			"testId2", "", 8082, "", new String[0], new String[0]);

		Assert.assertTrue(MPIUtil.registerSPI(mockSPI2));

		mockSPI2.failOnIsAlive = true;

		spis = MPIUtil.getSPIs(name);

		Assert.assertEquals(1, spis.size());

		mockSPI1 = (MockSPI)spis.get(0);

		Assert.assertEquals(name, mockSPI1.spiProviderName);

		// 15) Get SPIs by SPIProvider, not exist

		spis = MPIUtil.getSPIs("name2");

		Assert.assertTrue(spis.isEmpty());

		// 16) Unregister MPI mismatch, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.WARNING);

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = new MockMPI();

		Assert.assertFalse(MPIUtil.unregisterSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Can not unregister SPI instance " + mockSPI1 +
				". It is created by a foreign MPI " + mockSPI1.mpi +
					", not by us " + MPIUtil.getMPIStub(),
			logRecord.getMessage());

		// 17) Unregister MPI mismatch, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		Assert.assertFalse(MPIUtil.unregisterSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// 18) Unregister no such SPIProvider, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.WARNING);

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = MPIUtil.getMPIStub();
		mockSPI1.spiProviderName = "name2";

		Assert.assertFalse(MPIUtil.unregisterSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Can not unregister SPI instance " + mockSPI1 +
				". No such SPIProvider registered with name " +
					mockSPI1.spiProviderName,
			logRecord.getMessage());

		// 19) Unregister no such SPIProvider, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = MPIUtil.getMPIStub();
		mockSPI1.spiProviderName = "name2";

		Assert.assertFalse(MPIUtil.unregisterSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// 20) Unregister no such SPI, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.WARNING);

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = MPIUtil.getMPIStub();
		mockSPI1.spiProviderName = name;
		mockSPI1.spiConfiguration = new SPIConfiguration(
			"testId3", "", 8083, "", new String[0], new String[0]);

		Assert.assertFalse(MPIUtil.unregisterSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertTrue(logRecord.getMessage().startsWith(
			"Can not unregister SPI " + mockSPI1 + " with key "));

		// 21) Unregister no such SPI, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		mockSPI1 = new MockSPI();
		mockSPI1.mpi = MPIUtil.getMPIStub();
		mockSPI1.spiProviderName = name;
		mockSPI1.spiConfiguration = new SPIConfiguration(
			"testId3", "", 8083, "", new String[0], new String[0]);

		Assert.assertFalse(MPIUtil.unregisterSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// 21) Unregister success, with log

		mockSPI1 = (MockSPI)MPIUtil.getSPI(name, "testId1");

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.INFO);

		Assert.assertTrue(MPIUtil.unregisterSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertTrue(logRecord.getMessage().startsWith(
			"Unregistered SPI " + mockSPI1 + " with key "));

		// 22) Unregister success, without log

		Assert.assertTrue(MPIUtil.registerSPI(mockSPI1));

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIUtil.class.getName(), Level.OFF);

		Assert.assertTrue(MPIUtil.unregisterSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// 23) Unregister fail on get Configuration

		mockSPI1.failOnGetConfiguration = true;

		try {
			MPIUtil.unregisterSPI(mockSPI1);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				RemoteException.class, re.getCause().getClass());
		}
	}

	private static Object _createSPIKey(String spiProviderName, String spiId)
		throws Exception {

		Class<?> spiKeyClass = Class.forName(
			MPIUtil.class.getName().concat("$SPIKey"));

		Constructor<?> spiKeyConstructor = spiKeyClass.getConstructor(
			String.class, String.class);

		return spiKeyConstructor.newInstance(spiProviderName, spiId);
	}

	private static Object _directResigterSPI(String spiId, SPI spi)
		throws Exception {

		Field spiMapField = ReflectionUtil.getDeclaredField(
			MPIUtil.class, "_spiMap");

		Map<Object, SPI> spiMap = (Map<Object, SPI>)spiMapField.get(null);

		Object spiKey = _createSPIKey(spi.getSPIProviderName(), spiId);

		spiMap.put(spiKey, spi);

		return spiKey;
	}

	private static MPI _getMPIImpl() throws Exception {
		Field mpiImplField = ReflectionUtil.getDeclaredField(
			MPIUtil.class, "_mpiImpl");

		MPI mpiImpl = (MPI)mpiImplField.get(null);

		Assert.assertNotNull(mpiImpl);

		return mpiImpl;
	}

	private static class MockMPI implements MPI {

		public boolean isAlive() throws RemoteException {
			return true;
		}

	}

	private static class MockSPIProvider implements SPIProvider {

		public MockSPIProvider(String name) {
			_name = name;
		}

		public SPI createSPI(SPIConfiguration spiConfiguration)
			throws PortalResiliencyException {

			throw new UnsupportedOperationException();
		}

		public String getName() {
			return _name;
		}

		private String _name;
	}

}