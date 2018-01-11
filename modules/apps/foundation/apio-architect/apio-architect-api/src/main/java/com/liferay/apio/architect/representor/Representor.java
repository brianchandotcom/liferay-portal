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

import static com.liferay.apio.architect.date.DateTransformer.asString;

import com.liferay.apio.architect.alias.BinaryFunction;
import com.liferay.apio.architect.consumer.TriConsumer;
import com.liferay.apio.architect.language.Language;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.related.RelatedModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
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
 * @param  <S> the model identifier's type ({@link Long}, {@link String}, etc.)
 * @see    Representor.Builder
 * @review
 */
public class Representor<T, S> {

	public Representor(
		Class<S> identifierClass,
		Supplier<List<RelatedCollection<T, ?>>> relatedCollectionsSupplier) {

		_identifierClass = identifierClass;
		_relatedCollectionsSupplier = relatedCollectionsSupplier;
	}

	/**
	 * Returns the binary resources linked to a model.
	 *
	 * @return the binary resources linked to a model
	 */
	public Map<String, BinaryFunction<T>> getBinaryFunctions() {
		return _binaryFunctions;
	}

	/**
	 * Returns the map containing the boolean field names and the functions to
	 * get those fields.
	 *
	 * @return the map containing the boolean field names and functions
	 */
	public Map<String, Function<T, Boolean>> getBooleanFunctions() {
		return _booleanFunctions;
	}

	/**
	 * Returns the model's identifier.
	 *
	 * @param  model the model instance
	 * @return the model's identifier
	 */
	public S getIdentifier(T model) {
		return _identifierFunction.apply(model);
	}

	/**
	 * Returns the identifier class.
	 *
	 * @return the identifier class
	 */
	public Class<S> getIdentifierClass() {
		return _identifierClass;
	}

	/**
	 * Returns the links.
	 *
	 * @return the links
	 */
	public Map<String, String> getLinks() {
		return _links;
	}

	/**
	 * Returns a map containing the localized string field names and the
	 * functions to get those fields.
	 *
	 * @return the map containing the localized string field names and functions
	 */
	public Map<String, BiFunction<T, Language, String>>
		getLocalizedStringFunctions() {

		return _localizedStringFunctions;
	}

	/**
	 * Returns the map containing the number field names and the functions to
	 * get those fields.
	 *
	 * @return the map containing the number field names and functions
	 */
	public Map<String, Function<T, Number>> getNumberFunctions() {
		return _numberFunctions;
	}

	/**
	 * Returns the related collections.
	 *
	 * @return the related collections
	 */
	public Stream<RelatedCollection<T, ?>> getRelatedCollections() {
		Stream<List<RelatedCollection<T, ?>>> stream = Stream.of(
			_relatedCollections, _relatedCollectionsSupplier.get());

		return stream.filter(
			Objects::nonNull
		).flatMap(
			Collection::stream
		);
	}

	/**
	 * Returns the related models.
	 *
	 * @return the related models
	 */
	public List<RelatedModel<T, ?>> getRelatedModels() {
		return _relatedModels;
	}

	/**
	 * Returns the map containing the string field names and the functions to
	 * get those fields.
	 *
	 * @return the map containing the string field names and functions
	 */
	public Map<String, Function<T, String>> getStringFunctions() {
		return _stringFunctions;
	}

	/**
	 * Returns the types.
	 *
	 * @return the types
	 */
	public List<String> getTypes() {
		return _types;
	}

	/**
	 * Creates generic representations of your domain models that Apio
	 * hypermedia writers can understand.
	 */
	public static class Builder<T, U> {

		public Builder(Class<U> identifierClass) {
			Supplier<List<RelatedCollection<T, ?>>> listSupplier =
				Collections::emptyList;

			_addRelatedCollectionTriConsumer = TriConsumer.empty();
			_representor = new Representor<>(identifierClass, listSupplier);
		}

		public Builder(
			Class<U> identifierClass,
			TriConsumer<String, Class<?>, Function<Object, Object>>
				addRelatedCollectionTriConsumer,
			Supplier<List<RelatedCollection<T, ?>>>
				relatedCollectionsSupplier) {

			_addRelatedCollectionTriConsumer = addRelatedCollectionTriConsumer;
			_representor = new Representor<>(
				identifierClass, relatedCollectionsSupplier);
		}

		/**
		 * Adds a type for the model.
		 *
		 * @param  type the type name
		 * @return the builder's step
		 */
		public IdentifierStep types(String type, String... types) {
			_representor._types.add(type);

			Collections.addAll(_representor._types, types);

			return new IdentifierStep();
		}

		public class FirstStep {

			/**
			 * Adds information about the bidirectional relation of a linked
			 * model in the resource and a collection of items in the related
			 * resource.
			 *
			 * @param  key the relation's name in the resource
			 * @param  relatedKey the relation's name in the related resource
			 * @param  modelClass the related model's class
			 * @param  modelFunction the function used to get the related model
			 * @param  identifierFunction the function used to get the
			 *         collection's identifier
			 * @return the builder's step
			 */
			@SuppressWarnings("unchecked")
			public <S> FirstStep addBidirectionalModel(
				String key, String relatedKey, Class<S> modelClass,
				Function<T, Optional<S>> modelFunction,
				Function<S, Object> identifierFunction) {

				_representor._relatedModels.add(
					new RelatedModel<>(key, modelClass, modelFunction));

				_addRelatedCollectionTriConsumer.accept(
					relatedKey, modelClass,
					(Function<Object, Object>)identifierFunction);

				return this;
			}

			/**
			 * Adds binary files to a resource.
			 *
			 * @param  key the binary resource's name
			 * @param  binaryFunction the function used to get the binaries
			 * @return the builder's step
			 */
			public FirstStep addBinary(
				String key, BinaryFunction<T> binaryFunction) {

				_representor._binaryFunctions.put(key, binaryFunction);

				return this;
			}

			/**
			 * Adds information about a resource's boolean field.
			 *
			 * @param  key the field's name
			 * @param  booleanFunction the function used to get the boolean
			 *         value
			 * @return the builder's step
			 */
			public FirstStep addBoolean(
				String key, Function<T, Boolean> booleanFunction) {

				_representor._booleanFunctions.put(key, booleanFunction);

				return this;
			}

			/**
			 * Adds information about a resource's date field.
			 *
			 * @param  key the field's name
			 * @param  dateFunction the function used to get the date value
			 * @return the builder's step
			 */
			public FirstStep addDate(
				String key, Function<T, Date> dateFunction) {

				Function<Date, String> formatFunction = date -> {
					if (date == null) {
						return null;
					}

					return asString(date);
				};

				_representor._stringFunctions.put(
					key, dateFunction.andThen(formatFunction));

				return this;
			}

			/**
			 * Adds information about a resource link.
			 *
			 * @param  key the field's name
			 * @param  url the link's URL
			 * @return the builder's step
			 */
			public FirstStep addLink(String key, String url) {
				_representor._links.put(key, url);

				return this;
			}

			/**
			 * Adds information about an embeddable related model.
			 *
			 * @param  key the relation's name
			 * @param  modelClass the related model's class
			 * @param  modelFunction the function used to get the related model
			 * @return the builder's step
			 */
			public <S> FirstStep addLinkedModel(
				String key, Class<S> modelClass,
				Function<T, Optional<S>> modelFunction) {

				_representor._relatedModels.add(
					new RelatedModel<>(key, modelClass, modelFunction));

				return this;
			}

			/**
			 * Provides information about a resource localized string field.
			 *
			 * @param  key the field's name
			 * @param  stringFunction the function used to get the string value
			 * @return builder's step
			 */
			public FirstStep addLocalizedString(
				String key, BiFunction<T, Language, String> stringFunction) {

				_representor._localizedStringFunctions.put(key, stringFunction);

				return this;
			}

			/**
			 * Adds information about a resource's number field.
			 *
			 * @param  key the field's name
			 * @param  numberFunction the function used to get the number's
			 *         value
			 * @return the builder's step
			 */
			public FirstStep addNumber(
				String key, Function<T, Number> numberFunction) {

				_representor._numberFunctions.put(key, numberFunction);

				return this;
			}

			/**
			 * Adds information about a related collection.
			 *
			 * @param  key the relation's name
			 * @param  modelClass the class of the collection's related models
			 * @param  identifierFunction the function used to get the
			 *         collection's identifier
			 * @return the builder's step
			 */
			public <S> FirstStep addRelatedCollection(
				String key, Class<S> modelClass,
				Function<T, Object> identifierFunction) {

				_representor._relatedCollections.add(
					new RelatedCollection<>(
						key, modelClass, identifierFunction));

				return this;
			}

			/**
			 * Adds information about a resource's string field.
			 *
			 * @param  key the field's name
			 * @param  stringFunction the function used to get the string's
			 *         value
			 * @return the builder's step
			 */
			public FirstStep addString(
				String key, Function<T, String> stringFunction) {

				_representor._stringFunctions.put(key, stringFunction);

				return this;
			}

			/**
			 * Constructs and returns a {@link Representor} instance with the
			 * information provided to the builder.
			 *
			 * @return the {@code Representor} instance
			 */
			public Representor<T, U> build() {
				return _representor;
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
				_representor._identifierFunction = identifierFunction;

				return new FirstStep();
			}

		}

		private final TriConsumer<String, Class<?>,
			Function<Object, Object>> _addRelatedCollectionTriConsumer;
		private final Representor<T, U> _representor;

	}

	private Map<String, BinaryFunction<T>> _binaryFunctions = new HashMap<>();
	private Map<String, Function<T, Boolean>> _booleanFunctions =
		new HashMap<>();
	private final Class<S> _identifierClass;
	private Function<T, S> _identifierFunction;
	private Map<String, String> _links = new HashMap<>();
	private Map<String, BiFunction<T, Language, String>>
		_localizedStringFunctions = new HashMap<>();
	private Map<String, Function<T, Number>> _numberFunctions = new HashMap<>();
	private List<RelatedCollection<T, ?>> _relatedCollections =
		new ArrayList<>();
	private final Supplier<List<RelatedCollection<T, ?>>>
		_relatedCollectionsSupplier;
	private List<RelatedModel<T, ?>> _relatedModels = new ArrayList<>();
	private Map<String, Function<T, String>> _stringFunctions = new HashMap<>();
	private List<String> _types = new ArrayList<>();

}