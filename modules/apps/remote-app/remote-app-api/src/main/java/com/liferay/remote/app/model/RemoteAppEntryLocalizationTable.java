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

package com.liferay.remote.app.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;RemoteAppEntryLocalization&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryLocalization
 * @generated
 */
public class RemoteAppEntryLocalizationTable
	extends BaseTable<RemoteAppEntryLocalizationTable> {

	public static final RemoteAppEntryLocalizationTable INSTANCE =
		new RemoteAppEntryLocalizationTable();

	public final Column<RemoteAppEntryLocalizationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RemoteAppEntryLocalizationTable, Long>
		remoteAppEntryLocalizationId = createColumn(
			"remoteAppEntryLocalizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RemoteAppEntryLocalizationTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryLocalizationTable, Long>
		remoteAppEntryId = createColumn(
			"remoteAppEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryLocalizationTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryLocalizationTable, Clob> description =
		createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryLocalizationTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RemoteAppEntryLocalizationTable() {
		super(
			"RemoteAppEntryLocalization", RemoteAppEntryLocalizationTable::new);
	}

}