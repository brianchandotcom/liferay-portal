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

	public void setAutocomplete(
		UnsafeSupplier<Boolean, Throwable> autocompleteUnsafeSupplier) {

			try {
				autocomplete = autocompleteUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public void setDataSourceType(
		UnsafeSupplier<String, Throwable> dataSourceTypeUnsafeSupplier) {

			try {
				dataSourceType = dataSourceTypeUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
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

	public void setDisplayStyle(String displayStyle) {
		this.displayStyle = displayStyle;
	}

	public void setDisplayStyle(
		UnsafeSupplier<String, Throwable> displayStyleUnsafeSupplier) {

			try {
				displayStyle = displayStyleUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public void setGrid(UnsafeSupplier<Grid, Throwable> gridUnsafeSupplier) {
			try {
				grid = gridUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setHasFormRules(Boolean hasFormRules) {
		this.hasFormRules = hasFormRules;
	}

	public void setHasFormRules(
		UnsafeSupplier<Boolean, Throwable> hasFormRulesUnsafeSupplier) {

			try {
				hasFormRules = hasFormRulesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
			try {
				id = idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setImmutable(Boolean immutable) {
		this.immutable = immutable;
	}

	public void setImmutable(
		UnsafeSupplier<Boolean, Throwable> immutableUnsafeSupplier) {

			try {
				immutable = immutableUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setInline(Boolean inline) {
		this.inline = inline;
	}

	public void setInline(
		UnsafeSupplier<Boolean, Throwable> inlineUnsafeSupplier) {

			try {
				inline = inlineUnsafeSupplier.get();
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

	public void setOptions(Options options) {
		this.options = options;
	}

	public void setOptions(
		UnsafeSupplier<Options, Throwable> optionsUnsafeSupplier) {

			try {
				options = optionsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public void setPlaceholder(
		UnsafeSupplier<String, Throwable> placeholderUnsafeSupplier) {

			try {
				placeholder = placeholderUnsafeSupplier.get();
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

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setReadOnly(
		UnsafeSupplier<Boolean, Throwable> readOnlyUnsafeSupplier) {

			try {
				readOnly = readOnlyUnsafeSupplier.get();
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

	public void setShowAsSwitcher(Boolean showAsSwitcher) {
		this.showAsSwitcher = showAsSwitcher;
	}

	public void setShowAsSwitcher(
		UnsafeSupplier<Boolean, Throwable> showAsSwitcherUnsafeSupplier) {

			try {
				showAsSwitcher = showAsSwitcherUnsafeSupplier.get();
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

	public void setStyle(String style) {
		this.style = style;
	}

	public void setStyle(
		UnsafeSupplier<String, Throwable> styleUnsafeSupplier) {

			try {
				style = styleUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setText(UnsafeSupplier<String, Throwable> textUnsafeSupplier) {
			try {
				text = textUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public void setTooltip(
		UnsafeSupplier<String, Throwable> tooltipUnsafeSupplier) {

			try {
				tooltip = tooltipUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setValidation(
		UnsafeSupplier<Validation, Throwable> validationUnsafeSupplier) {

			try {
				validation = validationUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setValidation(Validation validation) {
		this.validation = validation;
	}

	@GraphQLField
	protected Boolean autocomplete;

	@GraphQLField
	protected String dataSourceType;

	@GraphQLField
	protected String dataType;

	@GraphQLField
	protected String displayStyle;

	@GraphQLField
	protected Grid grid;

	@GraphQLField
	protected Boolean hasFormRules;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected Boolean immutable;

	@GraphQLField
	protected Boolean inline;

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
	protected Options options;

	@GraphQLField
	protected String placeholder;

	@GraphQLField
	protected String predefinedValue;

	@GraphQLField
	protected Boolean readOnly;

	@GraphQLField
	protected Boolean repeatable;

	@GraphQLField
	protected Boolean required;

	@GraphQLField
	protected Boolean showAsSwitcher;

	@GraphQLField
	protected Boolean showLabel;

	@GraphQLField
	protected String style;

	@GraphQLField
	protected String text;

	@GraphQLField
	protected String tooltip;

	@GraphQLField
	protected Validation validation;

}