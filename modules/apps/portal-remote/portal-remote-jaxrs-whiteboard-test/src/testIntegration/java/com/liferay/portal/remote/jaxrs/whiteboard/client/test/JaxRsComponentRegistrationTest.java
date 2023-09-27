/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.jaxrs.whiteboard.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
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

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"liferay.auth.verifier", false
			).put(
				"liferay.oauth2", false
			).put(
				"osgi.jaxrs.application.base", "/rest-test/greeter1"
			).build();

		_serviceRegistrations.add(
			bundleContext.registerService(
				Application.class, new Greeter(), properties));

		properties.put("osgi.jaxrs.application.base", "/rest-test/greeter2");

		_serviceRegistrations.add(
			bundleContext.registerService(
				Application.class, new Greeter(), properties));

		properties.put("addonable", Boolean.TRUE);
		properties.put("osgi.jaxrs.application.base", "/rest-test/greeter3");

		_serviceRegistrations.add(
			bundleContext.registerService(
				Application.class, new Greeter(), properties));

		_serviceRegistrations.add(
			bundleContext.registerService(
				Object.class, new Addon(),
				HashMapDictionaryBuilder.<String, Object>put(
					"osgi.jaxrs.application.select", "(addonable=true)"
				).put(
					"osgi.jaxrs.resource", Boolean.TRUE
				).build()));
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
			"http://localhost:8080/o/rest-test/greeter1/sayHello");

		Assert.assertEquals("Hello.", StringUtil.read(url.openStream()));

		url = new URL("http://localhost:8080/o/rest-test/greeter2/sayHello");

		Assert.assertEquals("Hello.", StringUtil.read(url.openStream()));

		url = new URL("http://localhost:8080/o/rest-test/greeter3/sayHello");

		Assert.assertEquals("Hello.", StringUtil.read(url.openStream()));

		url = new URL("http://localhost:8080/o/rest-test/greeter3/addon");

		Assert.assertEquals("addon", StringUtil.read(url.openStream()));
	}

	@Test(expected = Exception.class)
	public void testServiceListIsUnavailable() throws Exception {
		URL url = new URL("http://localhost:8080/o/soap-test/services");

		StringUtil.read(url.openStream());
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