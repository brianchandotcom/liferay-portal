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

package com.liferay.portal.classloader.tracker.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.framework.wiring.FrameworkWiring;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class ClassLoaderTrackerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testClassLoaderTracker() throws Throwable {
		Bundle bundle = FrameworkUtil.getBundle(ClassLoaderTrackerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Map<String, ClassLoader> classLoaders =
			(Map<String, ClassLoader>)ReflectionTestUtil.getFieldValue(
				ClassLoaderPool.class, "_classLoaders");

		String bundleSymbolicName = ClassLoaderTrackerTest.class.getName();
		String bundleVersion = "1.0.0";

		String contextName = bundleSymbolicName.concat(
			StringPool.UNDERLINE
		).concat(
			bundleVersion
		);

		try {

			// Test 1, install bundle

			Assert.assertNull(classLoaders.get(contextName));

			bundle = bundleContext.installBundle(
				bundleSymbolicName,
				_createBundle(bundleSymbolicName, bundleVersion));

			Assert.assertEquals(Bundle.INSTALLED, bundle.getState());

			Assert.assertNull(classLoaders.get(contextName));

			// Test 2, resolve bundle

			Bundle systemBundle = bundleContext.getBundle(0);

			FrameworkWiring frameworkWiring = systemBundle.adapt(
				FrameworkWiring.class);

			Assert.assertTrue(
				frameworkWiring.resolveBundles(Arrays.asList(bundle)));

			Assert.assertEquals(Bundle.RESOLVED, bundle.getState());

			Assert.assertNull(classLoaders.get(contextName));

			// Test 3, start bundle

			bundle.start(Bundle.START_ACTIVATION_POLICY);

			Assert.assertEquals(Bundle.STARTING, bundle.getState());

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			Assert.assertSame(
				bundleWiring.getClassLoader(), classLoaders.get(contextName));

			// Test 4, load class cause lazy activation

			Assert.assertNotSame(
				ClassLoaderTrackerTest.class,
				bundle.loadClass(ClassLoaderTrackerTest.class.getName()));

			Assert.assertEquals(Bundle.ACTIVE, bundle.getState());

			Assert.assertSame(
				bundleWiring.getClassLoader(), classLoaders.get(contextName));

			// Test 5, refresh bundle

			DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
				new DefaultNoticeableFuture<>();

			frameworkWiring.refreshBundles(
				Arrays.asList(bundle),
				event -> defaultNoticeableFuture.set(event));

			try {
				FrameworkEvent frameworkEvent = defaultNoticeableFuture.get();

				if (frameworkEvent.getType() !=
						FrameworkEvent.PACKAGES_REFRESHED) {

					throw frameworkEvent.getThrowable();
				}
			}
			catch (Throwable t) {
				throw t;
			}

			BundleWiring newBundleWiring = bundle.adapt(BundleWiring.class);

			Assert.assertSame(
				newBundleWiring.getClassLoader(),
				classLoaders.get(contextName));
			Assert.assertNotSame(
				bundleWiring.getClassLoader(),
				newBundleWiring.getClassLoader());

			// Test 6, stop bundle

			bundle.stop();

			Assert.assertEquals(Bundle.RESOLVED, bundle.getState());

			Assert.assertNull(classLoaders.get(contextName));

			// Test 7, uninstall bundle

			bundle.uninstall();

			Assert.assertEquals(Bundle.UNINSTALLED, bundle.getState());

			Assert.assertNull(classLoaders.get(contextName));
		}
		finally {
			bundle = bundleContext.getBundle(bundleSymbolicName);

			if (bundle != null) {
				bundle.uninstall();
			}
		}
	}

	private InputStream _createBundle(
			String bundleSymbolicName, String bundleVersion)
		throws Exception {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				_writeManifest(
					bundleSymbolicName, bundleVersion, jarOutputStream);

				_writeClasses(jarOutputStream, ClassLoaderTrackerTest.class);
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

	private void _writeManifest(
			String bundleSymbolicName, String bundleVersion,
			JarOutputStream jarOutputStream)
		throws IOException {

		Manifest manifest = new Manifest();

		Attributes attributes = manifest.getMainAttributes();

		attributes.putValue(Constants.BUNDLE_ACTIVATIONPOLICY, "lazy");
		attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
		attributes.putValue(Constants.BUNDLE_SYMBOLICNAME, bundleSymbolicName);
		attributes.putValue(Constants.BUNDLE_VERSION, bundleVersion);
		attributes.putValue("Manifest-Version", "2");

		jarOutputStream.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));

		manifest.write(jarOutputStream);

		jarOutputStream.closeEntry();
	}

}