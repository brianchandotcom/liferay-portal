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

package com.liferay.apio.architect.exception.mapper.internal;

import static com.liferay.apio.architect.exception.mapper.internal.WebApplicationExceptionMapperUtil.isNotDefaultMessage;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.function.Predicate;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class WebApplicationExceptionMapperUtilTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			WebApplicationExceptionMapperUtil.class.
				getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test
	public void testIsNotDefaultMessageReturnsFalseIfEquals() {
		String message = "HTTP 404 Not Found";

		Predicate<String> predicate = isNotDefaultMessage(NOT_FOUND);

		assertThat(predicate.test(message), is(false));
	}

	@Test
	public void testIsNotDefaultMessageReturnsTrueIfNotEquals() {
		String message = "Other Message";

		Predicate<String> predicate = isNotDefaultMessage(NOT_FOUND);

		assertThat(predicate.test(message), is(true));
	}

}