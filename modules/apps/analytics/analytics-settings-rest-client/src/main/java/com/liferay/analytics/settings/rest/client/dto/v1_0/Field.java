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

package com.liferay.analytics.settings.rest.client.dto.v1_0;

import com.liferay.analytics.settings.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.settings.rest.client.serdes.v1_0.FieldSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public class Field implements Cloneable, Serializable {

	public static Field toDTO(String json) {
		return FieldSerDes.toDTO(json);
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public void setExample(
		UnsafeSupplier<String, Exception> exampleUnsafeSupplier) {

		try {
			example = exampleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String example;

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

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setRequired(
		UnsafeSupplier<Boolean, Exception> requiredUnsafeSupplier) {

		try {
			required = requiredUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean required;

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public void setSelected(
		UnsafeSupplier<Boolean, Exception> selectedUnsafeSupplier) {

		try {
			selected = selectedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean selected;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setSource(
		UnsafeSupplier<String, Exception> sourceUnsafeSupplier) {

		try {
			source = sourceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String source;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String type;

	@Override
	public Field clone() throws CloneNotSupportedException {
		return (Field)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Field)) {
			return false;
		}

		Field field = (Field)object;

		return Objects.equals(toString(), field.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FieldSerDes.toJSON(this);
	}

}