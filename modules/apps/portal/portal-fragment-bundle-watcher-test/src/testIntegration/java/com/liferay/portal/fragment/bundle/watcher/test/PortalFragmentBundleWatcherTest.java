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

package com.liferay.portal.fragment.bundle.watcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Eric Yan
 */
@RunWith(Arquillian.class)
public class PortalFragmentBundleWatcherTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(
			PortalFragmentBundleWatcherTest.class);

		_bundleContext = bundle.getBundleContext();

		_refreshCountBundleListener = new RefreshCountBundleListener(
			_HOST_SYMBOLIC_NAME);

		_bundleContext.addBundleListener(_refreshCountBundleListener);

		_installedBundles = Collections.newSetFromMap(
			new ConcurrentHashMap<>());
	}

	@After
	public void tearDown() throws BundleException {
		_bundleContext.removeBundleListener(_refreshCountBundleListener);

		for (Bundle bundle : _installedBundles) {
			bundle.uninstall();
		}
	}

	@Test
	public void testDeployFragment() throws Exception {
		Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME, null, null);

		hostBundle.start();

		Bundle fragmentBundle = _installBundle(
			_HOST_SYMBOLIC_NAME.concat(".fragment"), _HOST_SYMBOLIC_NAME, null);

		//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
		Thread.sleep(200);

		Assert.assertEquals(1, _refreshCountBundleListener.getRefreshCount());

		Assert.assertEquals(
			"Fragment should be in resolved state", fragmentBundle.getState(),
			Bundle.RESOLVED);
	}

	@Test
	public void testDeployFragmentWithDependency() throws Exception {
		Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME, null, null);

		hostBundle.start();

		String dependencySymbolicName = _PACKAGE_NAME.concat(".dependency");

		Bundle dependencyBundle = _installBundle(
			dependencySymbolicName, null, null);

		dependencyBundle.start();

		Bundle fragmentBundle = _installBundle(
			_HOST_SYMBOLIC_NAME.concat(".fragment"), _HOST_SYMBOLIC_NAME,
			dependencySymbolicName);

		//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
		Thread.sleep(200);

		Assert.assertEquals(1, _refreshCountBundleListener.getRefreshCount());

		Assert.assertEquals(
			"Fragment should be in resolved state", fragmentBundle.getState(),
			Bundle.RESOLVED);
	}

	@Test
	public void testDeployFragmentWithMissingDependency() throws Exception {
		Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME, null, null);

		hostBundle.start();

		Bundle fragmentBundle = _installBundle(
			_HOST_SYMBOLIC_NAME.concat(".fragment"), _HOST_SYMBOLIC_NAME,
			_PACKAGE_NAME.concat(".dependency"));

		//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
		Thread.sleep(200);

		Assert.assertEquals(0, _refreshCountBundleListener.getRefreshCount());

		Assert.assertNotEquals(
			"Fragment is in the resolved state, but should actually be in " +
				"the installed state, since it has a missing dependency",
			fragmentBundle.getState(), Bundle.RESOLVED);
	}

	@Test
	public void testDeployTwoFragmentsAndUnrelatedBundlesSimultaneously()
		throws Exception {

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		try {
			Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME, null, null);

			hostBundle.start();

			//Install unrelated bundles
			Bundle unrelatedABundle = _installBundle(
				_PACKAGE_NAME.concat(".unrelated.a"), null, null);
			Bundle unrelatedBBundle = _installBundle(
				_PACKAGE_NAME.concat(".unrelated.b"), null, null);
			Bundle unrelatedCBundle = _installBundle(
				_PACKAGE_NAME.concat(".unrelated.c"), null, null);
			Bundle unrelatedDBundle = _installBundle(
				_PACKAGE_NAME.concat(".unrelated.d"), null, null);
			Bundle unrelatedEBundle = _installBundle(
				_PACKAGE_NAME.concat(".unrelated.e"), null, null);
			Bundle unrelatedFBundle = _installBundle(
				_PACKAGE_NAME.concat(".unrelated.f"), null, null);

			//Create callables to install fragments and start unrelated bundles
			Callable<Bundle> installFragmentACallable = () -> _installBundle(
				_HOST_SYMBOLIC_NAME.concat(".fragment.a"), _HOST_SYMBOLIC_NAME,
				null);
			Callable<Bundle> installFragmentBCallable = () -> _installBundle(
				_HOST_SYMBOLIC_NAME.concat(".fragment.b"), _HOST_SYMBOLIC_NAME,
				null);
			Callable<Bundle> startUnrelatedBundleACallable = () -> _startBundle(
				unrelatedABundle);
			Callable<Bundle> startUnrelatedBundleBCallable = () -> _startBundle(
				unrelatedBBundle);
			Callable<Bundle> startUnrelatedBundleCCallable = () -> _startBundle(
				unrelatedCBundle);
			Callable<Bundle> startUnrelatedBundleDCallable = () -> _startBundle(
				unrelatedDBundle);
			Callable<Bundle> startUnrelatedBundleECallable = () -> _startBundle(
				unrelatedEBundle);
			Callable<Bundle> startUnrelatedBundleFCallable = () -> _startBundle(
				unrelatedFBundle);

			List<Callable<Bundle>> callables = Arrays.asList(
				installFragmentACallable, startUnrelatedBundleACallable,
				startUnrelatedBundleBCallable, startUnrelatedBundleCCallable,
				installFragmentBCallable, startUnrelatedBundleDCallable,
				startUnrelatedBundleECallable, startUnrelatedBundleFCallable);

			//Simulate multiple fragments and unrelated bundles being deployed
			//together and starting at the same time
			List<Future<Bundle>> futures = executorService.invokeAll(callables);

			for (Future<Bundle> future : futures) {
				future.get();
			}

			//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
			Thread.sleep(200);

			int expectedMaxHostRefreshCount = 2;

			Assert.assertTrue(
				StringBundler.concat(
					"Expected host to refresh at most ",
					expectedMaxHostRefreshCount, " times, but was refreshed ",
					_refreshCountBundleListener.getRefreshCount(),
					" times instead."),
				_refreshCountBundleListener.getRefreshCount() <=
					expectedMaxHostRefreshCount);
		}
		finally {
			executorService.shutdownNow();
		}
	}

	@Test
	public void testDeployTwoFragmentsWithDependencies() throws Exception {
		String dependencyASymbolicName = _PACKAGE_NAME.concat(".dependency.a");
		String dependencyBSymbolicName = _PACKAGE_NAME.concat(".dependency.b");

		Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME, null, null);

		hostBundle.start();

		Bundle dependencyBundleA = _installBundle(
			dependencyASymbolicName, null, null);

		dependencyBundleA.start();

		_installBundle(
			_HOST_SYMBOLIC_NAME.concat(".fragment.a"), _HOST_SYMBOLIC_NAME,
			dependencyASymbolicName);

		Bundle dependencyBundleB = _installBundle(
			dependencyBSymbolicName, null, null);

		dependencyBundleB.start();

		_installBundle(
			_HOST_SYMBOLIC_NAME.concat(".fragment.b"), _HOST_SYMBOLIC_NAME,
			dependencyBSymbolicName);

		//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
		Thread.sleep(200);

		int expectedMaxHostRefreshCount = 2;

		Assert.assertTrue(
			StringBundler.concat(
				"Expected host to refresh at most ",
				expectedMaxHostRefreshCount, " times, but was refreshed ",
				_refreshCountBundleListener.getRefreshCount(),
				" times instead."),
			_refreshCountBundleListener.getRefreshCount() <=
				expectedMaxHostRefreshCount);
	}

	@Test
	public void testDeployTwoFragmentsWithDependenciesWhereOneIsMissing()
		throws Exception {

		Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME, null, null);

		hostBundle.start();

		String dependencyASymbolicName = _PACKAGE_NAME.concat(".dependency.a");

		Bundle dependencyBundleA = _installBundle(
			dependencyASymbolicName, null, null);

		dependencyBundleA.start();

		_installBundle(
			_HOST_SYMBOLIC_NAME.concat(".fragment.a"), _HOST_SYMBOLIC_NAME,
			dependencyASymbolicName);

		Bundle fragmentBundleB = _installBundle(
			_HOST_SYMBOLIC_NAME.concat(".fragment.b"), _HOST_SYMBOLIC_NAME,
			_PACKAGE_NAME.concat(".dependency.b"));

		//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
		Thread.sleep(200);

		Assert.assertEquals(1, _refreshCountBundleListener.getRefreshCount());

		Assert.assertNotEquals(
			"Fragment B is in the resolved state, but should actually be in " +
				"the installed state, since it has a missing dependency",
			fragmentBundleB.getState(), Bundle.RESOLVED);
	}

	private InputStream _createBundle(
			String symbolicName, String exports, String imports,
			String fragmentHost)
		throws Exception {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME, symbolicName);
				attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");

				if (exports != null) {
					attributes.putValue(Constants.EXPORT_PACKAGE, exports);
				}

				if (fragmentHost != null) {
					attributes.putValue(Constants.FRAGMENT_HOST, fragmentHost);
				}

				if (imports != null) {
					attributes.putValue(Constants.IMPORT_PACKAGE, imports);
				}

				attributes.putValue("Manifest-Version", "2");

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private Bundle _installBundle(
			String bundleSymbolicName, String hostSymbolicName,
			String dependencySymbolicName)
		throws Exception {

		Bundle bundle = null;

		if (hostSymbolicName == null) {
			bundle = _bundleContext.installBundle(
				bundleSymbolicName,
				_createBundle(
					bundleSymbolicName, bundleSymbolicName, null, null));
		}
		else {
			bundle = _bundleContext.installBundle(
				bundleSymbolicName,
				_createBundle(
					bundleSymbolicName, null, dependencySymbolicName,
					hostSymbolicName));
		}

		_installedBundles.add(bundle);

		return bundle;
	}

	private Bundle _startBundle(Bundle bundle) throws Exception {
		bundle.start();

		return bundle;
	}

	private static final String _HOST_SYMBOLIC_NAME;

	private static final String _PACKAGE_NAME;

	static {
		Package pkg = PortalFragmentBundleWatcherTest.class.getPackage();

		_PACKAGE_NAME = pkg.getName();

		_HOST_SYMBOLIC_NAME = _PACKAGE_NAME.concat(".host");
	}

	private BundleContext _bundleContext;
	private Set<Bundle> _installedBundles;
	private RefreshCountBundleListener _refreshCountBundleListener;

	private class RefreshCountBundleListener implements BundleListener {

		@Override
		public void bundleChanged(BundleEvent bundleEvent) {
			Bundle bundle = bundleEvent.getBundle();

			String symbolicName = bundle.getSymbolicName();

			int type = bundleEvent.getType();

			if (Objects.equals(symbolicName, _symbolicName) &&
				(type == BundleEvent.STOPPED)) {

				_refreshCount.incrementAndGet();
			}
		}

		public int getRefreshCount() {
			return _refreshCount.get();
		}

		private RefreshCountBundleListener(String symbolicName) {
			_symbolicName = symbolicName;
		}

		private final AtomicInteger _refreshCount = new AtomicInteger();
		private final String _symbolicName;

	}

}