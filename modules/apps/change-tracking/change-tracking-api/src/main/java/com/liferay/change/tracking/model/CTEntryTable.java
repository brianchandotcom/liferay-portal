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
 * The table class for the &quot;CTEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntry
 * @generated
 */
public class CTEntryTable extends BaseTable<CTEntryTable> {

	public static final CTEntryTable INSTANCE = new CTEntryTable();

	public final Column<CTEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTEntryTable, Long> ctEntryId = createColumn(
		"ctEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CTEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CTEntryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTEntryTable, Long> modelClassNameId = createColumn(
		"modelClassNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTEntryTable, Long> modelClassPK = createColumn(
		"modelClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTEntryTable, Long> modelMvccVersion = createColumn(
		"modelMvccVersion", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTEntryTable, Integer> changeType = createColumn(
		"changeType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CTEntryTable() {
		super("CTEntry", CTEntryTable::new);
	}

}