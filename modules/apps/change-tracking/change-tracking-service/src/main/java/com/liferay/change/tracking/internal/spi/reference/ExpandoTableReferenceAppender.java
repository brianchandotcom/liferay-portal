/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.internal.reference.TableReferenceAppender;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.expando.kernel.model.ExpandoColumnTable;
import com.liferay.expando.kernel.model.ExpandoRowTable;
import com.liferay.expando.kernel.model.ExpandoTableTable;
import com.liferay.expando.kernel.model.ExpandoValueTable;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassNameTable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class ExpandoTableReferenceAppender implements TableReferenceAppender {

	@Override
	public <T extends Table<T>> void appendChildTableReferences(
		Class<? extends BaseModel<?>> modelClass,
		Column<T, Long> primaryKeyColumn,
		ChildTableReferenceInfoBuilder<T> childTableReferenceInfoBuilder) {

		T table = primaryKeyColumn.getTable();

		if (_excludeTables.contains(table)) {
			return;
		}

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				ExpandoRowTable.INSTANCE
			).innerJoinON(
				table,
				primaryKeyColumn.eq(
					ExpandoRowTable.INSTANCE.classPK
				).and(
					() -> {
						Column<T, Long> companyIdColumn = table.getColumn(
							"companyId", Long.class);

						if (companyIdColumn != null) {
							return companyIdColumn.eq(
								ExpandoRowTable.INSTANCE.companyId);
						}

						return null;
					}
				)
			).innerJoinON(
				ExpandoTableTable.INSTANCE,
				ExpandoTableTable.INSTANCE.tableId.eq(
					ExpandoRowTable.INSTANCE.tableId)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					ExpandoTableTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(modelClass.getName())
				)
			));
	}

	@Override
	public <T extends Table<T>> void appendParentTableReferences(
		Class<? extends BaseModel<?>> modelClass,
		Column<T, Long> primaryKeyColumn,
		ParentTableReferenceInfoBuilder<T> parentTableReferenceInfoBuilder) {
	}

	private static final Set<Table<?>> _excludeTables = new HashSet<>(
		Arrays.asList(
			ExpandoRowTable.INSTANCE, ExpandoTableTable.INSTANCE,
			ExpandoColumnTable.INSTANCE, ExpandoValueTable.INSTANCE));

}