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

package com.liferay.apio.architect.test.util.internal.util;

import static com.liferay.apio.architect.test.util.internal.util.DescriptionUtil.indentDescription;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.function.Consumer;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class DescriptionUtilTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			DescriptionUtil.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test
	public void testIndentDescription() {
		Consumer<Description> consumer =
			description -> description.appendText("a\nb");

		Description description = new StringDescription();

		indentDescription(description, "key", consumer);

		assertThat(description.toString(), is("  key: a\n  b\n"));
	}

	@Test
	public void testIndentDescriptionDoesNotCreateExtraNewlines() {
		Consumer<Description> consumer =
			description -> description.appendText("a\nb\n");

		Description description = new StringDescription();

		indentDescription(description, "key", consumer);

		assertThat(description.toString(), is("  key: a\n  b\n"));
	}

}