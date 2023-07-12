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

package com.liferay.headless.builder.util;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;

import javax.ws.rs.core.Application;

import org.junit.Assert;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Correa
 */
public class APIApplicationTestUtil {

	public static void assertDeployedAPIApplication(String baseURL)
		throws InterruptedException {

		ServiceTracker<Application, Application> serviceTracker =
			_getServiceTracker(baseURL);

		try {
			Assert.assertNotNull(
				"The API Application has not been deployed",
				serviceTracker.waitForService(10000));
		}
		finally {
			serviceTracker.close();
		}
	}

	public static void assertNotDeployedAPIApplication(String baseURL) {
		ServiceTracker<Application, Application> serviceTracker =
			_getServiceTracker(baseURL);

		try {
			Assert.assertEquals(
				"The API Application is deployed", 0, serviceTracker.size());
		}
		finally {
			serviceTracker.close();
		}
	}

	private static ServiceTracker<Application, Application> _getServiceTracker(
		String baseURL) {

		Bundle testBundle = FrameworkUtil.getBundle(
			APIApplicationTestUtil.class);

		return ServiceTrackerFactory.open(
			testBundle.getBundleContext(),
			StringBundler.concat(
				"(&(objectClass=", Application.class.getName(),
				")(liferay.headless.builder.application=true)",
				"(osgi.jaxrs.application.base=", baseURL, "))"));
	}

}