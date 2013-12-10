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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.portlet.PortletParameterUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sampsa Sohlman
 */
public class DynamicServletRequestTest {

	@Test
	public void testAddQueryStringToParameterMapWithEmptyMap1() {
		Map<String, String[]> map = new HashMap<String, String[]>();

		String queryString = PortletParameterUtil.addNamespace("15", "");

		DynamicServletRequest.addQueryStringToParameterMap(map, queryString);

		Map<String, String[]> expected = createMap("p_p_id=15");

		AssertUtils.assertEquals(expected, map);
	}

	@Test
	public void testAddQueryStringToParameterMapWithEmptyMap2() {
		Map<String, String[]> map = new HashMap<String, String[]>();

		String queryString = PortletParameterUtil.addNamespace(
			"15", "param1=value1&param2=value2&param3=value3");

		DynamicServletRequest.addQueryStringToParameterMap(map, queryString);

		Map<String, String[]> expected = createMap(
			"p_p_id=15&_15_param1=value1&_15_param2=value2&_15_param3=value3");

		AssertUtils.assertEquals(expected, map);
	}

	protected void assertNames(Set names, boolean exist) {
		Assert.assertEquals(exist, names.contains("p_p_mode"));
		Assert.assertEquals(exist, names.contains("p_p_state"));
		Assert.assertEquals(exist, names.contains("p_p_lifecycle"));
		Assert.assertEquals(exist, names.contains("p_p_anything"));
		Assert.assertEquals(exist, names.contains("other"));
	}

	protected Map<String, String[]> createMap(String parameters) {
		Map<String, String[]> map = new HashMap<String, String[]>();

		String[] keysAndValues = StringUtil.split(parameters, "&");

		for (String keyAndValue : keysAndValues) {
			String[] strings = StringUtil.split(keyAndValue, '=');
			map.put(strings[0], new String[] {strings[1]});
		}

		return map;
	}

}