/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 */
@PrepareForTest({ServiceContextFactory.class, PropsUtil.class})
@RunWith(PowerMockRunner.class)
public class JSONWebServiceTest extends BaseJSONWebServiceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		mockStatic(PropsUtil.class);

		when(
			PropsUtil.getArray(
				PropsKeys.JSONWS_WEB_SERVICE_INVALID_HTTP_METHODS)
		).thenReturn(
			null
		);

		initPortalServices();

		registerActionClass(CamelFooService.class);
		registerActionClass(FooService.class);
	}

	@Before
	public void setUp() throws Exception {
		Method method = method(
			ServiceContextFactory.class, "getInstance",
			HttpServletRequest.class);

		stub(method).toReturn(new ServiceContext());
	}

	@Test
	public void testArgumentsMatching() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello-world");

		try {
			lookupJSONWebServiceAction(mockHttpServletRequest);

			Assert.fail();
		}
		catch (RuntimeException re) {
		}

		mockHttpServletRequest = createHttpRequest(
			"/foo/hello-world/user-id/173/world-name/Forbidden Planet");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			"Welcome 173 to Forbidden Planet", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest("/foo/hello-world");

		mockHttpServletRequest.setParameter("userId", "371");
		mockHttpServletRequest.setParameter("worldName", "Impossible Planet");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			"Welcome 371 to Impossible Planet", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/hello-world/user-id/173");

		mockHttpServletRequest.setParameter("worldName", "Impossible Planet");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			"Welcome 173 to Impossible Planet", jsonWebServiceAction.invoke());
	}

	@Test
	public void testCamelCaseNormalizedParameters() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/camel/good-name/goodboy/bad-name/badboy");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		String result = (String)jsonWebServiceAction.invoke();

		Assert.assertEquals("goodboy*badboy", result);

		mockHttpServletRequest = createHttpRequest("/foo/camel");

		mockHttpServletRequest.setParameter("goodName", "goodboy");
		mockHttpServletRequest.setParameter("badNAME", "badboy");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		result = (String)jsonWebServiceAction.invoke();

		Assert.assertEquals("goodboy*badboy", result);

		mockHttpServletRequest.removeAllParameters();
		mockHttpServletRequest.setParameter("goodName", "goodboy");
		mockHttpServletRequest.setParameter("badName", "badboy");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		result = (String)jsonWebServiceAction.invoke();

		Assert.assertEquals("goodboy*badboy", result);
	}

	@Test
	public void testCreateArgumentInstances() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/use1/+foo-data");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			"using #1: h=177/id=-1/n=John Doe/v=foo!",
			jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest("/foo/use2/+foo-data");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		try {
			jsonWebServiceAction.invoke();

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		mockHttpServletRequest = createHttpRequest(
			"/foo/use2/+foo-data:" + FooDataImpl.class.getName());

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			"using #2: h=177/id=-1/n=John Doe/v=foo!",
			jsonWebServiceAction.invoke());
	}

	@Test
	public void testCreateArgumentInstancesWithJSONData() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/use1");

		mockHttpServletRequest.setParameter(
			"fooData", "{height: 121, name:'Felix', value:'!!!'}");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			"using #1: h=121/id=-1/n=Felix/v=!!!",
			jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest("/foo/use2");

		mockHttpServletRequest.setParameter(
			"fooData", "{height: 121, name:'Felix', value:'!!!'}");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		try {
			jsonWebServiceAction.invoke();

			Assert.fail();
		}
		catch (Exception e) {
		}

	}

	@Test
	public void testDefaultServiceContext() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/srvcctx");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			ServiceContext.class.getName(), jsonWebServiceAction.invoke());
	}

	@Test
	public void testInnerParameters() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/use1/+foo-data/foo-data.value/bar!");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			"using #1: h=177/id=-1/n=John Doe/v=bar!",
			jsonWebServiceAction.invoke());
	}

	@Test
	public void testMatchingOverload() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/method-one/id/123");

		try {
			lookupJSONWebServiceAction(mockHttpServletRequest);

			Assert.fail();
		}
		catch (Exception e) {
		}

		mockHttpServletRequest = createHttpRequest(
			"/foo/method-one/id/123/name/Name");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals("m-1", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/method-one/id/123/name-id/321");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals("m-2", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/method-one.3/id/123/name-id/321");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals("m-3", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/method-one/id/123/name/Name/name-id/321");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals("m-1", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest("/foo/method-one.2/id/123");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals("m-1", jsonWebServiceAction.invoke());
	}

	@Test
	public void testModifyServiceContext() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/srvcctx2");

		mockHttpServletRequest.setParameter(
			"serviceContext", "{'failOnPortalException' : false}");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		ServiceContext serviceContext =
			(ServiceContext)jsonWebServiceAction.invoke();

		Assert.assertFalse(serviceContext.isFailOnPortalException());
	}

	@Test
	public void testNaming() {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/not-found");

		try {
			lookupJSONWebServiceAction(mockHttpServletRequest);

			Assert.fail();
		}
		catch (RuntimeException re) {
		}

		mockHttpServletRequest = createHttpRequest("/foo/hello");

		Assert.assertNotNull(
			lookupJSONWebServiceAction(mockHttpServletRequest));

		mockHttpServletRequest = createHttpRequest("/camelfoo/hello");

		Assert.assertNotNull(
			lookupJSONWebServiceAction(mockHttpServletRequest));

		mockHttpServletRequest = createHttpRequest("/camelfoo/hello-world");

		Assert.assertNotNull(
			lookupJSONWebServiceAction(mockHttpServletRequest));

		mockHttpServletRequest = createHttpRequest("/camelfoo/brave-new-world");

		try {
			lookupJSONWebServiceAction(mockHttpServletRequest);

			Assert.fail();
		}
		catch (RuntimeException re) {
		}

		mockHttpServletRequest = createHttpRequest("/camelfoo/cool-new-world");

		Assert.assertNotNull(
			lookupJSONWebServiceAction(mockHttpServletRequest));
	}

	@Test
	public void testNullValues() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/null-lover");

		mockHttpServletRequest.setParameter("-name", "");
		mockHttpServletRequest.setParameter("number", "173");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals("null!", jsonWebServiceAction.invoke());

		mockHttpServletRequest.setParameter("name", "liferay");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals("[liferay|173]", jsonWebServiceAction.invoke());

		mockHttpServletRequest = createHttpRequest(
			"/foo/null-lover/-name/number/173");

		jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals("null!", jsonWebServiceAction.invoke());
	}

	@Test
	public void testSimpleMethod() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertNotNull(jsonWebServiceAction);

		Assert.assertEquals("world", jsonWebServiceAction.invoke());
	}

	@Test
	public void testTypeConversion1() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hey");

		mockHttpServletRequest.setParameter("calendar", "1330419334285");
		mockHttpServletRequest.setParameter("userIds", "1,2,3");
		mockHttpServletRequest.setParameter("locales", "en,fr");
		mockHttpServletRequest.setParameter("ids", "173,-7,007");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			"2012, 1/3, en/2, 173/3", jsonWebServiceAction.invoke());
	}

	@Test
	public void testTypeConversion2() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hey");

		mockHttpServletRequest.setParameter("calendar", "1330419334285");
		mockHttpServletRequest.setParameter("userIds", "[1,2,3]");
		mockHttpServletRequest.setParameter("locales", "[en,fr]");
		mockHttpServletRequest.setParameter("ids", "[173,-7,007]");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertEquals(
			"2012, 1/3, en/2, 173/3", jsonWebServiceAction.invoke());
	}

}