/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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