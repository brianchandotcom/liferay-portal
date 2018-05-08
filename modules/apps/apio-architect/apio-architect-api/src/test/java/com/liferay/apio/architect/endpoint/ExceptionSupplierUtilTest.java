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

package com.liferay.apio.architect.endpoint;

import static com.liferay.apio.architect.endpoint.ExceptionSupplierUtil.notAllowed;
import static com.liferay.apio.architect.endpoint.ExceptionSupplierUtil.notFound;
import static com.liferay.apio.architect.operation.Method.POST;
import static com.liferay.apio.architect.operation.Method.PUT;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class ExceptionSupplierUtilTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			ExceptionSupplierUtil.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test
	public void testNotAllowedReturnsValidException() {
		NotAllowedException notAllowedException = notAllowed(
			POST, "a", "b", "c").get();

		String expected = POST.name() + " method is not allowed for path a/b/c";

		assertThat(notAllowedException.getMessage(), is(expected));
	}

	@Test
	public void testNotAllowedWithOneComponentPathDoesNotAddSlashes() {
		NotAllowedException notAllowedException = notAllowed(PUT, "a").get();

		String expected = PUT.name() + " method is not allowed for path a";

		assertThat(notAllowedException.getMessage(), is(expected));
	}

	@Test
	public void testNotFoundReturnsValidException() {
		NotFoundException notFoundException = notFound("a", "b", "c").get();

		String expected = "No endpoint found at path a/b/c";

		assertThat(notFoundException.getMessage(), is(expected));
	}

	@Test
	public void testNotFoundWithOneComponentPathDoesNotAddSlashes() {
		NotFoundException notFoundException = notFound("a").get();

		String expected = "No endpoint found at path a";

		assertThat(notFoundException.getMessage(), is(expected));
	}

}