/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.url.pattern.mapper.internal;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class DynamicSizeTrieURLPatternMapperCorrectnessTest
	extends BaseURLPatternMapperCorrectnessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.add(DynamicSizeTrieURLPatternMapper.class);

					Collections.addAll(
						assertClasses,
						DynamicSizeTrieURLPatternMapper.class.
							getDeclaredClasses());
				}

			},
			LiferayUnitTestRule.INSTANCE);

	@Test
	public void testConstructor() {
		Map<String, Integer> map = new HashMap<>();

		for (int i = 0; i < 1024; i++) {
			map.put("*.key" + i, i);
		}

		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			map);

		for (int i = 0; i < 1024; i++) {
			Assert.assertTrue(i == urlPatternMapper.getValue("*.key" + i));
		}
	}

	@Override
	protected URLPatternMapper<Integer> createURLPatternMapper(
		Map<String, Integer> values) {

		return new DynamicSizeTrieURLPatternMapper<>(values);
	}

}