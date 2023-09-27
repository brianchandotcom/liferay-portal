/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author Jorge Ferrer
 */
public class TagsInfoFieldType implements InfoFieldType {

	public static final TagsInfoFieldType INSTANCE = new TagsInfoFieldType();

	@Override
	public String getName() {
		return "tags";
	}

	private TagsInfoFieldType() {
	}

}