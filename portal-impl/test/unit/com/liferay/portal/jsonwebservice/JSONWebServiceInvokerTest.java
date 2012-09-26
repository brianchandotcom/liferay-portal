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
import com.liferay.portal.kernel.util.StringBundler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
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
	public void testInnerCallsNested() throws Exception {
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map1.put("$data = /foo/get-foo-data", params);

		params.put("id", 173);

		Map<String, Object> map2 = new LinkedHashMap<String, Object>();

		params.put("$spy = /foo/get-foo-data", map2);

		map2.put("id", "007");

		Map<String, Object> map3 = new LinkedHashMap<String, Object>();

		map2.put("$thief = /foo/get-foo-data", map3);

		map3.put("id", -13);

		Map<String, Object> map4 = new LinkedHashMap<String, Object>();

		map3.put("$world = /foo/hello-world", map4);

		map4.put("@userId", "$thief.id");
		map4.put("worldName", "Jupiter");

		String json = toJSON(map1);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertTrue(result instanceof Map);

		StringBundler sb = new StringBundler(5);

		sb.append("{\"id\":173,\"height\":177,\"spy\":{\"id\":7,\"height\":");
		sb.append("173,\"name\":\"James Bond\",\"value\":\"licensed\",");
		sb.append("\"thief\":{\"id\":-13,\"height\":59,\"name\":\"Dr. Evil\",");
		sb.append("\"value\":\"fun\",\"world\":\"Welcome -13 to Jupiter\"}},");
		sb.append("\"name\":\"John Doe\",\"value\":\"foo!\"}");

		Assert.assertEquals(sb.toString(), toJSON(invokerResult));
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
	public void testListFilteringAndFlags() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("$world[id] = /foo/get-foo-datas", params);

		Map<String, Object> map2 = new LinkedHashMap<String, Object>();

		params.put("$resource[id,value] = /foo/get-foo-data", map2);

		map2.put("@id", "$world.id");

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertTrue(result instanceof List);
		Assert.assertEquals(
			"[{\"id\":1,\"resource\":{\"id\":1,\"value\":\"foo!\"}}," +
			"{\"id\":2,\"resource\":{\"id\":2,\"value\":\"foo!\"}}," +
			"{\"id\":3,\"resource\":{\"id\":3,\"value\":\"foo!\"}}]",
			toJSON(invokerResult));
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
		Assert.assertEquals("\"Welcome 173 to null\"", toJSON(invokerResult));
	}

	protected JSONWebServiceAction prepareInvokerAction(String content)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		mockHttpServletRequest.setContent(content.getBytes());

		return new JSONWebServiceInvokerAction(mockHttpServletRequest);
	}

}