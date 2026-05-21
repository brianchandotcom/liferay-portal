/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author Víctor Galán
 */
public class PhoneInfoFieldType implements InfoFieldType {

	public static final PhoneInfoFieldType INSTANCE = new PhoneInfoFieldType();

	public static final Attribute<PhoneInfoFieldType, String> PREFIX =
		new Attribute<>();

	public static final Attribute<PhoneInfoFieldType, String> PREFIX_TYPE =
		new Attribute<>();

	@Override
	public String getName() {
		return "phone-number";
	}

	private PhoneInfoFieldType() {
	}

}