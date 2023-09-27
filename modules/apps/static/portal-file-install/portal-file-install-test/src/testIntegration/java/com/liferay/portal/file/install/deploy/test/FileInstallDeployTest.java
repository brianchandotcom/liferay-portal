/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.deploy.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;
import org.osgi.framework.wiring.BundleRequirement;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class FileInstallDeployTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(FileInstallDeployTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testConfiguration() throws Exception {
		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
			_CONFIGURATION_PID.concat(".config"));

		try {
			Configuration configuration =
				ConfigurationTestUtil.updateConfiguration(
					_CONFIGURATION_PID,
					() -> {
						String content1 = StringBundler.concat(
							_TEST_KEY, StringPool.EQUAL, StringPool.QUOTE,
							_TEST_VALUE_1, StringPool.QUOTE);

						Files.write(path, content1.getBytes());
					});

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertEquals(_TEST_VALUE_1, properties.get(_TEST_KEY));

			configuration = ConfigurationTestUtil.updateConfiguration(
				_CONFIGURATION_PID,
				() -> {
					String content = StringBundler.concat(
						_TEST_KEY, StringPool.EQUAL, StringPool.QUOTE,
						_TEST_VALUE_2, StringPool.QUOTE);

					Files.write(path, content.getBytes());

					File file = path.toFile();

					file.setLastModified(file.lastModified() + 1000);
				});

			properties = configuration.getProperties();

			Assert.assertEquals(_TEST_VALUE_2, properties.get(_TEST_KEY));

			configuration = ConfigurationTestUtil.updateConfiguration(
				_CONFIGURATION_PID, () -> Files.delete(path));

			Assert.assertNull(configuration);
		}
		finally {
			Files.deleteIfExists(path);
		}
	}

	@Test
	public void testConfigurationSystem() throws Exception {
		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
			_CONFIGURATION_PID.concat(".config"));

		String systemTestPropertyKey = StringBundler.concat(
			_CONFIGURATION_PID, StringPool.PERIOD, _TEST_KEY);

		System.setProperty(systemTestPropertyKey, _TEST_VALUE_1);

		try {
			Configuration configuration =
				ConfigurationTestUtil.updateConfiguration(
					_CONFIGURATION_PID,
					() -> {
						String content = StringBundler.concat(
							_TEST_KEY, "=\"${", systemTestPropertyKey, "}\"");

						Files.write(path, content.getBytes());
					});

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertEquals(_TEST_VALUE_1, properties.get(_TEST_KEY));
		}
		finally {
			System.clearProperty(systemTestPropertyKey);

			Files.deleteIfExists(path);
		}
	}

	@Test
	public void testDeployAndDelete() throws Exception {
		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_MODULES_DIR, _TEST_JAR_NAME);

		CountDownLatch installCountDownLatch = new CountDownLatch(1);

		CountDownLatch updateCountDownLatch = new CountDownLatch(3);

		CountDownLatch deleteCountDownLatch = new CountDownLatch(1);

		BundleListener bundleListener = new BundleListener() {

			@Override
			public void bundleChanged(BundleEvent bundleEvent) {
				Bundle bundle = bundleEvent.getBundle();

				if (!Objects.equals(
						bundle.getSymbolicName(), _TEST_JAR_SYMBOLIC_NAME)) {

					return;
				}

				int type = bundleEvent.getType();

				if (type == BundleEvent.STARTED) {
					installCountDownLatch.countDown();
					updateCountDownLatch.countDown();
				}
				else if (type == BundleEvent.UNINSTALLED) {
					deleteCountDownLatch.countDown();
				}
				else if (type == BundleEvent.UPDATED) {
					updateCountDownLatch.countDown();
				}
			}

		};

		_bundleContext.addBundleListener(bundleListener);

		Version baseVersion = new Version(1, 0, 0);

		Version updateVersion = new Version(2, 0, 0);

		Bundle bundle = null;

		try {
			JarBuilder jarBuilder = new JarBuilder(
				path, _TEST_JAR_SYMBOLIC_NAME);

			jarBuilder.setVersion(
				baseVersion
			).build();

			installCountDownLatch.await();

			bundle = BundleUtil.getBundle(
				_bundleContext, _TEST_JAR_SYMBOLIC_NAME);

			Assert.assertNotNull(bundle);

			Assert.assertEquals(Bundle.ACTIVE, bundle.getState());
			Assert.assertEquals(baseVersion, bundle.getVersion());

			jarBuilder = new JarBuilder(path, _TEST_JAR_SYMBOLIC_NAME);

			jarBuilder.setVersion(
				updateVersion
			).build();

			updateCountDownLatch.await();

			Assert.assertEquals(Bundle.ACTIVE, bundle.getState());
			Assert.assertEquals(updateVersion, bundle.getVersion());

			Files.delete(path);

			deleteCountDownLatch.await();

			Assert.assertEquals(Bundle.UNINSTALLED, bundle.getState());
		}
		finally {
			_bundleContext.removeBundleListener(bundleListener);

			_uninstall(_TEST_JAR_SYMBOLIC_NAME, path);
		}
	}

	@Test
	public void testDeployAndDeleteFragmentHost() throws Exception {
		String testFragmentSymbolicName = _TEST_JAR_SYMBOLIC_NAME.concat(
			".fragment");

		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_MODULES_DIR, _TEST_JAR_NAME);

		Path fragmentPath = Paths.get(
			PropsValues.MODULE_FRAMEWORK_MODULES_DIR,
			testFragmentSymbolicName.concat(".jar"));

		CountDownLatch installCountDownLatch = new CountDownLatch(1);

		CountDownLatch fragmentInstallCountDownLatch = new CountDownLatch(1);

		CountDownLatch deleteCountDownLatch = new CountDownLatch(1);

		CountDownLatch fragmentDeleteCountDownLatch = new CountDownLatch(1);

		BundleListener bundleListener = new BundleListener() {

			@Override
			public void bundleChanged(BundleEvent bundleEvent) {
				Bundle bundle = bundleEvent.getBundle();

				int type = bundleEvent.getType();

				if (Objects.equals(
						bundle.getSymbolicName(), testFragmentSymbolicName)) {

					if (type == BundleEvent.RESOLVED) {
						fragmentInstallCountDownLatch.countDown();
					}
					else if (type == BundleEvent.UNINSTALLED) {
						fragmentDeleteCountDownLatch.countDown();
					}
				}

				if (Objects.equals(
						bundle.getSymbolicName(), _TEST_JAR_SYMBOLIC_NAME)) {

					if (type == BundleEvent.STARTED) {
						installCountDownLatch.countDown();
					}
					else if (type == BundleEvent.UNINSTALLED) {
						deleteCountDownLatch.countDown();
					}
				}
			}

		};

		_bundleContext.addBundleListener(bundleListener);

		try {
			JarBuilder jarBuilder = new JarBuilder(
				path, _TEST_JAR_SYMBOLIC_NAME);

			jarBuilder.build();

			installCountDownLatch.await();

			Bundle bundle = BundleUtil.getBundle(
				_bundleContext, _TEST_JAR_SYMBOLIC_NAME);

			Assert.assertEquals(Bundle.ACTIVE, bundle.getState());

			jarBuilder = new JarBuilder(fragmentPath, testFragmentSymbolicName);

			jarBuilder.setFragmentHost(
				_TEST_JAR_SYMBOLIC_NAME
			).setImport(
				"javax.servlet"
			).build();

			fragmentInstallCountDownLatch.await();

			Bundle fragmentBundle = BundleUtil.getBundle(
				_bundleContext, testFragmentSymbolicName);

			Assert.assertEquals(Bundle.RESOLVED, fragmentBundle.getState());

			Files.delete(path);

			deleteCountDownLatch.await();

			Assert.assertEquals(Bundle.UNINSTALLED, bundle.getState());

			Files.delete(fragmentPath);

			fragmentDeleteCountDownLatch.await();

			Assert.assertEquals(Bundle.UNINSTALLED, fragmentBundle.getState());
		}
		finally {
			_bundleContext.removeBundleListener(bundleListener);

			_uninstall(_TEST_JAR_SYMBOLIC_NAME, path);

			_uninstall(testFragmentSymbolicName, fragmentPath);
		}
	}

	@Test
	public void testDeployOptionalDependency() throws Exception {
		String testOptionalProviderSymbolicName =
			_TEST_JAR_SYMBOLIC_NAME.concat(".optional.provider");

		CountDownLatch installCountDownLatch = new CountDownLatch(1);

		CountDownLatch optionalProviderInstallCountDownLatch =
			new CountDownLatch(1);

		AtomicBoolean bundleRefreshed = new AtomicBoolean();

		BundleListener bundleListener = new BundleListener() {

			@Override
			public void bundleChanged(BundleEvent bundleEvent) {
				Bundle bundle = bundleEvent.getBundle();

				int type = bundleEvent.getType();

				if (Objects.equals(
						bundle.getSymbolicName(),
						testOptionalProviderSymbolicName) &&
					(type == BundleEvent.STARTED)) {

					optionalProviderInstallCountDownLatch.countDown();
				}

				if (Objects.equals(
						bundle.getSymbolicName(), _TEST_JAR_SYMBOLIC_NAME) &&
					(type == BundleEvent.STARTED)) {

					if (installCountDownLatch.getCount() == 0) {
						bundleRefreshed.set(true);
					}

					installCountDownLatch.countDown();
				}
			}

		};

		_bundleContext.addBundleListener(bundleListener);

		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_MODULES_DIR, _TEST_JAR_NAME);

		Path optionalProviderPath = Paths.get(
			PropsValues.MODULE_FRAMEWORK_MODULES_DIR,
			testOptionalProviderSymbolicName.concat(".jar"));

		try {
			String optionalPackage = "com.liferay.test.optional.package";

			JarBuilder jarBuilder = new JarBuilder(
				path, _TEST_JAR_SYMBOLIC_NAME);

			jarBuilder.setImport(
				optionalPackage + ";resolution:=optional"
			).build();

			installCountDownLatch.await();

			Bundle bundle = BundleUtil.getBundle(
				_bundleContext, _TEST_JAR_SYMBOLIC_NAME);

			Assert.assertEquals(Bundle.ACTIVE, bundle.getState());

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			List<BundleRequirement> bundleRequirements =
				bundleWiring.getRequirements(null);

			Assert.assertTrue(
				bundleRequirements.toString(), bundleRequirements.isEmpty());

			jarBuilder = new JarBuilder(
				optionalProviderPath, testOptionalProviderSymbolicName);

			jarBuilder.setExport(
				optionalPackage
			).build();

			optionalProviderInstallCountDownLatch.await();

			Bundle optionalProviderBundle = BundleUtil.getBundle(
				_bundleContext, testOptionalProviderSymbolicName);

			Assert.assertEquals(
				Bundle.ACTIVE, optionalProviderBundle.getState());

			bundleWiring = bundle.adapt(BundleWiring.class);

			bundleRequirements = bundleWiring.getRequirements(null);

			Assert.assertEquals(
				bundleRequirements.toString(), 1, bundleRequirements.size());

			BundleRequirement bundleRequirement = bundleRequirements.get(0);

			Map<String, String> directives = bundleRequirement.getDirectives();

			String filter = directives.get(Constants.FILTER_DIRECTIVE);

			Assert.assertTrue(
				filter + " does not contain " + optionalPackage,
				filter.contains(optionalPackage));
		}
		finally {
			_bundleContext.removeBundleListener(bundleListener);

			_uninstall(_TEST_JAR_SYMBOLIC_NAME, path);

			_uninstall(testOptionalProviderSymbolicName, optionalProviderPath);
		}
	}

	private void _uninstall(String symbolicName, Path path) throws Exception {
		if (!Files.exists(path)) {
			return;
		}

		CountDownLatch countDownLatch = new CountDownLatch(1);

		BundleListener bundleListener = new BundleListener() {

			@Override
			public void bundleChanged(BundleEvent bundleEvent) {
				Bundle bundle = bundleEvent.getBundle();

				if (!Objects.equals(bundle.getSymbolicName(), symbolicName)) {
					return;
				}

				int type = bundleEvent.getType();

				if (type == BundleEvent.UNINSTALLED) {
					countDownLatch.countDown();
				}
			}

		};

		_bundleContext.addBundleListener(bundleListener);

		try {
			Files.deleteIfExists(path);

			countDownLatch.await();
		}
		finally {
			_bundleContext.removeBundleListener(bundleListener);
		}
	}

	private static final String _CONFIGURATION_PID =
		FileInstallDeployTest.class.getName() + "Configuration";

	private static final String _TEST_JAR_NAME;

	private static final String _TEST_JAR_SYMBOLIC_NAME;

	private static final String _TEST_KEY = "testKey";

	private static final String _TEST_VALUE_1 = "testValue1";

	private static final String _TEST_VALUE_2 = "testValue2";

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	static {
		Package pkg = FileInstallDeployTest.class.getPackage();

		_TEST_JAR_SYMBOLIC_NAME = pkg.getName();

		_TEST_JAR_NAME = _TEST_JAR_SYMBOLIC_NAME.concat(".jar");
	}

	private BundleContext _bundleContext;

	private class JarBuilder {

		public void build() throws IOException {
			try (OutputStream outputStream = Files.newOutputStream(_path);
				JarOutputStream jarOutputStream = new JarOutputStream(
					outputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME, _symbolicName);
				attributes.putValue(
					Constants.BUNDLE_VERSION, _version.toString());

				if (_exports != null) {
					attributes.putValue(Constants.EXPORT_PACKAGE, _exports);
				}

				if (_fragmentHost != null) {
					attributes.putValue(Constants.FRAGMENT_HOST, _fragmentHost);
				}

				if (_imports != null) {
					attributes.putValue(Constants.IMPORT_PACKAGE, _imports);
				}

				attributes.putValue("Manifest-Version", "2");

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();
			}
		}

		public JarBuilder setExport(String exports) {
			_exports = exports;

			return this;
		}

		public JarBuilder setFragmentHost(String fragmentHost) {
			_fragmentHost = fragmentHost;

			return this;
		}

		public JarBuilder setImport(String imports) {
			_imports = imports;

			return this;
		}

		public JarBuilder setVersion(Version version) {
			_version = version;

			return this;
		}

		private JarBuilder(Path path, String symbolicName) {
			_path = path;
			_symbolicName = symbolicName;
		}

		private String _exports;
		private String _fragmentHost;
		private String _imports;
		private final Path _path;
		private final String _symbolicName;
		private Version _version = new Version(1, 0, 0);

	}

}