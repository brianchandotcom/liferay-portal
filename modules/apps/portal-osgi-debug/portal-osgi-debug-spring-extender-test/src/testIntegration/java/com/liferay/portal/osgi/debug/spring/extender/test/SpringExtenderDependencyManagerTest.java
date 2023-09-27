/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.debug.spring.extender.test.reference.SpringExtenderTestComponentReference;
import com.liferay.portal.osgi.debug.spring.extender.test.service.impl.SpringExtenderTestComponentLocalServiceImpl;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.util.Dictionary;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

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
				StringPool.QUESTION);

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

		try (LogCapture logCapture = _configureLog4JLogger()) {
			_captureLog(logCapture);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message = logEntry.getMessage();

			Assert.assertEquals(
				message,
				"All Spring extender dependency manager components are " +
					"registered",
				message);

			Assert.assertEquals(LoggerTestUtil.INFO, logEntry.getPriority());

			logEntry = logEntries.get(1);

			message = logEntry.getMessage();

			Assert.assertEquals(
				message, "Stopped scanning for unavailable components",
				message);

			Assert.assertEquals(LoggerTestUtil.INFO, logEntry.getPriority());
		}
	}

	@Test
	public void testSpringExtenderDependencyManagerUnresolvedDependencies()
		throws Exception {

		Bundle bundle = _bundleContext.installBundle(
			"location", _createBundle());

		bundle.start();

		try (LogCapture logCapture = _configureLog4JLogger()) {
			_captureLog(logCapture);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message = logEntry.getMessage();

			StringBundler sb = new StringBundler(5);

			sb.append("is unavailable due to missing required dependencies:");

			if (SanitizerLogWrapper.isEnabled()) {
				sb.append(StringPool.UNDERLINE);
			}
			else {
				sb.append(StringPool.NEW_LINE);
			}

			sb.append("\t\tservice dependency [");
			sb.append(_CLASS_NAME_SPRING_EXTENDER_TEST_COMPONENT_REFERENCE);
			sb.append("]");

			Assert.assertTrue(message, message.contains(sb.toString()));

			Assert.assertEquals(LoggerTestUtil.WARN, logEntry.getPriority());

			logEntry = logEntries.get(1);

			message = logEntry.getMessage();

			Assert.assertEquals(
				message, "Stopped scanning for unavailable components",
				message);

			Assert.assertEquals(LoggerTestUtil.INFO, logEntry.getPriority());
		}
		finally {
			bundle.uninstall();
		}
	}

	private static void _captureLog(LogCapture logCapture) throws Exception {
		AtomicReference<Thread> scanningThreadReference =
			new AtomicReference<>();

		CountDownLatch countDownLatch = new CountDownLatch(1);

		ReflectionTestUtil.setFieldValue(
			logCapture, "_logEntries",
			new CopyOnWriteArrayList<LogEntry>() {

				@Override
				public boolean add(LogEntry logEntry) {
					boolean added = super.add(logEntry);

					if (Objects.equals(
							logEntry.getMessage(),
							"Stopped scanning for unavailable components")) {

						return added;
					}

					try {
						_unavailableComponentScannerConfiguration.update(
							new HashMapDictionary<String, Object>());
					}
					catch (IOException ioException) {
						ReflectionUtil.throwException(ioException);
					}

					Thread thread = Thread.currentThread();

					while (!thread.isInterrupted()) {
					}

					scanningThreadReference.set(Thread.currentThread());

					countDownLatch.countDown();

					return added;
				}

			});

		_unavailableComponentScannerConfiguration.update(
			HashMapDictionaryBuilder.<String, Object>put(
				"unavailableComponentScanningInterval", "1"
			).build());

		Assert.assertTrue(
			"Scanning thread did not send a response",
			countDownLatch.await(30, TimeUnit.SECONDS));

		Thread scanningThread = scanningThreadReference.get();

		scanningThread.join();
	}

	private static LogCapture _configureLog4JLogger() {
		return LoggerTestUtil.configureLog4JLogger(
			"com.liferay.portal.osgi.debug.spring.extender.internal." +
				"UnavailableComponentScanner",
			LoggerTestUtil.INFO);
	}

	private static void _ensureStopScanning() throws Exception {
		try (LogCapture logCapture = _configureLog4JLogger()) {
			_captureLog(logCapture);
		}
	}

	private InputStream _createBundle() throws Exception {
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
					_CLASS_NAME_SPRING_EXTENDER_TEST_COMPONENT_REFERENCE.
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
		_CLASS_NAME_SPRING_EXTENDER_TEST_COMPONENT_REFERENCE =
			SpringExtenderTestComponentReference.class.getName();

	private static BundleContext _bundleContext;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static Dictionary<String, Object> _properties;
	private static Configuration _unavailableComponentScannerConfiguration;

}