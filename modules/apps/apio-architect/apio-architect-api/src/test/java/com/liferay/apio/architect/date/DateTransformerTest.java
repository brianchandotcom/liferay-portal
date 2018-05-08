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

package com.liferay.apio.architect.date;

import static com.liferay.apio.architect.test.util.result.TryMatchers.aFailTry;

import static org.exparity.hamcrest.date.DateMatchers.sameInstant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.functional.Try;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.Date;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class DateTransformerTest {

	@Test
	public void testAsDateWithInvalidDateReturnsFailure() {
		Try<Date> dateTry = DateTransformer.asDate("2016-06-15");

		assertThat(dateTry, is(aFailTry()));
	}

	@Test
	public void testAsDateWithValidDateReturnsSuccess() {
		Try<Date> dateTry = DateTransformer.asDate("2016-06-15T09:00Z");

		Date date = dateTry.getUnchecked();

		assertThat(date, is(sameInstant(1465981200000L)));
	}

	@Test
	public void testAsStringReturnsDateInISO8061Format() {
		String date = DateTransformer.asString(new Date(1465981200000L));

		assertThat(date, is("2016-06-15T09:00Z"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			DateTransformer.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

}