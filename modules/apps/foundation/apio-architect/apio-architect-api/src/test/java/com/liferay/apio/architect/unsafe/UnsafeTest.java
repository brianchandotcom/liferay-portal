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

package com.liferay.apio.architect.unsafe;

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class UnsafeTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor = Unsafe.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test(expected = ClassCastException.class)
	public void testUnsafeWithInvalidCastThrowsException() {
		Integer integer = 42;

		String string = unsafeCast(integer);

		throw new AssertionError("Should not be able to cast: " + string);
	}

	@Test
	public void testUnsafeWithValidCastReturnsValidValue() {
		Integer integer = 42;

		Number number = unsafeCast(integer);

		assertThat(number, is(42));
	}

}