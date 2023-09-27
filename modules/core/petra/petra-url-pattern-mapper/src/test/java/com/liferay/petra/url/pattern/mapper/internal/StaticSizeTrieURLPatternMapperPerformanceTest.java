/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.url.pattern.mapper.internal;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Arthur Chan
 */
public class StaticSizeTrieURLPatternMapperPerformanceTest
	extends BaseURLPatternMapperPerformanceTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	protected URLPatternMapper<Integer> createURLPatternMapper(
		Map<String, Integer> values) {

		return new StaticSizeTrieURLPatternMapper<>(values);
	}

	@Override
	protected int testConsumeValuesExpectedTime() {
		return 400;
	}

	@Override
	protected int testConsumeValuesOrderedExpectedTime() {
		return 800;
	}

	@Override
	protected int testGetValueExpectedTime() {
		return 350;
	}

	@Override
	protected int testGetValuesExpectedTime() {
		return 780;
	}

}