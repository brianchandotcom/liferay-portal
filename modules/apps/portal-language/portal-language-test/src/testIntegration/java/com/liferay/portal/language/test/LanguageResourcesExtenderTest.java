/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.language.LanguageResources;

import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Kevin Lee
 */
@RunWith(Arquillian.class)
public class LanguageResourcesExtenderTest {

	@Test
	public void testRegistration() throws Exception {
		Bundle bundle = _installResourceBundle(
			"test.bundle", "content1.Language",
			_getProvideCapability("content1.Language", 1));

		try {
			bundle.start();

			Assert.assertEquals(
				"Test 1",
				LanguageResources.getMessage(_LOCALE, "language-key-1"));
			Assert.assertEquals(
				"Test 1", LanguageResources.getMessage(_LOCALE, "about"));
			Assert.assertEquals(
				"Test 1",
				LanguageResources.getMessage(_LOCALE, "shared-language-key"));

			Assert.assertEquals(
				"Enabled", LanguageResources.getMessage(_LOCALE, "enabled"));
		}
		finally {
			bundle.uninstall();
		}
	}

	@Test
	public void testRegistrationExcludePortalResources() throws Exception {
		Bundle bundle = _installResourceBundle(
			"test.bundle", "content1.Language",
			_getProvideCapabilityModuleOnly(
				"test.bundle", "test-bundle", "content1.Language", 1, true));

		try {
			bundle.start();

			ResourceBundleLoader resourceBundleLoader =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName("test.bundle");

			Assert.assertNotNull(resourceBundleLoader);

			ResourceBundle resourceBundle =
				resourceBundleLoader.loadResourceBundle(_LOCALE);

			Assert.assertNotNull(resourceBundle);

			Assert.assertEquals(
				"Test 1", resourceBundle.getString("language-key-1"));
			Assert.assertEquals("Test 1", resourceBundle.getString("about"));
			Assert.assertEquals(
				"Test 1", resourceBundle.getString("shared-language-key"));

			Assert.assertFalse(resourceBundle.containsKey("enabled"));
		}
		finally {
			bundle.uninstall();
		}
	}

	@Test
	public void testRegistrationLegacy() throws Exception {
		String bundleSymbolicName = "test.bundle";
		String servletContextName = "test-bundle";

		Bundle bundle = _installResourceBundle(
			bundleSymbolicName, "content1.Language",
			_getProvideCapabilityLegacy(
				bundleSymbolicName, servletContextName, "content1.Language", 1,
				false));

		try {
			bundle.start();

			Assert.assertNotNull(
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(
						bundleSymbolicName));
			Assert.assertNotNull(
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByServletContextName(
						servletContextName));
		}
		finally {
			bundle.uninstall();
		}
	}

	@Test
	public void testRegistrationModuleOnly() throws Exception {
		String bundleSymbolicName = "test.bundle";
		String servletContextName = "test-bundle";

		Bundle bundle = _installResourceBundle(
			bundleSymbolicName, "content1.Language",
			_getProvideCapabilityModuleOnly(
				bundleSymbolicName, servletContextName, "content1.Language", 1,
				false));

		try {
			bundle.start();

			Assert.assertNull(
				LanguageResources.getMessage(_LOCALE, "language-key-1"));
			Assert.assertNull(
				LanguageResources.getMessage(_LOCALE, "shared-language-key"));

			Assert.assertEquals(
				"About", LanguageResources.getMessage(_LOCALE, "about"));

			ResourceBundleLoader resourceBundleLoader1 =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(
						bundleSymbolicName);
			ResourceBundleLoader resourceBundleLoader2 =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByServletContextName(
						servletContextName);

			Assert.assertNotNull(resourceBundleLoader1);
			Assert.assertNotNull(resourceBundleLoader2);

			ResourceBundle resourceBundle1 =
				resourceBundleLoader1.loadResourceBundle(_LOCALE);
			ResourceBundle resourceBundle2 =
				resourceBundleLoader2.loadResourceBundle(_LOCALE);

			Assert.assertNotNull(resourceBundle1);
			Assert.assertNotNull(resourceBundle2);

			Assert.assertEquals(
				"Test 1", resourceBundle1.getString("language-key-1"));
			Assert.assertEquals(
				"Test 1", resourceBundle2.getString("language-key-1"));

			Assert.assertEquals("Test 1", resourceBundle1.getString("about"));
			Assert.assertEquals("Test 1", resourceBundle2.getString("about"));

			Assert.assertEquals(
				"Test 1", resourceBundle1.getString("shared-language-key"));
			Assert.assertEquals(
				"Test 1", resourceBundle2.getString("shared-language-key"));

			Assert.assertEquals(
				"Enabled", resourceBundle1.getString("enabled"));
			Assert.assertEquals(
				"Enabled", resourceBundle2.getString("enabled"));
		}
		finally {
			bundle.uninstall();
		}
	}

	@Test
	public void testRegistrationModuleOnlyMultiple() throws Exception {
		Bundle bundle1 = _installResourceBundle(
			"test.bundle1", "content1.Language",
			_getProvideCapabilityModuleOnly(
				"test.bundle1", "test-bundle1", "content1.Language", 1, false));
		Bundle bundle2 = _installResourceBundle(
			"test.bundle2", "content2.Language",
			_getProvideCapabilityModuleOnly(
				"test.bundle2", "test-bundle2", "content2.Language", 1, false));

		try {
			bundle1.start();
			bundle2.start();

			ResourceBundleLoader resourceBundleLoader1 =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName("test.bundle1");
			ResourceBundleLoader resourceBundleLoader2 =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName("test.bundle2");

			Assert.assertNotNull(resourceBundleLoader1);
			Assert.assertNotNull(resourceBundleLoader2);

			ResourceBundle resourceBundle1 =
				resourceBundleLoader1.loadResourceBundle(_LOCALE);
			ResourceBundle resourceBundle2 =
				resourceBundleLoader2.loadResourceBundle(_LOCALE);

			Assert.assertNotNull(resourceBundle1);
			Assert.assertNotNull(resourceBundle2);

			Assert.assertEquals(
				"Test 1", resourceBundle1.getString("language-key-1"));
			Assert.assertEquals("Test 1", resourceBundle1.getString("about"));
			Assert.assertEquals(
				"Test 1", resourceBundle1.getString("shared-language-key"));

			Assert.assertEquals(
				"Test 2", resourceBundle2.getString("language-key-2"));
			Assert.assertEquals("Test 2", resourceBundle2.getString("about"));
			Assert.assertEquals(
				"Test 2", resourceBundle2.getString("shared-language-key"));

			Assert.assertFalse(resourceBundle1.containsKey("language-key-2"));
			Assert.assertFalse(resourceBundle2.containsKey("language-key-1"));
		}
		finally {
			bundle1.uninstall();
			bundle2.uninstall();
		}
	}

	@Test
	public void testRegistrationServiceRanking() throws Exception {
		Bundle bundle1 = _installResourceBundle(
			"test.bundle1", "content1.Language",
			_getProvideCapability("content1.Language", 1));
		Bundle bundle2 = _installResourceBundle(
			"test.bundle2", "content2.Language",
			_getProvideCapability("content2.Language", 2));

		try {
			bundle1.start();
			bundle2.start();

			Assert.assertEquals(
				"Test 1",
				LanguageResources.getMessage(_LOCALE, "language-key-1"));
			Assert.assertEquals(
				"Test 2",
				LanguageResources.getMessage(_LOCALE, "language-key-2"));
			Assert.assertEquals(
				"Test 2", LanguageResources.getMessage(_LOCALE, "about"));
			Assert.assertEquals(
				"Test 2",
				LanguageResources.getMessage(_LOCALE, "shared-language-key"));
		}
		finally {
			bundle1.uninstall();
			bundle2.uninstall();
		}
	}

	private String _getProvideCapability(String baseName, int serviceRanking) {
		return StringBundler.concat(
			"liferay.language.resources;module.only=false;",
			"resource.bundle.base.name=\"", baseName, "\";service.ranking=",
			serviceRanking);
	}

	private String _getProvideCapabilityLegacy(
		String bundleSymbolicName, String servletContextName, String baseName,
		int serviceRanking, boolean excludePortalResources) {

		String provideCapability = _getProvideCapabilityModuleOnly(
			bundleSymbolicName, servletContextName, baseName, serviceRanking,
			excludePortalResources);

		return StringUtil.replace(
			provideCapability, "liferay.language.resources",
			"liferay.resource.bundle");
	}

	private String _getProvideCapabilityModuleOnly(
		String bundleSymbolicName, String servletContextName, String baseName,
		int serviceRanking, boolean excludePortalResources) {

		StringBundler sb = new StringBundler(11);

		sb.append("liferay.language.resources;bundle.symbolic.name=\"");
		sb.append(bundleSymbolicName);
		sb.append("\";exclude.portal.resources=");
		sb.append(excludePortalResources);
		sb.append(";module.only=true;resource.bundle.base.name=\"");
		sb.append(baseName);
		sb.append("\";service.ranking=");
		sb.append(serviceRanking);
		sb.append(";servlet.context.name=\"");
		sb.append(servletContextName);
		sb.append("\"");

		return sb.toString();
	}

	private Bundle _installResourceBundle(
			String bundleSymbolicName, String baseName,
			String provideCapability)
		throws Exception {

		return _installResourceBundle(
			bundleSymbolicName, baseName, new String[] {provideCapability},
			null);
	}

	private Bundle _installResourceBundle(
			String bundleSymbolicName, String baseName,
			String[] provideCapabilities, String[] requireCapabilities)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			LanguageResourcesExtenderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				_writeManifest(
					jarOutputStream, bundleSymbolicName, provideCapabilities,
					requireCapabilities);

				_writeResources(jarOutputStream, bundle, baseName);
			}

			return bundleContext.installBundle(
				bundleSymbolicName,
				new UnsyncByteArrayInputStream(
					unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
					unsyncByteArrayOutputStream.size()));
		}
	}

	private void _writeManifest(
			JarOutputStream jarOutputStream, String bundleSymbolicName,
			String[] provideCapabilities, String[] requireCapabilities)
		throws Exception {

		Manifest manifest = new Manifest();

		Attributes attributes = manifest.getMainAttributes();

		attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
		attributes.putValue(Constants.BUNDLE_SYMBOLICNAME, bundleSymbolicName);
		attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
		attributes.putValue("Manifest-Version", "2");

		if (provideCapabilities != null) {
			attributes.putValue(
				Constants.PROVIDE_CAPABILITY,
				StringUtil.merge(provideCapabilities, StringPool.COMMA));
		}

		if (requireCapabilities != null) {
			attributes.putValue(
				Constants.REQUIRE_CAPABILITY,
				StringUtil.merge(requireCapabilities, StringPool.COMMA));
		}

		jarOutputStream.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));

		manifest.write(jarOutputStream);

		jarOutputStream.closeEntry();
	}

	private void _writeResources(
			JarOutputStream jarOutputStream, Bundle bundle, String baseName)
		throws Exception {

		int index = baseName.lastIndexOf(CharPool.PERIOD);

		String path = baseName.substring(0, index);
		String name = baseName.substring(index + 1);

		Enumeration<URL> enumeration = bundle.findEntries(
			"com/liferay/portal/language/test/dependencies/" + path,
			name.concat("*.properties"), false);

		if (enumeration == null) {
			return;
		}

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String urlPath = url.getPath();

			String fileName = urlPath.substring(
				urlPath.lastIndexOf(CharPool.SLASH) + 1);

			jarOutputStream.putNextEntry(
				new ZipEntry(path + StringPool.SLASH + fileName));

			try (InputStream inputStream = url.openStream()) {
				StreamUtil.transfer(inputStream, jarOutputStream, false);
			}

			jarOutputStream.closeEntry();
		}
	}

	private static final Locale _LOCALE = LocaleUtil.ENGLISH;

}