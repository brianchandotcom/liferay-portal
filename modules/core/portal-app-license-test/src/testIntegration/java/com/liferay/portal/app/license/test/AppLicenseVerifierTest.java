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

import com.liferay.portal.app.license.AppLicenseVerifier;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.SortedMap;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Amos Fong
 */
@RunWith(Arquillian.class)
public class AppLicenseVerifierTest {

	@Before
	public void setUp() throws BundleException {
		bundle.start();

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, AppLicenseVerifier.class, null);

		_serviceTracker.open();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("version", "1.0.0");

		_bundleContext.registerService(
			AppLicenseVerifier.class, new FailAppLicenseVerifier(), properties);

		properties = new Hashtable<>();

		properties.put("version", "1.0.1");

		_bundleContext.registerService(
			AppLicenseVerifier.class, new PassAppLicenseVerifier(), properties);
	}

	@After
	public void tearDown() throws BundleException {
		_serviceTracker.close();

		bundle.stop();
	}

	@Test
	public void testVerifyFailure() {
		try {
			SortedMap<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
				verifiers = _serviceTracker.getTracked();

			Filter filter = FrameworkUtil.createFilter("(version=1.0.0)");

			for (ServiceReference serviceReference : verifiers.keySet()) {
				if (!filter.match(serviceReference)) {
					continue;
				}

				AppLicenseVerifier appLicenseVerifier = verifiers.get(
					serviceReference);

				appLicenseVerifier.verify(bundle, "", "", "");

				break;
			}

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testVerifyPass() {
		try {
			SortedMap<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
				verifiers = _serviceTracker.getTracked();

			Filter filter = FrameworkUtil.createFilter("(version=1.0.1)");

			for (ServiceReference serviceReference : verifiers.keySet()) {
				if (!filter.match(serviceReference)) {
					continue;
				}

				AppLicenseVerifier appLicenseVerifier = verifiers.get(
					serviceReference);

				appLicenseVerifier.verify(bundle, "", "", "");

				break;
			}
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@ArquillianResource
	public Bundle bundle;

	private static ServiceTracker<AppLicenseVerifier, AppLicenseVerifier>
		_serviceTracker;

	private BundleContext _bundleContext;

}