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

import com.liferay.portal.jsonwebservice.action.JSONWebServiceInvokerAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
@PrepareForTest(ServiceContextFactory.class)
@RunWith(PowerMockRunner.class)
public class JSONWebServiceInvokerTest extends BaseJSONWebServiceTestCase {

	@BeforeClass
	public static void init() throws Exception {
		initPortalServices();

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
	public void testCamelCaseNormalizedParameters() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/camel", params);

		params.put("goodName", "goodboy");
		params.put("badNAME", "badboy");

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		Assert.assertEquals("\"goodboy*badboy\"", toJSON(invokerResult));
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
	public void testFilteringList() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("$datas[id] = /foo/get-foo-datas2", params);

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertTrue(result instanceof List);

		Assert.assertEquals(
			"[{\"id\":1},{\"id\":2},{\"id\":3}]", toJSON(result));
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
			"[{\"id\":1,\"resource\":{\"id\":1,\"value\":\"foo!\"}},{\"id\":" +
				"2,\"resource\":{\"id\":2,\"value\":\"foo!\"}},{\"id\":3," +
					"\"resource\":{\"id\":3,\"value\":\"foo!\"}}]",
			toJSON(invokerResult));
	}

	@Test
	public void testNoProperty() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/bar", params);

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		json = invokerResult.toJSONString();

		Assert.assertEquals("{\"array\":[1,2,3],\"value\":\"value\"}", json);
	}

	@Test
	public void testPropertyInner() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/bar", params);

		Map<String, Object> innerParam = new LinkedHashMap<String, Object>();

		params.put("$new1 = /foo/bar", innerParam);

		innerParam.put("$new2 = /foo/hello", Collections.emptyMap());

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		json = invokerResult.toJSONString();

		Assert.assertEquals(2, StringUtil.count(json, "\"array\":[1,2,3]"));
		Assert.assertFalse(json.contains("\"secret\""));
		Assert.assertTrue(json.contains("\"new1\":{"));
		Assert.assertTrue(json.contains("\"new2\":\"world\""));
	}

	@Test
	public void testPropertySimple() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/bar", params);

		Map<String, Object> innerParam = new LinkedHashMap<String, Object>();

		params.put("$new = /foo/hello", innerParam);

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		json = invokerResult.toJSONString();

		Assert.assertTrue(json.contains("\"array\":[1,2,3]"));
		Assert.assertFalse(json.contains("\"secret\""));
		Assert.assertTrue(json.contains("\"new\":\"world\""));
	}

	@Test
	public void testSerializationComplexObjects1() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/search", params);

		params.put("name", "target");
		params.put("params", new String[] {"active:false:boolean"});

		String json = toJSON(map, "*.params");

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		Assert.assertEquals(
			"\"search target>active:false:boolean\"", toJSON(invokerResult));

		params.put("params", new String[] {"active", "false", "boolean"});

		json = toJSON(map, "*.params");

		jsonWebServiceAction = prepareInvokerAction(json);

		result = jsonWebServiceAction.invoke();

		invokerResult = (JSONWebServiceInvokerAction.InvokerResult)result;

		Assert.assertEquals(
			"\"search target>active,false,boolean\"", toJSON(invokerResult));
	}

	@Test
	public void testSerializationComplexObjects2() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/complex", params);

		params.put("longs", "1,2,3");
		params.put("ints", "1,2");
		params.put("map", "{'key' : 122}");

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		Assert.assertEquals("6", toJSON(invokerResult));
	}

	@Test
	public void testSerializationComplexObjects3() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/complex", params);

		params.put("longs", new long[] {1,2,3});
		params.put("ints", new int[] {1,2});

		Map<String, Integer> map2 = new HashMap<String, Integer>(1);
		map2.put("key", Integer.valueOf(122));

		params.put("map", map2);

		String json = toJSON(map, "*.ints", "*.longs", "*.map");

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		Assert.assertEquals("6", toJSON(invokerResult));
	}

	@Test
	public void testSerializationHack() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/bar", params);

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		Assert.assertEquals(
			"{\"array\":[1,2,3],\"value\":\"value\"}", toJSON(invokerResult));

		// Hack 1

		map.clear();

		map.put("$* = /foo/bar", params);

		json = toJSON(map);

		jsonWebServiceAction = prepareInvokerAction(json);

		result = jsonWebServiceAction.invoke();

		invokerResult = (JSONWebServiceInvokerAction.InvokerResult)result;

		try {
			toJSON(invokerResult);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Hack 2

		map.clear();

		map.put("$secret = /foo/bar", params);

		json = toJSON(map);

		jsonWebServiceAction = prepareInvokerAction(json);

		result = jsonWebServiceAction.invoke();

		invokerResult = (JSONWebServiceInvokerAction.InvokerResult)result;

		Assert.assertEquals(
			"{\"array\":[1,2,3],\"value\":\"value\"}", toJSON(invokerResult));
	}

	@Test
	public void testServiceContext() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/srvcctx2", params);

		params.put("serviceContext", "{'failOnPortalException': false}");

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		ServiceContext serviceContext =
			(ServiceContext)invokerResult.getResult();

		Assert.assertFalse(serviceContext.isFailOnPortalException());
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

	@Test
	public void testTypeConversion1() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hey", params);

		params.put("calendar", "1330419334285");
		params.put("userIds", "1,2,3");
		params.put("locales", "en,fr");
		params.put("ids", "173,-7,007");

		String json = toJSON(map);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertEquals("2012, 1/3, en/2, 173/3", result);
	}

	@Test
	public void testTypeConversion2() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hey", params);

		params.put("calendar", "1330419334285");
		params.put("userIds", new long[] {1,2,3});
		params.put("locales", new String[] {"en","fr"});
		params.put("ids", new long[] {173, -7, 7});

		String json = toJSON(map, "*.userIds", "*.locales", "*.ids");

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		Object result = jsonWebServiceAction.invoke();

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)result;

		result = invokerResult.getResult();

		Assert.assertEquals("2012, 1/3, en/2, 173/3", result);
	}

	protected JSONWebServiceAction prepareInvokerAction(String content)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		mockHttpServletRequest.setContent(content.getBytes());

		return new JSONWebServiceInvokerAction(mockHttpServletRequest);
	}

}