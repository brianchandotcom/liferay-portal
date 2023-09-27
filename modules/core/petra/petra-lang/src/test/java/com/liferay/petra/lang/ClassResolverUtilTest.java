/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.lang;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ClassResolverUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testConstructor() {
		new ClassResolverUtil();
	}

	@Test
	public void testResolve() throws ClassNotFoundException {
		Assert.assertSame(
			ClassResolverUtilTest.class,
			ClassResolverUtil.resolve(
				ClassResolverUtilTest.class.getName(),
				ClassResolverUtilTest.class.getClassLoader()));
	}

	@Test
	public void testResolveWithDifferentClassLoader()
		throws ClassNotFoundException {

		Assert.assertSame(
			ClassResolverUtilTest.class,
			ClassResolverUtil.resolve(
				ClassResolverUtilTest.class.getName(),
				new URLClassLoader(new URL[0])));
	}

	@Test(expected = ClassNotFoundException.class)
	public void testResolveWithDifferentClassLoaderAndException()
		throws ClassNotFoundException {

		ClassResolverUtil.resolve(
			ClassResolverUtilTest.class.getName(),
			new URLClassLoader(new URL[0], null));
	}

	@Test(expected = ClassNotFoundException.class)
	public void testResolveWithException() throws ClassNotFoundException {
		ClassResolverUtil.resolve(
			"not.a.real.Class", ClassResolverUtilTest.class.getClassLoader());
	}

	@Test
	public void testResolveWithPrimitive() throws ClassNotFoundException {
		Assert.assertSame(
			boolean.class,
			ClassResolverUtil.resolve(
				"boolean", ClassResolverUtilTest.class.getClassLoader()));
	}

}