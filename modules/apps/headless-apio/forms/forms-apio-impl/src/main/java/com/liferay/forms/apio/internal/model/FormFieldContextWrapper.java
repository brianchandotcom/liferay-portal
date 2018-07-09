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

package com.liferay.forms.apio.internal.model;

import com.liferay.apio.architect.functional.Try;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Paulo Cruz
 */
public class FormFieldContextWrapper extends BaseFormContextWrapper {

	public FormFieldContextWrapper(Map<String, Object> wrappedMap) {
		super(wrappedMap);
	}

	public String getErrorMessage() {
		return getValue("errorMessage", String.class);
	}

	public String getName() {
		return getValue("fieldName", String.class);
	}

	public List<KeyValuePair> getOptions() {
		return Try.fromFallible(
			() -> getWrappedList("options", BaseFormContextWrapper::new)
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).map(
			this::_getOptionsPair
		).collect(
			Collectors.toList()
		);
	}

	public String getValue() {
		return getValue("value", Object::toString);
	}

	public boolean isEvaluable() {
		return getValueOrDefault("evaluable", Boolean.class, false);
	}

	public boolean isReadOnly() {
		return getValue("readOnly", Boolean.class);
	}

	public boolean isRequired() {
		return getValue("required", Boolean.class);
	}

	public boolean isValid() {
		return getValue("valid", Boolean.class);
	}

	public boolean isValueChanged() {
		return getValue("valueChanged", Boolean.class);
	}

	public boolean isVisible() {
		return getValue("visible", Boolean.class);
	}

	private KeyValuePair _getOptionsPair(
		BaseFormContextWrapper optionsWrapper) {

		String label = optionsWrapper.getValue("label", String.class);
		String value = optionsWrapper.getValue("value", String.class);

		return new KeyValuePair(value, label);
	}

}