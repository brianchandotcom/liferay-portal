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

package com.liferay.portal.bundle.blacklist.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.bundle.blacklist.BundleBlacklistManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.lpkg.deployer.test.util.LPKGTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collection;
import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;

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
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class BundleBlacklistTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(BundleBlacklistTest.class);

		_bundleContext = bundle.getBundleContext();

		_bundleBlacklistConfiguration = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				_CONFIG_NAME, null));

		_properties = _bundleBlacklistConfiguration.getProperties();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		_bundleBlacklistConfiguration.update(properties);

		CountDownLatch countDownLatch = new CountDownLatch(1);

		BundleTracker<Bundle> bundleTracker = new BundleTracker<Bundle>(
			_bundleContext, Bundle.ACTIVE, null) {

			@Override
			public Bundle addingBundle(Bundle bundle, BundleEvent event) {
				String symbolicName = bundle.getSymbolicName();

				if (symbolicName.equals(_LPKG_NAME)) {
					countDownLatch.countDown();

					close();
				}

				return null;
			}

		};

		bundleTracker.open();

		File deploymentDir = new File(
			GetterUtil.getString(
				_bundleContext.getProperty("lpkg.deployer.dir"),
				PropsValues.MODULE_FRAMEWORK_MARKETPLACE_DIR));

		deploymentDir = deploymentDir.getCanonicalFile();

		_lpkgPath = Paths.get(
			deploymentDir.toString(), _LPKG_NAME.concat(".lpkg"));

		LPKGTestUtil.createLPKG(_lpkgPath, _SYMBOLIC_NAME, true);

		countDownLatch.await();
	}

	@After
	public void tearDown() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(1);

		BundleTracker<Bundle> bundleTracker = new BundleTracker<Bundle>(
			_bundleContext, Bundle.UNINSTALLED, null) {

			@Override
			public Bundle addingBundle(Bundle bundle, BundleEvent event) {
				String symbolicName = bundle.getSymbolicName();

				if (symbolicName.equals(_LPKG_NAME)) {
					countDownLatch.countDown();

					close();
				}

				return null;
			}

		};

		bundleTracker.open();

		Files.delete(_lpkgPath);

		countDownLatch.await();

		_updateConfiguration(_properties);
	}

	@Test
	public void testAddToAndRemoveFromBlacklist() throws Exception {
		Bundle bundle = _findBundle(_SYMBOLIC_NAME);

		Assert.assertEquals(Bundle.ACTIVE, bundle.getState());

		Collection<String> blacklistBundleSymbolicNames =
			_bundleBlacklistManager.getBlacklistBundleSymbolicNames();

		Assert.assertFalse(
			_SYMBOLIC_NAME + " should not be blacklisted",
			blacklistBundleSymbolicNames.contains(_SYMBOLIC_NAME));

		_bundleBlacklistManager.addToBlacklistAndUninstall(_SYMBOLIC_NAME);

		for (Bundle curBundle : _bundleContext.getBundles()) {
			Assert.assertFalse(
				curBundle + " should not be installed",
				_SYMBOLIC_NAME.equals(curBundle.getSymbolicName()));
		}

		blacklistBundleSymbolicNames =
			_bundleBlacklistManager.getBlacklistBundleSymbolicNames();

		Assert.assertTrue(
			_SYMBOLIC_NAME + " should be blacklisted",
			blacklistBundleSymbolicNames.contains(_SYMBOLIC_NAME));

		_bundleBlacklistManager.removeFromBlacklistAndInstall(_SYMBOLIC_NAME);

		bundle = _findBundle(_SYMBOLIC_NAME);

		Assert.assertEquals(Bundle.ACTIVE, bundle.getState());

		blacklistBundleSymbolicNames =
			_bundleBlacklistManager.getBlacklistBundleSymbolicNames();

		Assert.assertFalse(
			_SYMBOLIC_NAME + " should not be blacklisted",
			blacklistBundleSymbolicNames.contains(_SYMBOLIC_NAME));
	}

	@Test
	public void testBundleBlacklist() throws Exception {
		Bundle jarBundle = null;
		Bundle lpkgBundle = null;
		Bundle warBundle = null;
		Bundle warWrapperBundle = null;

		for (Bundle bundle : _bundleContext.getBundles()) {
			String symbolicName = bundle.getSymbolicName();

			if (symbolicName.equals(_LPKG_NAME)) {
				lpkgBundle = bundle;
			}
			else if (symbolicName.equals(_SYMBOLIC_NAME)) {
				jarBundle = bundle;
			}
			else if (symbolicName.equals(_SYMBOLIC_NAME.concat("-war"))) {
				warBundle = bundle;
			}
			else if (symbolicName.equals(
						StringBundler.concat(
							_LPKG_NAME, StringPool.DASH, _SYMBOLIC_NAME,
							"-war-wrapper"))) {

				warWrapperBundle = bundle;
			}
		}

		Assert.assertNotNull("No JAR bundle found", jarBundle);
		Assert.assertNotNull("No LPKG bundle found", lpkgBundle);
		Assert.assertNotNull("No WAR bundle found", warBundle);
		Assert.assertNotNull("No WAR wrapper bundle found", warWrapperBundle);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		// Blacklist WAR wrapper

		properties.put(_PROP_KEY, warWrapperBundle.getSymbolicName());

		_updateConfiguration(properties);

		Assert.assertEquals(
			"WAR wrapper bundle not uninstalled", Bundle.UNINSTALLED,
			warWrapperBundle.getState());

		Assert.assertEquals(
			"WAR bundle not uninstalled", Bundle.UNINSTALLED,
			warBundle.getState());

		properties.remove(_PROP_KEY);

		_updateConfiguration(properties);

		warWrapperBundle = _findBundle(warWrapperBundle.getSymbolicName());

		Assert.assertEquals(
			"WAR wrapper bundle not reinstalled", Bundle.ACTIVE,
			warWrapperBundle.getState());

		warBundle = _findBundle(warBundle.getSymbolicName());

		Assert.assertEquals(
			"WAR bundle not reinstalled", Bundle.ACTIVE, warBundle.getState());

		// Blacklist WAR

		properties.put(_PROP_KEY, warBundle.getSymbolicName());

		_updateConfiguration(properties);

		Assert.assertEquals(
			"WAR bundle not uninstalled", Bundle.UNINSTALLED,
			warBundle.getState());

		properties.remove(_PROP_KEY);

		_updateConfiguration(properties);

		warBundle = _findBundle(warBundle.getSymbolicName());

		Assert.assertEquals(
			"WAR bundle not reinstalled", Bundle.ACTIVE, warBundle.getState());

		// Blacklist JAR

		properties.put(_PROP_KEY, jarBundle.getSymbolicName());

		_updateConfiguration(properties);

		Assert.assertEquals(
			"JAR bundle not uninstalled", Bundle.UNINSTALLED,
			jarBundle.getState());

		properties.remove(_PROP_KEY);

		_updateConfiguration(properties);

		jarBundle = _findBundle(jarBundle.getSymbolicName());

		Assert.assertEquals(
			"JAR bundle not reinstalled", Bundle.ACTIVE, jarBundle.getState());

		// Blacklist LPKG

		properties.put(_PROP_KEY, lpkgBundle.getSymbolicName());

		_updateConfiguration(properties);

		Assert.assertEquals(
			"LPKG not uninstalled", Bundle.UNINSTALLED, lpkgBundle.getState());

		Assert.assertEquals(
			"JAR bundle not uninstalled", Bundle.UNINSTALLED,
			jarBundle.getState());

		Assert.assertEquals(
			"WAR wrapper bundle not uninstalled", Bundle.UNINSTALLED,
			warWrapperBundle.getState());

		Assert.assertEquals(
			"WAR bundle not uninstalled", Bundle.UNINSTALLED,
			warBundle.getState());

		properties.remove(_PROP_KEY);

		_updateConfiguration(properties);

		lpkgBundle = _findBundle(lpkgBundle.getSymbolicName());

		Assert.assertEquals(
			"LPKG not reinstalled", Bundle.ACTIVE, lpkgBundle.getState());

		jarBundle = _findBundle(jarBundle.getSymbolicName());

		Assert.assertEquals(
			"JAR bundle not reinstalled", Bundle.ACTIVE, jarBundle.getState());

		warWrapperBundle = _findBundle(warWrapperBundle.getSymbolicName());

		Assert.assertEquals(
			"WAR wrapper bundle not reinstalled", Bundle.ACTIVE,
			warWrapperBundle.getState());

		warBundle = _findBundle(warBundle.getSymbolicName());

		Assert.assertEquals(
			"WAR bundle not reinstalled", Bundle.ACTIVE, warBundle.getState());
	}

	private Bundle _findBundle(String symbolicName) {
		for (Bundle bundle : _bundleContext.getBundles()) {
			if (symbolicName.equals(bundle.getSymbolicName())) {
				return bundle;
			}
		}

		throw new IllegalArgumentException(
			"No bundle installed with symbolic name " + symbolicName);
	}

	private void _updateConfiguration(Dictionary<String, Object> dictionary)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		ServiceListener serviceListener = new ServiceListener() {

			@Override
			public void serviceChanged(ServiceEvent serviceEvent) {
				if (serviceEvent.getType() != ServiceEvent.MODIFIED) {
					return;
				}

				ServiceReference<?> serviceReference =
					serviceEvent.getServiceReference();

				Object service = _bundleContext.getService(serviceReference);

				Class<?> clazz = service.getClass();

				if (_CLASS_NAME.equals(clazz.getName())) {
					countDownLatch.countDown();
				}
			}

		};

		_bundleContext.addServiceListener(serviceListener);

		try {
			if (dictionary == null) {
				_bundleBlacklistConfiguration.delete();
			}
			else {
				_bundleBlacklistConfiguration.update(dictionary);
			}

			countDownLatch.await();
		}
		finally {
			_bundleContext.removeServiceListener(serviceListener);
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.bundle.blacklist.internal.BundleBlacklist";

	private static final String _CONFIG_NAME =
		"com.liferay.portal.bundle.blacklist.internal." +
			"BundleBlacklistConfiguration";

	private static final String _LPKG_NAME = "Bundle Blacklist Test";

	private static final String _PROP_KEY = "blacklistBundleSymbolicNames";

	private static final String _SYMBOLIC_NAME =
		"com.liferay.portal.bundle.blacklist.test.bundle";

	@Inject
	private static BundleBlacklistManager _bundleBlacklistManager;

	private Configuration _bundleBlacklistConfiguration;
	private BundleContext _bundleContext;
	private Path _lpkgPath;
	private Dictionary<String, Object> _properties;

}