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

package com.liferay.portal.osgi.debug.declarative.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.debug.declarative.service.test.component.DeclarativeServiceTestComponent;
import com.liferay.portal.osgi.debug.declarative.service.test.reference.DeclarativeServiceTestReference;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.util.Dictionary;
import java.util.List;
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
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class DeclarativeServiceDependencyManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			DeclarativeServiceDependencyManagerTest.class);

		_bundleContext = bundle.getBundleContext();

		_unsatisfiedComponentScannerConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.portal.osgi.debug.declarative.service.internal." +
					"configuration.UnsatisfiedComponentScannerConfiguration",
				"?");

		_properties = _unsatisfiedComponentScannerConfiguration.getProperties();

		_ensureStopScanning();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_properties == null) {
			_unsatisfiedComponentScannerConfiguration.delete();
		}
		else {
			_unsatisfiedComponentScannerConfiguration.update(_properties);
		}
	}

	@Test
	public void testDeclarativeServiceDependencyManagerResolvedDependencies()
		throws Exception {

		try (CaptureAppender captureAppender = _configureLog4JLogger()) {
			_captureLog(captureAppender);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 2, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"All declarative service components are satisfied",
				loggingEvent.getMessage());
			Assert.assertEquals(Level.INFO, loggingEvent.getLevel());

			loggingEvent = loggingEvents.get(1);

			Assert.assertEquals(
				"Stopped scanning for unsatisfied declarative service " +
					"components",
				loggingEvent.getMessage());
			Assert.assertEquals(Level.INFO, loggingEvent.getLevel());
		}
	}

	@Test
	public void testDeclarativeServiceDependencyManagerUnresolvedDependencies()
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

			message = message.replaceAll("\\s", "");
			message = message.replaceAll("\\n", "");
			message = message.replaceAll("_", "");

			StringBundler sb = new StringBundler(4);

			sb.append("name: ");
			sb.append(DeclarativeServiceTestComponent.class.getName());
			sb.append(", unsatisfied references: {name: ");
			sb.append("declarativeServiceTestReference, target: null}");

			String s = sb.toString();

			Assert.assertTrue(
				message, message.contains(s.replaceAll("\\s", "")));

			Assert.assertEquals(Level.WARN, loggingEvent.getLevel());

			loggingEvent = loggingEvents.get(1);

			Assert.assertEquals(
				"Stopped scanning for unsatisfied declarative service " +
					"components",
				loggingEvent.getMessage());
			Assert.assertEquals(Level.INFO, loggingEvent.getLevel());
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

					String message = String.valueOf(loggingEvent.getMessage());

					if (message.equals(
							"Stopped scanning for unsatisfied declarative " +
								"service components")) {

						return added;
					}

					try {
						_unsatisfiedComponentScannerConfiguration.update(
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

		properties.put("unsatisfiedComponentScanningInterval", 1);

		_unsatisfiedComponentScannerConfiguration.update(properties);

		countDownLatch.await();

		Thread scanningThread = scanningThreadReference.get();

		scanningThread.join();
	}

	private static CaptureAppender _configureLog4JLogger() {
		return Log4JLoggerTestUtil.configureLog4JLogger(
			"com.liferay.portal.osgi.debug.declarative.service.internal." +
				"UnsatisfiedComponentScanner",
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
					"com.liferay.portal.osgi.debug.declarative.service.test." +
						"bundle");
				attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
				attributes.putValue("Manifest-Version", "1.0");
				attributes.putValue(
					"Service-Component",
					"OSGI-INF/" + _TEST_COMPONENT_FILE_NAME);

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();

				_writeClasses(
					jarOutputStream, DeclarativeServiceTestComponent.class,
					DeclarativeServiceTestReference.class);

				jarOutputStream.putNextEntry(
					new ZipEntry("OSGI-INF/" + _TEST_COMPONENT_FILE_NAME));

				_writeServiceComponentFile(jarOutputStream, getClass());

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

	private void _writeServiceComponentFile(
			JarOutputStream jarOutputStream, Class<?> clazz)
		throws IOException {

		ClassLoader classLoader = clazz.getClassLoader();

		Package pkg = clazz.getPackage();

		String packagePath = StringUtil.replace(
			pkg.getName(), CharPool.PERIOD, CharPool.SLASH);

		StreamUtil.transfer(
			classLoader.getResourceAsStream(
				packagePath.concat(
					"/dependencies/"
				).concat(
					_TEST_COMPONENT_FILE_NAME
				)),
			jarOutputStream, false);
	}

	private static final String _TEST_COMPONENT_FILE_NAME =
		"DeclarativeServiceTestComponent.xml";

	private static BundleContext _bundleContext;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static Dictionary<String, Object> _properties;
	private static Configuration _unsatisfiedComponentScannerConfiguration;

}