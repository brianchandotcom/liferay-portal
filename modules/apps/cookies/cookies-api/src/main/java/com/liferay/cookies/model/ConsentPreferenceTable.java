/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ConsentPreference&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ConsentPreference
 * @generated
 */
public class ConsentPreferenceTable extends BaseTable<ConsentPreferenceTable> {

	public static final ConsentPreferenceTable INSTANCE =
		new ConsentPreferenceTable();

	public final Column<ConsentPreferenceTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ConsentPreferenceTable, Long> consentPreferenceId =
		createColumn(
			"consentPreferenceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ConsentPreferenceTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ConsentPreferenceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ConsentPreferenceTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ConsentPreferenceTable, String> domain = createColumn(
		"domain", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ConsentPreferenceTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ConsentPreferenceTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ConsentPreferenceTable, String> value = createColumn(
		"value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ConsentPreferenceTable() {
		super("ConsentPreference", ConsentPreferenceTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1754899266