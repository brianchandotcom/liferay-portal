/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author Víctor Galán
 */
public class PhoneNumberInfoFieldType implements InfoFieldType {

	public static final Attribute<PhoneNumberInfoFieldType, String> COUNTRY =
		new Attribute<>();

	public static final Attribute<PhoneNumberInfoFieldType, String>
		COUNTRY_SOURCE = new Attribute<>();

	public static final PhoneNumberInfoFieldType INSTANCE =
		new PhoneNumberInfoFieldType();

	@Override
	public String getName() {
		return "phone-number";
	}

	private PhoneNumberInfoFieldType() {
	}

}