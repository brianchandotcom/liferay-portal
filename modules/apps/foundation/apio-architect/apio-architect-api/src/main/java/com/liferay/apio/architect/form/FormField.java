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
 * Instances of this class represents the field of a {@code Form}.
 *
 * @author Alejandro Hernández
 * @review
 */
public final class FormField {

	public FormField(String name, boolean isRequired, FieldType fieldType) {
		this.name = name;
		this.isRequired = isRequired;
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

		if (Objects.equals(name, formField.name) &&
			Objects.equals(isRequired, formField.isRequired) &&
			Objects.equals(fieldType, formField.fieldType)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, isRequired, fieldType);
	}

	/**
	 * The type of this field.
	 */
	public final FieldType fieldType;

	/**
	 * {@code true} if the field is required in this {@code Form}; {@code false}
	 * otherwise.
	 */
	public final boolean isRequired;

	/**
	 * The name of the field.
	 */
	public final String name;

}