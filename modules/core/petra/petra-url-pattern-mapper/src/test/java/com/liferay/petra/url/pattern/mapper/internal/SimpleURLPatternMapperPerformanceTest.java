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
public class SimpleURLPatternMapperPerformanceTest
	extends BaseURLPatternMapperPerformanceTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	protected URLPatternMapper<Integer> createURLPatternMapper(
		Map<String, Integer> values) {

		return new SimpleURLPatternMapper<>(values);
	}

	@Override
	protected int testConsumeValuesExpectedTime() {
		return 3600;
	}

	@Override
	protected int testConsumeValuesOrderedExpectedTime() {
		return 4300;
	}

	@Override
	protected int testGetValueExpectedTime() {
		return 1400;
	}

	@Override
	protected int testGetValuesExpectedTime() {
		return 3900;
	}

}