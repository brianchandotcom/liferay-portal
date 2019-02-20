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

	public void setDataType(
			String dataType);

	public void setDataType(
			UnsafeSupplier<String, Throwable>
				dataTypeUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public String getInputControl();

	public void setInputControl(
			String inputControl);

	public void setInputControl(
			UnsafeSupplier<String, Throwable>
				inputControlUnsafeSupplier);
	public String getLabel();

	public void setLabel(
			String label);

	public void setLabel(
			UnsafeSupplier<String, Throwable>
				labelUnsafeSupplier);
	public String getName();

	public void setName(
			String name);

	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier);
	public Value getValue();

	public void setValue(
			Value value);

	public void setValue(
			UnsafeSupplier<Value, Throwable>
				valueUnsafeSupplier);

}