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

package com.liferay.vulcan.message.json;

import aQute.bnd.annotation.ProviderType;

import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Use instances of this builder to create JSON objects.
 *
 * This builder should be used to write a complete path in each call.
 * For example, this code:
 *
 * <p>
 * {@code jsonObjectBuilder.nestedField("object", "inner", "other").value(42);}
 * </p>
 *
 * <p>
 * Results in the following JSON object:
 * </p>
 *
 * <p>
 * {@code { "object": { "inner": { "other": 42 } } } }
 * </p>
 *
 * This builder is incremental, so, once you have made the first call, you can
 * continue with the next one, and the following paths will be added to the
 * previous one, respecting the previous state.
 *
 * For example, making this call to the previous builder:
 *
 * <p>
 * {@code jsonObjectBuilder.nestedField("object", "inner",
 * "another").value("Hello World!"); }
 * </p>
 *
 * <p>
 * Results in the following JSON object:
 * </p>
 *
 * <p>
 * {@code { "object": { "inner": { "another": "Hello World!", "other": 42 } } }
 * }
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @review
 */
@ProviderType
@SuppressWarnings("unused")
public interface JSONObjectBuilder {

	/**
	 * Returns the {@link JsonObject} constructed by this builder.
	 *
	 * @return the constructed JSON object.
	 * @review
	 */
	public JsonObject build();

	/**
	 * Start the creation of a field inside the JSON object.
	 *
	 * @param  name the name of the field
	 * @return the next step of the builder.
	 * @review
	 */
	public FieldStep field(String name);

	/**
	 * Starts the creation of a field inside the JSON object conditionally. If
	 * the handed condition is met, the {@link FieldStep} created by {@code
	 * ifFunction} is returned, otherwise, the {@link FieldStep} created by
	 * {@code elseFunction} is returned.
	 *
	 * @param  condition the condition to check.
	 * @param  ifFunction the function to be used to create the next step if the
	 *         condition is {@code true}.
	 * @param  elseFunction the function to be used to create the next step if
	 *         the condition is {@code false}.
	 * @return the next step of the builder.
	 * @review
	 */
	public FieldStep ifElseCondition(
		boolean condition, Function<JSONObjectBuilder, FieldStep> ifFunction,
		Function<JSONObjectBuilder, FieldStep> elseFunction);

	/**
	 * Starts the creation of a nested field inside the JSON object.
	 *
	 * @param  parentName the name of the first field.
	 * @param  nestedNames the list of names of the subsequent field.
	 * @return the next step of the builder.
	 * @review
	 */
	public FieldStep nestedField(String parentName, String... nestedNames);

	/**
	 * Starts the creation of a nested field inside the JSON object. This method
	 * behaves like {@link #nestedField(String, String...)} except it adds a
	 * {@code prefix} before every level. For example:
	 *
	 * <p>
	 * The following code:
	 * </p>
	 *
	 * <p>
	 * {@code jsonObjectBuilder.nestedPrefixedField("prefix", "first",
	 * "second").value(42);}
	 * </p>
	 *
	 * <p>
	 * Results in the following JSON object:
	 * </p>
	 *
	 * <p>
	 * {@code { "prefix": { "first": { "prefix": { "second": 42 } } } }
	 * </p>
	 *
	 * @param  prefix the prefix field to use before every field.
	 * @param  parentName the name of the first field.
	 * @param  nestedNames the list of names of the subsequent field.
	 * @return the next step of the builder.
	 * @review
	 */
	public FieldStep nestedPrefixedField(
		String prefix, String parentName, String... nestedNames);

	/**
	 * Starts the creation of a nested field inside the JSON object. This method
	 * behaves like {@link #nestedField(String, String...)} except it adds a
	 * {@code suffix} after every level. For example:
	 *
	 * <p>
	 * The following code:
	 * </p>
	 *
	 * <p>
	 * {@code jsonObjectBuilder.nestedSuffixedField("suffix", "first",
	 * "second").value(42);}
	 * </p>
	 *
	 * <p>
	 * Results in the following JSON object:
	 * </p>
	 *
	 * <p>
	 * {@code { "first": { "suffix": { "second": { "suffix": 42 } } } }
	 * </p>
	 *
	 * @param  suffix the suffix field to use after every field.
	 * @param  parentName the name of the first field.
	 * @param  nestedNames the list of names of the subsequent field.
	 * @return the next step of the builder.
	 * @review
	 */
	public FieldStep nestedSuffixedField(
		String suffix, String parentName, String... nestedNames);

	/**
	 * Step to add the value of a field as a JSON array.
	 *
	 * @review
	 */
	public interface ArrayValueStep {

		/**
		 * Adds a new jsonObject to the JSON array, created by the provided
		 * consumer.
		 *
		 * @param  consumer consumer used to create the new JSON object.
		 * @review
		 */
		public void add(Consumer<JSONObjectBuilder> consumer);

		/**
		 * Adds the jsonObject created by the provided {@link
		 * JSONObjectBuilder}.
		 *
		 * @param  jsonObjectBuilder {@code JSONObjectBuilder} whose JSON object
		 *         is going to be added
		 * @review
		 */
		public void add(JSONObjectBuilder jsonObjectBuilder);

		/**
		 * Adds all elements in a boolean collection as elements of this JSON
		 * array.
		 *
		 * @param  collection the collection to be added.
		 * @review
		 */
		public void addAllBooleans(Collection<Boolean> collection);

		/**
		 * Adds all elements in a JSON object collection as elements of this
		 * JSON array.
		 *
		 * @param  collection the collection to be added.
		 * @review
		 */
		public void addAllJsonObjects(Collection<JsonObject> collection);

		/**
		 * Adds all elements in a number collection as elements of this JSON
		 * array.
		 *
		 * @param  collection the collection to be added.
		 * @review
		 */
		public void addAllNumbers(Collection<Number> collection);

		/**
		 * Adds all elements in a string collection as elements of this JSON
		 * array.
		 *
		 * @param  collection the collection to be added.
		 * @review
		 */
		public void addAllStrings(Collection<String> collection);

		/**
		 * Adds a new boolean value to the JSON array.
		 *
		 * @param  value value to be added.
		 * @review
		 */
		public void addBoolean(Boolean value);

		/**
		 * Adds a new number value to the JSON array.
		 *
		 * @param  value value to be added.
		 * @review
		 */
		public void addNumber(Number value);

		/**
		 * Adds a new string value to the JSON array.
		 *
		 * @param  value value to be added.
		 * @review
		 */
		public void addString(String value);

	}

	/**
	 * Step to add the value of a field. It can be another JSONObject (field
	 * methods), an JSON array ({@link #arrayValue()} method) or a primitive
	 * value ({@link #stringValue(String)}, {@link #numberValue(Number)} and
	 * {@link #booleanValue(Boolean)} method).
 *
	 * @review
	 */
	public interface FieldStep {

		/**
		 * Starts the creation of a JSON array inside the actual field.
		 *
		 * @return the next step of the builder.
		 * @review
		 */
		public ArrayValueStep arrayValue();

		/**
		 * Adds a new boolean value to the JSON array.
		 *
		 * @param  value value to be added.
		 * @review
		 */
		public void booleanValue(Boolean value);

		/**
		 * Start the creation of a new JSON object field inside the actual
		 * field.
		 *
		 * @param  name the name of the field
		 * @return the next step of the builder.
		 * @review
		 */
		public FieldStep field(String name);

		/**
		 * Starts the creation of a new JSON object field inside the actual
		 * field conditionally. If the handed condition is met, the {@link
		 * FieldStep} created by {@code ifFunction} is returned, otherwise, no
		 * operation is performed.
		 *
		 * @param  condition the condition to check.
		 * @param  ifFunction the function to be used to create the next step if
		 *         the condition is {@code true}.
		 * @return the next step of the builder.
		 * @review
		 */
		public FieldStep ifCondition(
			boolean condition, Function<FieldStep, FieldStep> ifFunction);

		/**
		 * Starts the creation of a new JSON object field inside the actual
		 * field conditionally. If the handed condition is met, the {@code
		 * FieldStep} created by {@code ifFunction} is returned, otherwise, the
		 * {@code FieldStep} created by {@code elseFunction} is returned.
		 *
		 * @param  condition the condition to check.
		 * @param  ifFunction the function to be used to create the next step if
		 *         the condition is {@code true}.
		 * @param  elseFunction the function to be used to create the next step
		 *         if the condition is {@code false}.
		 * @return the next step of the builder.
		 * @review
		 */
		public FieldStep ifElseCondition(
			boolean condition, Function<FieldStep, FieldStep> ifFunction,
			Function<FieldStep, FieldStep> elseFunction);

		/**
		 * Starts the creation of a new JSON object nested field inside the
		 * actual field.
		 *
		 * @param  parentName the name of the first field.
		 * @param  nestedNames the list of names of the subsequent field.
		 * @return the next step of the builder.
		 * @review
		 */
		public FieldStep nestedField(String parentName, String... nestedNames);

		/**
		 * Starts the creation of a new JSON object nested field inside the
		 * actual field. This method behaves like {@link
		 * JSONObjectBuilder#nestedPrefixedField(String, String, String...)}
		 *
		 * @param  prefix the prefix field to use after every field.
		 * @param  parentName the name of the first field.
		 * @param  nestedNames the list of names of the subsequent field.
		 * @return the next step of the builder.
		 * @review
		 */
		public FieldStep nestedPrefixedField(
			String prefix, String parentName, String... nestedNames);

		/**
		 * Starts the creation of a new JSON object nested field inside the
		 * actual field. This method behaves like {@link
		 * JSONObjectBuilder#nestedSuffixedField(String, String, String...)}
		 *
		 * @param  suffix the suffix field to use after every field.
		 * @param  parentName the name of the first field.
		 * @param  nestedNames the list of names of the subsequent field.
		 * @return the next step of the builder.
		 * @review
		 */
		public FieldStep nestedSuffixedField(
			String suffix, String parentName, String... nestedNames);

		/**
		 * Adds a new number value to the JSON array.
		 *
		 * @param  value value to be added.
		 * @review
		 */
		public void numberValue(Number value);

		/**
		 * Adds a new string value to the JSON array.
		 *
		 * @param  value value to be added.
		 * @review
		 */
		public void stringValue(String value);

	}

}