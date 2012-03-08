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

import com.liferay.portal.kernel.test.NewClassLoaderTestCase;

import java.lang.reflect.Constructor;

import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class ModifyStaticFinalTest extends NewClassLoaderTestCase {

	public void testModifyStaticFinal1() throws Exception {
		// Anonymous Class

		assertFalse(StaticFinalClass.VALUE);

		System.setProperty(_testKey, "true");

		assertFalse(StaticFinalClass.VALUE);

		boolean result = runInNewClassLoader(
			new Callable<Boolean>() {

				public Boolean call() throws Exception {
					return StaticFinalClass.VALUE;
				}
			}.getClass());

		assertTrue(result);
	}

	public void testModifyStaticFinal2() throws Exception {
		// Local Class

		assertFalse(StaticFinalClass.VALUE);

		System.setProperty(_testKey, "true");

		assertFalse(StaticFinalClass.VALUE);

		class LocalTestCallable implements Callable<Boolean> {

			public Boolean call() throws Exception {
				return StaticFinalClass.VALUE;
			}

		}

		boolean result = runInNewClassLoader(LocalTestCallable.class);

		assertTrue(result);
	}

	public void testModifyStaticFinal3() throws Exception {
		// Member Class

		assertFalse(StaticFinalClass.VALUE);

		System.setProperty(_testKey, "true");

		assertFalse(StaticFinalClass.VALUE);

		boolean result = runInNewClassLoader(MemberTestCallable.class);

		assertTrue(result);
	}

	public void testModifyStaticFinal4() throws Exception {
		// Static Member Class

		assertFalse(StaticFinalClass.VALUE);

		System.setProperty(_testKey, "true");

		assertFalse(StaticFinalClass.VALUE);

		boolean result = runInNewClassLoader(StaticMemberTestCallable.class);

		assertTrue(result);
	}

	public void testModifyStaticFinal5() throws Exception {
		// No default Constructor

		assertFalse(StaticFinalClass.VALUE);

		System.setProperty(_testKey, "true");

		assertFalse(StaticFinalClass.VALUE);

		class ConstructorTestCallable implements Callable<Boolean> {

			// Dummy Constructor to eliminate default one
			public ConstructorTestCallable(String dummyValue) {
			}

			public Boolean call() throws Exception {
				return StaticFinalClass.VALUE;
			}

		}

		try {
			runInNewClassLoader(ConstructorTestCallable.class);

			fail();
		}
		catch (Exception e) {
		}

		Constructor<ConstructorTestCallable> constructor =
			ConstructorTestCallable.class.getDeclaredConstructor(
				getClass(), String.class);

		boolean result = runInNewClassLoader(constructor, "dummyValue");

		assertTrue(result);
	}

	public void testModifyStaticFinal6() throws Exception {
		// Error Callable to satisfy code coverage

		try {
			runInNewClassLoader(
				new Callable<Boolean>() {

					public Boolean call() throws Exception {
						throw new Exception();
					}
				}.getClass());

			fail();
		}
		catch (Exception e) {
		}
	}

	private static final String _testKey = "test.key";

	private static class StaticFinalClass {

		public static final boolean VALUE = Boolean.valueOf(
			System.getProperty(_testKey));

	}

	private static class StaticMemberTestCallable implements Callable<Boolean> {

		public Boolean call() throws Exception {
			return StaticFinalClass.VALUE;
		}

	}

	private class MemberTestCallable implements Callable<Boolean> {

		public Boolean call() throws Exception {
			return StaticFinalClass.VALUE;
		}

	}

}