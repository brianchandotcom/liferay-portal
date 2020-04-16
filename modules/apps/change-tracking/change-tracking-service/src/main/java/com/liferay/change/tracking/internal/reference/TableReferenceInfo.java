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

package com.liferay.change.tracking.internal.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.petra.sql.dsl.Table;

import java.util.List;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class TableReferenceInfo<T extends Table<T>> {

	public TableReferenceInfo(
		TableReferenceDefinition<T> tableReferenceDefinition,
		Map<Table<?>, List<TableJoinHolder>> parentTableJoinHoldersMap,
		Map<Table<?>, List<TableJoinHolder>> childTableJoinHoldersMap) {

		_tableReferenceDefinition = tableReferenceDefinition;
		_parentTableJoinHoldersMap = parentTableJoinHoldersMap;
		_childTableJoinHoldersMap = childTableJoinHoldersMap;
	}

	public Map<Table<?>, List<TableJoinHolder>> getChildTableJoinHoldersMap() {
		return _childTableJoinHoldersMap;
	}

	public Map<Table<?>, List<TableJoinHolder>> getParentTableJoinHoldersMap() {
		return _parentTableJoinHoldersMap;
	}

	public TableReferenceDefinition<T> getTableReferenceDefinition() {
		return _tableReferenceDefinition;
	}

	private final Map<Table<?>, List<TableJoinHolder>>
		_childTableJoinHoldersMap;
	private final Map<Table<?>, List<TableJoinHolder>>
		_parentTableJoinHoldersMap;
	private final TableReferenceDefinition<T> _tableReferenceDefinition;

}