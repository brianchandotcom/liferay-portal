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

package com.liferay.change.tracking.internal.mapping;

import com.liferay.change.tracking.mapping.CTMappingTableInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Cheryl Tang
 */
public final class CTMappingTableInfoImpl implements CTMappingTableInfo {

	public CTMappingTableInfoImpl(
		String tableName, String leftColumnName, Class<?> leftModelClass,
		String rightColumnName, Class<?> rightModelClass,
		List<Map.Entry<Long, Long>> addedMappings,
		List<Map.Entry<Long, Long>> removedMappings) {

		_tableName = tableName;
		_leftColumnName = leftColumnName;
		_leftModelClass = leftModelClass;
		_rightColumnName = rightColumnName;
		_rightModelClass = rightModelClass;
		_addedMappings = addedMappings;
		_removedMappings = removedMappings;
	}

	@Override
	public List<Map.Entry<Long, Long>> getAddedMappings() {
		return _addedMappings;
	}

	@Override
	public String getLeftColumnName() {
		return _leftColumnName;
	}

	@Override
	public Class<?> getLeftModelClass() {
		return _leftModelClass;
	}

	@Override
	public List<Map.Entry<Long, Long>> getRemovedMappings() {
		return _removedMappings;
	}

	@Override
	public String getRightColumnName() {
		return _rightColumnName;
	}

	@Override
	public Class<?> getRightModelClass() {
		return _rightModelClass;
	}

	@Override
	public String getTableName() {
		return _tableName;
	}

	private final List<Map.Entry<Long, Long>> _addedMappings;
	private final String _leftColumnName;
	private final Class<?> _leftModelClass;
	private final List<Map.Entry<Long, Long>> _removedMappings;
	private final String _rightColumnName;
	private final Class<?> _rightModelClass;
	private final String _tableName;

}