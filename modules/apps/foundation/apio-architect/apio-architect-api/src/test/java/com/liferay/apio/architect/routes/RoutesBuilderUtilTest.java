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

package com.liferay.apio.architect.routes;

import static com.liferay.apio.architect.routes.RoutesBuilderUtil.provide;
import static com.liferay.apio.architect.routes.RoutesBuilderUtil.provideConsumer;
import static com.liferay.apio.architect.routes.RoutesTestUtil.PROVIDE_FUNCTION;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.error.ApioDeveloperError.MustHaveProvider;
import com.liferay.apio.architect.language.Language;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class RoutesBuilderUtilTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			RoutesBuilderUtil.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test(expected = MustHaveProvider.class)
	public void testFiveParameterProvideConsumerMethodFailsIfOptionalEmpty() {
		provideConsumer(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			Boolean.class,
			string -> aLong -> integer -> aBoolean -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test(expected = MustHaveProvider.class)
	public void testFiveParameterProvideMethodFailsIfOptionalEmpty() {
		provide(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			Boolean.class, Float.class,
			string -> aLong -> integer -> aBoolean -> list -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testFiveParameterProvideMethodProvides() {
		String result = provide(
			PROVIDE_FUNCTION, String.class, Long.class, Integer.class,
			Boolean.class, Float.class,
			string -> aLong -> integer -> aBoolean -> aFloat -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
				assertThat(integer, is(2017));
				assertThat(aBoolean, is(true));
				assertThat(aFloat, is(0.1F));

				return "The result";
			});

		assertThat(result, is("The result"));
	}

	@Test(expected = MustHaveProvider.class)
	public void testFourParameterProvideConsumerMethodFailsIfOptionalEmpty() {
		provideConsumer(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			Boolean.class,
			string -> aLong -> integer -> aBoolean -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testFourParameterProvideConsumerMethodProvides() {
		provideConsumer(
			PROVIDE_FUNCTION, String.class, Long.class, Integer.class,
			Boolean.class,
			string -> aLong -> integer -> aBoolean -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
				assertThat(integer, is(2017));
				assertThat(aBoolean, is(true));
			});
	}

	@Test(expected = MustHaveProvider.class)
	public void testFourParameterProvideMethodFailsIfOptionalEmpty() {
		provide(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			Boolean.class,
			string -> aLong -> integer -> aBoolean -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testFourParameterProvideMethodProvides() {
		String result = provide(
			PROVIDE_FUNCTION, String.class, Long.class, Integer.class,
			Boolean.class,
			string -> aLong -> integer -> aBoolean -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
				assertThat(integer, is(2017));
				assertThat(aBoolean, is(true));

				return "The result";
			});

		assertThat(result, is("The result"));
	}

	@Test(expected = MustHaveProvider.class)
	public void testOneParameterProvideConsumerMethodFailsIfOptionalEmpty() {
		provideConsumer(
			PROVIDE_FUNCTION, Language.class,
			string -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testOneParameterProvideConsumerMethodProvides() {
		provideConsumer(
			PROVIDE_FUNCTION, String.class,
			string -> assertThat(string, is("Apio")));
	}

	@Test(expected = MustHaveProvider.class)
	public void testOneParameterProvideMethodFailsIfOptionalEmpty() {
		provide(
			PROVIDE_FUNCTION, Language.class,
			string -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testOneParameterProvideMethodProvides() {
		String result = provide(
			PROVIDE_FUNCTION, String.class,
			string -> {
				assertThat(string, is("Apio"));

				return "The result";
			});

		assertThat(result, is("The result"));
	}

	@Test(expected = MustHaveProvider.class)
	public void testThreeParameterProvideConsumerMethodFailsIfOptionalEmpty() {
		provideConsumer(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			string -> aLong -> integer -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testThreeParameterProvideConsumerMethodProvides() {
		provideConsumer(
			PROVIDE_FUNCTION, String.class, Long.class, Integer.class,
			string -> aLong -> integer -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
				assertThat(integer, is(2017));
			});
	}

	@Test(expected = MustHaveProvider.class)
	public void testThreeParameterProvideMethodFailsIfOptionalEmpty() {
		provide(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			string -> aLong -> integer -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testThreeParameterProvideMethodProvides() {
		String result = provide(
			PROVIDE_FUNCTION, String.class, Long.class, Integer.class,
			string -> aLong -> integer -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
				assertThat(integer, is(2017));

				return "The result";
			});

		assertThat(result, is("The result"));
	}

	@Test(expected = MustHaveProvider.class)
	public void testTwoParameterProvideConsumerMethodFailsIfOptionalEmpty() {
		provideConsumer(
			PROVIDE_FUNCTION, Language.class, Long.class,
			string -> aLong -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testTwoParameterProvideConsumerMethodProvides() {
		provideConsumer(
			PROVIDE_FUNCTION, String.class, Long.class,
			string -> aLong -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
			});
	}

	@Test(expected = MustHaveProvider.class)
	public void testTwoParameterProvideMethodFailsIfOptionalEmpty() {
		provide(
			PROVIDE_FUNCTION, Language.class, Long.class,
			string -> aLong -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testTwoParameterProvideMethodProvides() {
		String result = provide(
			PROVIDE_FUNCTION, String.class, Long.class,
			string -> aLong -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));

				return "The result";
			});

		assertThat(result, is("The result"));
	}

}