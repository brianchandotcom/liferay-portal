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
import static com.liferay.apio.architect.test.util.result.TryMatchers.aFailTry;
import static com.liferay.apio.architect.test.util.result.TryMatchers.aTryWithValueThat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.language.Language;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.NotFoundException;

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

	@Test(expected = NotFoundException.class)
	public void testFiveParameterProvideConsumerMethodFailsIfNoProvider()
		throws Exception {

		provideConsumer(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			Boolean.class,
			string -> aLong -> integer -> aBoolean -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testFiveParameterProvideMethodFailsIfNoProvider() {
		Try<Object> aTry = provide(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			Boolean.class, Float.class,
			string -> aLong -> integer -> aBoolean -> list -> {
				throw new AssertionError("This lambda should not be called");
			});

		assertThat(aTry, is(aFailTry()));
	}

	@Test
	public void testFiveParameterProvideMethodProvides() {
		Try<String> result = provide(
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

		assertThat(result, is(aTryWithValueThat(is("The result"))));
	}

	@Test(expected = NotFoundException.class)
	public void testFourParameterProvideConsumerMethodFailsIfNoProvider()
		throws Exception {

		provideConsumer(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			Boolean.class,
			string -> aLong -> integer -> aBoolean -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testFourParameterProvideConsumerMethodProvides()
		throws Exception {

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

	@Test
	public void testFourParameterProvideMethodFailsIfNoProvider() {
		Try<Object> aTry = provide(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			Boolean.class,
			string -> aLong -> integer -> aBoolean -> {
				throw new AssertionError("This lambda should not be called");
			});

		assertThat(aTry, is(aFailTry()));
	}

	@Test
	public void testFourParameterProvideMethodProvides() {
		Try<String> result = provide(
			PROVIDE_FUNCTION, String.class, Long.class, Integer.class,
			Boolean.class,
			string -> aLong -> integer -> aBoolean -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
				assertThat(integer, is(2017));
				assertThat(aBoolean, is(true));

				return "The result";
			});

		assertThat(result, is(aTryWithValueThat(is("The result"))));
	}

	@Test(expected = NotFoundException.class)
	public void testOneParameterProvideConsumerMethodFailsIfNoProvider()
		throws Exception {

		provideConsumer(
			PROVIDE_FUNCTION, Language.class,
			string -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testOneParameterProvideConsumerMethodProvides()
		throws Exception {

		provideConsumer(
			PROVIDE_FUNCTION, String.class,
			string -> assertThat(string, is("Apio")));
	}

	@Test
	public void testOneParameterProvideMethodFailsIfNoProvider() {
		Try<Object> aTry = provide(
			PROVIDE_FUNCTION, Language.class,
			string -> {
				throw new AssertionError("This lambda should not be called");
			});

		assertThat(aTry, is(aFailTry()));
	}

	@Test
	public void testOneParameterProvideMethodProvides() {
		Try<String> result = provide(
			PROVIDE_FUNCTION, String.class,
			string -> {
				assertThat(string, is("Apio"));

				return "The result";
			});

		assertThat(result, is(aTryWithValueThat(is("The result"))));
	}

	@Test(expected = NotFoundException.class)
	public void testThreeParameterProvideConsumerMethodFailsIfNoProvider()
		throws Exception {

		provideConsumer(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			string -> aLong -> integer -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testThreeParameterProvideConsumerMethodProvides()
		throws Exception {

		provideConsumer(
			PROVIDE_FUNCTION, String.class, Long.class, Integer.class,
			string -> aLong -> integer -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
				assertThat(integer, is(2017));
			});
	}

	@Test
	public void testThreeParameterProvideMethodFailsIfNoProvider() {
		Try<Object> aTry = provide(
			PROVIDE_FUNCTION, Language.class, Long.class, Integer.class,
			string -> aLong -> integer -> {
				throw new AssertionError("This lambda should not be called");
			});

		assertThat(aTry, is(aFailTry()));
	}

	@Test
	public void testThreeParameterProvideMethodProvides() {
		Try<String> result = provide(
			PROVIDE_FUNCTION, String.class, Long.class, Integer.class,
			string -> aLong -> integer -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
				assertThat(integer, is(2017));

				return "The result";
			});

		assertThat(result, is(aTryWithValueThat(is("The result"))));
	}

	@Test(expected = NotFoundException.class)
	public void testTwoParameterProvideConsumerMethodFailsIfNoProvider()
		throws Exception {

		provideConsumer(
			PROVIDE_FUNCTION, Language.class, Long.class,
			string -> aLong -> {
				throw new AssertionError("This lambda should not be called");
			});
	}

	@Test
	public void testTwoParameterProvideConsumerMethodProvides()
		throws Exception {

		provideConsumer(
			PROVIDE_FUNCTION, String.class, Long.class,
			string -> aLong -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));
			});
	}

	@Test
	public void testTwoParameterProvideMethodFailsIfNoProvider() {
		Try<Object> aTry = provide(
			PROVIDE_FUNCTION, Language.class, Long.class,
			string -> aLong -> {
				throw new AssertionError("This lambda should not be called");
			});

		assertThat(aTry, is(aFailTry()));
	}

	@Test
	public void testTwoParameterProvideMethodProvides() {
		Try<String> result = provide(
			PROVIDE_FUNCTION, String.class, Long.class,
			string -> aLong -> {
				assertThat(string, is("Apio"));
				assertThat(aLong, is(42L));

				return "The result";
			});

		assertThat(result, is(aTryWithValueThat(is("The result"))));
	}

}