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

package com.liferay.vulcan.representor;

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

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 */
public interface RoutesBuilder<T> {

	public <A> SingleStep<T> collectionPage(
		BiFunction<Pagination, A, PageItems<T>> biFunction, Class<A> aClass);

	public <A, B, C, D, E, F, G, H, I> SingleStep<T> collectionPage(
		DecaFunction<Pagination, A, B, C, D, E, F, G, H, I, PageItems<T>>
			decaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass,
		Class<H> hClass, Class<I> iClass);

	public <A, B, C, D, E, F, G, H> SingleStep<T> collectionPage(
		EnneaFunction<Pagination, A, B, C, D, E, F, G, H, PageItems<T>>
			enneaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass,
		Class<H> hClass);

	public SingleStep<T> collectionPage(
		Function<Pagination, PageItems<T>> function);

	public <A, B, C, D, E, F> SingleStep<T> collectionPage(
		HeptaFunction<Pagination, A, B, C, D, E, F, PageItems<T>> heptaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass);

	public <A, B, C, D, E> SingleStep<T> collectionPage(
		HexaFunction<Pagination, A, B, C, D, E, PageItems<T>> hexaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass);

	public <A, B, C, D, E, F, G> SingleStep<T> collectionPage(
		OctaFunction<Pagination, A, B, C, D, E, F, G, PageItems<T>>
			octaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass);

	public <A, B, C, D> SingleStep<T> collectionPage(
		PentaFunction<Pagination, A, B, C, D, PageItems<T>> pentaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass);

	public <A, B, C> SingleStep<T> collectionPage(
		TetraFunction<Pagination, A, B, C, PageItems<T>> tetraFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass);

	public <A, B> SingleStep<T> collectionPage(
		TriFunction<Pagination, A, B, PageItems<T>> triFunction,
		Class<A> aClass, Class<B> bClass);

	public interface SingleStep<T> {

		public <U, A> Routes<T> collectionItem(
			BiFunction<U, A, T> biFunction, Class<U> identifierClass,
			Class<A> aClass);

		public <U, A, B, C, D, E, F, G, H, I> Routes<T> collectionItem(
			DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass, Class<I> iClass);

		public <U, A, B, C, D, E, F, G, H> Routes<T> collectionItem(
			EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass);

		public <U> Routes<T> collectionItem(
			Function<U, T> function, Class<U> identifierClass);

		public <U, A, B, C, D, E, F> Routes<T> collectionItem(
			HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass);

		public <U, A, B, C, D, E> Routes<T> collectionItem(
			HexaFunction<U, A, B, C, D, E, T> hexaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass);

		public <U, A, B, C, D, E, F, G> Routes<T> collectionItem(
			OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass);

		public <U, A, B, C, D> Routes<T> collectionItem(
			PentaFunction<U, A, B, C, D, T> pentaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass);

		public <U, A, B, C> Routes<T> collectionItem(
			TetraFunction<U, A, B, C, T> tetraFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass);

		public <U, A, B> Routes<T> collectionItem(
			TriFunction<U, A, B, T> triFunction, Class<U> identifierClass,
			Class<A> aClass, Class<B> bClass);

	}

}