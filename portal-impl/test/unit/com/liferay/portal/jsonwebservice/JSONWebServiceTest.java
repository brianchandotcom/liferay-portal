/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.jsonwebservice.dependencies.CamelFooService;
import com.liferay.portal.jsonwebservice.dependencies.FooService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.service.ServiceContext;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceTest extends JSONWebServiceAbstractTest {

	@BeforeClass
	public static void init() throws Exception {
		initPortalServices();

		registerActionClass(FooService.class);
		registerActionClass(CamelFooService.class);
	}

	@Test
	public void testArgumentsMatching() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello-world");

		try {
			lookupAction(mockHttpServletRequest);

			fail();
		}
		catch (RuntimeException ignore) {
		}

		mockHttpServletRequest = createHttpRequest(
			"/foo/hello-world/user-id/173/world-name/Forbidden Planet");

		JSONWebServiceAction jsonWebServiceAction = lookupAction(
			mockHttpServletRequest);

		assertEquals(
			"Welcome 173 to Forbidden Planet", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest("/foo/hello-world");

		mockHttpServletRequest.setParameter("userId", "371");
		mockHttpServletRequest.setParameter("worldName", "Impossible Planet");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		assertEquals(
			"Welcome 371 to Impossible Planet", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/hello-world/user-id/173");

		mockHttpServletRequest.setParameter("worldName", "Impossible Planet");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		assertEquals(
			"Welcome 173 to Impossible Planet", jsonWebServiceAction.invoke());
	}

	@Test
	public void testCreateArgumentInstances() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/use1/+foo-data");

		JSONWebServiceAction jsonWebServiceAction = lookupAction(
			mockHttpServletRequest);

		assertEquals("using #1: foo!", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest("/foo/use2/+foo-data");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		try {
			jsonWebServiceAction.invoke();

			fail();
		}
		catch (IllegalArgumentException ignore) {
		}

		mockHttpServletRequest = createHttpRequest(
			"/foo/use2/+foo-data:" +
				"com.liferay.portal.jsonwebservice.dependencies.FooDataImpl");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		assertEquals("using #2: foo!", jsonWebServiceAction.invoke());
	}

	@Test
	public void testDefaultServiceContext() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/srvcctx");

		JSONWebServiceAction jsonWebServiceAction = lookupAction(
			mockHttpServletRequest);

		assertEquals(
			ServiceContext.class.getName(), jsonWebServiceAction.invoke());
	}

	@Test
	public void testInnerParameters() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/use1/+foo-data/foo-data.value/bar!");

		JSONWebServiceAction jsonWebServiceAction = lookupAction(
			mockHttpServletRequest);

		assertEquals("using #1: bar!", jsonWebServiceAction.invoke());
	}

	@Test
	public void testMatchingOverload() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/method-one/id/123");

		try {
			lookupAction(mockHttpServletRequest);

			fail();
		}
		catch (Exception ignore) {
		}

		mockHttpServletRequest = createHttpRequest(
			"/foo/method-one/id/123/name/Name");

		JSONWebServiceAction jsonWebServiceAction = lookupAction(
			mockHttpServletRequest);

		assertEquals("m-1", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/method-one/id/123/name-id/321");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		assertEquals("m-2", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/method-one.3/id/123/name-id/321");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		assertEquals("m-3", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/method-one/id/123/name/Name/name-id/321");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		assertEquals("m-1", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest("/foo/method-one.2/id/123");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		assertEquals("m-1", jsonWebServiceAction.invoke());
	}

	@Test
	public void testNaming() {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/not-found");

		try {
			lookupAction(mockHttpServletRequest);

			fail();
		}
		catch (RuntimeException ignore) {
		}

		mockHttpServletRequest = createHttpRequest("/foo/hello");

		assertNotNull(lookupAction(mockHttpServletRequest));

		mockHttpServletRequest = createHttpRequest("/camelfoo/hello");

		assertNotNull(lookupAction(mockHttpServletRequest));

		mockHttpServletRequest = createHttpRequest("/camelfoo/hello-world");

		assertNotNull(lookupAction(mockHttpServletRequest));

		mockHttpServletRequest = createHttpRequest("/camelfoo/brave-new-world");

		try {
			lookupAction(mockHttpServletRequest);

			fail();
		}
		catch (RuntimeException ignore) {
		}

		mockHttpServletRequest = createHttpRequest("/camelfoo/cool-new-world");

		assertNotNull(lookupAction(mockHttpServletRequest));
	}

	@Test
	public void testNullValues() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/null-lover");

		mockHttpServletRequest.setParameter("-name", "");
		mockHttpServletRequest.setParameter("number", "173");

		JSONWebServiceAction jsonWebServiceAction = lookupAction(
			mockHttpServletRequest);

		assertEquals("null!", jsonWebServiceAction.invoke());

		mockHttpServletRequest.setParameter("name", "liferay");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		assertEquals("[liferay|173]", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/null-lover/-name/number/173");

		jsonWebServiceAction = lookupAction(mockHttpServletRequest);

		assertEquals("null!", jsonWebServiceAction.invoke());
	}

	@Test
	public void testSimpleMethod() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello");

		JSONWebServiceAction jsonWebServiceAction = lookupAction(
			mockHttpServletRequest);

		assertNotNull(jsonWebServiceAction);

		assertEquals("world", jsonWebServiceAction.invoke());
	}

	@Test
	public void testTypeConversion() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hey");

		mockHttpServletRequest.setParameter("calendar", "1330419334285");
		mockHttpServletRequest.setParameter("userIds", "1,2,3");
		mockHttpServletRequest.setParameter("locales", "[en,fr]");

		JSONWebServiceAction jsonWebServiceAction = lookupAction(
			mockHttpServletRequest);

		assertEquals("2012, 1/3, en/2", jsonWebServiceAction.invoke());
	}

}