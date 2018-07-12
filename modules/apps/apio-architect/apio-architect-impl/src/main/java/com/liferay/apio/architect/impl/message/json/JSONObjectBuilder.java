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

package com.liferay.apio.architect.impl.message.json;

import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Creates JSON objects. Instances of this interface should be used to write a
 * complete path in each call.
 *
 * <p>
 * For example, this {@code nestedField} call produces the JSON object that
 * follows it:
 * </p>
 *
 * <p>
 * <pre>
 * {@code
 * jsonObjectBuilder
 * 	.nestedField("object", "inner", "other")
 * 	.value(42);
 * }
 * </pre><pre>
 * {@code {
 *      "object": {
 *          "inner": {
 *              "other": 42
 *          }
 *      }
 *   }}
 * </pre></p>
 *
 * <p>
 * {@code JSONObjectBuilder} is incremental, so additional calls add paths to
 * previous calls, respecting the previous state. For example, this {@code
 * nestedField} call adds to the preceding one:
 * </p>
 *
 * <p>
 * <pre>
 * {@code
 * jsonObjectBuilder
 *      .nestedField("object", "inner", "another")
 *      .value("Hello World!");
 * }
 * </pre><pre>
 * {@code {
 *      "object": {
 *          "inner": {
 *              "another": "Hello World!",
 *              "other": 42
 *          }
 *      }
 *   }}
 * </pre></p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public class JSONObjectBuilder {

	public JSONObjectBuilder() {
		_objectNode = _OBJECT_MAPPER.createObjectNode();
	}

	/**
	 * Returns the JSON object constructed by the JSON object builder as a
	 * {@code String}.
	 *
	 * @return the JSON object
	 * @review
	 */
	public String build() {
		try {
			return _OBJECT_MAPPER.writeValueAsString(_objectNode);
		}
		catch (JsonProcessingException jpe) {
			return _objectNode.toString();
		}
	}

	/**
	 * Begins creating a field inside the JSON object.
	 *
	 * @param  name the field's name
	 * @return the builder's next step
	 */
	public FieldStep field(String name) {
		return new FieldStep(name, _objectNode);
	}

	/**
	 * Conditionally begins creating a field inside the JSON object. If the
	 * condition is met, this method returns the field step that {@code
	 * ifFunction} creates. Otherwise, this method returns the field step that
	 * {@code elseFunction} creates.
	 *
	 * @param  condition the condition to check
	 * @param  ifFunction the function that creates the field step if the
	 *         condition is {@code true}
	 * @param  elseFunction the function that creates the field step if the
	 *         condition is {@code false}
	 * @return the builder's field step
	 */
	public FieldStep ifElseCondition(
		boolean condition, Function<JSONObjectBuilder, FieldStep> ifFunction,
		Function<JSONObjectBuilder, FieldStep> elseFunction) {

		if (condition) {
			return ifFunction.apply(this);
		}
		else {
			return elseFunction.apply(this);
		}
	}

	/**
	 * Begins creating a nested field inside the JSON object.
	 *
	 * @param  parentName the parent field's name
	 * @param  nestedNames the nested field's list of names
	 * @return the builder's field step
	 */
	public FieldStep nestedField(String parentName, String... nestedNames) {
		FieldStep fieldStep = field(parentName);

		for (String nestedName : nestedNames) {
			fieldStep = fieldStep.field(nestedName);
		}

		return fieldStep;
	}

	/**
	 * Begins creating a nested field inside the JSON object, adding a prefix to
	 * each level.
	 *
	 * <p>
	 * For example, the following {@code nestedPrefixedField} call produces the
	 * JSON object that follows it:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * {@code jsonObjectBuilder
	 * 	.nestedPrefixedField("prefix", "first", "second")
	 * 	.value(42);
	 * }
	 * </pre><pre>
	 * {@code {
	 * 	"prefix": {
	 * 	    "first": {
	 * 		"prefix": {
	 * 		    "second": 42
	 * 	        }
	 * 	    }
	 *       }
	 *   }}
	 * </pre></p>
	 *
	 * @param  prefix each field's prefix
	 * @param  parentName the parent field's name
	 * @param  nestedNames the list of the nested field names
	 * @return the builder's field step
	 */
	public FieldStep nestedPrefixedField(
		String prefix, String parentName, String... nestedNames) {

		FieldStep fieldStep = nestedField(prefix, parentName);

		for (String nestedName : nestedNames) {
			fieldStep = fieldStep.nestedField(prefix, nestedName);
		}

		return fieldStep;
	}

	/**
	 * Begins creating a nested field inside the JSON object, adding a suffix to
	 * each level.
	 *
	 * <p>
	 * For example, the following {@code nestedSuffixedField} call produces the
	 * JSON object that follows it:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * {@code jsonObjectBuilder
	 * 	.nestedSuffixedField("suffix", "first", "second")
	 * 	.value(42);
	 * }
	 * </pre><pre>
	 * {@code {
	 * 	"first": {
	 * 	    "suffix": {
	 * 		"second": {
	 * 		    "suffix": 42
	 * 	        }
	 * 	    }
	 *       }
	 *   }}
	 * </pre></p>
	 *
	 * @param  suffix each field's suffix
	 * @param  parentName the parent field's name
	 * @param  nestedNames the list of the nested field names
	 * @return the builder's field step
	 */
	public FieldStep nestedSuffixedField(
		String suffix, String parentName, String... nestedNames) {

		FieldStep fieldStep = nestedField(parentName, suffix);

		for (String nestedName : nestedNames) {
			fieldStep = fieldStep.nestedField(nestedName, suffix);
		}

		return fieldStep;
	}

	public static class ArrayValueStep {

		public ArrayValueStep(ArrayNode arrayNode) {
			_arrayNode = arrayNode;
		}

		/**
		 * Adds a new JSON object, created by the provided consumer, to the JSON
		 * array.
		 *
		 * @param consumer the consumer that creates the new JSON object
		 */
		public void add(Consumer<JSONObjectBuilder> consumer) {
			JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilder();

			consumer.accept(jsonObjectBuilder);

			add(jsonObjectBuilder);
		}

		/**
		 * Adds several JSON object, created by the provided consumers, to the
		 * JSON array.
		 *
		 * @param  consumer the consumer that creates the new JSON object
		 * @param  consumers the list of consumers that creates new JSON objects
		 * @review
		 */
		@SafeVarargs
		public final void add(
			Consumer<JSONObjectBuilder> consumer,
			Consumer<JSONObjectBuilder>... consumers) {

			add(consumer);

			for (Consumer<JSONObjectBuilder> jsonObjectBuilderConsumer :
					consumers) {

				add(jsonObjectBuilderConsumer);
			}
		}

		/**
		 * Adds the JSON object, created by the provided JSON object builder, to
		 * the JSON array.
		 *
		 * @param jsonObjectBuilder the JSON object builder containing the JSON
		 *        object to add to the JSON array
		 */
		public void add(JSONObjectBuilder jsonObjectBuilder) {
			_arrayNode.add(jsonObjectBuilder._objectNode);
		}

		/**
		 * Adds all elements of a boolean collection as elements of the JSON
		 * array.
		 *
		 * @param collection the boolean collection to add to the JSON array
		 */
		public void addAllBooleans(Collection<Boolean> collection) {
			collection.forEach(this::addBoolean);
		}

		/**
		 * Adds all elements of a number collection as elements of the JSON
		 * array.
		 *
		 * @param collection the number collection to add to the JSON array
		 */
		public void addAllNumbers(Collection<Number> collection) {
			collection.forEach(this::addNumber);
		}

		/**
		 * Adds all elements of a string collection as elements of the JSON
		 * array.
		 *
		 * @param collection the string collection to add to the JSON array
		 */
		public void addAllStrings(Collection<String> collection) {
			collection.forEach(this::addString);
		}

		/**
		 * Adds a new boolean value to the JSON array.
		 *
		 * @param value the boolean value to add to the JSON array
		 */
		public void addBoolean(Boolean value) {
			_arrayNode.add(value);
		}

		/**
		 * Adds a new number to the JSON array.
		 *
		 * @param value the number to add to the JSON array
		 */
		public void addNumber(Number value) {
			_arrayNode.addPOJO(value);
		}

		/**
		 * Adds a new string to the JSON array.
		 *
		 * @param value the string to add to the JSON array
		 */
		public void addString(String value) {
			_arrayNode.add(value);
		}

		private final ArrayNode _arrayNode;

	}

	/**
	 * Defines the step to add the value of a field. The step can be another
	 * JSON object (field methods), a JSON array ({@link #arrayValue()}), or a
	 * primitive value ({@link #stringValue(String)}, {@link
	 * #numberValue(Number)}, or {@link #booleanValue(Boolean)}).
	 */
	public static class FieldStep {

		public FieldStep(String name, ObjectNode objectNode) {
			_name = name;
			_objectNode = objectNode;
		}

		/**
		 * Begins creating a JSON array inside the field.
		 *
		 * @return the builder's array value step
		 */
		public ArrayValueStep arrayValue() {
			ArrayNode arrayNode = Optional.ofNullable(
				_objectNode.get(_name)
			).filter(
				JsonNode::isArray
			).map(
				ArrayNode.class::cast
			).orElseGet(
				_OBJECT_MAPPER::createArrayNode
			);

			_objectNode.set(_name, arrayNode);

			return new ArrayValueStep(arrayNode);
		}

		/**
		 * Creates a JSON array inside the field and populates it with the
		 * provided consumers.
		 *
		 * @param consumer the consumer that creates the first JSON object of
		 *        the array
		 * @param consumers the list of consumers that creates the rest of JSON
		 *        objects of the array
		 */
		@SafeVarargs
		public final void arrayValue(
			Consumer<ArrayValueStep> consumer,
			Consumer<ArrayValueStep>... consumers) {

			ArrayValueStep arrayValueStep = arrayValue();

			consumer.accept(arrayValueStep);

			for (Consumer<ArrayValueStep> arrayValueStepConsumer : consumers) {
				arrayValueStepConsumer.accept(arrayValueStep);
			}
		}

		/**
		 * Adds a new boolean value to the JSON object.
		 *
		 * @param value the boolean value to add to the JSON object
		 */
		public void booleanValue(Boolean value) {
			_objectNode.put(_name, value);
		}

		/**
		 * Begins creating a new JSON object field.
		 *
		 * @param  name the new field's name
		 * @return the builder's field step
		 */
		public FieldStep field(String name) {
			ObjectNode objectNode = Optional.ofNullable(
				_objectNode.get(_name)
			).filter(
				JsonNode::isObject
			).map(
				ObjectNode.class::cast
			).orElseGet(
				_OBJECT_MAPPER::createObjectNode
			);

			_objectNode.set(_name, objectNode);

			return new FieldStep(name, objectNode);
		}

		/**
		 * Creates a new JSON object inside the field and populates it with the
		 * provided consumers.
		 *
		 * @param consumer the consumer that first populates the JSON Object
		 * @param consumers the rest of the list of consumers that populates the
		 *        JSON Object
		 */
		@SafeVarargs
		public final void fields(
			Consumer<FieldStep> consumer, Consumer<FieldStep>... consumers) {

			consumer.accept(this);

			for (Consumer<FieldStep> fieldStepConsumer : consumers) {
				fieldStepConsumer.accept(this);
			}
		}

		/**
		 * Begins creating a new JSON object field, only if a condition is met.
		 * If the condition is met, this method returns the field step created
		 * by {@code ifFunction}. Otherwise, no operation is performed.
		 *
		 * @param  condition the condition to check
		 * @param  ifFunction the function that creates the field step if the
		 *         condition is {@code true}
		 * @return the builder's field step
		 */
		public FieldStep ifCondition(
			boolean condition, Function<FieldStep, FieldStep> ifFunction) {

			if (condition) {
				return ifFunction.apply(this);
			}
			else {
				return this;
			}
		}

		/**
		 * Begins creating a new JSON object field, where the resulting field
		 * step is conditional. If the condition is met, this method returns the
		 * field step created by {@code ifFunction}. Otherwise, this method
		 * returns the field step created by {@code elseFunction}.
		 *
		 * @param  condition the condition to check
		 * @param  ifFunction the function that creates the field step if the
		 *         condition is {@code true}
		 * @param  elseFunction the function that creates the field step if the
		 *         condition is {@code false}
		 * @return the builder's field step
		 */
		public FieldStep ifElseCondition(
			boolean condition, Function<FieldStep, FieldStep> ifFunction,
			Function<FieldStep, FieldStep> elseFunction) {

			if (condition) {
				return ifFunction.apply(this);
			}
			else {
				return elseFunction.apply(this);
			}
		}

		/**
		 * Begins creating a new nested JSON object field.
		 *
		 * @param  parentName the parent field's name
		 * @param  nestedNames the list of the nested field names
		 * @return the builder's field step
		 */
		public FieldStep nestedField(String parentName, String... nestedNames) {
			FieldStep fieldStep = field(parentName);

			for (String nestedName : nestedNames) {
				fieldStep = fieldStep.field(nestedName);
			}

			return fieldStep;
		}

		/**
		 * Begins creating a new nested JSON object field, adding a prefix to
		 * each field. This method behaves like {@link
		 * JSONObjectBuilder#nestedPrefixedField(String, String, String...)}.
		 *
		 * @param  prefix each field's prefix
		 * @param  parentName the parent field's name
		 * @param  nestedNames the list of the nested field names
		 * @return the builder's field step
		 */
		public FieldStep nestedPrefixedField(
			String prefix, String parentName, String... nestedNames) {

			FieldStep fieldStep = nestedField(prefix, parentName);

			for (String nestedName : nestedNames) {
				fieldStep = fieldStep.nestedField(prefix, nestedName);
			}

			return fieldStep;
		}

		/**
		 * Begins creating a new nested JSON object field, adding a suffix to
		 * each field. This method behaves like {@link
		 * JSONObjectBuilder#nestedSuffixedField(String, String, String...)}.
		 *
		 * @param  suffix each field's suffix
		 * @param  parentName the parent field's name
		 * @param  nestedNames the list of the nested field names
		 * @return the builder's field step
		 */
		public FieldStep nestedSuffixedField(
			String suffix, String parentName, String... nestedNames) {

			FieldStep fieldStep = nestedField(parentName, suffix);

			for (String nestedName : nestedNames) {
				fieldStep = fieldStep.nestedField(nestedName, suffix);
			}

			return fieldStep;
		}

		/**
		 * Adds a new number to the JSON object.
		 *
		 * @param value the number to add to the JSON object
		 */
		public void numberValue(Number value) {
			_objectNode.putPOJO(_name, value);
		}

		/**
		 * Adds the JSON object created by another {@link JSONObjectBuilder}.
		 *
		 * @param  jsonObjectBuilder the {@link JSONObjectBuilder} whose JSON
		 *         object is going to be added
		 * @review
		 */
		public void objectValue(JSONObjectBuilder jsonObjectBuilder) {
			ObjectNode objectNode = jsonObjectBuilder._objectNode;

			_objectNode.set(_name, objectNode);
		}

		/**
		 * Adds a new string to the JSON object.
		 *
		 * @param value the string to add to the JSON object
		 */
		public void stringValue(String value) {
			_objectNode.put(_name, value);
		}

		private final String _name;
		private final ObjectNode _objectNode;

	}

	private static final ObjectMapper _OBJECT_MAPPER;

	static {
		_OBJECT_MAPPER = new ObjectMapper();

		_OBJECT_MAPPER.configure(SORT_PROPERTIES_ALPHABETICALLY, true);
		_OBJECT_MAPPER.enable(INDENT_OUTPUT);
	}

	private final ObjectNode _objectNode;

}