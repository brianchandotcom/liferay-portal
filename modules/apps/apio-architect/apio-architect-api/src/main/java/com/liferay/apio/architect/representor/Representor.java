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

package com.liferay.apio.architect.representor;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.related.RelatedCollection;

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
 * Holds information about the metadata supported for a resource.
 *
 * <p>
 * Instances of this interface should always be created by using a {@link
 * Representor.Builder}.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @see    Representor.Builder
 */
public class Representor<T> extends BaseRepresentor<T> {

	/**
	 * Returns the model's identifier.
	 *
	 * @param  model the model instance
	 * @return the model's identifier
	 */
	public Object getIdentifier(T model) {
		return _identifierFunction.apply(model);
	}

	/**
	 * Returns the related collections.
	 *
	 * @return the related collections
	 */
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

	/**
	 * Creates generic representations of your domain models that Apio
	 * hypermedia writers can understand.
	 *
	 * @param <T> the model's type
	 */
	public static class Builder<T, U> extends BaseBuilder<T, Representor<T>> {

		public Builder(Class<? extends Identifier<U>> identifierClass) {
			this(
				identifierClass,
				(clazz, relatedCollection) -> {
				},
				Collections::emptyList);
		}

		public Builder(
			Class<? extends Identifier<U>> identifierClass,
			BiConsumer<Class<?>, RelatedCollection<?>> biConsumer,
			Supplier<List<RelatedCollection<?>>> supplier) {

			super(new Representor<>(supplier));

			_identifierClass = identifierClass;
			_biConsumer = biConsumer;
		}

		/**
		 * Adds a type for the model.
		 *
		 * @param  type the type name
		 * @param  types the rest of the types
		 * @return the builder's step
		 */
		public IdentifierStep types(String type, String... types) {
			baseRepresentor.addTypes(type, types);

			return new IdentifierStep();
		}

		public class FirstStep extends BaseFirstStep<FirstStep> {

			/**
			 * Adds information about the bidirectional relation of a linked
			 * resource in the actual resource and a collection of items in the
			 * related resource.
			 *
			 * @param  key the relation's name in the resource
			 * @param  relatedKey the relation's name in the related resource
			 * @param  identifierClass the related resource identifier's class
			 * @param  identifierFunction the function used to get the related
			 *         resource's identifier
			 * @return the builder's step
			 */
			public <S> FirstStep addBidirectionalModel(
				String key, String relatedKey,
				Class<? extends Identifier<S>> identifierClass,
				Function<T, S> identifierFunction) {

				RelatedCollection<?> relatedCollection =
					new RelatedCollection<>(relatedKey, _identifierClass);

				_biConsumer.accept(identifierClass, relatedCollection);

				baseRepresentor.addRelatedModel(
					key, identifierClass, identifierFunction);

				return this;
			}

			/**
			 * Adds information about a related collection.
			 *
			 * @param  key the relation's name
			 * @param  itemIdentifierClass the class of the collection items'
			 *         identifier
			 * @return the builder's step
			 */
			public <S extends Identifier> FirstStep addRelatedCollection(
				String key, Class<S> itemIdentifierClass) {

				baseRepresentor._addRelatedCollection(key, itemIdentifierClass);

				return this;
			}

			@Override
			public FirstStep getThis() {
				return this;
			}

		}

		public class IdentifierStep {

			/**
			 * Provides a lambda function that can be used to obtain a model's
			 * identifier.
			 *
			 * @param  identifierFunction lambda function used to obtain a
			 *         model's identifier
			 * @return the builder's next step
			 */
			public FirstStep identifier(Function<T, U> identifierFunction) {
				baseRepresentor._setIdentifierFunction(identifierFunction);

				return new FirstStep();
			}

			private IdentifierStep() {
			}

		}

		private final BiConsumer<Class<?>, RelatedCollection<?>> _biConsumer;
		private final Class<? extends Identifier> _identifierClass;

	}

	private Representor(Supplier<List<RelatedCollection<?>>> supplier) {
		_supplier = supplier;

		_relatedCollections = new ArrayList<>();
	}

	private <S extends Identifier> void _addRelatedCollection(
		String key, Class<S> itemIdentifierClass) {

		RelatedCollection<S> relatedCollection = new RelatedCollection<>(
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