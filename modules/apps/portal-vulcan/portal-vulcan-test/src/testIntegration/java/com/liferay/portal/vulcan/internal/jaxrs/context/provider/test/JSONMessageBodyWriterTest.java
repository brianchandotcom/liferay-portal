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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider.test;

import com.fasterxml.jackson.annotation.JsonFilter;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Hernández
 */
@RunWith(Arquillian.class)
public class JSONMessageBodyWriterTest {

	@Before
	public void setUp() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("liferay.auth.verifier", true);
		properties.put("liferay.oauth2", false);
		properties.put("osgi.jaxrs.application.base", "/test-vulcan");
		properties.put(
			"osgi.jaxrs.extension.select", "(osgi.jaxrs.name=Liferay.Vulcan)");

		_serviceRegistration = registry.registerService(
			Application.class, new TestApplication(), properties);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testFieldsFilterNestedJSONObject() throws Exception {
		URL url = new URL(
			"http://localhost:8080/o/test-vulcan/test-class?" +
				"fields=string,testClass,testClass.number");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			StringUtil.read(url.openStream()));

		Assert.assertFalse(jsonObject.has("number"));
		Assert.assertEquals("hello", jsonObject.getString("string"));

		JSONObject testClassJSONObject = jsonObject.getJSONObject("testClass");

		Assert.assertEquals(6L, testClassJSONObject.getLong("number"));
		Assert.assertFalse(testClassJSONObject.has("string"));
		Assert.assertFalse(testClassJSONObject.has("testClass"));
	}

	@Test
	public void testFieldsFilterRootJSONObject() throws Exception {
		URL url = new URL(
			"http://localhost:8080/o/test-vulcan/test-class?fields=string");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			StringUtil.read(url.openStream()));

		Assert.assertFalse(jsonObject.has("number"));
		Assert.assertFalse(jsonObject.has("testClass"));
		Assert.assertEquals("hello", jsonObject.getString("string"));
	}

	@Test
	public void testIsWrittenToJSON() throws Exception {
		URL url = new URL("http://localhost:8080/o/test-vulcan/test-class");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			StringUtil.read(url.openStream()));

		Assert.assertEquals(1L, jsonObject.getLong("number"));
		Assert.assertEquals("hello", jsonObject.getString("string"));

		JSONObject testClassJSONObject = jsonObject.getJSONObject("testClass");

		Assert.assertEquals(6L, testClassJSONObject.getLong("number"));
		Assert.assertEquals("hi", testClassJSONObject.getString("string"));
		Assert.assertTrue(testClassJSONObject.isNull("testClass"));
	}

	public static class TestApplication extends Application {

		@Override
		public Set<Object> getSingletons() {
			return Collections.singleton(this);
		}

		@GET
		@Path("/test-class")
		@Produces(MediaType.APPLICATION_JSON)
		public TestClass testClass() {
			return TestClass.of(1L, "hello", TestClass.of(6L, "hi", null));
		}

		@JsonFilter("Liferay.Vulcan")
		public static class TestClass {

			public static TestClass of(
				Long number, String string, TestClass testClass) {

				return new TestClass(number, string, testClass);
			}

			public TestClass(Long number, String string, TestClass testClass) {
				this.number = number;
				this.string = string;
				this.testClass = testClass;
			}

			public final Long number;
			public final String string;
			public final TestClass testClass;

		}

	}

	private ServiceRegistration<Application> _serviceRegistration;

}