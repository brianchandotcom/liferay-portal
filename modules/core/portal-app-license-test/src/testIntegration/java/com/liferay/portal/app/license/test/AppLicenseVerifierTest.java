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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Amos Fong
 */
@RunWith(Arquillian.class)
public class AppLicenseVerifierTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(AppLicenseVerifierTest.class);

		_bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("version", "1.0.0");

		_failServiceRegistration = _bundleContext.registerService(
			AppLicenseVerifier.class, new FailAppLicenseVerifier(), properties);

		properties = new HashMapDictionary<>();

		properties.put("version", "1.0.1");

		_passServiceRegistration = _bundleContext.registerService(
			AppLicenseVerifier.class, new PassAppLicenseVerifier(), properties);
	}

	@AfterClass
	public static void tearDownClass() {
		_failServiceRegistration.unregister();
		_passServiceRegistration.unregister();
	}

	@Test
	public void testVerifyFailure() throws Exception {
		Collection<ServiceReference<AppLicenseVerifier>> serviceReferences =
			_bundleContext.getServiceReferences(
				AppLicenseVerifier.class, "(version=1.0.0)");

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		Iterator<ServiceReference<AppLicenseVerifier>> iterator =
			serviceReferences.iterator();

		ServiceReference<AppLicenseVerifier> serviceReference = iterator.next();

		AppLicenseVerifier appLicenseVerifier = _bundleContext.getService(
			serviceReference);

		try {
			appLicenseVerifier.verify(_bundleContext.getBundle(), "", "", "");

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertSame(FailAppLicenseVerifier.EXCEPTION, e);
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	@Test
	public void testVerifyPass() throws Exception {
		Collection<ServiceReference<AppLicenseVerifier>> serviceReferences =
			_bundleContext.getServiceReferences(
				AppLicenseVerifier.class, "(version=1.0.1)");

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		Iterator<ServiceReference<AppLicenseVerifier>> iterator =
			serviceReferences.iterator();

		ServiceReference<AppLicenseVerifier> serviceReference = iterator.next();

		AppLicenseVerifier appLicenseVerifier = _bundleContext.getService(
			serviceReference);

		try {
			appLicenseVerifier.verify(_bundleContext.getBundle(), "", "", "");
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	private static BundleContext _bundleContext;
	private static ServiceRegistration<AppLicenseVerifier>
		_failServiceRegistration;
	private static ServiceRegistration<AppLicenseVerifier>
		_passServiceRegistration;

}