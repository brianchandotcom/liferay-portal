/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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