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

import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.model.BaseModel;

/**
 * @author Preston Crary
 */
public interface TableReferenceAppender {

	public <T extends Table<T>> void appendChildTableReferences(
		Class<? extends BaseModel<?>> modelClass,
		Column<T, Long> primaryKeyColumn,
		ChildTableReferenceInfoBuilder<T> childTableReferenceInfoBuilder);

	public <T extends Table<T>> void appendParentTableReferences(
		Class<? extends BaseModel<?>> modelClass,
		Column<T, Long> primaryKeyColumn,
		ParentTableReferenceInfoBuilder<T> parentTableReferenceInfoBuilder);

}