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
import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.language.Language;
import com.liferay.apio.architect.related.RelatedModel;
import com.liferay.apio.architect.representor.function.FieldFunction;
import com.liferay.apio.architect.representor.function.NestedFieldFunction;
import com.liferay.apio.architect.unsafe.Unsafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Base class for {@code Representors}.
 *
 * <p>
 * Descendants of this class holds information about the metadata supported for
 * a resource.
 * </p>
 *
 * <p>
 * Only two descendants are allowed: {@link Representor} and {@link
 * NestedRepresentor}.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 */
public abstract class BaseRepresentor<T> {

	/**
	 * Returns a binary resource linked to a model, if present. Returns {@code
	 * Optional#empty} otherwise.
	 *
	 * @param  binaryId the ID of the binary resource
	 * @return a binary resource linked to a model if present; {@code
	 *         Optional#empty()} otherwise
	 * @review
	 */
	public Optional<BinaryFunction<T>> getBinaryFunction(String binaryId) {
		return Optional.ofNullable(binaryFunctions.get(binaryId));
	}

	/**
	 * Returns the binary functions linked to a model.
	 *
	 * @return the binary functions linked to a model
	 */
	public List<FieldFunction<T, BinaryFile>> getBinaryFunctions() {
		return Optional.ofNullable(
			fieldFunctions.get("BINARY")
		).<List<FieldFunction<T, BinaryFile>>>map(
			Unsafe::unsafeCast
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns the list containing the boolean field names and the functions to
	 * get those fields.
	 *
	 * @return the list containing the boolean field names and functions
	 */
	public List<FieldFunction<T, Boolean>> getBooleanFunctions() {
		return Optional.ofNullable(
			fieldFunctions.get("BOOLEAN")
		).<List<FieldFunction<T, Boolean>>>map(
			Unsafe::unsafeCast
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns the list containing the boolean list field names and the
	 * functions to get those fields.
	 *
	 * @return the list containing the boolean list field names and functions
	 */
	public List<FieldFunction<T, List<Boolean>>> getBooleanListFunctions() {
		return Optional.ofNullable(
			fieldFunctions.get("BOOLEAN_LIST")
		).<List<FieldFunction<T, List<Boolean>>>>map(
			Unsafe::unsafeCast
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns the list containing the links field names and the functions to
	 * get those links.
	 *
	 * @return the list containing the links field names and functions
	 */
	public List<FieldFunction<T, String>> getLinkFunctions() {
		return Optional.ofNullable(
			fieldFunctions.get("LINK")
		).<List<FieldFunction<T, String>>>map(
			Unsafe::unsafeCast
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns a map containing the localized string field names and the
	 * functions to get those fields.
	 *
	 * @return the list containing the localized string field names and
	 *         functions
	 */
	public List<FieldFunction<T, Function<Language, String>>>
		getLocalizedStringFunctions() {

		return Optional.ofNullable(
			fieldFunctions.get("LOCALIZED")
		).<List<FieldFunction<T, Function<Language, String>>>>map(
			Unsafe::unsafeCast
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns the list of nested field functions.
	 *
	 * @return the list of nested field functions.
	 */
	public List<NestedFieldFunction<T, ?>> getNestedFieldFunctions() {
		return nestedFieldFunctions;
	}

	/**
	 * Returns the list containing the number field names and the functions to
	 * get those fields.
	 *
	 * @return the list containing the number field names and functions
	 */
	public List<FieldFunction<T, Number>> getNumberFunctions() {
		return Optional.ofNullable(
			fieldFunctions.get("NUMBER")
		).<List<FieldFunction<T, Number>>>map(
			Unsafe::unsafeCast
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns the list containing the number list field names and the functions
	 * to get those fields.
	 *
	 * @return the list containing the number list field names and functions
	 */
	public List<FieldFunction<T, List<Number>>> getNumberListFunctions() {
		return Optional.ofNullable(
			fieldFunctions.get("NUMBER_LIST")
		).<List<FieldFunction<T, List<Number>>>>map(
			Unsafe::unsafeCast
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns the related models.
	 *
	 * @return the related models
	 */
	public List<RelatedModel<T, ?>> getRelatedModels() {
		return relatedModels;
	}

	/**
	 * Returns the list containing the string field names and the functions to
	 * get those fields.
	 *
	 * @return the list containing the string field names and functions
	 */
	public List<FieldFunction<T, String>> getStringFunctions() {
		return Optional.ofNullable(
			fieldFunctions.get("STRING")
		).<List<FieldFunction<T, String>>>map(
			Unsafe::unsafeCast
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns the list containing the string list field names and the functions
	 * to get those fields.
	 *
	 * @return the list containing the string list field names and functions
	 */
	public List<FieldFunction<T, List<String>>> getStringListFunctions() {
		return Optional.ofNullable(
			fieldFunctions.get("STRING_LIST")
		).<List<FieldFunction<T, List<String>>>>map(
			Unsafe::unsafeCast
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns the types.
	 *
	 * @return the types
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * Returns {@code true} if this {@code Representor} is a {@link
	 * NestedRepresentor}. Returns {@code false} otherwise.
	 *
	 * @return {@code true} if this {@code Representor} is a {@link
	 *         NestedRepresentor}; {@code false} otherwise
	 * @review
	 */
	public abstract boolean isNested();

	protected BaseRepresentor() {
		binaryFunctions = new LinkedHashMap<>();
		fieldFunctions = new LinkedHashMap<>();
		nestedFieldFunctions = new ArrayList<>();
		relatedModels = new ArrayList<>();
		types = new ArrayList<>();
	}

	/**
	 * Adds a binary function to the {@code Representor}.
	 *
	 * @param  key the binary resource's name
	 * @param  binaryFunction the function used to get the binaries
	 * @review
	 */
	protected void addBinaryFunction(
		String key, BinaryFunction<T> binaryFunction) {

		binaryFunctions.put(key, binaryFunction);

		_addFieldFunction(key, binaryFunction, "BINARY");
	}

	/**
	 * Adds a boolean function to the {@code Representor}.
	 *
	 * @param  key the field's name
	 * @param  function the function used to get the boolean
	 * @review
	 */
	protected void addBooleanFunction(
		String key, Function<T, Boolean> function) {

		_addFieldFunction(key, function, "BOOLEAN");
	}

	/**
	 * Adds a boolean list function to the {@code Representor}.
	 *
	 * @param  key the field's name
	 * @param  function the function used to get the boolean list
	 * @review
	 */
	protected void addBooleanListFunction(
		String key, Function<T, List<Boolean>> function) {

		_addFieldFunction(key, function, "BOOLEAN_LIST");
	}

	/**
	 * Adds a language function to the {@code Representor}.
	 *
	 * @param  key the field's name
	 * @param  function the function used to get the language function
	 * @review
	 */
	protected void addLanguageFunction(
		String key, Function<T, Function<Language, String>> function) {

		_addFieldFunction(key, function, "LOCALIZED");
	}

	/**
	 * Adds a link function to the {@code Representor}.
	 *
	 * @param  key the field's name
	 * @param  function the function used to get the link
	 * @review
	 */
	protected void addLinkFunction(String key, Function<T, String> function) {
		_addFieldFunction(key, function, "LINK");
	}

	/**
	 * Adds a nested field to the {@code Representor}.
	 *
	 * @param  key the field's name
	 * @param  transformFunction the function that transforms the model into the
	 *         model used inside the nested representor
	 * @param  function the function used to get the {@link NestedRepresentor}
	 * @review
	 */
	protected <S> void addNestedField(
		String key, Function<T, S> transformFunction,
		Function<NestedRepresentor.Builder<S>, NestedRepresentor<S>> function) {

		NestedFieldFunction<T, S> nestedFieldFunction = function.andThen(
			nestedRepresentor -> new NestedFieldFunction<>(
				key, transformFunction, nestedRepresentor)
		).apply(
			new NestedRepresentor.Builder<>()
		);

		nestedFieldFunctions.add(nestedFieldFunction);
	}

	/**
	 * Adds a number function to the {@code Representor}.
	 *
	 * @param  key the field's name
	 * @param  function the function used to get the number
	 * @review
	 */
	protected void addNumberFunction(String key, Function<T, Number> function) {
		_addFieldFunction(key, function, "NUMBER");
	}

	/**
	 * Adds a number list function to the {@code Representor}.
	 *
	 * @param  key the field's name
	 * @param  function the function used to get the number list
	 * @review
	 */
	protected void addNumberListFunction(
		String key, Function<T, List<Number>> function) {

		_addFieldFunction(key, function, "NUMBER_LIST");
	}

	/**
	 * Adds information about a related model.
	 *
	 * @param  key the relation's name
	 * @param  identifierClass the related model identifier's class
	 * @param  identifierFunction the function used to get the related model's
	 *         identifier
	 * @review
	 */
	protected <S> void addRelatedModel(
		String key, Class<? extends Identifier<S>> identifierClass,
		Function<T, S> identifierFunction) {

		RelatedModel<T, S> relatedModel = new RelatedModel<>(
			key, identifierClass, identifierFunction);

		relatedModels.add(relatedModel);
	}

	/**
	 * Adds a string function to the {@code Representor}.
	 *
	 * @param  key the field's name
	 * @param  function the function used to get the string
	 * @review
	 */
	protected void addStringFunction(String key, Function<T, String> function) {
		_addFieldFunction(key, function, "STRING");
	}

	/**
	 * Adds a string list function to the {@code Representor}.
	 *
	 * @param  key the field's name
	 * @param  function the function used to get the string list
	 * @review
	 */
	protected void addStringListFunction(
		String key, Function<T, List<String>> function) {

		_addFieldFunction(key, function, "STRING_LIST");
	}

	/**
	 * Adds the types to the {@code Representor}.
	 *
	 * @param  type the first type
	 * @param  types the rest of the types
	 * @review
	 */
	protected void addTypes(String type, String... types) {
		this.types.add(type);
		Collections.addAll(this.types, types);
	}

	protected final Map<String, BinaryFunction<T>> binaryFunctions;
	protected final Map<String, List<FieldFunction<T, ?>>> fieldFunctions;
	protected final List<NestedFieldFunction<T, ?>> nestedFieldFunctions;
	protected final List<RelatedModel<T, ?>> relatedModels;
	protected final List<String> types;

	/**
	 * Base class for {@code Representor} builders.
	 *
	 * <p>
	 * Descendants of this class creates generic representations of your domain
	 * models that Apio hypermedia writers can understand.
	 * </p>
	 *
	 * <p>
	 * Only two descendants are allowed: {@link Representor.Builder} and {@link
	 * NestedRepresentor.Builder}.
	 * </p>
	 *
	 * @param  <T> the model's type
	 * @param  <S> the {@code Representor}'s type
	 * @review
	 */
	protected abstract static class BaseBuilder
		<T, S extends BaseRepresentor<T>> {

		public abstract class BaseFirstStep<U extends BaseFirstStep<U>> {

			/**
			 * Adds binary files to a resource.
			 *
			 * @param  key the binary resource's name
			 * @param  binaryFunction the function used to get the binaries
			 * @return the builder's step
			 */
			public U addBinary(String key, BinaryFunction<T> binaryFunction) {
				baseRepresentor.addBinaryFunction(key, binaryFunction);

				return _this;
			}

			/**
			 * Adds information about a resource's boolean field.
			 *
			 * @param  key the field's name
			 * @param  function the function used to get the boolean value
			 * @return the builder's step
			 */
			public U addBoolean(String key, Function<T, Boolean> function) {
				baseRepresentor.addBooleanFunction(key, function);

				return _this;
			}

			/**
			 * Adds information about a resource's boolean list field.
			 *
			 * @param  key the field's name
			 * @param  function the function used to get the boolean list
			 * @return the builder's step
			 */
			public U addBooleanList(
				String key, Function<T, List<Boolean>> function) {

				baseRepresentor.addBooleanListFunction(key, function);

				return _this;
			}

			/**
			 * Adds information about a resource's date field.
			 *
			 * @param  key the field's name
			 * @param  function the function used to get the date value
			 * @return the builder's step
			 */
			public U addDate(String key, Function<T, Date> function) {
				Function<Date, String> formatFunction = date -> {
					if (date == null) {
						return null;
					}

					return asString(date);
				};

				baseRepresentor.addStringFunction(
					key, function.andThen(formatFunction));

				return _this;
			}

			/**
			 * Adds information about a resource link.
			 *
			 * @param  key the field's name
			 * @param  url the link's URL
			 * @return the builder's step
			 */
			public U addLink(String key, String url) {
				baseRepresentor.addLinkFunction(key, __ -> url);

				return _this;
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
			public <W> U addLinkedModel(
				String key, Class<? extends Identifier<W>> identifierClass,
				Function<T, W> identifierFunction) {

				baseRepresentor.addRelatedModel(
					key, identifierClass, identifierFunction);

				return _this;
			}

			/**
			 * Provides information about a resource localized string field.
			 *
			 * @param  key the field's name
			 * @param  stringFunction the function used to get the string value
			 * @return builder's step
			 */
			public U addLocalizedStringByLanguage(
				String key, BiFunction<T, Language, String> stringFunction) {

				baseRepresentor.addLanguageFunction(
					key, t -> language -> stringFunction.apply(t, language));

				return _this;
			}

			/**
			 * Provides information about a resource localized string field.
			 *
			 * @param  key the field's name
			 * @param  stringFunction the function used to get the string value
			 * @return builder's step
			 */
			public U addLocalizedStringByLocale(
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
			 * @param  transformFunction the function that transforms the model
			 *         into the model used inside the nested representor
			 * @param  function the function that creates the nested representor
			 * @return the builder's step
			 */
			public <W> U addNested(
				String key, Function<T, W> transformFunction,
				Function<NestedRepresentor.Builder<W>, NestedRepresentor<W>>
					function) {

				baseRepresentor.addNestedField(
					key, transformFunction, function);

				return _this;
			}

			/**
			 * Adds information about a resource's number field.
			 *
			 * @param  key the field's name
			 * @param  function the function used to get the number's value
			 * @return the builder's step
			 */
			public U addNumber(String key, Function<T, Number> function) {
				baseRepresentor.addNumberFunction(key, function);

				return _this;
			}

			/**
			 * Adds information about a resource's number list field.
			 *
			 * @param  key the field's name
			 * @param  function the function used to get the number list
			 * @return the builder's step
			 */
			public U addNumberList(
				String key, Function<T, List<Number>> function) {

				baseRepresentor.addNumberListFunction(key, function);

				return _this;
			}

			/**
			 * Adds information about a resource's string field.
			 *
			 * @param  key the field's name
			 * @param  function the function used to get the string's value
			 * @return the builder's step
			 */
			public U addString(String key, Function<T, String> function) {
				baseRepresentor.addStringFunction(key, function);

				return _this;
			}

			/**
			 * Adds information about a resource's string list field.
			 *
			 * @param  key the field's name
			 * @param  function the function used to get the string list
			 * @return the builder's step
			 */
			public U addStringList(
				String key, Function<T, List<String>> function) {

				baseRepresentor.addStringListFunction(key, function);

				return _this;
			}

			/**
			 * Constructs and returns a {@link NestedRepresentor} instance _with
			 * the information provided to the builder.
			 *
			 * @return the {@code Representor} instance
			 */
			public S build() {
				return baseRepresentor;
			}

			/**
			 * Returns the generic instance of this builder's step
			 *
			 * <p>
			 * All descendants should just return {@code this}
			 * </p>
			 *
			 * @return the generic instance of this builder's step
			 * @review
			 */
			public abstract U getThis();

			protected BaseFirstStep() {
				_this = getThis();
			}

			private final U _this;

		}

		protected BaseBuilder(S baseRepresentor) {
			this.baseRepresentor = baseRepresentor;
		}

		protected final S baseRepresentor;

	}

	private <S> void _addFieldFunction(
		String key, Function<T, S> function, String mapKey) {

		List<FieldFunction<T, ?>> list = fieldFunctions.computeIfAbsent(
			mapKey, __ -> new ArrayList<>());

		FieldFunction<T, S> fieldFunction = new FieldFunction<>(key, function);

		list.add(fieldFunction);
	}

}