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

package com.liferay.vulcan.wiring.osgi.internal;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveProvider;
import com.liferay.vulcan.functions.DecaFunction;
import com.liferay.vulcan.functions.EnneaFunction;
import com.liferay.vulcan.functions.HeptaFunction;
import com.liferay.vulcan.functions.HexaFunction;
import com.liferay.vulcan.functions.OctaFunction;
import com.liferay.vulcan.functions.PentaFunction;
import com.liferay.vulcan.functions.TetraFunction;
import com.liferay.vulcan.functions.TriFunction;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.representor.Routes;
import com.liferay.vulcan.representor.RoutesBuilder;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.ws.rs.BadRequestException;

/**
 * @author Alejandro Hernández
 */
public class RoutesBuilderImpl<T> implements RoutesBuilder<T> {

	@Override
	public <A> SingleStep<T> collectionPage(
		BiFunction<Pagination, A, PageItems<T>> biFunction, Class<A> aClass) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);
			A a = _provide(aClass, provideFunction);

			return biFunction.apply(pagination, a);
		};

		return new SingleStepImpl();
	}

	@Override
	public <A, B, C, D, E, F, G, H, I> SingleStep<T> collectionPage(
		DecaFunction<Pagination, A, B, C, D, E, F, G, H, I,
			PageItems<T>> decaFunction, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
		Class<G> gClass, Class<H> hClass, Class<I> iClass) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);
			A a = _provide(aClass, provideFunction);
			B b = _provide(bClass, provideFunction);
			C c = _provide(cClass, provideFunction);
			D d = _provide(dClass, provideFunction);
			E e = _provide(eClass, provideFunction);
			F f = _provide(fClass, provideFunction);
			G g = _provide(gClass, provideFunction);
			H h = _provide(hClass, provideFunction);
			I i = _provide(iClass, provideFunction);

			return decaFunction.apply(pagination, a, b, c, d, e, f, g, h, i);
		};

		return new SingleStepImpl();
	}

	@Override
	public <A, B, C, D, E, F, G, H> SingleStep<T> collectionPage(
		EnneaFunction<Pagination, A, B, C, D, E, F, G, H, PageItems<T>>
			enneaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass,
		Class<H> hClass) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);
			A a = _provide(aClass, provideFunction);
			B b = _provide(bClass, provideFunction);
			C c = _provide(cClass, provideFunction);
			D d = _provide(dClass, provideFunction);
			E e = _provide(eClass, provideFunction);
			F f = _provide(fClass, provideFunction);
			G g = _provide(gClass, provideFunction);
			H h = _provide(hClass, provideFunction);

			return enneaFunction.apply(pagination, a, b, c, d, e, f, g, h);
		};

		return new SingleStepImpl();
	}

	@Override
	public SingleStep<T> collectionPage(
		Function<Pagination, PageItems<T>> function) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);

			return function.apply(pagination);
		};

		return new SingleStepImpl();
	}

	@Override
	public <A, B, C, D, E, F> SingleStep<T> collectionPage(
		HeptaFunction<Pagination, A, B, C, D, E, F, PageItems<T>> heptaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);
			A a = _provide(aClass, provideFunction);
			B b = _provide(bClass, provideFunction);
			C c = _provide(cClass, provideFunction);
			D d = _provide(dClass, provideFunction);
			E e = _provide(eClass, provideFunction);
			F f = _provide(fClass, provideFunction);

			return heptaFunction.apply(pagination, a, b, c, d, e, f);
		};

		return new SingleStepImpl();
	}

	@Override
	public <A, B, C, D, E> SingleStep<T> collectionPage(
		HexaFunction<Pagination, A, B, C, D, E, PageItems<T>> hexaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);
			A a = _provide(aClass, provideFunction);
			B b = _provide(bClass, provideFunction);
			C c = _provide(cClass, provideFunction);
			D d = _provide(dClass, provideFunction);
			E e = _provide(eClass, provideFunction);

			return hexaFunction.apply(pagination, a, b, c, d, e);
		};

		return new SingleStepImpl();
	}

	@Override
	public <A, B, C, D, E, F, G> SingleStep<T> collectionPage(
		OctaFunction<Pagination, A, B, C, D, E, F, G, PageItems<T>>
			octaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);
			A a = _provide(aClass, provideFunction);
			B b = _provide(bClass, provideFunction);
			C c = _provide(cClass, provideFunction);
			D d = _provide(dClass, provideFunction);
			E e = _provide(eClass, provideFunction);
			F f = _provide(fClass, provideFunction);
			G g = _provide(gClass, provideFunction);

			return octaFunction.apply(pagination, a, b, c, d, e, f, g);
		};

		return new SingleStepImpl();
	}

	@Override
	public <A, B, C, D> SingleStep<T> collectionPage(
		PentaFunction<Pagination, A, B, C, D, PageItems<T>> pentaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);
			A a = _provide(aClass, provideFunction);
			B b = _provide(bClass, provideFunction);
			C c = _provide(cClass, provideFunction);
			D d = _provide(dClass, provideFunction);

			return pentaFunction.apply(pagination, a, b, c, d);
		};

		return new SingleStepImpl();
	}

	@Override
	public <A, B, C> SingleStep<T> collectionPage(
		TetraFunction<Pagination, A, B, C, PageItems<T>> tetraFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);
			A a = _provide(aClass, provideFunction);
			B b = _provide(bClass, provideFunction);
			C c = _provide(cClass, provideFunction);

			return tetraFunction.apply(pagination, a, b, c);
		};

		return new SingleStepImpl();
	}

	@Override
	public <A, B> SingleStep<T> collectionPage(
		TriFunction<Pagination, A, B, PageItems<T>> triFunction,
		Class<A> aClass, Class<B> bClass) {

		_pageItemsFunction = provideFunction -> {
			Pagination pagination = _provide(Pagination.class, provideFunction);
			A a = _provide(aClass, provideFunction);
			B b = _provide(bClass, provideFunction);

			return triFunction.apply(pagination, a, b);
		};

		return new SingleStepImpl();
	}

	private <U> U _provide(
		Class<U> clazz, Function<Class<?>, Optional<?>> provideFunction) {

		Optional<U> optional = (Optional<U>)provideFunction.apply(clazz);

		return optional.orElseThrow(
			() -> new MustHaveProvider(Pagination.class));
	}

	private Function<Function<Class<?>, Optional<?>>, PageItems<T>>
		_pageItemsFunction;

	private class SingleStepImpl implements SingleStep<T> {

		@Override
		public <U, A> Routes<T> collectionItem(
			BiFunction<U, A, T> biFunction, Class<U> identifierClass,
			Class<A> aClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				id -> {
					A a = _provide(aClass, provideFunction);

					return biFunction.apply(id, a);
				}
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
		}

		@Override
		public <U, A, B, C, D, E, F, G, H, I> Routes<T> collectionItem(
			DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass, Class<I> iClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				id -> {
					A a = _provide(aClass, provideFunction);
					B b = _provide(bClass, provideFunction);
					C c = _provide(cClass, provideFunction);
					D d = _provide(dClass, provideFunction);
					E e = _provide(eClass, provideFunction);
					F f = _provide(fClass, provideFunction);
					G g = _provide(gClass, provideFunction);
					H h = _provide(hClass, provideFunction);
					I i = _provide(iClass, provideFunction);

					return decaFunction.apply(id, a, b, c, d, e, f, g, h, i);
				}
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
		}

		@Override
		public <U, A, B, C, D, E, F, G, H> Routes<T> collectionItem(
			EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				id -> {
					A a = _provide(aClass, provideFunction);
					B b = _provide(bClass, provideFunction);
					C c = _provide(cClass, provideFunction);
					D d = _provide(dClass, provideFunction);
					E e = _provide(eClass, provideFunction);
					F f = _provide(fClass, provideFunction);
					G g = _provide(gClass, provideFunction);
					H h = _provide(hClass, provideFunction);

					return enneaFunction.apply(id, a, b, c, d, e, f, g, h);
				}
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
		}

		@Override
		public <U> Routes<T> collectionItem(
			Function<U, T> function, Class<U> identifierClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				function
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
		}

		@Override
		public <U, A, B, C, D, E, F> Routes<T> collectionItem(
			HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				id -> {
					A a = _provide(aClass, provideFunction);
					B b = _provide(bClass, provideFunction);
					C c = _provide(cClass, provideFunction);
					D d = _provide(dClass, provideFunction);
					E e = _provide(eClass, provideFunction);
					F f = _provide(fClass, provideFunction);

					return heptaFunction.apply(id, a, b, c, d, e, f);
				}
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
		}

		@Override
		public <U, A, B, C, D, E> Routes<T> collectionItem(
			HexaFunction<U, A, B, C, D, E, T> hexaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				id -> {
					A a = _provide(aClass, provideFunction);
					B b = _provide(bClass, provideFunction);
					C c = _provide(cClass, provideFunction);
					D d = _provide(dClass, provideFunction);
					E e = _provide(eClass, provideFunction);

					return hexaFunction.apply(id, a, b, c, d, e);
				}
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
		}

		@Override
		public <U, A, B, C, D, E, F, G> Routes<T> collectionItem(
			OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				id -> {
					A a = _provide(aClass, provideFunction);
					B b = _provide(bClass, provideFunction);
					C c = _provide(cClass, provideFunction);
					D d = _provide(dClass, provideFunction);
					E e = _provide(eClass, provideFunction);
					F f = _provide(fClass, provideFunction);
					G g = _provide(gClass, provideFunction);

					return octaFunction.apply(id, a, b, c, d, e, f, g);
				}
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
		}

		@Override
		public <U, A, B, C, D> Routes<T> collectionItem(
			PentaFunction<U, A, B, C, D, T> pentaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				id -> {
					A a = _provide(aClass, provideFunction);
					B b = _provide(bClass, provideFunction);
					C c = _provide(cClass, provideFunction);
					D d = _provide(dClass, provideFunction);

					return pentaFunction.apply(id, a, b, c, d);
				}
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
		}

		@Override
		public <U, A, B, C> Routes<T> collectionItem(
			TetraFunction<U, A, B, C, T> tetraFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				id -> {
					A a = _provide(aClass, provideFunction);
					B b = _provide(bClass, provideFunction);
					C c = _provide(cClass, provideFunction);

					return tetraFunction.apply(id, a, b, c);
				}
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
		}

		@Override
		public <U, A, B> Routes<T> collectionItem(
			TriFunction<U, A, B, T> triFunction, Class<U> identifierClass,
			Class<A> aClass, Class<B> bClass) {

			_modelFunction = provideFunction -> _convertIdentifier(
				identifierClass
			).andThen(
				id -> {
					A a = _provide(aClass, provideFunction);
					B b = _provide(bClass, provideFunction);

					return triFunction.apply(id, a, b);
				}
			);

			return new RoutesImpl<>(_pageItemsFunction, _modelFunction);
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
				throw new RuntimeException();
			}
		}

		private Function<Function<Class<?>, Optional<?>>, Function<String, T>>
			_modelFunction;

	}

}