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