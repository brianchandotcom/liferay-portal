/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.sync.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DLSyncEvent&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEvent
 * @generated
 */
public class DLSyncEventTable extends BaseTable<DLSyncEventTable> {

	public static final DLSyncEventTable INSTANCE = new DLSyncEventTable();

	public final Column<DLSyncEventTable, Long> syncEventId = createColumn(
		"syncEventId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLSyncEventTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLSyncEventTable, Long> modifiedTime = createColumn(
		"modifiedTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLSyncEventTable, String> event = createColumn(
		"event", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLSyncEventTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLSyncEventTable, Long> typePK = createColumn(
		"typePK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DLSyncEventTable() {
		super("DLSyncEvent", DLSyncEventTable::new);
	}

}