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

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.alias.BinaryFunction;
import com.liferay.apio.architect.alias.representor.FieldFunction;
import com.liferay.apio.architect.alias.representor.NestedFieldFunction;
import com.liferay.apio.architect.alias.representor.NestedListFieldFunction;
import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.related.RelatedModel;

import java.util.Date;
import java.util.List;
import java.util.Locale;
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
@ProviderType
public interface BaseRepresentor<T> {

	/**
	 * Returns the list containing the application relative URL field names and
	 * the functions to get those fields.
	 *
	 * @return the list containing the application relative URL field names and
	 *         functions
	 * @review
	 */
	public List<FieldFunction<T, String>> getApplicationRelativeURLFunctions();

	/**
	 * Returns a binary resource linked to a model, if present. Returns {@code
	 * Optional#empty} otherwise.
	 *
	 * @param  binaryId the ID of the binary resource
	 * @return a binary resource linked to a model if present; {@code
	 *         Optional#empty()} otherwise
	 * @review
	 */
	public Optional<BinaryFunction<T>> getBinaryFunction(String binaryId);

	/**
	 * Returns the binary functions linked to a model.
	 *
	 * @return the binary functions linked to a model
	 */
	public List<FieldFunction<T, BinaryFile>> getBinaryFunctions();

	/**
	 * Returns the list containing the boolean field names and the functions to
	 * get those fields.
	 *
	 * @return the list containing the boolean field names and functions
	 */
	public List<FieldFunction<T, Boolean>> getBooleanFunctions();

	/**
	 * Returns the list containing the boolean list field names and the
	 * functions to get those fields.
	 *
	 * @return the list containing the boolean list field names and functions
	 */
	public List<FieldFunction<T, List<Boolean>>> getBooleanListFunctions();

	/**
	 * Returns the list containing the links field names and the functions to
	 * get those links.
	 *
	 * @return the list containing the links field names and functions
	 */
	public List<FieldFunction<T, String>> getLinkFunctions();

	/**
	 * Returns a map containing the localized string field names and the
	 * functions to get those fields.
	 *
	 * @return the list containing the localized string field names and
	 *         functions
	 */
	public List<FieldFunction<T, Function<AcceptLanguage, String>>>
		getLocalizedStringFunctions();

	/**
	 * Returns the list of nested field functions.
	 *
	 * @return the list of nested field functions.
	 * @review
	 */
	public List<NestedFieldFunction<T, ?>> getNestedFieldFunctions();

	/**
	 * Returns the list of nested list field functions.
	 *
	 * @return the list of nested list field functions.
	 * @review
	 */
	public List<NestedListFieldFunction<T, ?>> getNestedListFieldFunctions();

	/**
	 * Returns the list containing the number field names and the functions to
	 * get those fields.
	 *
	 * @return the list containing the number field names and functions
	 */
	public List<FieldFunction<T, Number>> getNumberFunctions();

	/**
	 * Returns the list containing the number list field names and the functions
	 * to get those fields.
	 *
	 * @return the list containing the number list field names and functions
	 */
	public List<FieldFunction<T, List<Number>>> getNumberListFunctions();

	/**
	 * Returns the primary type.
	 *
	 * @return the primary type
	 */
	public String getPrimaryType();

	/**
	 * Returns the related models.
	 *
	 * @return the related models
	 */
	public List<RelatedModel<T, ?>> getRelatedModels();

	/**
	 * Returns the list containing the relative URL field names and the
	 * functions to get those fields.
	 *
	 * @return the list containing the relative URL field names and functions
	 */
	public List<FieldFunction<T, String>> getRelativeURLFunctions();

	/**
	 * Returns the list containing the string field names and the functions to
	 * get those fields.
	 *
	 * @return the list containing the string field names and functions
	 */
	public List<FieldFunction<T, String>> getStringFunctions();

	/**
	 * Returns the list containing the string list field names and the functions
	 * to get those fields.
	 *
	 * @return the list containing the string list field names and functions
	 */
	public List<FieldFunction<T, List<String>>> getStringListFunctions();

	/**
	 * Returns the types.
	 *
	 * @return the types
	 */
	public List<String> getTypes();

	/**
	 * Returns {@code true} if this {@code Representor} is a {@link
	 * NestedRepresentor}. Returns {@code false} otherwise.
	 *
	 * @return {@code true} if this {@code Representor} is a {@link
	 *         NestedRepresentor}; {@code false} otherwise
	 * @review
	 */
	public boolean isNested();

	@ProviderType
	public interface BaseFirstStep
		<T, S extends BaseRepresentor<T>, U extends BaseFirstStep<T, S, U>> {

		/**
		 * Adds information about a resource's application relative URL field.
		 * This field's value will be represented as an absolute URI, by
		 * prefixing it with the application URL.
		 *
		 * <p>
		 * URLs returned by this function should be already encoded (to check
		 * for potential security holes).
		 * </p>
		 *
		 * @param  key the field's name
		 * @param  function the function used to get the relative url
		 * @return the builder's step
		 * @review
		 */
		public U addApplicationRelativeURL(
			String key, Function<T, String> function);

		/**
		 * Adds binary files to a resource.
		 *
		 * @param  key the binary resource's name
		 * @param  binaryFunction the function used to get the binaries
		 * @return the builder's step
		 */
		public U addBinary(String key, BinaryFunction<T> binaryFunction);

		/**
		 * Adds information about a resource's boolean field.
		 *
		 * @param  key the field's name
		 * @param  function the function used to get the boolean value
		 * @return the builder's step
		 */
		public U addBoolean(String key, Function<T, Boolean> function);

		/**
		 * Adds information about a resource's boolean list field.
		 *
		 * @param  key the field's name
		 * @param  function the function used to get the boolean list
		 * @return the builder's step
		 */
		public U addBooleanList(
			String key, Function<T, List<Boolean>> function);

		/**
		 * Adds information about a resource's date field.
		 *
		 * @param  key the field's name
		 * @param  function the function used to get the date value
		 * @return the builder's step
		 */
		public U addDate(String key, Function<T, Date> function);

		/**
		 * Adds information about a resource link.
		 *
		 * @param  key the field's name
		 * @param  url the link's URL
		 * @return the builder's step
		 */
		public U addLink(String key, String url);

		/**
		 * Adds information about an embeddable related resource.
		 *
		 * @param  key the relation's name
		 * @param  identifierClass the related resource identifier's class
		 * @param  identifierFunction the function used to get the related
		 *         resource's identifier
		 * @return the builder's step
		 */
		public <V> U addLinkedModel(
			String key, Class<? extends Identifier<V>> identifierClass,
			Function<T, V> identifierFunction);

		/**
		 * Provides information about a resource localized string field.
		 *
		 * @param  key the field's name
		 * @param  stringFunction the function used to get the string value
		 * @return builder's step
		 */
		public U addLocalizedStringByLanguage(
			String key, BiFunction<T, AcceptLanguage, String> stringFunction);

		/**
		 * Provides information about a resource localized string field.
		 *
		 * @param  key the field's name
		 * @param  stringFunction the function used to get the string value
		 * @return builder's step
		 */
		public U addLocalizedStringByLocale(
			String key, BiFunction<T, Locale, String> stringFunction);

		/**
		 * Provides information about a nested field.
		 *
		 * @param  key the field's name
		 * @param  transformFunction the function that transforms the model into
		 *         the model used inside the nested representor
		 * @param  function the function that creates the nested representor
		 * @return the builder's step
		 */
		public <V> U addNested(
			String key, Function<T, V> transformFunction,
			Function<NestedRepresentor.Builder<V>, NestedRepresentor<V>>
				function);

		/**
		 * Adds a nested list field to the {@code Representor}.
		 *
		 * @param  key the field's name
		 * @param  transformFunction the function that transforms the model into
		 *         the list whose models are used inside the nested representor
		 * @param  function the function that creates the nested representor for
		 *         each model
		 * @review
		 */
		public <V> U addNestedList(
			String key, Function<T, List<V>> transformFunction,
			Function<NestedRepresentor.Builder<V>,
				NestedRepresentor<V>> function);

		/**
		 * Adds information about a resource's number field.
		 *
		 * @param  key the field's name
		 * @param  function the function used to get the number's value
		 * @return the builder's step
		 */
		public U addNumber(String key, Function<T, Number> function);

		/**
		 * Adds information about a resource's number list field.
		 *
		 * @param  key the field's name
		 * @param  function the function used to get the number list
		 * @return the builder's step
		 */
		public U addNumberList(String key, Function<T, List<Number>> function);

		/**
		 * Adds information about a resource's relative URL field. This field's
		 * value will be represented as an absolute URI, by prefixing it with
		 * the server URL.
		 *
		 * <p>
		 * URLs returned by this function should be already encoded (to check
		 * for potential security holes).
		 * </p>
		 *
		 * @param  key the field's name
		 * @param  function the function used to get the relative url
		 * @return the builder's step
		 * @review
		 */
		public U addRelativeURL(String key, Function<T, String> function);

		/**
		 * Adds information about a resource's string field.
		 *
		 * @param  key the field's name
		 * @param  function the function used to get the string's value
		 * @return the builder's step
		 */
		public U addString(String key, Function<T, String> function);

		/**
		 * Adds information about a resource's string list field.
		 *
		 * @param  key the field's name
		 * @param  function the function used to get the string list
		 * @return the builder's step
		 */
		public U addStringList(String key, Function<T, List<String>> function);

		/**
		 * Constructs and returns a {@link NestedRepresentor} instance with the
		 * information provided to the builder.
		 *
		 * @return the {@code Representor} instance
		 */
		public S build();

	}

}