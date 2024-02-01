/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.entry.util;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntrySearchUtil {

	public static Predicate getObjectFieldPredicate(
		Column<?, Object> column, String dbType, String search) {

		if (dbType.equals(ObjectFieldConstants.DB_TYPE_BIG_DECIMAL) ||
			dbType.equals(ObjectFieldConstants.DB_TYPE_DOUBLE)) {

			BigDecimal searchBigDecimal = BigDecimal.valueOf(
				GetterUtil.getDouble(search));

			if (searchBigDecimal.compareTo(BigDecimal.ZERO) != 0) {
				return column.eq(searchBigDecimal);
			}
		}
		else if (dbType.equals(ObjectFieldConstants.DB_TYPE_CLOB) ||
				 dbType.equals(ObjectFieldConstants.DB_TYPE_STRING)) {

			return column.like("%" + search + "%");
		}
		else if (dbType.equals(ObjectFieldConstants.DB_TYPE_INTEGER) ||
				 dbType.equals(ObjectFieldConstants.DB_TYPE_LONG)) {

			long searchLong = GetterUtil.getLong(search);

			if (searchLong != 0L) {
				return column.eq(searchLong);
			}
		}

		return null;
	}

	public static Column<?, Long> getPrimaryKeyColumn(
		String pkObjectFieldDBColumnName, Table<?> table) {

		Column<?, Long> primaryKeyColumn = (Column<?, Long>)table.getColumn(
			pkObjectFieldDBColumnName);

		if (primaryKeyColumn == null) {
			primaryKeyColumn = ObjectEntryTable.INSTANCE.objectEntryId;
		}

		return primaryKeyColumn;
	}

	public static Predicate getRelatedModelsPredicate(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService, String search,
		Table<?> table) {

		if ((objectDefinition == null) || Validator.isNull(search) ||
			(table == null)) {

			return null;
		}

		ObjectField titleObjectField = objectFieldLocalService.fetchObjectField(
			objectDefinition.getTitleObjectFieldId());

		if (titleObjectField == null) {
			titleObjectField = objectFieldLocalService.fetchObjectField(
				objectDefinition.getObjectDefinitionId(), "id");
		}

		Predicate objectFieldPredicate = getObjectFieldPredicate(
			(Column<?, Object>)objectFieldLocalService.getColumn(
				objectDefinition.getObjectDefinitionId(),
				titleObjectField.getName()),
			titleObjectField.getDBType(), search);

		long searchLong = GetterUtil.getLong(search);

		if (searchLong == 0) {
			return objectFieldPredicate;
		}

		Column<?, Long> primaryKeyColumn = getPrimaryKeyColumn(
			objectDefinition.getPKObjectFieldDBColumnName(), table);

		Predicate primaryKeyPredicate = primaryKeyColumn.eq(searchLong);

		if (objectFieldPredicate == null) {
			return primaryKeyPredicate;
		}

		return objectFieldPredicate.or(
			primaryKeyPredicate
		).withParentheses();
	}

	public static Predicate getUniqueCompositeKeyObjectFieldPredicate(
		Column<?, Object> column, String dbType, Object value) {

		if (dbType.equals(ObjectFieldConstants.DB_TYPE_INTEGER) ||
			dbType.equals(ObjectFieldConstants.DB_TYPE_LONG)) {

			return column.eq(GetterUtil.getLong(value));
		}
		else if (dbType.equals(ObjectFieldConstants.DB_TYPE_STRING)) {
			if (value == null) {
				return column.isNull();
			}

			return column.eq(String.valueOf(value));
		}

		return null;
	}

}