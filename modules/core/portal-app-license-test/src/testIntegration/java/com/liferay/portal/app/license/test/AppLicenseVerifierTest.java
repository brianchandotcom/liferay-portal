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

package com.liferay.portal.app.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.app.license.AppLicenseVerifier;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Amos Fong
 */
@RunWith(Arquillian.class)
public class AppLicenseVerifierTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_bundle = FrameworkUtil.getBundle(AppLicenseVerifierTest.class);

		BundleContext bundleContext = _bundle.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			bundleContext, AppLicenseVerifier.class, null);

		_serviceTracker.open();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("version", "1.0.0");

		_serviceRegistrations.add(
			bundleContext.registerService(
				AppLicenseVerifier.class, new FailAppLicenseVerifier(),
				properties));

		properties = new Hashtable<>();

		properties.put("version", "1.0.1");

		_serviceRegistrations.add(
			bundleContext.registerService(
				AppLicenseVerifier.class, new PassAppLicenseVerifier(),
				properties));
	}

	@After
	public void tearDown() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceTracker.close();
	}

	@Test(expected = Exception.class)
	public void testVerifyFailure() throws Exception {
		Filter filter = FrameworkUtil.createFilter("(version=1.0.0)");

		Map<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
			serviceReferences = _serviceTracker.getTracked();

		for (Map.Entry<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
				entry : serviceReferences.entrySet()) {

			if (!filter.match(entry.getKey())) {
				continue;
			}

			AppLicenseVerifier appLicenseVerifier = entry.getValue();

			appLicenseVerifier.verify(_bundle, "", "", "");

			break;
		}
	}

	@Test
	public void testVerifyPass() throws Exception {
		Filter filter = FrameworkUtil.createFilter("(version=1.0.1)");

		Map<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
			serviceReferences = _serviceTracker.getTracked();

		for (Map.Entry<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
				entry : serviceReferences.entrySet()) {

			ServiceReference serviceReference = entry.getKey();

			if (!filter.match(serviceReference)) {
				continue;
			}

			AppLicenseVerifier appLicenseVerifier = entry.getValue();

			appLicenseVerifier.verify(_bundle, "", "", "");

			break;
		}
	}

	private Bundle _bundle;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();
	private ServiceTracker<AppLicenseVerifier, AppLicenseVerifier>
		_serviceTracker;

}