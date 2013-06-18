/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import java.lang.reflect.Field;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ClassLoaderPoolTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());

		Field classLoadersField = ReflectionUtil.getDeclaredField(
			ClassLoaderPool.class, "_classLoaders");

		_classLoaders = (Map<String, ClassLoader>)classLoadersField.get(null);

		_classLoaders.clear();

		Field contextNamesField = ReflectionUtil.getDeclaredField(
			ClassLoaderPool.class, "_contextNames");

		_contextNames = (Map<ClassLoader, String>)contextNamesField.get(null);

		_contextNames.clear();

	}

	@Test
	public void testGetClassLoaderWithInvalidContextName() {
		String contextName = "contextName";

		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(contextName, classLoader);

		Class<?> clazz = getClass();

		Assert.assertSame(
			clazz.getClassLoader(),
			ClassLoaderPool.getClassLoader(StringPool.BLANK));
	}

	@Test
	public void testGetClassLoaderWithInvalidContextNamePortalContextNotSet() {
		String contextName = "contextName";

		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(contextName, classLoader);

		PortalClassLoaderUtil.setClassLoader(null);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Assert.assertSame(
			contextClassLoader,
			ClassLoaderPool.getClassLoader(StringPool.BLANK));
	}

	@Test
	public void testGetClassLoaderWithValidContextName() {
		String contextName = "contextName";

		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(contextName, classLoader);

		Assert.assertSame(
			classLoader, ClassLoaderPool.getClassLoader(contextName));
	}

	@Test
	public void testGetContextNameWithInvalidClassLoader() {
		String contextName = "contextName";

		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(contextName, classLoader);

		Assert.assertEquals(
			StringPool.BLANK,
			ClassLoaderPool.getContextName(new URLClassLoader(new URL[0])));
	}

	@Test
	public void testGetContextNameWithNullClassLoader() {
		String contextName = "contextName";

		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(contextName, classLoader);

		Assert.assertEquals(
			StringPool.BLANK, ClassLoaderPool.getContextName(null));
	}

	@Test
	public void testGetContextNameWithValidClassLoader() {
		String contextName = "contextName";

		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(contextName, classLoader);

		Assert.assertEquals(
			contextName, ClassLoaderPool.getContextName(classLoader));
	}

	@Test
	public void testRegister() {
		String contextName = "contextName";

		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(contextName, classLoader);

		Assert.assertEquals(1, _contextNames.size());
		Assert.assertEquals(1, _classLoaders.size());
		Assert.assertSame(classLoader, _classLoaders.get(contextName));
		Assert.assertEquals(contextName, _contextNames.get(classLoader));
	}

	@Test
	public void testRegisterWithNullClassLoader() {
		try {
			ClassLoaderPool.register(StringPool.BLANK, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testRegisterWithNullContextName() {
		try {
			ClassLoaderPool.register(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testUnregisterByInvalidClassLoader() {
		ClassLoaderPool.unregister(new URLClassLoader(new URL[0]));

		assertEmptyMaps();
	}

	@Test
	public void testUnregisterByInvalidContextName() {
		ClassLoaderPool.unregister("contextName");

		assertEmptyMaps();
	}

	@Test
	public void testUnregisterByValidClassLoader() {
		String contextName = "contextName";

		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(contextName, classLoader);

		ClassLoaderPool.unregister(classLoader);

		assertEmptyMaps();
	}

	@Test
	public void testUnregisterByValidContextName() {
		String contextName = "contextName";

		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(contextName, classLoader);

		ClassLoaderPool.unregister(contextName);

		assertEmptyMaps();
	}

	protected void assertEmptyMaps() {
		Assert.assertTrue(_contextNames.isEmpty());
		Assert.assertTrue(_classLoaders.isEmpty());
	}

	private static Map<String, ClassLoader> _classLoaders;
	private static Map<ClassLoader, String> _contextNames;

}