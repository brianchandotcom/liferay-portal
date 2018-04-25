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

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.alias.form.FormBuilderFunction;
import com.liferay.apio.architect.alias.routes.CreateItemFunction;
import com.liferay.apio.architect.alias.routes.GetPageFunction;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.function.throwable.ThrowableBiFunction;
import com.liferay.apio.architect.function.throwable.ThrowableFunction;
import com.liferay.apio.architect.function.throwable.ThrowablePentaFunction;
import com.liferay.apio.architect.function.throwable.ThrowableTetraFunction;
import com.liferay.apio.architect.function.throwable.ThrowableTriFunction;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
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

		public Builder(
			String name, ProvideFunction provideFunction,
			Consumer<String> neededProviderConsumer) {

			_name = name;
			_provideFunction = provideFunction;
			_neededProviderConsumer = neededProviderConsumer;
		}

		/**
		 * Adds a route to a creator function that has one extra parameter.
		 *
		 * @param  throwableBiFunction the creator function
		 * @param  aClass the class of the creator function's second parameter
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, R> Builder<T> addCreator(
			ThrowableBiFunction<R, A, T> throwableBiFunction, Class<A> aClass,
			Function<Credentials, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());

			_collectionPermissionFunction = permissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_form = form;

			_createItemFunction = httpServletRequest -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass,
				a -> throwableBiFunction.andThen(
					t -> new SingleModel<>(t, _name, Collections.emptyList())
				).apply(
					form.get(body), a
				));

			return this;
		}

		/**
		 * Adds a route to a creator function that has no extra parameters.
		 *
		 * @param  throwableFunction the creator function
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <R> Builder<T> addCreator(
			ThrowableFunction<R, T> throwableFunction,
			Function<Credentials, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_collectionPermissionFunction = permissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_form = form;

			_createItemFunction = httpServletRequest -> body ->
				Try.fromFallible(
					() -> throwableFunction.andThen(
						t -> new SingleModel<>(
							t, _name, Collections.emptyList())
					).apply(
						form.get(body)
					));

			return this;
		}

		/**
		 * Adds a route to a creator function that has four extra parameters.
		 *
		 * @param  throwablePentaFunction the creator function
		 * @param  aClass the class of the creator function's second parameter
		 * @param  bClass the class of the creator function's third parameter
		 * @param  cClass the class of the creator function's fourth parameter
		 * @param  dClass the class of the creator function's fifth parameter
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, B, C, D, R> Builder<T> addCreator(
			ThrowablePentaFunction<R, A, B, C, D, T> throwablePentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Function<Credentials, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());
			_neededProviderConsumer.accept(dClass.getName());

			_collectionPermissionFunction = permissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_form = form;

			_createItemFunction = httpServletRequest -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass, dClass,
				a -> b -> c -> d -> throwablePentaFunction.andThen(
					t -> new SingleModel<>(t, _name, Collections.emptyList())
				).apply(
					form.get(body), a, b, c, d
				));

			return this;
		}

		/**
		 * Adds a route to a creator function that has three extra parameters.
		 *
		 * @param  throwableTetraFunction the creator function
		 * @param  aClass the class of the creator function's second parameter
		 * @param  bClass the class of the creator function's third parameter
		 * @param  cClass the class of the creator function's fourth parameter
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, B, C, R> Builder<T> addCreator(
			ThrowableTetraFunction<R, A, B, C, T> throwableTetraFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Function<Credentials, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());

			_collectionPermissionFunction = permissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_form = form;

			_createItemFunction = httpServletRequest -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass,
				a -> b -> c -> throwableTetraFunction.andThen(
					t -> new SingleModel<>(t, _name, Collections.emptyList())
				).apply(
					form.get(body), a, b, c
				));

			return this;
		}

		/**
		 * Adds a route to a creator function that has two extra parameters.
		 *
		 * @param  throwableTriFunction the creator function
		 * @param  aClass the class of the creator function's second parameter
		 * @param  bClass the class of the creator function's third parameter
		 * @param  permissionFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, B, R> Builder<T> addCreator(
			ThrowableTriFunction<R, A, B, T> throwableTriFunction,
			Class<A> aClass, Class<B> bClass,
			Function<Credentials, Boolean> permissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());

			_collectionPermissionFunction = permissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name)));

			_form = form;

			_createItemFunction = httpServletRequest -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				a -> b -> throwableTriFunction.andThen(
					t -> new SingleModel<>(t, _name, Collections.emptyList())
				).apply(
					form.get(body), a, b
				));

			return this;
		}

		/**
		 * Adds a route to a collection page function with one extra parameter.
		 *
		 * @param  throwableBiFunction the function that calculates the page
		 * @param  aClass the class of the page function's third parameter
		 * @return the updated builder
		 */
		public <A> Builder<T> addGetter(
			ThrowableBiFunction<Pagination, A, PageItems<T>>
				throwableBiFunction,
			Class<A> aClass) {

			_neededProviderConsumer.accept(aClass.getName());

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				aClass, Credentials.class,
				pagination -> a -> credentials -> throwableBiFunction.andThen(
					items -> new Page<>(
						_name, items, pagination, _getOperations(credentials))
				).apply(
					pagination, a
				));

			return this;
		}

		/**
		 * Adds a route to a collection page function with none extra
		 * parameters.
		 *
		 * @param  throwableFunction the function that calculates the page
		 * @return the updated builder
		 */
		public Builder<T> addGetter(
			ThrowableFunction<Pagination, PageItems<T>> throwableFunction) {

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				Credentials.class,
				pagination -> credentials -> throwableFunction.andThen(
					items -> new Page<>(
						_name, items, pagination, _getOperations(credentials))
				).apply(
					pagination
				));

			return this;
		}

		/**
		 * Adds a route to a collection page function with four extra
		 * parameters.
		 *
		 * @param  throwablePentaFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @param  bClass the class of the page function's third parameter
		 * @param  cClass the class of the page function's fourth parameter
		 * @param  dClass the class of the page function's fifth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D> Builder<T> addGetter(
			ThrowablePentaFunction<Pagination, A, B, C, D, PageItems<T>>
				throwablePentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());
			_neededProviderConsumer.accept(dClass.getName());

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				aClass, bClass, cClass, dClass, Credentials.class,
				pagination -> a -> b -> c -> d -> credentials ->
					throwablePentaFunction.andThen(
						items -> new Page<>(
							_name, items, pagination,
							_getOperations(credentials))
					).apply(
						pagination, a, b, c, d
					));

			return this;
		}

		/**
		 * Adds a route to a collection page function with three extra
		 * parameters.
		 *
		 * @param  throwableTetraFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @param  bClass the class of the page function's third parameter
		 * @param  cClass the class of the page function's fourth parameter
		 * @return the updated builder
		 */
		public <A, B, C> Builder<T> addGetter(
			ThrowableTetraFunction<Pagination, A, B, C, PageItems<T>>
				throwableTetraFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				aClass, bClass, cClass, Credentials.class,
				pagination -> a -> b -> c -> credentials ->
					throwableTetraFunction.andThen(
						items -> new Page<>(
							_name, items, pagination,
							_getOperations(credentials))
					).apply(
						pagination, a, b, c
					));

			return this;
		}

		/**
		 * Adds a route to a collection page function with two extra parameters.
		 *
		 * @param  throwableTriFunction the function that calculates the page
		 * @param  aClass the class of the page function's second parameter
		 * @param  bClass the class of the page function's third parameter
		 * @return the updated builder
		 */
		public <A, B> Builder<T> addGetter(
			ThrowableTriFunction<Pagination, A, B, PageItems<T>>
				throwableTriFunction,
			Class<A> aClass, Class<B> bClass) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());

			_getPageFunction = httpServletRequest -> provide(
				_provideFunction.apply(httpServletRequest), Pagination.class,
				aClass, bClass, Credentials.class,
				pagination -> a -> b -> credentials ->
					throwableTriFunction.andThen(
						items -> new Page<>(
							_name, items, pagination,
							_getOperations(credentials))
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

		private List<Operation> _getOperations(Credentials credentials) {
			return Optional.ofNullable(
				_form
			).filter(
				__ -> _collectionPermissionFunction.apply(credentials)
			).map(
				form -> new Operation(form, POST, _name + "/create")
			).map(
				Collections::singletonList
			).orElseGet(
				Collections::emptyList
			);
		}

		private Function<Credentials, Boolean> _collectionPermissionFunction;
		private CreateItemFunction<T> _createItemFunction;
		private Form _form;
		private GetPageFunction<T> _getPageFunction;
		private final String _name;
		private final Consumer<String> _neededProviderConsumer;
		private final ProvideFunction _provideFunction;

	}

	private final CreateItemFunction<T> _createItemFunction;
	private final Form _form;
	private final GetPageFunction<T> _getPageFunction;

}