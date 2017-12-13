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

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.alias.RequestFunction;
import com.liferay.apio.architect.alias.routes.CreateItemFunction;
import com.liferay.apio.architect.alias.routes.DeleteItemConsumer;
import com.liferay.apio.architect.alias.routes.GetItemFunction;
import com.liferay.apio.architect.alias.routes.GetPageFunction;
import com.liferay.apio.architect.alias.routes.UpdateItemFunction;
import com.liferay.apio.architect.consumer.DecaConsumer;
import com.liferay.apio.architect.consumer.EnneaConsumer;
import com.liferay.apio.architect.consumer.HeptaConsumer;
import com.liferay.apio.architect.consumer.HexaConsumer;
import com.liferay.apio.architect.consumer.OctaConsumer;
import com.liferay.apio.architect.consumer.PentaConsumer;
import com.liferay.apio.architect.consumer.TetraConsumer;
import com.liferay.apio.architect.consumer.TriConsumer;
import com.liferay.apio.architect.error.ApioDeveloperError;
import com.liferay.apio.architect.error.ApioDeveloperError.MustHavePathIdentifierMapper;
import com.liferay.apio.architect.error.ApioDeveloperError.MustUseSameIdentifier;
import com.liferay.apio.architect.function.DecaFunction;
import com.liferay.apio.architect.function.EnneaFunction;
import com.liferay.apio.architect.function.HeptaFunction;
import com.liferay.apio.architect.function.HexaFunction;
import com.liferay.apio.architect.function.OctaFunction;
import com.liferay.apio.architect.function.PentaFunction;
import com.liferay.apio.architect.function.TetraFunction;
import com.liferay.apio.architect.function.TriFunction;
import com.liferay.apio.architect.function.UndecaFunction;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

/**
 * Holds information about the routes supported for a {@link
 * com.liferay.apio.architect.resource.CollectionResource}.
 *
 * <p>
 * This interface's methods return functions to get the different endpoints of
 * the collection resource. You should always use a {@link Builder} to create
 * instances of this interface.
 * </p>
 *
 * @author Alejandro Hernández
 * @see    Builder
 */
@ProviderType
public class Routes<T> {

	public Routes(Builder<T, ? extends Identifier> builder) {
		_createItemFunction = builder._createItemFunction;
		_deleteItemConsumer = builder._deleteItemConsumer;
		_getPageFunction = builder._getPageFunction;
		_singleModelFunction = builder._singleModelFunction;
		_updateItemFunction = builder._updateItemFunction;
	}

	/**
	 * Returns the function that is used to create the single model of a {@link
	 * com.liferay.apio.architect.resource.CollectionResource}, if the endpoint
	 * was added through the {@link Builder} and the function therefore exists.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the function that uses a POST request to create the single model,
	 *         if the function exists; {@code Optional#empty()} otherwise
	 */
	public Optional<CreateItemFunction<T>> getCreateItemFunctionOptional() {
		return Optional.ofNullable(_createItemFunction);
	}

	/**
	 * Returns the function used to remove a single model of a {@link
	 * com.liferay.apio.architect.resource.CollectionResource}, if the endpoint
	 * was added through the {@link Builder} and the function therefore exists.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to remove a single model, if the function
	 *         exists; {@code Optional#empty()} otherwise
	 */
	public Optional<DeleteItemConsumer> getDeleteConsumerOptional() {
		return Optional.ofNullable(_deleteItemConsumer);
	}

	/**
	 * Returns the function used to obtain the page of a {@link
	 * com.liferay.apio.architect.resource.CollectionResource}, if the endpoint
	 * was added through the {@link Builder} and the function therefore exists.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to create the page, if the function exists;
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<GetPageFunction<T>> getGetPageFunctionOptional() {
		return Optional.ofNullable(_getPageFunction);
	}

	/**
	 * Returns the function used to retrieve the single model of a {@link
	 * com.liferay.apio.architect.resource.CollectionResource}, if the endpoint
	 * was added through the {@link Builder} and the function therefore exists.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the function that uses a GET request to retrieve the single
	 *         model, if the function exists; {@code Optional#empty()} otherwise
	 */
	public Optional<GetItemFunction<T>> getItemFunctionOptional() {
		return Optional.ofNullable(_singleModelFunction);
	}

	/**
	 * Returns the function used to update the single model of a {@link
	 * com.liferay.apio.architect.resource.CollectionResource}, if the endpoint
	 * was added through the {@link Builder} and the function therefore exists.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to update the single model, if the function
	 *         exists; {@code Optional#empty()} otherwise
	 */
	public Optional<UpdateItemFunction<T>> getUpdateItemFunctionOptional() {
		return Optional.ofNullable(_updateItemFunction);
	}

	/**
	 * Creates {@link Routes} of a {@link
	 * com.liferay.apio.architect.resource.CollectionResource}.
	 */
	@ProviderType
	@SuppressWarnings("unused")
	public static class Builder<T, U extends Identifier> {

		public Builder(
			Class<T> modelClass, Class<U> singleModelIdentifierClass,
			RequestFunction<Function<Class<?>, Optional<?>>>
				provideClassFunction,
			BiFunction<Class<? extends Identifier>, Path,
				Optional<? extends Identifier>> identifierFunction) {

			_modelClass = modelClass;
			_singleModelIdentifierClass = singleModelIdentifierClass;
			_provideClassFunction = provideClassFunction;
			_identifierFunction = identifierFunction;
		}

		/**
		 * Adds a route to a collection page function with two parameters.
		 *
		 * @param  biFunction the function that calculates the page
		 * @param  identifierClass the identifier's class
		 * @return the updated builder
		 */
		public <V extends Identifier> Routes.Builder<T, U>
			addCollectionPageGetter(
				BiFunction<Pagination, V, PageItems<T>> biFunction,
				Class<V> identifierClass) {

			_getPageFunction = httpServletRequest -> path -> identifier -> {
				Pagination pagination = _provideClass(
					httpServletRequest, Pagination.class);

				V v = _getIdentifier(identifier, identifierClass);

				PageItems<T> pageItems = biFunction.apply(pagination, v);

				return new Page<>(_modelClass, pageItems, pagination, path);
			};

			return this;
		}

		/**
		 * Adds a route to a collection page function with ten parameters.
		 *
		 * @param  decaFunction the function that calculates the page
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the page function's third parameter
		 * @param  bClass the class of the page function's fourth parameter
		 * @param  cClass the class of the page function's fifth parameter
		 * @param  dClass the class of the page function's sixth parameter
		 * @param  eClass the class of the page function's seventh parameter
		 * @param  fClass the class of the page function's eighth parameter
		 * @param  gClass the class of the page function's ninth parameter
		 * @param  hClass the class of the page function's tenth parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B, C, D, E, F, G, H>
			Routes.Builder<T, U> addCollectionPageGetter(
				DecaFunction<Pagination, V, A, B, C, D, E, F, G, H,
					PageItems<T>> decaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass, Class<E> eClass,
				Class<F> fClass, Class<G> gClass, Class<H> hClass) {

			_getPageFunction = httpServletRequest -> path -> identifier -> {
				Pagination pagination = _provideClass(
					httpServletRequest, Pagination.class);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);
				H h = _provideClass(httpServletRequest, hClass);

				V v = _getIdentifier(identifier, identifierClass);

				PageItems<T> pageItems = decaFunction.apply(
					pagination, v, a, b, c, d, e, f, g, h);

				return new Page<>(_modelClass, pageItems, pagination, path);
			};

			return this;
		}

		/**
		 * Adds a route to a collection page function with nine parameters.
		 *
		 * @param  enneaFunction the function that calculates the page
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the page function's third parameter
		 * @param  bClass the class of the page function's fourth parameter
		 * @param  cClass the class of the page function's fifth parameter
		 * @param  dClass the class of the page function's sixth parameter
		 * @param  eClass the class of the page function's seventh parameter
		 * @param  fClass the class of the page function's eighth parameter
		 * @param  gClass the class of the page function's ninth parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B, C, D, E, F, G> Routes.Builder<T, U>
			addCollectionPageGetter(
				EnneaFunction<Pagination, V, A, B, C, D, E, F, G, PageItems<T>>
					enneaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass, Class<E> eClass,
				Class<F> fClass, Class<G> gClass) {

			_getPageFunction = httpServletRequest -> path -> identifier -> {
				Pagination pagination = _provideClass(
					httpServletRequest, Pagination.class);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);

				V v = _getIdentifier(identifier, identifierClass);

				PageItems<T> pageItems = enneaFunction.apply(
					pagination, v, a, b, c, d, e, f, g);

				return new Page<>(_modelClass, pageItems, pagination, path);
			};

			return this;
		}

		/**
		 * Adds a route to a collection page function with seven parameters.
		 *
		 * @param  heptaFunction the function that calculates the page
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the page function's third parameter
		 * @param  bClass the class of the page function's fourth parameter
		 * @param  cClass the class of the page function's fifth parameter
		 * @param  dClass the class of the page function's sixth parameter
		 * @param  eClass the class of the page function's seventh parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B, C, D, E> Routes.Builder<T, U>
			addCollectionPageGetter(
				HeptaFunction<Pagination, V, A, B, C, D, E, PageItems<T>>
					heptaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass, Class<E> eClass) {

			_getPageFunction = httpServletRequest -> path -> identifier -> {
				Pagination pagination = _provideClass(
					httpServletRequest, Pagination.class);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);

				V v = _getIdentifier(identifier, identifierClass);

				PageItems<T> pageItems = heptaFunction.apply(
					pagination, v, a, b, c, d, e);

				return new Page<>(_modelClass, pageItems, pagination, path);
			};

			return this;
		}

		/**
		 * Adds a route to a collection page function with six parameters.
		 *
		 * @param  hexaFunction the function that calculates the page
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the page function's third parameter
		 * @param  bClass the class of the page function's fourth parameter
		 * @param  cClass the class of the page function's fifth parameter
		 * @param  dClass the class of the page function's sixth parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B, C, D> Routes.Builder<T, U>
			addCollectionPageGetter(
				HexaFunction<Pagination, V, A, B, C, D, PageItems<T>>
					hexaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass) {

			_getPageFunction = httpServletRequest -> path -> identifier -> {
				Pagination pagination = _provideClass(
					httpServletRequest, Pagination.class);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);

				V v = _getIdentifier(identifier, identifierClass);

				PageItems<T> pageItems = hexaFunction.apply(
					pagination, v, a, b, c, d);

				return new Page<>(_modelClass, pageItems, pagination, path);
			};

			return this;
		}

		/**
		 * Adds a route to a collection page function with eight parameters.
		 *
		 * @param  octaFunction the function that calculates the page
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the page function's third parameter
		 * @param  bClass the class of the page function's fourth parameter
		 * @param  cClass the class of the page function's fifth parameter
		 * @param  dClass the class of the page function's sixth parameter
		 * @param  eClass the class of the page function's seventh parameter
		 * @param  fClass the class of the page function's eighth parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B, C, D, E, F> Routes.Builder<T, U>
			addCollectionPageGetter(
				OctaFunction<Pagination, V, A, B, C, D, E, F, PageItems<T>>
					octaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass, Class<E> eClass,
				Class<F> fClass) {

			_getPageFunction = httpServletRequest -> path -> identifier -> {
				Pagination pagination = _provideClass(
					httpServletRequest, Pagination.class);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);

				V v = _getIdentifier(identifier, identifierClass);

				PageItems<T> pageItems = octaFunction.apply(
					pagination, v, a, b, c, d, e, f);

				return new Page<>(_modelClass, pageItems, pagination, path);
			};

			return this;
		}

		/**
		 * Adds a route to a collection page function with five parameters.
		 *
		 * @param  pentaFunction the function that calculates the page
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the page function's third parameter
		 * @param  bClass the class of the page function's fourth parameter
		 * @param  cClass the class of the page function's fifth parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B, C> Routes.Builder<T, U>
			addCollectionPageGetter(
				PentaFunction<Pagination, V, A, B, C, PageItems<T>>
					pentaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass) {

			_getPageFunction = httpServletRequest -> path -> identifier -> {
				Pagination pagination = _provideClass(
					httpServletRequest, Pagination.class);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);

				V v = _getIdentifier(identifier, identifierClass);

				PageItems<T> pageItems = pentaFunction.apply(
					pagination, v, a, b, c);

				return new Page<>(_modelClass, pageItems, pagination, path);
			};

			return this;
		}

		/**
		 * Adds a route to a collection page function with four parameters.
		 *
		 * @param  tetraFunction the function that calculates the page
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the page function's third parameter
		 * @param  bClass the class of the page function's fourth parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A, B> Routes.Builder<T, U>
			addCollectionPageGetter(
				TetraFunction<Pagination, V, A, B, PageItems<T>> tetraFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass) {

			_getPageFunction = httpServletRequest -> path -> identifier -> {
				Pagination pagination = _provideClass(
					httpServletRequest, Pagination.class);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);

				V v = _getIdentifier(identifier, identifierClass);

				PageItems<T> pageItems = tetraFunction.apply(
					pagination, v, a, b);

				return new Page<>(_modelClass, pageItems, pagination, path);
			};

			return this;
		}

		/**
		 * Adds a route to a collection page function with three parameters.
		 *
		 * @param  triFunction the function that calculates the page
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the page function's third parameter
		 * @return the updated builder
		 */
		public <V extends Identifier, A> Routes.Builder<T, U>
			addCollectionPageGetter(
				TriFunction<Pagination, V, A, PageItems<T>> triFunction,
				Class<V> identifierClass, Class<A> aClass) {

			_getPageFunction = httpServletRequest -> path -> identifier -> {
				Pagination pagination = _provideClass(
					httpServletRequest, Pagination.class);
				A a = _provideClass(httpServletRequest, aClass);

				V v = _getIdentifier(identifier, identifierClass);

				PageItems<T> pageItems = triFunction.apply(pagination, v, a);

				return new Page<>(_modelClass, pageItems, pagination, path);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with two parameters.
		 *
		 * @param  biFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @return the updated builder
		 */
		public <V extends Identifier> Routes.Builder<T, U>
			addCollectionPageItemCreator(
				BiFunction<V, Map<String, Object>, T> biFunction,
				Class<V> identifierClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				V v = _getIdentifier(identifier, identifierClass);

				T t = biFunction.apply(v, body);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with ten parameters.
		 *
		 * @param  decaFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the single model POST function's third
		 *         parameter
		 * @param  bClass the class of the single model POST function's fourth
		 *         parameter
		 * @param  cClass the class of the single model POST function's fifth
		 *         parameter
		 * @param  dClass the class of the single model POST function's sixth
		 *         parameter
		 * @param  eClass the class of the single model POST function's seventh
		 *         parameter
		 * @param  fClass the class of the single model POST function's eighth
		 *         parameter
		 * @param  gClass the class of the single model POST function's ninth
		 *         parameter
		 * @param  hClass the class of the single model POST function's tenth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G, H, V extends Identifier>
			Routes.Builder<T, U> addCollectionPageItemCreator(
				DecaFunction<V, Map<String, Object>, A, B, C, D, E, F, G, H, T>
					decaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass, Class<E> eClass,
				Class<F> fClass, Class<G> gClass, Class<H> hClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);
				H h = _provideClass(httpServletRequest, hClass);

				V v = _getIdentifier(identifier, identifierClass);

				T t = decaFunction.apply(v, body, a, b, c, d, e, f, g, h);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with nine parameters.
		 *
		 * @param  enneaFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the single model POST function's third
		 *         parameter
		 * @param  bClass the class of the single model POST function's fourth
		 *         parameter
		 * @param  cClass the class of the single model POST function's fifth
		 *         parameter
		 * @param  dClass the class of the single model POST function's sixth
		 *         parameter
		 * @param  eClass the class of the single model POST function's seventh
		 *         parameter
		 * @param  fClass the class of the single model POST function's eighth
		 *         parameter
		 * @param  gClass the class of the single model POST function's ninth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G, V extends Identifier> Routes.Builder<T, U>
			addCollectionPageItemCreator(
				EnneaFunction<V, Map<String, Object>, A, B, C, D, E, F, G, T>
					enneaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass, Class<E> eClass,
				Class<F> fClass, Class<G> gClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);

				V v = _getIdentifier(identifier, identifierClass);

				T t = enneaFunction.apply(v, body, a, b, c, d, e, f, g);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with seven parameters.
		 *
		 * @param  heptaFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the single model POST function's third
		 *         parameter
		 * @param  bClass the class of the single model POST function's fourth
		 *         parameter
		 * @param  cClass the class of the single model POST function's fifth
		 *         parameter
		 * @param  dClass the class of the single model POST function's sixth
		 *         parameter
		 * @param  eClass the class of the single model POST function's seventh
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, V extends Identifier> Routes.Builder<T, U>
			addCollectionPageItemCreator(
				HeptaFunction<V, Map<String, Object>, A, B, C, D, E, T>
					heptaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass, Class<E> eClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);

				V v = _getIdentifier(identifier, identifierClass);

				T t = heptaFunction.apply(v, body, a, b, c, d, e);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with six parameters.
		 *
		 * @param  hexaFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the single model POST function's third
		 *         parameter
		 * @param  bClass the class of the single model POST function's fourth
		 *         parameter
		 * @param  cClass the class of the single model POST function's fifth
		 *         parameter
		 * @param  dClass the class of the single model POST function's sixth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, V extends Identifier> Routes.Builder<T, U>
			addCollectionPageItemCreator(
				HexaFunction<V, Map<String, Object>, A, B, C, D, T>
					hexaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);

				V v = _getIdentifier(identifier, identifierClass);

				T t = hexaFunction.apply(v, body, a, b, c, d);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with eight parameters.
		 *
		 * @param  octaFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the single model POST function's third
		 *         parameter
		 * @param  bClass the class of the single model POST function's fourth
		 *         parameter
		 * @param  cClass the class of the single model POST function's fifth
		 *         parameter
		 * @param  dClass the class of the single model POST function's sixth
		 *         parameter
		 * @param  eClass the class of the single model POST function's seventh
		 *         parameter
		 * @param  fClass the class of the single model POST function's eighth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, V extends Identifier> Routes.Builder<T, U>
			addCollectionPageItemCreator(
				OctaFunction<V, Map<String, Object>, A, B, C, D, E, F, T>
					octaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass, Class<E> eClass,
				Class<F> fClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);

				V v = _getIdentifier(identifier, identifierClass);

				T t = octaFunction.apply(v, body, a, b, c, d, e, f);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with five parameters.
		 *
		 * @param  pentaFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the single model POST function's third
		 *         parameter
		 * @param  bClass the class of the single model POST function's fourth
		 *         parameter
		 * @param  cClass the class of the single model POST function's fifth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, V extends Identifier> Routes.Builder<T, U>
			addCollectionPageItemCreator(
				PentaFunction<V, Map<String, Object>, A, B, C, T> pentaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);

				V v = _getIdentifier(identifier, identifierClass);

				T t = pentaFunction.apply(v, body, a, b, c);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with four parameters.
		 *
		 * @param  tetraFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the single model POST function's third
		 *         parameter
		 * @param  bClass the class of the single model POST function's fourth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, V extends Identifier> Routes.Builder<T, U>
			addCollectionPageItemCreator(
				TetraFunction<V, Map<String, Object>, A, B, T> tetraFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);

				V v = _getIdentifier(identifier, identifierClass);

				T t = tetraFunction.apply(v, body, a, b);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with three parameters.
		 *
		 * @param  triFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the single model POST function's third
		 *         parameter
		 * @return the updated builder
		 */
		public <A, V extends Identifier> Routes.Builder<T, U>
			addCollectionPageItemCreator(
				TriFunction<V, Map<String, Object>, A, T> triFunction,
				Class<V> identifierClass, Class<A> aClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				A a = _provideClass(httpServletRequest, aClass);

				V v = _getIdentifier(identifier, identifierClass);

				T t = triFunction.apply(v, body, a);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model POST function with eleven parameters.
		 *
		 * @param  undecaFunction the POST function that adds the single model
		 * @param  identifierClass the identifier's class
		 * @param  aClass the class of the single model POST function's third
		 *         parameter
		 * @param  bClass the class of the single model POST function's fourth
		 *         parameter
		 * @param  cClass the class of the single model POST function's fifth
		 *         parameter
		 * @param  dClass the class of the single model POST function's sixth
		 *         parameter
		 * @param  eClass the class of the single model POST function's seventh
		 *         parameter
		 * @param  fClass the class of the single model POST function's eighth
		 *         parameter
		 * @param  gClass the class of the single model POST function's ninth
		 *         parameter
		 * @param  hClass the class of the single model POST function's tenth
		 *         parameter
		 * @param  iClass the class of the single model POST function's eleventh
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G, H, I, V extends Identifier>
			Routes.Builder<T, U> addCollectionPageItemCreator(
				UndecaFunction
					<V, Map<String, Object>, A, B, C, D, E, F, G, H, I, T>
						undecaFunction,
				Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
				Class<C> cClass, Class<D> dClass, Class<E> eClass,
				Class<F> fClass, Class<G> gClass, Class<H> hClass,
				Class<I> iClass) {

			_createItemFunction = httpServletRequest -> identifier -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);
				H h = _provideClass(httpServletRequest, hClass);
				I i = _provideClass(httpServletRequest, iClass);

				V v = _getIdentifier(identifier, identifierClass);

				T t = undecaFunction.apply(v, body, a, b, c, d, e, f, g, h, i);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a single model function with two parameters.
		 *
		 * @param  biFunction the function that calculates the single model
		 * @param  aClass the class of the single model function's second
		 *         parameter
		 * @return the updated builder
		 */
		public <A> Routes.Builder<T, U> addCollectionPageItemGetter(
			BiFunction<U, A, T> biFunction, Class<A> aClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				return biFunction.andThen(
					_getCreateSingleModelFunction()
				).apply(
					u, a
				);
			};

			return this;
		}

		/**
		 * Adds a route to a single model function with ten parameters.
		 *
		 * @param  decaFunction the function that calculates the single model
		 * @param  aClass the class of the single model function's second
		 *         parameter
		 * @param  bClass the class of the single model function's third
		 *         parameter
		 * @param  cClass the class of the single model function's fourth
		 *         parameter
		 * @param  dClass the class of the single model function's fifth
		 *         parameter
		 * @param  eClass the class of the single model function's sixth
		 *         parameter
		 * @param  fClass the class of the single model function's seventh
		 *         parameter
		 * @param  gClass the class of the single model function's eighth
		 *         parameter
		 * @param  hClass the class of the single model function's ninth
		 *         parameter
		 * @param  iClass the class of the single model function's tenth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G, H, I> Routes.Builder<T, U>
			addCollectionPageItemGetter(
				DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass,
				Class<G> gClass, Class<H> hClass, Class<I> iClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);
				H h = _provideClass(httpServletRequest, hClass);
				I i = _provideClass(httpServletRequest, iClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				return decaFunction.andThen(
					_getCreateSingleModelFunction()
				).apply(
					u, a, b, c, d, e, f, g, h, i
				);
			};

			return this;
		}

		/**
		 * Adds a route to a single model function with nine parameters.
		 *
		 * @param  enneaFunction the function that calculates the single model
		 * @param  aClass the class of the single model function's second
		 *         parameter
		 * @param  bClass the class of the single model function's third
		 *         parameter
		 * @param  cClass the class of the single model function's fourth
		 *         parameter
		 * @param  dClass the class of the single model function's fifth
		 *         parameter
		 * @param  eClass the class of the single model function's sixth
		 *         parameter
		 * @param  fClass the class of the single model function's seventh
		 *         parameter
		 * @param  gClass the class of the single model function's eighth
		 *         parameter
		 * @param  hClass the class of the single model function's ninth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G, H> Routes.Builder<T, U>
			addCollectionPageItemGetter(
				EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass,
				Class<G> gClass, Class<H> hClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);
				H h = _provideClass(httpServletRequest, hClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				return enneaFunction.andThen(
					_getCreateSingleModelFunction()
				).apply(
					u, a, b, c, d, e, f, g, h
				);
			};

			return this;
		}

		/**
		 * Adds a route to a single model function.
		 *
		 * @param  function the function that calculates the single model
		 * @return the updated builder
		 */
		public Routes.Builder<T, U> addCollectionPageItemGetter(
			Function<U, T> function) {

			_singleModelFunction =
				httpServletRequest -> path -> function.andThen(
					_getCreateSingleModelFunction()
				).apply(
					_convertIdentifier(path, _singleModelIdentifierClass)
				);

			return this;
		}

		/**
		 * Adds a route to a single model function with seven parameters.
		 *
		 * @param  heptaFunction the function that calculates the single model
		 * @param  aClass the class of the single model function's second
		 *         parameter
		 * @param  bClass the class of the single model function's third
		 *         parameter
		 * @param  cClass the class of the single model function's fourth
		 *         parameter
		 * @param  dClass the class of the single model function's fifth
		 *         parameter
		 * @param  eClass the class of the single model function's sixth
		 *         parameter
		 * @param  fClass the class of the single model function's seventh
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F> Routes.Builder<T, U>
			addCollectionPageItemGetter(
				HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				return heptaFunction.andThen(
					_getCreateSingleModelFunction()
				).apply(
					u, a, b, c, d, e, f
				);
			};

			return this;
		}

		/**
		 * Adds a route to a single model function with six parameters.
		 *
		 * @param  hexaFunction the function that calculates the single model
		 * @param  aClass the class of the single model function's second
		 *         parameter
		 * @param  bClass the class of the single model function's third
		 *         parameter
		 * @param  cClass the class of the single model function's fourth
		 *         parameter
		 * @param  dClass the class of the single model function's fifth
		 *         parameter
		 * @param  eClass the class of the single model function's sixth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E> Routes.Builder<T, U> addCollectionPageItemGetter(
			HexaFunction<U, A, B, C, D, E, T> hexaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				return hexaFunction.andThen(
					_getCreateSingleModelFunction()
				).apply(
					u, a, b, c, d, e
				);
			};

			return this;
		}

		/**
		 * Adds a route to a single model function with eight parameters.
		 *
		 * @param  octaFunction the function that calculates the single model
		 * @param  aClass the class of the single model function's second
		 *         parameter
		 * @param  bClass the class of the single model function's third
		 *         parameter
		 * @param  cClass the class of the single model function's fourth
		 *         parameter
		 * @param  dClass the class of the single model function's fifth
		 *         parameter
		 * @param  eClass the class of the single model function's sixth
		 *         parameter
		 * @param  fClass the class of the single model function's seventh
		 *         parameter
		 * @param  gClass the class of the single model function's eighth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G> Routes.Builder<T, U>
			addCollectionPageItemGetter(
				OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass,
				Class<G> gClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				return octaFunction.andThen(
					_getCreateSingleModelFunction()
				).apply(
					u, a, b, c, d, e, f, g
				);
			};

			return this;
		}

		/**
		 * Adds a route to a single model function with five parameters.
		 *
		 * @param  pentaFunction the function that calculates the single model
		 * @param  aClass the class of the single model function's second
		 *         parameter
		 * @param  bClass the class of the single model function's third
		 *         parameter
		 * @param  cClass the class of the single model function's fourth
		 *         parameter
		 * @param  dClass the class of the single model function's fifth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C, D> Routes.Builder<T, U> addCollectionPageItemGetter(
			PentaFunction<U, A, B, C, D, T> pentaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				return pentaFunction.andThen(
					_getCreateSingleModelFunction()
				).apply(
					u, a, b, c, d
				);
			};

			return this;
		}

		/**
		 * Adds a route to a single model function with four parameters.
		 *
		 * @param  tetraFunction the function that calculates the single model
		 * @param  aClass the class of the single model function's second
		 *         parameter
		 * @param  bClass the class of the single model function's third
		 *         parameter
		 * @param  cClass the class of the single model function's fourth
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B, C> Routes.Builder<T, U> addCollectionPageItemGetter(
			TetraFunction<U, A, B, C, T> tetraFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				return tetraFunction.andThen(
					_getCreateSingleModelFunction()
				).apply(
					u, a, b, c
				);
			};

			return this;
		}

		/**
		 * Adds a route to a single model function with three parameters.
		 *
		 * @param  triFunction the function that calculates the single model
		 * @param  aClass the class of the single model function's second
		 *         parameter
		 * @param  bClass the class of the single model function's third
		 *         parameter
		 * @return the updated builder
		 */
		public <A, B> Routes.Builder<T, U> addCollectionPageItemGetter(
			TriFunction<U, A, B, T> triFunction, Class<A> aClass,
			Class<B> bClass) {

			_singleModelFunction = httpServletRequest -> path -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				return triFunction.andThen(
					_getCreateSingleModelFunction()
				).apply(
					u, a, b
				);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function with two
		 * parameters.
		 *
		 * @param  biConsumer the function that deletes the collection item
		 * @param  aClass the class of the remover function's second parameter
		 * @return the updated builder
		 */
		public <A> Routes.Builder<T, U> addCollectionPageItemRemover(
			BiConsumer<U, A> biConsumer, Class<A> aClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);
				A a = _provideClass(httpServletRequest, aClass);

				biConsumer.accept(u, a);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function.
		 *
		 * @param  consumer the function that deletes the collection item
		 * @return the updated builder
		 */
		public Routes.Builder<T, U> addCollectionPageItemRemover(
			Consumer<U> consumer) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				consumer.accept(u);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function with ten
		 * parameters.
		 *
		 * @param  decaConsumer the function that deletes the collection item
		 * @param  aClass the class of the remover function's second parameter
		 * @param  bClass the class of the remover function's third parameter
		 * @param  cClass the class of the remover function's fourth parameter
		 * @param  dClass the class of the remover function's fifth parameter
		 * @param  eClass the class of the remover function's sixth parameter
		 * @param  fClass the class of the remover function's seventh parameter
		 * @param  gClass the class of the remover function's eighth parameter
		 * @param  hClass the class of the remover function's ninth parameter
		 * @param  iClass the class of the remover function's tenth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G, H, I> Routes.Builder<T, U>
			addCollectionPageItemRemover(
				DecaConsumer<U, A, B, C, D, E, F, G, H, I> decaConsumer,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass,
				Class<G> gClass, Class<H> hClass, Class<I> iClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);
				H h = _provideClass(httpServletRequest, hClass);
				I i = _provideClass(httpServletRequest, iClass);

				decaConsumer.accept(u, a, b, c, d, e, f, g, h, i);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function with nine
		 * parameters.
		 *
		 * @param  enneaConsumer the function that deletes the collection item
		 * @param  aClass the class of the remover function's second parameter
		 * @param  bClass the class of the remover function's third parameter
		 * @param  cClass the class of the remover function's fourth parameter
		 * @param  dClass the class of the remover function's fifth parameter
		 * @param  eClass the class of the remover function's sixth parameter
		 * @param  fClass the class of the remover function's seventh parameter
		 * @param  gClass the class of the remover function's eighth parameter
		 * @param  hClass the class of the remover function's ninth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G, H> Routes.Builder<T, U>
			addCollectionPageItemRemover(
				EnneaConsumer<U, A, B, C, D, E, F, G, H> enneaConsumer,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass,
				Class<G> gClass, Class<H> hClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);
				H h = _provideClass(httpServletRequest, hClass);

				enneaConsumer.accept(u, a, b, c, d, e, f, g, h);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function with seven
		 * parameters.
		 *
		 * @param  heptaConsumer the function that deletes the collection item
		 * @param  aClass the class of the remover function's second parameter
		 * @param  bClass the class of the remover function's third parameter
		 * @param  cClass the class of the remover function's fourth parameter
		 * @param  dClass the class of the remover function's fifth parameter
		 * @param  eClass the class of the remover function's sixth parameter
		 * @param  fClass the class of the remover function's seventh parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F> Routes.Builder<T, U>
			addCollectionPageItemRemover(
				HeptaConsumer<U, A, B, C, D, E, F> heptaConsumer,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);

				heptaConsumer.accept(u, a, b, c, d, e, f);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function with six
		 * parameters.
		 *
		 * @param  hexaConsumer the function that deletes the collection item
		 * @param  aClass the class of the remover function's second parameter
		 * @param  bClass the class of the remover function's third parameter
		 * @param  cClass the class of the remover function's fourth parameter
		 * @param  dClass the class of the remover function's fifth parameter
		 * @param  eClass the class of the remover function's sixth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E> Routes.Builder<T, U>
			addCollectionPageItemRemover(
				HexaConsumer<U, A, B, C, D, E> hexaConsumer, Class<A> aClass,
				Class<B> bClass, Class<C> cClass, Class<D> dClass,
				Class<E> eClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);

				hexaConsumer.accept(u, a, b, c, d, e);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function with eight
		 * parameters.
		 *
		 * @param  octaConsumer the function that deletes the collection item
		 * @param  aClass the class of the remover function's second parameter
		 * @param  bClass the class of the remover function's third parameter
		 * @param  cClass the class of the remover function's fourth parameter
		 * @param  dClass the class of the remover function's fifth parameter
		 * @param  eClass the class of the remover function's sixth parameter
		 * @param  fClass the class of the remover function's seventh parameter
		 * @param  gClass the class of the remover function's eighth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G> Routes.Builder<T, U>
			addCollectionPageItemRemover(
				OctaConsumer<U, A, B, C, D, E, F, G> octaConsumer,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass,
				Class<G> gClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);

				octaConsumer.accept(u, a, b, c, d, e, f, g);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function with five
		 * parameters.
		 *
		 * @param  pentaConsumer the function that deletes the collection item
		 * @param  aClass the class of the remover function's second parameter
		 * @param  bClass the class of the remover function's third parameter
		 * @param  cClass the class of the remover function's fourth parameter
		 * @param  dClass the class of the remover function's fifth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D> Routes.Builder<T, U> addCollectionPageItemRemover(
			PentaConsumer<U, A, B, C, D> pentaConsumer, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);

				pentaConsumer.accept(u, a, b, c, d);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function with four
		 * parameters.
		 *
		 * @param  tetraConsumer the function that deletes the collection item
		 * @param  aClass the class of the remover function's second parameter
		 * @param  bClass the class of the remover function's third parameter
		 * @param  cClass the class of the remover function's fourth parameter
		 * @return the updated builder
		 */
		public <A, B, C> Routes.Builder<T, U> addCollectionPageItemRemover(
			TetraConsumer<U, A, B, C> tetraConsumer, Class<A> aClass,
			Class<B> bClass, Class<C> cClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);

				tetraConsumer.accept(u, a, b, c);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item remover function with three
		 * parameters.
		 *
		 * @param  triConsumer the function that deletes the collection item
		 * @param  aClass the class of the remover function's second parameter
		 * @param  bClass the class of the remover function's third parameter
		 * @return the updated builder
		 */
		public <A, B> Routes.Builder<T, U> addCollectionPageItemRemover(
			TriConsumer<U, A, B> triConsumer, Class<A> aClass,
			Class<B> bClass) {

			_deleteItemConsumer = httpServletRequest -> path -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);

				triConsumer.accept(u, a, b);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with two
		 * parameters.
		 *
		 * @param  biFunction the function that updates the collection item
		 * @return the updated builder
		 */
		public Routes.Builder<T, U> addCollectionPageItemUpdater(
			BiFunction<U, Map<String, Object>, T> biFunction) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = biFunction.apply(u, body);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with ten
		 * parameters.
		 *
		 * @param  decaFunction the function that updates the collection item
		 * @param  aClass the class of the updater function's third parameter
		 * @param  bClass the class of the updater function's fourth parameter
		 * @param  cClass the class of the updater function's fifth parameter
		 * @param  dClass the class of the updater function's sixth parameter
		 * @param  eClass the class of the updater function's seventh parameter
		 * @param  fClass the class of the updater function's eighth parameter
		 * @param  gClass the class of the updater function's ninth parameter
		 * @param  hClass the class of the updater function's tenth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G, H> Routes.Builder<T, U>
			addCollectionPageItemUpdater(
				DecaFunction<U, Map<String, Object>, A, B, C, D, E, F, G, H, T>
					decaFunction,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass,
				Class<G> gClass, Class<H> hClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);
				H h = _provideClass(httpServletRequest, hClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = decaFunction.apply(u, body, a, b, c, d, e, f, g, h);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with nine
		 * parameters.
		 *
		 * @param  enneaFunction the function that updates the collection item
		 * @param  aClass the class of the updater function's third parameter
		 * @param  bClass the class of the updater function's fourth parameter
		 * @param  cClass the class of the updater function's fifth parameter
		 * @param  dClass the class of the updater function's sixth parameter
		 * @param  eClass the class of the updater function's seventh parameter
		 * @param  fClass the class of the updater function's eighth parameter
		 * @param  gClass the class of the updater function's ninth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G> Routes.Builder<T, U>
			addCollectionPageItemUpdater(
				EnneaFunction<U, Map<String, Object>, A, B, C, D, E, F, G, T>
					enneaFunction,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass,
				Class<G> gClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = enneaFunction.apply(u, body, a, b, c, d, e, f, g);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with seven
		 * parameters.
		 *
		 * @param  heptaFunction the function that updates the collection item
		 * @param  aClass the class of the updater function's third parameter
		 * @param  bClass the class of the updater function's fourth parameter
		 * @param  cClass the class of the updater function's fifth parameter
		 * @param  dClass the class of the updater function's sixth parameter
		 * @param  eClass the class of the updater function's seventh parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E> Routes.Builder<T, U>
			addCollectionPageItemUpdater(
				HeptaFunction<U, Map<String, Object>, A, B, C, D, E, T>
					heptaFunction,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = heptaFunction.apply(u, body, a, b, c, d, e);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with six
		 * parameters.
		 *
		 * @param  hexaFunction the function that updates the collection item
		 * @param  aClass the class of the updater function's third parameter
		 * @param  bClass the class of the updater function's fourth parameter
		 * @param  cClass the class of the updater function's fifth parameter
		 * @param  dClass the class of the updater function's sixth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D> Routes.Builder<T, U> addCollectionPageItemUpdater(
			HexaFunction<U, Map<String, Object>, A, B, C, D, T> hexaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = hexaFunction.apply(u, body, a, b, c, d);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with eight
		 * parameters.
		 *
		 * @param  octaFunction the function that updates the collection item
		 * @param  aClass the class of the updater function's third parameter
		 * @param  bClass the class of the updater function's fourth parameter
		 * @param  cClass the class of the updater function's fifth parameter
		 * @param  dClass the class of the updater function's sixth parameter
		 * @param  eClass the class of the updater function's seventh parameter
		 * @param  fClass the class of the updater function's eighth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F> Routes.Builder<T, U>
			addCollectionPageItemUpdater(
				OctaFunction<U, Map<String, Object>, A, B, C, D, E, F, T>
					octaFunction,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = octaFunction.apply(u, body, a, b, c, d, e, f);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with five
		 * parameters.
		 *
		 * @param  pentaFunction the function that updates the collection item
		 * @param  aClass the class of the updater function's third parameter
		 * @param  bClass the class of the updater function's fourth parameter
		 * @param  cClass the class of the updater function's fifth parameter
		 * @return the updated builder
		 */
		public <A, B, C> Routes.Builder<T, U> addCollectionPageItemUpdater(
			PentaFunction<U, Map<String, Object>, A, B, C, T> pentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = pentaFunction.apply(u, body, a, b, c);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with four
		 * parameters.
		 *
		 * @param  tetraFunction the function that updates the collection item
		 * @param  aClass the class of the updater function's third parameter
		 * @param  bClass the class of the updater function's fourth parameter
		 * @return the updated builder
		 */
		public <A, B> Routes.Builder<T, U> addCollectionPageItemUpdater(
			TetraFunction<U, Map<String, Object>, A, B, T> tetraFunction,
			Class<A> aClass, Class<B> bClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = tetraFunction.apply(u, body, a, b);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with three
		 * parameters.
		 *
		 * @param  triFunction the function that updates the collection item
		 * @param  aClass the class of the updater function's third parameter
		 * @return the updated builder
		 */
		public <A> Routes.Builder<T, U> addCollectionPageItemUpdater(
			TriFunction<U, Map<String, Object>, A, T> triFunction,
			Class<A> aClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = triFunction.apply(u, body, a);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Adds a route to a collection item updater function with eleven
		 * parameters.
		 *
		 * @param  undecaFunction the function that updates the collection item
		 * @param  aClass the class of the updater function's third parameter
		 * @param  bClass the class of the updater function's fourth parameter
		 * @param  cClass the class of the updater function's fifth parameter
		 * @param  dClass the class of the updater function's sixth parameter
		 * @param  eClass the class of the updater function's seventh parameter
		 * @param  fClass the class of the updater function's eighth parameter
		 * @param  gClass the class of the updater function's ninth parameter
		 * @param  hClass the class of the updater function's tenth parameter
		 * @param  iClass the class of the updater function's eleventh parameter
		 * @return the updated builder
		 */
		public <A, B, C, D, E, F, G, H, I> Routes.Builder<T, U>
			addCollectionPageItemUpdater(
				UndecaFunction
					<U, Map<String, Object>, A, B, C, D, E, F, G, H, I, T>
						undecaFunction,
				Class<A> aClass, Class<B> bClass, Class<C> cClass,
				Class<D> dClass, Class<E> eClass, Class<F> fClass,
				Class<G> gClass, Class<H> hClass, Class<I> iClass) {

			_updateItemFunction = httpServletRequest -> path -> body -> {
				A a = _provideClass(httpServletRequest, aClass);
				B b = _provideClass(httpServletRequest, bClass);
				C c = _provideClass(httpServletRequest, cClass);
				D d = _provideClass(httpServletRequest, dClass);
				E e = _provideClass(httpServletRequest, eClass);
				F f = _provideClass(httpServletRequest, fClass);
				G g = _provideClass(httpServletRequest, gClass);
				H h = _provideClass(httpServletRequest, hClass);
				I i = _provideClass(httpServletRequest, iClass);

				U u = _convertIdentifier(path, _singleModelIdentifierClass);

				T t = undecaFunction.apply(u, body, a, b, c, d, e, f, g, h, i);

				return _getCreateSingleModelFunction().apply(t);
			};

			return this;
		}

		/**
		 * Constructs the {@link Routes} instance with the information provided
		 * to the builder.
		 *
		 * @return the {@code Routes} instance
		 */
		public Routes<T> build() {
			return new Routes<>(this);
		}

		@SuppressWarnings("unchecked")
		private <V extends Identifier> V _convertIdentifier(
			Path path, Class<V> identifierClass) {

			Optional<? extends Identifier> optional = _identifierFunction.apply(
				identifierClass, path);

			return optional.map(
				convertedIdentifier -> (V)convertedIdentifier
			).orElseThrow(
				() -> new MustHavePathIdentifierMapper(identifierClass)
			);
		}

		private Function<T, SingleModel<T>> _getCreateSingleModelFunction() {
			return t -> new SingleModel<>(t, _modelClass);
		}

		@SuppressWarnings("unchecked")
		private <V extends Identifier> V _getIdentifier(
			Identifier identifier, Class<V> identifierClass) {

			Class<? extends Identifier> clazz = identifier.getClass();

			if (!identifierClass.isAssignableFrom(clazz)) {
				throw new MustUseSameIdentifier(clazz, identifierClass);
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
				() -> new ApioDeveloperError.MustHaveProvider(clazz)
			);
		}

		private CreateItemFunction<T> _createItemFunction;
		private DeleteItemConsumer _deleteItemConsumer;
		private GetPageFunction<T> _getPageFunction;
		private final BiFunction<Class<? extends Identifier>, Path,
			Optional<? extends Identifier>> _identifierFunction;
		private final Class<T> _modelClass;
		private final RequestFunction<Function<Class<?>, Optional<?>>>
			_provideClassFunction;
		private GetItemFunction<T> _singleModelFunction;
		private final Class<U> _singleModelIdentifierClass;
		private UpdateItemFunction<T> _updateItemFunction;

	}

	private CreateItemFunction<T> _createItemFunction;
	private DeleteItemConsumer _deleteItemConsumer;
	private GetPageFunction<T> _getPageFunction;
	private GetItemFunction<T> _singleModelFunction;
	private UpdateItemFunction<T> _updateItemFunction;

}