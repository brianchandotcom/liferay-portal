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

package com.liferay.apio.architect.form;

import java.util.Objects;

/**
 * Represents the field of a {@link Form}.
 *
 * @author Alejandro Hernández
 */
public final class FormField {

	public FormField(String name, boolean required, FieldType fieldType) {
		this.name = name;
		this.required = required;
		this.fieldType = fieldType;
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

		if (Objects.equals(fieldType, formField.fieldType) &&
			Objects.equals(name, formField.name) &&
			Objects.equals(required, formField.required)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, required, fieldType);
	}

	/**
	 * The type of this field.
	 */
	public final FieldType fieldType;

	/**
	 * The name of the field.
	 */
	public final String name;

	/**
	 * {@code true} if the field is required in this {@code Form}; {@code false}
	 * otherwise.
	 */
	public final boolean required;

}