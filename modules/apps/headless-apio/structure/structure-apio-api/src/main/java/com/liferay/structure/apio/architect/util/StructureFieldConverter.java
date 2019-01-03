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

package com.liferay.structure.apio.architect.util;

/**
 * Converts values used in {@code DDMFormField} to values to be exposed through
 * the web API.
 *
 * @author Rubén Pulido
 * @review
 */
public interface StructureFieldConverter {

	/**
	 * Gets the field data type.
	 *
	 * @param  dataType the {@code DDMFormField#dataType}
	 * @return the field data type
	 */
	public String getFieldDataType(String dataType);

	/**
	 * Gets the field data type.
	 *
	 * @param  dataType the {@code DDMFormField#dataType}
	 * @param  type the {@code DDMFormField#type}
	 * @return the field data type
	 * @review
	 */
	public default String getFieldDataType(String dataType, String type) {
		return getFieldDataType(dataType);
	}

	/**
	 * Gets the field input control.
	 *
	 * @param  type the {@code DDMFormField#type()}
	 * @return the field input control
	 */
	public String getFieldInputControl(String type);

}