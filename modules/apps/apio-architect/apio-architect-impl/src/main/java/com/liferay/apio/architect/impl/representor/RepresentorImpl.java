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

package com.liferay.apio.architect.impl.representor;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.impl.related.RelatedCollectionImpl;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.representor.Representor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Alejandro Hernández
 */
public class RepresentorImpl<T>
	extends BaseRepresentorImpl<T> implements Representor<T> {

	@Override
	public Object getIdentifier(T model) {
		return _identifierFunction.apply(model);
	}

	@Override
	public Stream<RelatedCollection<? extends Identifier>>
		getRelatedCollections() {

		return Stream.of(
			_relatedCollections, _supplier.get()
		).filter(
			Objects::nonNull
		).flatMap(
			Collection::stream
		);
	}

	@Override
	public boolean isNested() {
		return false;
	}

	public static class BuilderImpl<T, S>
		extends BaseBuilderImpl<T, RepresentorImpl<T>>
		implements Builder<T, S> {

		public BuilderImpl(Class<? extends Identifier<S>> identifierClass) {
			this(
				identifierClass,
				(clazz, relatedCollection) -> {
				},
				Collections::emptyList);
		}

		public BuilderImpl(
			Class<? extends Identifier<S>> identifierClass,
			BiConsumer<Class<?>, RelatedCollection<?>> biConsumer,
			Supplier<List<RelatedCollection<?>>> supplier) {

			super(new RepresentorImpl<>(supplier));

			_identifierClass = identifierClass;
			_biConsumer = biConsumer;
		}

		@Override
		public IdentifierStepImpl types(String type, String... types) {
			baseRepresentor.addTypes(type, types);

			return new IdentifierStepImpl();
		}

		public class FirstStepImpl
			extends BaseFirstStepImpl<Representor<T>, FirstStep<T>>
			implements FirstStep<T> {

			@Override
			public <U> FirstStep<T> addBidirectionalModel(
				String key, String relatedKey,
				Class<? extends Identifier<U>> identifierClass,
				Function<T, U> identifierFunction) {

				RelatedCollection<?> relatedCollection =
					new RelatedCollectionImpl<>(relatedKey, _identifierClass);

				_biConsumer.accept(identifierClass, relatedCollection);

				baseRepresentor.addRelatedModel(
					key, identifierClass, identifierFunction);

				return this;
			}

			@Override
			public <U extends Identifier> FirstStep<T> addRelatedCollection(
				String key, Class<U> itemIdentifierClass) {

				baseRepresentor._addRelatedCollection(key, itemIdentifierClass);

				return this;
			}

			@Override
			public FirstStepImpl getThis() {
				return this;
			}

		}

		public class IdentifierStepImpl implements IdentifierStep<T, S> {

			@Override
			public FirstStep<T> identifier(Function<T, S> identifierFunction) {
				baseRepresentor._setIdentifierFunction(identifierFunction);

				return new FirstStepImpl();
			}

			private IdentifierStepImpl() {
			}

		}

		private final BiConsumer<Class<?>, RelatedCollection<?>> _biConsumer;
		private final Class<? extends Identifier> _identifierClass;

	}

	private RepresentorImpl(Supplier<List<RelatedCollection<?>>> supplier) {
		_supplier = supplier;

		_relatedCollections = new ArrayList<>();
	}

	private <S extends Identifier> void _addRelatedCollection(
		String key, Class<S> itemIdentifierClass) {

		RelatedCollection<S> relatedCollection = new RelatedCollectionImpl<>(
			key, itemIdentifierClass);

		_relatedCollections.add(relatedCollection);
	}

	private void _setIdentifierFunction(Function<T, ?> identifierFunction) {
		_identifierFunction = identifierFunction;
	}

	private Function<T, ?> _identifierFunction;
	private final List<RelatedCollection<?>> _relatedCollections;
	private final Supplier<List<RelatedCollection<?>>> _supplier;

}