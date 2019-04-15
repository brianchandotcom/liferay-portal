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

package com.liferay.portal.remote.jaxrs.whiteboard.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

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
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class JaxRsComponentRegistrationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			JaxRsComponentRegistrationTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("liferay.auth.verifier", false);
		properties.put("liferay.oauth2", false);
		properties.put("osgi.jaxrs.application.base", "/test-rest/greeter1");

		_serviceRegistrations.add(
			bundleContext.registerService(
				Application.class, new Greeter(), properties));

		properties.put("osgi.jaxrs.application.base", "/test-rest/greeter2");

		_serviceRegistrations.add(
			bundleContext.registerService(
				Application.class, new Greeter(), properties));

		properties.put("addonable", Boolean.TRUE);
		properties.put("osgi.jaxrs.application.base", "/test-rest/greeter3");

		_serviceRegistrations.add(
			bundleContext.registerService(
				Application.class, new Greeter(), properties));

		properties = new HashMapDictionary<>();

		properties.put("osgi.jaxrs.application.select", "(addonable=true)");
		properties.put("osgi.jaxrs.resource", Boolean.TRUE);

		_serviceRegistrations.add(
			bundleContext.registerService(
				Object.class, new Addon(), properties));
	}

	@AfterClass
	public static void tearDownClass() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testIsRegistered() throws Exception {
		URL url = new URL(
			"http://localhost:8080/o/test-rest/greeter1/sayHello");

		Assert.assertEquals("Hello.", StringUtil.read(url.openStream()));

		url = new URL("http://localhost:8080/o/test-rest/greeter2/sayHello");

		Assert.assertEquals("Hello.", StringUtil.read(url.openStream()));

		url = new URL("http://localhost:8080/o/test-rest/greeter3/sayHello");

		Assert.assertEquals("Hello.", StringUtil.read(url.openStream()));

		url = new URL("http://localhost:8080/o/test-rest/greeter3/addon");

		Assert.assertEquals("addon", StringUtil.read(url.openStream()));
	}

	public static class Addon {

		@GET
		@Path("/addon")
		public String addon() {
			return "addon";
		}

	}

	public static class Greeter extends Application {

		@Override
		public Set<Object> getSingletons() {
			return Collections.singleton(this);
		}

		@GET
		@Path("/sayHello")
		@Produces("text/plain")
		public String sayHello() {
			return "Hello.";
		}

	}

	private static final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}