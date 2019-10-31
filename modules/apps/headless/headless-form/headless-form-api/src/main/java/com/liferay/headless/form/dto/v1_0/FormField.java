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

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("FormField")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FormField")
public class FormField {

	@Schema
	public Boolean getAutocomplete() {
		return autocomplete;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean autocomplete;

	@Schema
	public String getDataSourceType() {
		return dataSourceType;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String dataSourceType;

	@Schema
	public String getDataType() {
		return dataType;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String dataType;

	@Schema
	public String getDisplayStyle() {
		return displayStyle;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String displayStyle;

	@Schema
	@Valid
	public FormFieldOption[] getFormFieldOptions() {
		return formFieldOptions;
	}

	public void setFormFieldOptions(FormFieldOption[] formFieldOptions) {
		this.formFieldOptions = formFieldOptions;
	}

	@JsonIgnore
	public void setFormFieldOptions(
		UnsafeSupplier<FormFieldOption[], Exception>
			formFieldOptionsUnsafeSupplier) {

		try {
			formFieldOptions = formFieldOptionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FormFieldOption[] formFieldOptions;

	@Schema
	@Valid
	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	@JsonIgnore
	public void setGrid(UnsafeSupplier<Grid, Exception> gridUnsafeSupplier) {
		try {
			grid = gridUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Grid grid;

	@Schema
	public Boolean getHasFormRules() {
		return hasFormRules;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean hasFormRules;

	@Schema
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	@Schema
	public Boolean getImmutable() {
		return immutable;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean immutable;

	@Schema
	public Boolean getInline() {
		return inline;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean inline;

	@Schema
	public String getInputControl() {
		return inputControl;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String inputControl;

	@Schema
	public String getLabel() {
		return label;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String label;

	@Schema
	public Boolean getLocalizable() {
		return localizable;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean localizable;

	@Schema
	public Boolean getMultiple() {
		return multiple;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean multiple;

	@Schema
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	@Schema
	public String getPlaceholder() {
		return placeholder;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String placeholder;

	@Schema
	public String getPredefinedValue() {
		return predefinedValue;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String predefinedValue;

	@Schema
	public Boolean getReadOnly() {
		return readOnly;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean readOnly;

	@Schema
	public Boolean getRepeatable() {
		return repeatable;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean repeatable;

	@Schema
	public Boolean getRequired() {
		return required;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean required;

	@Schema
	public Boolean getShowAsSwitcher() {
		return showAsSwitcher;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean showAsSwitcher;

	@Schema
	public Boolean getShowLabel() {
		return showLabel;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean showLabel;

	@Schema
	public String getStyle() {
		return style;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String style;

	@Schema
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@JsonIgnore
	public void setText(UnsafeSupplier<String, Exception> textUnsafeSupplier) {
		try {
			text = textUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String text;

	@Schema
	public String getTooltip() {
		return tooltip;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String tooltip;

	@Schema(description = "https://www.schema.org/FormFieldValidation")
	@Valid
	public Validation getValidation() {
		return validation;
	}

	public void setValidation(Validation validation) {
		this.validation = validation;
	}

	@JsonIgnore
	public void setValidation(
		UnsafeSupplier<Validation, Exception> validationUnsafeSupplier) {

		try {
			validation = validationUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Validation validation;

	@Schema(
		defaultValue = "com.liferay.headless.form.dto.v1_0.FormField",
		name = "x-classname"
	)
	public String xClassName;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormField)) {
			return false;
		}

		FormField formField = (FormField)object;

		return Objects.equals(toString(), formField.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (autocomplete != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"autocomplete\": ");

			sb.append(autocomplete);
		}

		if (dataSourceType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataSourceType\": ");

			sb.append("\"");

			sb.append(_escape(dataSourceType));

			sb.append("\"");
		}

		if (dataType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataType\": ");

			sb.append("\"");

			sb.append(_escape(dataType));

			sb.append("\"");
		}

		if (displayStyle != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayStyle\": ");

			sb.append("\"");

			sb.append(_escape(displayStyle));

			sb.append("\"");
		}

		if (formFieldOptions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formFieldOptions\": ");

			sb.append("[");

			for (int i = 0; i < formFieldOptions.length; i++) {
				sb.append(String.valueOf(formFieldOptions[i]));

				if ((i + 1) < formFieldOptions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (grid != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"grid\": ");

			sb.append(String.valueOf(grid));
		}

		if (hasFormRules != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hasFormRules\": ");

			sb.append(hasFormRules);
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (immutable != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"immutable\": ");

			sb.append(immutable);
		}

		if (inline != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inline\": ");

			sb.append(inline);
		}

		if (inputControl != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inputControl\": ");

			sb.append("\"");

			sb.append(_escape(inputControl));

			sb.append("\"");
		}

		if (label != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(label));

			sb.append("\"");
		}

		if (localizable != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"localizable\": ");

			sb.append(localizable);
		}

		if (multiple != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multiple\": ");

			sb.append(multiple);
		}

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		if (placeholder != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"placeholder\": ");

			sb.append("\"");

			sb.append(_escape(placeholder));

			sb.append("\"");
		}

		if (predefinedValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"predefinedValue\": ");

			sb.append("\"");

			sb.append(_escape(predefinedValue));

			sb.append("\"");
		}

		if (readOnly != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"readOnly\": ");

			sb.append(readOnly);
		}

		if (repeatable != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"repeatable\": ");

			sb.append(repeatable);
		}

		if (required != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(required);
		}

		if (showAsSwitcher != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showAsSwitcher\": ");

			sb.append(showAsSwitcher);
		}

		if (showLabel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showLabel\": ");

			sb.append(showLabel);
		}

		if (style != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"style\": ");

			sb.append("\"");

			sb.append(_escape(style));

			sb.append("\"");
		}

		if (text != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			sb.append("\"");

			sb.append(_escape(text));

			sb.append("\"");
		}

		if (tooltip != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tooltip\": ");

			sb.append("\"");

			sb.append(_escape(tooltip));

			sb.append("\"");
		}

		if (validation != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"validation\": ");

			sb.append(String.valueOf(validation));
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}