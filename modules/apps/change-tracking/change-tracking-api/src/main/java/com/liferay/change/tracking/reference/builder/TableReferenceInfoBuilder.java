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

package com.liferay.change.tracking.reference.builder;

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.UserTable;

import java.util.Date;
import java.util.function.Function;

/**
 * @author Preston Crary
 */
public interface TableReferenceInfoBuilder<T extends Table<T>> {

	public default TableReferenceInfoBuilder<T> assetEntryReference(
		Column<T, Long> pkColumn, Class<?> modelClass) {

		return referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				pkColumn.getTable(),
				pkColumn.eq(AssetEntryTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					modelClass.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						AssetEntryTable.INSTANCE.classNameId)
				)
			));
	}

	public default TableReferenceInfoBuilder<T> groupedModel(T table) {
		singleColumnReference(
			table.getColumn("groupId", Long.class), GroupTable.INSTANCE.groupId
		).singleColumnReference(
			table.getColumn("companyId", Long.class),
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			table.getColumn("userId", Long.class), UserTable.INSTANCE.userId
		).nonreferenceColumns(
			table.getColumn("userName", String.class),
			table.getColumn("createDate", Date.class),
			table.getColumn("modifiedDate", Date.class)
		);

		return this;
	}

	public TableReferenceInfoBuilder<T> nonreferenceColumn(Column<T, ?> column);

	@SuppressWarnings("unchecked")
	public default TableReferenceInfoBuilder<T> nonreferenceColumns(
		Column<?, ?>... columns) {

		for (Column<?, ?> column : columns) {
			nonreferenceColumn((Column<T, ?>)column);
		}

		return this;
	}

	public default <C> TableReferenceInfoBuilder<T> parentColumnReference(
		Column<T, C> pkColumn, Column<T, C> parentPKColumn) {

		if (!pkColumn.isPrimaryKey()) {
			throw new IllegalArgumentException(pkColumn + " is not primary");
		}

		T parentTable = pkColumn.getTable();

		T aliasParentTable = parentTable.as("aliasParentTable");

		Column<T, C> aliasPKColumn = aliasParentTable.getColumn(
			pkColumn.getName(), pkColumn.getJavaType());

		singleColumnReference(parentPKColumn, aliasPKColumn);

		return this;
	}

	public TableReferenceInfoBuilder<T> referenceInnerJoin(
		Function<FromStep, JoinStep> joinFunction);

	public default TableReferenceInfoBuilder<T> resourcePermissionReference(
		Column<T, Long> pkColumn, Class<?> modelClass) {

		T table = pkColumn.getTable();

		Column<T, Long> companyIdColumn = table.getColumn(
			"companyId", Long.class);

		return referenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				table,
				companyIdColumn.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.eq(
						modelClass.getName())
				).and(
					pkColumn.eq(ResourcePermissionTable.INSTANCE.primKeyId)
				)
			));
	}

	public default <C> TableReferenceInfoBuilder<T> singleColumnReference(
		Column<T, C> column1, Column<?, C> column2) {

		if (column1.getTable() == column2.getTable()) {
			throw new IllegalArgumentException();
		}

		Predicate predicate = column1.eq(column2);

		referenceInnerJoin(
			fromStep -> fromStep.from(
				column2.getTable()
			).innerJoinON(
				column1.getTable(), predicate
			));

		return this;
	}

}