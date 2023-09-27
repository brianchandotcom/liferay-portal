/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPInstanceOptionValueRel&quot; database table.
 *
 * @author Marco Leo
 * @see CPInstanceOptionValueRel
 * @generated
 */
public class CPInstanceOptionValueRelTable
	extends BaseTable<CPInstanceOptionValueRelTable> {

	public static final CPInstanceOptionValueRelTable INSTANCE =
		new CPInstanceOptionValueRelTable();

	public final Column<CPInstanceOptionValueRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPInstanceOptionValueRelTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPInstanceOptionValueRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceOptionValueRelTable, Long>
		CPInstanceOptionValueRelId = createColumn(
			"CPInstanceOptionValueRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPInstanceOptionValueRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceOptionValueRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceOptionValueRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceOptionValueRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceOptionValueRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceOptionValueRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceOptionValueRelTable, Long>
		CPDefinitionOptionRelId = createColumn(
			"CPDefinitionOptionRelId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceOptionValueRelTable, Long>
		CPDefinitionOptionValueRelId = createColumn(
			"CPDefinitionOptionValueRelId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceOptionValueRelTable, Long> CPInstanceId =
		createColumn(
			"CPInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private CPInstanceOptionValueRelTable() {
		super("CPInstanceOptionValueRel", CPInstanceOptionValueRelTable::new);
	}

}