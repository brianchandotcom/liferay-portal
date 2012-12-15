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

package com.liferay.portal.resiliency.spi;

import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.LogRecord;

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
public class SPIRuntimeMappingImplTest {

	@Before
	public void setUp() throws Exception {
		_spiRuntimeMappingImpl = new SPIRuntimeMappingImpl();
		_portletIdToSPIMap = _getPortletIdToSPIMap(_spiRuntimeMappingImpl);
		_skipMappingPortletIds = _getSkipMappingPortletIds(
			_spiRuntimeMappingImpl);
		_spiToPortletIdsMap = _getSPIToPortletIdsMap(_spiRuntimeMappingImpl);
	}

	@AdviseWith(adviceClasses = {PortletLocalServiceUtilAdvice.class})
	@Test
	public void testRegisteration() throws Exception {
		PortletLocalServiceUtilAdvice._portletIds = Arrays.asList(
			"portlet3", "portlet4");

		SPIConfiguration spiConfiguration = new SPIConfiguration(
			"", "", 8081, "", new String[] {"portlet1", "portlet2"},
			new String[] {"portletApp1", "portletApp2"});

		final AtomicBoolean throwException = new AtomicBoolean();

		MockSPI mockSPI = new MockSPI() {

			@Override
			public int hashCode() {
				if (throwException.get()) {
					throw new RuntimeException();
				}

				return super.hashCode();
			}

		};

		mockSPI.spiConfiguration = spiConfiguration;

		// 1) With log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIRuntimeMappingImpl.class.getName(), Level.WARNING);

		_spiRuntimeMappingImpl.register(mockSPI);

		Assert.assertEquals(3, _portletIdToSPIMap.size());
		Assert.assertEquals(mockSPI, _portletIdToSPIMap.remove("portlet1"));
		Assert.assertEquals(mockSPI, _portletIdToSPIMap.remove("portlet3"));
		Assert.assertEquals(mockSPI, _portletIdToSPIMap.remove("portlet4"));

		List<String> portletIds = Arrays.asList(
			_spiToPortletIdsMap.remove(mockSPI));

		Assert.assertTrue(portletIds.contains("portlet1"));
		Assert.assertTrue(portletIds.contains("portlet3"));
		Assert.assertTrue(portletIds.contains("portlet4"));

		Assert.assertEquals(2, logRecords.size());

		LogRecord logRecord1 = logRecords.get(0);
		LogRecord logRecord2 = logRecords.get(1);

		Assert.assertEquals(
			"Skip unknown core portlet id portlet2", logRecord1.getMessage());
		Assert.assertEquals(
			"Skip unknow plugin servlet context name portletApp2",
			logRecord2.getMessage());

		// 2) Without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIRuntimeMappingImpl.class.getName(), Level.OFF);

		_spiRuntimeMappingImpl.register(mockSPI);

		Assert.assertEquals(3, _portletIdToSPIMap.size());
		Assert.assertEquals(mockSPI, _portletIdToSPIMap.remove("portlet1"));
		Assert.assertEquals(mockSPI, _portletIdToSPIMap.remove("portlet3"));
		Assert.assertEquals(mockSPI, _portletIdToSPIMap.remove("portlet4"));

		portletIds = Arrays.asList(_spiToPortletIdsMap.remove(mockSPI));

		Assert.assertTrue(portletIds.contains("portlet1"));
		Assert.assertTrue(portletIds.contains("portlet3"));
		Assert.assertTrue(portletIds.contains("portlet4"));

		Assert.assertTrue(logRecords.isEmpty());

		// 3) Hash failure

		throwException.set(true);

		try {
			_spiRuntimeMappingImpl.register(mockSPI);

			Assert.fail();
		}
		catch (RuntimeException re) {
		}

		_portletIdToSPIMap.clear();

		// 4) Unregister, normal

		throwException.set(false);

		_spiRuntimeMappingImpl.register(mockSPI);

		_spiRuntimeMappingImpl.unregister(mockSPI);

		Assert.assertTrue(_spiToPortletIdsMap.isEmpty());
		Assert.assertTrue(_portletIdToSPIMap.isEmpty());

		// 5) Unregister, duplicate

		_spiRuntimeMappingImpl.unregister(mockSPI);

		Assert.assertTrue(_spiToPortletIdsMap.isEmpty());
		Assert.assertTrue(_portletIdToSPIMap.isEmpty());

		// 6) Hash failure

		throwException.set(true);

		try {
			_spiRuntimeMappingImpl.unregister(mockSPI);

			Assert.fail();
		}
		catch (RuntimeException re) {
		}
	}

	@Test
	public void testSkipMappingPortletIds() {
		Assert.assertSame(
			_skipMappingPortletIds,
			_spiRuntimeMappingImpl.getSkipMappingPortletIds());

		String portlet1 = "portlet1";

		_spiRuntimeMappingImpl.addSkipMappingPortletId(portlet1);

		Assert.assertEquals(1, _skipMappingPortletIds.size());
		Assert.assertTrue(_skipMappingPortletIds.contains(portlet1));

		String portlet2 = "portlet2";
		SPI spi = new MockSPI();

		_portletIdToSPIMap.put(portlet2, spi);

		Assert.assertNull(
			_spiRuntimeMappingImpl.getMappingSPIForPortlet(portlet1));
		Assert.assertSame(
			spi, _spiRuntimeMappingImpl.getMappingSPIForPortlet(portlet2));

		_spiRuntimeMappingImpl.removeSkipMappingPortletId(portlet1);

		Assert.assertTrue(_skipMappingPortletIds.isEmpty());
	}

	@Aspect
	public static class PortletLocalServiceUtilAdvice {

		@Around("execution(public static com.liferay.portal.model.Portlet " +
			"com.liferay.portal.service.PortletLocalServiceUtil." +
				"getPortletById(String)) && args(portletId)")
		public Portlet getPortletById(String portletId) {
			if (portletId.equals("portlet1")) {
				return _createPortletProxy(portletId);
			}

			return null;
		}

		@Around("execution(public static com.liferay.portal.model.PortletApp " +
			"com.liferay.portal.service.PortletLocalServiceUtil." +
				"getPortletApp(String)) && args(servletContextName)")
		public PortletApp getPortletApp(String servletContextName) {
			if (servletContextName.equals("portletApp1")) {
				return _createPortletAppProxy(_portletIds);
			}

			return null;
		}

		private static List<String> _portletIds;

	}

		private static PortletApp _createPortletAppProxy(
		final List<String> portletIds) {

		return (PortletApp)ProxyUtil.newProxyInstance(
			PortletApp.class.getClassLoader(), new Class<?>[]{PortletApp.class},
			new InvocationHandler() {

				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					if (method.getName().equals("getPortlets")) {
						List<Portlet> portlets = new ArrayList<Portlet>(
							portletIds.size());

						for (String portletId : portletIds) {
							portlets.add(_createPortletProxy(portletId));
						}

						return portlets;
					}

					throw new UnsupportedOperationException();
				}
			});
	}

	private static Portlet _createPortletProxy(final String portletId) {
		return (Portlet)ProxyUtil.newProxyInstance(
			Portlet.class.getClassLoader(), new Class<?>[]{Portlet.class},
			new InvocationHandler() {

				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					if (method.getName().equals("getPortletId")) {
						return portletId;
					}

					throw new UnsupportedOperationException();
				}
			});
	}

	private static Map<String, SPI> _getPortletIdToSPIMap(
			SPIRuntimeMappingImpl spiRuntimeMappingImpl)
		throws Exception {

		Field portletIdToSPIMapField = ReflectionUtil.getDeclaredField(
			SPIRuntimeMappingImpl.class, "_portletIdToSPIMap");

		return (Map<String, SPI>)portletIdToSPIMapField.get(
			spiRuntimeMappingImpl);
	}

	private static Set<String> _getSkipMappingPortletIds(
			SPIRuntimeMappingImpl spiRuntimeMappingImpl)
		throws Exception {

		Field skipMappingPortletIdsField = ReflectionUtil.getDeclaredField(
			SPIRuntimeMappingImpl.class, "_skipMappingPortletIds");

		return (Set<String>)skipMappingPortletIdsField.get(
			spiRuntimeMappingImpl);
	}

	private static Map<SPI, String[]> _getSPIToPortletIdsMap(
			SPIRuntimeMappingImpl spiRuntimeMappingImpl)
		throws Exception {

		Field spiToPortletIdsMapField = ReflectionUtil.getDeclaredField(
			SPIRuntimeMappingImpl.class, "_spiToPortletIdsMap");

		return (Map<SPI, String[]>)spiToPortletIdsMapField.get(
			spiRuntimeMappingImpl);
	}

	private Map<String, SPI> _portletIdToSPIMap;
	private Set<String> _skipMappingPortletIds;
	private SPIRuntimeMappingImpl _spiRuntimeMappingImpl;
	private Map<SPI, String[]> _spiToPortletIdsMap;

}