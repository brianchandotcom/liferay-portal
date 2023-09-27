/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;LVEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LVEntry
 * @generated
 */
public class LVEntryTable extends BaseTable<LVEntryTable> {

	public static final LVEntryTable INSTANCE = new LVEntryTable();

	public final Column<LVEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LVEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, Long> headId = createColumn(
		"headId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, Boolean> head = createColumn(
		"head", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, String> defaultLanguageId = createColumn(
		"defaultLanguageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, Long> lvEntryId = createColumn(
		"lvEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LVEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, String> uniqueGroupKey = createColumn(
		"uniqueGroupKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private LVEntryTable() {
		super("LVEntry", LVEntryTable::new);
	}

}