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

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;

import com.liferay.apio.architect.consumer.throwable.ThrowableConsumer;
import com.liferay.apio.architect.function.throwable.ThrowableFunction;
import com.liferay.apio.architect.functional.Try;

import java.util.function.Function;

/**
 * Provides utility functions for routes builders.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class RoutesBuilderUtil {

	/**
	 * Returns the result of applying instances of the six classes requested
	 * from the HTTP request to the {@code function}.
	 *
	 * @param  provideFunction the function used to provide the class instances
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  cClass the third class to provide
	 * @param  dClass the fourth class to provide
	 * @param  eClass the fifth class to provide
	 * @param  fClass the sixth class to provide
	 * @param  function the function that receives the class instances
	 * @return the result of applying the class instances to the {@code
	 *         function}
	 */
	public static <A, B, C, D, E, F, R> Try<R> provide(
		Function<Class<?>, ?> provideFunction, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
		Function<A, Function<B, Function<C, Function<D, Function<E,
			ThrowableFunction<F, R>>>>>> function) {

		return provide(
			provideFunction, aClass, bClass, cClass, dClass, eClass,
			a -> b -> c -> d -> e -> function.apply(
				a
			).apply(
				b
			).apply(
				c
			).apply(
				d
			).apply(
				e
			).apply(
				_provideClass(provideFunction, fClass)
			));
	}

	/**
	 * Returns the result of applying instances of the five classes requested
	 * from the HTTP request to the {@code function}.
	 *
	 * @param  provideFunction the function used to provide the class instances
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  cClass the third class to provide
	 * @param  dClass the fourth class to provide
	 * @param  eClass the fifth class to provide
	 * @param  function the function that receives the class instances
	 * @return the result of applying the class instances to the {@code
	 *         function}
	 */
	public static <A, B, C, D, E, R> Try<R> provide(
		Function<Class<?>, ?> provideFunction, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Function<A, Function<B, Function<C, Function<D,
			ThrowableFunction<E, R>>>>> function) {

		return provide(
			provideFunction, aClass, bClass, cClass, dClass,
			a -> b -> c -> d -> function.apply(
				a
			).apply(
				b
			).apply(
				c
			).apply(
				d
			).apply(
				_provideClass(provideFunction, eClass)
			));
	}

	/**
	 * Returns the result of applying instances of the four classes requested
	 * from the HTTP request to the {@code function}.
	 *
	 * @param  provideFunction the function used to provide the class instances
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  cClass the third class to provide
	 * @param  dClass the fourth class to provide
	 * @param  function the function that receives the class instances
	 * @return the result of applying the class instances to the {@code
	 *         function}
	 */
	public static <A, B, C, D, R> Try<R> provide(
		Function<Class<?>, ?> provideFunction, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass,
		Function<A, Function<B, Function<C, ThrowableFunction<D, R>>>>
			function) {

		return provide(
			provideFunction, aClass, bClass, cClass,
			a -> b -> c -> function.apply(
				a
			).apply(
				b
			).apply(
				c
			).apply(
				_provideClass(provideFunction, dClass)
			));
	}

	/**
	 * Returns the result of applying instances of the three classes requested
	 * from the HTTP request to the {@code function}.
	 *
	 * @param  provideFunction the function used to provide the class instances
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  cClass the third class to provide
	 * @param  function the function that receives the class instances
	 * @return the result of applying the class instances to the {@code
	 *         function}
	 */
	public static <A, B, C, R> Try<R> provide(
		Function<Class<?>, ?> provideFunction, Class<A> aClass, Class<B> bClass,
		Class<C> cClass,
		Function<A, Function<B, ThrowableFunction<C, R>>> function) {

		return provide(
			provideFunction, aClass, bClass,
			a -> b -> function.apply(
				a
			).apply(
				b
			).apply(
				_provideClass(provideFunction, cClass)
			));
	}

	/**
	 * Returns the result of applying instances of the two classes requested
	 * from the HTTP request to the {@code function}.
	 *
	 * @param  provideFunction the function used to provide the class instances
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  function the function that receives the class instances
	 * @return the result of applying the class instances to the {@code
	 *         function}
	 */
	public static <A, B, R> Try<R> provide(
		Function<Class<?>, ?> provideFunction, Class<A> aClass, Class<B> bClass,
		Function<A, ThrowableFunction<B, R>> function) {

		return provide(
			provideFunction, aClass,
			a -> function.apply(
				a
			).apply(
				_provideClass(provideFunction, bClass)
			));
	}

	/**
	 * Returns the result of applying an instance of the class requested from
	 * the HTTP request to the {@code function}.
	 *
	 * @param  provideFunction the function used to provide the class instance
	 * @param  aClass the class to provide
	 * @param  function the function that receives the class instance
	 * @return the result of applying the class instance to the {@code function}
	 */
	public static <A, R> Try<R> provide(
		Function<Class<?>, ?> provideFunction, Class<A> aClass,
		ThrowableFunction<A, R> function) {

		return Try.fromFallible(
			() -> function.apply(_provideClass(provideFunction, aClass)));
	}

	/**
	 * Applies the instances of the four classes requested from the HTTP request
	 * to the {@code function}.
	 *
	 * @param provideFunction the function used to provide the class instances
	 * @param aClass the first class to provide
	 * @param bClass the second class to provide
	 * @param cClass the third class to provide
	 * @param dClass the fourth class to provide
	 * @param function the function that receives the class instances
	 */
	public static <A, B, C, D> void provideConsumer(
			Function<Class<?>, ?> provideFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Function<A, Function<B, Function<C, ThrowableConsumer<D>>>>
				function)
		throws Exception {

		provideConsumer(
			provideFunction, aClass, bClass, cClass,
			a -> b -> c -> function.apply(
				a
			).apply(
				b
			).apply(
				c
			).accept(
				_provideClass(provideFunction, dClass)
			));
	}

	/**
	 * Applies the instances of the three classes requested from the HTTP
	 * request to the {@code function}.
	 *
	 * @param provideFunction the function used to provide the class instances
	 * @param aClass the first class to provide
	 * @param bClass the second class to provide
	 * @param cClass the third class to provide
	 * @param function the function that receives the class instances
	 */
	public static <A, B, C> void provideConsumer(
			Function<Class<?>, ?> provideFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass,
			Function<A, Function<B, ThrowableConsumer<C>>> function)
		throws Exception {

		provideConsumer(
			provideFunction, aClass, bClass,
			a -> b -> function.apply(
				a
			).apply(
				b
			).accept(
				_provideClass(provideFunction, cClass)
			));
	}

	/**
	 * Applies the instances of the two classes requested from the HTTP request
	 * to the {@code function}.
	 *
	 * @param provideFunction the function used to provide the class instances
	 * @param aClass the first class to provide
	 * @param bClass the second class to provide
	 * @param function the function that receives the class instances
	 */
	public static <A, B> void provideConsumer(
			Function<Class<?>, ?> provideFunction, Class<A> aClass,
			Class<B> bClass, Function<A, ThrowableConsumer<B>> function)
		throws Exception {

		provideConsumer(
			provideFunction, aClass,
			a -> function.apply(
				a
			).accept(
				_provideClass(provideFunction, bClass)
			));
	}

	/**
	 * Applies an instance of the class requested from the HTTP request to the
	 * consumer.
	 *
	 * @param provideFunction the function used to provide the class instance
	 * @param aClass the class to provide
	 * @param consumer the consumer
	 */
	public static <A> void provideConsumer(
			Function<Class<?>, ?> provideFunction, Class<A> aClass,
			ThrowableConsumer<A> consumer)
		throws Exception {

		consumer.accept(_provideClass(provideFunction, aClass));
	}

	private static <T> T _provideClass(
		Function<Class<?>, ?> provideFunction, Class<T> clazz) {

		return unsafeCast(provideFunction.apply(clazz));
	}

	private RoutesBuilderUtil() {
		throw new UnsupportedOperationException();
	}

}