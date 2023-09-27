/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.app.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.app.license.AppLicenseVerifier;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
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

		_failServiceRegistration = _bundleContext.registerService(
			AppLicenseVerifier.class, new FailAppLicenseVerifier(),
			HashMapDictionaryBuilder.<String, Object>put(
				"version", "1.0.0"
			).build());

		_passServiceRegistration = _bundleContext.registerService(
			AppLicenseVerifier.class, new PassAppLicenseVerifier(),
			HashMapDictionaryBuilder.<String, Object>put(
				"version", "1.0.1"
			).build());
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

		Bundle bundle = _bundleContext.getBundle();

		try {
			appLicenseVerifier.verify("", "", "", bundle.getSymbolicName());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertSame(FailAppLicenseVerifier.EXCEPTION, exception);
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

		Bundle bundle = _bundleContext.getBundle();

		try {
			appLicenseVerifier.verify("", "", "", bundle.getSymbolicName());
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