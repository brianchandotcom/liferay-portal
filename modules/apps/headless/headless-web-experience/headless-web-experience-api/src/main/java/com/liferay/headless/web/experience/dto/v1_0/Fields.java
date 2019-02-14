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

import com.liferay.petra.function.UnsafeSupplier;

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

	public void setDataType(
		UnsafeSupplier<String, Throwable> dataTypeUnsafeSupplier) {

		try {
			dataType = dataTypeUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setInputControl(String inputControl) {
		this.inputControl = inputControl;
	}

	public void setInputControl(
		UnsafeSupplier<String, Throwable> inputControlUnsafeSupplier) {

		try {
			inputControl = inputControlUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setLabel(
		UnsafeSupplier<String, Throwable> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setLocalizable(Boolean localizable) {
		this.localizable = localizable;
	}

	public void setLocalizable(
		UnsafeSupplier<Boolean, Throwable> localizableUnsafeSupplier) {

		try {
			localizable = localizableUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	public void setMultiple(
		UnsafeSupplier<Boolean, Throwable> multipleUnsafeSupplier) {

		try {
			multiple = multipleUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setOptions(Options[] options) {
		this.options = options;
	}

	public void setOptions(
		UnsafeSupplier<Options[], Throwable> optionsUnsafeSupplier) {

		try {
			options = optionsUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setPredefinedValue(String predefinedValue) {
		this.predefinedValue = predefinedValue;
	}

	public void setPredefinedValue(
		UnsafeSupplier<String, Throwable> predefinedValueUnsafeSupplier) {

		try {
			predefinedValue = predefinedValueUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}

	public void setRepeatable(
		UnsafeSupplier<Boolean, Throwable> repeatableUnsafeSupplier) {

		try {
			repeatable = repeatableUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setRequired(
		UnsafeSupplier<Boolean, Throwable> requiredUnsafeSupplier) {

		try {
			required = requiredUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setShowLabel(Boolean showLabel) {
		this.showLabel = showLabel;
	}

	public void setShowLabel(
		UnsafeSupplier<Boolean, Throwable> showLabelUnsafeSupplier) {

		try {
			showLabel = showLabelUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected String dataType;

	@GraphQLField
	protected String inputControl;

	@GraphQLField
	protected String label;

	@GraphQLField
	protected Boolean localizable;

	@GraphQLField
	protected Boolean multiple;

	@GraphQLField
	protected String name;

	@GraphQLField
	protected Options[] options;

	@GraphQLField
	protected String predefinedValue;

	@GraphQLField
	protected Boolean repeatable;

	@GraphQLField
	protected Boolean required;

	@GraphQLField
	protected Boolean showLabel;

}