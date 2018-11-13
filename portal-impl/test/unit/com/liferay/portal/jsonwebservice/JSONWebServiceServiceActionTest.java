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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PropsImpl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public class JSONWebServiceServiceActionTest
	extends BaseJSONWebServiceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		initPortalServices();

		Class<?> clazz = JSONWebServiceServiceAction.class;

		PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PropsUtil.setProps(new PropsImpl());

		_jsonWebServiceServiceAction = new JSONWebServiceServiceAction();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PortalClassLoaderUtil.setClassLoader(null);
	}

	@Before
	public void setUp() {
		JSONWebServiceActionsManagerUtil jsonWebServiceActionsManagerUtil =
			new JSONWebServiceActionsManagerUtil();

		jsonWebServiceActionsManagerUtil.setJSONWebServiceActionsManager(
			new JSONWebServiceActionsManagerImpl());
	}

	@Test
	public void testInvokerNullCall() throws Exception {
		registerActionClass(FooService.class);

		Map<String, Object> map = new LinkedHashMap<>();

		Map<String, Object> params = new LinkedHashMap<>();

		map.put("/foo/null-return", params);

		String json = toJSON(map);

		MockHttpServletRequest mockHttpServletRequest =
			createInvokerHttpServletRequest(json);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		json = _jsonWebServiceServiceAction.getJSON(
			null, null, mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals("{}", json);
	}

	@Test
	public void testInvokerSimpleCall() throws Exception {
		registerActionClass(FooService.class);

		Map<String, Object> map = new LinkedHashMap<>();

		Map<String, Object> params = new LinkedHashMap<>();

		map.put("/foo/hello-world", params);

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String json = toJSON(map);

		MockHttpServletRequest mockHttpServletRequest =
			createInvokerHttpServletRequest(json);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		json = _jsonWebServiceServiceAction.getJSON(
			null, null, mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	@Test
	public void testMultipartRequest() throws Exception {
		registerActionClass(FooService.class);

		Map<String, FileItem[]> fileParams = new HashMap<>();

		fileParams.put("fileName", null);

		HttpServletRequest httpServletRequest = new UploadServletRequestImpl(
			createHttpRequest("/foo/add-file"), fileParams, null) {

			@Override
			public String getFileName(String name) {
				return "test";
			}

		};

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			httpServletRequest);

		Assert.assertNotNull(jsonWebServiceAction);
	}

	@Test
	public void testServletContextInvoker1() throws Exception {
		testServletContextInvoker("somectx", true, "/foo/hello-world");
	}

	@Test
	public void testServletContextInvoker2() throws Exception {
		testServletContextInvoker("somectx", false, "/somectx.foo/hello-world");
	}

	@Test
	public void testServletContextRequestParams1() throws Exception {
		testServletContextRequestParams("somectx", true, "/foo/hello-world");
	}

	@Test
	public void testServletContextRequestParams2() throws Exception {
		testServletContextRequestParams(
			"somectx", false, "/somectx.foo/hello-world");
	}

	@Test
	public void testServletContextURL1() throws Exception {
		testServletContextURL(
			"somectx", true, "/foo/hello-world/user-id/173/world-name/Jupiter");
	}

	@Test
	public void testServletContextURL2() throws Exception {
		testServletContextURL(
			"somectx", false,
			"/somectx.foo/hello-world/user-id/173/world-name/Jupiter");
	}

	protected MockHttpServletRequest createInvokerHttpServletRequest(
		String content) {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoke");

		mockHttpServletRequest.setContent(content.getBytes());
		mockHttpServletRequest.setMethod(HttpMethods.POST);
		mockHttpServletRequest.setRemoteUser("root");

		return mockHttpServletRequest;
	}

	protected void testServletContextInvoker(
			String contextName, boolean setContextPath, String query)
		throws Exception {

		registerActionClass(FooService.class, contextName);

		Map<String, Object> map = new LinkedHashMap<>();

		Map<String, Object> params = new LinkedHashMap<>();

		map.put(query, params);

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String json = toJSON(map);

		MockHttpServletRequest mockHttpServletRequest =
			createInvokerHttpServletRequest(json);

		if (setContextPath) {
			setServletContext(mockHttpServletRequest, contextName);
		}

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		json = _jsonWebServiceServiceAction.getJSON(
			null, null, mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	protected void testServletContextRequestParams(
			String contextName, boolean setContextPath, String request)
		throws Exception {

		registerActionClass(FooService.class, contextName);

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			request);

		mockHttpServletRequest.setParameter("userId", "173");
		mockHttpServletRequest.setParameter("worldName", "Jupiter");

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		if (setContextPath) {
			setServletContext(mockHttpServletRequest, contextName);
		}

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		String json = _jsonWebServiceServiceAction.getJSON(
			null, null, mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	protected void testServletContextURL(
			String contextName, boolean setContextPath, String request)
		throws Exception {

		registerActionClass(FooService.class, contextName);

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			request);

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		if (setContextPath) {
			setServletContext(mockHttpServletRequest, contextName);
		}

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		String json = _jsonWebServiceServiceAction.getJSON(
			null, null, mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	private static JSONWebServiceServiceAction _jsonWebServiceServiceAction;

}