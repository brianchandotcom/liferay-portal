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

package com.liferay.petra.reflect;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class GenericUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testConstructor() {
		new GenericUtil();
	}

	@Test
	public void testGetGenericClass() {
		Assert.assertEquals(
			String.class,
			GenericUtil.getGenericClass(new StringParameterizedType()));
		Assert.assertEquals(
			Object.class,
			GenericUtil.getGenericClass(new NoParameterizedTypeImpl()));
		Assert.assertEquals(
			Object.class,
			GenericUtil.getGenericClass(ExtendsNoParameterizedTypeImpl.class));
		Assert.assertEquals(
			Object.class,
			GenericUtil.getGenericClass(NoParameterizedTypeImpl.class));
		Assert.assertEquals(
			String.class,
			GenericUtil.getGenericClass(StringParameterizedType.class));
	}

	@Test
	public void testGetGenericClassName() {
		Assert.assertEquals(
			Object.class.getCanonicalName(),
			GenericUtil.getGenericClassName(new NoParameterizedTypeImpl()));
		Assert.assertEquals(
			String.class.getCanonicalName(),
			GenericUtil.getGenericClassName(new StringParameterizedType()));
	}

	public static class ExtendsNoParameterizedTypeImpl
		extends NoParameterizedTypeImpl {
	}

	public static class NoParameterizedTypeImpl implements NoParameterizedType {
	}

	public static class StringParameterizedType
		implements ParameterizedType<String> {
	}

	public interface NoParameterizedType {
	}

	public interface ParameterizedType<T> {
	}

}