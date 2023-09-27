/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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