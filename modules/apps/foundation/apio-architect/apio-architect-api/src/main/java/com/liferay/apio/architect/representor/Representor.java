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
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.language.Language;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.related.RelatedModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
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
 * @param  <S> the model identifier's type (e.g., {@code Long}, {@code String},
 *         etc.)
 * @see    Representor.Builder
 */
public class Representor<T, S> {

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
	 * Returns the map containing the boolean list field names and the functions
	 * to get those fields.
	 *
	 * @return the map containing the boolean list field names and functions
	 */
	public Map<String, Function<T, List<Boolean>>> getBooleanListFunctions() {
		return _booleanListFunctions;
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
	 * Returns the function that extracts the identifier from the model.
	 *
	 * @return the function
	 */
	public Function<T, S> getIdentifierFunction() {
		return _identifierFunction;
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
	 * Returns the representors used to render the subresources.
	 *
	 * @return the representors
	 */
	public Map<String, Representor<?, ?>> getNested() {
		return _nested;
	}

	/**
	 * Returns the mappers for the resource and the subresource.
	 *
	 * @return the mappers
	 */
	public Map<String, Function<T, ?>> getNestedFunctions() {
		return _nestedFunctions;
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
	 * Returns the map containing the number list field names and the functions
	 * to get those fields.
	 *
	 * @return the map containing the number list field names and functions
	 */
	public Map<String, Function<T, List<Number>>> getNumberListFunctions() {
		return _numberListFunctions;
	}

	/**
	 * Returns the related collections.
	 *
	 * @return the related collections
	 */
	public Stream<RelatedCollection<? extends Identifier>>
		getRelatedCollections() {

		Stream<List<RelatedCollection<? extends Identifier>>> stream =
			Stream.of(_relatedCollections, _relatedCollectionsSupplier.get());

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
	 * Returns the map containing the string list field names and the functions
	 * to get those fields.
	 *
	 * @return the map containing the string list field names and functions
	 */
	public Map<String, Function<T, List<String>>> getStringListFunctions() {
		return _stringListFunctions;
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

		public Builder() {
			this(null);
		}

		public Builder(Class<? extends Identifier<U>> identifierClass) {
			Supplier<List<RelatedCollection<?>>> listSupplier =
				Collections::emptyList;

			_relatedCollectionBiConsumer = (clazz, relatedCollection) -> {
			};
			_representor = new Representor<>(identifierClass, listSupplier);
		}

		public Builder(
			Class<? extends Identifier<U>> identifierClass,
			BiConsumer<Class<?>, RelatedCollection<?>>
				relatedCollectionBiConsumer,
			Supplier<List<RelatedCollection<?>>> relatedCollectionsSupplier) {

			_relatedCollectionBiConsumer = relatedCollectionBiConsumer;

			_representor = new Representor<>(
				identifierClass, relatedCollectionsSupplier);
		}

		/**
		 * Adds a type for a nested resource, skipping the identifier function
		 * because nested resources don't have an ID.
		 *
		 * @param  type the type name
		 * @return the builder's step
		 */
		public FirstStep nestedTypes(String type, String... types) {
			_representor._types.add(type);

			Collections.addAll(_representor._types, types);

			return new FirstStep();
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

				_representor._relatedModels.add(
					new RelatedModel<>(
						key, identifierClass, identifierFunction));

				RelatedCollection<?> relatedCollection =
					new RelatedCollection<>(
						relatedKey, _representor._identifierClass);

				_relatedCollectionBiConsumer.accept(
					identifierClass, relatedCollection);

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
			 * Adds information about a resource's boolean list field.
			 *
			 * @param  key the field's name
			 * @param  booleanListFunction the function used to get the boolean
			 *         list
			 * @return the builder's step
			 */
			public FirstStep addBooleanList(
				String key, Function<T, List<Boolean>> booleanListFunction) {

				_representor._booleanListFunctions.put(
					key, booleanListFunction);

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
			 * Adds information about an embeddable related resource.
			 *
			 * @param  key the relation's name
			 * @param  identifierClass the related resource identifier's class
			 * @param  identifierFunction the function used to get the related
			 *         resource's identifier
			 * @return the builder's step
			 */
			public <S> FirstStep addLinkedModel(
				String key, Class<? extends Identifier<S>> identifierClass,
				Function<T, S> identifierFunction) {

				_representor._relatedModels.add(
					new RelatedModel<>(
						key, identifierClass, identifierFunction));

				return this;
			}

			/**
			 * Provides information about a resource localized string field.
			 *
			 * @param  key the field's name
			 * @param  stringFunction the function used to get the string value
			 * @return builder's step
			 */
			public FirstStep addLocalizedStringByLanguage(
				String key, BiFunction<T, Language, String> stringFunction) {

				_representor._localizedStringFunctions.put(key, stringFunction);

				return this;
			}

			/**
			 * Provides information about a resource localized string field.
			 *
			 * @param  key the field's name
			 * @param  stringFunction the function used to get the string value
			 * @return builder's step
			 */
			public FirstStep addLocalizedStringByLocale(
				String key, BiFunction<T, Locale, String> stringFunction) {

				return addLocalizedStringByLanguage(
					key,
					(t, language) -> stringFunction.apply(
						t, language.getPreferredLocale()));
			}

			/**
			 * Provides information about a nested field.
			 *
			 * @param  key the field's name
			 * @param  representorFunction the function that creates the nested
			 *         representor
			 * @return the builder's step
			 */
			public <W> FirstStep addNested(
				String key,
				Function<Builder<T, ?>, Representor<T, ?>>
					representorFunction) {

				_representor._nested.put(
					key, representorFunction.apply(new Builder()));

				_representor._nestedFunctions.put(key, Function.identity());

				return this;
			}

			/**
			 * Provides information about a nested field.
			 *
			 * @param  key the field's name
			 * @param  transformFunction the function that transforms the model
			 *         into the model used inside the nested representor
			 * @param  representorFunction the function that creates the nested
			 *         representor
			 * @return the builder's step
			 */
			public <W> FirstStep addNested(
				String key, Function<T, W> transformFunction,
				Function<Builder<W, ?>, Representor<W, ?>>
					representorFunction) {

				_representor._nested.put(
					key, representorFunction.apply(new Builder()));

				_representor._nestedFunctions.put(key, transformFunction);

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
			 * Adds information about a resource's number list field.
			 *
			 * @param  key the field's name
			 * @param  numberListFunction the function used to get the number
			 *         list
			 * @return the builder's step
			 */
			public FirstStep addNumberList(
				String key, Function<T, List<Number>> numberListFunction) {

				_representor._numberListFunctions.put(key, numberListFunction);

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

				RelatedCollection<S> relatedCollection =
					new RelatedCollection<>(key, itemIdentifierClass);

				_representor._relatedCollections.add(relatedCollection);

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
			 * Adds information about a resource's string list field.
			 *
			 * @param  key the field's name
			 * @param  stringListFunction the function used to get the string
			 *         list
			 * @return the builder's step
			 */
			public FirstStep addStringList(
				String key, Function<T, List<String>> stringListFunction) {

				_representor._stringListFunctions.put(key, stringListFunction);

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

		private final BiConsumer<Class<?>, RelatedCollection<?>>
			_relatedCollectionBiConsumer;
		private final Representor<T, U> _representor;

	}

	private Representor(
		Class<? extends Identifier<S>> identifierClass,
		Supplier<List<RelatedCollection<? extends Identifier>>>
			relatedCollectionsSupplier) {

		_identifierClass = identifierClass;
		_relatedCollectionsSupplier = relatedCollectionsSupplier;
	}

	private final Map<String, BinaryFunction<T>> _binaryFunctions =
		new LinkedHashMap<>();
	private final Map<String, Function<T, Boolean>> _booleanFunctions =
		new LinkedHashMap<>();
	private final Map<String, Function<T, List<Boolean>>>
		_booleanListFunctions = new LinkedHashMap<>();
	private final Class<? extends Identifier<S>> _identifierClass;
	private Function<T, S> _identifierFunction;
	private final Map<String, String> _links = new LinkedHashMap<>();
	private final Map<String, BiFunction<T, Language, String>>
		_localizedStringFunctions = new LinkedHashMap<>();
	private final Map<String, Representor<?, ?>> _nested = new HashMap<>();
	private final Map<String, Function<T, ?>> _nestedFunctions =
		new HashMap<>();
	private final Map<String, Function<T, Number>> _numberFunctions =
		new LinkedHashMap<>();
	private final Map<String, Function<T, List<Number>>> _numberListFunctions =
		new LinkedHashMap<>();
	private final List<RelatedCollection<? extends Identifier>>
		_relatedCollections = new ArrayList<>();
	private final Supplier<List<RelatedCollection<?>>>
		_relatedCollectionsSupplier;
	private final List<RelatedModel<T, ?>> _relatedModels = new ArrayList<>();
	private final Map<String, Function<T, String>> _stringFunctions =
		new LinkedHashMap<>();
	private final Map<String, Function<T, List<String>>> _stringListFunctions =
		new LinkedHashMap<>();
	private final List<String> _types = new ArrayList<>();

}