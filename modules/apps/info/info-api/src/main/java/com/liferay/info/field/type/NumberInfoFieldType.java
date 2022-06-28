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

package com.liferay.info.field.type;

import java.math.BigDecimal;

/**
 * @author Alejandro Tardín
 */
public class NumberInfoFieldType implements InfoFieldType {

	public static final Attribute<NumberInfoFieldType, Boolean> DECIMAL =
		new Attribute<>();

	public static final Attribute<NumberInfoFieldType, Integer>
		DECIMAL_PART_MAX_LENGTH = new Attribute<>();

	public static final NumberInfoFieldType INSTANCE =
		new NumberInfoFieldType();

	public static final Attribute<NumberInfoFieldType, BigDecimal> MAX_VALUE =
		new Attribute<>();

	public static final Attribute<NumberInfoFieldType, BigDecimal> MIN_VALUE =
		new Attribute<>();

	@Override
	public String getName() {
		return "number";
	}

	private NumberInfoFieldType() {
	}

}