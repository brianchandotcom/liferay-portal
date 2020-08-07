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
 * The table class for the &quot;CPFriendlyURLEntry&quot; database table.
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntry
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 com.liferay.friendly.url.model.impl.FriendlyURLEntryImpl}
 * @generated
 */
@Deprecated
public class CPFriendlyURLEntryTable
	extends BaseTable<CPFriendlyURLEntryTable> {

	public static final CPFriendlyURLEntryTable INSTANCE =
		new CPFriendlyURLEntryTable();

	public final Column<CPFriendlyURLEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPFriendlyURLEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, Long> CPFriendlyURLEntryId =
		createColumn(
			"CPFriendlyURLEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPFriendlyURLEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, String> urlTitle =
		createColumn(
			"urlTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPFriendlyURLEntryTable, Boolean> main = createColumn(
		"main", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private CPFriendlyURLEntryTable() {
		super("CPFriendlyURLEntry", CPFriendlyURLEntryTable::new);
	}

}