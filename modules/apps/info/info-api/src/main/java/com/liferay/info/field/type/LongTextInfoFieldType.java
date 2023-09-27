/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author Víctor Galán
 */
public class LongTextInfoFieldType implements InfoFieldType {

	public static final LongTextInfoFieldType INSTANCE =
		new LongTextInfoFieldType();

	public static final Attribute<LongTextInfoFieldType, Long> MAX_LENGTH =
		new Attribute<>();

	@Override
	public String getName() {
		return "long-text";
	}

	private LongTextInfoFieldType() {
	}

}