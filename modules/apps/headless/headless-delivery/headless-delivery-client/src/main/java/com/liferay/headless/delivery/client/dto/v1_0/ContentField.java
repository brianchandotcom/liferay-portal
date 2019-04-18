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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentField {

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setDataType(
		UnsafeSupplier<String, Exception> dataTypeUnsafeSupplier) {

		try {
			dataType = dataTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String dataType;

	public String getInputControl() {
		return inputControl;
	}

	public void setInputControl(String inputControl) {
		this.inputControl = inputControl;
	}

	public void setInputControl(
		UnsafeSupplier<String, Exception> inputControlUnsafeSupplier) {

		try {
			inputControl = inputControlUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String inputControl;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setLabel(
		UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String label;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public ContentField[] getNestedFields() {
		return nestedFields;
	}

	public void setNestedFields(ContentField[] nestedFields) {
		this.nestedFields = nestedFields;
	}

	public void setNestedFields(
		UnsafeSupplier<ContentField[], Exception> nestedFieldsUnsafeSupplier) {

		try {
			nestedFields = nestedFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ContentField[] nestedFields;

	public Boolean getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}

	public void setRepeatable(
		UnsafeSupplier<Boolean, Exception> repeatableUnsafeSupplier) {

		try {
			repeatable = repeatableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean repeatable;

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public void setValue(UnsafeSupplier<Value, Exception> valueUnsafeSupplier) {
		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Value value;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContentField)) {
			return false;
		}

		ContentField contentField = (ContentField)object;

		return Objects.equals(toString(), contentField.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"dataType\": ");

		if (dataType == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dataType);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"inputControl\": ");

		if (inputControl == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(inputControl);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"label\": ");

		if (label == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(label);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (name == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(name);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"nestedFields\": ");

		if (nestedFields == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < nestedFields.length; i++) {
				sb.append(nestedFields[i]);

				if ((i + 1) < nestedFields.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"repeatable\": ");

		if (repeatable == null) {
			sb.append("null");
		}
		else {
			sb.append(repeatable);
		}

		sb.append(", ");

		sb.append("\"value\": ");

		if (value == null) {
			sb.append("null");
		}
		else {
			sb.append(value);
		}

		sb.append("}");

		return sb.toString();
	}

}