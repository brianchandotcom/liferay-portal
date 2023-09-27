/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ERCGroupEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntry
 * @generated
 */
public class ERCGroupEntryTable extends BaseTable<ERCGroupEntryTable> {

	public static final ERCGroupEntryTable INSTANCE = new ERCGroupEntryTable();

	public final Column<ERCGroupEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ERCGroupEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ERCGroupEntryTable, Long> ercGroupEntryId =
		createColumn(
			"ercGroupEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ERCGroupEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ERCGroupEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private ERCGroupEntryTable() {
		super("ERCGroupEntry", ERCGroupEntryTable::new);
	}

}