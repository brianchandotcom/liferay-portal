/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.url.pattern.mapper.internal;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 */
public class StaticSizeTrieURLPatternMapperCorrectnessTest
	extends SimpleURLPatternMapperCorrectnessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.add(StaticSizeTrieURLPatternMapper.class);
				}

			},
			LiferayUnitTestRule.INSTANCE);

	@Override
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