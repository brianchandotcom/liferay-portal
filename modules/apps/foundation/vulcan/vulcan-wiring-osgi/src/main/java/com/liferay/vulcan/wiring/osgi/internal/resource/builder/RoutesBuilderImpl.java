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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveConverter;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveFilterProvider;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveProvider;
import com.liferay.vulcan.error.VulcanDeveloperError.MustUseFilteredCollectionPage;
import com.liferay.vulcan.filter.QueryParamFilterType;
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
import com.liferay.vulcan.wiring.osgi.internal.pagination.PageImpl;
import com.liferay.vulcan.wiring.osgi.internal.resource.RoutesImpl;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.ws.rs.BadRequestException;

/**
 * @author Alejandro Hernández
 */
public class RoutesBuilderImpl<T> implements RoutesBuilder<T> {

	public RoutesBuilderImpl(
		Class<T> modelClass, BiFunction<Class<?>, String, ?> convertFunction,
		Function<Class<?>, Optional<?>> provideClassFunction,
		Function<Class<? extends QueryParamFilterType>,
			Optional<? extends QueryParamFilterType>> provideFilterFunction,
		Function<QueryParamFilterType, Optional<String>> getFilterNameFunction,
		String filterName) {

		_modelClass = modelClass;
		_convertFunction = convertFunction;
		_provideClassFunction = provideClassFunction;
		_provideFilterFunction = provideFilterFunction;
		_createSingleModelFunction = t -> new SingleModel<>(t, modelClass);
		_getFilterNameFunction = getFilterNameFunction;
		_filterName = filterName;
	}

	@Override
	public Routes<T> build() {
		return _routesImpl;
	}

	@Override
	public <U, A> RoutesBuilder<T> collectionItem(
		BiFunction<U, A, T> biFunction, Class<U> identifierClass,
		Class<A> aClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(
				id -> {
					A a = _provideClass(aClass);

					return biFunction.apply(id, a);
				}).andThen(_createSingleModelFunction));

		return this;
	}

	@Override
	public <U, A, B, C, D, E, F, G, H, I> RoutesBuilder<T> collectionItem(
		DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
		Class<G> gClass, Class<H> hClass, Class<I> iClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(
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
				}).andThen(_createSingleModelFunction));

		return this;
	}

	@Override
	public <U, A, B, C, D, E, F, G, H> RoutesBuilder<T> collectionItem(
		EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
		Class<G> gClass, Class<H> hClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(
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
				}).andThen(_createSingleModelFunction));

		return this;
	}

	@Override
	public <U> RoutesBuilder<T> collectionItem(
		Function<U, T> function, Class<U> identifierClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(function).andThen(
				_createSingleModelFunction));

		return this;
	}

	@Override
	public <U, A, B, C, D, E, F> RoutesBuilder<T> collectionItem(
		HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(
				id -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);

					return heptaFunction.apply(id, a, b, c, d, e, f);
				}).andThen(_createSingleModelFunction));

		return this;
	}

	@Override
	public <U, A, B, C, D, E> RoutesBuilder<T> collectionItem(
		HexaFunction<U, A, B, C, D, E, T> hexaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(
				id -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);

					return hexaFunction.apply(id, a, b, c, d, e);
				}).andThen(_createSingleModelFunction));

		return this;
	}

	@Override
	public <U, A, B, C, D, E, F, G> RoutesBuilder<T> collectionItem(
		OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
		Class<G> gClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(
				id -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);

					return octaFunction.apply(id, a, b, c, d, e, f, g);
				}).andThen(_createSingleModelFunction));

		return this;
	}

	@Override
	public <U, A, B, C, D> RoutesBuilder<T> collectionItem(
		PentaFunction<U, A, B, C, D, T> pentaFunction, Class<U> identifierClass,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(
				id -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);

					return pentaFunction.apply(id, a, b, c, d);
				}).andThen(_createSingleModelFunction));

		return this;
	}

	@Override
	public <U, A, B, C> RoutesBuilder<T> collectionItem(
		TetraFunction<U, A, B, C, T> tetraFunction, Class<U> identifierClass,
		Class<A> aClass, Class<B> bClass, Class<C> cClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(
				id -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);

					return tetraFunction.apply(id, a, b, c);
				}).andThen(_createSingleModelFunction));

		return this;
	}

	@Override
	public <U, A, B> RoutesBuilder<T> collectionItem(
		TriFunction<U, A, B, T> triFunction, Class<U> identifierClass,
		Class<A> aClass, Class<B> bClass) {

		Function<String, U> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setSingleModelFunction(
			identifierFunction.andThen(
				id -> {
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);

					return triFunction.apply(id, a, b);
				}).andThen(_createSingleModelFunction));

		return this;
	}

	@Override
	public <A> RoutesBuilder<T> collectionPage(
		BiFunction<Pagination, A, PageItems<T>> biFunction, Class<A> aClass) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);

				PageItems<T> pageItems = biFunction.apply(pagination, a);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T> collectionPage(
		DecaFunction<Pagination, A, B, C, D, E, F, G, H, I,
			PageItems<T>> decaFunction, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
		Class<G> gClass, Class<H> hClass, Class<I> iClass) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);
				H h = _provideClass(hClass);
				I i = _provideClass(iClass);

				PageItems<T> pageItems = decaFunction.apply(
					pagination, a, b, c, d, e, f, g, h, i);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T> collectionPage(
		EnneaFunction<Pagination, A, B, C, D, E, F, G, H, PageItems<T>>
			enneaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass,
		Class<H> hClass) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);
				H h = _provideClass(hClass);

				PageItems<T> pageItems = enneaFunction.apply(
					pagination, a, b, c, d, e, f, g, h);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public RoutesBuilder<T> collectionPage(
		Function<Pagination, PageItems<T>> function) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);

				PageItems<T> pageItems = function.apply(pagination);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <A, B, C, D, E, F> RoutesBuilder<T> collectionPage(
		HeptaFunction<Pagination, A, B, C, D, E, F, PageItems<T>> heptaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);

				PageItems<T> pageItems = heptaFunction.apply(
					pagination, a, b, c, d, e, f);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <A, B, C, D, E> RoutesBuilder<T> collectionPage(
		HexaFunction<Pagination, A, B, C, D, E, PageItems<T>> hexaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);

				PageItems<T> pageItems = hexaFunction.apply(
					pagination, a, b, c, d, e);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G> RoutesBuilder<T> collectionPage(
		OctaFunction<Pagination, A, B, C, D, E, F, G, PageItems<T>>
			octaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);

				PageItems<T> pageItems = octaFunction.apply(
					pagination, a, b, c, d, e, f, g);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <A, B, C, D> RoutesBuilder<T> collectionPage(
		PentaFunction<Pagination, A, B, C, D, PageItems<T>> pentaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);

				PageItems<T> pageItems = pentaFunction.apply(
					pagination, a, b, c, d);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <A, B, C> RoutesBuilder<T> collectionPage(
		TetraFunction<Pagination, A, B, C, PageItems<T>> tetraFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);

				PageItems<T> pageItems = tetraFunction.apply(
					pagination, a, b, c);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <A, B> RoutesBuilder<T> collectionPage(
		TriFunction<Pagination, A, B, PageItems<T>> triFunction,
		Class<A> aClass, Class<B> bClass) {

		if (_filterName != null) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);

				PageItems<T> pageItems = triFunction.apply(pagination, a, b);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType> RoutesBuilder<T>
		filteredCollectionPage(
			BiFunction<Q, Pagination, PageItems<T>> function,
			Class<Q> filterClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);

				PageItems<T> pageItems = function.apply(
					queryParamFilterType, pagination);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType, A, B, C, D, E, F, G, H>
		RoutesBuilder<T> filteredCollectionPage(
			DecaFunction<Q, Pagination, A, B, C, D, E, F, G, H,
				PageItems<T>> decaFunction, Class<Q> filterClass,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass,
			Class<H> hClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
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
					queryParamFilterType, pagination, a, b, c, d, e, f, g, h);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType, A, B, C, D, E, F, G>
		RoutesBuilder<T> filteredCollectionPage(
			EnneaFunction<Q, Pagination, A, B, C, D, E, F, G,
				PageItems<T>> enneaFunction, Class<Q> filterClass,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);

				PageItems<T> pageItems = enneaFunction.apply(
					queryParamFilterType, pagination, a, b, c, d, e, f, g);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType, A, B, C, D, E> RoutesBuilder<T>
		filteredCollectionPage(
			HeptaFunction<Q, Pagination, A, B, C, D, E, PageItems<T>>
				heptaFunction, Class<Q> filterClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);

				PageItems<T> pageItems = heptaFunction.apply(
					queryParamFilterType, pagination, a, b, c, d, e);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType, A, B, C, D> RoutesBuilder<T>
		filteredCollectionPage(
			HexaFunction<Q, Pagination, A, B, C, D, PageItems<T>> hexaFunction,
			Class<Q> filterClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);

				PageItems<T> pageItems = hexaFunction.apply(
					queryParamFilterType, pagination, a, b, c, d);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType, A, B, C, D, E, F> RoutesBuilder<T>
		filteredCollectionPage(
			OctaFunction<Q, Pagination, A, B, C, D, E, F, PageItems<T>>
				octaFunction, Class<Q> filterClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);

				PageItems<T> pageItems = octaFunction.apply(
					queryParamFilterType, pagination, a, b, c, d, e, f);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType, A, B, C> RoutesBuilder<T>
		filteredCollectionPage(
			PentaFunction<Q, Pagination, A, B, C, PageItems<T>> pentaFunction,
			Class<Q> filterClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);

				PageItems<T> pageItems = pentaFunction.apply(
					queryParamFilterType, pagination, a, b, c);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType, A, B> RoutesBuilder<T>
		filteredCollectionPage(
			TetraFunction<Q, Pagination, A, B, PageItems<T>> tetraFunction,
			Class<Q> filterClass, Class<A> aClass, Class<B> bClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);

				PageItems<T> pageItems = tetraFunction.apply(
					queryParamFilterType, pagination, a, b);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType, A> RoutesBuilder<T>
		filteredCollectionPage(
			TriFunction<Q, Pagination, A, PageItems<T>> triFunction,
			Class<Q> filterClass, Class<A> aClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);

				PageItems<T> pageItems = triFunction.apply(
					queryParamFilterType, pagination, a);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	@Override
	public <Q extends QueryParamFilterType, A, B, C, D, E, F, G, H, I>
		RoutesBuilder<T> filteredCollectionPage(
			UndecaFunction<Q, Pagination, A, B, C, D, E, F, G, H, I,
				PageItems<T>> undecaFunction, Class<Q> filterClass,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
			Class<I> iClass) {

		if (_filterName == null) {
			return this;
		}

		Q queryParamFilterType = _provideFilter(filterClass);

		if (_isNotActiveFilter(queryParamFilterType)) {
			return this;
		}

		_routesImpl.setPageSupplier(
			() -> {
				Pagination pagination = _provideClass(Pagination.class);
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);
				H h = _provideClass(hClass);
				I i = _provideClass(iClass);

				PageItems<T> pageItems = undecaFunction.apply(
					queryParamFilterType, pagination, a, b, c, d, e, f, g, h,
					i);

				return new PageImpl<>(
					_modelClass, pageItems.getItems(),
					pagination.getItemsPerPage(), pagination.getPageNumber(),
					pageItems.getTotalCount());
			});

		return this;
	}

	private <U> Function<String, U> _convertIdentifier(
		Class<U> identifierClass) {

		if (identifierClass.isAssignableFrom(Long.class)) {
			return id -> {
				Long longId = GetterUtil.getLong(id);

				if (longId == GetterUtil.DEFAULT_LONG) {
					throw new BadRequestException();
				}

				return (U)longId;
			};
		}
		else if (identifierClass.isAssignableFrom(Integer.class)) {
			return id -> {
				Integer integerId = GetterUtil.getInteger(id);

				if (integerId == GetterUtil.DEFAULT_INTEGER) {
					throw new BadRequestException();
				}

				return (U)integerId;
			};
		}
		else if (identifierClass.isAssignableFrom(String.class)) {
			return id -> (U)id;
		}
		else {
			return id -> {
				Optional<U> optional = (Optional<U>)_convertFunction.apply(
					identifierClass, id);

				return optional.orElseThrow(
					() -> new MustHaveConverter(identifierClass));
			};
		}
	}

	private Boolean _isNotActiveFilter(
		QueryParamFilterType queryParamFilterType) {

		Optional<String> optional = _getFilterNameFunction.apply(
			queryParamFilterType);

		String filterName = optional.orElseThrow(
			() -> new MustHaveFilterProvider(queryParamFilterType));

		if (filterName.equals(_filterName)) {
			return false;
		}
		else {
			return true;
		}
	}

	private <U> U _provideClass(Class<U> clazz) {
		if (QueryParamFilterType.class.isAssignableFrom(clazz)) {
			throw new MustUseFilteredCollectionPage();
		}

		Optional<U> optional = (Optional<U>)_provideClassFunction.apply(clazz);

		return optional.orElseThrow(() -> new MustHaveProvider(clazz));
	}

	private <U extends QueryParamFilterType> U _provideFilter(Class<U> clazz) {
		Optional<U> optional = (Optional<U>)_provideFilterFunction.apply(clazz);

		return optional.orElseThrow(() -> new MustHaveFilterProvider(clazz));
	}

	private final BiFunction<Class<?>, String, ?> _convertFunction;
	private final Function<T, SingleModel<T>> _createSingleModelFunction;
	private final String _filterName;
	private final Function<QueryParamFilterType, Optional<String>>
		_getFilterNameFunction;
	private final Class<T> _modelClass;
	private final Function<Class<?>, Optional<?>> _provideClassFunction;
	private final Function<Class<? extends QueryParamFilterType>,
		Optional<? extends QueryParamFilterType>> _provideFilterFunction;
	private final RoutesImpl<T> _routesImpl = new RoutesImpl<>();

}