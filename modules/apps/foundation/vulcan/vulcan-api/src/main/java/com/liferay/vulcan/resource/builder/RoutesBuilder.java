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

import aQute.bnd.annotation.ProviderType;

import com.liferay.vulcan.consumer.DecaConsumer;
import com.liferay.vulcan.consumer.EnneaConsumer;
import com.liferay.vulcan.consumer.HeptaConsumer;
import com.liferay.vulcan.consumer.HexaConsumer;
import com.liferay.vulcan.consumer.OctaConsumer;
import com.liferay.vulcan.consumer.PentaConsumer;
import com.liferay.vulcan.consumer.TetraConsumer;
import com.liferay.vulcan.consumer.TriConsumer;
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
import com.liferay.vulcan.resource.identifier.Identifier;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Use instances of this builder to create {@link Routes} of a {@link
 * com.liferay.vulcan.resource.CollectionResource}.
 *
 * @author Alejandro Hernández
 * @review
 */
@ProviderType
@SuppressWarnings("unused")
public interface RoutesBuilder<T, U extends Identifier> {

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  biFunction the function that will be used to calculate the page.
	 * @param  identifierClass the class of the identifier.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier> RoutesBuilder<T, U> addCollectionPageGetter(
		BiFunction<Pagination, V, PageItems<T>> biFunction,
		Class<V> identifierClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  decaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the eighth parameter of the page function.
	 * @param  hClass the class of the ninth parameter of the page function.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier, A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		addCollectionPageGetter(
			DecaFunction<Pagination, V, A, B, C, D, E, F, G, H,
				PageItems<T>> decaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  enneaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the eighth parameter of the page function.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier, A, B, C, D, E, F, G> RoutesBuilder<T, U>
		addCollectionPageGetter(
			EnneaFunction<Pagination, V, A, B, C, D, E, F, G, PageItems<T>>
				enneaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  heptaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier, A, B, C, D, E> RoutesBuilder<T, U>
		addCollectionPageGetter(
			HeptaFunction<Pagination, V, A, B, C, D, E, PageItems<T>>
				heptaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  hexaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier, A, B, C, D> RoutesBuilder<T, U>
		addCollectionPageGetter(
			HexaFunction<Pagination, V, A, B, C, D, PageItems<T>> hexaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  octaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier, A, B, C, D, E, F> RoutesBuilder<T, U>
		addCollectionPageGetter(
			OctaFunction<Pagination, V, A, B, C, D, E, F, PageItems<T>>
				octaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  pentaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier, A, B, C> RoutesBuilder<T, U>
		addCollectionPageGetter(
			PentaFunction<Pagination, V, A, B, C, PageItems<T>> pentaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  tetraFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier, A, B> RoutesBuilder<T, U>
		addCollectionPageGetter(
			TetraFunction<Pagination, V, A, B, PageItems<T>> tetraFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a collection page function.
	 * #filteredCollectionPage(TriFunction,
	 *
	 * @param  triFunction the function that will be used to calculate the page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier, A> RoutesBuilder<T, U>
		addCollectionPageGetter(
			TriFunction<Pagination, V, A, PageItems<T>> triFunction,
			Class<V> identifierClass, Class<A> aClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  biFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
	 * @return the updated builder.
	 * @review
	 */
	public <V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			BiFunction<V, Map<String, Object>, T> biFunction,
			Class<V> identifierClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  decaFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
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
	 * @review
	 */
	public <A, B, C, D, E, F, G, H, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			DecaFunction<V, Map<String, Object>, A, B, C, D, E, F, G, H, T>
				decaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  enneaFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
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
	 * @review
	 */
	public <A, B, C, D, E, F, G, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			EnneaFunction<V, Map<String, Object>, A, B, C, D, E, F, G, T>
				enneaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  heptaFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
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
	 * @review
	 */
	public <A, B, C, D, E, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			HeptaFunction<V, Map<String, Object>, A, B, C, D, E, T>
				heptaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  hexaFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			HexaFunction<V, Map<String, Object>, A, B, C, D, T> hexaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  octaFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
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
	 * @review
	 */
	public <A, B, C, D, E, F, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			OctaFunction<V, Map<String, Object>, A, B, C, D, E, F, T>
				octaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  pentaFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			PentaFunction<V, Map<String, Object>, A, B, C, T> pentaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  tetraFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			TetraFunction<V, Map<String, Object>, A, B, T> tetraFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  triFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			TriFunction<V, Map<String, Object>, A, T> triFunction,
			Class<V> identifierClass, Class<A> aClass);

	/**
	 * Adds a route to a single model post function.
	 *
	 * @param  undecaFunction the function that will be used to add the single
	 *         model.
	 * @param  identifierClass the class of the identifier.
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
	 * @review
	 */
	public <A, B, C, D, E, F, G, H, I, V extends Identifier> RoutesBuilder<T, U>
		addCollectionPageItemCreator(
			UndecaFunction<V, Map<String, Object>, A, B, C, D, E, F, G, H, I, T>
				undecaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass, Class<I> iClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  biFunction the function that will be used to calculate the single
	 *         model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @return the updated builder.
	 * @review
	 */
	public <A> RoutesBuilder<T, U> addCollectionPageItemGetter(
		BiFunction<U, A, T> biFunction, Class<A> aClass);

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
	 * @review
	 */
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T, U>
		addCollectionPageItemGetter(
			DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
			Class<I> iClass);

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
	 * @review
	 */
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		addCollectionPageItemGetter(
			EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  function the function that will be used to calculate the single
	 *         model.
	 * @return the updated builder.
	 * @review
	 */
	public RoutesBuilder<T, U> addCollectionPageItemGetter(
		Function<U, T> function);

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
	 * @review
	 */
	public <A, B, C, D, E, F> RoutesBuilder<T, U> addCollectionPageItemGetter(
		HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Class<F> fClass);

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
	 * @review
	 */
	public <A, B, C, D, E> RoutesBuilder<T, U> addCollectionPageItemGetter(
		HexaFunction<U, A, B, C, D, E, T> hexaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass);

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
	 * @review
	 */
	public <A, B, C, D, E, F, G> RoutesBuilder<T, U>
		addCollectionPageItemGetter(
			OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass);

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
	 * @review
	 */
	public <A, B, C, D> RoutesBuilder<T, U> addCollectionPageItemGetter(
		PentaFunction<U, A, B, C, D, T> pentaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass);

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
	 * @review
	 */
	public <A, B, C> RoutesBuilder<T, U> addCollectionPageItemGetter(
		TetraFunction<U, A, B, C, T> tetraFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass);

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
	 * @review
	 */
	public <A, B> RoutesBuilder<T, U> addCollectionPageItemGetter(
		TriFunction<U, A, B, T> triFunction, Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  biConsumer the function that will be used to delete the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the remover function.
	 * @return the updated builder.
	 * @review
	 */
	public <A> RoutesBuilder<T, U> addCollectionPageItemRemover(
		BiConsumer<U, A> biConsumer, Class<A> aClass);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  consumer the function that will be used to delete the single
	 *         model.
	 * @return the updated builder.
	 * @review
	 */
	public RoutesBuilder<T, U> addCollectionPageItemRemover(
		Consumer<U> consumer);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  decaConsumer the function that will be used to delete the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the remover function.
	 * @param  bClass the class of the third parameter of the remover function.
	 * @param  cClass the class of the fourth parameter of the remover function.
	 * @param  dClass the class of the fifth parameter of the remover function.
	 * @param  eClass the class of the sixth parameter of the remover function.
	 * @param  fClass the class of the seventh parameter of the remover
	 *         function.
	 * @param  gClass the class of the eighth parameter of the remover function.
	 * @param  hClass the class of the ninth parameter of the remover function.
	 * @param  iClass the class of the tenth parameter of the remover function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T, U>
		addCollectionPageItemRemover(
			DecaConsumer<U, A, B, C, D, E, F, G, H, I> decaConsumer,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
			Class<I> iClass);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  enneaConsumer the function that will be used to delete the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the remover function.
	 * @param  bClass the class of the third parameter of the remover function.
	 * @param  cClass the class of the fourth parameter of the remover function.
	 * @param  dClass the class of the fifth parameter of the remover function.
	 * @param  eClass the class of the sixth parameter of the remover function.
	 * @param  fClass the class of the seventh parameter of the remover
	 *         function.
	 * @param  gClass the class of the eighth parameter of the remover function.
	 * @param  hClass the class of the ninth parameter of the remover function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		addCollectionPageItemRemover(
			EnneaConsumer<U, A, B, C, D, E, F, G, H> enneaConsumer,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  heptaConsumer the function that will be used to delete the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the remover function.
	 * @param  bClass the class of the third parameter of the remover function.
	 * @param  cClass the class of the fourth parameter of the remover function.
	 * @param  dClass the class of the fifth parameter of the remover function.
	 * @param  eClass the class of the sixth parameter of the remover function.
	 * @param  fClass the class of the seventh parameter of the remover
	 *         function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E, F> RoutesBuilder<T, U> addCollectionPageItemRemover(
		HeptaConsumer<U, A, B, C, D, E, F> heptaConsumer, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Class<F> fClass);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  hexaConsumer the function that will be used to delete the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the remover function.
	 * @param  bClass the class of the third parameter of the remover function.
	 * @param  cClass the class of the fourth parameter of the remover function.
	 * @param  dClass the class of the fifth parameter of the remover function.
	 * @param  eClass the class of the sixth parameter of the remover function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E> RoutesBuilder<T, U> addCollectionPageItemRemover(
		HexaConsumer<U, A, B, C, D, E> hexaConsumer, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  octaConsumer the function that will be used to delete the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the remover function.
	 * @param  bClass the class of the third parameter of the remover function.
	 * @param  cClass the class of the fourth parameter of the remover function.
	 * @param  dClass the class of the fifth parameter of the remover function.
	 * @param  eClass the class of the sixth parameter of the remover function.
	 * @param  fClass the class of the seventh parameter of the remover
	 *         function.
	 * @param  gClass the class of the eighth parameter of the remover function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E, F, G> RoutesBuilder<T, U>
		addCollectionPageItemRemover(
			OctaConsumer<U, A, B, C, D, E, F, G> octaConsumer, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass, Class<G> gClass);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  pentaConsumer the function that will be used to delete the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the remover function.
	 * @param  bClass the class of the third parameter of the remover function.
	 * @param  cClass the class of the fourth parameter of the remover function.
	 * @param  dClass the class of the fifth parameter of the remover function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D> RoutesBuilder<T, U> addCollectionPageItemRemover(
		PentaConsumer<U, A, B, C, D> pentaConsumer, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  tetraConsumer the function that will be used to delete the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the remover function.
	 * @param  bClass the class of the third parameter of the remover function.
	 * @param  cClass the class of the fourth parameter of the remover function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C> RoutesBuilder<T, U> addCollectionPageItemRemover(
		TetraConsumer<U, A, B, C> tetraConsumer, Class<A> aClass,
		Class<B> bClass, Class<C> cClass);

	/**
	 * Adds a route to a collection item remover function.
	 *
	 * @param  triConsumer the function that will be used to delete the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the remover function.
	 * @param  bClass the class of the third parameter of the remover function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B> RoutesBuilder<T, U> addCollectionPageItemRemover(
		TriConsumer<U, A, B> triConsumer, Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  biFunction the function that will be used to update the single
	 *         model.
	 * @return the updated builder.
	 * @review
	 */
	public RoutesBuilder<T, U> addCollectionPageItemUpdater(
		BiFunction<U, Map<String, Object>, T> biFunction);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  decaFunction the function that will be used to update the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the updater function.
	 * @param  bClass the class of the third parameter of the updater function.
	 * @param  cClass the class of the fourth parameter of the updater function.
	 * @param  dClass the class of the fifth parameter of the updater function.
	 * @param  eClass the class of the sixth parameter of the updater function.
	 * @param  fClass the class of the seventh parameter of the updater
	 *         function.
	 * @param  gClass the class of the eighth parameter of the updater function.
	 * @param  hClass the class of the ninth parameter of the updater function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		addCollectionPageItemUpdater(
			DecaFunction<U, Map<String, Object>, A, B, C, D, E, F, G, H, T>
				decaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  enneaFunction the function that will be used to update the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the updater function.
	 * @param  bClass the class of the third parameter of the updater function.
	 * @param  cClass the class of the fourth parameter of the updater function.
	 * @param  dClass the class of the fifth parameter of the updater function.
	 * @param  eClass the class of the sixth parameter of the updater function.
	 * @param  fClass the class of the seventh parameter of the updater
	 *         function.
	 * @param  gClass the class of the eighth parameter of the updater function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E, F, G> RoutesBuilder<T, U>
		addCollectionPageItemUpdater(
			EnneaFunction<U, Map<String, Object>, A, B, C, D, E, F, G, T>
				enneaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  heptaFunction the function that will be used to update the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the updater function.
	 * @param  bClass the class of the third parameter of the updater function.
	 * @param  cClass the class of the fourth parameter of the updater function.
	 * @param  dClass the class of the fifth parameter of the updater function.
	 * @param  eClass the class of the sixth parameter of the updater function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		HeptaFunction<U, Map<String, Object>, A, B, C, D, E, T> heptaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  hexaFunction the function that will be used to update the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the updater function.
	 * @param  bClass the class of the third parameter of the updater function.
	 * @param  cClass the class of the fourth parameter of the updater function.
	 * @param  dClass the class of the fifth parameter of the updater function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		HexaFunction<U, Map<String, Object>, A, B, C, D, T> hexaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  octaFunction the function that will be used to update the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the updater function.
	 * @param  bClass the class of the third parameter of the updater function.
	 * @param  cClass the class of the fourth parameter of the updater function.
	 * @param  dClass the class of the fifth parameter of the updater function.
	 * @param  eClass the class of the sixth parameter of the updater function.
	 * @param  fClass the class of the seventh parameter of the updater
	 *         function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E, F> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		OctaFunction<U, Map<String, Object>, A, B, C, D, E, F, T> octaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  pentaFunction the function that will be used to update the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the updater function.
	 * @param  bClass the class of the third parameter of the updater function.
	 * @param  cClass the class of the fourth parameter of the updater function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		PentaFunction<U, Map<String, Object>, A, B, C, T> pentaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  tetraFunction the function that will be used to update the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the updater function.
	 * @param  bClass the class of the third parameter of the updater function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		TetraFunction<U, Map<String, Object>, A, B, T> tetraFunction,
		Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  triFunction the function that will be used to update the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the updater function.
	 * @return the updated builder.
	 * @review
	 */
	public <A> RoutesBuilder<T, U> addCollectionPageItemUpdater(
		TriFunction<U, Map<String, Object>, A, T> triFunction, Class<A> aClass);

	/**
	 * Adds a route to a collection item updater function.
	 *
	 * @param  undecaFunction the function that will be used to update the
	 *         collection item.
	 * @param  aClass the class of the second parameter of the updater function.
	 * @param  bClass the class of the third parameter of the updater function.
	 * @param  cClass the class of the fourth parameter of the updater function.
	 * @param  dClass the class of the fifth parameter of the updater function.
	 * @param  eClass the class of the sixth parameter of the updater function.
	 * @param  fClass the class of the seventh parameter of the updater
	 *         function.
	 * @param  gClass the class of the eighth parameter of the updater function.
	 * @param  hClass the class of the ninth parameter of the updater function.
	 * @param  iClass the class of the tenth parameter of the updater function.
	 * @return the updated builder.
	 * @review
	 */
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T, U>
		addCollectionPageItemUpdater(
			UndecaFunction<U, Map<String, Object>, A, B, C, D, E, F, G, H, I, T>
				undecaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
			Class<I> iClass);

	/**
	 * Constructs the {@code Routes} instance with the information provided to
	 * the builder.
	 *
	 * @return the {@code Routes} instance.
	 * @review
	 */
	public Routes<T> build();

}