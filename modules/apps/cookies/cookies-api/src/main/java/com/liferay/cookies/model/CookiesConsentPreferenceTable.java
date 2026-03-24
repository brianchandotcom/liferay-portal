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
 * The table class for the &quot;CookiesConsentPreference&quot; database table.
 *
 * @author Christopher Kian
 * @see CookiesConsentPreference
 * @generated
 */
public class CookiesConsentPreferenceTable
	extends BaseTable<CookiesConsentPreferenceTable> {

	public static final CookiesConsentPreferenceTable INSTANCE =
		new CookiesConsentPreferenceTable();

	public final Column<CookiesConsentPreferenceTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CookiesConsentPreferenceTable, Long>
		cookiesConsentPreferenceId = createColumn(
			"cookiesConsentPreferenceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CookiesConsentPreferenceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CookiesConsentPreferenceTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CookiesConsentPreferenceTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CookiesConsentPreferenceTable, String> domain =
		createColumn(
			"domain", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CookiesConsentPreferenceTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CookiesConsentPreferenceTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CookiesConsentPreferenceTable, String> value =
		createColumn("value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CookiesConsentPreferenceTable() {
		super("CookiesConsentPreference", CookiesConsentPreferenceTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-941541119