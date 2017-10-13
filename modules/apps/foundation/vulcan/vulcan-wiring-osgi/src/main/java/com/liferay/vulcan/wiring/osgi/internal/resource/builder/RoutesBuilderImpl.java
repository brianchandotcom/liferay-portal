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

package com.liferay.vulcan.wiring.osgi.internal.resource.builder;

import com.liferay.vulcan.consumer.DecaConsumer;
import com.liferay.vulcan.consumer.EnneaConsumer;
import com.liferay.vulcan.consumer.HeptaConsumer;
import com.liferay.vulcan.consumer.HexaConsumer;
import com.liferay.vulcan.consumer.OctaConsumer;
import com.liferay.vulcan.consumer.PentaConsumer;
import com.liferay.vulcan.consumer.TetraConsumer;
import com.liferay.vulcan.consumer.TriConsumer;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHavePathIdentifierMapper;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveProvider;
import com.liferay.vulcan.error.VulcanDeveloperError.MustUseSameIdentifier;
import com.liferay.vulcan.function.DecaFunction;
import com.liferay.vulcan.function.EnneaFunction;
import com.liferay.vulcan.function.HeptaFunction;
import com.liferay.vulcan.function.HexaFunction;
import com.liferay.vulcan.function.OctaFunction;
import com.liferay.vulcan.function.PentaFunction;
import com.liferay.vulcan.function.TetraFunction;
import com.liferay.vulcan.function.TriFunction;
import com.liferay.vulcan.function.UndecaFunction;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.uri.Path;
import com.liferay.vulcan.wiring.osgi.internal.pagination.PageImpl;
import com.liferay.vulcan.wiring.osgi.internal.resource.RoutesImpl;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 */
public class RoutesBuilderImpl<T, U extends Identifier>
	implements RoutesBuilder<T, U> {

	public RoutesBuilderImpl(
		Class<T> modelClass, Class<U> singleModelIdentifierClass,
		Function<Class<?>, Optional<?>> provideClassFunction,
		BiFunction<Class<? extends Identifier>, Path,
			Optional<? extends Identifier>> identifierFunction) {

		_modelClass = modelClass;
		_singleModelIdentifierClass = singleModelIdentifierClass;
		_provideClassFunction = provideClassFunction;
		_identifierFunction = identifierFunction;
	}

	@Override
	public <V extends Identifier> RoutesBuilder<T, U> addCollectionPageGetter(
		BiFunction<Pagination, V, PageItems<T>> biFunction,
		Class<V> identifierClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPageFunction(
			path -> collectionIdentifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);

					PageItems<T> pageItems = biFunction.apply(
						pagination, identifier);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						path);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		addCollectionPageGetter(
			DecaFunction<Pagination, V, A, B, C, D, E, F, G, H,
				PageItems<T>> decaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPageFunction(
			path -> collectionIdentifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);
					H h = _provideClass(hClass);

					PageItems<T> pageItems = decaFunction.apply(
						pagination, identifier, a, b, c, d, e, f, g, h);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						path);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D, E, F, G> RoutesBuilder<T, U>
		addCollectionPageGetter(
			EnneaFunction<Pagination, V, A, B, C, D, E, F, G, PageItems<T>>
				enneaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPageFunction(
			path -> collectionIdentifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);

					PageItems<T> pageItems = enneaFunction.apply(
						pagination, identifier, a, b, c, d, e, f, g);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						path);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D, E> RoutesBuilder<T, U>
		addCollectionPageGetter(
			HeptaFunction<Pagination, V, A, B, C, D, E, PageItems<T>>
				heptaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPageFunction(
			path -> collectionIdentifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);

					PageItems<T> pageItems = heptaFunction.apply(
						pagination, identifier, a, b, c, d, e);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						path);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D> RoutesBuilder<T, U>
		addCollectionPageGetter(
			HexaFunction<Pagination, V, A, B, C, D, PageItems<T>> hexaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPageFunction(
			path -> collectionIdentifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);

					PageItems<T> pageItems = hexaFunction.apply(
						pagination, identifier, a, b, c, d);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						path);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D, E, F> RoutesBuilder<T, U>
		addCollectionPageGetter(
			OctaFunction<Pagination, V, A, B, C, D, E, F, PageItems<T>>
				octaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPageFunction(
			path -> collectionIdentifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);

					PageItems<T> pageItems = octaFunction.apply(
						pagination, identifier, a, b, c, d, e, f);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						path);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C> RoutesBuilder<T, U>
		addCollectionPageGetter(
			PentaFunction<Pagination, V, A, B, C, PageItems<T>> pentaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPageFunction(
			path -> collectionIdentifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);

					PageItems<T> pageItems = pentaFunction.apply(
						pagination, identifier, a, b, c);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						path);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B> RoutesBuilder<T, U>
		addCollectionPageGetter(
			TetraFunction<Pagination, V, A, B, PageItems<T>> tetraFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPageFunction(
			path -> collectionIdentifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);

					PageItems<T> pageItems = tetraFunction.apply(
						pagination, identifier, a, b);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						path);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A> RoutesBuilder<T, U>
		addCollectionPageGetter(
			TriFunction<Pagination, V, A, PageItems<T>> triFunction,
			Class<V> identifierClass, Class<A> aClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPageFunction(
			path -> collectionIdentifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);

					PageItems<T> pageItems = triFunction.apply(
						pagination, identifier, a);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						path);
				}));

		return this;
	}

	@Override
	public <V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			BiFunction<V, Map<String, Object>, T> biFunction,
			Class<V> identifierClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					T t = biFunction.apply(v, body);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			DecaFunction<V, Map<String, Object>, A, B, C, D, E, F, G, H, T>
				decaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);
					H h = _provideClass(hClass);

					T t = decaFunction.apply(v, body, a, b, c, d, e, f, g, h);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			EnneaFunction<V, Map<String, Object>, A, B, C, D, E, F, G, T>
				enneaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);

					T t = enneaFunction.apply(v, body, a, b, c, d, e, f, g);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			HeptaFunction<V, Map<String, Object>, A, B, C, D, E, T>
				heptaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);

					T t = heptaFunction.apply(v, body, a, b, c, d, e);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			HexaFunction<V, Map<String, Object>, A, B, C, D, T> hexaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);

					T t = hexaFunction.apply(v, body, a, b, c, d);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			OctaFunction<V, Map<String, Object>, A, B, C, D, E, F, T>
				octaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);

					T t = octaFunction.apply(v, body, a, b, c, d, e, f);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			PentaFunction<V, Map<String, Object>, A, B, C, T> pentaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);

					T t = pentaFunction.apply(v, body, a, b, c);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			TetraFunction<V, Map<String, Object>, A, B, T> tetraFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);

					T t = tetraFunction.apply(v, body, a, b);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			TriFunction<V, Map<String, Object>, A, T> triFunction,
			Class<V> identifierClass, Class<A> aClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					A a = _provideClass(aClass);

					T t = triFunction.apply(v, body, a);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H, I, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			UndecaFunction<V, Map<String, Object>, A, B, C, D, E, F, G, H, I, T>
				undecaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass, Class<I> iClass) {

		Function<Identifier, V> collectionIdentifierFunction =
			_getCollectionIdentifierFunction(identifierClass);

		_routesImpl.setPostSingleModelFunction(
			collectionIdentifierFunction.andThen(
				v -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);
					H h = _provideClass(hClass);
					I i = _provideClass(iClass);

					T t = undecaFunction.apply(
						v, body, a, b, c, d, e, f, g, h, i);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A> RoutesBuilder<T, U> addCollectionPageItemGetter(
		BiFunction<U, A, T> biFunction, Class<A> aClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);

				return biFunction.apply(id, a);
			});

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T, U>
		addCollectionPageItemGetter(
			DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
			Class<I> iClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);
				H h = _provideClass(hClass);
				I i = _provideClass(iClass);

				return decaFunction.apply(id, a, b, c, d, e, f, g, h, i);
			});

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		addCollectionPageItemGetter(
			EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass,
			Class<H> hClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);
				H h = _provideClass(hClass);

				return enneaFunction.apply(id, a, b, c, d, e, f, g, h);
			});

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public RoutesBuilder<T, U> addCollectionPageItemGetter(
		Function<U, T> function) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(function);

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public <A, B, C, D, E, F> RoutesBuilder<T, U> addCollectionPageItemGetter(
		HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Class<F> fClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);

				return heptaFunction.apply(id, a, b, c, d, e, f);
			});

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public <A, B, C, D, E> RoutesBuilder<T, U> addCollectionPageItemGetter(
		HexaFunction<U, A, B, C, D, E, T> hexaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);

				return hexaFunction.apply(id, a, b, c, d, e);
			});

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G> RoutesBuilder<T, U>
		addCollectionPageItemGetter(
			OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);

				return octaFunction.apply(id, a, b, c, d, e, f, g);
			});

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public <A, B, C, D> RoutesBuilder<T, U> addCollectionPageItemGetter(
		PentaFunction<U, A, B, C, D, T> pentaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);

				return pentaFunction.apply(id, a, b, c, d);
			});

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public <A, B, C> RoutesBuilder<T, U> addCollectionPageItemGetter(
		TetraFunction<U, A, B, C, T> tetraFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);

				return tetraFunction.apply(id, a, b, c);
			});

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public <A, B> RoutesBuilder<T, U> addCollectionPageItemGetter(
		TriFunction<U, A, B, T> triFunction, Class<A> aClass, Class<B> bClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Path, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);

				return triFunction.apply(id, a, b);
			});

		_routesImpl.setSingleModelFunction(
			modelFunction.andThen(_getCreateSingleModelFunction()));

		return this;
	}

	@Override
	public <A> RoutesBuilder<T, U> addCollectionPageItemRemover(
		BiConsumer<U, A> biConsumer, Class<A> aClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);
			A a = _provideClass(aClass);

			biConsumer.accept(u, a);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public RoutesBuilder<T, U> addCollectionPageItemRemover(
		Consumer<U> consumer) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);

			consumer.accept(u);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T, U>
		addCollectionPageItemRemover(
			DecaConsumer<U, A, B, C, D, E, F, G, H, I> decaConsumer,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
			Class<I> iClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);
			A a = _provideClass(aClass);
			B b = _provideClass(bClass);
			C c = _provideClass(cClass);
			D d = _provideClass(dClass);
			E e = _provideClass(eClass);
			F f = _provideClass(fClass);
			G g = _provideClass(gClass);
			H h = _provideClass(hClass);
			I i = _provideClass(iClass);

			decaConsumer.accept(u, a, b, c, d, e, f, g, h, i);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		addCollectionPageItemRemover(
			EnneaConsumer<U, A, B, C, D, E, F, G, H> enneaConsumer,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass,
			Class<H> hClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);
			A a = _provideClass(aClass);
			B b = _provideClass(bClass);
			C c = _provideClass(cClass);
			D d = _provideClass(dClass);
			E e = _provideClass(eClass);
			F f = _provideClass(fClass);
			G g = _provideClass(gClass);
			H h = _provideClass(hClass);

			enneaConsumer.accept(u, a, b, c, d, e, f, g, h);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public <A, B, C, D, E, F> RoutesBuilder<T, U> addCollectionPageItemRemover(
		HeptaConsumer<U, A, B, C, D, E, F> heptaConsumer, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Class<F> fClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);
			A a = _provideClass(aClass);
			B b = _provideClass(bClass);
			C c = _provideClass(cClass);
			D d = _provideClass(dClass);
			E e = _provideClass(eClass);
			F f = _provideClass(fClass);

			heptaConsumer.accept(u, a, b, c, d, e, f);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public <A, B, C, D, E> RoutesBuilder<T, U> addCollectionPageItemRemover(
		HexaConsumer<U, A, B, C, D, E> hexaConsumer, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);
			A a = _provideClass(aClass);
			B b = _provideClass(bClass);
			C c = _provideClass(cClass);
			D d = _provideClass(dClass);
			E e = _provideClass(eClass);

			hexaConsumer.accept(u, a, b, c, d, e);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G> RoutesBuilder<T, U>
		addCollectionPageItemRemover(
			OctaConsumer<U, A, B, C, D, E, F, G> octaConsumer, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass, Class<G> gClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);
			A a = _provideClass(aClass);
			B b = _provideClass(bClass);
			C c = _provideClass(cClass);
			D d = _provideClass(dClass);
			E e = _provideClass(eClass);
			F f = _provideClass(fClass);
			G g = _provideClass(gClass);

			octaConsumer.accept(u, a, b, c, d, e, f, g);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public <A, B, C, D> RoutesBuilder<T, U> addCollectionPageItemRemover(
		PentaConsumer<U, A, B, C, D> pentaConsumer, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);
			A a = _provideClass(aClass);
			B b = _provideClass(bClass);
			C c = _provideClass(cClass);
			D d = _provideClass(dClass);

			pentaConsumer.accept(u, a, b, c, d);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public <A, B, C> RoutesBuilder<T, U> addCollectionPageItemRemover(
		TetraConsumer<U, A, B, C> tetraConsumer, Class<A> aClass,
		Class<B> bClass, Class<C> cClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);
			A a = _provideClass(aClass);
			B b = _provideClass(bClass);
			C c = _provideClass(cClass);

			tetraConsumer.accept(u, a, b, c);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public <A, B> RoutesBuilder<T, U> addCollectionPageItemRemover(
		TriConsumer<U, A, B> triConsumer, Class<A> aClass, Class<B> bClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Consumer<Path> deleteSingleModelConsumer = path -> {
			U u = identifierFunction.apply(path);
			A a = _provideClass(aClass);
			B b = _provideClass(bClass);

			triConsumer.accept(u, a, b);
		};

		_routesImpl.setDeleteSingleModelConsumer(deleteSingleModelConsumer);

		return this;
	}

	@Override
	public RoutesBuilder<T, U> addCollectionPageItemUpdater(
		BiFunction<U, Map<String, Object>, T> biFunction) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					T t = biFunction.apply(id, body);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		addCollectionPageItemUpdater(
			DecaFunction<U, Map<String, Object>, A, B, C, D, E, F, G, H, T>
				decaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass,
			Class<H> hClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);
					H h = _provideClass(hClass);

					T t = decaFunction.apply(id, body, a, b, c, d, e, f, g, h);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G> RoutesBuilder<T, U>
		addCollectionPageItemUpdater(
			EnneaFunction<U, Map<String, Object>, A, B, C, D, E, F, G, T>
				enneaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);

					T t = enneaFunction.apply(id, body, a, b, c, d, e, f, g);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		HeptaFunction<U, Map<String, Object>, A, B, C, D, E, T> heptaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);

					T t = heptaFunction.apply(id, body, a, b, c, d, e);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		HexaFunction<U, Map<String, Object>, A, B, C, D, T> hexaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);

					T t = hexaFunction.apply(id, body, a, b, c, d);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E, F> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		OctaFunction<U, Map<String, Object>, A, B, C, D, E, F, T> octaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);

					T t = octaFunction.apply(id, body, a, b, c, d, e, f);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		PentaFunction<U, Map<String, Object>, A, B, C, T> pentaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);

					T t = pentaFunction.apply(id, body, a, b, c);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		TetraFunction<U, Map<String, Object>, A, B, T> tetraFunction,
		Class<A> aClass, Class<B> bClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);

					T t = tetraFunction.apply(id, body, a, b);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		TriFunction<U, Map<String, Object>, A, T> triFunction,
		Class<A> aClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					A a = _provideClass(aClass);

					T t = triFunction.apply(id, body, a);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T, U>
		addCollectionPageItemUpdater(
			UndecaFunction<U, Map<String, Object>, A, B, C, D, E, F, G, H, I, T>
				undecaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
			Class<I> iClass) {

		Function<Path, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		_routesImpl.setPutSingleModelFunction(
			identifierFunction.andThen(
				id -> body -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);
					H h = _provideClass(hClass);
					I i = _provideClass(iClass);

					T t = undecaFunction.apply(
						id, body, a, b, c, d, e, f, g, h, i);

					return _getCreateSingleModelFunction().apply(t);
				}));

		return this;
	}

	@Override
	public Routes<T> build() {
		return _routesImpl;
	}

	private <V extends Identifier> Function<Path, V> _convertIdentifier(
		Class<V> identifierClass) {

		return path -> {
			Optional<? extends Identifier> optional = _identifierFunction.apply(
				identifierClass, path);

			return optional.map(
				convertedIdentifier -> (V)convertedIdentifier
			).orElseThrow(
				() -> new MustHavePathIdentifierMapper(identifierClass)
			);
		};
	}

	private <V extends Identifier> Function<Identifier, V>
		_getCollectionIdentifierFunction(Class<V> identifierClass) {

		return identifier -> {
			Class<? extends Identifier> clazz = identifier.getClass();

			if (!identifierClass.isAssignableFrom(clazz)) {
				throw new MustUseSameIdentifier(clazz, identifierClass);
			}

			return (V)identifier;
		};
	}

	private Function<T, SingleModel<T>> _getCreateSingleModelFunction() {
		return t -> new SingleModel<>(t, _modelClass);
	}

	private <V> V _provideClass(Class<V> clazz) {
		Optional<?> optional = _provideClassFunction.apply(clazz);

		return optional.map(
			provided -> (V)provided
		).orElseThrow(
			() -> new MustHaveProvider(clazz)
		);
	}

	private final BiFunction<Class<? extends Identifier>, Path,
		Optional<? extends Identifier>> _identifierFunction;
	private final Class<T> _modelClass;
	private final Function<Class<?>, Optional<?>> _provideClassFunction;
	private final RoutesImpl<T> _routesImpl = new RoutesImpl<>();
	private final Class<U> _singleModelIdentifierClass;

}