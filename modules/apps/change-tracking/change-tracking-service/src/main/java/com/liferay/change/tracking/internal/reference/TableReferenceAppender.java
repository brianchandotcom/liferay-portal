/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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