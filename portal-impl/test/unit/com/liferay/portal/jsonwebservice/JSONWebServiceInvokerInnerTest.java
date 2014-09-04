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

import com.liferay.portal.jsonwebservice.action.JSONWebServiceInvokerAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;

import java.util.ArrayList;
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
public class JSONWebServiceInvokerInnerTest extends BaseJSONWebServiceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		initPortalServices();

		registerActionClass(FooService.class);
	}

	@Test
	public void testAddVariableToInnerProperty() throws Exception {
		Map<String, Object> commandMap = new LinkedHashMap<String, Object>();

		Map<String, Object> pParams = new LinkedHashMap<String, Object>();

		Map<String, Object> xxx2Params = new LinkedHashMap<String, Object>();

		xxx2Params.put("@userId", "$p.page");
		xxx2Params.put("worldName", "star");

		pParams.put("data.$XXX2 = /foo/hello-world", xxx2Params);

		commandMap.put("$p = /foo/get-foo-data-page", pParams);

		Map expectedMap = prepareExpectedResult(false, false, false);
		Map resultMap = invokeAndReturnMap(commandMap);

		Assert.assertEquals(expectedMap, resultMap);
	}

	@Test
	public void testAddVariableToRootAndInnerProperty() throws Exception {
		Map<String, Object> commandMap = new LinkedHashMap<String, Object>();

		Map<String, Object> pParams = new LinkedHashMap<String, Object>();

		Map<String, Object> xxx1Params = new LinkedHashMap<String, Object>();

		xxx1Params.put("@userId", "$p.page");
		xxx1Params.put("worldName", "galaxy");

		pParams.put("$XXX1 = /foo/hello-world", xxx1Params);

		Map<String, Object> xxx2Params = new LinkedHashMap<String, Object>();

		xxx2Params.put("@userId", "$p.page");
		xxx2Params.put("worldName", "star");

		pParams.put("data.$XXX2 = /foo/hello-world", xxx2Params);

		commandMap.put("$p = /foo/get-foo-data-page", pParams);

		Map expectedMap = prepareExpectedResult(true, false, false);
		Map resultMap = invokeAndReturnMap(commandMap);

		Assert.assertEquals(expectedMap, resultMap);
	}

	@Test
	public void testAddVariableToRootInnerAndListProperty() throws Exception {
		Map<String, Object> commandMap = new LinkedHashMap<String, Object>();

		Map<String, Object> pParams = new LinkedHashMap<String, Object>();

		Map<String, Object> xxx1Params = new LinkedHashMap<String, Object>();

		xxx1Params.put("@userId", "$p.page");
		xxx1Params.put("worldName", "galaxy");

		pParams.put("$XXX1 = /foo/hello-world", xxx1Params);

		Map<String, Object> xxx2Params = new LinkedHashMap<String, Object>();

		xxx2Params.put("@userId", "$p.page");
		xxx2Params.put("worldName", "star");

		pParams.put("data.$XXX2 = /foo/hello-world", xxx2Params);

		Map<String, Object> xxx3Params = new LinkedHashMap<String, Object>();

		xxx3Params.put("@userId", "$p.page");
		xxx3Params.put("worldName", "pulsar");

		pParams.put("list.$XXX3 = /foo/hello-world", xxx3Params);

		commandMap.put("$p = /foo/get-foo-data-page", pParams);

		Map expectedMap = prepareExpectedResult(true, true, false);
		Map resultMap = invokeAndReturnMap(commandMap);

		Assert.assertEquals(expectedMap, resultMap);
	}

	@Test
	public void testAddVariableToRootInnerAndListPropertyAndListReference()
		throws Exception {

		Map<String, Object> commandMap = new LinkedHashMap<String, Object>();

		Map<String, Object> pParams = new LinkedHashMap<String, Object>();

		Map<String, Object> xxx1Params = new LinkedHashMap<String, Object>();

		xxx1Params.put("@userId", "$p.page");
		xxx1Params.put("worldName", "galaxy");

		pParams.put("$XXX1 = /foo/hello-world", xxx1Params);

		Map<String, Object> xxx2Params = new LinkedHashMap<String, Object>();

		xxx2Params.put("@userId", "$p.page");
		xxx2Params.put("worldName", "star");

		pParams.put("data.$XXX2 = /foo/hello-world", xxx2Params);

		Map<String, Object> xxx3Params = new LinkedHashMap<String, Object>();

		xxx3Params.put("@userId", "$p.list.id");
		xxx3Params.put("worldName", "pulsar");

		pParams.put("list.$XXX3 = /foo/hello-world", xxx3Params);

		commandMap.put("$p = /foo/get-foo-data-page", pParams);

		Map expectedMap = prepareExpectedResult(true, true, true);
		Map resultMap = invokeAndReturnMap(commandMap);

		Assert.assertEquals(expectedMap, resultMap);
	}

	protected String invoke(Object command) throws Exception {
		String json = toJSON(command);

		JSONWebServiceAction jsonWebServiceAction = prepareInvokerAction(json);

		JSONWebServiceInvokerAction.InvokerResult invokerResult =
			(JSONWebServiceInvokerAction.InvokerResult)
				jsonWebServiceAction.invoke();

		return invokerResult.toJSONString();
	}

	protected Map<String, Object> invokeAndReturnMap(Object command)
		throws Exception {

		String json = invoke(command);

		return toMap(json);
	}

	protected Map prepareExpectedResult(
		boolean xxx1, boolean xxx3, boolean index) {

		LinkedHashMap<String, Object> resultMap =
			new LinkedHashMap<String, Object>();

		if (xxx1) {
			resultMap.put("XXX1", "Welcome 3 to galaxy");
		}

		resultMap.put("page", 3);

		Map<String, Object> data = new LinkedHashMap<String, Object>();

		data.put("id", 2);
		data.put("height", 8);
		data.put("XXX2", "Welcome 3 to star");
		data.put("name", "life");

		List<Integer> list = new ArrayList<>();
		list.add(9);
		list.add(5);
		list.add(7);
		data.put("array", list);

		resultMap.put("data", data);

		List<Map<String, Object>> resultList =
			new ArrayList<Map<String, Object>>();

		LinkedHashMap<String, Object> resultListElement =
			new LinkedHashMap<String, Object>();

		if (xxx3) {
			resultListElement.put("id", 1);
			resultListElement.put("height", 177);
			resultListElement.put("XXX3", "Welcome 3 to pulsar");
		}
		else {
			resultListElement.put("height", 177);
			resultListElement.put("id", 1);
		}

		resultListElement.put("name", "John Doe");
		resultListElement.put("value", "foo!");

		if (index) {
			resultListElement.put("XXX3", "Welcome 1 to pulsar");
		}

		resultList.add(resultListElement);

		resultListElement =
			(LinkedHashMap<String, Object>)resultListElement.clone();

		resultListElement.put("id", 2);

		if (index) {
			resultListElement.put("XXX3", "Welcome 2 to pulsar");
		}

		resultList.add(resultListElement);

		resultListElement =
			(LinkedHashMap<String, Object>)resultListElement.clone();

		resultListElement.put("id", 3);

		if (index) {
			resultListElement.put("XXX3", "Welcome 3 to pulsar");
		}

		resultList.add(resultListElement);

		resultMap.put("list", resultList);

		//return toJSON(resultMap, "list", "data.array");
		return resultMap;
	}

	protected JSONWebServiceAction prepareInvokerAction(String content)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		mockHttpServletRequest.setContent(content.getBytes());

		return new JSONWebServiceInvokerAction(mockHttpServletRequest);
	}

}