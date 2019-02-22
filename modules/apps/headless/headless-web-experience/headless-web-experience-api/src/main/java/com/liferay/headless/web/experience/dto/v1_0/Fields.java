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

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Fields {

	public String getDataType();

	public String getInputControl();

	public String getLabel();

	public Boolean getLocalizable();

	public Boolean getMultiple();

	public String getName();

	public Options[] getOptions();

	public String getPredefinedValue();

	public Boolean getRepeatable();

	public Boolean getRequired();

	public Boolean getShowLabel();

	public void setDataType(String dataType);

	public void setDataType(
		UnsafeSupplier<String, Throwable> dataTypeUnsafeSupplier);

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

	public void setOptions(Options[] options);

	public void setOptions(
		UnsafeSupplier<Options[], Throwable> optionsUnsafeSupplier);

	public void setPredefinedValue(String predefinedValue);

	public void setPredefinedValue(
		UnsafeSupplier<String, Throwable> predefinedValueUnsafeSupplier);

	public void setRepeatable(Boolean repeatable);

	public void setRepeatable(
		UnsafeSupplier<Boolean, Throwable> repeatableUnsafeSupplier);

	public void setRequired(Boolean required);

	public void setRequired(
		UnsafeSupplier<Boolean, Throwable> requiredUnsafeSupplier);

	public void setShowLabel(Boolean showLabel);

	public void setShowLabel(
		UnsafeSupplier<Boolean, Throwable> showLabelUnsafeSupplier);

}