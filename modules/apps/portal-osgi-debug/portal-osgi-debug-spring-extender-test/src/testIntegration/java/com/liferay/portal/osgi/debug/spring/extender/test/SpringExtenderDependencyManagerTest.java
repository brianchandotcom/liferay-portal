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

package com.liferay.portal.osgi.debug.spring.extender.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.SanitizerLogWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.debug.spring.extender.test.reference.SpringExtenderTestComponentReference;
import com.liferay.portal.osgi.debug.spring.extender.test.service.impl.SpringExtenderTestComponentLocalServiceImpl;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.util.Dictionary;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class SpringExtenderDependencyManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			SpringExtenderDependencyManagerTest.class);

		_bundleContext = bundle.getBundleContext();

		_unavailableComponentScannerConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.portal.osgi.debug.spring.extender.internal." +
					"configuration.UnavailableComponentScannerConfiguration",
				null);

		_properties = _unavailableComponentScannerConfiguration.getProperties();

		_ensureStopScanning();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_properties == null) {
			_unavailableComponentScannerConfiguration.delete();
		}
		else {
			_unavailableComponentScannerConfiguration.update(_properties);
		}
	}

	@Test
	public void testSpringExtenderDependencyManagerResolvedDependencies()
		throws Exception {

		try (CaptureAppender captureAppender = _configureLog4JLogger()) {
			_captureLog(captureAppender);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 2, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			String message = (String)loggingEvent.getMessage();

			Assert.assertEquals(
				message,
				"All Spring extender dependency manager components are " +
					"registered",
				message);

			Level level = loggingEvent.getLevel();

			Assert.assertEquals(level.toString(), Level.INFO, level);

			loggingEvent = loggingEvents.get(1);

			message = (String)loggingEvent.getMessage();

			Assert.assertEquals(
				message, "Stopped scanning for unavailable components",
				message);

			level = loggingEvent.getLevel();

			Assert.assertEquals(level.toString(), Level.INFO, level);
		}
	}

	@Test
	public void testSpringExtenderDependencyManagerUnresolvedDependencies()
		throws Exception {

		Bundle bundle = _bundleContext.installBundle(
			"location", _createBundle());

		bundle.start();

		try (CaptureAppender captureAppender = _configureLog4JLogger()) {
			_captureLog(captureAppender);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 2, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			String message = (String)loggingEvent.getMessage();

			StringBundler sb = new StringBundler(5);

			sb.append("is unavailable due to missing required dependencies:");

			if (SanitizerLogWrapper.isEnabled()) {
				sb.append(StringPool.UNDERLINE);
			}
			else {
				sb.append(StringPool.NEW_LINE);
			}

			sb.append("\t\tservice dependency [");
			sb.append(_SPRING_EXTENDER_TEST_COMPONENT_REFERENCE_CLASS_NAME);
			sb.append("]");

			Assert.assertTrue(message, message.contains(sb.toString()));

			Level level = loggingEvent.getLevel();

			Assert.assertEquals(
				level.toString(), Level.WARN, loggingEvent.getLevel());

			loggingEvent = loggingEvents.get(1);

			message = (String)loggingEvent.getMessage();

			Assert.assertEquals(
				message, "Stopped scanning for unavailable components",
				message);

			level = loggingEvent.getLevel();

			Assert.assertEquals(level.toString(), Level.INFO, level);
		}
		finally {
			bundle.uninstall();
		}
	}

	private static void _captureLog(CaptureAppender captureAppender)
		throws Exception {

		AtomicReference<Thread> scanningThreadReference =
			new AtomicReference<>();

		CountDownLatch countDownLatch = new CountDownLatch(1);

		ReflectionTestUtil.setFieldValue(
			captureAppender, "_loggingEvents",
			new CopyOnWriteArrayList<LoggingEvent>() {

				@Override
				public boolean add(LoggingEvent loggingEvent) {
					boolean added = super.add(loggingEvent);

					if (Objects.equals(
							loggingEvent.getMessage(),
							"Stopped scanning for unavailable components")) {

						return added;
					}

					try {
						_unavailableComponentScannerConfiguration.update(
							new HashMapDictionary<String, Object>());
					}
					catch (IOException ioe) {
						ReflectionUtil.throwException(ioe);
					}

					Thread thread = Thread.currentThread();

					while (!thread.isInterrupted()) {
					}

					scanningThreadReference.set(Thread.currentThread());

					countDownLatch.countDown();

					return added;
				}

			});

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("unavailableComponentScanningInterval", "1");

		_unavailableComponentScannerConfiguration.update(properties);

		countDownLatch.await();

		Thread scanningThread = scanningThreadReference.get();

		scanningThread.join();
	}

	private static CaptureAppender _configureLog4JLogger() {
		return Log4JLoggerTestUtil.configureLog4JLogger(
			"com.liferay.portal.osgi.debug.spring.extender.internal." +
				"UnavailableComponentScanner",
			Level.INFO);
	}

	private static void _ensureStopScanning() throws Exception {
		try (CaptureAppender captureAppender = _configureLog4JLogger()) {
			_captureLog(captureAppender);
		}
	}

	private InputStream _createBundle() throws IOException {
		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME,
					"com.liferay.portal.osgi.debug.spring.extender.test." +
						"bundle");
				attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
				attributes.putValue("Manifest-Version", "1.0");
				attributes.putValue("Liferay-Service", Boolean.TRUE.toString());
				attributes.putValue("Liferay-Spring-Context", null);

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();

				_writeClasses(
					jarOutputStream, SpringExtenderTestComponentReference.class,
					SpringExtenderTestComponentLocalServiceImpl.class);

				jarOutputStream.putNextEntry(
					new ZipEntry("OSGI-INF/context/context.dependencies"));

				jarOutputStream.write(
					_SPRING_EXTENDER_TEST_COMPONENT_REFERENCE_CLASS_NAME.
						getBytes());

				jarOutputStream.closeEntry();
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private void _writeClasses(
			JarOutputStream jarOutputStream, Class<?>... classes)
		throws IOException {

		for (Class<?> clazz : classes) {
			String className = clazz.getName();

			String path = StringUtil.replace(
				className, CharPool.PERIOD, CharPool.SLASH);

			String resourcePath = path.concat(".class");

			jarOutputStream.putNextEntry(new ZipEntry(resourcePath));

			ClassLoader classLoader = clazz.getClassLoader();

			StreamUtil.transfer(
				classLoader.getResourceAsStream(resourcePath), jarOutputStream,
				false);

			jarOutputStream.closeEntry();
		}
	}

	private static final String
		_SPRING_EXTENDER_TEST_COMPONENT_REFERENCE_CLASS_NAME =
			SpringExtenderTestComponentReference.class.getName();

	private static BundleContext _bundleContext;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static Dictionary<String, Object> _properties;
	private static Configuration _unavailableComponentScannerConfiguration;

}