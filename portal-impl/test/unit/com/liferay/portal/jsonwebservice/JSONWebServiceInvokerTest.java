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

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

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

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String json = toJSON(map);

		json = "[" + json + ", " + json + "]";

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertTrue(result instanceof List);
		Assert.assertEquals(
			"[\"Welcome 173 to Jupiter\",\"Welcome 173 to Jupiter\"]",
			toJSON(invokerResult));
	}

	@Test
	public void testFiltering() throws Exception {
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map1.put("$data[id] = /foo/get-foo-data", params);

		params.put("id", 173);

		Map<String, Object> map2 = new LinkedHashMap<String, Object>();

		params.put("$world = /foo/hello-world", map2);

		map2.put("@userId", "$data.id");
		map2.put("worldName", "Jupiter");

		String json = toJSON(map1);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertTrue(result instanceof Map);
		Assert.assertEquals(
			"{\"id\":173,\"world\":\"Welcome 173 to Jupiter\"}",
			toJSON(invokerResult));
	}

	@Test
	public void testInnerCalls() throws Exception {
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map1.put("$data = /foo/get-foo-data", params);

		params.put("id", 173);

		Map<String, Object> map2 = new LinkedHashMap<String, Object>();

		params.put("$world = /foo/hello-world", map2);

		map2.put("@userId", "$data.id");
		map2.put("worldName", "Jupiter");

		String json = toJSON(map1);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertTrue(result instanceof Map);
		Assert.assertEquals(
			"{\"id\":173,\"height\":177,\"name\":\"John Doe\",\"value\":" +
				"\"foo!\",\"world\":\"Welcome 173 to Jupiter\"}",
			toJSON(invokerResult));
	}

	@Test
	public void testListFiltering() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("$world[id] = /foo/get-foo-datas", params);

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertTrue(result instanceof List);
		Assert.assertEquals(
			"[{\"id\":1},{\"id\":2},{\"id\":3}]", toJSON(invokerResult));
	}

	@Test
	public void testSimpleCall() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hello-world", params);

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertEquals("Welcome 173 to Jupiter", result);
		Assert.assertEquals(
			"\"Welcome 173 to Jupiter\"", toJSON(invokerResult));
	}

	@Test
	public void testSimpleCallUsingCmdParam() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hello-world", params);

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String command = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(
			command);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertEquals("Welcome 173 to Jupiter", result);
		Assert.assertEquals(
			"\"Welcome 173 to Jupiter\"", toJSON(invokerResult));
	}

	@Test
	public void testSimpleCallWithName() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("$world = /foo/hello-world", params);

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertEquals("Welcome 173 to Jupiter", result);
		Assert.assertEquals(
			"\"Welcome 173 to Jupiter\"", toJSON(invokerResult));
	}

	@Test
	public void testSimpleCallWithNull() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hello-world", params);

		params.put("userId", 173);
		params.put("worldName", null);

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertEquals("Welcome 173 to null", result);
		Assert.assertEquals(
			"\"Welcome 173 to null\"", toJSON(invokerResult));
	}

	protected JSONWebServiceAction prepareInvokerAction(String content)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		mockHttpServletRequest.setContent(content.getBytes());

		return new JSONWebServiceInvokerAction(mockHttpServletRequest);
	}

}