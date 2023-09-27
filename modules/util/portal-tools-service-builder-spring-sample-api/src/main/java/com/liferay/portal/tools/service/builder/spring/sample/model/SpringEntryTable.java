/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.spring.sample.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SpringEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SpringEntry
 * @generated
 */
public class SpringEntryTable extends BaseTable<SpringEntryTable> {

	public static final SpringEntryTable INSTANCE = new SpringEntryTable();

	public final Column<SpringEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SpringEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SpringEntryTable, Long> springEntryId = createColumn(
		"springEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SpringEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SpringEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private SpringEntryTable() {
		super("SpringEntry", SpringEntryTable::new);
	}

}