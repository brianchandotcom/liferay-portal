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

import static java.lang.String.join;

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.alias.form.FormBuilderFunction;
import com.liferay.apio.architect.alias.routes.NestedCreateItemFunction;
import com.liferay.apio.architect.alias.routes.NestedGetPageFunction;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.function.throwable.ThrowableBiFunction;
import com.liferay.apio.architect.function.throwable.ThrowableHexaFunction;
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
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Holds information about the routes supported for a {@link
 * com.liferay.apio.architect.router.NestedCollectionRouter}.
 *
 * <p>
 * This interface's methods return functions to get the collection resource's
 * different endpoints. You should always use a {@link Builder} to create
 * instances of this interface.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @param  <S> the type of the parent model's identifier (e.g., {@code Long},
 *         {@code String}, etc.)
 * @see    Builder
 */
public class NestedCollectionRoutes<T, S> {

	public NestedCollectionRoutes(Builder<T, S> builder) {
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
	public Optional<Form> getFormOptional() {
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
	public Optional<NestedCreateItemFunction<T, S>>
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
	public Optional<NestedGetPageFunction<T, S>>
		getNestedGetPageFunctionOptional() {

		return Optional.ofNullable(_nestedGetPageFunction);
	}

	/**
	 * Creates the {@link NestedCollectionRoutes} of a {@link
	 * com.liferay.apio.architect.router.NestedCollectionRouter}.
	 *
	 * @param <T> the model's type
	 * @param <S> the type of the parent model's identifier (e.g., {@code Long},
	 *        {@code String}, etc.)
	 */
	@SuppressWarnings("unused")
	public static class Builder<T, S> {

		public Builder(
			String name, String nestedName, ProvideFunction provideFunction,
			Consumer<String> neededProviderConsumer) {

			_name = name;
			_nestedName = nestedName;
			_provideFunction = provideFunction;
			_neededProviderConsumer = neededProviderConsumer;
		}

		/**
		 * Adds a route to a creator function that has no extra parameters.
		 *
		 * @param  throwableBiFunction the creator function that adds the
		 *         collection item
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <R> Builder<T, S> addCreator(
			ThrowableBiFunction<S, R, T> throwableBiFunction,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_nestedCollectionPermissionFunction = permissionBiFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_form = form;

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> Try.fromFallible(
					() -> throwableBiFunction.andThen(
						t -> new SingleModel<>(
							t, _nestedName, Collections.emptyList())
					).apply(
						identifier, form.get(body)
					));

			return this;
		}

		/**
		 * Adds a route to a creator function that has four extra parameters.
		 *
		 * @param  throwableHexaFunction the creator function that adds the
		 *         collection item
		 * @param  aClass the class of the creator function's third parameter
		 * @param  bClass the class of the creator function's fourth parameter
		 * @param  cClass the class of the creator function's fifth parameter
		 * @param  dClass the class of the creator function's sixth parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, B, C, D, R> Builder<T, S> addCreator(
			ThrowableHexaFunction<S, R, A, B, C, D, T> throwableHexaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());
			_neededProviderConsumer.accept(dClass.getName());

			_nestedCollectionPermissionFunction = permissionBiFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_form = form;

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction.apply(httpServletRequest), aClass, bClass,
					cClass, dClass,
					a -> b -> c -> d -> throwableHexaFunction.andThen(
						t -> new SingleModel<>(
							t, _nestedName, Collections.emptyList())
					).apply(
						identifier, form.get(body), a, b, c, d
					));

			return this;
		}

		/**
		 * Adds a route to a creator function that has three extra parameters.
		 *
		 * @param  throwablePentaFunction the creator function that adds the
		 *         collection item
		 * @param  aClass the class of the creator function's third parameter
		 * @param  bClass the class of the creator function's fourth parameter
		 * @param  cClass the class of the creator function's fifth parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, B, C, R> Builder<T, S> addCreator(
			ThrowablePentaFunction<S, R, A, B, C, T> throwablePentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());

			_nestedCollectionPermissionFunction = permissionBiFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_form = form;

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction.apply(httpServletRequest), aClass, bClass,
					cClass,
					a -> b -> c -> throwablePentaFunction.andThen(
						t -> new SingleModel<>(
							t, _nestedName, Collections.emptyList())
					).apply(
						identifier, form.get(body), a, b, c
					));

			return this;
		}

		/**
		 * Adds a route to a creator function that has two extra parameters.
		 *
		 * @param  throwableTetraFunction the creator function that adds the
		 *         collection item
		 * @param  aClass the class of the creator function's third parameter
		 * @param  bClass the class of the creator function's fourth parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, B, R> Builder<T, S> addCreator(
			ThrowableTetraFunction<S, R, A, B, T> throwableTetraFunction,
			Class<A> aClass, Class<B> bClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());

			_nestedCollectionPermissionFunction = permissionBiFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_form = form;

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction.apply(httpServletRequest), aClass, bClass,
					a -> b -> throwableTetraFunction.andThen(
						t -> new SingleModel<>(
							t, _nestedName, Collections.emptyList())
					).apply(
						identifier, form.get(body), a, b
					));

			return this;
		}

		/**
		 * Adds a route to a creator function that has one extra parameter.
		 *
		 * @param  throwableTriFunction the creator function that adds the
		 *         collection item
		 * @param  aClass the class of the creator function's third parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, R> Builder<T, S> addCreator(
			ThrowableTriFunction<S, R, A, T> throwableTriFunction,
			Class<A> aClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());

			_nestedCollectionPermissionFunction = permissionBiFunction;

			Form<R> form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_form = form;

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction.apply(httpServletRequest), aClass,
					a -> throwableTriFunction.andThen(
						t -> new SingleModel<>(
							t, _nestedName, Collections.emptyList())
					).apply(
						identifier, form.get(body), a
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
		public Builder<T, S> addGetter(
			ThrowableBiFunction<Pagination, S, PageItems<T>> biFunction) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class, Credentials.class,
					pagination -> credentials -> biFunction.andThen(
						items -> new Page<>(
							_nestedName, items, pagination, path,
							_getOperations(credentials, identifier))
					).apply(
						pagination, identifier
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
		public <A, B, C, D> Builder<T, S> addGetter(
			ThrowableHexaFunction<Pagination, S, A, B, C, D, PageItems<T>>
				hexaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());
			_neededProviderConsumer.accept(dClass.getName());

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class, aClass, bClass, cClass, dClass,
					Credentials.class,
					pagination -> a -> b -> c -> d -> credentials ->
						hexaFunction.andThen(
							items -> new Page<>(
								_nestedName, items, pagination, path,
								_getOperations(credentials, identifier))
						).apply(
							pagination, identifier, a, b, c, d
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
		public <A, B, C> Builder<T, S> addGetter(
			ThrowablePentaFunction<Pagination, S, A, B, C, PageItems<T>>
				pentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class, aClass, bClass, cClass, Credentials.class,
					pagination -> a -> b -> c -> credentials ->
						pentaFunction.andThen(
							items -> new Page<>(
								_nestedName, items, pagination, path,
								_getOperations(credentials, identifier))
						).apply(
							pagination, identifier, a, b, c
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
		public <A, B> Builder<T, S> addGetter(
			ThrowableTetraFunction<Pagination, S, A, B, PageItems<T>>
				tetraFunction,
			Class<A> aClass, Class<B> bClass) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class, aClass, bClass, Credentials.class,
					pagination -> a -> b -> credentials ->
						tetraFunction.andThen(
							items -> new Page<>(
								_nestedName, items, pagination, path,
								_getOperations(credentials, identifier))
						).apply(
							pagination, identifier, a, b
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
		public <A> Builder<T, S> addGetter(
			ThrowableTriFunction<Pagination, S, A, PageItems<T>> triFunction,
			Class<A> aClass) {

			_neededProviderConsumer.accept(aClass.getName());

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class, aClass, Credentials.class,
					pagination -> a -> credentials -> triFunction.andThen(
						items -> new Page<>(
							_nestedName, items, pagination, path,
							_getOperations(credentials, identifier))
					).apply(
						pagination, identifier, a
					));

			return this;
		}

		/**
		 * Constructs the {@link NestedCollectionRoutes} instance with the
		 * information provided to the builder.
		 *
		 * @return the {@code Routes} instance
		 */
		public NestedCollectionRoutes<T, S> build() {
			return new NestedCollectionRoutes<>(this);
		}

		private List<Operation> _getOperations(
			Credentials credentials, S identifier) {

			return Optional.ofNullable(
				_form
			).filter(
				__ -> _nestedCollectionPermissionFunction.apply(
					credentials, identifier)
			).map(
				form -> new Operation(
					form, POST, join("/", _name, _nestedName, "create"))
			).map(
				Collections::singletonList
			).orElseGet(
				Collections::emptyList
			);
		}

		private Form _form;
		private final String _name;
		private final Consumer<String> _neededProviderConsumer;
		private BiFunction<Credentials, S, Boolean>
			_nestedCollectionPermissionFunction;
		private NestedCreateItemFunction<T, S> _nestedCreateItemFunction;
		private NestedGetPageFunction<T, S> _nestedGetPageFunction;
		private final String _nestedName;
		private final ProvideFunction _provideFunction;

	}

	private final Form _form;
	private final NestedCreateItemFunction<T, S> _nestedCreateItemFunction;
	private final NestedGetPageFunction<T, S> _nestedGetPageFunction;

}