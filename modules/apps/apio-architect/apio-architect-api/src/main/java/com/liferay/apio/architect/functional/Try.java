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

import com.liferay.apio.architect.consumer.throwable.ThrowableConsumer;
import com.liferay.apio.architect.exception.FalsePredicateException;
import com.liferay.apio.architect.function.throwable.ThrowableFunction;
import com.liferay.apio.architect.supplier.ThrowableSupplier;

import java.io.Closeable;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Implements the monadic {@code Try} type. This class's instances represent the
 * result of an operation that either succeeds with type {@code T} or fails with
 * an exception. Only two descendants of this class are allowed: {@link Success}
 * for the success case, and {@link Failure} for the failure case.
 *
 * Never instantiate this class directly. If you're unsure whether the operation
 * will succeed, use {@link #fromFallible(ThrowableSupplier)} to create an
 * instance of this class. To create a {@code Failure} instance directly
 * from an exception, use {@link #fail(Exception)}. To create a {@code Success}
 * instance directly from {@code T}, use {@link #success(Object)}.
 *
 * @author Alejandro Hernández
 * @param  <T> the possible value type
 */
@SuppressWarnings("unused")
public abstract class Try<T> {

	/**
	 * Creates a new {@code Try} instance from an exception. This method creates
	 * the instance as a {@code Failure} object.
	 *
	 * @param  exception the exception to create the {@code Failure} from
	 * @return the {@code Try} instance created from the exception
	 */
	public static <T> Try<T> fail(Exception exception) {
		return new Failure<>(exception);
	}

	/**
	 * Creates a new {@code Try} instance by executing a fallible lambda in a
	 * {@link ThrowableSupplier}. If this throws an exception, a {@code Failure}
	 * instance is created. Otherwise, a {@code Success} instance with the
	 * lambda'S result is created.
	 *
	 * @param  throwableSupplier the throwable supplier that contains the
	 *         fallible lambda
	 * @return {@code Failure} if the throwable supplier throws an exception;
	 *         {@code Success} otherwise
	 */
	public static <T> Try<T> fromFallible(
		ThrowableSupplier<T> throwableSupplier) {

		Objects.requireNonNull(throwableSupplier);

		try {
			return success(throwableSupplier.get());
		}
		catch (Exception exception) {
			return fail(exception);
		}
	}

	/**
	 * Creates a new {@code Try} instance by composing two functions: {@code
	 * throwableSupplier} and {@code throwableFunction}. The result of {@code
	 * throwableSupplier} is passed as the only parameter to {@code
	 * throwableFunction}. If either function fails, a {@code Failure} instance
	 * is returned. Otherwise, a {@code Success} instance with the results of
	 * {@code throwableFunction} is returned.
	 *
	 * @param  throwableFunction the function to execute with the results of
	 *         {@code throwableSupplier}
	 * @return a {@code Success} instance if the functions succeed; a {@code
	 *         Failure} instance otherwise
	 */
	public static <T, S extends Closeable> Try<T> fromFallibleWithResources(
		ThrowableSupplier<S> throwableSupplier,
		ThrowableFunction<S, T> throwableFunction) {

		Objects.requireNonNull(throwableFunction);
		Objects.requireNonNull(throwableSupplier);

		try (S s = throwableSupplier.get()) {
			return success(throwableFunction.apply(s));
		}
		catch (Exception exception) {
			return fail(exception);
		}
	}

	/**
	 * Creates a new {@code Try} instance by executing a fallible lambda that
	 * returns an {@code Optional} in a {@link ThrowableSupplier}. If this
	 * throws an exception, a {@code Failure} instance is created. If the
	 * returned {@code Optional} is empty, a {@code Failure} containing the
	 * exception supplier's value is returned. Otherwise, a {@code Success}
	 * instance with the lambda's {@code Optional} result is created.
	 *
	 * @param  throwableSupplier the throwable supplier that contains the
	 *         fallible lambda that returns an {@code Optional}
	 * @param  supplier the supplier for the exception if the obtained {@code
	 *         Optional} is {@code Optional#empty()}
	 * @return {@code Failure} if the throwable supplier throws an exception, or
	 *         the {@code Optional} is empty; {@code Success} otherwise
	 */
	public static <T> Try<T> fromOptional(
		ThrowableSupplier<Optional<T>> throwableSupplier,
		Supplier<? extends Exception> supplier) {

		Objects.requireNonNull(throwableSupplier);
		Objects.requireNonNull(supplier);

		return Try.fromFallible(
			throwableSupplier
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class, supplier
		);
	}

	/**
	 * Creates a new {@code Try} instance from an object. This method creates
	 * the instance as a {@code Success} object.
	 *
	 * @param  t the object to create the {@code Success} object from
	 * @return the {@code Success} object
	 */
	public static <T> Try<T> success(T t) {
		return new Success<>(t);
	}

	/**
	 * Returns a {@code Try} instance with a value, if that value matches the
	 * predicate. Otherwise, this method returns a {@code Try} instance with an
	 * exception that indicates the {@code false} predicate.
	 *
	 * @param  predicate the predicate to apply to a value
	 * @return a {@code Try} instance with a value, if that value matches the
	 *         predicate; otherwise a {@code Try} instance with an exception for
	 *         a {@code {@code false}} predicate
	 */
	public abstract Try<T> filter(Predicate<T> predicate);

	/**
	 * Returns the result of applying the mapping function to the {@code
	 * Success} instance'S value, if the current {@code Try} instance is a
	 * {@code Success}; otherwise returns the {@code Failure} instance.
	 *
	 * <p>
	 * This method is similar to {@link #map(ThrowableFunction)}, but the
	 * mapping function'S result is already a {@code Try}, and if invoked,
	 * {@code flatMap} doesn't wrap it in an additional {@code Try}.
	 * </p>
	 *
	 * @param  function the mapping function
	 * @return the result of the mapping function if applied to the {@code
	 *         Success} instance'S value; the {@code Failure} instance otherwise
	 */
	public abstract <S> Try<S> flatMap(
		ThrowableFunction<? super T, Try<S>> function);

	/**
	 * Returns the value that results from applying {@code failureFunction} if
	 * this is a {@code Failure}, or {@code successFunction} if this is a {@code
	 * Success}.
	 *
	 * <p>
	 * If {@code successFunction} throws an {@code Exception}, this method
	 * returns the result of applying {@code failureFunction} to the new {@code
	 * Exception}.
	 * </p>
	 *
	 * @param  failureFunction the function to apply when this {@code Try} is a
	 *         {@code Failure}
	 * @param  successFunction the function to apply when this {@code Try} is a
	 *         {@code Success}
	 * @return the value that results from applying the corresponding function
	 */
	public abstract <S> S fold(
		Function<Exception, S> failureFunction,
		ThrowableFunction<T, S> successFunction);

	/**
	 * Returns a {@code Success} instance'S value, or a {@code Failure}
	 * instance'S exception. What this method returns therefore depends on
	 * whether the current {@code Try} instance is a {@code Success} or {@code
	 * Failure}.
	 *
	 * @return a {@code Success} instance'S value; otherwise the {@code Failure}
	 *         instance'S exception
	 * @throws Exception if the operation failed
	 */
	public abstract T get() throws Exception;

	/**
	 * Returns a {@code Success} instance'S value, or a {@code Failure}
	 * instance'S exception wrapped in a {@code RuntimeException}. What this
	 * method returns therefore depends on whether the current {@code Try}
	 * instance is a {@code Success} or {@code Failure}.
	 *
	 * @return a {@code Success} instance'S value; otherwise the {@code Failure}
	 *         instance'S exception wrapped in a {@code RuntimeException}
	 */
	public abstract T getUnchecked();

	/**
	 * Calls the provided consumer if the current {@code Try} instance is a
	 * {@code Failure}; otherwise nothing occurs.
	 *
	 * @param consumer the consumer
	 */
	public abstract void ifFailure(Consumer<Exception> consumer);

	/**
	 * Calls the provided consumer if the current {@code Try} instance is a
	 * {@code Success}; otherwise nothing occurs.
	 *
	 * @param consumer the consumer
	 */
	public abstract void ifSuccess(Consumer<T> consumer);

	/**
	 * Returns {@code true} if the current {@code Try} instance is a {@code
	 * Failure}; otherwise returns {@code false}.
	 *
	 * @return {@code true} if the current {@code Try} instance is a {@code
	 *         Failure}; {@code false} otherwise.
	 */
	public abstract boolean isFailure();

	/**
	 * Returns {@code true} if the current {@code Try} instance is a {@code
	 * Success}; otherwise returns {@code false}.
	 *
	 * @return {@code true} if the current {@code Try} instance is a {@code
	 *         Success}; {@code false} otherwise.
	 */
	public abstract boolean isSuccess();

	/**
	 * Returns the result of applying the mapping function to the {@code
	 * Success} instance'S value, if the current {@code Try} instance is a
	 * {@code Success}; otherwise returns the {@code Failure} instance.
	 *
	 * <p>
	 * This function is similar to {@link #flatMap(ThrowableFunction)}, but the
	 * mapping function'S result isn't a {@code Try}, and if invoked, {@code
	 * map} wraps it in {@code Try}.
	 * </p>
	 *
	 * @param  throwableFunction the mapping function
	 * @return the result of the mapping function if applied to the {@code
	 *         Success} instance'S value; the {@code Failure} instance otherwise
	 */
	public abstract <S> Try<S> map(
		ThrowableFunction<? super T, ? extends S> throwableFunction);

	/**
	 * Returns a {@code Try} instance that contains the result of applying the
	 * mapping function to the current {@code Try} instance'S exception, if that
	 * instance is a {@code Failure} object. If the current {@code Try} instance
	 * is a {@code Success} object, this method returns it unmodified.
	 *
	 * @param  function the mapping function to apply to the {@code Failure}
	 *         instance'S exception
	 * @return the {@code Try} with the mapping function'S result; the {@code
	 *         Success} object otherwise
	 */
	public abstract <S extends Exception> Try<T> mapFail(
		Function<Exception, S> function);

	/**
	 * Returns a {@code Try} instance that contains the exception provided by
	 * the {@code Supplier}, if the current {@code Try} instance is a {@code
	 * Failure} object whose exception class matches that of the {@code
	 * exceptionClass} parameter. If the current {@code Try} instance is a
	 * {@code Success} object, this method returns it unmodified.
	 *
	 * @param  supplier the supplier
	 * @return the {@code Try} with the exception from the supplier; the {@code
	 *         Success} object otherwise
	 */
	public abstract <S extends Exception, U extends Exception> Try<T>
		mapFailMatching(Class<U> exceptionClass, Supplier<S> supplier);

	/**
	 * Returns the result of applying the mapping function to the {@code
	 * Success} instance's value and unwrapping the resulting {@code Optional},
	 * if the current {@code Try} instance is a {@code Success} and the {@code
	 * Optional} isn't {@code Optional#empty()}; otherwise returns the {@code
	 * Failure}.
	 *
	 * @param  throwableFunction the mapping function
	 * @return the mapping function's result if the {@code Success} instance's
	 *         value doesn't return {@code Optional#empty()}; the {@code
	 *         Failure} instance otherwise
	 */
	public <S> Try<S> mapOptional(
		ThrowableFunction<? super T, ? extends Optional<S>> throwableFunction) {

		Try<T> tTry = this;

		return tTry.map(
			throwableFunction
		).map(
			Optional::get
		);
	}

	/**
	 * Returns the result of applying the mapping function to the {@code
	 * Success} instance's value and unwrapping the resulting {@code Optional},
	 * if the current {@code Try} instance is a {@code Success} and the {@code
	 * Optional} isn't {@code Optional#empty()}; otherwise returns the {@code
	 * Failure} instance with the provided exception.
	 *
	 * @param  throwableFunction the mapping function
	 * @param  supplier the exception's supplier in case the obtained {@code
	 *         Optional} is {@code Optional#empty()}
	 * @return the mapping function's result if the {@code Success} instance's
	 *         value doesn't return {@code Optional#empty()}; the {@code
	 *         Failure} instance otherwise
	 */
	public <S> Try<S> mapOptional(
		ThrowableFunction<? super T, ? extends Optional<S>> throwableFunction,
		Supplier<? extends Exception> supplier) {

		Try<T> tTry = this;

		return tTry.map(
			throwableFunction
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class, supplier
		);
	}

	/**
	 * Returns the {@code Success} instance'S value, if the current {@code Try}
	 * instance is a {@code Success} object. If the current {@code Try} instance
	 * is a {@code Failure} object, this method returns the {@code other}
	 * parameter.
	 *
	 * @param  other the value to return if current {@code Try} instance is a
	 *         {@code Failure} object
	 * @return the {@code Success} instance'S value, or {@code other}
	 */
	public abstract T orElse(T other);

	/**
	 * Returns the {@code Success} instance'S value, if the current {@code Try}
	 * instance is a {@code Success} object. If the current {@code Try} instance
	 * is a {@code Failure} object, this method returns the result of invoking
	 * {@code Supplier#get()}.
	 *
	 * @param  supplier the supplier
	 * @return the {@code Success} instance'S value, or the result of the
	 *         supplier'S {@code get} method
	 */
	public abstract T orElseGet(Supplier<? extends T> supplier);

	/**
	 * Returns the {@code Success} instance'S value, if the current {@code Try}
	 * instance is a {@code Success} object. If the current {@code Try} instance
	 * is a {@code Failure} object, this method throws the exception that
	 * results from invoking {@code Supplier#get()}.
	 *
	 * @param  supplier the supplier
	 * @return the {@code Success} instance'S value
	 * @throws S if the current {@code Try} instance was a {@code Failure}
	 *         object
	 */
	public abstract <S extends Throwable> T orElseThrow(
			Supplier<? extends S> supplier)
		throws S;

	/**
	 * Returns the result of applying the function to the {@code Failure}
	 * object'S exception, if the current {@code Try} instance is a {@code
	 * Failure} object. If the current {@code Try} instance is a {@code Success}
	 * object, this method returns that object'S value.
	 *
	 * @param  function the function
	 * @return the function'S result when applied to the {@code Failure}; the
	 *         {@code Success} object'S value otherwise
	 */
	public abstract T recover(Function<? super Exception, T> function);

	/**
	 * Returns a new {@code Try} instance extracted from the provided function,
	 * if the current {@code Try} instance is a {@code Failure} object. If the
	 * current {@code Try} instance is a {@code Success} object, this method
	 * returns that object.
	 *
	 * @param  throwableFunction the function
	 * @return the new {@code Try} instance, if the current {@code Try} instance
	 *         is a {@code Failure} object; the current {@code Success} object
	 *         otherwise
	 */
	public abstract Try<T> recoverWith(
		ThrowableFunction<? super Exception, Try<T>> throwableFunction);

	/**
	 * Returns the current {@code Try} instance as an {@code Optional}
	 * containing the value if it's a {@code Success} or {@code
	 * Optional#empty()} if it's a {@code Failure}.
	 *
	 * @return an {@code Optional} containing the value if it's a {@code
	 *         Success}; {@code Optional#empty()} otherwise
	 */
	public abstract Optional<T> toOptional();

	/**
	 * Applies {@code failureConsumer} if this is a {@code Failure}, or {@code
	 * successConsumer} if this is a {@code Success}.
	 *
	 * <p>
	 * If {@code successConsumer} throws an {@code Exception}, this method
	 * returns the result of applying {@code failureConsumer} to the new {@code
	 * Exception}.
	 * </p>
	 *
	 * @param  failureConsumer the consumer to apply when this {@code Try} is a
	 *         {@code Failure}
	 * @param  successConsumer the consumer to apply when this {@code Try} is a
	 *         {@code Success}
	 * @review
	 */
	public abstract void voidFold(
		Consumer<Exception> failureConsumer,
		ThrowableConsumer<T> successConsumer);

	/**
	 * The implementation of {@code Try}'S failure case. Don't try to
	 * instantiate this class directly. To instantiate this class when you don't
	 * know if the operation will fail, use {@link
	 * #fromFallible(ThrowableSupplier)}. To instantiate this class from an
	 * exception, use {@link #fail(Exception)}.
	 */
	public static class Failure<T> extends Try<T> {

		@Override
		public Try<T> filter(Predicate<T> predicate) {
			return this;
		}

		@Override
		public <S> Try<S> flatMap(
			ThrowableFunction<? super T, Try<S>> throwableFunction) {

			Objects.requireNonNull(throwableFunction);

			return Try.fail(_exception);
		}

		@Override
		public <S> S fold(
			Function<Exception, S> failureFunction,
			ThrowableFunction<T, S> successFunction) {

			Objects.requireNonNull(failureFunction);

			return failureFunction.apply(_exception);
		}

		@Override
		public T get() throws Exception {
			throw _exception;
		}

		/**
		 * Returns the current {@code Failure} instance'S exception.
		 *
		 * @return the exception
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
		public void ifFailure(Consumer<Exception> consumer) {
			Objects.requireNonNull(consumer);

			consumer.accept(_exception);
		}

		@Override
		public void ifSuccess(Consumer<T> consumer) {
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
		public <S> Try<S> map(
			ThrowableFunction<? super T, ? extends S> throwableFunction) {

			Objects.requireNonNull(throwableFunction);

			return Try.fail(_exception);
		}

		@Override
		public <S extends Exception> Try<T> mapFail(
			Function<Exception, S> function) {

			Objects.requireNonNull(function);

			return Try.fail(function.apply(_exception));
		}

		@Override
		public <S extends Exception, U extends Exception> Try<T>
			mapFailMatching(Class<U> exceptionClass, Supplier<S> supplier) {

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
		public <S extends Throwable> T orElseThrow(
				Supplier<? extends S> supplier)
			throws S {

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

		@Override
		public Optional<T> toOptional() {
			return Optional.empty();
		}

		@Override
		public void voidFold(
			Consumer<Exception> failureConsumer,
			ThrowableConsumer<T> successConsumer) {

			Objects.requireNonNull(failureConsumer);

			failureConsumer.accept(_exception);
		}

		private Failure(Exception exception) {
			_exception = exception;
		}

		private final Exception _exception;

	}

	/**
	 * The implementation of {@code Try}'S success case. Don't try to
	 * instantiate this class directly. To instantiate this class when you don't
	 * know if the operation will fail, use {@link
	 * #fromFallible(ThrowableSupplier)}. To instantiate this class from a value
	 * of type {@code T}, use {@link #success(Object)}.
	 */
	public static class Success<T> extends Try<T> {

		@Override
		public Try<T> filter(Predicate<T> predicate) {
			Objects.requireNonNull(predicate);

			try {
				if (predicate.test(_value)) {
					return this;
				}
				else {
					return Try.fail(new FalsePredicateException(_value));
				}
			}
			catch (Exception e) {
				return Try.fail(new FalsePredicateException(_value));
			}
		}

		@Override
		public <S> Try<S> flatMap(
			ThrowableFunction<? super T, Try<S>> function) {

			Objects.requireNonNull(function);

			try {
				return function.apply(_value);
			}
			catch (Exception e) {
				return Try.fail(e);
			}
		}

		@Override
		public <S> S fold(
			Function<Exception, S> failureFunction,
			ThrowableFunction<T, S> successFunction) {

			Objects.requireNonNull(successFunction);
			Objects.requireNonNull(failureFunction);

			try {
				return successFunction.apply(_value);
			}
			catch (Exception e) {
				return failureFunction.apply(e);
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
		 * Returns the current {@code Success} instance'S value.
		 *
		 * @return the current {@code Success} instance'S value
		 */
		public T getValue() {
			return _value;
		}

		@Override
		public void ifFailure(Consumer<Exception> consumer) {
		}

		@Override
		public void ifSuccess(Consumer<T> consumer) {
			Objects.requireNonNull(consumer);

			consumer.accept(_value);
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
		public <S> Try<S> map(
			ThrowableFunction<? super T, ? extends S> throwableFunction) {

			Objects.requireNonNull(throwableFunction);

			try {
				return Try.success(throwableFunction.apply(_value));
			}
			catch (Exception e) {
				return Try.fail(e);
			}
		}

		@Override
		public <S extends Exception> Try<T> mapFail(
			Function<Exception, S> function) {

			Objects.requireNonNull(function);

			return this;
		}

		@Override
		public <S extends Exception, U extends Exception> Try<T>
			mapFailMatching(Class<U> exceptionClass, Supplier<S> supplier) {

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
		public <S extends Throwable> T orElseThrow(
				Supplier<? extends S> supplier)
			throws S {

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

		@Override
		public Optional<T> toOptional() {
			return Optional.ofNullable(_value);
		}

		@Override
		public void voidFold(
			Consumer<Exception> failureConsumer,
			ThrowableConsumer<T> successConsumer) {

			Objects.requireNonNull(successConsumer);
			Objects.requireNonNull(failureConsumer);

			try {
				successConsumer.accept(_value);
			}
			catch (Exception e) {
				failureConsumer.accept(e);
			}
		}

		private Success(T value) {
			_value = value;
		}

		private final T _value;

	}

	private Try() {
	}

}