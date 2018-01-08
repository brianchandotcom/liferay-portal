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

import static com.liferay.apio.architect.routes.RoutesBuilderUtil.provide;

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.alias.form.FormBuilderFunction;
import com.liferay.apio.architect.alias.routes.NestedCreateItemFunction;
import com.liferay.apio.architect.alias.routes.NestedGetPageFunction;
import com.liferay.apio.architect.error.ApioDeveloperError.MustUseSameIdentifier;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.function.HexaFunction;
import com.liferay.apio.architect.function.PentaFunction;
import com.liferay.apio.architect.function.TetraFunction;
import com.liferay.apio.architect.function.TriFunction;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;

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
 * @param  <T> the model's type
 * @see    NestedCollectionRoutes.Builder
 * @review
 */
public class NestedCollectionRoutes<T> {

	public NestedCollectionRoutes(Builder<T, ?> builder) {
		_form = builder._form;
		_nestedCreateItemFunction = builder._nestedCreateItemFunction;
		_nestedGetPageFunction = builder._nestedGetPageFunction;
	}

	/**
	 * Returns the form that is used to create a collection item, if it was
	 * added through the {@link Builder}. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @return the form used to create a collection item; {@code
	 *         Optional#empty()} otherwise
	 */
	public Optional<Form> getForm() {
		return Optional.ofNullable(_form);
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
	 *
	 * @param  <T> the model's type
	 * @param  <S> the parent model identifier's type ({@link Long}, {@link
	 *         String}, etc.)
	 * @review
	 */
	@SuppressWarnings("unused")
	public static class Builder<T, S> {

		public Builder(
			Class<T> modelClass, String name, String nestedName,
			Class<S> identifierClass, ProvideFunction provideFunction) {

			_modelClass = modelClass;
			_name = name;
			_nestedName = nestedName;
			_identifierClass = identifierClass;
			_provideFunction = provideFunction;
		}

		/**
		 * Adds a route to a creator function with none extra parameters.
		 *
		 * @param  biFunction the creator function that adds the collection item
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation      tor function that adds the collection
		 *         item
		 * @return the updated builder
		 * @review
		 */
		@SuppressWarnings("unchecked")
		public <R, V> Builder<T, S> addCreator(
			BiFunction<V, R, T> biFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> biFunction.andThen(
					t -> new SingleModel<>(t, _modelClass)
				).apply(
					_getIdentifier(identifier), (R)_form.get(body)
				);

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
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		@SuppressWarnings("unchecked")
		public <A, B, C, D, R, V> Builder<T, S> addCreator(
			HexaFunction<V, R, A, B, C, D, T> hexaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass,
			FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction, httpServletRequest, aClass, bClass,
					cClass, dClass,
					a -> b -> c -> d -> hexaFunction.andThen(
						t -> new SingleModel<>(t, _modelClass)
					).apply(
						_getIdentifier(identifier), (R)_form.get(body), a, b, c,
						d
					));

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
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		@SuppressWarnings("unchecked")
		public <A, B, C, R, V> Builder<T, S> addCreator(
			PentaFunction<V, R, A, B, C, T> pentaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass,
			FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction, httpServletRequest, aClass, bClass,
					cClass,
					a -> b -> c -> pentaFunction.andThen(
						t -> new SingleModel<>(t, _modelClass)
					).apply(
						_getIdentifier(identifier), (R)_form.get(body), a, b, c
					));

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
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		@SuppressWarnings("unchecked")
		public <A, B, R, V> Builder<T, S> addCreator(
			TetraFunction<V, R, A, B, T> tetraFunction, Class<A> aClass,
			Class<B> bClass, FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction, httpServletRequest, aClass, bClass,
					a -> b -> tetraFunction.andThen(
						t -> new SingleModel<>(t, _modelClass)
					).apply(
						_getIdentifier(identifier), (R)_form.get(body), a, b
					));

			return this;
		}

		/**
		 * Adds a route to a creator function with one extra parameter.
		 *
		 * @param  triFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the collection item creator function's
		 *         third parameter
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		@SuppressWarnings("unchecked")
		public <A, R, V> Builder<T, S> addCreator(
			TriFunction<V, R, A, T> triFunction, Class<A> aClass,
			FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction, httpServletRequest, aClass,
					a -> triFunction.andThen(
						t -> new SingleModel<>(t, _modelClass)
					).apply(
						_getIdentifier(identifier), (R)_form.get(body), a
					));

			return this;
		}

		/**
		 * Adds a route to a collection page function with none extra
		 * parameters.
		 *
		 * @param  biFunction the function that calculates the page
		 * @return the updated builder
		 */
		public <V> Builder<T, S> addGetter(
			BiFunction<Pagination, V, PageItems<T>> biFunction) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction, httpServletRequest, Pagination.class,
					pagination -> biFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, _getIdentifier(identifier)
					));

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
		public <V, A, B, C, D> Builder<T, S> addGetter(
			HexaFunction<Pagination, V, A, B, C, D, PageItems<T>> hexaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction, httpServletRequest, Pagination.class,
					aClass, bClass, cClass, dClass,
					pagination -> a -> b -> c -> d -> hexaFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, _getIdentifier(identifier), a, b, c, d
					));

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
		public <V, A, B, C> Builder<T, S> addGetter(
			PentaFunction<Pagination, V, A, B, C, PageItems<T>> pentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction, httpServletRequest, Pagination.class,
					aClass, bClass, cClass,
					pagination -> a -> b -> c -> pentaFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, _getIdentifier(identifier), a, b, c
					));

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
		public <V, A, B> Builder<T, S> addGetter(
			TetraFunction<Pagination, V, A, B, PageItems<T>> tetraFunction,
			Class<A> aClass, Class<B> bClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction, httpServletRequest, Pagination.class,
					aClass, bClass,
					pagination -> a -> b -> tetraFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, _getIdentifier(identifier), a, b
					));

			return this;
		}

		/**
		 * Adds a route to a collection page function with one extra parameter.
		 *
		 * @param  triFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @return the updated builder
		 */
		public <V, A> Builder<T, S> addGetter(
			TriFunction<Pagination, V, A, PageItems<T>> triFunction,
			Class<A> aClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction, httpServletRequest, Pagination.class,
					aClass,
					pagination -> a -> triFunction.andThen(
						items -> new Page<>(
							_modelClass, items, pagination, path)
					).apply(
						pagination, _getIdentifier(identifier), a
					));

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
		private <V> V _getIdentifier(Object identifier) {
			Class<?> clazz = identifier.getClass();

			if (!_identifierClass.isAssignableFrom(clazz)) {
				throw new MustUseSameIdentifier(clazz, _identifierClass);
			}

			return (V)identifier;
		}

		private Form _form;
		private final Class<S> _identifierClass;
		private final Class<T> _modelClass;
		private final String _name;
		private NestedCreateItemFunction<T> _nestedCreateItemFunction;
		private NestedGetPageFunction<T> _nestedGetPageFunction;
		private final String _nestedName;
		private final ProvideFunction _provideFunction;

	}

	private Form _form;
	private NestedCreateItemFunction<T> _nestedCreateItemFunction;
	private NestedGetPageFunction<T> _nestedGetPageFunction;

}