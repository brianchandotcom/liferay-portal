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

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.UserTable;

import java.util.Date;
import java.util.function.Function;

/**
 * @author Preston Crary
 */
public interface TableReferenceInfoBuilder<T extends Table<T>> {

	public default TableReferenceInfoBuilder<T> defineGroupedModel(T table) {
		defineSingleColumnReference(
			table.getColumn("groupId", Long.class),
			GroupTable.INSTANCE.groupId);

		defineSingleColumnReference(
			table.getColumn("companyId", Long.class),
			CompanyTable.INSTANCE.companyId);

		defineSingleColumnReference(
			table.getColumn("userId", Long.class), UserTable.INSTANCE.userId);

		defineNonreferenceColumn(table.getColumn("userName", String.class));

		defineNonreferenceColumn(table.getColumn("createDate", Date.class));

		defineNonreferenceColumn(table.getColumn("modifiedDate", Date.class));

		return this;
	}

	public TableReferenceInfoBuilder<T> defineNonreferenceColumn(
		Column<T, ?> column);

	@SuppressWarnings("unchecked")
	public default TableReferenceInfoBuilder<T> defineNonreferenceColumns(
		Column<?, ?>... columns) {

		for (Column<?, ?> column : columns) {
			defineNonreferenceColumn((Column<T, ?>)column);
		}

		return this;
	}

	public default <C> TableReferenceInfoBuilder<T> defineParentColumnReference(
		Column<T, C> pkColumn, Column<T, C> parentPKColumn) {

		if (!pkColumn.isPrimaryKey()) {
			throw new IllegalArgumentException(pkColumn + " is not primary");
		}

		T parentTable = pkColumn.getTable();

		T aliasParentTable = parentTable.as("aliasParentTable");

		Column<T, C> aliasPKColumn = aliasParentTable.getColumn(
			pkColumn.getName(), pkColumn.getJavaType());

		defineSingleColumnReference(parentPKColumn, aliasPKColumn);

		return this;
	}

	public TableReferenceInfoBuilder<T> defineReferenceInnerJoin(
		Function<FromStep, JoinStep> joinFunction);

	public default <C> TableReferenceInfoBuilder<T> defineSingleColumnReference(
		Column<T, C> column1, Column<?, C> column2) {

		if (column1.getTable() == column2.getTable()) {
			throw new IllegalArgumentException();
		}

		Predicate predicate = column1.eq(column2);

		defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				column2.getTable()
			).innerJoinON(
				column1.getTable(), predicate
			));

		return this;
	}

}