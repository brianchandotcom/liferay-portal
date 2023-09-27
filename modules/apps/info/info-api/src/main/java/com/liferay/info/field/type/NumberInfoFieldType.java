/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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