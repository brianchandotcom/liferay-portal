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

package com.liferay.vulcan.jaxrs.json.internal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.vulcan.message.json.JSONObjectBuilder;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public class JSONObjectBuilderImpl implements JSONObjectBuilder {

	@Override
	public JsonObject build() {
		return _jsonObject;
	}

	@Override
	public FieldStep field(String name) {
		return new FieldStepImpl(name, _jsonObject);
	}

	@Override
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

	@Override
	public FieldStep nestedField(String parentName, String... nestedNames) {
		FieldStep fieldStep = field(parentName);

		for (String nestedName : nestedNames) {
			fieldStep = fieldStep.field(nestedName);
		}

		return fieldStep;
	}

	@Override
	public FieldStep nestedPrefixedField(
		String prefix, String parentName, String... nestedNames) {

		FieldStep fieldStep = nestedField(prefix, parentName);

		for (String nestedName : nestedNames) {
			fieldStep = fieldStep.nestedField(prefix, nestedName);
		}

		return fieldStep;
	}

	@Override
	public FieldStep nestedSuffixedField(
		String suffix, String parentName, String... nestedNames) {

		FieldStep fieldStep = nestedField(parentName, suffix);

		for (String nestedName : nestedNames) {
			fieldStep = fieldStep.nestedField(nestedName, suffix);
		}

		return fieldStep;
	}

	public static class ArrayValueStepImpl implements ArrayValueStep {

		public ArrayValueStepImpl(JsonArray jsonArray) {
			_jsonArray = jsonArray;
		}

		@Override
		public void add(Consumer<JSONObjectBuilder> consumer) {
			JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilderImpl();

			consumer.accept(jsonObjectBuilder);

			add(jsonObjectBuilder);
		}

		@Override
		public void add(JSONObjectBuilder jsonObjectBuilder) {
			_jsonArray.add(jsonObjectBuilder.build());
		}

		@Override
		public void addAllBooleans(Collection<Boolean> collection) {
			Stream<Boolean> stream = collection.stream();

			stream.forEach(_jsonArray::add);
		}

		@Override
		public void addAllJsonObjects(Collection<JsonObject> collection) {
			collection.forEach(_jsonArray::add);
		}

		@Override
		public void addAllNumbers(Collection<Number> collection) {
			collection.forEach(_jsonArray::add);
		}

		@Override
		public void addAllStrings(Collection<String> collection) {
			Stream<String> stream = collection.stream();

			stream.forEach(_jsonArray::add);
		}

		@Override
		public void addBoolean(Boolean value) {
			_jsonArray.add(value);
		}

		@Override
		public void addNumber(Number value) {
			_jsonArray.add(value);
		}

		@Override
		public void addString(String value) {
			_jsonArray.add(value);
		}

		private final JsonArray _jsonArray;

	}

	private final JsonObject _jsonObject = new JsonObject();

	private static class FieldStepImpl implements FieldStep {

		public FieldStepImpl(String name, JsonObject jsonObject) {
			_name = name;
			_jsonObject = jsonObject;
		}

		@Override
		public ArrayValueStep arrayValue() {
			Optional<JsonElement> optional = Optional.ofNullable(
				_jsonObject.get(_name));

			JsonArray jsonArray = optional.filter(
				JsonElement::isJsonArray
			).map(
				JsonArray.class::cast
			).orElseGet(
				JsonArray::new
			);

			_jsonObject.add(_name, jsonArray);

			return new ArrayValueStepImpl(jsonArray);
		}

		@Override
		public void booleanValue(Boolean value) {
			_jsonObject.addProperty(_name, value);
		}

		@Override
		public FieldStep field(String name) {
			Optional<JsonElement> optional = Optional.ofNullable(
				_jsonObject.get(_name));

			JsonObject jsonObject = optional.filter(
				JsonElement::isJsonObject
			).map(
				JsonObject.class::cast
			).orElseGet(
				JsonObject::new
			);

			_jsonObject.add(_name, jsonObject);

			return new FieldStepImpl(name, jsonObject);
		}

		@Override
		public FieldStep ifCondition(
			boolean condition, Function<FieldStep, FieldStep> ifFunction) {

			if (condition) {
				return ifFunction.apply(this);
			}
			else {
				return this;
			}
		}

		@Override
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

		@Override
		public FieldStep nestedField(String parentName, String... nestedNames) {
			FieldStep fieldStep = field(parentName);

			for (String nestedName : nestedNames) {
				fieldStep = fieldStep.field(nestedName);
			}

			return fieldStep;
		}

		@Override
		public FieldStep nestedPrefixedField(
			String prefix, String parentName, String... nestedNames) {

			FieldStep fieldStep = nestedField(prefix, parentName);

			for (String nestedName : nestedNames) {
				fieldStep = fieldStep.nestedField(prefix, nestedName);
			}

			return fieldStep;
		}

		@Override
		public FieldStep nestedSuffixedField(
			String suffix, String parentName, String... nestedNames) {

			FieldStep fieldStep = nestedField(parentName, suffix);

			for (String nestedName : nestedNames) {
				fieldStep = fieldStep.nestedField(nestedName, suffix);
			}

			return fieldStep;
		}

		@Override
		public void numberValue(Number value) {
			_jsonObject.addProperty(_name, value);
		}

		@Override
		public void stringValue(String value) {
			_jsonObject.addProperty(_name, value);
		}

		private final JsonObject _jsonObject;
		private final String _name;

	}

}