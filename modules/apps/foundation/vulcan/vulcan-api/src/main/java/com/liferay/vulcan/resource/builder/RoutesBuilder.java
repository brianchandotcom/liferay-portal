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

package com.liferay.vulcan.resource.builder;

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
import com.liferay.vulcan.resource.Routes;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Use instances of this builder to create {@link Routes} of a {@link
 * com.liferay.vulcan.resource.Resource}.
 *
 * @author Alejandro Hernández
 */
@SuppressWarnings("unused")
public interface RoutesBuilder<T> {

	/**
	 * Constructs the <code>Routes</code> instance with the information provided
	 * to the builder.
	 *
	 * @return the <code>Routes</code> instance.
	 */
	public Routes<T> build();

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  biFunction the function that will be used to calculate the single
	 *         model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <U, A> RoutesBuilder<T> collectionItem(
		BiFunction<U, A, T> biFunction, Class<U> identifierClass,
		Class<A> aClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  decaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @param  fClass the class of the seventh parameter of the single model
	 *         function.
	 * @param  gClass the class of the eighth parameter of the single model
	 *         function.
	 * @param  hClass the class of the ninth parameter of the single model
	 *         function.
	 * @param  iClass the class of the tenth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <U, A, B, C, D, E, F, G, H, I> RoutesBuilder<T> collectionItem(
		DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
		Class<G> gClass, Class<H> hClass, Class<I> iClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  enneaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @param  fClass the class of the seventh parameter of the single model
	 *         function.
	 * @param  gClass the class of the eighth parameter of the single model
	 *         function.
	 * @param  hClass the class of the ninth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <U, A, B, C, D, E, F, G, H> RoutesBuilder<T> collectionItem(
		EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
		Class<G> gClass, Class<H> hClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  function the function that will be used to calculate the single
	 *         model.
	 * @return the updated builder.
	 */
	public <U> RoutesBuilder<T> collectionItem(
		Function<U, T> function, Class<U> identifierClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  heptaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @param  fClass the class of the seventh parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <U, A, B, C, D, E, F> RoutesBuilder<T> collectionItem(
		HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  hexaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <U, A, B, C, D, E> RoutesBuilder<T> collectionItem(
		HexaFunction<U, A, B, C, D, E, T> hexaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  octaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @param  fClass the class of the seventh parameter of the single model
	 *         function.
	 * @param  gClass the class of the eighth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <U, A, B, C, D, E, F, G> RoutesBuilder<T> collectionItem(
		OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction,
		Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
		Class<G> gClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  pentaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <U, A, B, C, D> RoutesBuilder<T> collectionItem(
		PentaFunction<U, A, B, C, D, T> pentaFunction, Class<U> identifierClass,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  tetraFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <U, A, B, C> RoutesBuilder<T> collectionItem(
		TetraFunction<U, A, B, C, T> tetraFunction, Class<U> identifierClass,
		Class<A> aClass, Class<B> bClass, Class<C> cClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  triFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <U, A, B> RoutesBuilder<T> collectionItem(
		TriFunction<U, A, B, T> triFunction, Class<U> identifierClass,
		Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link #filteredCollectionPage(TriFunction,
	 * Class, Class)} instead.
	 *
	 * @param  biFunction the function that will be used to calculate the page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @return the updated builder.
	 */
	public <A> RoutesBuilder<T> collectionPage(
		BiFunction<Pagination, A, PageItems<T>> biFunction, Class<A> aClass);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link
	 * #filteredCollectionPage(UndecaFunction, Class, Class, Class, Class,
	 * Class, Class, Class, Class, Class, Class)} instead.
	 *
	 * @param  decaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the eighth parameter of the page function.
	 * @param  hClass the class of the ninth parameter of the page function.
	 * @param  iClass the class of the tenth parameter of the page function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T> collectionPage(
		DecaFunction<Pagination, A, B, C, D, E, F, G, H, I, PageItems<T>>
			decaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass,
		Class<H> hClass, Class<I> iClass);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link
	 * #filteredCollectionPage(DecaFunction, Class, Class, Class, Class, Class,
	 * Class, Class, Class, Class)} instead.
	 *
	 * @param  enneaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the eighth parameter of the page function.
	 * @param  hClass the class of the ninth parameter of the page function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T> collectionPage(
		EnneaFunction<Pagination, A, B, C, D, E, F, G, H, PageItems<T>>
			enneaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass,
		Class<H> hClass);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link #filteredCollectionPage(BiFunction,
	 * Class)} instead.
	 *
	 * @param  function the function that will be used to calculate the page.
	 * @return the updated builder.
	 */
	public RoutesBuilder<T> collectionPage(
		Function<Pagination, PageItems<T>> function);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link
	 * #filteredCollectionPage(OctaFunction, Class, Class, Class, Class, Class,
	 * Class, Class)} instead.
	 *
	 * @param  heptaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E, F> RoutesBuilder<T> collectionPage(
		HeptaFunction<Pagination, A, B, C, D, E, F, PageItems<T>> heptaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link
	 * #filteredCollectionPage(HeptaFunction, Class, Class, Class, Class, Class,
	 * Class)} instead.
	 *
	 * @param  hexaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E> RoutesBuilder<T> collectionPage(
		HexaFunction<Pagination, A, B, C, D, E, PageItems<T>> hexaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link
	 * #filteredCollectionPage(EnneaFunction, Class, Class, Class, Class, Class,
	 * Class, Class, Class)} instead.
	 *
	 * @param  octaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the eighth parameter of the page function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E, F, G> RoutesBuilder<T> collectionPage(
		OctaFunction<Pagination, A, B, C, D, E, F, G, PageItems<T>>
			octaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link
	 * #filteredCollectionPage(HexaFunction, Class, Class, Class, Class, Class)}
	 * instead.
	 *
	 * @param  pentaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @return the updated builder.
	 */
	public <A, B, C, D> RoutesBuilder<T> collectionPage(
		PentaFunction<Pagination, A, B, C, D, PageItems<T>> pentaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link
	 * #filteredCollectionPage(PentaFunction, Class, Class, Class, Class)}
	 * instead.
	 *
	 * @param  tetraFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @return the updated builder.
	 */
	public <A, B, C> RoutesBuilder<T> collectionPage(
		TetraFunction<Pagination, A, B, C, PageItems<T>> tetraFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass);

	/**
	 * Adds a route to a collection page function. Don't use this method for a
	 * filtered collection page, use {@link
	 * #filteredCollectionPage(TetraFunction, Class, Class, Class)} instead.
	 *
	 * @param  triFunction the function that will be used to calculate the page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @return the updated builder.
	 */
	public <A, B> RoutesBuilder<T> collectionPage(
		TriFunction<Pagination, A, B, PageItems<T>> triFunction,
		Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  function the function that will be used to calculate the page.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType> RoutesBuilder<T>
		filteredCollectionPage(
			BiFunction<Q, Pagination, PageItems<T>> function,
			Class<Q> filterClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  decaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the third parameter of the page function.
	 * @param  bClass the class of the fourth parameter of the page function.
	 * @param  cClass the class of the fifth parameter of the page function.
	 * @param  dClass the class of the sixth parameter of the page function.
	 * @param  eClass the class of the seventh parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the ninth parameter of the page function.
	 * @param  hClass the class of the tenth parameter of the page function.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType, A, B, C, D, E, F, G, H>
		RoutesBuilder<T> filteredCollectionPage(
			DecaFunction<Q, Pagination, A, B, C, D, E, F, G, H, PageItems<T>>
				decaFunction, Class<Q> filterClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass, Class<G> gClass, Class<H> hClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  enneaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the third parameter of the page function.
	 * @param  bClass the class of the fourth parameter of the page function.
	 * @param  cClass the class of the fifth parameter of the page function.
	 * @param  dClass the class of the sixth parameter of the page function.
	 * @param  eClass the class of the seventh parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the ninth parameter of the page function.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType, A, B, C, D, E, F, G>
		RoutesBuilder<T> filteredCollectionPage(
			EnneaFunction<Q, Pagination, A, B, C, D, E, F, G, PageItems<T>>
				enneaFunction, Class<Q> filterClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass, Class<G> gClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  heptaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the third parameter of the page function.
	 * @param  bClass the class of the fourth parameter of the page function.
	 * @param  cClass the class of the fifth parameter of the page function.
	 * @param  dClass the class of the sixth parameter of the page function.
	 * @param  eClass the class of the seventh parameter of the page function.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType, A, B, C, D, E> RoutesBuilder<T>
		filteredCollectionPage(
			HeptaFunction<Q, Pagination, A, B, C, D, E, PageItems<T>>
				heptaFunction, Class<Q> filterClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  hexaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the third parameter of the page function.
	 * @param  bClass the class of the fourth parameter of the page function.
	 * @param  cClass the class of the fifth parameter of the page function.
	 * @param  dClass the class of the sixth parameter of the page function.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType, A, B, C, D> RoutesBuilder<T>
		filteredCollectionPage(
			HexaFunction<Q, Pagination, A, B, C, D, PageItems<T>> hexaFunction,
			Class<Q> filterClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  octaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the third parameter of the page function.
	 * @param  bClass the class of the fourth parameter of the page function.
	 * @param  cClass the class of the fifth parameter of the page function.
	 * @param  dClass the class of the sixth parameter of the page function.
	 * @param  eClass the class of the seventh parameter of the page function.
	 * @param  fClass the class of the eighth parameter of the page function.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType, A, B, C, D, E, F> RoutesBuilder<T>
		filteredCollectionPage(
			OctaFunction<Q, Pagination, A, B, C, D, E, F, PageItems<T>>
				octaFunction, Class<Q> filterClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  pentaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the third parameter of the page function.
	 * @param  bClass the class of the fourth parameter of the page function.
	 * @param  cClass the class of the fifth parameter of the page function.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType, A, B, C> RoutesBuilder<T>
		filteredCollectionPage(
			PentaFunction<Q, Pagination, A, B, C, PageItems<T>> pentaFunction,
			Class<Q> filterClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  tetraFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the third parameter of the page function.
	 * @param  bClass the class of the fourth parameter of the page function.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType, A, B> RoutesBuilder<T>
		filteredCollectionPage(
			TetraFunction<Q, Pagination, A, B, PageItems<T>> tetraFunction,
			Class<Q> filterClass, Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  triFunction the function that will be used to calculate the page.
	 * @param  aClass the class of the third parameter of the page function.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType, A> RoutesBuilder<T>
		filteredCollectionPage(
			TriFunction<Q, Pagination, A, PageItems<T>> triFunction,
			Class<Q> filterClass, Class<A> aClass);

	/**
	 * Adds a route to a filtered collection page function.
	 *
	 * @param  undecaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the third parameter of the page function.
	 * @param  bClass the class of the fourth parameter of the page function.
	 * @param  cClass the class of the fifth parameter of the page function.
	 * @param  dClass the class of the sixth parameter of the page function.
	 * @param  eClass the class of the seventh parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the ninth parameter of the page function.
	 * @param  hClass the class of the tenth parameter of the page function.
	 * @param  iClass the class of the eleventh parameter of the page function.
	 * @return the updated builder.
	 */
	public <Q extends QueryParamFilterType, A, B, C, D, E, F, G, H, I>
		RoutesBuilder<T> filteredCollectionPage(
			UndecaFunction<Q, Pagination, A, B, C, D, E, F, G, H, I,
				PageItems<T>> undecaFunction, Class<Q> filterClass,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
			Class<I> iClass);

}