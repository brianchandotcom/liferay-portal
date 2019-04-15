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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.remote.cors.test.internal.CORSTestApplication;
import com.liferay.portal.remote.cors.test.internal.activator.BaseTestPreparatorBundleActivator;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Assert;
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
	public void testCORSApplication() {
		WebTarget webTarget = getWebTarget("/cors/cors-app");

		Invocation.Builder invocationBuilder = webTarget.request();

		invocationBuilder.header("Origin", _TEST_CORS_URI);
		invocationBuilder.header(
			_ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET);

		Response response = invocationBuilder.options();

		Assert.assertEquals(
			_TEST_CORS_URI,
			response.getHeaderString(_ACCESS_CONTROL_ALLOW_ORIGIN));

		response = invocationBuilder.get();

		Assert.assertEquals(
			_TEST_CORS_URI,
			response.getHeaderString(_ACCESS_CONTROL_ALLOW_ORIGIN));
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotEquals(
			StringPool.BLANK, response.readEntity(String.class));

		getWebTarget("/test-cors-url/cors-app");

		invocationBuilder = webTarget.request();

		invocationBuilder.header("Origin", _TEST_CORS_URI);
		invocationBuilder.header(
			_ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET);

		response = invocationBuilder.options();

		Assert.assertEquals(
			_TEST_CORS_URI,
			response.getHeaderString(_ACCESS_CONTROL_ALLOW_ORIGIN));

		response = invocationBuilder.get();

		Assert.assertEquals(
			_TEST_CORS_URI,
			response.getHeaderString(_ACCESS_CONTROL_ALLOW_ORIGIN));
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotEquals(
			StringPool.BLANK, response.readEntity(String.class));

		webTarget = getWebTarget("/no-cors/cors-app");

		invocationBuilder = webTarget.request();

		invocationBuilder.header("Origin", _TEST_CORS_URI);
		invocationBuilder.header(
			_ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET);

		response = invocationBuilder.options();

		Assert.assertNull(
			response.getHeaderString(_ACCESS_CONTROL_ALLOW_ORIGIN));

		response = invocationBuilder.get();

		Assert.assertNull(
			response.getHeaderString(_ACCESS_CONTROL_ALLOW_ORIGIN));
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotEquals(
			StringPool.BLANK, response.readEntity(String.class));

		webTarget = getWebTarget("/test-cors-wrong-url/cors-app");

		invocationBuilder = webTarget.request();

		invocationBuilder.header("Origin", _TEST_CORS_URI);
		invocationBuilder.header(
			_ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET);

		response = invocationBuilder.options();

		Assert.assertNull(
			response.getHeaderString(_ACCESS_CONTROL_ALLOW_ORIGIN));

		response = invocationBuilder.get();

		Assert.assertNull(
			response.getHeaderString(_ACCESS_CONTROL_ALLOW_ORIGIN));
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotEquals(
			StringPool.BLANK, response.readEntity(String.class));
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

	private static final String _ACCESS_CONTROL_ALLOW_ORIGIN =
		"Access-Control-Allow-Origin";

	private static final String _ACCESS_CONTROL_REQUEST_METHOD =
		"Access-Control-Request-Method";

	private static final String _TEST_CORS_URI = "http://test-cors.com";

}