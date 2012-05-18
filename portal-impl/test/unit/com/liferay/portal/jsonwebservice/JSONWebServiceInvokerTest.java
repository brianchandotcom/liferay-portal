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

import com.liferay.portal.jsonwebservice.action.JSONWebServiceInvokerAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceInvokerTest extends BaseJSONWebServiceTestCase {

	@BeforeClass
	public static void init() throws Exception {
		initPortalServices();

		registerActionClass(FooService.class);
	}

	@Test
	public void testBatchCalls() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hello-world", params);

		params.put("userId", Integer.valueOf(173));

		params.put("worldName", "Jupiter");

		String command = toJSON(map);

		command = "[" + command + ", " + command + "]";

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(
			command);

		Object result = jsonWebServiceAction.invoke();

		assertTrue(result instanceof List);

		String jsonResult = toJSON(result);

		assertEquals(
			"[\"Welcome 173 to Jupiter\",\"Welcome 173 to Jupiter\"]",
			jsonResult);
	}

	@Test
	public void testFiltering() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("$data[id, world] = /foo/get-foo-data", params);

		params.put("id", Integer.valueOf(173));

		Map<String, Object> map2 = new LinkedHashMap<String, Object>();

		params.put("$world = /foo/hello-world", map2);

		map2.put("@userId", "$data.id");

		map2.put("worldName", "Jupiter");

		String command = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(
			command);

		Object result = jsonWebServiceAction.invoke();

		assertTrue(result instanceof Map);

		String jsonResult = toJSON(result);

		assertEquals(
			"{\"id\":173,\" world\":null,\"world\":\"Welcome 173 to Jupiter\"}",
			jsonResult);
	}

	@Test
	public void testInnerCalls() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("$data = /foo/get-foo-data", params);

		params.put("id", Integer.valueOf(173));

		Map<String, Object> map2 = new LinkedHashMap<String, Object>();

		params.put("$world = /foo/hello-world", map2);

		map2.put("@userId", "$data.id");

		map2.put("worldName", "Jupiter");

		String command = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(
			command);

		Object result = jsonWebServiceAction.invoke();

		assertTrue(result instanceof Map);

		String jsonResult = toJSON(result);

		assertEquals(
			"{\"id\":173,\"height\":177,\"name\":\"John Doe\"," +
				"\"value\":\"foo!\",\"world\":\"Welcome 173 to Jupiter\"}",
			jsonResult);
	}

	@Test
	public void testSimpleCall() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hello-world", params);

		params.put("userId", Integer.valueOf(173));

		params.put("worldName", "Jupiter");

		String command = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(
			command);

		assertEquals("Welcome 173 to Jupiter", jsonWebServiceAction.invoke());
	}

	@Test
	public void testSimpleCallUsingCmdParam() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hello-world", params);

		params.put("userId", Integer.valueOf(173));

		params.put("worldName", "Jupiter");

		String command = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(
			command);

		assertEquals("Welcome 173 to Jupiter", jsonWebServiceAction.invoke());
	}

	@Test
	public void testSimpleCallWithName() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("$world = /foo/hello-world", params);

		params.put("userId", Integer.valueOf(173));

		params.put("worldName", "Jupiter");

		String command = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(
			command);

		assertEquals("Welcome 173 to Jupiter", jsonWebServiceAction.invoke());
	}

	protected JSONWebServiceAction prepareInvokerAction(String command)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		mockHttpServletRequest.setContent(command.getBytes());

		return new JSONWebServiceInvokerAction(mockHttpServletRequest);
	}

}