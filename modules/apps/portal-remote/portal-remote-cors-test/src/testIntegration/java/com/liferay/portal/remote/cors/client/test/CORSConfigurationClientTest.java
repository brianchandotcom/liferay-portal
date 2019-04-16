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

package com.liferay.portal.remote.cors.client.test;

import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.remote.cors.test.internal.CORSTestApplication;
import com.liferay.portal.remote.cors.test.internal.activator.BaseTestPreparatorBundleActivator;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marta Medio
 */
@RunAsClient
@RunWith(Arquillian.class)
public class CORSConfigurationClientTest extends BaseCORSClientTestCase {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseCORSClientTestCase.getArchive(
			CORSConfigurationTestPreparatorBundleActivator.class);
	}

	@Test
	public void testCORSApplication() throws Exception {
		assertURL("/cors/cors-app", true);
		assertURL("/test-cors-url/cors-app", true);
		assertURL("/no-cors/cors-app", false);
		assertURL("/test-cors-wrong-url/cors-app", false);
	}

	public static class CORSConfigurationTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		protected void prepareTest() {
			HashMapDictionary<String, Object> properties =
				new HashMapDictionary<>();

			properties.put("osgi.jaxrs.name", "test-cors");

			registerJaxRsApplication(
				new CORSTestApplication(), "cors", properties);

			properties = new HashMapDictionary<>();

			properties.put(
				"servlet.context.helper.select.filter",
				"(osgi.jaxrs.name=test-cors)");

			createFactoryConfiguration(properties);

			properties = new HashMapDictionary<>();

			properties.put("osgi.jaxrs.name", "test-cors-url");

			registerJaxRsApplication(
				new CORSTestApplication(), "test-cors-url", properties);

			properties = new HashMapDictionary<>();

			properties.put("filter.mapping.url.patterns", "/cors-app");
			properties.put(
				"servlet.context.helper.select.filter",
				"(osgi.jaxrs.name=test-cors-url)");

			createFactoryConfiguration(properties);

			registerJaxRsApplication(
				new CORSTestApplication(), "no-cors",
				new HashMapDictionary<>());

			properties = new HashMapDictionary<>();

			properties.put("osgi.jaxrs.name", "test-cors-wrong-url");

			registerJaxRsApplication(
				new CORSTestApplication(), "test-cors-wrong-url", properties);

			properties = new HashMapDictionary<>();

			properties.put("filter.mapping.url.patterns", "/wrong");
			properties.put(
				"servlet.context.helper.select.filter",
				"(osgi.jaxrs.name=test-cors-wrong-url)");

			createFactoryConfiguration(properties);
		}

	}

}