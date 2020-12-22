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

package com.liferay.document.library.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DLStorageQuota&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DLStorageQuota
 * @generated
 */
public class DLStorageQuotaTable extends BaseTable<DLStorageQuotaTable> {

	public static final DLStorageQuotaTable INSTANCE =
		new DLStorageQuotaTable();

	public final Column<DLStorageQuotaTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DLStorageQuotaTable, Long> dlStorageQuotaId =
		createColumn(
			"dlStorageQuotaId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLStorageQuotaTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLStorageQuotaTable, Long> storageSize = createColumn(
		"storageSize", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DLStorageQuotaTable() {
		super("DLStorageQuota", DLStorageQuotaTable::new);
	}

}