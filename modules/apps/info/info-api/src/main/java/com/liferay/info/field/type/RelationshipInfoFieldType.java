/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author Eudaldo Alonso
 */
public class RelationshipInfoFieldType implements InfoFieldType {

	public static final RelationshipInfoFieldType INSTANCE =
		new RelationshipInfoFieldType();

	public static final Attribute<RelationshipInfoFieldType, String>
		LABEL_FIELD_NAME = new Attribute<>();

	public static final Attribute<RelationshipInfoFieldType, Boolean> MULTIPLE =
		new Attribute<>();

	public static final Attribute<RelationshipInfoFieldType, String> URL =
		new Attribute<>();

	public static final Attribute<RelationshipInfoFieldType, String>
		VALUE_FIELD_NAME = new Attribute<>();

	@Override
	public String getName() {
		return "relationship";
	}

	private RelationshipInfoFieldType() {
	}

}