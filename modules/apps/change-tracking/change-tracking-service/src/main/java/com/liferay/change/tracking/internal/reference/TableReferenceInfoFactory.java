/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.reference;

import com.liferay.change.tracking.internal.reference.builder.ChildTableReferenceInfoBuilderImpl;
import com.liferay.change.tracking.internal.reference.builder.ParentTableReferenceInfoBuilderImpl;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

/**
 * @author Preston Crary
 */
public class TableReferenceInfoFactory {

	public static <T extends Table<T>> TableReferenceInfo<T> create(
		long classNameId, Column<T, Long> primaryKeyColumn,
		TableReferenceDefinition<T> tableReferenceDefinition) {

		ParentTableReferenceInfoBuilderImpl<T>
			parentTableReferenceInfoBuilderImpl =
				new ParentTableReferenceInfoBuilderImpl<>(
					tableReferenceDefinition, primaryKeyColumn);

		tableReferenceDefinition.defineParentTableReferences(
			parentTableReferenceInfoBuilderImpl);

		ChildTableReferenceInfoBuilderImpl<T>
			childTableReferenceInfoBuilderImpl =
				new ChildTableReferenceInfoBuilderImpl<>(
					tableReferenceDefinition, primaryKeyColumn);

		tableReferenceDefinition.defineChildTableReferences(
			childTableReferenceInfoBuilderImpl);

		TableReferenceAppenderRegistry.appendTableReferences(
			tableReferenceDefinition, primaryKeyColumn,
			parentTableReferenceInfoBuilderImpl,
			childTableReferenceInfoBuilderImpl);

		return new TableReferenceInfo<>(
			childTableReferenceInfoBuilderImpl.getTableJoinHoldersMap(),
			classNameId,
			parentTableReferenceInfoBuilderImpl.getTableJoinHoldersMap(),
			tableReferenceDefinition);
	}

}