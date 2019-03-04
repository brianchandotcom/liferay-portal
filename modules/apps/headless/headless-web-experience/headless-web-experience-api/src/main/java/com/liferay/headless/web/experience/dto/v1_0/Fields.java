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
@GraphQLName("Fields")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "Fields")
public class Fields {

	public String getDataType() {
		return dataType;
	}

	public String getInputControl() {
		return inputControl;
	}

	public String getLabel() {
		return label;
	}

	public Boolean getLocalizable() {
		return localizable;
	}

	public Boolean getMultiple() {
		return multiple;
	}

	public String getName() {
		return name;
	}

	public Options[] getOptions() {
		return options;
	}

	public String getPredefinedValue() {
		return predefinedValue;
	}

	public Boolean getRepeatable() {
		return repeatable;
	}

	public Boolean getRequired() {
		return required;
	}

	public Boolean getShowLabel() {
		return showLabel;
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

	public void setLocalizable(Boolean localizable) {
		this.localizable = localizable;
	}

	@JsonIgnore
	public void setLocalizable(
		UnsafeSupplier<Boolean, Exception> localizableUnsafeSupplier) {

		try {
			localizable = localizableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	@JsonIgnore
	public void setMultiple(
		UnsafeSupplier<Boolean, Exception> multipleUnsafeSupplier) {

		try {
			multiple = multipleUnsafeSupplier.get();
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

	public void setOptions(Options[] options) {
		this.options = options;
	}

	@JsonIgnore
	public void setOptions(
		UnsafeSupplier<Options[], Exception> optionsUnsafeSupplier) {

		try {
			options = optionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setPredefinedValue(String predefinedValue) {
		this.predefinedValue = predefinedValue;
	}

	@JsonIgnore
	public void setPredefinedValue(
		UnsafeSupplier<String, Exception> predefinedValueUnsafeSupplier) {

		try {
			predefinedValue = predefinedValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}

	@JsonIgnore
	public void setRepeatable(
		UnsafeSupplier<Boolean, Exception> repeatableUnsafeSupplier) {

		try {
			repeatable = repeatableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@JsonIgnore
	public void setRequired(
		UnsafeSupplier<Boolean, Exception> requiredUnsafeSupplier) {

		try {
			required = requiredUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setShowLabel(Boolean showLabel) {
		this.showLabel = showLabel;
	}

	@JsonIgnore
	public void setShowLabel(
		UnsafeSupplier<Boolean, Exception> showLabelUnsafeSupplier) {

		try {
			showLabel = showLabelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(46);

		sb.append("{");

		sb.append("\"dataType\": ");

		sb.append("\"");
		sb.append(dataType);
		sb.append("\"");
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

		sb.append("\"localizable\": ");

		sb.append(localizable);
		sb.append(", ");

		sb.append("\"multiple\": ");

		sb.append(multiple);
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"options\": ");

		sb.append("\"");
		sb.append(options);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"predefinedValue\": ");

		sb.append("\"");
		sb.append(predefinedValue);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"repeatable\": ");

		sb.append(repeatable);
		sb.append(", ");

		sb.append("\"required\": ");

		sb.append(required);
		sb.append(", ");

		sb.append("\"showLabel\": ");

		sb.append(showLabel);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String dataType;

	@GraphQLField
	@JsonProperty
	protected String inputControl;

	@GraphQLField
	@JsonProperty
	protected String label;

	@GraphQLField
	@JsonProperty
	protected Boolean localizable;

	@GraphQLField
	@JsonProperty
	protected Boolean multiple;

	@GraphQLField
	@JsonProperty
	protected String name;

	@GraphQLField
	@JsonProperty
	protected Options[] options;

	@GraphQLField
	@JsonProperty
	protected String predefinedValue;

	@GraphQLField
	@JsonProperty
	protected Boolean repeatable;

	@GraphQLField
	@JsonProperty
	protected Boolean required;

	@GraphQLField
	@JsonProperty
	protected Boolean showLabel;

}