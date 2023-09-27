/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.reference.builder;

import com.liferay.change.tracking.internal.reference.TableJoinHolder;
import com.liferay.change.tracking.internal.reference.TableJoinHolderFactory;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Preston Crary
 */
public abstract class BaseTableReferenceInfoBuilder<T extends Table<T>> {

	public BaseTableReferenceInfoBuilder(
		TableReferenceDefinition<T> tableReferenceDefinition,
		Column<T, Long> primaryKeyColumn, boolean parent) {

		_tableReferenceDefinition = tableReferenceDefinition;
		_primaryKeyColumn = primaryKeyColumn;
		_parent = parent;
	}

	public Map<Table<?>, List<TableJoinHolder>> getTableJoinHoldersMap() {
		return _tableJoinHoldersMap;
	}

	protected void applyReferenceInnerJoin(
		Function<FromStep, JoinStep> joinFunction) {

		TableJoinHolder tableJoinHolder = TableJoinHolderFactory.create(
			joinFunction, _parent, _primaryKeyColumn,
			_tableReferenceDefinition);

		Column<?, Long> parentPKColumn = tableJoinHolder.getParentPKColumn();

		List<TableJoinHolder> tableJoinHolders =
			_tableJoinHoldersMap.computeIfAbsent(
				parentPKColumn.getTable(), table -> new ArrayList<>());

		tableJoinHolders.add(tableJoinHolder);
	}

	private final boolean _parent;
	private final Column<T, Long> _primaryKeyColumn;
	private final Map<Table<?>, List<TableJoinHolder>> _tableJoinHoldersMap =
		new HashMap<>();
	private final TableReferenceDefinition<T> _tableReferenceDefinition;

}