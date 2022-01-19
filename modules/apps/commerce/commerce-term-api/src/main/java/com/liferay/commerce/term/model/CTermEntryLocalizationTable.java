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

package com.liferay.commerce.term.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;CTermEntryLocalization&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CTermEntryLocalization
 * @generated
 */
public class CTermEntryLocalizationTable
	extends BaseTable<CTermEntryLocalizationTable> {

	public static final CTermEntryLocalizationTable INSTANCE =
		new CTermEntryLocalizationTable();

	public final Column<CTermEntryLocalizationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTermEntryLocalizationTable, Long>
		cTermEntryLocalizationId = createColumn(
			"cTermEntryLocalizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CTermEntryLocalizationTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTermEntryLocalizationTable, Long> commerceTermEntryId =
		createColumn(
			"commerceTermEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CTermEntryLocalizationTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CTermEntryLocalizationTable, Clob> description =
		createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CTermEntryLocalizationTable, String> label =
		createColumn("label", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CTermEntryLocalizationTable() {
		super("CTermEntryLocalization", CTermEntryLocalizationTable::new);
	}

}