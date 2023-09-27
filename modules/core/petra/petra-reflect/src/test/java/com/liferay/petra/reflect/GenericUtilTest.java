/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		// Index 0

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

		// Index 1

		Assert.assertEquals(
			String.class,
			GenericUtil.getGenericClass(
				new StringDoubleParameterizedType(), 1));
		Assert.assertEquals(
			Object.class,
			GenericUtil.getGenericClass(new NoParameterizedTypeImpl(), 1));
		Assert.assertEquals(
			Object.class,
			GenericUtil.getGenericClass(
				ExtendsNoParameterizedTypeImpl.class, 1));
		Assert.assertEquals(
			Object.class,
			GenericUtil.getGenericClass(NoParameterizedTypeImpl.class, 1));
		Assert.assertEquals(
			Object.class,
			GenericUtil.getGenericClass(StringParameterizedType.class, 1));
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

	public static class StringDoubleParameterizedType
		implements DoubleParameterizedType<Object, String> {
	}

	public static class StringParameterizedType
		implements ParameterizedType<String> {
	}

	public interface DoubleParameterizedType<O, T> {
	}

	public interface NoParameterizedType {
	}

	public interface ParameterizedType<T> {
	}

}