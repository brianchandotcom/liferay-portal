/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

import java.util.List;

/**
 * @author Sandro Chinea
 */
public class EmailInfoFieldType implements InfoFieldType {

	public static final EmailInfoFieldType INSTANCE = new EmailInfoFieldType();

	public static final Attribute<EmailInfoFieldType, List<String>>
		PREFERRED_DOMAINS = new Attribute<>();

	@Override
	public String getName() {
		return "email";
	}

	private EmailInfoFieldType() {
	}

}