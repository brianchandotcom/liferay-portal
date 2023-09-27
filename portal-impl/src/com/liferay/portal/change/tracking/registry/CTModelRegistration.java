/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.change.tracking.registry;

/**
 * @author Preston Crary
 */
public class CTModelRegistration {

	public CTModelRegistration(
		Class<?> modelClass, String tableName, String primaryColumnName) {

		_modelClass = modelClass;
		_tableName = tableName;
		_primaryColumnName = primaryColumnName;
	}

	public Class<?> getModelClass() {
		return _modelClass;
	}

	public String getPrimaryColumnName() {
		return _primaryColumnName;
	}

	public String getTableName() {
		return _tableName;
	}

	private final Class<?> _modelClass;
	private final String _primaryColumnName;
	private final String _tableName;

}