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

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Fields {

	public Boolean getAutocomplete();

	public String getDataSourceType();

	public String getDataType();

	public String getDisplayStyle();

	public Grid getGrid();

	public Boolean getHasFormRules();

	public Long getId();

	public Boolean getImmutable();

	public Boolean getInline();

	public String getInputControl();

	public String getLabel();

	public Boolean getLocalizable();

	public Boolean getMultiple();

	public String getName();

	public Options getOptions();

	public String getPlaceholder();

	public String getPredefinedValue();

	public Boolean getReadOnly();

	public Boolean getRepeatable();

	public Boolean getRequired();

	public Boolean getShowAsSwitcher();

	public Boolean getShowLabel();

	public String getStyle();

	public String getText();

	public String getTooltip();

	public Validation getValidation();

	public void setAutocomplete(Boolean autocomplete);

	public void setAutocomplete(
		UnsafeSupplier<Boolean, Throwable> autocompleteUnsafeSupplier);

	public void setDataSourceType(String dataSourceType);

	public void setDataSourceType(
		UnsafeSupplier<String, Throwable> dataSourceTypeUnsafeSupplier);

	public void setDataType(String dataType);

	public void setDataType(
		UnsafeSupplier<String, Throwable> dataTypeUnsafeSupplier);

	public void setDisplayStyle(String displayStyle);

	public void setDisplayStyle(
		UnsafeSupplier<String, Throwable> displayStyleUnsafeSupplier);

	public void setGrid(Grid grid);

	public void setGrid(UnsafeSupplier<Grid, Throwable> gridUnsafeSupplier);

	public void setHasFormRules(Boolean hasFormRules);

	public void setHasFormRules(
		UnsafeSupplier<Boolean, Throwable> hasFormRulesUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setImmutable(Boolean immutable);

	public void setImmutable(
		UnsafeSupplier<Boolean, Throwable> immutableUnsafeSupplier);

	public void setInline(Boolean inline);

	public void setInline(
		UnsafeSupplier<Boolean, Throwable> inlineUnsafeSupplier);

	public void setInputControl(String inputControl);

	public void setInputControl(
		UnsafeSupplier<String, Throwable> inputControlUnsafeSupplier);

	public void setLabel(String label);

	public void setLabel(UnsafeSupplier<String, Throwable> labelUnsafeSupplier);

	public void setLocalizable(Boolean localizable);

	public void setLocalizable(
		UnsafeSupplier<Boolean, Throwable> localizableUnsafeSupplier);

	public void setMultiple(Boolean multiple);

	public void setMultiple(
		UnsafeSupplier<Boolean, Throwable> multipleUnsafeSupplier);

	public void setName(String name);

	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier);

	public void setOptions(Options options);

	public void setOptions(
		UnsafeSupplier<Options, Throwable> optionsUnsafeSupplier);

	public void setPlaceholder(String placeholder);

	public void setPlaceholder(
		UnsafeSupplier<String, Throwable> placeholderUnsafeSupplier);

	public void setPredefinedValue(String predefinedValue);

	public void setPredefinedValue(
		UnsafeSupplier<String, Throwable> predefinedValueUnsafeSupplier);

	public void setReadOnly(Boolean readOnly);

	public void setReadOnly(
		UnsafeSupplier<Boolean, Throwable> readOnlyUnsafeSupplier);

	public void setRepeatable(Boolean repeatable);

	public void setRepeatable(
		UnsafeSupplier<Boolean, Throwable> repeatableUnsafeSupplier);

	public void setRequired(Boolean required);

	public void setRequired(
		UnsafeSupplier<Boolean, Throwable> requiredUnsafeSupplier);

	public void setShowAsSwitcher(Boolean showAsSwitcher);

	public void setShowAsSwitcher(
		UnsafeSupplier<Boolean, Throwable> showAsSwitcherUnsafeSupplier);

	public void setShowLabel(Boolean showLabel);

	public void setShowLabel(
		UnsafeSupplier<Boolean, Throwable> showLabelUnsafeSupplier);

	public void setStyle(String style);

	public void setStyle(UnsafeSupplier<String, Throwable> styleUnsafeSupplier);

	public void setText(String text);

	public void setText(UnsafeSupplier<String, Throwable> textUnsafeSupplier);

	public void setTooltip(String tooltip);

	public void setTooltip(
		UnsafeSupplier<String, Throwable> tooltipUnsafeSupplier);

	public void setValidation(
		UnsafeSupplier<Validation, Throwable> validationUnsafeSupplier);

	public void setValidation(Validation validation);

}