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

package com.liferay.apio.architect.impl.form;

import com.liferay.apio.architect.form.FieldType;
import com.liferay.apio.architect.form.FormField;

import java.util.Objects;

/**
 * @author Alejandro Hernández
 */
public final class FormFieldImpl implements FormField {

	public FormFieldImpl(String name, boolean required, FieldType fieldType) {
		_name = name;
		_required = required;
		_fieldType = fieldType;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof FormField)) {
			return false;
		}

		FormField formField = (FormField)object;

		if (Objects.equals(_fieldType, formField.getFieldType()) &&
			Objects.equals(_name, formField.getName()) &&
			Objects.equals(_required, formField.isRequired())) {

			return true;
		}

		return false;
	}

	@Override
	public FieldType getFieldType() {
		return _fieldType;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_name, _required, _fieldType);
	}

	@Override
	public boolean isRequired() {
		return _required;
	}

	private final FieldType _fieldType;
	private final String _name;
	private final boolean _required;

}