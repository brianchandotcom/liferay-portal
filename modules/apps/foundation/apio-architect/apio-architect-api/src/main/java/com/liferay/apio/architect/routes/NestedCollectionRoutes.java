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

import static java.lang.String.join;

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.alias.form.FormBuilderFunction;
import com.liferay.apio.architect.alias.routes.NestedCreateItemFunction;
import com.liferay.apio.architect.alias.routes.NestedGetPageFunction;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.function.HexaFunction;
import com.liferay.apio.architect.function.PentaFunction;
import com.liferay.apio.architect.function.TetraFunction;
import com.liferay.apio.architect.function.TriFunction;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

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
		_nestedName = builder._nestedName;
		_name = builder._name;
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
	 * Returns the list of operations for the single item resource.
	 *
	 * @return the list of operations
	 */
	public List<Operation> getOperations() {
		List<Operation> operations = new ArrayList<>();

		Optional<Form> formOptional = getFormOptional();

		formOptional.ifPresent(
			form -> operations.add(
				new Operation(
					form, POST, join("/", _name, _nestedName, "create"))));

		return operations;
	}

	/**
	 * Creates the {@link NestedCollectionRoutes} of a {@link
	 * com.liferay.apio.architect.router.NestedCollectionRouter}.
	 *
	 * @param  <T> the model's type
	 * @param  <S> the type of the parent model's identifier (e.g., {@code
	 *         Long}, {@code String}, etc.)
	 * @review
	 */
	@SuppressWarnings("unused")
	public static class Builder<T, S> {

		public Builder(
			String name, String nestedName, ProvideFunction provideFunction) {

			_name = name;
			_nestedName = nestedName;
			_provideFunction = provideFunction;
		}

		/**
		 * Adds a route to a creator function that has no extra parameters.
		 *
		 * @param  biFunction the creator function that adds the collection item
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <R> Builder<T, S> addCreator(
			BiFunction<S, R, T> biFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> biFunction.andThen(
					t -> new SingleModel<>(t, _nestedName)
				).apply(
					identifier, unsafeCast(_form.get(body))
				);

			return this;
		}

		/**
		 * Adds a route to a creator function that has four extra parameters.
		 *
		 * @param  hexaFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the creator function's third parameter
		 * @param  bClass the class of the creator function's fourth parameter
		 * @param  cClass the class of the creator function's fifth parameter
		 * @param  dClass the class of the creator function's sixth parameter
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, B, C, D, R> Builder<T, S> addCreator(
			HexaFunction<S, R, A, B, C, D, T> hexaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass,
			FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction.apply(httpServletRequest), aClass, bClass,
					cClass, dClass,
					a -> b -> c -> d -> hexaFunction.andThen(
						t -> new SingleModel<>(t, _nestedName)
					).apply(
						identifier, unsafeCast(_form.get(body)), a, b, c, d
					));

			return this;
		}

		/**
		 * Adds a route to a creator function that has three extra parameters.
		 *
		 * @param  pentaFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the creator function's third parameter
		 * @param  bClass the class of the creator function's fourth parameter
		 * @param  cClass the class of the creator function's fifth parameter
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, B, C, R> Builder<T, S> addCreator(
			PentaFunction<S, R, A, B, C, T> pentaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass,
			FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction.apply(httpServletRequest), aClass, bClass,
					cClass,
					a -> b -> c -> pentaFunction.andThen(
						t -> new SingleModel<>(t, _nestedName)
					).apply(
						identifier, unsafeCast(_form.get(body)), a, b, c
					));

			return this;
		}

		/**
		 * Adds a route to a creator function that has two extra parameters.
		 *
		 * @param  tetraFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the creator function's third parameter
		 * @param  bClass the class of the creator function's fourth parameter
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, B, R> Builder<T, S> addCreator(
			TetraFunction<S, R, A, B, T> tetraFunction, Class<A> aClass,
			Class<B> bClass, FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction.apply(httpServletRequest), aClass, bClass,
					a -> b -> tetraFunction.andThen(
						t -> new SingleModel<>(t, _nestedName)
					).apply(
						identifier, unsafeCast(_form.get(body)), a, b
					));

			return this;
		}

		/**
		 * Adds a route to a creator function that has one extra parameter.
		 *
		 * @param  triFunction the creator function that adds the collection
		 *         item
		 * @param  aClass the class of the creator function's third parameter
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 */
		public <A, R> Builder<T, S> addCreator(
			TriFunction<S, R, A, T> triFunction, Class<A> aClass,
			FormBuilderFunction<R> formBuilderFunction) {

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("c", _name, _nestedName)));

			_nestedCreateItemFunction =
				httpServletRequest -> identifier -> body -> provide(
					_provideFunction.apply(httpServletRequest), aClass,
					a -> triFunction.andThen(
						t -> new SingleModel<>(t, _nestedName)
					).apply(
						identifier, unsafeCast(_form.get(body)), a
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
			BiFunction<Pagination, S, PageItems<T>> biFunction) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class,
					pagination -> biFunction.andThen(
						items -> new Page<>(
							_nestedName, items, pagination, path)
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
			HexaFunction<Pagination, S, A, B, C, D, PageItems<T>> hexaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class, aClass, bClass, cClass, dClass,
					pagination -> a -> b -> c -> d -> hexaFunction.andThen(
						items -> new Page<>(
							_nestedName, items, pagination, path)
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
			PentaFunction<Pagination, S, A, B, C, PageItems<T>> pentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class, aClass, bClass, cClass,
					pagination -> a -> b -> c -> pentaFunction.andThen(
						items -> new Page<>(
							_nestedName, items, pagination, path)
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
			TetraFunction<Pagination, S, A, B, PageItems<T>> tetraFunction,
			Class<A> aClass, Class<B> bClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class, aClass, bClass,
					pagination -> a -> b -> tetraFunction.andThen(
						items -> new Page<>(
							_nestedName, items, pagination, path)
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
			TriFunction<Pagination, S, A, PageItems<T>> triFunction,
			Class<A> aClass) {

			_nestedGetPageFunction =
				httpServletRequest -> path -> identifier -> provide(
					_provideFunction.apply(httpServletRequest),
					Pagination.class, aClass,
					pagination -> a -> triFunction.andThen(
						items -> new Page<>(
							_nestedName, items, pagination, path)
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

		private Form _form;
		private final String _name;
		private NestedCreateItemFunction<T, S> _nestedCreateItemFunction;
		private NestedGetPageFunction<T, S> _nestedGetPageFunction;
		private final String _nestedName;
		private final ProvideFunction _provideFunction;

	}

	private final Form _form;
	private final String _name;
	private final NestedCreateItemFunction<T, S> _nestedCreateItemFunction;
	private final NestedGetPageFunction<T, S> _nestedGetPageFunction;
	private final String _nestedName;

}