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

package com.liferay.portal.remote.cors.internal.url.pattern.mapper;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 */
public class StaticSizeTrieURLPatternMapperTest
	extends SimpleURLPatternMapperTest {

	@Test(expected = IllegalArgumentException.class)
	public void testExtensionMaximumCount() {
		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < (Long.SIZE + 1); i++) {
			map.put("*.key" + i, "value" + i);
		}

		createURLPatternMapper(map);
	}

	@Test
	public void testFullAddresses() {
		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < Long.SIZE; i++) {
			map.put("*.key" + i, "value" + i);
		}

		URLPatternMapper<String> urlPatternMapper = createURLPatternMapper(map);

		for (int i = 0; i < Long.SIZE; i++) {
			Assert.assertEquals(
				"value" + i, urlPatternMapper.getValue("*.key" + i));
		}
	}

	@Test
	public void testGetExtensionValueBounds() {
		URLPatternMapper<String> urlPatternMapper = createURLPatternMapper(
			HashMapBuilder.put(
				"*.jsp", "*.jsp"
			).build());

		Assert.assertNull(urlPatternMapper.getValue("jsp"));
		Assert.assertEquals("*.jsp", urlPatternMapper.getValue(".jsp"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWilcardMaximumCount() {
		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < (Long.SIZE + 1); i++) {
			map.put("key" + i, "value" + 1);
		}

		createURLPatternMapper(map);
	}

	@Override
	protected URLPatternMapper<String> createURLPatternMapper(
		Map<String, String> values) {

		return new StaticSizeTrieURLPatternMapper<>(values);
	}

}