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

package com.liferay.vulcan.result;

import com.liferay.vulcan.exception.FalsePredicateException;

import java.io.Closeable;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Implementation of the monadic "Try" type.
 *
 * Represents the result of an operation that could have succeeded (with a type
 * T) or failed (with an {@link Exception}).
 *
 * Only two descendants of this class are allowed: {@link Success} for the
 * success case and {@link Failure} for the failure one.
 *
 * Never instantiate this class directly. To create an instance of this class,
 * use {@link
 * #fromFallible(ThrowableSupplier)}
 * if you don't know if the operation is going to fail or not. {@link
 * #fail(Exception)}
 * to directly create a {@link Failure} from an {@link Exception}. Or {@link
 * #success(Object)}
 * to directly create a {@link Success} from a T.
 *
 * @author Alejandro Hernández
 * @review
 */
@SuppressWarnings("unused")
public abstract class Try<T> {

	/**
	 * Creates a new {@code Try} instance from an {@link Exception}. The
	 * instance will be created as a {@link Failure}.
	 *
	 * @param  exception the exception to include in the {@link Failure}
	 * @return the {@code Try} instance for the exception.
	 * @review
	 */
	public static <U> Try<U> fail(Exception exception) {
		return new Failure<>(exception);
	}

	/**
	 * Creates a new {@code Try} instance by executing a fallible lambda. If
	 * executing the lambda throws an exception a {@link Failure} instance will
	 * be created; otherwise, a {@link Success} instance with the lambda result
	 * will be created.
	 *
	 * @param  throwableSupplier the supplier to be executed in order to obtain
	 *         the value.
	 * @return a {@code Try} instance with the value obtained by the supplier:
	 *         {@link Failure} if the supplier throws an exception; {@link
	 *         Success} otherwise.
	 * @review
	 */
	public static <U> Try<U> fromFallible(
		ThrowableSupplier<U> throwableSupplier) {

		Objects.requireNonNull(throwableSupplier);

		try {
			return success(throwableSupplier.get());
		}
		catch (Exception exception) {
			return fail(exception);
		}
	}

	/**
	 * Creates a new {@code Try} instance by executing a fallible lambda with
	 * resources. If executing the lambda throws an exception a {@link Failure}
	 * instance will be created; otherwise, a {@link Success} instance with the
	 * lambda result will be created.
	 *
	 * @param  closeableSupplier the supplier to be executed in order to obtain
	 *         the closeable value.
	 * @param  throwableFunction the function to be executed in order to obtain
	 *         the value.
	 * @return a {@code Try} instance with the value obtained by the supplier:
	 *         {@link Failure} if the supplier throws an exception; {@link
	 *         Success} otherwise.
	 * @review
	 */
	public static <U, V extends Closeable> Try<U> fromFallibleWithResources(
		ThrowableSupplier<V> closeableSupplier,
		ThrowableFunction<V, U> throwableFunction) {

		Objects.requireNonNull(throwableFunction);
		Objects.requireNonNull(closeableSupplier);

		try (V v = closeableSupplier.get()) {
			return success(throwableFunction.apply(v));
		}
		catch (Exception exception) {
			return fail(exception);
		}
	}

	/**
	 * Creates a new {@code Try} instance from a valid object. The instance will
	 * be created as a {@link Success}.
	 *
	 * @param  u object to include as value of the {@link Success}.
	 * @return the {@code Try} instance for the value.
	 * @review
	 */
	public static <U> Try<U> success(U u) {
		return new Success<>(u);
	}

	/**
	 * If a value is present, and the value matches the given predicate, return
	 * a {@code Try} with the value, otherwise return a {@code Try} with an
	 * exception indicating the {@code false} predicate.
	 *
	 * @param  predicate a predicate to apply to the value, if present
	 * @return a {@code Try} with the value of this {@code Try} if a value is
	 *         present and the value matches the given predicate, otherwise a
	 *         {@code Try} with and exception for a {@code false} predicate.
	 * @review
	 */
	public abstract Try<T> filter(Predicate<T> predicate);

	/**
	 * If success case, apply the provided {@code Try}-bearing mapping function
	 * to it, return that result; otherwise propagate the failure.
	 *
	 * <p>
	 * This method is similar to {@link #map(ThrowableFunction)}, but the
	 * provided mapper is one whose result is already a {@code Try}, and if
	 * invoked, {@code flatMap} does not wrap it with an additional {@code Try}.
	 * </p>
	 *
	 * @param  function a mapping function to apply to the value, if success.
	 * @return the result of applying a {@code Try}-bearing mapping function to
	 *         the value of this {@code Try}, if a success, otherwise propagates
	 *         the failure.
	 * @review
	 */
	public abstract <U> Try<U> flatMap(
		ThrowableFunction<? super T, Try<U>> function);

	/**
	 * Returns the value T on success or throws the cause of the failure.
	 *
	 * @return T if success case, throws {@code Exception} otherwise.
	 * @throws Exception if this is a {@code Failure}.
	 * @review
	 */
	public abstract T get() throws Exception;

	/**
	 * Returns the value T on success or throws a {@link RuntimeException} with
	 * the cause of the failure.
	 *
	 * @return T if success case, throws {@code RuntimeException} otherwise.
	 * @review
	 */
	public abstract T getUnchecked();

	/**
	 * Returns {@code true} if this {@code Try} instance is a failure. Returns
	 * {@code false} otherwise.
	 *
	 * @return {@code true} if instance is a failure; {@code false} otherwise.
	 * @review
	 */
	public abstract boolean isFailure();

	/**
	 * Returns {@code true} if this {@code Try} instance is a success. Returns
	 * {@code false} otherwise.
	 *
	 * @return {@code true} if instance is a success; {@code false} otherwise.
	 * @review
	 */
	public abstract boolean isSuccess();

	/**
	 * If success, apply the provided mapping function to it, and if the
	 * function doesn't throw an exception, return it inside a {@code Try}.
	 * Otherwise return a {@code Failure}.
	 *
	 * @param  throwableFunction a mapping function to apply to the value, if
	 *         success.
	 * @return a {@code Try} with the result of applying a mapping function to
	 *         the value inside this {@code Try}, if success case; a {@code
	 *         Failure} describing the exception otherwise.
	 * @review
	 */
	public abstract <U> Try<U> map(
		ThrowableFunction<? super T, ? extends U> throwableFunction);

	/**
	 * If failure, apply the provided mapping function to the exception.
	 * Otherwise return the {@code Success}.
	 *
	 * @param  function a mapping function to apply to the exception, if
	 *         failure.
	 * @return a {@code Try} with the result of applying a mapping function to
	 *         the exception inside this {@code Try}, if failure case: the same
	 *         {@code Success}, otherwise.
	 * @review
	 */
	public abstract <X extends Exception> Try<T> mapFail(
		Function<Exception, X> function);

	/**
	 * If failure, and the class of the exception match the handed param class,
	 * replace the actual exception with the one provided by the supplier.
	 * Otherwise return the {@code Success}.
	 *
	 * @param  supplier a supplier for getting the new exception, if failure and
	 *         exception classes match.
	 * @return a {@code Try} with the result of the exception supplier, if
	 *         failure case and exception classes match: the same {@code
	 *         Success}, otherwise.
	 * @review
	 */
	public abstract <X extends Exception, Y extends Exception> Try<T>
		mapFailMatching(Class<Y> exceptionClass, Supplier<X> supplier);

	/**
	 * Return the value if success, otherwise return {@code other}.
	 *
	 * @param  other the value to be returned if failure.
	 * @return the value, if success, otherwise {@code other}.
	 * @review
	 */
	public abstract T orElse(T other);

	/**
	 * Return the value if success, otherwise invoke {@code supplier} and return
	 * the result of that invocation.
	 *
	 * @param  supplier a {@code Supplier} whose result is returned if failure.
	 * @return the value if success otherwise the result of {@code
	 *         supplier.get()}.
	 * @review
	 */
	public abstract T orElseGet(Supplier<? extends T> supplier);

	/**
	 * Return the contained value, if success, otherwise throw an exception to
	 * be created by the provided supplier.
	 *
	 * @param  supplier The supplier which will return the exception to be
	 *         thrown
	 * @return the present value
	 * @review
	 */
	public abstract <X extends Throwable> T orElseThrow(
			Supplier<? extends X> supplier)
		throws X;

	/**
	 * Returns the result extracted from the provided function if this instance
	 * is a failure case. Returns the inner result in the success case.
	 *
	 * <p>
	 * The lambda passed as parameter gives access to the exception in the case
	 * of failure.
	 * </p>
	 *
	 * @param  function function to execute on failure result.
	 * @return the result from the function, if failure; the inner value on
	 *         success.
	 * @review
	 */
	public abstract T recover(Function<? super Exception, T> function);

	/**
	 * Returns a new {@code Try} instance, extracted from the provided function
	 * if this instance is a failure case. Returns the current success
	 * otherwise.
	 *
	 * <p>
	 * The lambda passed as parameter gives access to the exception in the case
	 * of failure.
	 * </p>
	 *
	 * @param  throwableFunction function to execute on failure result.
	 * @return the new {@code Try} result from the function, if failure; the
	 *         current success, otherwise.
	 * @review
	 */
	public abstract Try<T> recoverWith(
		ThrowableFunction<? super Exception, Try<T>> throwableFunction);

	/**
	 * Implementation of the failure case of a {@code Try}.
	 *
	 * Don't try to directly instantiate this class. To create an instance of
	 * this class, use {@link #fromFallible(ThrowableSupplier)} if you don't
	 * know if the operation is going to fail or not. Or
	 * {@link #fail(Exception)} to directly create a {@link Failure} from an
	 * {@code Exception}.
	 *
	 * @review
	 */
	public static class Failure<T> extends Try<T> {

		@Override
		public Try<T> filter(Predicate<T> predicate) {
			return this;
		}

		@Override
		public <U> Try<U> flatMap(
			ThrowableFunction<? super T, Try<U>> throwableFunction) {

			Objects.requireNonNull(throwableFunction);

			return Try.fail(_exception);
		}

		@Override
		public T get() throws Exception {
			throw _exception;
		}

		/**
		 * Returns the cause of this failure.
		 *
		 * @return the cause of this failure.
		 * @review
		 */
		public Exception getException() {
			return _exception;
		}

		@Override
		public T getUnchecked() {
			if (_exception instanceof RuntimeException) {
				throw (RuntimeException)_exception;
			}

			throw new RuntimeException(_exception);
		}

		@Override
		public boolean isFailure() {
			return true;
		}

		@Override
		public boolean isSuccess() {
			return false;
		}

		@Override
		public <U> Try<U> map(
			ThrowableFunction<? super T, ? extends U> throwableFunction) {

			Objects.requireNonNull(throwableFunction);

			return Try.fail(_exception);
		}

		@Override
		public <X extends Exception> Try<T> mapFail(
			Function<Exception, X> function) {

			Objects.requireNonNull(function);

			return Try.fail(function.apply(_exception));
		}

		@Override
		public <X extends Exception, Y extends Exception> Try<T>
			mapFailMatching(Class<Y> exceptionClass, Supplier<X> supplier) {

			Objects.requireNonNull(supplier);

			Class<? extends Exception> causeClass = _exception.getClass();

			if (causeClass.equals(exceptionClass)) {
				return Try.fail(supplier.get());
			}

			return this;
		}

		@Override
		public T orElse(T other) {
			return other;
		}

		@Override
		public T orElseGet(Supplier<? extends T> supplier) {
			return supplier.get();
		}

		@Override
		public <X extends Throwable> T orElseThrow(
				Supplier<? extends X> supplier)
			throws X {

			throw supplier.get();
		}

		@Override
		public T recover(Function<? super Exception, T> function) {
			Objects.requireNonNull(function);

			return function.apply(_exception);
		}

		@Override
		public Try<T> recoverWith(
			ThrowableFunction<? super Exception, Try<T>> throwableFunction) {

			Objects.requireNonNull(throwableFunction);
			try {
				return throwableFunction.apply(_exception);
			}
			catch (Exception e) {
				return Try.fail(e);
			}
		}

		private Failure(Exception exception) {
			_exception = exception;
		}

		private final Exception _exception;

	}

	/**
	 * Implementation of the success case of a {@code Try}.
	 *
	 * Don't try to directly instantiate this class. To create an instance of
	 * this class, use {@link #fromFallible(ThrowableSupplier)} if you don't
	 * know if the operation is going to fail or not. Or
	 * {@link #success(Object)} to directly create a {@link Success} from a T.
	 *
	 * @review
	 */
	public static class Success<T> extends Try<T> {

		@Override
		public Try<T> filter(Predicate<T> predicate) {
			Objects.requireNonNull(predicate);

			if (predicate.test(_value)) {
				return this;
			}
			else {
				return Try.fail(new FalsePredicateException(_value));
			}
		}

		@Override
		public <U> Try<U> flatMap(
			ThrowableFunction<? super T, Try<U>> function) {

			Objects.requireNonNull(function);

			try {
				return function.apply(_value);
			}
			catch (Exception e) {
				return Try.fail(e);
			}
		}

		@Override
		public T get() throws Exception {
			return _value;
		}

		@Override
		public T getUnchecked() {
			return _value;
		}

		/**
		 * Returns the inner value of this {@code Success}.
		 *
		 * @return the inner value of the {@code Success}.
		 * @review
		 */
		public T getValue() {
			return _value;
		}

		@Override
		public boolean isFailure() {
			return false;
		}

		@Override
		public boolean isSuccess() {
			return true;
		}

		@Override
		public <U> Try<U> map(
			ThrowableFunction<? super T, ? extends U> throwableFunction) {

			Objects.requireNonNull(throwableFunction);

			try {
				return Try.success(throwableFunction.apply(_value));
			}
			catch (Exception e) {
				return Try.fail(e);
			}
		}

		@Override
		public <X extends Exception> Try<T> mapFail(
			Function<Exception, X> function) {

			Objects.requireNonNull(function);

			return this;
		}

		@Override
		public <X extends Exception, Y extends Exception> Try<T>
			mapFailMatching(Class<Y> exceptionClass, Supplier<X> supplier) {

			Objects.requireNonNull(supplier);

			return this;
		}

		@Override
		public T orElse(T other) {
			return _value;
		}

		@Override
		public T orElseGet(Supplier<? extends T> supplier) {
			return _value;
		}

		@Override
		public <X extends Throwable> T orElseThrow(
				Supplier<? extends X> supplier)
			throws X {

			return _value;
		}

		@Override
		public T recover(Function<? super Exception, T> function) {
			Objects.requireNonNull(function);

			return _value;
		}

		@Override
		public Try<T> recoverWith(
			ThrowableFunction<? super Exception, Try<T>> throwableFunction) {

			Objects.requireNonNull(throwableFunction);

			return this;
		}

		private Success(T value) {
			_value = value;
		}

		private final T _value;

	}

	private Try() {
	}

}