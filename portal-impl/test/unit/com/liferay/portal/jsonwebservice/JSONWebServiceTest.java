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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.jsonwebservice.dependencies.CamelFooService;
import com.liferay.portal.jsonwebservice.dependencies.FooService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.util.MethodParametersResolverUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.MethodParametersResolverImpl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceTest {

	@BeforeClass
	public static void init() throws Exception {
		new MethodParametersResolverUtil().setMethodParametersResolver(
			new MethodParametersResolverImpl());

		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());

		new JSONWebServiceActionsManagerUtil().setJSONWebServiceActionsManager(
			new JSONWebServiceActionsManagerImpl());

		_registerActionClass(FooService.class);
		_registerActionClass(CamelFooService.class);
	}

	@Test
	public void testArgumentsMatching() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_createHttpRequest("/foo/hello-world");

		try {
			_lookupAction(mockHttpServletRequest);

			fail();
		}
		catch (RuntimeException ignore) {
		}

		mockHttpServletRequest = _createHttpRequest(
			"/foo/hello-world/user-id/173/world-name/Forbidden Planet");

		JSONWebServiceAction jsonWebServiceAction = _lookupAction(
			mockHttpServletRequest);

		assertEquals("Welcome 173 to Forbidden Planet",
			jsonWebServiceAction.invoke());

		mockHttpServletRequest = _createHttpRequest("/foo/hello-world");

		mockHttpServletRequest.setParameter("userId", "371");
		mockHttpServletRequest.setParameter("worldName", "Impossible Planet");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		assertEquals("Welcome 371 to Impossible Planet",
			jsonWebServiceAction.invoke());

		mockHttpServletRequest = _createHttpRequest(
			"/foo/hello-world/user-id/173");

		mockHttpServletRequest.setParameter("worldName", "Impossible Planet");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		assertEquals("Welcome 173 to Impossible Planet",
			jsonWebServiceAction.invoke());
	}

	@Test
	public void testCreateArgumentInstances() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_createHttpRequest("/foo/use1/+foo-data");

		JSONWebServiceAction jsonWebServiceAction = _lookupAction(
			mockHttpServletRequest);

		assertEquals("using #1: foo!", jsonWebServiceAction.invoke());

		mockHttpServletRequest = _createHttpRequest("/foo/use2/+foo-data");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		try {
			jsonWebServiceAction.invoke();

			fail();
		}
		catch (IllegalArgumentException ignore) {
		}

		mockHttpServletRequest = _createHttpRequest("/foo/use2/+foo-data:" +
			"com.liferay.portal.jsonwebservice.dependencies.FooDataImpl");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		assertEquals("using #2: foo!", jsonWebServiceAction.invoke());
	}

	@Test
	public void testDefaultServiceContext() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
					_createHttpRequest("/foo/srvcctx");

		JSONWebServiceAction jsonWebServiceAction = _lookupAction(
					mockHttpServletRequest);

		assertEquals(ServiceContext.class.getName(),
			jsonWebServiceAction.invoke());
	}

	@Test
	public void testInnerParameters() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_createHttpRequest("/foo/use1/+foo-data/foo-data.value/bar!");

		JSONWebServiceAction jsonWebServiceAction = _lookupAction(
			mockHttpServletRequest);

		assertEquals("using #1: bar!", jsonWebServiceAction.invoke());
	}

	@Test
	public void testMatchingOverload() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_createHttpRequest("/foo/method-one/id/123");

		try {
			_lookupAction(mockHttpServletRequest);

			fail();
		}
		catch (Exception ignore) {
		}

		mockHttpServletRequest = _createHttpRequest(
			"/foo/method-one/id/123/name/Name");

		JSONWebServiceAction jsonWebServiceAction = _lookupAction(
			mockHttpServletRequest);

		assertEquals("m-1", jsonWebServiceAction.invoke());

		mockHttpServletRequest = _createHttpRequest(
			"/foo/method-one/id/123/name-id/321");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		assertEquals("m-2", jsonWebServiceAction.invoke());

		mockHttpServletRequest = _createHttpRequest(
			"/foo/method-one.3/id/123/name-id/321");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		assertEquals("m-3", jsonWebServiceAction.invoke());

		mockHttpServletRequest = _createHttpRequest(
			"/foo/method-one/id/123/name/Name/name-id/321");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		assertEquals("m-1", jsonWebServiceAction.invoke());

		mockHttpServletRequest = _createHttpRequest("/foo/method-one.2/id/123");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		assertEquals("m-1", jsonWebServiceAction.invoke());
	}

	@Test
	public void testNaming() {
		MockHttpServletRequest mockHttpServletRequest =
			_createHttpRequest("/foo/not-found");

		try {
			_lookupAction(mockHttpServletRequest);

			fail();
		}
		catch (RuntimeException ignore) {
		}

		mockHttpServletRequest = _createHttpRequest("/foo/hello");

		assertNotNull(_lookupAction(mockHttpServletRequest));

		mockHttpServletRequest = _createHttpRequest("/camelfoo/hello");

		assertNotNull(_lookupAction(mockHttpServletRequest));

		mockHttpServletRequest = _createHttpRequest("/camelfoo/hello-world");

		assertNotNull(_lookupAction(mockHttpServletRequest));

		mockHttpServletRequest = _createHttpRequest(
			"/camelfoo/brave-new-world");

		try {
			_lookupAction(mockHttpServletRequest);

			fail();
		}
		catch (RuntimeException ignore) {
		}

		mockHttpServletRequest = _createHttpRequest("/camelfoo/cool-new-world");

		assertNotNull(_lookupAction(mockHttpServletRequest));
	}

	@Test
	public void testNullValues() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_createHttpRequest("/foo/null-lover");

		mockHttpServletRequest.setParameter("-name", "");
		mockHttpServletRequest.setParameter("number", "173");

		JSONWebServiceAction jsonWebServiceAction = _lookupAction(
			mockHttpServletRequest);

		assertEquals("null!", jsonWebServiceAction.invoke());

		mockHttpServletRequest.setParameter("name", "liferay");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		assertEquals("[liferay|173]", jsonWebServiceAction.invoke());

		mockHttpServletRequest = _createHttpRequest(
			"/foo/null-lover/-name/number/173");

		jsonWebServiceAction = _lookupAction(mockHttpServletRequest);

		assertEquals("null!", jsonWebServiceAction.invoke());
	}

	@Test
	public void testSimpleMethod() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_createHttpRequest("/foo/hello");

		JSONWebServiceAction jsonWebServiceAction = _lookupAction(
			mockHttpServletRequest);

		assertNotNull(jsonWebServiceAction);

		assertEquals("world", jsonWebServiceAction.invoke());
	}

	@Test
	public void testTypeConversion() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_createHttpRequest("/foo/hey");

		mockHttpServletRequest.setParameter("calendar", "1330419334285");
		mockHttpServletRequest.setParameter("userIds", "1,2,3");
		mockHttpServletRequest.setParameter("locales", "[en,fr]");

		JSONWebServiceAction jsonWebServiceAction = _lookupAction(
			mockHttpServletRequest);

		assertEquals("2012, 1/3, en/2", jsonWebServiceAction.invoke());
	}

	private static void _registerActionClass(Class actionClass) {

		JSONWebServiceMappingResolver jsonWebServiceMappingResolver =
				new JSONWebServiceMappingResolver();

		Method[] methods = actionClass.getMethods();

		for (Method actionMethod : methods) {
			if (actionMethod.getDeclaringClass() != actionClass) {
				continue;
			}

			String actionPath = jsonWebServiceMappingResolver.resolvePath(
				actionClass, actionMethod);

			String actionHttpMethod =
				jsonWebServiceMappingResolver.resolveHttpMethod(actionMethod);

			JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
				"", actionClass, actionMethod, actionPath, actionHttpMethod);
		}
	}

	private MockHttpServletRequest _createHttpRequest(String pathInfo) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(pathInfo);

		return mockHttpServletRequest;
	}

	private JSONWebServiceAction _lookupAction(
		HttpServletRequest httpServletRequest) {

		return JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
			httpServletRequest);
	}

}