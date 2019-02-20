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

package com.liferay.headless.web.experience.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.web.experience.dto.v1_0.Fields;
import com.liferay.headless.web.experience.dto.v1_0.Options;
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
public class FieldsImpl implements Fields {

	public String getDataType() {
			return dataType;
	}

	public void setDataType(
			String dataType) {

			this.dataType = dataType;
	}

	@JsonIgnore
	public void setDataType(
			UnsafeSupplier<String, Throwable>
				dataTypeUnsafeSupplier) {

			try {
				dataType =
					dataTypeUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String dataType;
	public String getLabel() {
			return label;
	}

	public void setLabel(
			String label) {

			this.label = label;
	}

	@JsonIgnore
	public void setLabel(
			UnsafeSupplier<String, Throwable>
				labelUnsafeSupplier) {

			try {
				label =
					labelUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String label;
	public Boolean getLocalizable() {
			return localizable;
	}

	public void setLocalizable(
			Boolean localizable) {

			this.localizable = localizable;
	}

	@JsonIgnore
	public void setLocalizable(
			UnsafeSupplier<Boolean, Throwable>
				localizableUnsafeSupplier) {

			try {
				localizable =
					localizableUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean localizable;
	public Boolean getMultiple() {
			return multiple;
	}

	public void setMultiple(
			Boolean multiple) {

			this.multiple = multiple;
	}

	@JsonIgnore
	public void setMultiple(
			UnsafeSupplier<Boolean, Throwable>
				multipleUnsafeSupplier) {

			try {
				multiple =
					multipleUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean multiple;
	public String getInputControl() {
			return inputControl;
	}

	public void setInputControl(
			String inputControl) {

			this.inputControl = inputControl;
	}

	@JsonIgnore
	public void setInputControl(
			UnsafeSupplier<String, Throwable>
				inputControlUnsafeSupplier) {

			try {
				inputControl =
					inputControlUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String inputControl;
	public String getPredefinedValue() {
			return predefinedValue;
	}

	public void setPredefinedValue(
			String predefinedValue) {

			this.predefinedValue = predefinedValue;
	}

	@JsonIgnore
	public void setPredefinedValue(
			UnsafeSupplier<String, Throwable>
				predefinedValueUnsafeSupplier) {

			try {
				predefinedValue =
					predefinedValueUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String predefinedValue;
	public String getName() {
			return name;
	}

	public void setName(
			String name) {

			this.name = name;
	}

	@JsonIgnore
	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier) {

			try {
				name =
					nameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String name;
	public Boolean getRepeatable() {
			return repeatable;
	}

	public void setRepeatable(
			Boolean repeatable) {

			this.repeatable = repeatable;
	}

	@JsonIgnore
	public void setRepeatable(
			UnsafeSupplier<Boolean, Throwable>
				repeatableUnsafeSupplier) {

			try {
				repeatable =
					repeatableUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean repeatable;
	public Boolean getRequired() {
			return required;
	}

	public void setRequired(
			Boolean required) {

			this.required = required;
	}

	@JsonIgnore
	public void setRequired(
			UnsafeSupplier<Boolean, Throwable>
				requiredUnsafeSupplier) {

			try {
				required =
					requiredUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean required;
	public Boolean getShowLabel() {
			return showLabel;
	}

	public void setShowLabel(
			Boolean showLabel) {

			this.showLabel = showLabel;
	}

	@JsonIgnore
	public void setShowLabel(
			UnsafeSupplier<Boolean, Throwable>
				showLabelUnsafeSupplier) {

			try {
				showLabel =
					showLabelUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean showLabel;
	public Options[] getOptions() {
			return options;
	}

	public void setOptions(
			Options[] options) {

			this.options = options;
	}

	@JsonIgnore
	public void setOptions(
			UnsafeSupplier<Options[], Throwable>
				optionsUnsafeSupplier) {

			try {
				options =
					optionsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Options[] options;

}