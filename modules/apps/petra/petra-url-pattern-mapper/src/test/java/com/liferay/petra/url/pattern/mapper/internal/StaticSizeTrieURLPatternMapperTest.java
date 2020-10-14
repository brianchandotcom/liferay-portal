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

package com.liferay.petra.url.pattern.mapper.internal;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 */
public class StaticSizeTrieURLPatternMapperTest
	extends SimpleURLPatternMapperTest {

	@Test
	public void testConstructor() {
		super.testConstructor();

		try {
			Map<String, Integer> map = new HashMap<>();

			for (int i = 0; i < 65; i++) {
				map.put("*.key" + i, i);
			}

			createURLPatternMapper(map);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		try {
			Map<String, Integer> map = new HashMap<>();

			for (int i = 0; i < (Long.SIZE + 1); i++) {
				map.put("key" + i, i);
			}

			createURLPatternMapper(map);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}
	}

	@Override
	@Test
	public void testGetValue() {
		super.testGetValue();

		Map<String, Integer> map = new HashMap<>();

		for (int i = 0; i < Long.SIZE; i++) {
			map.put("*.key" + i, i);
		}

		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			map);

		for (int i = 0; i < Long.SIZE; i++) {
			Assert.assertTrue(i == urlPatternMapper.getValue("*.key" + i));
		}
	}

	@Override
	protected URLPatternMapper<Integer> createURLPatternMapper(
		Map<String, Integer> values) {

		return new StaticSizeTrieURLPatternMapper<>(values);
	}

}