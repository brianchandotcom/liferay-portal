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

package com.liferay.headless.form.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.form.dto.v1_0.Fields;
import com.liferay.headless.form.dto.v1_0.Grid;
import com.liferay.headless.form.dto.v1_0.Options;
import com.liferay.headless.form.dto.v1_0.Validation;
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

	public Boolean getAutocomplete() {
			return autocomplete;
	}

	public void setAutocomplete(
			Boolean autocomplete) {

			this.autocomplete = autocomplete;
	}

	@JsonIgnore
	public void setAutocomplete(
			UnsafeSupplier<Boolean, Throwable>
				autocompleteUnsafeSupplier) {

			try {
				autocomplete =
					autocompleteUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean autocomplete;
	public String getDataSourceType() {
			return dataSourceType;
	}

	public void setDataSourceType(
			String dataSourceType) {

			this.dataSourceType = dataSourceType;
	}

	@JsonIgnore
	public void setDataSourceType(
			UnsafeSupplier<String, Throwable>
				dataSourceTypeUnsafeSupplier) {

			try {
				dataSourceType =
					dataSourceTypeUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String dataSourceType;
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
	public String getDisplayStyle() {
			return displayStyle;
	}

	public void setDisplayStyle(
			String displayStyle) {

			this.displayStyle = displayStyle;
	}

	@JsonIgnore
	public void setDisplayStyle(
			UnsafeSupplier<String, Throwable>
				displayStyleUnsafeSupplier) {

			try {
				displayStyle =
					displayStyleUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String displayStyle;
	public Grid getGrid() {
			return grid;
	}

	public void setGrid(
			Grid grid) {

			this.grid = grid;
	}

	@JsonIgnore
	public void setGrid(
			UnsafeSupplier<Grid, Throwable>
				gridUnsafeSupplier) {

			try {
				grid =
					gridUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Grid grid;
	public Boolean getHasFormRules() {
			return hasFormRules;
	}

	public void setHasFormRules(
			Boolean hasFormRules) {

			this.hasFormRules = hasFormRules;
	}

	@JsonIgnore
	public void setHasFormRules(
			UnsafeSupplier<Boolean, Throwable>
				hasFormRulesUnsafeSupplier) {

			try {
				hasFormRules =
					hasFormRulesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean hasFormRules;
	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;
	public Boolean getImmutable() {
			return immutable;
	}

	public void setImmutable(
			Boolean immutable) {

			this.immutable = immutable;
	}

	@JsonIgnore
	public void setImmutable(
			UnsafeSupplier<Boolean, Throwable>
				immutableUnsafeSupplier) {

			try {
				immutable =
					immutableUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean immutable;
	public Boolean getInline() {
			return inline;
	}

	public void setInline(
			Boolean inline) {

			this.inline = inline;
	}

	@JsonIgnore
	public void setInline(
			UnsafeSupplier<Boolean, Throwable>
				inlineUnsafeSupplier) {

			try {
				inline =
					inlineUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean inline;
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
	public Options getOptions() {
			return options;
	}

	public void setOptions(
			Options options) {

			this.options = options;
	}

	@JsonIgnore
	public void setOptions(
			UnsafeSupplier<Options, Throwable>
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
	protected Options options;
	public String getPlaceholder() {
			return placeholder;
	}

	public void setPlaceholder(
			String placeholder) {

			this.placeholder = placeholder;
	}

	@JsonIgnore
	public void setPlaceholder(
			UnsafeSupplier<String, Throwable>
				placeholderUnsafeSupplier) {

			try {
				placeholder =
					placeholderUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String placeholder;
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
	public Boolean getReadOnly() {
			return readOnly;
	}

	public void setReadOnly(
			Boolean readOnly) {

			this.readOnly = readOnly;
	}

	@JsonIgnore
	public void setReadOnly(
			UnsafeSupplier<Boolean, Throwable>
				readOnlyUnsafeSupplier) {

			try {
				readOnly =
					readOnlyUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean readOnly;
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
	public Boolean getShowAsSwitcher() {
			return showAsSwitcher;
	}

	public void setShowAsSwitcher(
			Boolean showAsSwitcher) {

			this.showAsSwitcher = showAsSwitcher;
	}

	@JsonIgnore
	public void setShowAsSwitcher(
			UnsafeSupplier<Boolean, Throwable>
				showAsSwitcherUnsafeSupplier) {

			try {
				showAsSwitcher =
					showAsSwitcherUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean showAsSwitcher;
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
	public String getStyle() {
			return style;
	}

	public void setStyle(
			String style) {

			this.style = style;
	}

	@JsonIgnore
	public void setStyle(
			UnsafeSupplier<String, Throwable>
				styleUnsafeSupplier) {

			try {
				style =
					styleUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String style;
	public String getText() {
			return text;
	}

	public void setText(
			String text) {

			this.text = text;
	}

	@JsonIgnore
	public void setText(
			UnsafeSupplier<String, Throwable>
				textUnsafeSupplier) {

			try {
				text =
					textUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String text;
	public String getTooltip() {
			return tooltip;
	}

	public void setTooltip(
			String tooltip) {

			this.tooltip = tooltip;
	}

	@JsonIgnore
	public void setTooltip(
			UnsafeSupplier<String, Throwable>
				tooltipUnsafeSupplier) {

			try {
				tooltip =
					tooltipUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String tooltip;
	public Validation getValidation() {
			return validation;
	}

	public void setValidation(
			Validation validation) {

			this.validation = validation;
	}

	@JsonIgnore
	public void setValidation(
			UnsafeSupplier<Validation, Throwable>
				validationUnsafeSupplier) {

			try {
				validation =
					validationUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Validation validation;

}