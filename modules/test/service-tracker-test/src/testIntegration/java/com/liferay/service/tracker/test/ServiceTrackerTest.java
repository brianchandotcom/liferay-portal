/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.service.tracker.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class ServiceTrackerTest {

	@Test
	public void testUnregisterServiceDuringServiceTrackerClosing() {
		Bundle bundle = FrameworkUtil.getBundle(ServiceTrackerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		TestService testService1 = new TestService();

		ServiceRegistration<TestService> serviceRegistration1 =
			bundleContext.registerService(
				TestService.class, testService1, null);

		ServiceReference<TestService> serviceReference1 =
			serviceRegistration1.getReference();

		TestService testService2 = new TestService();

		ServiceRegistration<TestService> serviceRegistration2 =
			bundleContext.registerService(
				TestService.class, testService2, null);

		ServiceReference<TestService> serviceReference2 =
			serviceRegistration2.getReference();

		testService1._serviceRegistration = serviceRegistration2;

		testService2._serviceRegistration = serviceRegistration1;

		List<ObjectValuePair<ServiceReference<TestService>, Bundle>>
			objectValuePairs = new ArrayList<>();

		ServiceTracker<TestService, TestService> serviceTracker =
			new ServiceTracker<TestService, TestService>(
				bundleContext, TestService.class, null) {

				@Override
				public void removedService(
					ServiceReference<TestService> serviceReference,
					TestService testService) {

					objectValuePairs.add(
						new ObjectValuePair<>(
							serviceReference, serviceReference.getBundle()));

					ServiceRegistration<TestService> serviceRegistration =
						testService._serviceRegistration;

					serviceRegistration.unregister();

					super.removedService(serviceReference, testService);
				}

			};

		serviceTracker.open();

		serviceTracker.close();

		Assert.assertEquals(
			objectValuePairs.toString(), 2, objectValuePairs.size());

		ObjectValuePair<ServiceReference<TestService>, Bundle> objectValuePair =
			objectValuePairs.get(0);

		if (serviceReference1 == objectValuePair.getKey()) {
			Assert.assertSame(bundle, objectValuePair.getValue());

			objectValuePair = objectValuePairs.get(1);

			Assert.assertSame(serviceReference2, objectValuePair.getKey());
			Assert.assertNull(objectValuePair.getValue());
		}
		else {
			Assert.assertSame(serviceReference2, objectValuePair.getKey());
			Assert.assertSame(bundle, objectValuePair.getValue());

			objectValuePair = objectValuePairs.get(1);

			Assert.assertSame(serviceReference1, objectValuePair.getKey());
			Assert.assertNull(objectValuePair.getValue());
		}
	}

	private class TestService {

		private ServiceRegistration<TestService> _serviceRegistration;

	}

}