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

package com.liferay.petra.lang;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ClassResolverUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

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