/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DepotEntryPin&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryPin
 * @generated
 */
public class DepotEntryPinTable extends BaseTable<DepotEntryPinTable> {

	public static final DepotEntryPinTable INSTANCE = new DepotEntryPinTable();

	public final Column<DepotEntryPinTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DepotEntryPinTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DepotEntryPinTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DepotEntryPinTable, Long> depotEntryPinId =
		createColumn(
			"depotEntryPinId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DepotEntryPinTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DepotEntryPinTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DepotEntryPinTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DepotEntryPinTable, Long> depotEntryId = createColumn(
		"depotEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DepotEntryPinTable() {
		super("DepotEntryPin", DepotEntryPinTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-2145004676