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

package com.liferay.portal.odata.entity;

/**
 * Models a collection entity field.
 *
 * @author Ruben Pulido
 * @review
 */
public class CollectionEntityField extends EntityField {

	/**
	 * Creates a new {@code EntityField} of type COLLECTION.
	 *
	 * @param  entityField the entity field
	 * @review
	 */
	public CollectionEntityField(EntityField entityField) {
		super(
			entityField.getName(), Type.COLLECTION,
			locale -> entityField.getName(), locale -> entityField.getName(),
			String::valueOf);

		_entityField = entityField;
	}

	/**
	 * Gets the {@code EntityField}.
	 *
	 * @return the entity field
	 * @review
	 */
	public EntityField getEntityField() {
		return _entityField;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	private final EntityField _entityField;

}