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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
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
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			PortalFragmentBundleWatcherTest.class);

		_bundleContext = bundle.getBundleContext();

		_actualHostRefreshCount.set(0);
		_bundleContext.addBundleListener(_hostRefreshCountBundleListener);
	}

	@After
	public void tearDown() throws Exception {
		_bundleContext.removeBundleListener(_hostRefreshCountBundleListener);
	}

	@Test
	public void testDeployFragment() throws Exception {
		String fragmentSymbolicName = _HOST_SYMBOLIC_NAME.concat(".fragment");

		try {
			Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME);

			hostBundle.start();

			Bundle fragmentBundle = _installFragmentBundle(
				fragmentSymbolicName, _HOST_SYMBOLIC_NAME);

			//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
			Thread.sleep(200);

			int expectedHostRefreshCount = 1;

			Assert.assertEquals(
				expectedHostRefreshCount, _actualHostRefreshCount.intValue());

			Assert.assertEquals(
				"Fragment should be in resolved state",
				fragmentBundle.getState(), Bundle.RESOLVED);
		}
		finally {
			_uninstallBundle(_HOST_SYMBOLIC_NAME);
			_uninstallBundle(fragmentSymbolicName);
		}
	}

	@Test
	public void testDeployFragmentWithDependency() throws Exception {
		String fragmentSymbolicName = _HOST_SYMBOLIC_NAME.concat(".fragment");
		String dependencySymbolicName = _PACKAGE_NAME.concat(".dependency");

		try {
			Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME);

			hostBundle.start();

			Bundle dependencyBundle = _installDependencyBundle(
				dependencySymbolicName);

			dependencyBundle.start();

			Bundle fragmentBundle = _installFragmentBundleWithDependency(
				fragmentSymbolicName, _HOST_SYMBOLIC_NAME,
				dependencySymbolicName);

			//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
			Thread.sleep(200);

			int expectedHostRefreshCount = 1;

			Assert.assertEquals(
				expectedHostRefreshCount, _actualHostRefreshCount.intValue());

			Assert.assertEquals(
				"Fragment should be in resolved state",
				fragmentBundle.getState(), Bundle.RESOLVED);
		}
		finally {
			_uninstallBundle(_HOST_SYMBOLIC_NAME);
			_uninstallBundle(dependencySymbolicName);
			_uninstallBundle(fragmentSymbolicName);
		}
	}

	@Test
	public void testDeployFragmentWithMissingDependency() throws Exception {
		String fragmentSymbolicName = _HOST_SYMBOLIC_NAME.concat(".fragment");
		String dependencySymbolicName = _PACKAGE_NAME.concat(".dependency");

		try {
			Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME);

			hostBundle.start();

			Bundle fragmentBundle = _installFragmentBundleWithDependency(
				fragmentSymbolicName, _HOST_SYMBOLIC_NAME,
				dependencySymbolicName);

			//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
			Thread.sleep(200);

			int expectedHostRefreshCount = 0;

			Assert.assertEquals(
				expectedHostRefreshCount, _actualHostRefreshCount.intValue());

			Assert.assertNotEquals(
				"Fragment is in the resolved state, but should actually be " +
					"in the installed state, since it has a missing dependency",
				fragmentBundle.getState(), Bundle.RESOLVED);
		}
		finally {
			_uninstallBundle(_HOST_SYMBOLIC_NAME);
			_uninstallBundle(fragmentSymbolicName);
		}
	}

	@Test
	public void testDeployTwoFragmentsAndUnrelatedBundlesSimultaneously()
		throws Exception {

		String fragmentASymbolicName = _HOST_SYMBOLIC_NAME.concat(
			".fragment.a");
		String fragmentBSymbolicName = _HOST_SYMBOLIC_NAME.concat(
			".fragment.b");
		String unrelatedASymbolicName = _PACKAGE_NAME.concat(".unrelated.a");
		String unrelatedBSymbolicName = _PACKAGE_NAME.concat(".unrelated.b");
		String unrelatedCSymbolicName = _PACKAGE_NAME.concat(".unrelated.c");
		String unrelatedDSymbolicName = _PACKAGE_NAME.concat(".unrelated.d");
		String unrelatedESymbolicName = _PACKAGE_NAME.concat(".unrelated.e");
		String unrelatedFSymbolicName = _PACKAGE_NAME.concat(".unrelated.f");

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		try {
			Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME);

			hostBundle.start();

			//Install unrelated bundles
			Bundle unrelatedABundle = _installBundle(unrelatedASymbolicName);
			Bundle unrelatedBBundle = _installBundle(unrelatedBSymbolicName);
			Bundle unrelatedCBundle = _installBundle(unrelatedCSymbolicName);
			Bundle unrelatedDBundle = _installBundle(unrelatedDSymbolicName);
			Bundle unrelatedEBundle = _installBundle(unrelatedESymbolicName);
			Bundle unrelatedFBundle = _installBundle(unrelatedFSymbolicName);

			//Create callables to install fragments and start unrelated bundles
			Callable<Bundle> installFragmentACallable =
				() -> _installFragmentBundle(
					fragmentASymbolicName, _HOST_SYMBOLIC_NAME);
			Callable<Bundle> installFragmentBCallable =
				() -> _installFragmentBundle(
					fragmentBSymbolicName, _HOST_SYMBOLIC_NAME);
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
					_actualHostRefreshCount.intValue(), " times instead."),
				_actualHostRefreshCount.intValue() <=
					expectedMaxHostRefreshCount);
		}
		finally {
			executorService.shutdownNow();

			_uninstallBundle(_HOST_SYMBOLIC_NAME);
			_uninstallBundle(fragmentASymbolicName);
			_uninstallBundle(fragmentBSymbolicName);
			_uninstallBundle(unrelatedASymbolicName);
			_uninstallBundle(unrelatedBSymbolicName);
			_uninstallBundle(unrelatedCSymbolicName);
			_uninstallBundle(unrelatedDSymbolicName);
			_uninstallBundle(unrelatedESymbolicName);
			_uninstallBundle(unrelatedFSymbolicName);
		}
	}

	@Test
	public void testDeployTwoFragmentsWithDependencies() throws Exception {
		String dependencyASymbolicName = _PACKAGE_NAME.concat(".dependency.a");
		String dependencyBSymbolicName = _PACKAGE_NAME.concat(".dependency.b");
		String fragmentASymbolicName = _HOST_SYMBOLIC_NAME.concat(
			".fragment.a");
		String fragmentBSymbolicName = _HOST_SYMBOLIC_NAME.concat(
			".fragment.b");

		try {
			Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME);

			hostBundle.start();

			Bundle dependencyBundleA = _installDependencyBundle(
				dependencyASymbolicName);

			dependencyBundleA.start();

			_installFragmentBundleWithDependency(
				fragmentASymbolicName, _HOST_SYMBOLIC_NAME,
				dependencyASymbolicName);

			Bundle dependencyBundleB = _installDependencyBundle(
				dependencyBSymbolicName);

			dependencyBundleB.start();

			_installFragmentBundleWithDependency(
				fragmentBSymbolicName, _HOST_SYMBOLIC_NAME,
				dependencyBSymbolicName);

			//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
			Thread.sleep(200);

			int expectedMaxHostRefreshCount = 2;

			Assert.assertTrue(
				StringBundler.concat(
					"Expected host to refresh at most ",
					expectedMaxHostRefreshCount, " times, but was refreshed ",
					_actualHostRefreshCount.intValue(), " times instead."),
				_actualHostRefreshCount.intValue() <=
					expectedMaxHostRefreshCount);
		}
		finally {
			_uninstallBundle(_HOST_SYMBOLIC_NAME);
			_uninstallBundle(dependencyASymbolicName);
			_uninstallBundle(dependencyBSymbolicName);
			_uninstallBundle(fragmentASymbolicName);
			_uninstallBundle(fragmentBSymbolicName);
		}
	}

	@Test
	public void testDeployTwoFragmentsWithDependenciesWhereOneIsMissing()
		throws Exception {

		String dependencyASymbolicName = _PACKAGE_NAME.concat(".dependency.a");
		String dependencyBSymbolicName = _PACKAGE_NAME.concat(".dependency.b");
		String fragmentASymbolicName = _HOST_SYMBOLIC_NAME.concat(
			".fragment.a");
		String fragmentBSymbolicName = _HOST_SYMBOLIC_NAME.concat(
			".fragment.b");

		try {
			Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME);

			hostBundle.start();

			Bundle dependencyBundleA = _installDependencyBundle(
				dependencyASymbolicName);

			dependencyBundleA.start();

			_installFragmentBundleWithDependency(
				fragmentASymbolicName, _HOST_SYMBOLIC_NAME,
				dependencyASymbolicName);

			Bundle fragmentBundleB = _installFragmentBundleWithDependency(
				fragmentBSymbolicName, _HOST_SYMBOLIC_NAME,
				dependencyBSymbolicName);

			//Add delay to wait for PortalFragmentBundleWatcher bundle refreshes
			Thread.sleep(200);

			int expectedHostRefreshCount = 1;

			Assert.assertEquals(
				expectedHostRefreshCount, _actualHostRefreshCount.intValue());

			Assert.assertNotEquals(
				"Fragment B is in the resolved state, but should actually be " +
					"in the installed state, since it has a missing dependency",
				fragmentBundleB.getState(), Bundle.RESOLVED);
		}
		finally {
			_uninstallBundle(_HOST_SYMBOLIC_NAME);
			_uninstallBundle(dependencyASymbolicName);
			_uninstallBundle(fragmentASymbolicName);
			_uninstallBundle(fragmentBSymbolicName);
		}
	}

	private InputStream _createBundle(String symbolicName) throws Exception {
		return _createBundle(symbolicName, symbolicName, null, null);
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

	private InputStream _createDependencyBundle(String dependencySymbolicName)
		throws Exception {

		return _createBundle(
			dependencySymbolicName, dependencySymbolicName, null, null);
	}

	private InputStream _createFragmentBundle(
			String fragmentSymbolicName, String hostSymbolicName)
		throws Exception {

		return _createBundle(
			fragmentSymbolicName, null, null, hostSymbolicName);
	}

	private InputStream _createFragmentBundleWithDependency(
			String fragmentSymbolicName, String hostSymbolicName,
			String dependencySymbolicName)
		throws Exception {

		return _createBundle(
			fragmentSymbolicName, null, dependencySymbolicName,
			hostSymbolicName);
	}

	private Bundle _installBundle(String bundleSymbolicName) throws Exception {
		return _bundleContext.installBundle(
			bundleSymbolicName, _createBundle(bundleSymbolicName));
	}

	private Bundle _installDependencyBundle(String dependencyBundleSymbolicName)
		throws Exception {

		return _bundleContext.installBundle(
			dependencyBundleSymbolicName,
			_createDependencyBundle(dependencyBundleSymbolicName));
	}

	private Bundle _installFragmentBundle(
			String fragmentSymbolicName, String hostSymbolicName)
		throws Exception {

		return _bundleContext.installBundle(
			fragmentSymbolicName,
			_createFragmentBundle(fragmentSymbolicName, hostSymbolicName));
	}

	private Bundle _installFragmentBundleWithDependency(
			String fragmentSymbolicName, String hostSymbolicName,
			String dependencySymbolicName)
		throws Exception {

		return _bundleContext.installBundle(
			fragmentSymbolicName,
			_createFragmentBundleWithDependency(
				fragmentSymbolicName, hostSymbolicName,
				dependencySymbolicName));
	}

	private Bundle _startBundle(Bundle bundle) throws Exception {
		bundle.start();

		return bundle;
	}

	private void _uninstallBundle(String bundleSymbolicName)
		throws BundleException {

		Bundle bundle = _bundleContext.getBundle(bundleSymbolicName);

		if (bundle != null) {
			bundle.uninstall();
		}
	}

	private static final String _HOST_SYMBOLIC_NAME;

	private static final String _PACKAGE_NAME;

	static {
		Package pkg = PortalFragmentBundleWatcherTest.class.getPackage();

		_PACKAGE_NAME = pkg.getName();

		_HOST_SYMBOLIC_NAME = _PACKAGE_NAME.concat(".host");
	}

	private final AtomicInteger _actualHostRefreshCount = new AtomicInteger();
	private BundleContext _bundleContext;

	private final BundleListener _hostRefreshCountBundleListener =
		bundleEvent -> {
			Bundle bundle = bundleEvent.getBundle();

			String symbolicName = bundle.getSymbolicName();

			int type = bundleEvent.getType();

			if (Objects.equals(symbolicName, _HOST_SYMBOLIC_NAME) &&
				(type == BundleEvent.STOPPED)) {

				_actualHostRefreshCount.incrementAndGet();
			}
		};

}