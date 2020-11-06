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
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class SimpleURLPatternMapperCorrectnessTest
	extends BaseURLPatternMapperCorrectnessTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(SimpleURLPatternMapper.class);
			}

		};

	@Test
	public void testConstructor() {
		try {
			createURLPatternMapper(
				new HashMap() {
					{
						put("", 0);
					}
				});

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		try {
			createURLPatternMapper(
				new HashMap() {
					{
						put(null, 0);
					}
				});

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}
	}

	@Override
	protected URLPatternMapper<Integer> createURLPatternMapper(
		Map<String, Integer> values) {

		return new SimpleURLPatternMapper<>(values);
	}

}