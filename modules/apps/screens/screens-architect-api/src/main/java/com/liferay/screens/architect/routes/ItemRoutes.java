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

package com.liferay.screens.architect.routes;

import static com.liferay.screens.architect.operation.Method.DELETE;
import static com.liferay.screens.architect.operation.Method.PUT;
import static com.liferay.screens.architect.routes.RoutesBuilderUtil.provide;
import static com.liferay.screens.architect.unsafe.Unsafe.unsafeCast;

import com.liferay.screens.architect.alias.IdentifierFunction;
import com.liferay.screens.architect.alias.ProvideFunction;
import com.liferay.screens.architect.alias.form.FormBuilderFunction;
import com.liferay.screens.architect.alias.routes.DeleteItemConsumer;
import com.liferay.screens.architect.alias.routes.GetItemFunction;
import com.liferay.screens.architect.alias.routes.UpdateItemFunction;
import com.liferay.screens.architect.consumer.PentaConsumer;
import com.liferay.screens.architect.consumer.TetraConsumer;
import com.liferay.screens.architect.consumer.TriConsumer;
import com.liferay.screens.architect.credentials.Credentials;
import com.liferay.screens.architect.form.Form;
import com.liferay.screens.architect.function.HexaFunction;
import com.liferay.screens.architect.function.PentaFunction;
import com.liferay.screens.architect.function.TetraFunction;
import com.liferay.screens.architect.function.TriFunction;
import com.liferay.screens.architect.operation.Operation;
import com.liferay.screens.architect.single.model.SingleModel;
import com.liferay.screens.architect.unsafe.Unsafe;
import com.liferay.screens.architect.uri.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Holds information about the routes supported for an {@link
 * com.liferay.screens.architect.router.ItemRouter}.
 *
 * <p>
 * This interface's methods return functions to get the item resource's
 * different endpoints. You should always use a {@link Builder} to create
 * instances of this interface.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @see    Builder
 */
public class ItemRoutes<T> {

	public ItemRoutes(Builder<T, ?> builder) {
		_deleteItemConsumer = builder._deleteItemConsumer;
		_form = builder._form;
		_singleModelFunction = builder._singleModelFunction;
		_updateItemFunction = builder._updateItemFunction;
	}

	/**
	 * Returns the function used to delete the item, if the endpoint was added
	 * through the {@link Builder} and the function therefore exists. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to delete the item, if the function exists;
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<DeleteItemConsumer> getDeleteConsumerOptional() {
		return Optional.ofNullable(_deleteItemConsumer);
	}

	/**
	 * Returns the form that is used to update a collection item, if it was
	 * added through the {@link Builder}. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @return the form used to update a collection item; {@code
	 *         Optional#empty()} otherwise
	 */
	public Optional<Form> getFormOptional() {
		return Optional.ofNullable(_form);
	}

	/**
	 * Returns the function used to obtain the item, if the endpoint was added
	 * through the {@link Builder} and the function therefore exists. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to obtain the item, if the function exists;
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<GetItemFunction<T>> getItemFunctionOptional() {
		return Optional.ofNullable(_singleModelFunction);
	}

	/**
	 * Returns the function used to update the item, if the endpoint was added
	 * through the {@link Builder} and the function therefore exists. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @return the function used to update the item, if the function exists;
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<UpdateItemFunction<T>> getUpdateItemFunctionOptional() {
		return Optional.ofNullable(_updateItemFunction);
	}

	/**
	 * Creates the {@code ItemRoutes} of an {@link
	 * com.liferay.screens.architect.router.ItemRouter}.
	 *
	 * @param  <T> the model's type
	 * @param  <S> the model identifier's type. It must be a subclass of {@code
	 *         Identifier}.
	 * @review
	 */
	@SuppressWarnings("unused")
	public static class Builder<T, S> {

		public Builder(
			String name, ProvideFunction provideFunction,
			IdentifierFunction<S> identifierFunction) {

			_name = name;
			_provideFunction = provideFunction;
			_identifierFunction = identifierFunction;
		}

		/**
		 * Adds a route to an item function with one extra parameter.
		 *
		 * @param  biFunction the function that calculates the item
		 * @param  aClass the class of the item function's second parameter
		 * @return the updated builder
		 */
		public <A> Builder<T, S> addGetter(
			BiFunction<S, A, T> biFunction, Class<A> aClass) {

			return this;
		}

		/**
		 * Adds a route to an item function with none extra parameters.
		 *
		 * @param  function the function that calculates the item
		 * @return the updated builder
		 */
		public Builder<T, S> addGetter(Function<S, T> function) {
			return this;
		}

		/**
		 * Adds a route to an item function with four extra parameters.
		 *
		 * @param  pentaFunction the function that calculates the item
		 * @param  aClass the class of the item function's second parameter
		 * @param  bClass the class of the item function's third parameter
		 * @param  cClass the class of the item function's fourth parameter
		 * @param  dClass the class of the item function's fifth parameter
		 * @return the updated builder
		 */
		public <A, B, C, D> Builder<T, S> addGetter(
			PentaFunction<S, A, B, C, D, T> pentaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass) {

			return this;
		}

		/**
		 * Adds a route to an item function with three extra parameters.
		 *
		 * @param  tetraFunction the function that calculates the item
		 * @param  aClass the class of the item function's second parameter
		 * @param  bClass the class of the item function's third parameter
		 * @param  cClass the class of the item function's fourth parameter
		 * @return the updated builder
		 */
		public <A, B, C> Builder<T, S> addGetter(
			TetraFunction<S, A, B, C, T> tetraFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass) {

			return this;
		}

		/**
		 * Adds a route to an item function with two extra parameters.
		 *
		 * @param  triFunction the function that calculates the item
		 * @param  aClass the class of the item function's second parameter
		 * @param  bClass the class of the item function's third parameter
		 * @return the updated builder
		 */
		public <A, B> Builder<T, S> addGetter(
			TriFunction<S, A, B, T> triFunction, Class<A> aClass,
			Class<B> bClass) {

			return this;
		}

		/**
		 * Adds a route to a remover function with one extra parameter.
		 *
		 * @param  biConsumer the remover function that removes the item
		 * @param  aClass the class of the item remover function's second
		 *         parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @return the updated builder
		 * @review
		 */
		public <A> Builder<T, S> addRemover(
			BiConsumer<S, A> biConsumer, Class<A> aClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction) {

			_deleteItemPermissionFunction = permissionBiFunction;

			return this;
		}

		/**
		 * Adds a route to a remover function with none extra parameters.
		 *
		 * @param  consumer the remover function that removes the item
		 * @param  permissionBiFunction the permission function for this route
		 * @return the updated builder
		 * @review
		 */
		public Builder<T, S> addRemover(
			Consumer<S> consumer,
			BiFunction<Credentials, S, Boolean> permissionBiFunction) {

			_deleteItemPermissionFunction = permissionBiFunction;

			_deleteItemConsumer = httpServletRequest -> path -> consumer.accept(
				_identifierFunction.apply(path));

			return this;
		}

		/**
		 * Adds a route to a remover function with four extra parameters.
		 *
		 * @param  pentaConsumer the remover function that removes the item
		 * @param  aClass the class of the item remover function's second
		 *         parameter
		 * @param  bClass the class of the item remover function's third
		 *         parameter
		 * @param  cClass the class of the item remover function's fourth
		 *         parameter
		 * @param  dClass the class of the item remover function's fifth
		 *         parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @return the updated builder
		 * @review
		 */
		public <A, B, C, D> Builder<T, S> addRemover(
			PentaConsumer<S, A, B, C, D> pentaConsumer, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction) {

			_deleteItemPermissionFunction = permissionBiFunction;

			return this;
		}

		/**
		 * Adds a route to a remover function with three extra parameters.
		 *
		 * @param  tetraConsumer the remover function that removes the item
		 * @param  aClass the class of the item remover function's second
		 *         parameter
		 * @param  bClass the class of the item remover function's third
		 *         parameter
		 * @param  cClass the class of the item remover function's fourth
		 *         parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @return the updated builder
		 * @review
		 */
		public <A, B, C> Builder<T, S> addRemover(
			TetraConsumer<S, A, B, C> tetraConsumer, Class<A> aClass,
			Class<B> bClass, Class<C> cClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction) {

			_deleteItemPermissionFunction = permissionBiFunction;

			return this;
		}

		/**
		 * Adds a route to a remover function with two extra parameters.
		 *
		 * @param  triConsumer the remover function that removes the item
		 * @param  aClass the class of the item remover function's second
		 *         parameter
		 * @param  bClass the class of the item remover function's third
		 *         parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @return the updated builder
		 * @review
		 */
		public <A, B> Builder<T, S> addRemover(
			TriConsumer<S, A, B> triConsumer, Class<A> aClass, Class<B> bClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction) {

			_deleteItemPermissionFunction = permissionBiFunction;

			return this;
		}

		/**
		 * Adds a route to a updater function with none extra parameters.
		 *
		 * @param  biFunction the updater function that removes the item
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <R> Builder<T, S> addUpdater(
			BiFunction<S, R, T> biFunction,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_updateItemPermissionFunction = permissionBiFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("u", _name)));

			return this;
		}

		/**
		 * Adds a route to a updater function with four extra parameters.
		 *
		 * @param  hexaFunction the updater function that removes the item
		 * @param  aClass the class of the item updater function's third
		 *         parameter
		 * @param  bClass the class of the item updater function's fourth
		 *         parameter
		 * @param  cClass the class of the item updater function's fifth
		 *         parameter
		 * @param  dClass the class of the item updater function's sixth
		 *         parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <A, B, C, D, R> Builder<T, S> addUpdater(
			HexaFunction<S, R, A, B, C, D, T> hexaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_updateItemPermissionFunction = permissionBiFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("u", _name)));

			return this;
		}

		/**
		 * Adds a route to a updater function with three extra parameters.
		 *
		 * @param  pentaFunction the updater function that removes the item
		 * @param  aClass the class of the item updater function's third
		 *         parameter
		 * @param  bClass the class of the item updater function's fourth
		 *         parameter
		 * @param  cClass the class of the item updater function's fifth
		 *         parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <A, B, C, R> Builder<T, S> addUpdater(
			PentaFunction<S, R, A, B, C, T> pentaFunction, Class<A> aClass,
			Class<B> bClass, Class<C> cClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_updateItemPermissionFunction = permissionBiFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("u", _name)));

			return this;
		}

		/**
		 * Adds a route to a updater function with two extra parameters.
		 *
		 * @param  tetraFunction the updater function that removes the item
		 * @param  aClass the class of the item updater function's third
		 *         parameter
		 * @param  bClass the class of the item updater function's fourth
		 *         parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <A, B, R> Builder<T, S> addUpdater(
			TetraFunction<S, R, A, B, T> tetraFunction, Class<A> aClass,
			Class<B> bClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_updateItemPermissionFunction = permissionBiFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("u", _name)));

			return this;
		}

		/**
		 * Adds a route to a updater function with one extra parameter.
		 *
		 * @param  triFunction the updater function that removes the item
		 * @param  aClass the class of the item updater function's third
		 *         parameter
		 * @param  permissionBiFunction the permission function for this route
		 * @param  formBuilderFunction the function that creates the form for
		 *         this operation
		 * @return the updated builder
		 * @review
		 */
		public <A, R> Builder<T, S> addUpdater(
			TriFunction<S, R, A, T> triFunction, Class<A> aClass,
			BiFunction<Credentials, S, Boolean> permissionBiFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_updateItemPermissionFunction = permissionBiFunction;

			_form = formBuilderFunction.apply(
				new Form.Builder<>(Arrays.asList("u", _name)));

			_updateItemFunction =
				httpServletRequest -> path -> body -> RoutesBuilderUtil.provide(
					_provideFunction.apply(httpServletRequest), aClass,
					Credentials.class,
					a -> credentials -> triFunction.andThen(
						t -> new SingleModel<>(
							t, _name, _getOperations(credentials, path))
					).apply(
						_identifierFunction.apply(path),
						Unsafe.unsafeCast(_form.get(body)), a
					));

			return this;
		}

		/**
		 * Constructs the {@link ItemRoutes} instance with the information
		 * provided to the builder.
		 *
		 * @return the {@code Routes} instance
		 */
		public ItemRoutes<T> build() {
			return new ItemRoutes<>(this);
		}

		private List<Operation> _getOperations(
			Credentials credentials, Path path) {

			List<Operation> operations = new ArrayList<>();

			S s = _identifierFunction.apply(path);

			Optional<BiFunction<Credentials, S, Boolean>> optional1 =
				Optional.ofNullable(_deleteItemPermissionFunction);

			optional1.filter(
				function -> function.apply(credentials, s)
			).ifPresent(
				__ -> operations.add(new Operation(DELETE, _name + "/delete"))
			);

			Optional<BiFunction<Credentials, S, Boolean>> optional2 =
				Optional.ofNullable(_updateItemPermissionFunction);

			optional2.filter(
				function -> function.apply(credentials, s)
			).ifPresent(
				__ -> operations.add(
					new Operation(_form, PUT, _name + "/update"))
			);

			return operations;
		}

		private DeleteItemConsumer _deleteItemConsumer;
		private BiFunction<Credentials, S, Boolean>
			_deleteItemPermissionFunction;
		private Form _form;
		private final IdentifierFunction<S> _identifierFunction;
		private final String _name;
		private final ProvideFunction _provideFunction;
		private GetItemFunction<T> _singleModelFunction;
		private UpdateItemFunction<T> _updateItemFunction;
		private BiFunction<Credentials, S, Boolean>
			_updateItemPermissionFunction;

	}

	private final DeleteItemConsumer _deleteItemConsumer;
	private final Form _form;
	private final GetItemFunction<T> _singleModelFunction;
	private final UpdateItemFunction<T> _updateItemFunction;

}