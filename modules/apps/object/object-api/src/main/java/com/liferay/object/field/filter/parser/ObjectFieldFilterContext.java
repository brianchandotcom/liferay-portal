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

package com.liferay.object.field.filter.parser;

import com.liferay.object.model.ObjectViewFilterColumn;

import java.util.Locale;

/**
 * @author Feliphe Marinho
 */
public class ObjectFieldFilterContext {

	public ObjectFieldFilterContext(
		Locale locale, long objectDefinitionId,
		ObjectViewFilterColumn objectViewFilterColumn) {

		_locale = locale;
		_objectDefinitionId = objectDefinitionId;
		_objectViewFilterColumn = objectViewFilterColumn;
	}

	public Locale getLocale() {
		return _locale;
	}

	public long getObjectDefinitionId() {
		return _objectDefinitionId;
	}

	public ObjectViewFilterColumn getObjectViewFilterColumn() {
		return _objectViewFilterColumn;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setObjectDefinitionId(long objectDefinitionId) {
		_objectDefinitionId = objectDefinitionId;
	}

	public void setObjectViewFilterColumn(
		ObjectViewFilterColumn objectViewFilterColumn) {

		_objectViewFilterColumn = objectViewFilterColumn;
	}

	private Locale _locale;
	private long _objectDefinitionId;
	private ObjectViewFilterColumn _objectViewFilterColumn;

}