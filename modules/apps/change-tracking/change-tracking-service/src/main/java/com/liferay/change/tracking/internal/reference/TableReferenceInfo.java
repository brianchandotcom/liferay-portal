/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.petra.sql.dsl.Table;

import java.util.List;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class TableReferenceInfo<T extends Table<T>> {

	public TableReferenceInfo(
		Map<Table<?>, List<TableJoinHolder>> childTableJoinHoldersMap,
		long classNameId,
		Map<Table<?>, List<TableJoinHolder>> parentTableJoinHoldersMap,
		TableReferenceDefinition<T> tableReferenceDefinition) {

		_childTableJoinHoldersMap = childTableJoinHoldersMap;
		_classNameId = classNameId;
		_parentTableJoinHoldersMap = parentTableJoinHoldersMap;
		_tableReferenceDefinition = tableReferenceDefinition;
	}

	public Map<Table<?>, List<TableJoinHolder>> getChildTableJoinHoldersMap() {
		return _childTableJoinHoldersMap;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public Map<Table<?>, List<TableJoinHolder>> getParentTableJoinHoldersMap() {
		return _parentTableJoinHoldersMap;
	}

	public TableReferenceDefinition<T> getTableReferenceDefinition() {
		return _tableReferenceDefinition;
	}

	private final Map<Table<?>, List<TableJoinHolder>>
		_childTableJoinHoldersMap;
	private final long _classNameId;
	private final Map<Table<?>, List<TableJoinHolder>>
		_parentTableJoinHoldersMap;
	private final TableReferenceDefinition<T> _tableReferenceDefinition;

}