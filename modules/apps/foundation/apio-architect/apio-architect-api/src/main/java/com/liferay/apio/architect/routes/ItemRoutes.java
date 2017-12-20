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
import com.liferay.apio.architect.alias.routes.DeleteItemConsumer;
import com.liferay.apio.architect.alias.routes.GetItemFunction;
import com.liferay.apio.architect.alias.routes.UpdateItemFunction;
import com.liferay.apio.architect.consumer.PentaConsumer;
import com.liferay.apio.architect.consumer.TetraConsumer;
import com.liferay.apio.architect.consumer.TriConsumer;
import com.liferay.apio.architect.error.ApioDeveloperError;
import com.liferay.apio.architect.error.ApioDeveloperError.MustHavePathIdentifierMapper;
import com.liferay.apio.architect.function.HexaFunction;
import com.liferay.apio.architect.function.PentaFunction;
import com.liferay.apio.architect.function.TetraFunction;
import com.liferay.apio.architect.function.TriFunction;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

/**
 * Holds information about the routes supported for a {@link
 * com.liferay.apio.architect.router.ItemRouter}.
 *
 * <p>
 * This interface's methods return functions to get the different endpoints of
 * the item resource. You should always use a {@link Builder} to create
 * instances of this interface.
 * </p>
 *
 * @author Alejandro Hernández
 * @see    Builder
 */
public class ItemRoutes<T> {

	public ItemRoutes(Builder<T, ?> builder) {
		_deleteItemConsumer = builder._deleteItemConsumer;
		_singleModelFunction = builder._singleModelFunction;
		_updateItemFunction = builder._updateItemFunction;
	}

	/**
	 * Returns the function used to delete the item, if the endpoint was added
	 * through the {@link Builder} and the function therefore exists. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to delete the item, if the function exists;
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<DeleteItemConsumer> getDeleteConsumerOptional() {
		return Optional.ofNullable(_deleteItemConsumer);
	}

	/**
	 * Returns the function used to obtain the item, if the endpoint was added
	 * through the {@link Builder} and the function therefore exists. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to obtain the item, if the function exists;
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<GetItemFunction<T>> getItemFunctionOptional() {
		return Optional.ofNullable(_singleModelFunction);
	}

	/**
	 * Returns the function used to update the item, if the endpoint was added
	 * through the {@link Builder} and the function therefore exists. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to update the item, if the function exists;
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<UpdateItemFunction<T>> getUpdateItemFunctionOptional() {
		return Optional.ofNullable(_updateItemFunction);
	}

	/**
	 * Creates the {@link ItemRoutes} of a {@link
	 * com.liferay.apio.architect.router.ItemRouter}.
	 */
	@SuppressWarnings("unused")
	public static class Builder<T, U> {

		public Builder(
			Class<T> modelClass, Class<U> identifierClass,
			RequestFunction<Function<Class<?>, Optional<?>>>
				provideClassFunction,
			Supplier<BiFunction<Class<?>, Path,
				Optional<?>>> identifierFunctionSupplier) {

			_modelClass = modelClass;
			_identifierClass = identifierClass;
			_provideClassFunction = provideClassFunction;
			_identifierFunctionSupplier = identifierFunctionSupplier;
		}

		/**
		 * Adds a route to an item function with one extra parameter.
		 *
		 * @param  biFunction the function that calculates the item
		 * @param  aClass the class of the item function's second parameter
		 * @return the updated builder
		 */
		public <A> ItemRoutes.Builder<T, U> addGetter(
			BiFunction<U, A, T> biFunction, Class<A> aClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);

				U u = _convertIdentifier(path, _identifierClass);

				return biFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					u, a
				);
			};

			return this;
		}

		/**
		 * Adds a route to an item function with none extra parameters.
		 *
		 * @param  function the function that calculates the item
		 * @return the updated builder
		 */
		public ItemRoutes.Builder<T, U> addGetter(Function<U, T> function) {
			_singleModelFunction =
				httpServletRequest -> path -> function.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					_convertIdentifier(path, _identifierClass)
				);

			return this;
		}

		/**
		 * Adds a route to an item function with four extra parameters.
		 *
		 * @param  pentaFunction the function that calculates the item
		 * @param  aClass the class of the item function's second parameter
		 * @param  bClass the class of the item function's third parameter
		 * @param  cClass the class of the item function's fourth parameter
		 * @param  dClass the class of the item function's fifth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D> ItemRoutes.Builder<T, U> addGetter(
			PentaFunction<U, A, B, C, D, T> pentaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);

				U u = _convertIdentifier(path, _identifierClass);

				return pentaFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					u, a, b, c, d
				);
			};

			return this;
		}

		/**
		 * Adds a route to an item function with three extra parameters.
		 *
		 * @param  tetraFunction the function that calculates the item
		 * @param  aClass the class of the item function's second parameter
		 * @param  bClass the class of the item function's third parameter
		 * @param  cClass the class of the item function's fourth parameter
		 * @return the updated builder
		 */
		public <A, B, C> ItemRoutes.Builder<T, U> addGetter(
			TetraFunction<U, A, B, C, T> tetraFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);

				U u = _convertIdentifier(path, _identifierClass);

				return tetraFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					u, a, b, c
				);
			};

			return this;
		}

		/**
		 * Adds a route to an item function with two extra parameters.
		 *
		 * @param  triFunction the function that calculates the item
		 * @param  aClass the class of the item function's second parameter
		 * @param  bClass the class of the item function's third parameter
		 * @return the updated builder
		 */
		public <A, B> ItemRoutes.Builder<T, U> addGetter(
			TriFunction<U, A, B, T> triFunction, Class<A> aClass,
			Class<B> bClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);

				U u = _convertIdentifier(path, _identifierClass);

				return triFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					u, a, b
				);
			};

			return this;
		}

		/**
		 * Adds a route to a remover function with one extra parameter.
		 *
		 * @param  biConsumer the remover function that removes the item
		 * @param  aClass the class of the item remover function's second
		 *         parameter
		 * @return the updated builder
		 */
		public <A> ItemRoutes.Builder<T, U> addRemover(
			BiConsumer<U, A> biConsumer, Class<A> aClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _identifierClass);
				A a = _provideClass(httpServletRequest, aClass);

				biConsumer.accept(u, a);
			};

			return this;
		}

		/**
		 * Adds a route to a remover function with none extra parameters.
		 *
		 * @param  consumer the remover function that removes the item
		 * @return the updated builder
		 */
		public ItemRoutes.Builder<T, U> addRemover(Consumer<U> consumer) {
			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _identifierClass);

				consumer.accept(u);
			};

			return this;
		}

		/**
		 * Adds a route to a remover function with four extra parameters.
		 *
		 * @param  pentaConsumer the remover function that removes the item
		 * @param  aClass the class of the item remover function's second
		 *         parameter
		 * @param  bClass the class of the item remover function's third
		 *         parameter
		 * @param  cClass the class of the item remover function's fourth
		 *         parameter
		 * @param  dClass the class of the item remover function's fifth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D> ItemRoutes.Builder<T, U> addRemover(
			PentaConsumer<U, A, B, C, D> pentaConsumer, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _identifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);

				pentaConsumer.accept(u, a, b, c, d);
			};

			return this;
		}

		/**
		 * Adds a route to a remover function with three extra parameters.
		 *
		 * @param  tetraConsumer the remover function that removes the item
		 * @param  aClass the class of the item remover function's second
		 *         parameter
		 * @param  bClass the class of the item remover function's third
		 *         parameter
		 * @param  cClass the class of the item remover function's fourth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C> ItemRoutes.Builder<T, U> addRemover(
			TetraConsumer<U, A, B, C> tetraConsumer, Class<A> aClass,
			Class<B> bClass, Class<C> cClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _identifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);

				tetraConsumer.accept(u, a, b, c);
			};

			return this;
		}

		/**
		 * Adds a route to a remover function with two extra parameters.
		 *
		 * @param  triConsumer the remover function that removes the item
		 * @param  aClass the class of the item remover function's second
		 *         parameter
		 * @param  bClass the class of the item remover function's third
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B> ItemRoutes.Builder<T, U> addRemover(
			TriConsumer<U, A, B> triConsumer, Class<A> aClass,
			Class<B> bClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _identifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);

				triConsumer.accept(u, a, b);
			};

			return this;
		}

		/**
		 * Adds a route to a updater function with none extra parameters.
		 *
		 * @param  biFunction the updater function that removes the item
		 * @return the updated builder
		 */
		public ItemRoutes.Builder<T, U> addUpdater(
			BiFunction<U, Map<String, Object>, T> biFunction) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				U u = _convertIdentifier(path, _identifierClass);

				return biFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					u, body
				);
			};

			return this;
		}

		/**
		 * Adds a route to a updater function with four extra parameters.
		 *
		 * @param  hexaFunction the updater function that removes the item
		 * @param  aClass the class of the item updater function's third
		 *         parameter
		 * @param  bClass the class of the item updater function's fourth
		 *         parameter
		 * @param  cClass the class of the item updater function's fifth
		 *         parameter
		 * @param  dClass the class of the item updater function's sixth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D> ItemRoutes.Builder<T, U> addUpdater(
			HexaFunction<U, Map<String, Object>, A, B, C, D, T> hexaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);

				U u = _convertIdentifier(path, _identifierClass);

				return hexaFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					u, body, a, b, c, d
				);
			};

			return this;
		}

		/**
		 * Adds a route to a updater function with three extra parameters.
		 *
		 * @param  pentaFunction the updater function that removes the item
		 * @param  aClass the class of the item updater function's third
		 *         parameter
		 * @param  bClass the class of the item updater function's fourth
		 *         parameter
		 * @param  cClass the class of the item updater function's fifth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C> ItemRoutes.Builder<T, U> addUpdater(
			PentaFunction<U, Map<String, Object>, A, B, C, T> pentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);

				U u = _convertIdentifier(path, _identifierClass);

				return pentaFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					u, body, a, b, c
				);
			};

			return this;
		}

		/**
		 * Adds a route to a updater function with two extra parameters.
		 *
		 * @param  tetraFunction the updater function that removes the item
		 * @param  aClass the class of the item updater function's third
		 *         parameter
		 * @param  bClass the class of the item updater function's fourth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B> ItemRoutes.Builder<T, U> addUpdater(
			TetraFunction<U, Map<String, Object>, A, B, T> tetraFunction,
			Class<A> aClass, Class<B> bClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);

				U u = _convertIdentifier(path, _identifierClass);

				return tetraFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					u, body, a, b
				);
			};

			return this;
		}

		/**
		 * Adds a route to a updater function with one extra parameter.
		 *
		 * @param  triFunction the updater function that removes the item
		 * @param  aClass the class of the item updater function's third
		 *         parameter
		 * @return the updated builder
		 */
		public <A> ItemRoutes.Builder<T, U> addUpdater(
			TriFunction<U, Map<String, Object>, A, T> triFunction,
			Class<A> aClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);

				U u = _convertIdentifier(path, _identifierClass);

				return triFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					u, body, a
				);
			};

			return this;
		}

		/**
		 * Constructs the {@link ItemRoutes} instance with the information
		 * provided to the builder.
		 *
		 * @return the {@code Routes} instance
		 */
		public ItemRoutes<T> build() {
			return new ItemRoutes<>(this);
		}

		@SuppressWarnings("unchecked")
		private <V> V _convertIdentifier(Path path, Class<V> identifierClass) {
			Optional<?> optional = _identifierFunctionSupplier.get(
			).apply(
				identifierClass, path
			);

			return optional.map(
				convertedIdentifier -> (V)convertedIdentifier
			).orElseThrow(
				() -> new MustHavePathIdentifierMapper(identifierClass)
			);
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
				() -> new ApioDeveloperError.MustHaveProvider(clazz)
			);
		}

		private DeleteItemConsumer _deleteItemConsumer;
		private final Class<U> _identifierClass;
		private final Supplier<BiFunction<Class<?>, Path,
			Optional<?>>> _identifierFunctionSupplier;
		private final Class<T> _modelClass;
		private final RequestFunction<Function<Class<?>, Optional<?>>>
			_provideClassFunction;
		private GetItemFunction<T> _singleModelFunction;
		private UpdateItemFunction<T> _updateItemFunction;

	}

	private DeleteItemConsumer _deleteItemConsumer;
	private GetItemFunction<T> _singleModelFunction;
	private UpdateItemFunction<T> _updateItemFunction;

}