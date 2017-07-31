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

import com.liferay.vulcan.function.DecaFunction;
import com.liferay.vulcan.function.EnneaFunction;
import com.liferay.vulcan.function.HeptaFunction;
import com.liferay.vulcan.function.HexaFunction;
import com.liferay.vulcan.function.OctaFunction;
import com.liferay.vulcan.function.PentaFunction;
import com.liferay.vulcan.function.TetraFunction;
import com.liferay.vulcan.function.TriFunction;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Use instances of this builder to create {@link Routes} of a {@link Resource}.
 *
 * @author Alejandro Hernández
 */
public interface RoutesBuilder<T> {

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  biFunction the function that will be used to calculate the page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @return the builder's next step.
	 */
	public <A> SingleStep<T> collectionPage(
		BiFunction<Pagination, A, PageItems<T>> biFunction, Class<A> aClass);

	/**
	 * Adds a route to a collection page function.
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
	 * @return the builder's next step.
	 */
	public <A, B, C, D, E, F, G, H, I> SingleStep<T> collectionPage(
		DecaFunction<Pagination, A, B, C, D, E, F, G, H, I, PageItems<T>>
			decaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass,
		Class<H> hClass, Class<I> iClass);

	/**
	 * Adds a route to a collection page function.
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
	 * @return the builder's next step.
	 */
	public <A, B, C, D, E, F, G, H> SingleStep<T> collectionPage(
		EnneaFunction<Pagination, A, B, C, D, E, F, G, H, PageItems<T>>
			enneaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass,
		Class<H> hClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  function the function that will be used to calculate the page.
	 * @return the builder's next step.
	 */
	public SingleStep<T> collectionPage(
		Function<Pagination, PageItems<T>> function);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  heptaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @return the builder's next step.
	 */
	public <A, B, C, D, E, F> SingleStep<T> collectionPage(
		HeptaFunction<Pagination, A, B, C, D, E, F, PageItems<T>> heptaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  hexaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @return the builder's next step.
	 */
	public <A, B, C, D, E> SingleStep<T> collectionPage(
		HexaFunction<Pagination, A, B, C, D, E, PageItems<T>> hexaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass);

	/**
	 * Adds a route to a collection page function.
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
	 * @return the builder's next step.
	 */
	public <A, B, C, D, E, F, G> SingleStep<T> collectionPage(
		OctaFunction<Pagination, A, B, C, D, E, F, G, PageItems<T>>
			octaFunction, Class<A> aClass, Class<B> bClass, Class<C> cClass,
		Class<D> dClass, Class<E> eClass, Class<F> fClass, Class<G> gClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  pentaFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @return the builder's next step.
	 */
	public <A, B, C, D> SingleStep<T> collectionPage(
		PentaFunction<Pagination, A, B, C, D, PageItems<T>> pentaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  tetraFunction the function that will be used to calculate the
	 *         page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @return the builder's next step.
	 */
	public <A, B, C> SingleStep<T> collectionPage(
		TetraFunction<Pagination, A, B, C, PageItems<T>> tetraFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  triFunction the function that will be used to calculate the page.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @return the builder's next step.
	 */
	public <A, B> SingleStep<T> collectionPage(
		TriFunction<Pagination, A, B, PageItems<T>> triFunction,
		Class<A> aClass, Class<B> bClass);

	public interface SingleStep<T> {

		/**
		 * Adds a route to a single model function.
		 *
		 * @param biFunction the function that will be used to calculate the
		 *                     single model.
		 * @param aClass the class of the second parameter of the single model
		 *               function.
		 *
		 * @return the routes instance.
		 */
		public <U, A> Routes<T> collectionItem(
			BiFunction<U, A, T> biFunction, Class<U> identifierClass,
			Class<A> aClass);

		/**
		 * Adds a route to a single model function.
		 *
		 * @param decaFunction the function that will be used to calculate the
		 *                     single model.
		 * @param aClass the class of the second parameter of the single model
		 *               function.
		 * @param bClass the class of the third parameter of the single model
		 *               function.
		 * @param cClass the class of the fourth parameter of the single model
		 *               function.
		 * @param dClass the class of the fifth parameter of the single model
		 *               function.
		 * @param eClass the class of the sixth parameter of the single model
		 *               function.
		 * @param fClass the class of the seventh parameter of the single model
		 *               function.
		 * @param gClass the class of the eighth parameter of the single model
		 *               function.
		 * @param hClass the class of the ninth parameter of the single model
		 *               function.
		 * @param iClass the class of the tenth parameter of the single model
		 *               function.
		 *
		 * @return the routes instance.
		 */
		public <U, A, B, C, D, E, F, G, H, I> Routes<T> collectionItem(
			DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass, Class<I> iClass);

		/**
		 * Adds a route to a single model function.
		 *
		 * @param enneaFunction the function that will be used to calculate the
		 *                     single model.
		 * @param aClass the class of the second parameter of the single model
		 *               function.
		 * @param bClass the class of the third parameter of the single model
		 *               function.
		 * @param cClass the class of the fourth parameter of the single model
		 *               function.
		 * @param dClass the class of the fifth parameter of the single model
		 *               function.
		 * @param eClass the class of the sixth parameter of the single model
		 *               function.
		 * @param fClass the class of the seventh parameter of the single model
		 *               function.
		 * @param gClass the class of the eighth parameter of the single model
		 *               function.
		 * @param hClass the class of the ninth parameter of the single model
		 *               function.
		 *
		 * @return the routes instance.
		 */
		public <U, A, B, C, D, E, F, G, H> Routes<T> collectionItem(
			EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass, Class<H> hClass);

		/**
		 * Adds a route to a single model function.
		 *
		 * @param function the function that will be used to calculate the
		 *                     single model.
		 *
		 * @return the routes instance.
		 */
		public <U> Routes<T> collectionItem(
			Function<U, T> function, Class<U> identifierClass);

		/**
		 * Adds a route to a single model function.
		 *
		 * @param heptaFunction the function that will be used to calculate the
		 *                     single model.
		 * @param aClass the class of the second parameter of the single model
		 *               function.
		 * @param bClass the class of the third parameter of the single model
		 *               function.
		 * @param cClass the class of the fourth parameter of the single model
		 *               function.
		 * @param dClass the class of the fifth parameter of the single model
		 *               function.
		 * @param eClass the class of the sixth parameter of the single model
		 *               function.
		 * @param fClass the class of the seventh parameter of the single model
		 *               function.
		 *
		 * @return the routes instance.
		 */
		public <U, A, B, C, D, E, F> Routes<T> collectionItem(
			HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass);

		/**
		 * Adds a route to a single model function.
		 *
		 * @param hexaFunction the function that will be used to calculate the
		 *                     single model.
		 * @param aClass the class of the second parameter of the single model
		 *               function.
		 * @param bClass the class of the third parameter of the single model
		 *               function.
		 * @param cClass the class of the fourth parameter of the single model
		 *               function.
		 * @param dClass the class of the fifth parameter of the single model
		 *               function.
		 * @param eClass the class of the sixth parameter of the single model
		 *               function.
		 *
		 * @return the routes instance.
		 */
		public <U, A, B, C, D, E> Routes<T> collectionItem(
			HexaFunction<U, A, B, C, D, E, T> hexaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass);

		/**
		 * Adds a route to a single model function.
		 *
		 * @param octaFunction the function that will be used to calculate the
		 *                     single model.
		 * @param aClass the class of the second parameter of the single model
		 *               function.
		 * @param bClass the class of the third parameter of the single model
		 *               function.
		 * @param cClass the class of the fourth parameter of the single model
		 *               function.
		 * @param dClass the class of the fifth parameter of the single model
		 *               function.
		 * @param eClass the class of the sixth parameter of the single model
		 *               function.
		 * @param fClass the class of the seventh parameter of the single model
		 *               function.
		 * @param gClass the class of the eighth parameter of the single model
		 *               function.
		 *
		 * @return the routes instance.
		 */
		public <U, A, B, C, D, E, F, G> Routes<T> collectionItem(
			OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass, Class<E> eClass, Class<F> fClass,
			Class<G> gClass);

		/**
		 * Adds a route to a single model function.
		 *
		 * @param pentaFunction the function that will be used to calculate the
		 *                     single model.
		 * @param aClass the class of the second parameter of the single model
		 *               function.
		 * @param bClass the class of the third parameter of the single model
		 *               function.
		 * @param cClass the class of the fourth parameter of the single model
		 *               function.
		 * @param dClass the class of the fifth parameter of the single model
		 *               function.
		 *
		 * @return the routes instance.
		 */
		public <U, A, B, C, D> Routes<T> collectionItem(
			PentaFunction<U, A, B, C, D, T> pentaFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass);

		/**
		 * Adds a route to a single model function.
		 *
		 * @param tetraFunction the function that will be used to calculate the
		 *                     single model.
		 * @param aClass the class of the second parameter of the single model
		 *               function.
		 * @param bClass the class of the third parameter of the single model
		 *               function.
		 * @param cClass the class of the fourth parameter of the single model
		 *               function.
		 *
		 * @return the routes instance.
		 */
		public <U, A, B, C> Routes<T> collectionItem(
			TetraFunction<U, A, B, C, T> tetraFunction,
			Class<U> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass);

		/**
		 * Adds a route to a single model function.
		 *
		 * @param triFunction the function that will be used to calculate the
		 *                     single model.
		 * @param aClass the class of the second parameter of the single model
		 *               function.
		 * @param bClass the class of the third parameter of the single model
		 *               function.
		 *
		 * @return the routes instance.
		 */
		public <U, A, B> Routes<T> collectionItem(
			TriFunction<U, A, B, T> triFunction, Class<U> identifierClass,
			Class<A> aClass, Class<B> bClass);

	}

}