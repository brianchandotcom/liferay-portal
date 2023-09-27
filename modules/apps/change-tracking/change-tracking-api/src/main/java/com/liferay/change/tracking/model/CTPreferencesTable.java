/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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