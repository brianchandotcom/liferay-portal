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

package com.liferay.user.associated.data.web.internal.display;

import java.io.Serializable;

/**
 * @author William Newbury
 */
public class UADEntity<T> {

	public UADEntity(T entity, Serializable primaryKey) {
		_entity = entity;
		_primaryKey = primaryKey;
	}

	public T getEntity() {
		return _entity;
	}

	public Serializable getPrimaryKey() {
		return _primaryKey;
	}

	private final T _entity;
	private final Serializable _primaryKey;

}