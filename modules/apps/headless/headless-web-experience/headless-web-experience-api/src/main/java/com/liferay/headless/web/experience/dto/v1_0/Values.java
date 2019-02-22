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
public interface Values {

	public String getDataType();

	public Long getId();

	public String getInputControl();

	public String getLabel();

	public String getName();

	public Value getValue();

	public void setDataType(String dataType);

	public void setDataType(
		UnsafeSupplier<String, Throwable> dataTypeUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setInputControl(String inputControl);

	public void setInputControl(
		UnsafeSupplier<String, Throwable> inputControlUnsafeSupplier);

	public void setLabel(String label);

	public void setLabel(UnsafeSupplier<String, Throwable> labelUnsafeSupplier);

	public void setName(String name);

	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier);

	public void setValue(UnsafeSupplier<Value, Throwable> valueUnsafeSupplier);

	public void setValue(Value value);

}