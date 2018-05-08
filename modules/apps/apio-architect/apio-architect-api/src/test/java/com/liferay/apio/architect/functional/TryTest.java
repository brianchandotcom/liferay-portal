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

package com.liferay.apio.architect.functional;

import static com.liferay.apio.architect.test.util.result.TryMatchers.aFailTry;
import static com.liferay.apio.architect.test.util.result.TryMatchers.aSuccessTry;
import static com.liferay.apio.architect.test.util.result.TryMatchers.aTryWithValueThat;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import com.liferay.apio.architect.exception.FalsePredicateException;

import java.io.Closeable;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
@RunWith(JUnitParamsRunner.class)
public class TryTest {

	public static final String FAIL = "fail";

	public static final String SUCCESS = "success";

	/**
	 * Returns a new list of failures for use in tests.
	 *
	 * @return the list of failures
	 */
	public Try[] fail() {
		return new Try[] {Try.fail(new IllegalArgumentException())};
	}

	/**
	 * Returns a new list of successes for use in tests.
	 *
	 * @return the list of successes
	 */
	public Try[] success() {
		return new Try[] {Try.success("Live long")};
	}

	@Test
	public void testInvokingFallibleWithResourcesClosesStream()
		throws Exception {

		Closeable closeable = Mockito.mock(Closeable.class);

		Try.fromFallibleWithResources(() -> closeable, __ -> "Live long");

		Mockito.verify(
			closeable
		).close();
	}

	@Test
	public void testInvokingFallibleWithResourcesWithExceptionCreatesFailure() {
		Try<String> stringTry = Try.fromFallibleWithResources(
			() -> () -> {
			},
			__ -> {
				throw new IllegalArgumentException();
			});

		assertThat(stringTry, is(aFailTry()));
	}

	@Test
	public void testInvokingFallibleWithResourcesWithValueCreatesSuccess() {
		Try<String> stringTry = Try.fromFallibleWithResources(
			() -> () -> {
			},
			__ -> "Live long");

		assertThat(stringTry, is(aSuccessTry()));
	}

	@Parameters(method = FAIL)
	@Test(expected = IllegalArgumentException.class)
	public void testInvokingFilterOnFailureThrowsException(
			Try<String> stringTry)
		throws Exception {

		stringTry.filter(
			__ -> true
		).get();
	}

	@Parameters(method = SUCCESS)
	@Test(expected = FalsePredicateException.class)
	public void testInvokingFilterOnSuccessWithFalsePredicateReturnsException(
			Try<String> stringTry)
		throws Exception {

		stringTry.filter(
			string -> string.startsWith("long")
		).get();
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingFilterOnSuccessWithValidPredicateReturnsValue(
		Try<String> stringTry) {

		assertThat(
			stringTry.filter(string -> string.startsWith("Live")),
			is(aTryWithValueThat(equalTo("Live long"))));
	}

	@Parameters(method = FAIL)
	@Test
	public void testInvokingFoldOnFailureExecutesFailureFunction(
		Try<String> stringTry) {

		Integer integer = stringTry.fold(exception -> 3, string -> 5);

		assertThat(integer, is(3));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingFoldOnSuccessExecutesSuccessFunction(
		Try<String> stringTry) {

		Integer integer = stringTry.fold(exception -> 3, string -> 5);

		assertThat(integer, is(5));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingFoldOnSuccessWithFailureExecutesBothFunctions(
		Try<String> stringTry) {

		Integer integer = stringTry.fold(
			exception -> {
				assertThat(
					exception, is(instanceOf(IllegalArgumentException.class)));

				return 3;
			},
			string -> {
				throw new IllegalArgumentException();
			});

		assertThat(integer, is(3));
	}

	@Test
	public void testInvokingFromFallibleWithExceptionCreatesFailure() {
		Try<String> stringTry = Try.fromFallible(
			() -> {
				throw new IllegalArgumentException();
			});

		assertThat(stringTry, is(aFailTry()));
	}

	@Test
	public void testInvokingFromFallibleWithValueCreatesSuccess() {
		Try<String> stringTry = Try.fromFallible(() -> "Live long");

		assertThat(stringTry, is(aSuccessTry()));
	}

	@Test
	public void testInvokingFromOptionalCreatesFailureFromEmptyOptional() {
		Try<Integer> integerTry = Try.fromOptional(
			Optional::empty, () -> new IllegalArgumentException("Apio"));

		assertThat(integerTry.isFailure(), is(true));

		integerTry.ifFailure(
			exception -> {
				assertThat(
					exception.getClass(), is(IllegalArgumentException.class));

				assertThat(exception.getMessage(), is("Apio"));
			});
	}

	@Test
	public void testInvokingFromOptionalCreatesSuccessFromValueOptional() {
		Try<Integer> integerTry = Try.fromOptional(
			() -> Optional.of(42), Exception::new);

		assertThat(integerTry.isSuccess(), is(true));
		assertThat(integerTry.getUnchecked(), is(42));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingGetOnSuccessShouldReturnValue(Try<String> stringTry)
		throws Exception {

		assertThat(stringTry.get(), is("Live long"));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingGetValueOnSuccessShouldReturnValue(
		Try<String> stringTry) {

		Try.Success success = (Try.Success)stringTry;

		assertThat(success.getValue(), is("Live long"));
	}

	@Parameters(method = FAIL)
	@Test(expected = IllegalArgumentException.class)
	public void testInvokingIfFailureWithFailureInvokesConsumer(
			Try<String> stringTry)
		throws Exception {

		List<Exception> list = new ArrayList<>();

		stringTry.ifFailure(list::add);

		throw list.get(0);
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingIfFailureWithSuccessDoesNotInvokeConsumer(
		Try<String> stringTry) {

		List<Exception> list = new ArrayList<>();

		stringTry.ifFailure(list::add);

		assertThat(list.isEmpty(), is(true));
	}

	@Parameters(method = FAIL)
	@Test
	public void testInvokingIfSuccessWithFailureDoesNotInvokeConsumer(
		Try<String> stringTry) {

		List<String> list = new ArrayList<>();

		stringTry.ifSuccess(list::add);

		assertThat(list.isEmpty(), is(true));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingIfSuccessWithSuccessInvokesConsumer(
		Try<String> stringTry) {

		List<String> list = new ArrayList<>();

		stringTry.ifSuccess(list::add);

		assertThat(list.get(0), is("Live long"));
	}

	@Parameters(method = FAIL)
	@Test
	public void testInvokingIsFailureOnFailureReturnsTrue(
		Try<String> stringTry) {

		assertThat(stringTry.isFailure(), is(true));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingIsFailureOnSuccessReturnsFalse(
		Try<String> stringTry) {

		assertThat(stringTry.isFailure(), is(false));
	}

	@Parameters(method = FAIL)
	@Test
	public void testInvokingIsSuccessOnFailureReturnsFalse(
		Try<String> stringTry) {

		assertThat(stringTry.isSuccess(), is(false));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingIsSuccessOnSuccessReturnsFalse(
		Try<String> stringTry) {

		assertThat(stringTry.isSuccess(), is(true));
	}

	@Parameters(method = FAIL)
	@Test(expected = IllegalArgumentException.class)
	public void testInvokingMapFailMatchingOnFailureShouldThrowFirstException(
			Try<String> stringTry)
		throws Exception {

		stringTry.mapFailMatching(
			RuntimeException.class, IOException::new
		).get();
	}

	@Parameters(method = FAIL)
	@Test(expected = IOException.class)
	public void testInvokingMapFailMatchingOnFailureShouldThrowMatchedException(
			Try<String> stringTry)
		throws Exception {

		stringTry.mapFailMatching(
			IllegalArgumentException.class, IOException::new
		).get();
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingMapFailMatchingOnSuccessShouldReturnValue(
		Try<String> stringTry) {

		assertThat(
			stringTry.mapFailMatching(RuntimeException.class, IOException::new),
			is(aTryWithValueThat(equalTo("Live long"))));
	}

	@Parameters(method = FAIL)
	@Test(expected = IOException.class)
	public void testInvokingMapFailOnFailureShouldThrowTransformedException(
			Try<String> stringTry)
		throws Exception {

		stringTry.mapFail(
			__ -> new IOException()
		).get();
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingMapFailOnSuccessShouldReturnValue(
		Try<String> stringTry) {

		assertThat(
			stringTry.mapFail(__ -> new IOException()),
			is(aTryWithValueThat(equalTo("Live long"))));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingMapOptionalCreatesFailureWithEmptyOptional(
		Try<String> stringTry) {

		Try<String> newStringTry1 = stringTry.mapOptional(
			__ -> Optional.empty(), () -> new IllegalArgumentException("Apio"));

		assertThat(newStringTry1.isFailure(), is(true));

		newStringTry1.ifFailure(
			exception -> {
				assertThat(
					exception.getClass(), is(IllegalArgumentException.class));

				assertThat(exception.getMessage(), is("Apio"));
			});

		Try<String> newStringTry2 = stringTry.mapOptional(
			__ -> Optional.empty());

		assertThat(newStringTry2.isFailure(), is(true));

		newStringTry2.ifFailure(
			exception -> assertThat(
				exception.getClass(), is(NoSuchElementException.class)));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingMapOptionalCreatesSuccessFromValueOptional(
		Try<String> stringTry) {

		Try<String> newStringTry = stringTry.mapOptional(
			string -> Optional.of(string + " and prosper"), Exception::new);

		assertThat(newStringTry.isSuccess(), is(true));
		assertThat(newStringTry.getUnchecked(), is("Live long and prosper"));
	}

	@Parameters(method = FAIL)
	@Test
	public void testInvokingOrElseGetOnFailureShouldReturnValue(
		Try<String> stringTry) {

		assertThat(stringTry.orElseGet(() -> "and prosper"), is("and prosper"));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingOrElseGetOnSuccessShouldReturnPreviousValue(
		Try<String> stringTry) {

		assertThat(stringTry.orElseGet(() -> "and prosper"), is("Live long"));
	}

	@Parameters(method = FAIL)
	@Test
	public void testInvokingOrElseOnFailureShouldReturnValue(
		Try<String> stringTry) {

		assertThat(stringTry.orElse("and prosper"), is("and prosper"));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingOrElseOnSuccessShouldReturnPreviousValue(
		Try<String> stringTry) {

		assertThat(stringTry.orElse("and prosper"), is("Live long"));
	}

	@Parameters(method = FAIL)
	@Test(expected = UnsupportedOperationException.class)
	public void testInvokingOrElseThrowOnFailureShouldThrowNewException(
		Try<String> stringTry) {

		stringTry.orElseThrow(UnsupportedOperationException::new);
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingOrElseThrowOnSuccessShouldReturnValue(
		Try<String> stringTry) {

		assertThat(
			stringTry.orElseThrow(UnsupportedOperationException::new),
			is("Live long"));
	}

	@Parameters(method = FAIL)
	@Test
	public void testInvokingToOptionalWithFailureReturnEmptyOptional(
		Try<String> stringTry) {

		assertThat(stringTry.toOptional(), is(emptyOptional()));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingToOptionalWithSuccessReturnValuedOptional(
		Try<String> stringTry) {

		assertThat(
			stringTry.toOptional(),
			is(optionalWithValue(equalTo("Live long"))));
	}

	@Parameters(method = FAIL)
	@Test
	public void testInvokingVoidFoldOnFailureExecutesFailureConsumer(
		Try<String> stringTry) {

		List<Integer> list = new ArrayList<>();

		stringTry.voidFold(__ -> list.add(3), __ -> list.add(5));

		assertThat(list, contains(3));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingVoidFoldOnSuccessExecutesSuccessConsumer(
		Try<String> stringTry) {

		List<Integer> list = new ArrayList<>();

		stringTry.voidFold(__ -> list.add(3), __ -> list.add(5));

		assertThat(list, contains(5));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testInvokingVoidFoldOnSuccessWithFailureExecutesBothConsumers(
		Try<String> stringTry) {

		List<Integer> list = new ArrayList<>();

		stringTry.voidFold(
			exception -> {
				assertThat(
					exception, is(instanceOf(IllegalArgumentException.class)));

				list.add(3);
			},
			__ -> {
				throw new IllegalArgumentException();
			});

		assertThat(list, contains(3));
	}

	@Parameters(method = FAIL)
	@Test
	public void testOnFailureShouldBeFailure(Try<String> stringTry) {
		assertThat(stringTry, is(aFailTry()));
	}

	@Test(expected = RuntimeException.class)
	public void testOnFailureWithUncheckedExceptionShouldThrowRuntimeException() {
		Try.fail(
			new Exception()
		).getUnchecked();
	}

	@Parameters(method = FAIL)
	@Test(expected = IllegalArgumentException.class)
	public void testOnGetShouldThrowTypedException(Try<String> stringTry)
		throws Exception {

		stringTry.get();
	}

	@Parameters(method = FAIL)
	@Test(expected = RuntimeException.class)
	public void testOnGetUncheckedShouldThrowRuntimeException(
		Try<String> stringTry) {

		stringTry.getUnchecked();
	}

	@Parameters(method = FAIL)
	@Test
	public void testOnInvokingFlatMapOnFailureShouldBeFailure(
		Try<String> stringTry) {

		Try<String> newTry = stringTry.flatMap(
			string -> Try.success(string + " and prosper"));

		assertThat(newTry, is(aFailTry()));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testOnInvokingFlatMapOnSuccessShouldReturnTransformedValue(
		Try<String> stringTry) {

		Try<String> newTry = stringTry.flatMap(
			string -> Try.success(string + " and prosper"));

		assertThat(
			newTry, is(aTryWithValueThat(equalTo("Live long and prosper"))));
	}

	@Parameters(method = FAIL)
	@Test
	public void testOnInvokingMapOnFailureShouldBeFailure(
		Try<String> stringTry) {

		Try<String> newTry = stringTry.map(string -> string + " and prosper");

		assertThat(newTry, is(aFailTry()));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testOnInvokingMapOnSuccessShouldReturnTransformedValue(
		Try<String> stringTry) {

		Try<String> newTry = stringTry.map(string -> string + " and prosper");

		assertThat(
			newTry, is(aTryWithValueThat(equalTo("Live long and prosper"))));
	}

	@Parameters(method = SUCCESS)
	@Test(expected = IOException.class)
	public void testOnInvokingThrowableFlatMapOnSuccessShouldReturnNewException(
			Try<String> stringTry)
		throws Exception {

		stringTry.flatMap(
			string -> {
				throw new IOException();
			}
		).get();
	}

	@Parameters(method = SUCCESS)
	@Test(expected = IOException.class)
	public void testOnInvokingThrowableMapOnSuccessShouldReturnNewException(
			Try<String> stringTry)
		throws Exception {

		stringTry.map(
			string -> {
				throw new IOException();
			}
		).get();
	}

	@Parameters(method = FAIL)
	@Test(expected = IOException.class)
	public void testOnRecoveringWithExceptionOnFailureShouldReturnNewException(
			Try<String> stringTry)
		throws Exception {

		Try<String> newTry = stringTry.recoverWith(
			__ -> {
				throw new IOException();
			});

		Try.Failure failure = (Try.Failure)newTry;

		throw failure.getException();
	}

	@Parameters(method = FAIL)
	@Test(expected = IOException.class)
	public void testOnRecoveringWithExceptionOnFailureShouldThrowNewException(
			Try<String> stringTry)
		throws Exception {

		stringTry.recoverWith(
			__ -> Try.fail(new IOException())
		).get();
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testOnRecoveringWithFailureOnSuccessShouldReturnFirstValue(
		Try<String> stringTry) {

		assertThat(
			stringTry.recoverWith(__ -> Try.fail(new Exception())),
			is(aTryWithValueThat(equalTo("Live long"))));
	}

	@Parameters(method = FAIL)
	@Test
	public void testOnRecoveringWithShouldHavePreviousExceptionAsParameter(
		Try<String> stringTry) {

		Try.Failure failure = (Try.Failure)stringTry;

		stringTry.recoverWith(
			exception -> {
				assertThat(failure.getException(), is(exception));

				return Try.success("and prosper");
			});
	}

	@Parameters(method = FAIL)
	@Test
	public void testOnRecoveringWithSuccessOnFailureShouldReturnNewValue(
		Try<String> stringTry) {

		assertThat(
			stringTry.recoverWith(__ -> Try.fromFallible(() -> "and prosper")),
			is(aTryWithValueThat(equalTo("and prosper"))));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testOnRecoveringWithSuccessOnSuccessShouldReturnFirstValue(
		Try<String> stringTry) {

		assertThat(
			stringTry.recoverWith(__ -> Try.fromFallible(() -> "and prosper")),
			is(aTryWithValueThat(equalTo("Live long"))));
	}

	@Parameters(method = FAIL)
	@Test
	public void testOnRecoverWithValueOnFailureShouldReturnValue(
		Try<String> stringTry) {

		String string = stringTry.recover(__ -> "and prosper");

		assertThat(string, is("and prosper"));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testOnRecoverWithValueOnSuccessShouldReturnPreviousValue(
		Try<String> stringTry) {

		String string = stringTry.recover(__ -> "and prosper");

		assertThat(string, is("Live long"));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testOnSuccessShouldBeSuccess(Try<String> stringTry) {
		assertThat(stringTry, is(aSuccessTry()));
	}

	@Parameters(method = SUCCESS)
	@Test
	public void testOnSuccessShouldGetValue(Try<String> stringTry) {
		assertThat(stringTry, is(aTryWithValueThat(equalTo("Live long"))));
	}

}