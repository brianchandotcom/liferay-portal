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

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.SystemEventTable;

import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Used to define child relationships for a TableReferenceDefinition.
 *
 * @author Preston Crary
 * @see    TableReferenceDefinition#defineChildTableReferences(
 *         ChildTableReferenceInfoBuilder)
 */
@ProviderType
public interface ChildTableReferenceInfoBuilder<T extends Table<T>> {

	public default ChildTableReferenceInfoBuilder<T> assetEntryReference(
		Column<T, Long> pkColumn, Class<? extends BaseModel<?>> modelClass) {

		return classNameReference(
			pkColumn, AssetEntryTable.INSTANCE.classPK, modelClass);
	}

	public default ChildTableReferenceInfoBuilder<T> classNameReference(
		Column<T, Long> pkColumn, Column<?, Long> classPKColumn,
		Class<? extends BaseModel<?>> modelClass) {

		if (pkColumn.getTable() == classPKColumn.getTable()) {
			throw new IllegalArgumentException();
		}

		Table<?> table = classPKColumn.getTable();

		return referenceInnerJoin(
			fromStep -> fromStep.from(
				table
			).innerJoinON(
				pkColumn.getTable(), pkColumn.eq(classPKColumn)
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

	public ChildTableReferenceInfoBuilder<T> referenceInnerJoin(
		Function<FromStep, JoinStep> joinFunction);

	public default ChildTableReferenceInfoBuilder<T>
		resourcePermissionReference(
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

	public default <C> ChildTableReferenceInfoBuilder<T> singleColumnReference(
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

	public default ChildTableReferenceInfoBuilder<T> systemEventReference(
		Column<T, Long> pkColumn, Class<? extends BaseModel<?>> modelClass) {

		if (!pkColumn.isPrimaryKey()) {
			throw new IllegalArgumentException(pkColumn + " is not primary");
		}

		T table = pkColumn.getTable();

		String className = modelClass.getName();

		referenceInnerJoin(
			fromStep -> fromStep.from(
				SystemEventTable.INSTANCE
			).innerJoinON(
				table, pkColumn.eq(SystemEventTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					SystemEventTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(className)
				)
			));

		Column<T, Long> groupIdColumn = table.getColumn("groupId", Long.class);

		Column<T, String> uuidColumn = table.getColumn("uuid_", String.class);

		if ((groupIdColumn != null) && (uuidColumn != null)) {
			referenceInnerJoin(
				fromStep -> fromStep.from(
					SystemEventTable.INSTANCE
				).innerJoinON(
					table,
					SystemEventTable.INSTANCE.groupId.eq(
						groupIdColumn
					).and(
						SystemEventTable.INSTANCE.classUuid.eq(uuidColumn)
					)
				));
		}

		return this;
	}

}