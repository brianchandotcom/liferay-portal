/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

import java.util.Collection;

/**
 * @author Alicia Garcia
 */
public class SelectInfoFieldType implements InfoFieldType {

	public static final Attribute<SelectInfoFieldType, Boolean> INLINE =
		new Attribute<>();

	public static final SelectInfoFieldType INSTANCE =
		new SelectInfoFieldType();

	public static final Attribute
		<SelectInfoFieldType, Collection<OptionInfoFieldType>> OPTIONS =
			new Attribute<>();

	@Override
	public String getName() {
		return "select";
	}

	private SelectInfoFieldType() {
	}

}