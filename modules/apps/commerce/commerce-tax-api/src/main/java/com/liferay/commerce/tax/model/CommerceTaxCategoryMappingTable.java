/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceTaxCategoryMapping&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceTaxCategoryMapping
 * @generated
 */
public class CommerceTaxCategoryMappingTable
	extends BaseTable<CommerceTaxCategoryMappingTable> {

	public static final CommerceTaxCategoryMappingTable INSTANCE =
		new CommerceTaxCategoryMappingTable();

	public final Column<CommerceTaxCategoryMappingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceTaxCategoryMappingTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxCategoryMappingTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTaxCategoryMappingTable, Long>
		commerceTaxCategoryMappingId = createColumn(
			"commerceTaxCategoryMappingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceTaxCategoryMappingTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxCategoryMappingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxCategoryMappingTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxCategoryMappingTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxCategoryMappingTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxCategoryMappingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxCategoryMappingTable, Long>
		commerceTaxMethodId = createColumn(
			"commerceTaxMethodId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTaxCategoryMappingTable, Long> CPTaxCategoryId =
		createColumn(
			"CPTaxCategoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private CommerceTaxCategoryMappingTable() {
		super(
			"CommerceTaxCategoryMapping", CommerceTaxCategoryMappingTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-2118695163