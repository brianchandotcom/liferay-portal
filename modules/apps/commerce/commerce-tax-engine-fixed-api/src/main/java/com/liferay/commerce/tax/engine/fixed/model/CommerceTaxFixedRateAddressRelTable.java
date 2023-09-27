/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceTaxFixedRateAddressRel&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRel
 * @generated
 */
public class CommerceTaxFixedRateAddressRelTable
	extends BaseTable<CommerceTaxFixedRateAddressRelTable> {

	public static final CommerceTaxFixedRateAddressRelTable INSTANCE =
		new CommerceTaxFixedRateAddressRelTable();

	public final Column<CommerceTaxFixedRateAddressRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceTaxFixedRateAddressRelTable, Long>
		commerceTaxFixedRateAddressRelId = createColumn(
			"CTaxFixedRateAddressRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceTaxFixedRateAddressRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, Long>
		commerceTaxMethodId = createColumn(
			"commerceTaxMethodId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, Long>
		CPTaxCategoryId = createColumn(
			"CPTaxCategoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, Long> countryId =
		createColumn(
			"countryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, Long> regionId =
		createColumn("regionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, String> zip =
		createColumn("zip", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateAddressRelTable, Double> rate =
		createColumn("rate", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CommerceTaxFixedRateAddressRelTable() {
		super(
			"CommerceTaxFixedRateAddressRel",
			CommerceTaxFixedRateAddressRelTable::new);
	}

}