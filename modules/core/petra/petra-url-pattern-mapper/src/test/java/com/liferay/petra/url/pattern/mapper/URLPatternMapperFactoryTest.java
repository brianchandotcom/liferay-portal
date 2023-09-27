/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.url.pattern.mapper;

import com.liferay.petra.url.pattern.mapper.internal.DynamicSizeTrieURLPatternMapper;
import com.liferay.petra.url.pattern.mapper.internal.StaticSizeTrieURLPatternMapper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class URLPatternMapperFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreate() {
		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < 64; i++) {
			map.put("*.key" + i, "value" + i);
		}

		Assert.assertTrue(
			URLPatternMapperFactory.create(map) instanceof
				StaticSizeTrieURLPatternMapper<?>);

		map.put("*.key" + 64, "value" + 64);

		Assert.assertTrue(
			URLPatternMapperFactory.create(map) instanceof
				DynamicSizeTrieURLPatternMapper<?>);
	}

}