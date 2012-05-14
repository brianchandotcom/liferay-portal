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
import com.liferay.portal.jsonwebservice.dependencies.FooService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;

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
public class JSONWebServiceInvokerTest extends JSONWebServiceAbstractTest {

	@BeforeClass
	public static void init() throws Exception {
		initPortalServices();

		registerActionClass(FooService.class);
	}

	@Test
	public void testBatchCalls() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		String command =
			"{\n" +
			"   \"/foo/hello-world\": {\n" +
			"       \"userId\": 173,\n" +
			"       \"worldName\": \"Jupiter\",\n" +
			"   }\n" +
			"}";

		command = "[" + command + ", " + command + "]";

		mockHttpServletRequest.setContent(command.getBytes());

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		Object result = jsonWebServiceAction.invoke();

		assertTrue(result instanceof List);

		String jsonResult = toJson(result);

		assertEquals("[\"Welcome 173 to Jupiter\",\"Welcome 173 to Jupiter\"]",
			jsonResult);
	}

	@Test
	public void testFiltering() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		String command =
			"{\n" +
			"   \"$data[id, world] = /foo/get-foo-data\": {\n" +
			"       \"id\": 173,\n" +
			"		\"$world = /foo/hello-world\": {\n" +
			"			\"@userId\": \"$data.id\", \n" +
			"			\"worldName\": \"Jupiter\"\n" +
			"		}\n" +
			"   }\n" +
			"}";

		mockHttpServletRequest.setContent(command.getBytes());

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		Object result = jsonWebServiceAction.invoke();

		assertTrue(result instanceof Map);

		String jsonResult = toJson(result);

		assertEquals(
			"{\"id\":173,\" world\":null," +
				"\"world\":\"Welcome 173 to Jupiter\"}", jsonResult);

	}

	@Test
	public void testInnerCalls() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		String command =
			"{\n" +
			"   \"$data = /foo/get-foo-data\": {\n" +
			"       \"id\": 173,\n" +
			"		\"$world = /foo/hello-world\": {\n" +
			"			\"@userId\": \"$data.id\", \n" +
			"			\"worldName\": \"Jupiter\"\n" +
			"		}\n" +
			"   }\n" +
			"}";

		mockHttpServletRequest.setContent(command.getBytes());

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		Object result = jsonWebServiceAction.invoke();

		assertTrue(result instanceof Map);

		String jsonResult = toJson(result);

		assertEquals(
			"{\"id\":173,\"height\":177,\"name\":\"John Doe\"," +
				"\"value\":\"foo!\",\"world\":\"Welcome 173 to Jupiter\"}",
			jsonResult);

	}

	@Test
	public void testSimpleCall() throws Exception {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		String command =
			"{\n" +
			"   \"/foo/hello-world\": {\n" +
			"       \"userId\": 173,\n" +
			"       \"worldName\": \"Jupiter\",\n" +
			"   }\n" +
			"}";

		mockHttpServletRequest.setContent(command.getBytes());

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		assertEquals(
			"Welcome 173 to Jupiter", jsonWebServiceAction.invoke());

	}

	@Test
	public void testSimpleCallUsingCmdParam() throws Exception {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		String command =
			"{\n" +
			"   \"/foo/hello-world\": {\n" +
			"       \"userId\": 173,\n" +
			"       \"worldName\": \"Jupiter\",\n" +
			"   }\n" +
			"}";

		mockHttpServletRequest.setParameter("cmd", command);

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		assertEquals(
			"Welcome 173 to Jupiter", jsonWebServiceAction.invoke());

	}

	@Test
	public void testSimpleCallWithName() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		String command =
			"{\n" +
			"   \"$world = /foo/hello-world\": {\n" +
			"       \"userId\": 173,\n" +
			"       \"worldName\": \"Jupiter\",\n" +
			"   }\n" +
			"}";

		mockHttpServletRequest.setContent(command.getBytes());

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		assertEquals(
			"Welcome 173 to Jupiter", jsonWebServiceAction.invoke());
	}

}