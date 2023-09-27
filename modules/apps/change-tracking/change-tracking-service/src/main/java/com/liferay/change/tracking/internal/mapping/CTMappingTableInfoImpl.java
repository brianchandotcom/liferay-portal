/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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