/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author Alicia Garcia
 */
public class DateInfoFieldType implements InfoFieldType {

	public static final DateInfoFieldType INSTANCE = new DateInfoFieldType();

	@Override
	public String getName() {
		return "date";
	}

	private DateInfoFieldType() {
	}

}