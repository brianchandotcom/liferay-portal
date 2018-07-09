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
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Paulo Cruz
 */
public class FormPageContextWrapper extends BaseFormContextWrapper {

	public FormPageContextWrapper(Map<String, Object> wrappedMap) {
		super(wrappedMap);
	}

	public LocalizedValue getDescription() {
		return getValue("localizedDescription", LocalizedValue.class);
	}

	public List<FormFieldContextWrapper> getFormFieldContexts() {
		return Try.fromFallible(
			() -> getWrappedList("rows", BaseFormContextWrapper::new)
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).map(
			row -> getListFromMap(
				row.getWrappedMap(), "columns", BaseFormContextWrapper::new)
		).flatMap(
			List::stream
		).map(
			column -> getListFromMap(
				column.getWrappedMap(), "fields", FormFieldContextWrapper::new)
		).flatMap(
			List::stream
		).collect(
			Collectors.toList()
		);
	}

	public LocalizedValue getTitle() {
		return getValue("localizedTitle", LocalizedValue.class);
	}

	public boolean isEnabled() {
		return getValue("enabled", Boolean.class);
	}

	public boolean isShowRequiredFieldsWarning() {
		return getValue("showRequiredFieldsWarning", Boolean.class);
	}

}