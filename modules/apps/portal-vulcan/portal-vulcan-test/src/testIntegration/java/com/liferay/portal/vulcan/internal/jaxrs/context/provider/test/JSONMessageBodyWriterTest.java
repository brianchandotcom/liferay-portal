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
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.vulcan.internal.test.util.URLConnectionUtil;

import java.util.Collections;
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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alejandro Hernández
 */
@RunWith(Arquillian.class)
public class JSONMessageBodyWriterTest {

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(
			JSONMessageBodyWriterTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			Application.class, new TestApplication(),
			HashMapDictionaryBuilder.<String, Object>put(
				"liferay.auth.verifier", true
			).put(
				"liferay.jackson", false
			).put(
				"liferay.oauth2", false
			).put(
				"osgi.jaxrs.application.base", "/test-vulcan"
			).put(
				"osgi.jaxrs.extension.select",
				"(osgi.jaxrs.name=Liferay.Vulcan)"
			).build());
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testFieldsFilterNestedJSONObject() throws Exception {
		JSONObject jsonObject = _createJSONObject(
			"http://localhost:8080/o/test-vulcan/test-class?" +
				"fields=string,testClass,testClass.number");

		Assert.assertFalse(jsonObject.has("number"));
		Assert.assertEquals("hello", jsonObject.getString("string"));

		JSONObject testClassJSONObject = jsonObject.getJSONObject("testClass");

		Assert.assertEquals(6L, testClassJSONObject.getLong("number"));
		Assert.assertFalse(testClassJSONObject.has("string"));
		Assert.assertFalse(testClassJSONObject.has("testClass"));
	}

	@Test
	public void testFieldsFilterRootJSONObject() throws Exception {
		JSONObject jsonObject = _createJSONObject(
			"http://localhost:8080/o/test-vulcan/test-class?fields=string");

		Assert.assertFalse(jsonObject.has("number"));
		Assert.assertFalse(jsonObject.has("testClass"));
		Assert.assertEquals("hello", jsonObject.getString("string"));
	}

	@Test
	public void testIsWrittenToJSON() throws Exception {
		JSONObject jsonObject = _createJSONObject(
			"http://localhost:8080/o/test-vulcan/test-class");

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

	private JSONObject _createJSONObject(String urlString) throws Exception {
		return JSONFactoryUtil.createJSONObject(
			URLConnectionUtil.read(urlString));
	}

	private ServiceRegistration<Application> _serviceRegistration;

}