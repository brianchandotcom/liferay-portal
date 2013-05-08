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

package com.liferay.portal.resiliency.spi;

import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
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
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class SPIRegistryImplTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {
		_spiRegistryImpl = new SPIRegistryImpl();

		_excludedPortletIds = _getExcludedPortletIds(_spiRegistryImpl);
		_portletIds = _getPortletIds(_spiRegistryImpl);
		_portletSPIs = _getPortletSPIs(_spiRegistryImpl);
	}

	@Test
	public void testExcludedPortletIds() {
		Assert.assertSame(
			_excludedPortletIds, _spiRegistryImpl.getExcludedPortletIds());

		String portlet1 = "portlet1";

		_spiRegistryImpl.addExcludedPortletId(portlet1);

		Assert.assertEquals(1, _excludedPortletIds.size());
		Assert.assertTrue(_excludedPortletIds.contains(portlet1));

		String portlet2 = "portlet2";

		SPI spi = new MockSPI();

		_portletSPIs.put(portlet2, spi);

		Assert.assertNull(_spiRegistryImpl.getPortletSPI(portlet1));
		Assert.assertSame(spi, _spiRegistryImpl.getPortletSPI(portlet2));

		_spiRegistryImpl.removeExcludedPortletId(portlet1);

		Assert.assertTrue(_excludedPortletIds.isEmpty());
	}

	@AdviseWith(adviceClasses = {PortletLocalServiceUtilAdvice.class})
	@Test
	public void testRegistration() throws Exception {

		// With log

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

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIRegistryImpl.class.getName(), Level.WARNING);

		_spiRegistryImpl.registerSPI(mockSPI);

		Assert.assertEquals(3, _portletSPIs.size());
		Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet1"));
		Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet3"));
		Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet4"));
		Assert.assertSame(
			mockSPI, _spiRegistryImpl.getServletContextSPI("portletApp1"));
		Assert.assertSame(
			mockSPI, _spiRegistryImpl.getServletContextSPI("portletApp2"));
		Assert.assertNull(_spiRegistryImpl.getServletContextSPI("portletApp3"));

		List<String> portletIds = Arrays.asList(_portletIds.remove(mockSPI));

		Assert.assertTrue(portletIds.contains("portlet1"));
		Assert.assertTrue(portletIds.contains("portlet3"));
		Assert.assertTrue(portletIds.contains("portlet4"));

		Assert.assertEquals(2, logRecords.size());

		LogRecord logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Skip unknown portlet id portlet2", logRecord1.getMessage());

		LogRecord logRecord2 = logRecords.get(1);

		Assert.assertEquals(
			"Skip unknown servlet context name portletApp2",
			logRecord2.getMessage());

		// Without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIRegistryImpl.class.getName(), Level.OFF);

		_spiRegistryImpl.registerSPI(mockSPI);

		Assert.assertEquals(3, _portletSPIs.size());
		Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet1"));
		Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet3"));
		Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet4"));

		portletIds = Arrays.asList(_portletIds.remove(mockSPI));

		Assert.assertTrue(portletIds.contains("portlet1"));
		Assert.assertTrue(portletIds.contains("portlet3"));
		Assert.assertTrue(portletIds.contains("portlet4"));

		Assert.assertTrue(logRecords.isEmpty());

		// Hash failure

		throwException.set(true);

		try {
			_spiRegistryImpl.registerSPI(mockSPI);

			Assert.fail();
		}
		catch (RuntimeException re) {
		}

		_portletSPIs.clear();

		// Unregister, normal

		throwException.set(false);

		_spiRegistryImpl.registerSPI(mockSPI);

		_spiRegistryImpl.unregisterSPI(mockSPI);

		Assert.assertTrue(_portletIds.isEmpty());
		Assert.assertTrue(_portletSPIs.isEmpty());

		// Unregister, again

		_spiRegistryImpl.unregisterSPI(mockSPI);

		Assert.assertTrue(_portletIds.isEmpty());
		Assert.assertTrue(_portletSPIs.isEmpty());

		// Hash failure

		throwException.set(true);

		try {
			_spiRegistryImpl.unregisterSPI(mockSPI);

			Assert.fail();
		}
		catch (RuntimeException re) {
		}
	}

	@Aspect
	public static class PortletLocalServiceUtilAdvice {

		@Around(
			"execution(public static com.liferay.portal.model.Portlet com." +
				"liferay.portal.service.PortletLocalServiceUtil." +
					"getPortletById(String)) && args(portletId)")
		public Portlet getPortletById(String portletId) {
			if (portletId.equals("portlet1")) {
				return _createPortletProxy(portletId);
			}

			return null;
		}

		@Around(
			"execution(public static com.liferay.portal.model.PortletApp " +
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
			PortletApp.class.getClassLoader(),
			new Class<?>[] {PortletApp.class},
			new InvocationHandler() {

				@Override
				public Object invoke(
					Object proxy, Method method, Object[] args) {

					String methodName = method.getName();

					if (methodName.equals("getPortlets")) {
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
			Portlet.class.getClassLoader(), new Class<?>[] {Portlet.class},
			new InvocationHandler() {

				@Override
				public Object invoke(
					Object proxy, Method method, Object[] args) {

					String methodName = method.getName();

					if (methodName.equals("getPortletId")) {
						return portletId;
					}

					throw new UnsupportedOperationException();
				}

			});
	}

	private static Set<String> _getExcludedPortletIds(
			SPIRegistryImpl spiRegistryImpl)
		throws Exception {

		Field excludedPortletIdsField = ReflectionUtil.getDeclaredField(
			SPIRegistryImpl.class, "_excludedPortletIds");

		return (Set<String>)excludedPortletIdsField.get(spiRegistryImpl);
	}

	private static Map<SPI, String[]> _getPortletIds(
			SPIRegistryImpl spiRegistryImpl)
		throws Exception {

		Field portletIdsField = ReflectionUtil.getDeclaredField(
			SPIRegistryImpl.class, "_portletIds");

		return (Map<SPI, String[]>)portletIdsField.get(spiRegistryImpl);
	}

	private static Map<String, SPI> _getPortletSPIs(
			SPIRegistryImpl spiRegistryImpl)
		throws Exception {

		Field portletSPIsField = ReflectionUtil.getDeclaredField(
			SPIRegistryImpl.class, "_portletSPIs");

		return (Map<String, SPI>)portletSPIsField.get(spiRegistryImpl);
	}

	private Set<String> _excludedPortletIds;
	private Map<SPI, String[]> _portletIds;
	private Map<String, SPI> _portletSPIs;
	private SPIRegistryImpl _spiRegistryImpl;

}