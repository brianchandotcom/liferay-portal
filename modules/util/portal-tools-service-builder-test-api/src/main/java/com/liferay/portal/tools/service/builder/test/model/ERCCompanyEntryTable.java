/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ERCCompanyEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntry
 * @generated
 */
public class ERCCompanyEntryTable extends BaseTable<ERCCompanyEntryTable> {

	public static final ERCCompanyEntryTable INSTANCE =
		new ERCCompanyEntryTable();

	public final Column<ERCCompanyEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ERCCompanyEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ERCCompanyEntryTable, Long> ercCompanyEntryId =
		createColumn(
			"ercCompanyEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ERCCompanyEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private ERCCompanyEntryTable() {
		super("ERCCompanyEntry", ERCCompanyEntryTable::new);
	}

}