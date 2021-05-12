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

package com.liferay.change.tracking.mapping;

import java.util.List;
import java.util.Map;

/**
 * @author Cheryl Tang
 */
public final class CTMappingTableInfo {

	public CTMappingTableInfo(
		String tableName, String leftColumnName, String rightColumnName,
		List<Map.Entry<Long, Long>> addedMappings,
		List<Map.Entry<Long, Long>> removedMappings) {

		_tableName = tableName;
		_leftColumnName = leftColumnName;
		_rightColumnName = rightColumnName;
		_addedMappings = addedMappings;
		_removedMappings = removedMappings;
	}

	public List<Map.Entry<Long, Long>> getAddedMappings() {
		return _addedMappings;
	}

	public String getLeftColumnName() {
		return _leftColumnName;
	}

	public List<Map.Entry<Long, Long>> getRemovedMappings() {
		return _removedMappings;
	}

	public String getRightColumnName() {
		return _rightColumnName;
	}

	public String getTableName() {
		return _tableName;
	}

	private final List<Map.Entry<Long, Long>> _addedMappings;
	private final String _leftColumnName;
	private final List<Map.Entry<Long, Long>> _removedMappings;
	private final String _rightColumnName;
	private final String _tableName;

}