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

package com.liferay.vulcan.jaxrs.writer.json.internal.writer;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.vulcan.message.json.JSONObjectBuilder;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public class JSONObjectBuilderImpl implements JSONObjectBuilder {

	@Override
	public JSONObject build() {
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

		public ArrayValueStepImpl(JSONArray jsonArray) {
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
			_jsonArray.put(jsonObjectBuilder.build());
		}

		@Override
		public void add(Object value) {
			if (value != null) {
				_jsonArray.put(value);
			}
		}

		@Override
		public <T> void addAll(Collection<T> collection) {
			collection.forEach(_jsonArray::put);
		}

		private final JSONArray _jsonArray;

	}

	private final JSONObject _jsonObject = JSONFactoryUtil.createJSONObject();

	private static class FieldStepImpl implements FieldStep {

		public FieldStepImpl(String name, JSONObject jsonObject) {
			_name = name;
			_stepJSONObject = jsonObject;
		}

		@Override
		public ArrayValueStep arrayValue() {
			JSONArray jsonArray = _stepJSONObject.getJSONArray(_name);

			if (jsonArray == null) {
				jsonArray = JSONFactoryUtil.createJSONArray();

				_stepJSONObject.put(_name, jsonArray);
			}

			return new ArrayValueStepImpl(jsonArray);
		}

		@Override
		public FieldStep field(String name) {
			JSONObject previousJSONObject = _stepJSONObject.getJSONObject(
				_name);

			if (previousJSONObject == null) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				_stepJSONObject.put(_name, jsonObject);

				return new FieldStepImpl(name, jsonObject);
			}

			return new FieldStepImpl(name, previousJSONObject);
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
		public void value(Object value) {
			if (value != null) {
				_stepJSONObject.put(_name, value);
			}
		}

		private final String _name;
		private final JSONObject _stepJSONObject;

	}

}