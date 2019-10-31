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

package com.liferay.portal.change.tracking.registry;

import java.util.Map;

/**
 * @author Preston Crary
 */
public class CTModelRegistration {

	public CTModelRegistration(
		Class<?> modelClass, String tableName, String primaryColumnName,
		Map<String, Integer> tableColumnsMap) {

		_modelClass = modelClass;
		_tableName = tableName;
		_primaryColumnName = primaryColumnName;
		_tableColumnsMap = tableColumnsMap;
	}

	public Class<?> getModelClass() {
		return _modelClass;
	}

	public String getPrimaryColumnName() {
		return _primaryColumnName;
	}

	public Map<String, Integer> getTableColumnsMap() {
		return _tableColumnsMap;
	}

	public String getTableName() {
		return _tableName;
	}

	private final Class<?> _modelClass;
	private final String _primaryColumnName;
	private final Map<String, Integer> _tableColumnsMap;
	private final String _tableName;

}