/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.lang.reflect.Field;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ClassLoaderPoolTest {

	@Before
	public void setUp() throws Exception {
		PortalClassLoaderUtil.setClassLoader(getClass().getClassLoader());

		Field classLoaderToContextNameMapField =
			ReflectionUtil.getDeclaredField(
				ClassLoaderPool.class, "_classLoaderToContextNameMap");
		Field contextNameToClassLoaderMapField =
			ReflectionUtil.getDeclaredField(
				ClassLoaderPool.class, "_contextNameToClassLoaderMap");

		_classLoaderToContextNameMap =
			(Map<ClassLoader, String>)classLoaderToContextNameMapField.get(
				null);
		_contextNameToClassLoaderMap =
			(Map<String, ClassLoader>)contextNameToClassLoaderMapField.get(
				null);

		_classLoaderToContextNameMap.clear();
		_contextNameToClassLoaderMap.clear();
	}

	@Test
	public void testRegister() {
		// 1) Register null contextName

		try {
			ClassLoaderPool.register(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// 2)Register null ClassLoader

		try {
			ClassLoaderPool.register(StringPool.BLANK, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// 3) Register normally

		String testContextName = "testContextName";
		ClassLoader testClassLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(testContextName, testClassLoader);

		Assert.assertEquals(1, _classLoaderToContextNameMap.size());
		Assert.assertEquals(1, _contextNameToClassLoaderMap.size());

		Assert.assertSame(
			testClassLoader, _contextNameToClassLoaderMap.get(testContextName));
		Assert.assertEquals(
			testContextName, _classLoaderToContextNameMap.get(testClassLoader));

		// 4) Get ClassLoader by contextName, normally

		Assert.assertSame(
			testClassLoader,
			ClassLoaderPool.getClassLoaderByContextName(testContextName));

		// 5) Get ClassLoader by contextName, not such ClassLoader

		Assert.assertSame(
			getClass().getClassLoader(),
			ClassLoaderPool.getClassLoaderByContextName(StringPool.BLANK));

		// 6) Get contextName by ClassLoader, normally

		Assert.assertEquals(
			testContextName,
			ClassLoaderPool.getContextNameByClassLoader(testClassLoader));

		// 7) Get contextName by ClassLoader, null ClassLoader

		Assert.assertEquals(
			StringPool.BLANK,
			ClassLoaderPool.getContextNameByClassLoader(null));

		// 8) Get contextName by ClassLoader, no such contextName

		Assert.assertEquals(
			StringPool.BLANK,
			ClassLoaderPool.getContextNameByClassLoader(
				new URLClassLoader(new URL[0])));

		// 9) Unregister by ClassLoader, normally

		ClassLoaderPool.unregisterByClassLoader(testClassLoader);

		Assert.assertTrue(_classLoaderToContextNameMap.isEmpty());
		Assert.assertTrue(_contextNameToClassLoaderMap.isEmpty());

		// 10) Unregister by ClassLoader, no such ClassLoader

		ClassLoaderPool.unregisterByClassLoader(testClassLoader);

		Assert.assertTrue(_classLoaderToContextNameMap.isEmpty());
		Assert.assertTrue(_contextNameToClassLoaderMap.isEmpty());

		ClassLoaderPool.register(testContextName, testClassLoader);

		// 11) Unregister by contextName, normally

		ClassLoaderPool.unregisterByName(testContextName);

		Assert.assertTrue(_classLoaderToContextNameMap.isEmpty());
		Assert.assertTrue(_contextNameToClassLoaderMap.isEmpty());

		// 12) Unregister by contextName, no such contextName

		ClassLoaderPool.unregisterByName(testContextName);

		Assert.assertTrue(_classLoaderToContextNameMap.isEmpty());
		Assert.assertTrue(_contextNameToClassLoaderMap.isEmpty());
	}

	private static Map<ClassLoader, String> _classLoaderToContextNameMap;

	private static Map<String, ClassLoader> _contextNameToClassLoaderMap;

}