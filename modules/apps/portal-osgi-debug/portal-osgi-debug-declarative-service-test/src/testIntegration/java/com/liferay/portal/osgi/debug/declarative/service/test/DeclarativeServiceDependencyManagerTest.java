/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.debug.declarative.service.test.component.DeclarativeServiceTestComponent;
import com.liferay.portal.osgi.debug.declarative.service.test.reference.DeclarativeServiceTestReference;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
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

		try (LogCapture logCapture = _configureLog4JLogger()) {
			_captureLog(logCapture);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"All declarative service components are satisfied",
				logEntry.getMessage());
			Assert.assertEquals(LoggerTestUtil.INFO, logEntry.getPriority());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"Stopped scanning for unsatisfied declarative service " +
					"components",
				logEntry.getMessage());
			Assert.assertEquals(LoggerTestUtil.INFO, logEntry.getPriority());
		}
	}

	@Test
	public void testDeclarativeServiceDependencyManagerUnresolvedDependencies()
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

			message = message.replaceAll("\\s", "");
			message = message.replaceAll("\\n", "");
			message = message.replaceAll("_", "");

			String s = StringBundler.concat(
				"name: ", DeclarativeServiceTestComponent.class.getName(),
				", unsatisfied references: {name: ",
				"declarativeServiceTestReference, target: null}");

			Assert.assertTrue(
				message, message.contains(s.replaceAll("\\s", "")));

			Assert.assertEquals(LoggerTestUtil.WARN, logEntry.getPriority());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"Stopped scanning for unsatisfied declarative service " +
					"components",
				logEntry.getMessage());
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

					String message = logEntry.getMessage();

					if (message.equals(
							"Stopped scanning for unsatisfied declarative " +
								"service components")) {

						return added;
					}

					try {
						_unsatisfiedComponentScannerConfiguration.update(
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

		_unsatisfiedComponentScannerConfiguration.update(
			HashMapDictionaryBuilder.<String, Object>put(
				"unsatisfiedComponentScanningInterval", 1
			).build());

		countDownLatch.await();

		Thread scanningThread = scanningThreadReference.get();

		scanningThread.join();
	}

	private static LogCapture _configureLog4JLogger() {
		return LoggerTestUtil.configureLog4JLogger(
			"com.liferay.portal.osgi.debug.declarative.service.internal." +
				"UnsatisfiedComponentScanner",
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
				StringBundler.concat(
					packagePath, "/dependencies/", _TEST_COMPONENT_FILE_NAME)),
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