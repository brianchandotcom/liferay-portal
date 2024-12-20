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
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

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

}