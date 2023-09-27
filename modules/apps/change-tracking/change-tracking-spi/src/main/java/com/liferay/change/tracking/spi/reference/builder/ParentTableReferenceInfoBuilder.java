/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * Used to define parent relationships for a {@link
 * com.liferay.change.tracking.spi.reference.TableReferenceDefinition}.
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