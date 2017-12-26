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

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.error.ApioDeveloperError;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

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
	 * Returns the result of applying the instances of the requested classes
	 * from the HTTP request to the provided function.
	 *
	 * @param  httpServletRequest the HTTP request
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  cClass the third class to provide
	 * @param  dClass the fourth class to provide
	 * @param  eClass the fifth class to provide
	 * @param  function a function that receives an instance of the classes and
	 *         return a value
	 * @return the result of applying requested classes' instances to the
	 *         provided function
	 * @review
	 */
	public static <A, B, C, D, E, R> R provide(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass,
		Function<A, Function<B, Function<C, Function<D, Function<E, R>>>>>
			function) {

		return provide(
			provideFunction, httpServletRequest, aClass, bClass, cClass, dClass,
			a -> b -> c -> d -> function.apply(
				a
			).apply(
				b
			).apply(
				c
			).apply(
				d
			).apply(
				_provideClass(provideFunction, httpServletRequest, eClass)
			));
	}

	/**
	 * Returns the result of applying the instances of the requested classes
	 * from the HTTP request to the provided function.
	 *
	 * @param  httpServletRequest the HTTP request
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  cClass the third class to provide
	 * @param  dClass the fourth class to provide
	 * @param  function a function that receives an instance of the classes and
	 *         return a value
	 * @return the result of applying requested classes' instances to the
	 *         provided function
	 * @review
	 */
	public static <A, B, C, D, R> R provide(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Function<A, Function<B, Function<C, Function<D, R>>>> function) {

		return provide(
			provideFunction, httpServletRequest, aClass, bClass, cClass,
			a -> b -> c -> function.apply(
				a
			).apply(
				b
			).apply(
				c
			).apply(
				_provideClass(provideFunction, httpServletRequest, dClass)
			));
	}

	/**
	 * Returns the result of applying the instances of the requested classes
	 * from the HTTP request to the provided function.
	 *
	 * @param  httpServletRequest the HTTP request
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  cClass the third class to provide
	 * @param  function a function that receives an instance of the classes and
	 *         return a value
	 * @return the result of applying requested classes' instances to the
	 *         provided function
	 * @review
	 */
	public static <A, B, C, R> R provide(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Function<A, Function<B, Function<C, R>>> function) {

		return provide(
			provideFunction, httpServletRequest, aClass, bClass,
			a -> b -> function.apply(
				a
			).apply(
				b
			).apply(
				_provideClass(provideFunction, httpServletRequest, cClass)
			));
	}

	/**
	 * Returns the result of applying the instances of the requested classes
	 * from the HTTP request to the provided function.
	 *
	 * @param  httpServletRequest the HTTP request
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  function a function that receives an instance of the classes and
	 *         return a value
	 * @return the result of applying requested classes' instances to the
	 *         provided function
	 * @review
	 */
	public static <A, B, R> R provide(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<A> aClass, Class<B> bClass,
		Function<A, Function<B, R>> function) {

		return provide(
			provideFunction, httpServletRequest, aClass,
			a -> function.apply(
				a
			).apply(
				_provideClass(provideFunction, httpServletRequest, bClass)
			));
	}

	/**
	 * Returns the result of applying the instances of the requested classes
	 * from the HTTP request to the provided function.
	 *
	 * @param  httpServletRequest the HTTP request
	 * @param  aClass the first class to provide
	 * @param  function a function that receives an instance of the classes and
	 *         return a value
	 * @return the result of applying requested classes' instances to the
	 *         provided function
	 * @review
	 */
	public static <A, R> R provide(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<A> aClass, Function<A, R> function) {

		return function.apply(
			_provideClass(provideFunction, httpServletRequest, aClass));
	}

	/**
	 * Applies the instances of the requested classes from the HTTP request to
	 * the provided function.
	 *
	 * @param  httpServletRequest the HTTP request
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  cClass the third class to provide
	 * @param  dClass the fourth class to provide
	 * @param  function a function that receives an instance of the classes
	 * @review
	 */
	public static <A, B, C, D> void provideConsumer(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Function<A, Function<B, Function<C, Consumer<D>>>> function) {

		provideConsumer(
			provideFunction, httpServletRequest, aClass, bClass, cClass,
			a -> b -> c -> function.apply(
				a
			).apply(
				b
			).apply(
				c
			).accept(
				_provideClass(provideFunction, httpServletRequest, dClass)
			));
	}

	/**
	 * Applies the instances of the requested classes from the HTTP request to
	 * the provided function.
	 *
	 * @param  httpServletRequest the HTTP request
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  cClass the third class to provide
	 * @param  function a function that receives an instance of the classes
	 * @review
	 */
	public static <A, B, C> void provideConsumer(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Function<A, Function<B, Consumer<C>>> function) {

		provideConsumer(
			provideFunction, httpServletRequest, aClass, bClass,
			a -> b -> function.apply(
				a
			).apply(
				b
			).accept(
				_provideClass(provideFunction, httpServletRequest, cClass)
			));
	}

	/**
	 * Applies the instances of the requested classes from the HTTP request to
	 * the provided function.
	 *
	 * @param  httpServletRequest the HTTP request
	 * @param  aClass the first class to provide
	 * @param  bClass the second class to provide
	 * @param  function a function that receives an instance of the classes
	 * @review
	 */
	public static <A, B> void provideConsumer(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<A> aClass, Class<B> bClass, Function<A, Consumer<B>> function) {

		provideConsumer(
			provideFunction, httpServletRequest, aClass,
			a -> function.apply(
				a
			).accept(
				_provideClass(provideFunction, httpServletRequest, bClass)
			));
	}

	/**
	 * Applies the instances of the requested classes from the HTTP request to
	 * the provided consumer.
	 *
	 * @param  httpServletRequest the HTTP request
	 * @param  aClass the first class to provide
	 * @param  consumer a consumer that receive an instance of the classes
	 * @review
	 */
	public static <A> void provideConsumer(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<A> aClass, Consumer<A> consumer) {

		consumer.accept(
			_provideClass(provideFunction, httpServletRequest, aClass));
	}

	@SuppressWarnings("unchecked")
	private static <V> V _provideClass(
		ProvideFunction provideFunction, HttpServletRequest httpServletRequest,
		Class<V> clazz) {

		Optional<?> optional = provideFunction.apply(
			httpServletRequest
		).apply(
			clazz
		);

		return optional.map(
			provided -> (V)provided
		).orElseThrow(
			() -> new ApioDeveloperError.MustHaveProvider(clazz)
		);
	}

	private RoutesBuilderUtil() {
		throw new UnsupportedOperationException();
	}

}