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

import com.liferay.apio.architect.error.ApioDeveloperError.MustHaveProvider;
import com.liferay.apio.architect.unsafe.Unsafe;

import java.util.Optional;
import java.util.function.Consumer;
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
	public static <A, B, C, D, E, R> R provide(
		Function<Class<?>, Optional<?>> provideFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Function<A, Function<B, Function<C, Function<D, Function<E, R>>>>>
			function) {

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
	public static <A, B, C, D, R> R provide(
		Function<Class<?>, Optional<?>> provideFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Function<A, Function<B, Function<C, Function<D, R>>>> function) {

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
	public static <A, B, C, R> R provide(
		Function<Class<?>, Optional<?>> provideFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass,
		Function<A, Function<B, Function<C, R>>> function) {

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
	public static <A, B, R> R provide(
		Function<Class<?>, Optional<?>> provideFunction, Class<A> aClass,
		Class<B> bClass, Function<A, Function<B, R>> function) {

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
	public static <A, R> R provide(
		Function<Class<?>, Optional<?>> provideFunction, Class<A> aClass,
		Function<A, R> function) {

		return function.apply(_provideClass(provideFunction, aClass));
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
		Function<Class<?>, Optional<?>> provideFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Function<A, Function<B, Function<C, Consumer<D>>>> function) {

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
		Function<Class<?>, Optional<?>> provideFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass,
		Function<A, Function<B, Consumer<C>>> function) {

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
		Function<Class<?>, Optional<?>> provideFunction, Class<A> aClass,
		Class<B> bClass, Function<A, Consumer<B>> function) {

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
		Function<Class<?>, Optional<?>> provideFunction, Class<A> aClass,
		Consumer<A> consumer) {

		consumer.accept(_provideClass(provideFunction, aClass));
	}

	private static <T> T _provideClass(
		Function<Class<?>, Optional<?>> provideFunction, Class<T> clazz) {

		Optional<T> optional = provideFunction.apply(
			clazz
		).map(
			Unsafe::unsafeCast
		);

		return optional.orElseThrow(() -> new MustHaveProvider(clazz));
	}

	private RoutesBuilderUtil() {
		throw new UnsupportedOperationException();
	}

}