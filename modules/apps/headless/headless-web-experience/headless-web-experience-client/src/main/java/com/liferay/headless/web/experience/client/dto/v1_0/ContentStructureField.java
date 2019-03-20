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

package com.liferay.headless.web.experience.client.dto.v1_0;

import com.liferay.headless.web.experience.client.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentStructureField {

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

	public Boolean getLocalizable() {
		return localizable;
	}

	public void setLocalizable(Boolean localizable) {
		this.localizable = localizable;
	}

	public void setLocalizable(
		UnsafeSupplier<Boolean, Exception> localizableUnsafeSupplier) {

		try {
			localizable = localizableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean localizable;

	public Boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	public void setMultiple(
		UnsafeSupplier<Boolean, Exception> multipleUnsafeSupplier) {

		try {
			multiple = multipleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean multiple;

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

	public com.liferay.headless.web.experience.dto.v1_0.ContentStructureField[]
		getNestedContentStructureFields() {

		return nestedContentStructureFields;
	}

	public void setNestedContentStructureFields(
		com.liferay.headless.web.experience.dto.v1_0.ContentStructureField[]
			nestedContentStructureFields) {

		this.nestedContentStructureFields = nestedContentStructureFields;
	}

	public void setNestedContentStructureFields(
		UnsafeSupplier
			<com.liferay.headless.web.experience.dto.v1_0.
				ContentStructureField[],
			 Exception> nestedContentStructureFieldsUnsafeSupplier) {

		try {
			nestedContentStructureFields =
				nestedContentStructureFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected
		com.liferay.headless.web.experience.dto.v1_0.ContentStructureField[]
			nestedContentStructureFields;

	public Option[] getOptions() {
		return options;
	}

	public void setOptions(Option[] options) {
		this.options = options;
	}

	public void setOptions(
		UnsafeSupplier<Option[], Exception> optionsUnsafeSupplier) {

		try {
			options = optionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Option[] options;

	public String getPredefinedValue() {
		return predefinedValue;
	}

	public void setPredefinedValue(String predefinedValue) {
		this.predefinedValue = predefinedValue;
	}

	public void setPredefinedValue(
		UnsafeSupplier<String, Exception> predefinedValueUnsafeSupplier) {

		try {
			predefinedValue = predefinedValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String predefinedValue;

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

	public Boolean getShowLabel() {
		return showLabel;
	}

	public void setShowLabel(Boolean showLabel) {
		this.showLabel = showLabel;
	}

	public void setShowLabel(
		UnsafeSupplier<Boolean, Exception> showLabelUnsafeSupplier) {

		try {
			showLabel = showLabelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean showLabel;

}