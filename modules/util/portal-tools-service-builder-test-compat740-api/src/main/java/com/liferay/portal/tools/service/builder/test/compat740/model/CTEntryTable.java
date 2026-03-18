/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;CTEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntry
 * @generated
 */
public class CTEntryTable extends BaseTable<CTEntryTable> {

	public static final CTEntryTable INSTANCE = new CTEntryTable();

	public final Column<CTEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTEntryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTEntryTable, Long> ctEntryId = createColumn(
		"ctEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CTEntryTable() {
		super("CTEntry", CTEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1831960335