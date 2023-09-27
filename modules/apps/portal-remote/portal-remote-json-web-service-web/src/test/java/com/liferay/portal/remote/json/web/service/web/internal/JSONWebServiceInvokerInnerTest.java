/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.remote.json.web.service.web.internal.action.JSONWebServiceInvokerAction;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceInvokerInnerTest extends BaseJSONWebServiceTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		initPortalServices();

		registerActionClass(FooService.class);
	}

	@Test
	public void testAddVariableToInnerProperty() throws Exception {
		Map<String, Object> expectedMap = prepareExpectedMap(
			false, false, false);
		Map<String, Object> actualMap = invokeAndReturnMap(
			LinkedHashMapBuilder.<String, Object>put(
				"$p = /foo/get-foo-data-page",
				LinkedHashMapBuilder.<String, Object>put(
					"data.$XXX2 = /foo/hello-world",
					LinkedHashMapBuilder.<String, Object>put(
						"@userId", "$p.page"
					).put(
						"worldName", "star"
					).build()
				).build()
			).build());

		Assert.assertEquals(expectedMap, actualMap);
	}

	@Test
	public void testAddVariableToRootAndInnerProperty() throws Exception {
		Map<String, Object> expectedMap = prepareExpectedMap(
			true, false, false);
		Map<String, Object> actualMap = invokeAndReturnMap(
			LinkedHashMapBuilder.<String, Object>put(
				"$p = /foo/get-foo-data-page",
				LinkedHashMapBuilder.<String, Object>put(
					"$XXX1 = /foo/hello-world",
					LinkedHashMapBuilder.<String, Object>put(
						"@userId", "$p.page"
					).put(
						"worldName", "galaxy"
					).build()
				).put(
					"data.$XXX2 = /foo/hello-world",
					LinkedHashMapBuilder.<String, Object>put(
						"@userId", "$p.page"
					).put(
						"worldName", "star"
					).build()
				).build()
			).build());

		Assert.assertEquals(expectedMap, actualMap);
	}

	@Test
	public void testAddVariableToRootInnerAndListProperty() throws Exception {
		Map<String, Object> expectedMap = prepareExpectedMap(true, true, false);
		Map<String, Object> actualMap = invokeAndReturnMap(
			LinkedHashMapBuilder.<String, Object>put(
				"$p = /foo/get-foo-data-page",
				LinkedHashMapBuilder.<String, Object>put(
					"$XXX1 = /foo/hello-world",
					LinkedHashMapBuilder.<String, Object>put(
						"@userId", "$p.page"
					).put(
						"worldName", "galaxy"
					).build()
				).put(
					"data.$XXX2 = /foo/hello-world",
					LinkedHashMapBuilder.<String, Object>put(
						"@userId", "$p.page"
					).put(
						"worldName", "star"
					).build()
				).put(
					"list.$XXX3 = /foo/hello-world",
					LinkedHashMapBuilder.<String, Object>put(
						"@userId", "$p.page"
					).put(
						"worldName", "pulsar"
					).build()
				).build()
			).build());

		Assert.assertEquals(expectedMap, actualMap);
	}

	@Test
	public void testAddVariableToRootInnerAndListPropertyAndListReference()
		throws Exception {

		Map<String, Object> expectedMap = prepareExpectedMap(true, true, true);
		Map<String, Object> actualMap = invokeAndReturnMap(
			LinkedHashMapBuilder.<String, Object>put(
				"$p = /foo/get-foo-data-page",
				LinkedHashMapBuilder.<String, Object>put(
					"$XXX1 = /foo/hello-world",
					LinkedHashMapBuilder.<String, Object>put(
						"@userId", "$p.page"
					).put(
						"worldName", "galaxy"
					).build()
				).put(
					"data.$XXX2 = /foo/hello-world",
					LinkedHashMapBuilder.<String, Object>put(
						"@userId", "$p.page"
					).put(
						"worldName", "star"
					).build()
				).put(
					"list.$XXX3 = /foo/hello-world",
					LinkedHashMapBuilder.<String, Object>put(
						"@userId", "$p.list.id"
					).put(
						"worldName", "pulsar"
					).build()
				).build()
			).build());

		Assert.assertEquals(expectedMap, actualMap);
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

	protected Map<String, Object> prepareExpectedMap(
		boolean xxx1, boolean xxx3, boolean index) {

		Map<String, Object> expectedMap = new LinkedHashMap<>();

		if (xxx1) {
			expectedMap.put("XXX1", "Welcome 3 to galaxy");
		}

		expectedMap.put("page", 3);
		expectedMap.put(
			"data",
			LinkedHashMapBuilder.<String, Object>put(
				"array", ListUtil.fromArray(9, 5, 7)
			).put(
				"id", 2
			).put(
				"height", 8
			).put(
				"XXX2", "Welcome 3 to star"
			).put(
				"name", "life"
			).build());

		List<Map<String, Object>> resultList = new ArrayList<>();

		LinkedHashMap<String, Object> resultListElement = new LinkedHashMap<>();

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

		expectedMap.put("list", resultList);

		return expectedMap;
	}

	protected JSONWebServiceAction prepareInvokerAction(String content)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoker");

		mockHttpServletRequest.setContent(content.getBytes());

		return new JSONWebServiceInvokerAction(mockHttpServletRequest);
	}

}