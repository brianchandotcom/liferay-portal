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

package com.liferay.headless.web.experience.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("ContentField")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "ContentField")
public class ContentField {

	public String getDataType() {
		return dataType;
	}

	public Long getId() {
		return id;
	}

	public String getInputControl() {
		return inputControl;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public ContentField[] getNestedFields() {
		return nestedFields;
	}

	public Value getValue() {
		return value;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@JsonIgnore
	public void setDataType(
		UnsafeSupplier<String, Exception> dataTypeUnsafeSupplier) {

		try {
			dataType = dataTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setInputControl(String inputControl) {
		this.inputControl = inputControl;
	}

	@JsonIgnore
	public void setInputControl(
		UnsafeSupplier<String, Exception> inputControlUnsafeSupplier) {

		try {
			inputControl = inputControlUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@JsonIgnore
	public void setLabel(
		UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setNestedFields(ContentField[] nestedFields) {
		this.nestedFields = nestedFields;
	}

	@JsonIgnore
	public void setNestedFields(
		UnsafeSupplier<ContentField[], Exception> nestedFieldsUnsafeSupplier) {

		try {
			nestedFields = nestedFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@JsonIgnore
	public void setValue(UnsafeSupplier<Value, Exception> valueUnsafeSupplier) {
		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public String toString() {
		StringBundler sb = new StringBundler(32);

		sb.append("{");

		sb.append("\"dataType\": ");

		sb.append("\"");
		sb.append(dataType);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"inputControl\": ");

		sb.append("\"");
		sb.append(inputControl);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"label\": ");

		sb.append("\"");
		sb.append(label);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"nestedFields\": ");

		sb.append("\"");
		sb.append(nestedFields);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"value\": ");

		sb.append(value);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String dataType;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String inputControl;

	@GraphQLField
	@JsonProperty
	protected String label;

	@GraphQLField
	@JsonProperty
	protected String name;

	@GraphQLField
	@JsonProperty
	protected ContentField[] nestedFields;

	@GraphQLField
	@JsonProperty
	protected Value value;

}