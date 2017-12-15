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

import com.liferay.apio.architect.alias.RequestFunction;
import com.liferay.apio.architect.alias.routes.NestedCreateItemFunction;
import com.liferay.apio.architect.alias.routes.NestedGetPageFunction;
import com.liferay.apio.architect.error.ApioDeveloperError.MustHaveProvider;
import com.liferay.apio.architect.error.ApioDeveloperError.MustUseSameIdentifier;
import com.liferay.apio.architect.function.HexaFunction;
import com.liferay.apio.architect.function.PentaFunction;
import com.liferay.apio.architect.function.TetraFunction;
import com.liferay.apio.architect.function.TriFunction;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

/**
 * Holds information about the routes supported for a {@link
 * com.liferay.apio.architect.router.NestedCollectionRouter}.
 *
 * <p>
 * This interface's methods return functions to get the different endpoints of
 * the collection resource. You should always use a {@link
 * NestedCollectionRoutes.Builder} to create instances of this interface.
 * </p>
 *
 * @author Alejandro Hernández
 * @see    NestedCollectionRoutes.Builder
 */
public class NestedCollectionRoutes<T> {

	public NestedCollectionRoutes(Builder<T, ? extends Identifier> builder) {
		_nestedCreateItemFunction = builder._nestedCreateItemFunction;
		_nestedGetPageFunction = builder._nestedGetPageFunction;
	}

	/**
	 * Returns the function that is used to create a collection item, if the
	 * endpoint was added through the {@link Builder} and the function therefore
	 * exists. Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to create a collection item, if the function
	 *         exists; {@code Optional#empty()} otherwise
	 */
	public Optional<NestedCreateItemFunction<T>>
		getNestedCreateItemFunctionOptional() {

		return Optional.ofNullable(_nestedCreateItemFunction);
	}

	/**
	 * Returns the function used to obtain the page, if the endpoint was added
	 * through the {@link Builder} and the function therefore exists. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to obtain the page, if the function exists;
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<NestedGetPageFunction<T>>
		getNestedGetPageFunctionOptional() {

		return Optional.ofNullable(_nestedGetPageFunction);
	}

	/**
	 * Creates the {@link NestedCollectionRoutes} of a {@link
	 * com.liferay.apio.architect.router.NestedCollectionRouter}.
	 */
	@SuppressWarnings("unused")
	public static class Builder<T, U extends Identifier> {

		public Builder(
			Class<T> modelClass, Class<U> identifierClass,
			RequestFunction<Function<Class<?>, Optional<?>>>
				provideClassFunction) {

			_modelClass = modelClass;
			_identifierClass = identifierClass;
			_provideClassFunction = provideClassFunction;
		}

		/**
		 * Adds a route to a creator function with none extra parameters.
		 *
		 * @param  biFunction the creator function that adds the collection item
		 * @return the updated builder
		 */
		public <V extends Identifier> Builder<T, U> addCreator(
			BiFunction<V, Map<String, Object>, T> biFunction) {

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> {
					V v = _getIdentifier(identifier);

					return biFunction.andThen(
						t -> new SingleModel<>(t, _modelClass)
					).apply(
						v, body
					);
				};

			return this;
		}

		/**
		 * Adds a route to a creator function with four extra parameters.
		 *
		 * @param  hexaFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the collection item creator function's
		 *         third parameter
		 * @param  bClass the class of the collection item creator function's
		 *         fourth parameter
		 * @param  cClass the class of the collection item creator function's
		 *         fifth parameter
		 * @param  dClass the class of the collection item creator function's
		 *         sixth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, V extends Identifier> Builder<T, U> addCreator(
			HexaFunction<V, Map<String, Object>, A, B, C, D, T> hexaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> {
					A a = _provideClass(httpServletRequest, aClass);
					B b = _provideClass(httpServletRequest, bClass);
					C c = _provideClass(httpServletRequest, cClass);
					D d = _provideClass(httpServletRequest, dClass);

					V v = _getIdentifier(identifier);

					return hexaFunction.andThen(
						t -> new SingleModel<>(t, _modelClass)
					).apply(
						v, body, a, b, c, d
					);
				};

			return this;
		}

		/**
		 * Adds a route to a creator function with three extra parameters.
		 *
		 * @param  pentaFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the collection item creator function's
		 *         third parameter
		 * @param  bClass the class of the collection item creator function's
		 *         fourth parameter
		 * @param  cClass the class of the collection item creator function's
		 *         fifth parameter
		 * @return the updated builder
		 */
		public <A, B, C, V extends Identifier> Builder<T, U> addCreator(
			PentaFunction<V, Map<String, Object>, A, B, C, T> pentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> {
					A a = _provideClass(httpServletRequest, aClass);
					B b = _provideClass(httpServletRequest, bClass);
					C c = _provideClass(httpServletRequest, cClass);

					V v = _getIdentifier(identifier);

					return pentaFunction.andThen(
						t -> new SingleModel<>(t, _modelClass)
					).apply(
						v, body, a, b, c
					);
				};

			return this;
		}

		/**
		 * Adds a route to a creator function with two extra parameters.
		 *
		 * @param  tetraFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the collection item creator function's
		 *         third parameter
		 * @param  bClass the class of the collection item creator function's
		 *         fourth parameter
		 * @return the updated builder
		 */
		public <A, B, V extends Identifier> Builder<T, U> addCreator(
			TetraFunction<V, Map<String, Object>, A, B, T> tetraFunction,
			Class<A> aClass, Class<B> bClass) {

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> {
					A a = _provideClass(httpServletRequest, aClass);
					B b = _provideClass(httpServletRequest, bClass);

					V v = _getIdentifier(identifier);

					return tetraFunction.andThen(
						t -> new SingleModel<>(t, _modelClass)
					).apply(
						v, body, a, b
					);
				};

			return this;
		}

		/**
		 * Adds a route to a creator function with one extra parameter.
		 *
		 * @param  triFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the collection item creator function's
		 *         third parameter
		 * @return the updated builder
		 */
		public <A, V extends Identifier> Builder<T, U> addCreator(
			TriFunction<V, Map<String, Object>, A, T> triFunction,
			Class<A> aClass) {

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> {
					A a = _provideClass(httpServletRequest, aClass);

					V v = _getIdentifier(identifier);

					return triFunction.andThen(
						t -> new SingleModel<>(t, _modelClass)
					).apply(
						v, body, a
					);
				};

			return this;
		}

		/**
		 * Adds a route to a collection page function with none extra
		 * parameters.
		 *
		 * @param  biFunction the function that calculates the page
		 * @return the updated builder
		 */
		public <V extends Identifier> Builder<T, U> addGetter(
			BiFunction<Pagination, V, PageItems<T>> biFunction) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> {
					Pagination pagination = _provideClass(
						httpServletRequest, Pagination.class);

					V v = _getIdentifier(identifier);

					return biFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, v
					);
				};

			return this;
		}

		/**
		 * Adds a route to a collection page function with four extra
		 * parameters.
		 *
		 * @param  hexaFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @param  bClass the class of the page function's third parameter
		 * @param  cClass the class of the page function's fourth parameter
		 * @param  dClass the class of the page function's fifth parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B, C, D> Builder<T, U> addGetter(
			HexaFunction<Pagination, V, A, B, C, D, PageItems<T>> hexaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> {
					Pagination pagination = _provideClass(
						httpServletRequest, Pagination.class);
					A a = _provideClass(httpServletRequest, aClass);
					B b = _provideClass(httpServletRequest, bClass);
					C c = _provideClass(httpServletRequest, cClass);
					D d = _provideClass(httpServletRequest, dClass);

					V v = _getIdentifier(identifier);

					return hexaFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, v, a, b, c, d
					);
				};

			return this;
		}

		/**
		 * Adds a route to a collection page function with three extra
		 * parameters.
		 *
		 * @param  pentaFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @param  bClass the class of the page function's third parameter
		 * @param  cClass the class of the page function's fourth parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B, C> Builder<T, U> addGetter(
			PentaFunction<Pagination, V, A, B, C, PageItems<T>> pentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> {
					Pagination pagination = _provideClass(
						httpServletRequest, Pagination.class);
					A a = _provideClass(httpServletRequest, aClass);
					B b = _provideClass(httpServletRequest, bClass);
					C c = _provideClass(httpServletRequest, cClass);

					V v = _getIdentifier(identifier);

					return pentaFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, v, a, b, c
					);
				};

			return this;
		}

		/**
		 * Adds a route to a collection page function with two extra parameters.
		 *
		 * @param  tetraFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @param  bClass the class of the page function's third parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B> Builder<T, U> addGetter(
			TetraFunction<Pagination, V, A, B, PageItems<T>> tetraFunction,
			Class<A> aClass, Class<B> bClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> {
					Pagination pagination = _provideClass(
						httpServletRequest, Pagination.class);
					A a = _provideClass(httpServletRequest, aClass);
					B b = _provideClass(httpServletRequest, bClass);

					V v = _getIdentifier(identifier);

					return tetraFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, v, a, b
					);
				};

			return this;
		}

		/**
		 * Adds a route to a collection page function with one extra parameter.
		 *
		 * @param  triFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A> Builder<T, U> addGetter(
			TriFunction<Pagination, V, A, PageItems<T>> triFunction,
			Class<A> aClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> {
					Pagination pagination = _provideClass(
						httpServletRequest, Pagination.class);
					A a = _provideClass(httpServletRequest, aClass);

					V v = _getIdentifier(identifier);

					return triFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, v, a
					);
				};

			return this;
		}

		/**
		 * Constructs the {@link NestedCollectionRoutes} instance with the
		 * information provided to the builder.
		 *
		 * @return the {@code Routes} instance
		 */
		public NestedCollectionRoutes<T> build() {
			return new NestedCollectionRoutes<>(this);
		}

		@SuppressWarnings("unchecked")
		private <V> V _getIdentifier(Identifier identifier) {
			Class<? extends Identifier> clazz = identifier.getClass();

			if (!_identifierClass.isAssignableFrom(clazz)) {
				throw new MustUseSameIdentifier(clazz, _identifierClass);
			}

			return (V)identifier;
		}

		@SuppressWarnings("unchecked")
		private <V> V _provideClass(
			HttpServletRequest httpServletRequest, Class<V> clazz) {

			Optional<?> optional = _provideClassFunction.apply(
				httpServletRequest
			).apply(
				clazz
			);

			return optional.map(
				provided -> (V)provided
			).orElseThrow(
				() -> new MustHaveProvider(clazz)
			);
		}

		private final Class<U> _identifierClass;
		private final Class<T> _modelClass;
		private NestedCreateItemFunction<T> _nestedCreateItemFunction;
		private NestedGetPageFunction<T> _nestedGetPageFunction;
		private final RequestFunction<Function<Class<?>, Optional<?>>>
			_provideClassFunction;

	}

	private NestedCreateItemFunction<T> _nestedCreateItemFunction;
	private NestedGetPageFunction<T> _nestedGetPageFunction;

}