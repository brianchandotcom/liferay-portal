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

package com.liferay.apio.architect.impl.routes;

import static com.liferay.apio.architect.impl.routes.RoutesBuilderUtil.provide;
import static com.liferay.apio.architect.impl.routes.RoutesBuilderUtil.provideConsumer;
import static com.liferay.apio.architect.operation.HTTPMethod.DELETE;
import static com.liferay.apio.architect.operation.HTTPMethod.PUT;

import com.liferay.apio.architect.alias.IdentifierFunction;
import com.liferay.apio.architect.alias.form.FormBuilderFunction;
import com.liferay.apio.architect.alias.routes.DeleteItemConsumer;
import com.liferay.apio.architect.alias.routes.GetItemFunction;
import com.liferay.apio.architect.alias.routes.UpdateItemFunction;
import com.liferay.apio.architect.alias.routes.permission.HasRemovePermissionFunction;
import com.liferay.apio.architect.alias.routes.permission.HasUpdatePermissionFunction;
import com.liferay.apio.architect.consumer.throwable.ThrowableBiConsumer;
import com.liferay.apio.architect.consumer.throwable.ThrowableConsumer;
import com.liferay.apio.architect.consumer.throwable.ThrowablePentaConsumer;
import com.liferay.apio.architect.consumer.throwable.ThrowableTetraConsumer;
import com.liferay.apio.architect.consumer.throwable.ThrowableTriConsumer;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.function.throwable.ThrowableBiFunction;
import com.liferay.apio.architect.function.throwable.ThrowableFunction;
import com.liferay.apio.architect.function.throwable.ThrowableHexaFunction;
import com.liferay.apio.architect.function.throwable.ThrowablePentaFunction;
import com.liferay.apio.architect.function.throwable.ThrowableTetraFunction;
import com.liferay.apio.architect.function.throwable.ThrowableTriFunction;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.impl.alias.ProvideFunction;
import com.liferay.apio.architect.impl.form.FormImpl;
import com.liferay.apio.architect.impl.operation.OperationImpl;
import com.liferay.apio.architect.impl.single.model.SingleModelImpl;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.uri.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 */
public class ItemRoutesImpl<T, S> implements ItemRoutes<T, S> {

	public ItemRoutesImpl(BuilderImpl<T, S> builderImpl) {
		_deleteItemConsumer = builderImpl._deleteItemConsumer;
		_form = builderImpl._form;
		_singleModelFunction = builderImpl._singleModelFunction;
		_updateItemFunction = builderImpl._updateItemFunction;
	}

	@Override
	public Optional<DeleteItemConsumer<S>> getDeleteConsumerOptional() {
		return Optional.ofNullable(_deleteItemConsumer);
	}

	@Override
	public Optional<Form> getFormOptional() {
		return Optional.ofNullable(_form);
	}

	@Override
	public Optional<GetItemFunction<T, S>> getItemFunctionOptional() {
		return Optional.ofNullable(_singleModelFunction);
	}

	@Override
	public Optional<UpdateItemFunction<T, S>> getUpdateItemFunctionOptional() {
		return Optional.ofNullable(_updateItemFunction);
	}

	/**
	 * Creates the {@code ItemRoutes} of an {@link
	 * com.liferay.apio.architect.router.ItemRouter}.
	 *
	 * @param <T> the model's type
	 * @param <S> the type of the model's identifier (e.g., {@code Long}, {@code
	 *        String}, etc.)
	 */
	@SuppressWarnings("unused")
	public static class BuilderImpl<T, S> implements Builder<T, S> {

		public BuilderImpl(
			String name, ProvideFunction provideFunction,
			Consumer<String> neededProviderConsumer,
			Function<Path, ?> identifierFunction) {

			_name = name;
			_provideFunction = provideFunction;
			_neededProviderConsumer = neededProviderConsumer;

			_identifierFunction = identifierFunction::apply;
		}

		@Override
		public <A> Builder<T, S> addGetter(
			ThrowableBiFunction<S, A, T> throwableBiFunction, Class<A> aClass) {

			_neededProviderConsumer.accept(aClass.getName());

			_singleModelFunction = httpServletRequest -> s -> provide(
				_provideFunction.apply(httpServletRequest), aClass,
				Credentials.class,
				(a, credentials) -> throwableBiFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s, a
				));

			return this;
		}

		@Override
		public Builder<T, S> addGetter(
			ThrowableFunction<S, T> throwableFunction) {

			_singleModelFunction = httpServletRequest -> s -> provide(
				_provideFunction.apply(httpServletRequest), Credentials.class,
				credentials -> throwableFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s
				));

			return this;
		}

		@Override
		public <A, B, C, D> Builder<T, S> addGetter(
			ThrowablePentaFunction<S, A, B, C, D, T> throwablePentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			Class<D> dClass) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());
			_neededProviderConsumer.accept(dClass.getName());

			_singleModelFunction = httpServletRequest -> s -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass, dClass, Credentials.class,
				(a, b, c, d, credentials) -> throwablePentaFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s, a, b, c, d
				));

			return this;
		}

		@Override
		public <A, B, C> Builder<T, S> addGetter(
			ThrowableTetraFunction<S, A, B, C, T> throwableTetraFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());

			_singleModelFunction = httpServletRequest -> s -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass, Credentials.class,
				(a, b, c, credentials) -> throwableTetraFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s, a, b, c
				));

			return this;
		}

		@Override
		public <A, B> Builder<T, S> addGetter(
			ThrowableTriFunction<S, A, B, T> throwableTriFunction,
			Class<A> aClass, Class<B> bClass) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());

			_singleModelFunction = httpServletRequest -> s -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				Credentials.class,
				(a, b, credentials) -> throwableTriFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s, a, b
				));

			return this;
		}

		@Override
		public <A> Builder<T, S> addRemover(
			ThrowableBiConsumer<S, A> throwableBiConsumer, Class<A> aClass,
			HasRemovePermissionFunction<S> hasRemovePermissionFunction) {

			_neededProviderConsumer.accept(aClass.getName());

			_hasRemovePermissionFunction = hasRemovePermissionFunction;

			_deleteItemConsumer = httpServletRequest -> s -> provideConsumer(
				_provideFunction.apply(httpServletRequest), aClass,
				a -> throwableBiConsumer.accept(s, a));

			return this;
		}

		@Override
		public Builder<T, S> addRemover(
			ThrowableConsumer<S> throwableConsumer,
			HasRemovePermissionFunction<S> hasRemovePermissionFunction) {

			_hasRemovePermissionFunction = hasRemovePermissionFunction;

			_deleteItemConsumer = __ -> throwableConsumer;

			return this;
		}

		@Override
		public <A, B, C, D> Builder<T, S> addRemover(
			ThrowablePentaConsumer<S, A, B, C, D> throwablePentaConsumer,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			HasRemovePermissionFunction<S> hasRemovePermissionFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());
			_neededProviderConsumer.accept(dClass.getName());

			_hasRemovePermissionFunction = hasRemovePermissionFunction;

			_deleteItemConsumer = httpServletRequest -> s -> provideConsumer(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass, dClass,
				(a, b, c, d) -> throwablePentaConsumer.accept(s, a, b, c, d));

			return this;
		}

		@Override
		public <A, B, C> Builder<T, S> addRemover(
			ThrowableTetraConsumer<S, A, B, C> throwableTetraConsumer,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			HasRemovePermissionFunction<S> hasRemovePermissionFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());

			_hasRemovePermissionFunction = hasRemovePermissionFunction;

			_deleteItemConsumer = httpServletRequest -> s -> provideConsumer(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass, (a, b, c) -> throwableTetraConsumer.accept(s, a, b, c));

			return this;
		}

		@Override
		public <A, B> Builder<T, S> addRemover(
			ThrowableTriConsumer<S, A, B> throwableTriConsumer, Class<A> aClass,
			Class<B> bClass,
			HasRemovePermissionFunction<S> hasRemovePermissionFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());

			_hasRemovePermissionFunction = hasRemovePermissionFunction;

			_deleteItemConsumer = httpServletRequest -> s -> provideConsumer(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				(a, b) -> throwableTriConsumer.accept(s, a, b));

			return this;
		}

		@Override
		public <R> Builder<T, S> addUpdater(
			ThrowableBiFunction<S, R, T> throwableBiFunction,
			HasUpdatePermissionFunction<S> hasUpdatePermissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_hasUpdatePermissionFunction = hasUpdatePermissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new FormImpl.BuilderImpl<>(
					Arrays.asList("u", _name), _identifierFunction));

			_form = form;

			_updateItemFunction = httpServletRequest -> s -> body -> provide(
				_provideFunction.apply(httpServletRequest), Credentials.class,
				credentials -> throwableBiFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s, form.get(body)
				));

			return this;
		}

		@Override
		public <A, B, C, D, R> Builder<T, S> addUpdater(
			ThrowableHexaFunction<S, R, A, B, C, D, T> throwableHexaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			HasUpdatePermissionFunction<S> hasUpdatePermissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());
			_neededProviderConsumer.accept(dClass.getName());

			_hasUpdatePermissionFunction = hasUpdatePermissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new FormImpl.BuilderImpl<>(
					Arrays.asList("u", _name), _identifierFunction));

			_form = form;

			_updateItemFunction = httpServletRequest -> s -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass, dClass, Credentials.class,
				(a, b, c, d, credentials) -> throwableHexaFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s, form.get(body), a, b, c, d
				));

			return this;
		}

		@Override
		public <A, B, C, R> Builder<T, S> addUpdater(
			ThrowablePentaFunction<S, R, A, B, C, T> throwablePentaFunction,
			Class<A> aClass, Class<B> bClass, Class<C> cClass,
			HasUpdatePermissionFunction<S> hasUpdatePermissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());
			_neededProviderConsumer.accept(cClass.getName());

			_hasUpdatePermissionFunction = hasUpdatePermissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new FormImpl.BuilderImpl<>(
					Arrays.asList("u", _name), _identifierFunction));

			_form = form;

			_updateItemFunction = httpServletRequest -> s -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				cClass, Credentials.class,
				(a, b, c, credentials) -> throwablePentaFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s, form.get(body), a, b, c
				));

			return this;
		}

		@Override
		public <A, B, R> Builder<T, S> addUpdater(
			ThrowableTetraFunction<S, R, A, B, T> throwableTetraFunction,
			Class<A> aClass, Class<B> bClass,
			HasUpdatePermissionFunction<S> hasUpdatePermissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());
			_neededProviderConsumer.accept(bClass.getName());

			_hasUpdatePermissionFunction = hasUpdatePermissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new FormImpl.BuilderImpl<>(
					Arrays.asList("u", _name), _identifierFunction));

			_form = form;

			_updateItemFunction = httpServletRequest -> s -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass, bClass,
				Credentials.class,
				(a, b, credentials) -> throwableTetraFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s, form.get(body), a, b
				));

			return this;
		}

		@Override
		public <A, R> Builder<T, S> addUpdater(
			ThrowableTriFunction<S, R, A, T> throwableTriFunction,
			Class<A> aClass,
			HasUpdatePermissionFunction<S> hasUpdatePermissionFunction,
			FormBuilderFunction<R> formBuilderFunction) {

			_neededProviderConsumer.accept(aClass.getName());

			_hasUpdatePermissionFunction = hasUpdatePermissionFunction;

			Form<R> form = formBuilderFunction.apply(
				new FormImpl.BuilderImpl<>(
					Arrays.asList("u", _name), _identifierFunction));

			_form = form;

			_updateItemFunction = httpServletRequest -> s -> body -> provide(
				_provideFunction.apply(httpServletRequest), aClass,
				Credentials.class,
				(a, credentials) -> throwableTriFunction.andThen(
					t -> new SingleModelImpl<>(
						t, _name, _getOperations(credentials, s))
				).apply(
					s, form.get(body), a
				));

			return this;
		}

		@Override
		public ItemRoutes<T, S> build() {
			return new ItemRoutesImpl<>(this);
		}

		private List<Operation> _getOperations(
			Credentials credentials, S identifier) {

			List<Operation> operations = new ArrayList<>();

			Optional.ofNullable(
				_hasRemovePermissionFunction
			).filter(
				function -> Try.fromFallible(
					() -> function.apply(credentials, identifier)
				).orElse(
					false
				)
			).ifPresent(
				__ -> operations.add(
					new OperationImpl(DELETE, _name + "/delete"))
			);

			Optional.ofNullable(
				_hasUpdatePermissionFunction
			).filter(
				function -> Try.fromFallible(
					() -> function.apply(credentials, identifier)
				).orElse(
					false
				)
			).ifPresent(
				__ -> operations.add(
					new OperationImpl(_form, PUT, _name + "/update"))
			);

			return operations;
		}

		private DeleteItemConsumer<S> _deleteItemConsumer;
		private Form _form;
		private HasRemovePermissionFunction<S> _hasRemovePermissionFunction;
		private HasUpdatePermissionFunction<S> _hasUpdatePermissionFunction;
		private final IdentifierFunction<?> _identifierFunction;
		private final String _name;
		private final Consumer<String> _neededProviderConsumer;
		private final ProvideFunction _provideFunction;
		private GetItemFunction<T, S> _singleModelFunction;
		private UpdateItemFunction<T, S> _updateItemFunction;

	}

	private final DeleteItemConsumer<S> _deleteItemConsumer;
	private final Form _form;
	private final GetItemFunction<T, S> _singleModelFunction;
	private final UpdateItemFunction<T, S> _updateItemFunction;

}