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

import com.liferay.portal.kernel.json.JSONObject;

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
 * <code> jsonObjectBuilder.nestedField("object", "inner", "other").value(42);
 * </code>
 * </p>
 *
 * <p>
 * Results in the following JSON object:
 * </p>
 *
 * <p>
 * <code> { "object": { "inner": { "other": 42 } } } </code>
 * </p>
 *
 * This builder is incremental, so, once you have made the first call, you can
 * continue with the next one, and the following paths will be added to the
 * previous one, respecting the previous state.
 *
 * For example, making this call to the previous builder:
 *
 * <p>
 * <code> jsonObjectBuilder.nestedField("object", "inner",
 * "another").value("Hello World!"); </code>
 * </p>
 *
 * <p>
 * Results in the following JSON object:
 * </p>
 *
 * <p>
 * <code> { "object": { "inner": { "another": "Hello World!", "other": 42 } } }
 * </code>
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@ProviderType
public interface JSONObjectBuilder {

	/**
	 * Returns the {@link JSONObject} constructed by this builder.
	 *
	 * @return the constructed JSON object.
	 */
	public JSONObject build();

	/**
	 * Start the creation of a field inside the JSON object.
	 *
	 * @param  name the name of the field
	 * @return the next step of the builder.
	 */
	public FieldStep field(String name);

	/**
	 * Starts the creation of a field inside the JSON object conditionally. If
	 * the handed condition is met, the {@link FieldStep} created by
	 * <code>ifFunction</code> is returned, otherwise, the {@link FieldStep}
	 * created by <code>elseFunction</code> is returned.
	 *
	 * @param  condition the condition to check.
	 * @param  ifFunction the function to be used to create the next step if the
	 *         condition is <code>true</code>.
	 * @param  elseFunction the function to be used to create the next step if
	 *         the condition is <code>false</code>.
	 * @return the next step of the builder.
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
	 */
	public FieldStep nestedField(String parentName, String... nestedNames);

	/**
	 * Starts the creation of a nested field inside the JSON object. This method
	 * behaves like {@link #nestedField(String, String...)} except it adds a
	 * <code>prefix</code> before every level. For example:
	 *
	 * <p>
	 * The following code:
	 * </p>
	 *
	 * <p>
	 * <code>jsonObjectBuilder.nestedPrefixedField("prefix", "first",
	 * "second").value(42);</code>
	 * </p>
	 *
	 * <p>
	 * Results in the following JSON object:
	 * </p>
	 *
	 * <p>
	 * <code> { "prefix": { "first": { "prefix": { "second": 42 } } } </code>
	 * </p>
	 *
	 * @param  prefix the prefix field to use before every field.
	 * @param  parentName the name of the first field.
	 * @param  nestedNames the list of names of the subsequent field.
	 * @return the next step of the builder.
	 */
	public FieldStep nestedPrefixedField(
		String prefix, String parentName, String... nestedNames);

	/**
	 * Starts the creation of a nested field inside the JSON object. This method
	 * behaves like {@link #nestedField(String, String...)} except it adds a
	 * <code>suffix</code> after every level. For example:
	 *
	 * <p>
	 * The following code:
	 * </p>
	 *
	 * <p>
	 * <code>jsonObjectBuilder.nestedSuffixedField("suffix", "first",
	 * "second").value(42);</code>
	 * </p>
	 *
	 * <p>
	 * Results in the following JSON object:
	 * </p>
	 *
	 * <p>
	 * <code> { "first": { "suffix": { "second": { "suffix": 42 } } } </code>
	 * </p>
	 *
	 * @param  suffix the suffix field to use after every field.
	 * @param  parentName the name of the first field.
	 * @param  nestedNames the list of names of the subsequent field.
	 * @return the next step of the builder.
	 */
	public FieldStep nestedSuffixedField(
		String suffix, String parentName, String... nestedNames);

	/**
	 * Step to add the value of a field as a JSON array.
	 */
	public interface ArrayValueStep {

		/**
		 * Adds a new jsonObject to the JSON array, created by the provided
		 * consumer.
		 *
		 * @param consumer consumer used to create the new JSON object.
		 */
		public void add(Consumer<JSONObjectBuilder> consumer);

		/**
		 * Adds the jsonObject created by the provided
		 * {@link JSONObjectBuilder}.
		 *
		 * @param jsonObjectBuilder <code>JSONObjectBuilder</code> whose JSON
		 *                          object is going to be added
		 */
		public void add(JSONObjectBuilder jsonObjectBuilder);

		/**
		 * Adds a new primitive value to the JSON array. It must be a
		 * {@link String}, {@link Number} or {@link Boolean} value.
		 *
		 * @param value value to be added.
		 */
		public void add(Object value);

		/**
		 * Adds all elements in a collection as elements of this JSON object.
		 * The collection must have elements of one of the following types:
		 * {@link String}, {@link Number} or {@link Boolean} value.
		 *
		 * @param collection the collection to be added.
		 */
		public <T> void addAll(Collection<T> collection);

	}

	/**
	 * Step to add the value of a field. It can be another JSONObject
	 * (field methods), an JSON array ({@link #arrayValue()} method) or a
	 * primitive value ({@link #value(Object)} method).
	 */
	public interface FieldStep {

		/**
		 * Starts the creation of a JSON array inside the actual field.
		 *
		 * @return the next step of the builder.
		 */
		public ArrayValueStep arrayValue();

		/**
		 * Start the creation of a new JSON object field inside the actual
		 * field.
		 *
		 * @param  name the name of the field
		 * @return the next step of the builder.
		 */
		public FieldStep field(String name);

		/**
		 * Starts the creation of a new JSON object field inside the actual
		 * field conditionally. If the handed condition is met, the
		 * {@link FieldStep} created by <code>ifFunction</code> is returned,
		 * otherwise, no operation is performed.
		 *
		 * @param  condition the condition to check.
		 * @param  ifFunction the function to be used to create the next step
		 *         if the condition is <code>true</code>.
		 * @return the next step of the builder.
		 */
		public FieldStep ifCondition(
			boolean condition, Function<FieldStep, FieldStep> ifFunction);

		/**
		 * Starts the creation of a new JSON object field inside the actual
		 * field conditionally. If the handed condition is met, the
		 * {@link FieldStep} created by <code>ifFunction</code> is returned,
		 * otherwise, the {@link FieldStep} created by <code>elseFunction</code>
		 * is returned.
		 *
		 * @param  condition the condition to check.
		 * @param  ifFunction the function to be used to create the next step if
		 *         the condition is <code>true</code>.
		 * @param  elseFunction the function to be used to create the next step
		 *         if the condition is <code>false</code>.
		 * @return the next step of the builder.
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
		 */
		public FieldStep nestedField(String parentName, String... nestedNames);

		/**
		 * Starts the creation of a new JSON object nested field inside the
		 * actual field. This method behaves like
		 * {@link JSONObjectBuilder#nestedPrefixedField(
		 * String, String, String...)}
		 *
		 *
		 * @param prefix the prefix field to use after every field.
		 * @param parentName the name of the first field.
		 * @param  nestedNames the list of names of the subsequent field.
		 * @return the next step of the builder.
		 */
		public FieldStep nestedPrefixedField(
			String prefix, String parentName, String... nestedNames);

		/**
		 * Starts the creation of a new JSON object nested field inside the
		 * actual field. This method behaves like
		 * {@link JSONObjectBuilder#nestedSuffixedField(
		 * String, String, String...)}
		 *
		 *
		 * @param suffix the suffix field to use after every field.
		 * @param parentName the name of the first field.
		 * @param  nestedNames the list of names of the subsequent field.
		 * @return the next step of the builder.
		 */
		public FieldStep nestedSuffixedField(
			String suffix, String parentName, String... nestedNames);

		/**
		 * Adds a primitive value to the actual field. It must be a
		 * {@link String}, {@link Number} or {@link Boolean} value.
		 *
		 * @param value the value to be added to the field.
		 */
		public void value(Object value);

	}

}