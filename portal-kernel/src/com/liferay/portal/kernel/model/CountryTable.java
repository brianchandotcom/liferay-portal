/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Country&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Country
 * @generated
 */
public class CountryTable extends BaseTable<CountryTable> {

	public static final CountryTable INSTANCE = new CountryTable();

	public final Column<CountryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CountryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CountryTable, String> defaultLanguageId = createColumn(
		"defaultLanguageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Long> countryId = createColumn(
		"countryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CountryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CountryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CountryTable, String> a2 = createColumn(
		"a2", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CountryTable, String> a3 = createColumn(
		"a3", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Boolean> billingAllowed = createColumn(
		"billingAllowed", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Boolean> groupFilterEnabled =
		createColumn(
			"groupFilterEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CountryTable, String> idd = createColumn(
		"idd_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CountryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CountryTable, String> number = createColumn(
		"number_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Double> position = createColumn(
		"position", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Boolean> shippingAllowed = createColumn(
		"shippingAllowed", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Boolean> subjectToVAT = createColumn(
		"subjectToVAT", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Boolean> zipRequired = createColumn(
		"zipRequired", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CountryTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CountryTable() {
		super("Country", CountryTable::new);
	}

}