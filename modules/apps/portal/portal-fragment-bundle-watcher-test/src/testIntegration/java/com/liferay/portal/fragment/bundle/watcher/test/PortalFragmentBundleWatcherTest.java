/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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

		_testFragmentBundleListener = new TestFragmentBundleListener();

		_bundleContext.addBundleListener(_testFragmentBundleListener);

		_installedBundles = Collections.newSetFromMap(
			new ConcurrentHashMap<>());
	}

	@After
	public void tearDown() throws BundleException {
		_bundleContext.removeBundleListener(_testFragmentBundleListener);

		for (Bundle bundle : _installedBundles) {
			bundle.uninstall();
		}
	}

	@Test
	public void testDeployFragment() throws Exception {
		_testDeployFragment(false, false);
	}

	@Test
	public void testDeployFragmentWithDependency() throws Exception {
		_testDeployFragment(true, false);
	}

	@Test
	public void testDeployFragmentWithMissingDependency() throws Exception {
		_testDeployFragment(true, true);
	}

	@Test
	public void testDeployTwoFragmentsAndUnrelatedBundlesSimultaneously()
		throws Exception {

		Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME, null, null);

		hostBundle.start();

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		try {

			// Install unrelated bundles

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

			// Create callables to install fragments and start unrelated bundles

			Callable<Bundle> installFragmentACallable = () -> _installBundle(
				_FRAGMENT_A_SYMBOLIC_NAME, _HOST_SYMBOLIC_NAME, null);
			Callable<Bundle> installFragmentBCallable = () -> _installBundle(
				_FRAGMENT_B_SYMBOLIC_NAME, _HOST_SYMBOLIC_NAME, null);
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

			// Simulate multiple fragments and unrelated bundles being deployed
			// together and starting at the same time

			executorService.invokeAll(
				Arrays.asList(
					installFragmentACallable, startUnrelatedBundleACallable,
					startUnrelatedBundleBCallable,
					startUnrelatedBundleCCallable, installFragmentBCallable,
					startUnrelatedBundleDCallable,
					startUnrelatedBundleECallable,
					startUnrelatedBundleFCallable));

			Assert.assertTrue(
				_testFragmentBundleListener.waitForFragmentAResolved());
			Assert.assertTrue(
				_testFragmentBundleListener.waitForFragmentBResolved());

			int expectedMaxHostRefreshCount = 2;

			Assert.assertTrue(
				StringBundler.concat(
					"Expected host to refresh at most ",
					expectedMaxHostRefreshCount, " times, but was refreshed ",
					_testFragmentBundleListener.getHostRefreshedCount(),
					" times instead."),
				_testFragmentBundleListener.getHostRefreshedCount() <=
					expectedMaxHostRefreshCount);
		}
		finally {
			executorService.shutdownNow();
		}
	}

	@Test
	public void testDeployTwoFragmentsWithDependencies() throws Exception {
		_testDeployTwoFragmentsWithDependencies(false);
	}

	@Test
	public void testDeployTwoFragmentsWithDependenciesWhereOneIsMissing()
		throws Exception {

		_testDeployTwoFragmentsWithDependencies(true);
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

	private void _testDeployFragment(
			boolean hasDependency, boolean missingDependency)
		throws Exception {

		Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME, null, null);

		hostBundle.start();

		if (hasDependency && !missingDependency) {
			Bundle dependencyBundle = _installBundle(
				_DEPENDENCY_A_SYMBOLIC_NAME, null, null);

			dependencyBundle.start();
		}

		if (hasDependency) {
			_installBundle(
				_FRAGMENT_A_SYMBOLIC_NAME, _HOST_SYMBOLIC_NAME,
				_DEPENDENCY_A_SYMBOLIC_NAME);
		}
		else {
			_installBundle(
				_FRAGMENT_A_SYMBOLIC_NAME, _HOST_SYMBOLIC_NAME, null);
		}

		if (missingDependency) {
			Assert.assertFalse(
				"Fragment A is in the resolved state, but should actually be " +
					"in the installed state, since it has a missing dependency",
				_testFragmentBundleListener.waitForFragmentAResolved());

			Assert.assertEquals(
				0, _testFragmentBundleListener.getHostRefreshedCount());
		}
		else {
			Assert.assertTrue(
				"Fragment A should be in resolved state",
				_testFragmentBundleListener.waitForFragmentAResolved());

			Assert.assertEquals(
				1, _testFragmentBundleListener.getHostRefreshedCount());
		}
	}

	private void _testDeployTwoFragmentsWithDependencies(
			boolean missingDependency)
		throws Exception {

		Bundle hostBundle = _installBundle(_HOST_SYMBOLIC_NAME, null, null);

		hostBundle.start();

		Bundle dependencyBundleA = _installBundle(
			_DEPENDENCY_A_SYMBOLIC_NAME, null, null);

		dependencyBundleA.start();

		_installBundle(
			_FRAGMENT_A_SYMBOLIC_NAME, _HOST_SYMBOLIC_NAME,
			_DEPENDENCY_A_SYMBOLIC_NAME);

		if (!missingDependency) {
			Bundle dependencyBundleB = _installBundle(
				_DEPENDENCY_B_SYMBOLIC_NAME, null, null);

			dependencyBundleB.start();
		}

		_installBundle(
			_FRAGMENT_B_SYMBOLIC_NAME, _HOST_SYMBOLIC_NAME,
			_DEPENDENCY_B_SYMBOLIC_NAME);

		if (missingDependency) {
			Assert.assertTrue(
				"Fragment A should be in resolved state",
				_testFragmentBundleListener.waitForFragmentAResolved());

			Assert.assertFalse(
				"Fragment B is in the resolved state, but should actually be " +
					"in the installed state, since it has a missing dependency",
				_testFragmentBundleListener.waitForFragmentBResolved());

			Assert.assertEquals(
				1, _testFragmentBundleListener.getHostRefreshedCount());
		}
		else {
			Assert.assertTrue(
				"Fragment A should be in resolved state",
				_testFragmentBundleListener.waitForFragmentAResolved());
			Assert.assertTrue(
				"Fragment B should be in resolved state",
				_testFragmentBundleListener.waitForFragmentBResolved());

			int expectedMaxHostRefreshCount = 2;

			Assert.assertTrue(
				StringBundler.concat(
					"Expected host to refresh at most ",
					expectedMaxHostRefreshCount, " times, but was refreshed ",
					_testFragmentBundleListener.getHostRefreshedCount(),
					" times instead."),
				_testFragmentBundleListener.getHostRefreshedCount() <=
					expectedMaxHostRefreshCount);
		}
	}

	private static final String _DEPENDENCY_A_SYMBOLIC_NAME;

	private static final String _DEPENDENCY_B_SYMBOLIC_NAME;

	private static final String _FRAGMENT_A_SYMBOLIC_NAME;

	private static final String _FRAGMENT_B_SYMBOLIC_NAME;

	private static final String _HOST_SYMBOLIC_NAME;

	private static final String _PACKAGE_NAME;

	static {
		Package pkg = PortalFragmentBundleWatcherTest.class.getPackage();

		_PACKAGE_NAME = pkg.getName();

		_DEPENDENCY_A_SYMBOLIC_NAME = _PACKAGE_NAME.concat(".dependency.a");
		_DEPENDENCY_B_SYMBOLIC_NAME = _PACKAGE_NAME.concat(".dependency.b");

		_HOST_SYMBOLIC_NAME = _PACKAGE_NAME.concat(".host");

		_FRAGMENT_A_SYMBOLIC_NAME = _HOST_SYMBOLIC_NAME.concat(".fragment.a");
		_FRAGMENT_B_SYMBOLIC_NAME = _HOST_SYMBOLIC_NAME.concat(".fragment.b");
	}

	private BundleContext _bundleContext;
	private Set<Bundle> _installedBundles;
	private TestFragmentBundleListener _testFragmentBundleListener;

	private class TestFragmentBundleListener implements BundleListener {

		@Override
		public void bundleChanged(BundleEvent bundleEvent) {
			Bundle bundle = bundleEvent.getBundle();

			if (Objects.equals(
					bundle.getSymbolicName(), _FRAGMENT_A_SYMBOLIC_NAME) &&
				(bundleEvent.getType() == BundleEvent.RESOLVED)) {

				_fragmentAResolvedCountDownLatch.countDown();
			}
			else if (Objects.equals(
						bundle.getSymbolicName(), _FRAGMENT_B_SYMBOLIC_NAME) &&
					 (bundleEvent.getType() == BundleEvent.RESOLVED)) {

				_fragmentBResolvedCountDownLatch.countDown();
			}
			else if (Objects.equals(
						bundle.getSymbolicName(), _HOST_SYMBOLIC_NAME) &&
					 (bundleEvent.getType() == BundleEvent.STOPPED)) {

				_hostRefreshCount.incrementAndGet();
			}
		}

		public int getHostRefreshedCount() {
			return _hostRefreshCount.get();
		}

		public boolean waitForFragmentAResolved() throws Exception {
			return _fragmentAResolvedCountDownLatch.await(1, TimeUnit.SECONDS);
		}

		public boolean waitForFragmentBResolved() throws Exception {
			return _fragmentBResolvedCountDownLatch.await(1, TimeUnit.SECONDS);
		}

		private final CountDownLatch _fragmentAResolvedCountDownLatch =
			new CountDownLatch(1);
		private final CountDownLatch _fragmentBResolvedCountDownLatch =
			new CountDownLatch(1);
		private final AtomicInteger _hostRefreshCount = new AtomicInteger();

	}

}