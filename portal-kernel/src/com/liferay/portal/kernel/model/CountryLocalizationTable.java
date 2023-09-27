/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;CountryLocalization&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CountryLocalization
 * @generated
 */
public class CountryLocalizationTable
	extends BaseTable<CountryLocalizationTable> {

	public static final CountryLocalizationTable INSTANCE =
		new CountryLocalizationTable();

	public final Column<CountryLocalizationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CountryLocalizationTable, Long> countryLocalizationId =
		createColumn(
			"countryLocalizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CountryLocalizationTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CountryLocalizationTable, Long> countryId =
		createColumn(
			"countryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CountryLocalizationTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CountryLocalizationTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CountryLocalizationTable() {
		super("CountryLocalization", CountryLocalizationTable::new);
	}

}