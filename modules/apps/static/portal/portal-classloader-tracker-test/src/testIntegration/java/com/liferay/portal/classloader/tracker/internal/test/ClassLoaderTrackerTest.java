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

import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Jar;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
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

		String contextName =
			bundleSymbolicName.concat(StringPool.UNDERLINE).concat(
				bundleVersion);

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

			bundle.start();

			Assert.assertEquals(Bundle.ACTIVE, bundle.getState());

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			Assert.assertSame(
				bundleWiring.getClassLoader(), classLoaders.get(contextName));

			// Test 4, refresh bundle

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

			// Test 5, stop bundle

			bundle.stop();

			Assert.assertEquals(Bundle.RESOLVED, bundle.getState());

			Assert.assertNull(classLoaders.get(contextName));

			// Test 6, uninstall bundle

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

		try (Builder builder = new Builder()) {
			builder.setBundleSymbolicName(bundleSymbolicName);
			builder.setBundleVersion(bundleVersion);

			try (Jar jar = builder.build()) {
				UnsyncByteArrayOutputStream outputStream =
					new UnsyncByteArrayOutputStream();

				jar.write(outputStream);

				return new UnsyncByteArrayInputStream(
					outputStream.unsafeGetByteArray(), 0, outputStream.size());
			}
		}
	}

}