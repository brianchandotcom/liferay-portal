/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CTProcess&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTProcess
 * @generated
 */
public class CTProcessTable extends BaseTable<CTProcessTable> {

	public static final CTProcessTable INSTANCE = new CTProcessTable();

	public final Column<CTProcessTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTProcessTable, Long> ctProcessId = createColumn(
		"ctProcessId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTProcessTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTProcessTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTProcessTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CTProcessTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTProcessTable, Long> backgroundTaskId = createColumn(
		"backgroundTaskId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTProcessTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CTProcessTable() {
		super("CTProcess", CTProcessTable::new);
	}

}