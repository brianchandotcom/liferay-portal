/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.reference;

import com.liferay.change.tracking.internal.spi.reference.ExpandoTableReferenceAppender;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Collections;
import java.util.List;

/**
 * @author Preston Crary
 */
public class TableReferenceAppenderRegistry {

	public static <T extends Table<T>> void appendTableReferences(
		TableReferenceDefinition<T> tableReferenceDefinition,
		Column<T, Long> primaryKeyColumn,
		ParentTableReferenceInfoBuilder<T> parentTableReferenceInfoBuilder,
		ChildTableReferenceInfoBuilder<T> childTableReferenceInfoBuilder) {

		BasePersistence<?> basePersistence =
			tableReferenceDefinition.getBasePersistence();

		Class<? extends BaseModel<?>> modelClass =
			basePersistence.getModelClass();

		for (TableReferenceAppender tableReferenceAppender :
				_tableReferenceAppenders) {

			tableReferenceAppender.appendParentTableReferences(
				modelClass, primaryKeyColumn, parentTableReferenceInfoBuilder);

			tableReferenceAppender.appendChildTableReferences(
				modelClass, primaryKeyColumn, childTableReferenceInfoBuilder);
		}
	}

	private static final List<TableReferenceAppender> _tableReferenceAppenders =
		Collections.singletonList(new ExpandoTableReferenceAppender());

}