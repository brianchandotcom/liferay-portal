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

import static com.liferay.apio.architect.operation.Method.POST;
import static com.liferay.apio.architect.routes.RoutesBuilderUtil.provide;
import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.alias.form.FormBuilderFunction;
import com.liferay.apio.architect.alias.routes.CreateItemFunction;
import com.liferay.apio.architect.alias.routes.GetPageFunction;
import com.liferay.apio.architect.auth.Auth;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.function.PentaFunction;
import com.liferay.apio.architect.function.TetraFunction;
import com.liferay.apio.architect.function.TriFunction;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Holds information about the routes supported for a {@link
 * com.liferay.apio.architect.router.CollectionRouter}.
 *
 * <p>
 * This interface's methods return functions that get the collection resource's
 * different endpoints. You should always use a {@link Builder} to create
 * instances of this interface.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @see    Builder
 */
public class CollectionRoutes<T> {

	public CollectionRoutes(Builder<T> builder) {
		_createItemFunction = builder._createItemFunction;
		_form = builder._form;
		_getPageFunction = builder._getPageFunction;
	}

	/**
	 * Returns the function that is used to create a collection item, if the
	 * endpoint was added through the {@link Builder} and the function therefore
	 * exists. Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to create a collection item, if the function
	 *         exists; {@code Optional#empty()} otherwise
	 */
	public Optional<CreateItemFunction<T>> getCreateItemFunctionOptional() {
		return Optional.ofNullable(_createItemFunction);
	}

	/**
	 * Returns the form that is used to create a collection item, if it was
	 * added through the {@link Builder}. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @return the form used to create a collection item; {@code
	 *         Optional#empty()} otherwise
	 */
	public Optional<Form> getFormOptional() {
		return Optional.ofNullable(_form);
	}

	/**
	 * Returns the function used to obtain the page, if the endpoint was added
	 * through the {@link Builder} and the function therefore exists. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to obtain the page, if the function exists;
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<GetPageFunction<T>> getGetPageFunctionOptional() {
		return Optional.ofNullable(_getPageFunction);
	}

	/**
	 * Creates the {@link CollectionRoutes} of a {@link
	 * com.liferay.apio.architect.router.CollectionRouter}.
	 */
	@SuppressWarnings("unused")
	public static class Builder<T> {

		public Builder(String name, ProvideFunction provideFunction) {
			_name = name;
			_provideFunction = provideFunction;
		}

		/**
		 * Adds a route to a creator function that has one extra parameter.
		 *
		 * @param  biFunction the creator function that adds the collection item
		 * @param  aClass the class of the creator function's second parameter
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <A, R> Builder<T> addCreator(
			BiFunction<R, A, T> biFunction, Class<A> aClass,
			Function<Auth, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_collectionPermissionFunction = permissionFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_createItemFunction = httpServletRequest -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass,
				a -> biFunction.andThen(
					t -> new SingleModel<>(t, _name, Collections.emptyList())
				).apply(
					unsafeCast(_form.get(body)), a
				));

			return this;
		}

		/**
		 * Adds a route to a creator function that has no extra parameters.
		 *
		 * @param  function the creator function that adds the collection item
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <R> Builder<T> addCreator(
			Function<R, T> function, Function<Auth, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_collectionPermissionFunction = permissionFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_createItemFunction = httpServletRequest -> body ->
				function.andThen(
					t -> new SingleModel<>(t, _name, Collections.emptyList())
				).apply(
					unsafeCast(_form.get(body))
				);

			return this;
		}

		/**
		 * Adds a route to a creator function that has four extra parameters.
		 *
		 * @param  pentaFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the creator function's second parameter
		 * @param  bClass the class of the creator function's third parameter
		 * @param  cClass the class of the creator function's fourth parameter
		 * @param  dClass the class of the creator function's fifth parameter
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <A, B, C, D, R> Builder<T> addCreator(
			PentaFunction<R, A, B, C, D, T> pentaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Function<Auth, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_collectionPermissionFunction = permissionFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_createItemFunction = httpServletRequest -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass, dClass,
				a -> b -> c -> d -> pentaFunction.andThen(
					t -> new SingleModel<>(t, _name, Collections.emptyList())
				).apply(
					unsafeCast(_form.get(body)), a, b, c, d
				));

			return this;
		}

		/**
		 * Adds a route to a creator function that has three extra parameters.
		 *
		 * @param  pentaFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the creator function's second parameter
		 * @param  bClass the class of the creator function's third parameter
		 * @param  cClass the class of the creator function's fourth parameter
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <A, B, C, R> Builder<T> addCreator(
			TetraFunction<R, A, B, C, T> pentaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass,
			Function<Auth, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_collectionPermissionFunction = permissionFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_createItemFunction = httpServletRequest -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass,
				a -> b -> c -> pentaFunction.andThen(
					t -> new SingleModel<>(t, _name, Collections.emptyList())
				).apply(
					unsafeCast(_form.get(body)), a, b, c
				));

			return this;
		}

		/**
		 * Adds a route to a creator function that has two extra parameters.
		 *
		 * @param  triFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the creator function's second parameter
		 * @param  bClass the class of the creator function's third parameter
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <A, B, R> Builder<T> addCreator(
			TriFunction<R, A, B, T> triFunction, Class<A> aClass,
			Class<B> bClass, Function<Auth, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_collectionPermissionFunction = permissionFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_createItemFunction = httpServletRequest -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				a -> b -> triFunction.andThen(
					t -> new SingleModel<>(t, _name, Collections.emptyList())
				).apply(
					unsafeCast(_form.get(body)), a, b
				));

			return this;
		}

		/**
		 * Adds a route to a collection page function with one extra parameter.
		 *
		 * @param  biFunction the function that calculates the page
		 * @param  aClass the class of the page function's third parameter
		 * @return the updated builder
		 */
		public <A> Builder<T> addGetter(
			BiFunction<Pagination, A, PageItems<T>> biFunction,
			Class<A> aClass) {

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				aClass, Auth.class,
				pagination -> a -> auth -> biFunction.andThen(
					items -> new Page<>(
						_name, items, pagination, _getOperations(auth))
				).apply(
					pagination, a
				));

			return this;
		}

		/**
		 * Adds a route to a collection page function with none extra
		 * parameters.
		 *
		 * @param  function the function that calculates the page
		 * @return the updated builder
		 */
		public Builder<T> addGetter(
			Function<Pagination, PageItems<T>> function) {

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				Auth.class,
				pagination -> auth -> function.andThen(
					items -> new Page<>(
						_name, items, pagination, _getOperations(auth))
				).apply(
					pagination
				));

			return this;
		}

		/**
		 * Adds a route to a collection page function with four extra
		 * parameters.
		 *
		 * @param  pentaFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @param  bClass the class of the page function's third parameter
		 * @param  cClass the class of the page function's fourth parameter
		 * @param  dClass the class of the page function's fifth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D> Builder<T> addGetter(
			PentaFunction<Pagination, A, B, C, D, PageItems<T>> pentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				aClass, bClass, cClass, dClass, Auth.class,
				pagination -> a -> b -> c -> d -> auth -> pentaFunction.andThen(
					items -> new Page<>(
						_name, items, pagination, _getOperations(auth))
				).apply(
					pagination, a, b, c, d
				));

			return this;
		}

		/**
		 * Adds a route to a collection page function with three extra
		 * parameters.
		 *
		 * @param  tetraFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @param  bClass the class of the page function's third parameter
		 * @param  cClass the class of the page function's fourth parameter
		 * @return the updated builder
		 */
		public <A, B, C> Builder<T> addGetter(
			TetraFunction<Pagination, A, B, C, PageItems<T>> tetraFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				aClass, bClass, cClass, Auth.class,
				pagination -> a -> b -> c -> auth -> tetraFunction.andThen(
					items -> new Page<>(
						_name, items, pagination, _getOperations(auth))
				).apply(
					pagination, a, b, c
				));

			return this;
		}

		/**
		 * Adds a route to a collection page function with two extra parameters.
		 *
		 * @param  triFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @param  bClass the class of the page function's third parameter
		 * @return the updated builder
		 */
		public <A, B> Builder<T> addGetter(
			TriFunction<Pagination, A, B, PageItems<T>> triFunction,
			Class<A> aClass, Class<B> bClass) {

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				aClass, bClass, Auth.class,
				pagination -> a -> b -> auth -> triFunction.andThen(
					items -> new Page<>(
						_name, items, pagination, _getOperations(auth))
				).apply(
					pagination, a, b
				));

			return this;
		}

		/**
		 * Constructs the {@link CollectionRoutes} instance with the information
		 * provided to the builder.
		 *
		 * @return the {@code Routes} instance
		 */
		public CollectionRoutes<T> build() {
			return new CollectionRoutes<>(this);
		}

		private List<Operation> _getOperations(Auth auth) {
			Optional<Form> optional = Optional.ofNullable(_form);

			return optional.filter(
				__ -> _collectionPermissionFunction.apply(auth)
			).map(
				form -> new Operation(form, POST, _name + "/create")
			).map(
				Collections::singletonList
			).orElseGet(
				Collections::emptyList
			);
		}

		private Function<Auth, Boolean> _collectionPermissionFunction;
		private CreateItemFunction<T> _createItemFunction;
		private Form _form;
		private GetPageFunction<T> _getPageFunction;
		private final String _name;
		private final ProvideFunction _provideFunction;

	}

	private final CreateItemFunction<T> _createItemFunction;
	private final Form _form;
	private final GetPageFunction<T> _getPageFunction;

}