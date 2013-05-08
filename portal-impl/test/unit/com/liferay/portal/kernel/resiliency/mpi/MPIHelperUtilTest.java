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

package com.liferay.portal.kernel.resiliency.mpi;

import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraband;
import com.liferay.portal.kernel.nio.intraband.nonblocking.SelectorIntraband;
import com.liferay.portal.kernel.nio.intraband.welder.socket.SocketWelder;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.PropsUtilAdvice;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.resiliency.spi.SPIRegistryImpl;
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
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class MPIHelperUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_IMPL, ExecutorIntraband.class.getName());
		PropsUtilAdvice.setProps(PropsKeys.INTRABAND_TIMEOUT_DEFAULT, "10000");
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_IMPL, SocketWelder.class.getName());

		SPIRegistryUtil spiRegistryUtil = new SPIRegistryUtil();

		spiRegistryUtil.setSPIRegistry(new SPIRegistryImpl());
	}

	@After
	public void tearDown() {
		try {
			MPIHelperUtil.shutdown();
		}
		catch (Throwable t) {
		}
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testClassInitializationFailed() {
		System.setProperty(PropsKeys.INTRABAND_IMPL, "No Such Class");

		try {
			MPIHelperUtil.getMPI();

			Assert.fail();
		}
		catch (ExceptionInInitializerError eiie) {
			Throwable throwable = eiie.getCause();

			Assert.assertSame(RuntimeException.class, throwable.getClass());

			Assert.assertEquals(
				"Unable to instantiate No Such Class", throwable.getMessage());
		}
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testClassInitializationOnMPI() throws Exception {
		PropsUtil.setProps(
			(Props)ProxyUtil.newProxyInstance(
				MPIHelperUtilTest.class.getClassLoader(),
				new Class<?>[] {Props.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						throw new UnsupportedOperationException();
					}

				}));

		MPI mpiImpi = _getMPIImpl();

		Assert.assertNotNull(mpiImpi);
		Assert.assertTrue(mpiImpi.isAlive());

		MPI mpi = MPIHelperUtil.getMPI();

		Assert.assertSame(mpi, UnicastRemoteObject.toStub(mpiImpi));
		Assert.assertTrue(mpi.isAlive());

		Intraband intraband = MPIHelperUtil.getIntraband();

		Assert.assertSame(ExecutorIntraband.class, intraband.getClass());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testClassInitializationOnSPI() throws Exception {
		System.setProperty(
			PropsKeys.INTRABAND_IMPL, SelectorIntraband.class.getName());
		System.setProperty(PropsKeys.INTRABAND_TIMEOUT_DEFAULT, "10000");
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL, SocketWelder.class.getName());

		MPI mpiImpi = _getMPIImpl();

		Assert.assertNotNull(mpiImpi);
		Assert.assertTrue(mpiImpi.isAlive());

		MPI mpi = MPIHelperUtil.getMPI();

		Assert.assertSame(mpi, UnicastRemoteObject.toStub(mpiImpi));
		Assert.assertTrue(mpi.isAlive());

		Intraband intraband = MPIHelperUtil.getIntraband();

		Assert.assertSame(SelectorIntraband.class, intraband.getClass());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testShutdownFail() throws Exception {

		// Shutdown after shutdown, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.WARNING);

		MPIHelperUtil.shutdown();

		MPIHelperUtil.shutdown();

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to unexport " + _getMPIImpl(), logRecord.getMessage());

		Throwable throwable = logRecord.getThrown();

		Assert.assertSame(NoSuchObjectException.class, throwable.getClass());

		// Shutdown after hutdown, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		MPIHelperUtil.shutdown();

		Assert.assertTrue(logRecords.isEmpty());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testSPIKey() throws Exception {

		// spiProvider name does not match

		Object spiKey1 = _createSPIKey("name1", "id1");
		Object spiKey2 = _createSPIKey("name2", "id1");

		Assert.assertFalse(spiKey1.equals(spiKey2));
		Assert.assertEquals("name1#id1", spiKey1.toString());
		Assert.assertEquals("name2#id1", spiKey2.toString());

		// spiId does not match

		spiKey1 = _createSPIKey("name1", "id1");
		spiKey2 = _createSPIKey("name1", "id2");

		Assert.assertFalse(spiKey1.equals(spiKey2));
		Assert.assertEquals("name1#id1", spiKey1.toString());
		Assert.assertEquals("name1#id2", spiKey2.toString());

	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testSPIProviderRegistration() throws Exception {

		// Register SPIProvider, null name

		MockSPIProvider mockSPIProvider1 = new MockSPIProvider(null);

		try {
			MPIHelperUtil.registerSPIProvider(mockSPIProvider1);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// Register SPIProvider, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.INFO);

		String name1 = "spiProvider1";

		mockSPIProvider1 = new MockSPIProvider(name1);

		Assert.assertTrue(MPIHelperUtil.registerSPIProvider(mockSPIProvider1));
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Registered SPI provider " + mockSPIProvider1,
			logRecord1.getMessage());

		// Register SPIProvider, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		String name2 = "spiProvider2";

		MockSPIProvider mockSPIProvider2 = new MockSPIProvider(name2);

		Assert.assertTrue(MPIHelperUtil.registerSPIProvider(mockSPIProvider2));
		Assert.assertTrue(logRecords.isEmpty());

		// Register SPIProvider, exist name, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.WARNING);

		MockSPIProvider mockSPIProvider3 = new MockSPIProvider(name1);

		Assert.assertFalse(MPIHelperUtil.registerSPIProvider(mockSPIProvider3));
		Assert.assertEquals(1, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Not registering SPI provider " + mockSPIProvider3 +
				" because it duplicates " + mockSPIProvider1,
			logRecord1.getMessage());

		// Register SPIProvider, exist name, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		mockSPIProvider3 = new MockSPIProvider(name1);

		Assert.assertFalse(MPIHelperUtil.registerSPIProvider(mockSPIProvider3));
		Assert.assertTrue(logRecords.isEmpty());

		// Get SPIProvider

		String name3 = "spiProvider3";

		Assert.assertSame(
			mockSPIProvider1, MPIHelperUtil.getSPIProvider(name1));
		Assert.assertSame(
			mockSPIProvider2, MPIHelperUtil.getSPIProvider(name2));
		Assert.assertNull(MPIHelperUtil.getSPIProvider(name3));

		List<SPIProvider> spiProviders = MPIHelperUtil.getSPIProviders();

		Assert.assertEquals(2, spiProviders.size());
		Assert.assertTrue(spiProviders.contains(mockSPIProvider1));
		Assert.assertTrue(spiProviders.contains(mockSPIProvider2));

		// Unregister SPIProvider, null name

		mockSPIProvider3 = new MockSPIProvider(null);

		try {
			MPIHelperUtil.unregisterSPIProvider(mockSPIProvider3);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// Unregister SPIProvider, not exist, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.WARNING);

		mockSPIProvider3 = new MockSPIProvider(name3);

		Assert.assertFalse(
			MPIHelperUtil.unregisterSPIProvider(mockSPIProvider3));
		Assert.assertEquals(1, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Not unregistering unregistered SPI provider " + mockSPIProvider3,
			logRecord1.getMessage());

		// Unregister SPIProvider, not exist, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		mockSPIProvider3 = new MockSPIProvider(name3);

		Assert.assertFalse(
			MPIHelperUtil.unregisterSPIProvider(mockSPIProvider3));
		Assert.assertTrue(logRecords.isEmpty());

		// Unregister SPIProvider, with no SPI, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.INFO);

		Assert.assertTrue(
			MPIHelperUtil.unregisterSPIProvider(mockSPIProvider2));
		Assert.assertEquals(1, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Unregistered SPI provider " + mockSPIProvider2,
			logRecord1.getMessage());

		// Unregister SPIProvider, with no SPI, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		Assert.assertTrue(
			MPIHelperUtil.unregisterSPIProvider(mockSPIProvider1));
		Assert.assertTrue(logRecords.isEmpty());

		// Unregister SPIProvider, with SPI, fail on destroy, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.SEVERE);

		mockSPIProvider1 = new MockSPIProvider(name1);

		Assert.assertTrue(MPIHelperUtil.registerSPIProvider(mockSPIProvider1));

		mockSPIProvider2 = new MockSPIProvider(name2);

		Assert.assertTrue(MPIHelperUtil.registerSPIProvider(mockSPIProvider2));

		MockSPI mockSPI1 = new MockSPI();

		mockSPI1.spiProviderName = name1;
		mockSPI1.failOnDestroy = true;

		_directResigterSPI("spi1", mockSPI1);

		MockSPI mockSPI2 = new MockSPI();

		mockSPI2.spiProviderName = name2;
		mockSPI2.failOnDestroy = true;

		_directResigterSPI("spi2", mockSPI2);

		Assert.assertTrue(
			MPIHelperUtil.unregisterSPIProvider(mockSPIProvider1));
		Assert.assertEquals(1, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Unable to unregister SPI " + mockSPI1 +
				" while unregistering SPI provider " + mockSPIProvider1,
			logRecord1.getMessage());

		Throwable throwable = logRecord1.getThrown();

		Assert.assertSame(RemoteException.class, throwable.getClass());

		// Unregister SPIProvider, with SPI, fail on destroy, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		Assert.assertTrue(
			MPIHelperUtil.unregisterSPIProvider(mockSPIProvider2));
		Assert.assertTrue(logRecords.isEmpty());

		// Unregister SPIProvider, with SPI, success, with log

		mockSPIProvider1 = new MockSPIProvider(name1);

		Assert.assertTrue(MPIHelperUtil.registerSPIProvider(mockSPIProvider1));

		mockSPIProvider2 = new MockSPIProvider(name2);

		Assert.assertTrue(MPIHelperUtil.registerSPIProvider(mockSPIProvider2));

		mockSPI1 = new MockSPI();

		mockSPI1.spiProviderName = name1;
		mockSPI1.failOnDestroy = false;

		_directResigterSPI("spi1", mockSPI1);

		mockSPI2 = new MockSPI();

		mockSPI2.spiProviderName = name2;
		mockSPI2.failOnDestroy = false;

		_directResigterSPI("spi2", mockSPI2);

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.INFO);

		Assert.assertTrue(
			MPIHelperUtil.unregisterSPIProvider(mockSPIProvider1));
		Assert.assertEquals(2, logRecords.size());

		logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Unregistered SPI " + mockSPI1 +
				" while unregistering SPI provider " + mockSPIProvider1,
			logRecord1.getMessage());

		LogRecord logRecord2 = logRecords.get(1);

		Assert.assertEquals(
			"Unregistered SPI provider " + mockSPIProvider1,
			logRecord2.getMessage());

		// Unregister SPIProvider, with SPI, success, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		Assert.assertTrue(
			MPIHelperUtil.unregisterSPIProvider(mockSPIProvider2));
		Assert.assertTrue(logRecords.isEmpty());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testSPIRegistration() {

		// Mismatch MPI, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.WARNING);

		MockSPI mockSPI1 = new MockSPI();

		mockSPI1.mpi = new MockMPI();

		Assert.assertFalse(MPIHelperUtil.registerSPI(mockSPI1));

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Not registering SPI " + mockSPI1 + " with foreign MPI " +
							mockSPI1.mpi + " versus " + MPIHelperUtil.getMPI(),
			logRecord.getMessage());

		// Mismatch MPI, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		Assert.assertFalse(MPIHelperUtil.registerSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// Null SPIProvider name

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = MPIHelperUtil.getMPI();
		mockSPI1.spiProviderName = null;

		try {
			MPIHelperUtil.registerSPI(mockSPI1);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// No such SPIProvider, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.WARNING);

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = MPIHelperUtil.getMPI();
		mockSPI1.spiProviderName = "name1";

		Assert.assertFalse(MPIHelperUtil.registerSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Not registering SPI " + mockSPI1 + " with unknown SPI provider " +
				mockSPI1.spiProviderName,
			logRecord.getMessage());

		// No such SPIProvider, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = MPIHelperUtil.getMPI();
		mockSPI1.spiProviderName = "name1";

		Assert.assertFalse(MPIHelperUtil.registerSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// Success register, with log

		String name = "name1";

		MockSPIProvider mockSPIProvider = new MockSPIProvider(name);

		Assert.assertTrue(MPIHelperUtil.registerSPIProvider(mockSPIProvider));

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = MPIHelperUtil.getMPI();
		mockSPI1.spiProviderName = name;
		mockSPI1.spiConfiguration = new SPIConfiguration(
			"testId1", "", 8081, "", new String[0], new String[0]);

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.INFO);

		Assert.assertTrue(MPIHelperUtil.registerSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Registered SPI " + mockSPI1, logRecord.getMessage());

		// Success register, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		MockSPI mockSPI2 = new MockSPI();

		mockSPI2.mpi = MPIHelperUtil.getMPI();
		mockSPI2.spiProviderName = name;
		mockSPI2.spiConfiguration = new SPIConfiguration(
			"testId2", "", 8082, "", new String[0], new String[0]);

		Assert.assertTrue(MPIHelperUtil.registerSPI(mockSPI2));
		Assert.assertTrue(logRecords.isEmpty());

		// Duplicate register, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.WARNING);

		Assert.assertFalse(MPIHelperUtil.registerSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Not registering SPI " + mockSPI1 + " because it duplicates " +
				mockSPI1, logRecord.getMessage());

		// Duplicate register, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		Assert.assertFalse(MPIHelperUtil.registerSPI(mockSPI2));
		Assert.assertTrue(logRecords.isEmpty());

		// Bad SPI impl

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = MPIHelperUtil.getMPI();
		mockSPI1.spiProviderName = name;
		mockSPI1.failOnGetConfiguration = true;

		try {
			MPIHelperUtil.registerSPI(mockSPI1);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			Assert.assertSame(RemoteException.class, throwable.getClass());
		}

		// Get SPI, exist

		Assert.assertNotNull(MPIHelperUtil.getSPI(name, "testId1"));

		// Get SPI, not exist

		Assert.assertNull(MPIHelperUtil.getSPI(name, "testId3"));

		// Get SPIs

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.SEVERE);

		mockSPI2.failOnIsAlive = true;

		List<SPI> spis = MPIHelperUtil.getSPIs();

		Assert.assertEquals(1, spis.size());

		mockSPI1 = (MockSPI)spis.get(0);

		Assert.assertEquals(name, mockSPI1.spiProviderName);
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Throwable throwable = logRecord.getThrown();

		Assert.assertSame(RemoteException.class, throwable.getClass());

		// Get SPIs by SPIProvider, exist

		mockSPI2 = new MockSPI();

		mockSPI2.mpi = MPIHelperUtil.getMPI();
		mockSPI2.spiProviderName = name;
		mockSPI2.spiConfiguration = new SPIConfiguration(
			"testId2", "", 8082, "", new String[0], new String[0]);

		Assert.assertTrue(MPIHelperUtil.registerSPI(mockSPI2));

		mockSPI2.failOnIsAlive = true;

		spis = MPIHelperUtil.getSPIs(name);

		Assert.assertEquals(1, spis.size());

		mockSPI1 = (MockSPI)spis.get(0);

		Assert.assertEquals(name, mockSPI1.spiProviderName);

		// Get SPIs by SPIProvider, not exist

		spis = MPIHelperUtil.getSPIs("name2");

		Assert.assertTrue(spis.isEmpty());

		// Unregister MPI mismatch, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.WARNING);

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = new MockMPI();

		Assert.assertFalse(MPIHelperUtil.unregisterSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Not unregistering SPI " + mockSPI1 + " with foreign MPI " +
							mockSPI1.mpi + " versus " + MPIHelperUtil.getMPI(),
			logRecord.getMessage());

		// Unregister MPI mismatch, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		Assert.assertFalse(MPIHelperUtil.unregisterSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// Unregister no such SPIProvider, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.WARNING);

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = MPIHelperUtil.getMPI();
		mockSPI1.spiProviderName = "name2";

		Assert.assertFalse(MPIHelperUtil.unregisterSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Not unregistering SPI " + mockSPI1 +
				" with unknown SPI provider " + mockSPI1.spiProviderName,
			logRecord.getMessage());

		// Unregister no such SPIProvider, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = MPIHelperUtil.getMPI();
		mockSPI1.spiProviderName = "name2";

		Assert.assertFalse(MPIHelperUtil.unregisterSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// Unregister no such SPI, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.WARNING);

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = MPIHelperUtil.getMPI();
		mockSPI1.spiProviderName = name;
		mockSPI1.spiConfiguration = new SPIConfiguration(
			"testId3", "", 8083, "", new String[0], new String[0]);

		Assert.assertFalse(MPIHelperUtil.unregisterSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Not unregistering unregistered SPI " + mockSPI1,
			logRecord.getMessage());

		// Unregister no such SPI, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		mockSPI1 = new MockSPI();

		mockSPI1.mpi = MPIHelperUtil.getMPI();
		mockSPI1.spiProviderName = name;
		mockSPI1.spiConfiguration = new SPIConfiguration(
			"testId3", "", 8083, "", new String[0], new String[0]);

		Assert.assertFalse(MPIHelperUtil.unregisterSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// Unregister success, with log

		mockSPI1 = (MockSPI)MPIHelperUtil.getSPI(name, "testId1");

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.INFO);

		Assert.assertTrue(MPIHelperUtil.unregisterSPI(mockSPI1));
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unregistered SPI " + mockSPI1, logRecord.getMessage());

		// Unregister success, without log

		Assert.assertTrue(MPIHelperUtil.registerSPI(mockSPI1));

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			MPIHelperUtil.class.getName(), Level.OFF);

		Assert.assertTrue(MPIHelperUtil.unregisterSPI(mockSPI1));
		Assert.assertTrue(logRecords.isEmpty());

		// Unregister fail on get Configuration

		mockSPI1.failOnGetConfiguration = true;

		try {
			MPIHelperUtil.unregisterSPI(mockSPI1);

			Assert.fail();
		}
		catch (RuntimeException re) {
			throwable = re.getCause();

			Assert.assertSame(RemoteException.class, throwable.getClass());
		}
	}

	private static Object _createSPIKey(String spiProviderName, String spiId)
		throws Exception {

		Class<?> spiKeyClass = Class.forName(
			MPIHelperUtil.class.getName().concat("$SPIKey"));

		Constructor<?> spiKeyConstructor = spiKeyClass.getConstructor(
			String.class, String.class);

		return spiKeyConstructor.newInstance(spiProviderName, spiId);
	}

	private static Object _directResigterSPI(String spiId, SPI spi)
		throws Exception {

		Field spisField = ReflectionUtil.getDeclaredField(
			MPIHelperUtil.class, "_spis");

		Map<Object, SPI> spis = (Map<Object, SPI>)spisField.get(null);

		Object spiKey = _createSPIKey(spi.getSPIProviderName(), spiId);

		spis.put(spiKey, spi);

		return spiKey;
	}

	private static MPI _getMPIImpl() throws Exception {
		Field mpiImplField = ReflectionUtil.getDeclaredField(
			MPIHelperUtil.class, "_mpiImpl");

		MPI mpiImpl = (MPI)mpiImplField.get(null);

		Assert.assertNotNull(mpiImpl);

		return mpiImpl;
	}

	private static class MockMPI implements MPI {

		@Override
		public boolean isAlive() {
			return true;
		}

	}

	private static class MockSPIProvider implements SPIProvider {

		public MockSPIProvider(String name) {
			_name = name;
		}

		@Override
		public SPI createSPI(SPIConfiguration spiConfiguration) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getName() {
			return _name;
		}

		private String _name;
	}

}