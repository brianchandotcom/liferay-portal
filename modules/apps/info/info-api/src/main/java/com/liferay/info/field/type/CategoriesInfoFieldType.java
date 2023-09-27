/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Jorge Ferrer
 */
public class CategoriesInfoFieldType implements InfoFieldType {

	public static final Attribute<CategoriesInfoFieldType, KeyValuePair>
		DEPENDENCY = new Attribute<>();

	public static final Attribute<CategoriesInfoFieldType, String>
		INFO_ITEM_SELECTOR_URL = new Attribute<>();

	public static final CategoriesInfoFieldType INSTANCE =
		new CategoriesInfoFieldType();

	@Override
	public String getName() {
		return "categories";
	}

	private CategoriesInfoFieldType() {
	}

}