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

package com.liferay.headless.form.dto.v1_0;

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
@XmlRootElement(name = "Fields")
public class Fields {

	public Boolean getAutocomplete() {
		return autocomplete;
	}

	public String getDataSourceType() {
		return dataSourceType;
	}

	public String getDataType() {
		return dataType;
	}

	public String getDisplayStyle() {
		return displayStyle;
	}

	public Grid getGrid() {
		return grid;
	}

	public Boolean getHasFormRules() {
		return hasFormRules;
	}

	public Long getId() {
		return id;
	}

	public Boolean getImmutable() {
		return immutable;
	}

	public Boolean getInline() {
		return inline;
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

	public Options getOptions() {
		return options;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public String getPredefinedValue() {
		return predefinedValue;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public Boolean getRepeatable() {
		return repeatable;
	}

	public Boolean getRequired() {
		return required;
	}

	public Boolean getShowAsSwitcher() {
		return showAsSwitcher;
	}

	public Boolean getShowLabel() {
		return showLabel;
	}

	public String getStyle() {
		return style;
	}

	public String getText() {
		return text;
	}

	public String getTooltip() {
		return tooltip;
	}

	public Validation getValidation() {
		return validation;
	}

	public void setAutocomplete(Boolean autocomplete) {
		this.autocomplete = autocomplete;
	}

	@JsonIgnore
	public void setAutocomplete(
		UnsafeSupplier<Boolean, Exception> autocompleteUnsafeSupplier) {

		try {
			autocomplete = autocompleteUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	@JsonIgnore
	public void setDataSourceType(
		UnsafeSupplier<String, Exception> dataSourceTypeUnsafeSupplier) {

		try {
			dataSourceType = dataSourceTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
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

	public void setDisplayStyle(String displayStyle) {
		this.displayStyle = displayStyle;
	}

	@JsonIgnore
	public void setDisplayStyle(
		UnsafeSupplier<String, Exception> displayStyleUnsafeSupplier) {

		try {
			displayStyle = displayStyleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	@JsonIgnore
	public void setGrid(UnsafeSupplier<Grid, Exception> gridUnsafeSupplier) {
		try {
			grid = gridUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setHasFormRules(Boolean hasFormRules) {
		this.hasFormRules = hasFormRules;
	}

	@JsonIgnore
	public void setHasFormRules(
		UnsafeSupplier<Boolean, Exception> hasFormRulesUnsafeSupplier) {

		try {
			hasFormRules = hasFormRulesUnsafeSupplier.get();
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

	public void setImmutable(Boolean immutable) {
		this.immutable = immutable;
	}

	@JsonIgnore
	public void setImmutable(
		UnsafeSupplier<Boolean, Exception> immutableUnsafeSupplier) {

		try {
			immutable = immutableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setInline(Boolean inline) {
		this.inline = inline;
	}

	@JsonIgnore
	public void setInline(
		UnsafeSupplier<Boolean, Exception> inlineUnsafeSupplier) {

		try {
			inline = inlineUnsafeSupplier.get();
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

	public void setOptions(Options options) {
		this.options = options;
	}

	@JsonIgnore
	public void setOptions(
		UnsafeSupplier<Options, Exception> optionsUnsafeSupplier) {

		try {
			options = optionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	@JsonIgnore
	public void setPlaceholder(
		UnsafeSupplier<String, Exception> placeholderUnsafeSupplier) {

		try {
			placeholder = placeholderUnsafeSupplier.get();
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

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	@JsonIgnore
	public void setReadOnly(
		UnsafeSupplier<Boolean, Exception> readOnlyUnsafeSupplier) {

		try {
			readOnly = readOnlyUnsafeSupplier.get();
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

	public void setShowAsSwitcher(Boolean showAsSwitcher) {
		this.showAsSwitcher = showAsSwitcher;
	}

	@JsonIgnore
	public void setShowAsSwitcher(
		UnsafeSupplier<Boolean, Exception> showAsSwitcherUnsafeSupplier) {

		try {
			showAsSwitcher = showAsSwitcherUnsafeSupplier.get();
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

	public void setStyle(String style) {
		this.style = style;
	}

	@JsonIgnore
	public void setStyle(
		UnsafeSupplier<String, Exception> styleUnsafeSupplier) {

		try {
			style = styleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	@JsonIgnore
	public void setText(UnsafeSupplier<String, Exception> textUnsafeSupplier) {
		try {
			text = textUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	@JsonIgnore
	public void setTooltip(
		UnsafeSupplier<String, Exception> tooltipUnsafeSupplier) {

		try {
			tooltip = tooltipUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@JsonIgnore
	public void setValidation(
		UnsafeSupplier<Validation, Exception> validationUnsafeSupplier) {

		try {
			validation = validationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setValidation(Validation validation) {
		this.validation = validation;
	}

	public String toString() {
		StringBundler sb = new StringBundler(101);

		sb.append("{");

		sb.append("\"autocomplete\": ");

		sb.append(autocomplete);
		sb.append(", ");

		sb.append("\"dataSourceType\": ");

		sb.append("\"");
		sb.append(dataSourceType);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dataType\": ");

		sb.append("\"");
		sb.append(dataType);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"displayStyle\": ");

		sb.append("\"");
		sb.append(displayStyle);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"grid\": ");

		sb.append(grid);
		sb.append(", ");

		sb.append("\"hasFormRules\": ");

		sb.append(hasFormRules);
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"immutable\": ");

		sb.append(immutable);
		sb.append(", ");

		sb.append("\"inline\": ");

		sb.append(inline);
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

		sb.append(options);
		sb.append(", ");

		sb.append("\"placeholder\": ");

		sb.append("\"");
		sb.append(placeholder);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"predefinedValue\": ");

		sb.append("\"");
		sb.append(predefinedValue);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"readOnly\": ");

		sb.append(readOnly);
		sb.append(", ");

		sb.append("\"repeatable\": ");

		sb.append(repeatable);
		sb.append(", ");

		sb.append("\"required\": ");

		sb.append(required);
		sb.append(", ");

		sb.append("\"showAsSwitcher\": ");

		sb.append(showAsSwitcher);
		sb.append(", ");

		sb.append("\"showLabel\": ");

		sb.append(showLabel);
		sb.append(", ");

		sb.append("\"style\": ");

		sb.append("\"");
		sb.append(style);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"text\": ");

		sb.append("\"");
		sb.append(text);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"tooltip\": ");

		sb.append("\"");
		sb.append(tooltip);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"validation\": ");

		sb.append(validation);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected Boolean autocomplete;

	@GraphQLField
	@JsonProperty
	protected String dataSourceType;

	@GraphQLField
	@JsonProperty
	protected String dataType;

	@GraphQLField
	@JsonProperty
	protected String displayStyle;

	@GraphQLField
	@JsonProperty
	protected Grid grid;

	@GraphQLField
	@JsonProperty
	protected Boolean hasFormRules;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected Boolean immutable;

	@GraphQLField
	@JsonProperty
	protected Boolean inline;

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
	protected Options options;

	@GraphQLField
	@JsonProperty
	protected String placeholder;

	@GraphQLField
	@JsonProperty
	protected String predefinedValue;

	@GraphQLField
	@JsonProperty
	protected Boolean readOnly;

	@GraphQLField
	@JsonProperty
	protected Boolean repeatable;

	@GraphQLField
	@JsonProperty
	protected Boolean required;

	@GraphQLField
	@JsonProperty
	protected Boolean showAsSwitcher;

	@GraphQLField
	@JsonProperty
	protected Boolean showLabel;

	@GraphQLField
	@JsonProperty
	protected String style;

	@GraphQLField
	@JsonProperty
	protected String text;

	@GraphQLField
	@JsonProperty
	protected String tooltip;

	@GraphQLField
	@JsonProperty
	protected Validation validation;

}