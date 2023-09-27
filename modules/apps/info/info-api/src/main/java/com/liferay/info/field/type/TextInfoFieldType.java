/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author Jorge Ferrer
 */
public class TextInfoFieldType implements InfoFieldType {

	public static final TextInfoFieldType INSTANCE = new TextInfoFieldType();

	public static final Attribute<TextInfoFieldType, Long> MAX_LENGTH =
		new Attribute<>();

	public static final Attribute<TextInfoFieldType, Boolean> MULTILINE =
		new Attribute<>();

	@Override
	public String getName() {
		return "text";
	}

	private TextInfoFieldType() {
	}

}