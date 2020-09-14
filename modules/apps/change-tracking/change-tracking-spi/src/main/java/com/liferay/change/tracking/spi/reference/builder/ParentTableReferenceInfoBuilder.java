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

package com.liferay.change.tracking.spi.reference.builder;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;

import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Used to define parent relationships for a {@link com.liferay.change.tracking.spi.reference.TableReferenceDefinition}.
 *
 * @author Preston Crary
 * @see    com.liferay.change.tracking.spi.reference.TableReferenceDefinition#defineParentTableReferences(
 *         ParentTableReferenceInfoBuilder)
 */
@ProviderType
public interface ParentTableReferenceInfoBuilder<T extends Table<T>> {

	public default ParentTableReferenceInfoBuilder<T> classNameReference(
		Column<T, Long> classPKColumn, Column<?, Long> pkColumn,
		Class<? extends BaseModel<?>> modelClass) {

		if (pkColumn.getTable() == classPKColumn.getTable()) {
			throw new IllegalArgumentException();
		}

		Table<?> table = classPKColumn.getTable();

		return referenceInnerJoin(
			fromStep -> fromStep.from(
				pkColumn.getTable()
			).innerJoinON(
				table, pkColumn.eq(classPKColumn)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					modelClass.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						table.getColumn("classNameId", Long.class))
				)
			));
	}

	public default ParentTableReferenceInfoBuilder<T> groupedModel(T table) {
		return singleColumnReference(
			table.getColumn("groupId", Long.class), GroupTable.INSTANCE.groupId
		).singleColumnReference(
			table.getColumn("companyId", Long.class),
			CompanyTable.INSTANCE.companyId
		);
	}

	public default <C> ParentTableReferenceInfoBuilder<T> parentColumnReference(
		Column<T, C> pkColumn, Column<T, C> parentPKColumn) {

		if (!pkColumn.isPrimaryKey()) {
			throw new IllegalArgumentException(pkColumn + " is not primary");
		}

		T parentTable = pkColumn.getTable();

		T aliasParentTable = parentTable.as("aliasParentTable");

		Column<T, C> aliasPKColumn = aliasParentTable.getColumn(
			pkColumn.getName(), pkColumn.getJavaType());

		return singleColumnReference(parentPKColumn, aliasPKColumn);
	}

	public ParentTableReferenceInfoBuilder<T> referenceInnerJoin(
		Function<FromStep, JoinStep> joinFunction);

	public default <C> ParentTableReferenceInfoBuilder<T> singleColumnReference(
		Column<T, C> column1, Column<?, C> column2) {

		if (column1.getTable() == column2.getTable()) {
			throw new IllegalArgumentException();
		}

		Predicate predicate = column1.eq(column2);

		return referenceInnerJoin(
			fromStep -> fromStep.from(
				column2.getTable()
			).innerJoinON(
				column1.getTable(), predicate
			));
	}

}