/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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