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

package com.liferay.portal.resiliency.spi;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.rmi.RemoteException;

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
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SPIRegistryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, AspectJNewEnvTestRule.INSTANCE);

	@Before
	public void setUp() {
		_spiRegistryImpl = new SPIRegistryImpl();

		_spiRegistryImpl.setErrorSPI(new ErrorSPI());

		SPIRegistryUtil spiRegistryUtil = new SPIRegistryUtil();

		spiRegistryUtil.setSPIRegistry(_spiRegistryImpl);

		_excludedPortletIds = ReflectionTestUtil.getFieldValue(
			_spiRegistryImpl, "_excludedPortletIds");
		_portletIds = ReflectionTestUtil.getFieldValue(
			_spiRegistryImpl, "_portletIds");
		_portletSPIs = ReflectionTestUtil.getFieldValue(
			_spiRegistryImpl, "_portletSPIs");
	}

	@Test
	public void testExcludedPortletIds() {
		Assert.assertSame(
			_excludedPortletIds, _spiRegistryImpl.getExcludedPortletIds());

		String portlet1 = "portlet1";

		_spiRegistryImpl.addExcludedPortletId(portlet1);

		Assert.assertEquals(
			_excludedPortletIds.toString(), 1, _excludedPortletIds.size());
		Assert.assertTrue(_excludedPortletIds.contains(portlet1));

		String portlet2 = "portlet2";

		SPI spi = new MockSPI();

		_portletSPIs.put(portlet2, spi);

		Assert.assertNull(_spiRegistryImpl.getPortletSPI(portlet1));
		Assert.assertSame(spi, _spiRegistryImpl.getPortletSPI(portlet2));

		_spiRegistryImpl.setSPIRegistryValidator(
			new MockSPIRegistryValidator());

		Assert.assertNull(_spiRegistryImpl.getPortletSPI(portlet1));
		Assert.assertSame(
			_spiRegistryImpl.getErrorSPI(),
			_spiRegistryImpl.getPortletSPI(portlet2));

		_spiRegistryImpl.setSPIRegistryValidator(null);

		_spiRegistryImpl.removeExcludedPortletId(portlet1);

		Assert.assertTrue(_excludedPortletIds.isEmpty());
	}

	@AdviseWith(adviceClasses = {PortletLocalServiceUtilAdvice.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testRegistration() throws RemoteException {
		PortletLocalServiceUtilAdvice._portletIds = Arrays.asList(
			"portlet3", "portlet4");

		SPIConfiguration spiConfiguration = new SPIConfiguration(
			"", "", 8081, "", new String[] {"portlet1", "portlet2"},
			new String[] {"portletApp1", "portletApp2"}, null);

		final AtomicBoolean throwException = new AtomicBoolean();

		MockSPI mockSPI = new MockSPI() {

			@Override
			public boolean equals(Object object) {
				return super.equals(object);
			}

			@Override
			public int hashCode() {
				if (throwException.get()) {
					throw new RuntimeException();
				}

				return super.hashCode();
			}

		};

		mockSPI.spiConfiguration = spiConfiguration;

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					SPIRegistryImpl.class.getName(), Level.WARNING)) {

			// With log

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			_spiRegistryImpl.registerSPI(mockSPI);

			Assert.assertEquals(
				_portletSPIs.toString(), 3, _portletSPIs.size());
			Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet1"));
			Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet3"));
			Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet4"));
			Assert.assertSame(
				mockSPI, _spiRegistryImpl.getServletContextSPI("portletApp1"));
			Assert.assertSame(
				mockSPI, _spiRegistryImpl.getServletContextSPI("portletApp2"));
			Assert.assertNull(
				_spiRegistryImpl.getServletContextSPI("portletApp3"));

			_spiRegistryImpl.setSPIRegistryValidator(
				new MockSPIRegistryValidator());

			Assert.assertSame(
				_spiRegistryImpl.getErrorSPI(),
				_spiRegistryImpl.getServletContextSPI("portletApp1"));
			Assert.assertSame(
				_spiRegistryImpl.getErrorSPI(),
				_spiRegistryImpl.getServletContextSPI("portletApp2"));
			Assert.assertNull(
				_spiRegistryImpl.getServletContextSPI("portletApp3"));

			_spiRegistryImpl.setSPIRegistryValidator(null);

			List<String> portletIds = Arrays.asList(
				_portletIds.remove(mockSPI));

			Assert.assertTrue(portletIds.contains("portlet1"));
			Assert.assertTrue(portletIds.contains("portlet3"));
			Assert.assertTrue(portletIds.contains("portlet4"));

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			LogRecord logRecord1 = logRecords.get(0);

			Assert.assertEquals(
				"Skip unknown portlet id portlet2", logRecord1.getMessage());

			LogRecord logRecord2 = logRecords.get(1);

			Assert.assertEquals(
				"Skip unknown servlet context name portletApp2",
				logRecord2.getMessage());

			// Without log

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			_spiRegistryImpl.registerSPI(mockSPI);

			Assert.assertEquals(
				_portletSPIs.toString(), 3, _portletSPIs.size());
			Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet1"));
			Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet3"));
			Assert.assertEquals(mockSPI, _portletSPIs.remove("portlet4"));

			portletIds = Arrays.asList(_portletIds.remove(mockSPI));

			Assert.assertTrue(portletIds.contains("portlet1"));
			Assert.assertTrue(portletIds.contains("portlet3"));
			Assert.assertTrue(portletIds.contains("portlet4"));

			Assert.assertTrue(logRecords.isEmpty());

			// Hash failure

			logRecords = captureHandler.resetLogLevel(Level.WARNING);

			throwException.set(true);

			try {
				_spiRegistryImpl.registerSPI(mockSPI);

				Assert.fail();
			}
			catch (RuntimeException re) {
			}

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			logRecord1 = logRecords.get(0);

			Assert.assertEquals(
				"Skip unknown portlet id portlet2", logRecord1.getMessage());

			logRecord2 = logRecords.get(1);

			Assert.assertEquals(
				"Skip unknown servlet context name portletApp2",
				logRecord2.getMessage());

			_portletSPIs.clear();

			// Unregister, normal

			logRecords = captureHandler.resetLogLevel(Level.WARNING);

			throwException.set(false);

			_spiRegistryImpl.registerSPI(mockSPI);

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			logRecord1 = logRecords.get(0);

			Assert.assertEquals(
				"Skip unknown portlet id portlet2", logRecord1.getMessage());

			logRecord2 = logRecords.get(1);

			Assert.assertEquals(
				"Skip unknown servlet context name portletApp2",
				logRecord2.getMessage());
		}

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
			"execution(public static com.liferay.portal.kernel.model.PortletApp " +
				"com.liferay.portal.kernel.service.PortletLocalServiceUtil." +
					"getPortletApp(String)) && args(servletContextName)"
		)
		public PortletApp getPortletApp(String servletContextName) {
			if (servletContextName.equals("portletApp1")) {
				return _createPortletAppProxy(_portletIds);
			}

			return null;
		}

		@Around(
			"execution(public static com.liferay.portal.kernel.model.Portlet " +
				"com.liferay.portal.kernel.service.PortletLocalServiceUtil." +
					"getPortletById(String)) && args(portletId)"
		)
		public Portlet getPortletById(String portletId) {
			if (portletId.equals("portlet1")) {
				return _createPortletProxy(portletId);
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
						List<Portlet> portlets = new ArrayList<>(
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

	private Set<String> _excludedPortletIds;
	private Map<SPI, String[]> _portletIds;
	private Map<String, SPI> _portletSPIs;
	private SPIRegistryImpl _spiRegistryImpl;

}