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

package com.liferay.commerce.product.internal.util;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Igor Beslic
 */
public class JsonHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ConfigurationFactoryUtil.setConfigurationFactory(
			Mockito.mock(ConfigurationFactory.class, Mockito.RETURNS_MOCKS));

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetFirstElementStringValue() throws Exception {
		String json = "[\"commerce\"]";

		String firstElementValue = null;

		if (JSONUtil.isArray(json)) {
			JSONArray jsonArray = JSONUtil.getJSONArray(json);

			if (jsonArray.length() > 0) {
				firstElementValue = (String)jsonArray.get(0);
			}
		}

		Assert.assertEquals(
			"First element string value", "commerce", firstElementValue);

		json = "[\"commerce\",\"value2\"]";

		firstElementValue = null;

		if (JSONUtil.isArray(json)) {
			JSONArray jsonArray = JSONUtil.getJSONArray(json);

			if (jsonArray.length() > 0) {
				firstElementValue = (String)jsonArray.get(0);
			}
		}

		Assert.assertEquals(
			"First element string value", "commerce", firstElementValue);
	}

	@Test
	public void testGetValueAsJSONArray() throws Exception {
		JSONObject jsonObject = _jsonFactory.createJSONObject("{\"array\":[]}");

		JSONArray jsonArray = JSONUtil.getValueAsJSONArray("array", jsonObject);

		Assert.assertNotNull("JSONArray is not null", jsonArray);

		Assert.assertEquals("JSONArray length", 0, jsonArray.length());

		jsonObject = _jsonFactory.createJSONObject(
			"{\"array\":[\"commerce\"]}");

		jsonArray = JSONUtil.getValueAsJSONArray("array", jsonObject);

		Assert.assertEquals("JSONArray length", 1, jsonArray.length());

		Assert.assertEquals(
			"JSONArray first string element value", "commerce",
			jsonArray.getString(0));

		jsonObject = _jsonFactory.createJSONObject("{\"array\":[1,2,300,4]}");

		jsonArray = JSONUtil.getValueAsJSONArray("array", jsonObject);

		Assert.assertEquals("JSONArray length", 4, jsonArray.length());

		Assert.assertEquals(
			"JSONArray first string element value", "300",
			jsonArray.getString(2));
	}

	@Test
	public void testIsArray() {
		Assert.assertFalse("null is not a JSON array", JSONUtil.isArray(null));

		Assert.assertFalse("\"\" is not a JSON array", JSONUtil.isArray(""));

		Assert.assertFalse("{} is not a JSON array", JSONUtil.isArray("{}"));

		Assert.assertTrue("[] is an empty JSON array", JSONUtil.isArray("[]"));

		Assert.assertFalse(
			"{\"key\":\"value\"} is not a JSON array",
			JSONUtil.isArray("{\"key\":\"value\"}"));

		Assert.assertTrue(
			"[{\"key\":\"value\"}] is a JSON array",
			JSONUtil.isArray("[{\"key\":\"value\"}]"));

		Assert.assertTrue(
			"[\"value1\",\"value2\"] is a JSON array",
			JSONUtil.isArray("[\"value1\",\"value2\"]"));
	}

	@Test
	public void testisValid() {
		Assert.assertTrue(
			"null is an empty JSON string", JSONUtil.isValid(null));

		Assert.assertTrue("\"\" is an empty JSON string", JSONUtil.isValid(""));

		Assert.assertTrue("[] is an empty JSON string", JSONUtil.isValid("[]"));

		Assert.assertTrue("{} is an empty JSON string", JSONUtil.isValid("{}"));

		Assert.assertTrue(
			"{\"key\":\"value\"} is not an empty JSON string",
			JSONUtil.isValid("{\"key\":\"value\"}"));

		Assert.assertTrue(
			"[{\"key\":\"value\"}] is not an empty JSON string",
			JSONUtil.isValid("[{\"key\":\"value\"}]"));

		Assert.assertTrue(
			"[\"value1\",\"value2\"] is not an empty JSON string",
			JSONUtil.isValid("[\"value1\",\"value2\"]"));
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}