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

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sampsa Sohlman
 */
public class MapUtilTest {

	@Test
	public void testFilter() throws Exception {
		Map<String, String> inputMap = new HashMap<String, String>();

		inputMap.put("1", "one");
		inputMap.put("2", "two");
		inputMap.put("3", "three");
		inputMap.put("4", "four");
		inputMap.put("5", "five");

		Map<String, String> outputMap = MapUtil.filter(
			inputMap, new HashMap<String, String>(),
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String key) {
					int value = Integer.parseInt(key);

					if ((value % 2) == 0) {
						return true;
					}

					return false;
				}

		});

		Assert.assertEquals(2, outputMap.size());
		Assert.assertEquals("two", outputMap.get("2"));
		Assert.assertEquals("four", outputMap.get("4"));
	}

	@Test
	public void testPrefixPredicateFilter() throws Exception {
		Map<String, String> inputMap = new HashMap<String, String>();

		inputMap.put("x1", "one");
		inputMap.put("2", "two");
		inputMap.put("x3", "three");
		inputMap.put("4", "four");
		inputMap.put("x5", "five");

		Map<String, String> outputMap = MapUtil.filter(
			inputMap, new HashMap<String, String>(),
			new PrefixPredicateFilter("x"));

		Assert.assertEquals(2, outputMap.size());
		Assert.assertEquals("two", outputMap.get("2"));
		Assert.assertEquals("four", outputMap.get("4"));
	}

}