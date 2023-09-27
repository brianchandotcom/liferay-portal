/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;OSBCommunity_AkismetEntry&quot; database table.
 *
 * @author Jamie Sammons
 * @see AkismetEntry
 * @generated
 */
public class AkismetEntryTable extends BaseTable<AkismetEntryTable> {

	public static final AkismetEntryTable INSTANCE = new AkismetEntryTable();

	public final Column<AkismetEntryTable, Long> akismetEntryId = createColumn(
		"akismetEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AkismetEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AkismetEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AkismetEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AkismetEntryTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AkismetEntryTable, String> permalink = createColumn(
		"permalink", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AkismetEntryTable, String> referrer = createColumn(
		"referrer", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AkismetEntryTable, String> userAgent = createColumn(
		"userAgent", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AkismetEntryTable, String> userIP = createColumn(
		"userIP", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AkismetEntryTable, String> userURL = createColumn(
		"userURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AkismetEntryTable() {
		super("OSBCommunity_AkismetEntry", AkismetEntryTable::new);
	}

}