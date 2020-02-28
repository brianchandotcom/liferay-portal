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

package com.liferay.change.tracking.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;CTPreferences&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTPreferences
 * @generated
 */
public class CTPreferencesTable extends BaseTable<CTPreferencesTable> {

	public static final CTPreferencesTable INSTANCE = new CTPreferencesTable();

	public final Column<CTPreferencesTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTPreferencesTable, Long> ctPreferencesId =
		createColumn(
			"ctPreferencesId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTPreferencesTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTPreferencesTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTPreferencesTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTPreferencesTable, Long> previousCtCollectionId =
		createColumn(
			"previousCtCollectionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CTPreferencesTable, Boolean> confirmationEnabled =
		createColumn(
			"confirmationEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);

	private CTPreferencesTable() {
		super("CTPreferences", CTPreferencesTable::new);
	}

}