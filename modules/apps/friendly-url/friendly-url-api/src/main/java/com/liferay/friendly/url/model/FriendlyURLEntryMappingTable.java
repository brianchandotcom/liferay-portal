/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;FriendlyURLEntryMapping&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryMapping
 * @generated
 */
public class FriendlyURLEntryMappingTable
	extends BaseTable<FriendlyURLEntryMappingTable> {

	public static final FriendlyURLEntryMappingTable INSTANCE =
		new FriendlyURLEntryMappingTable();

	public final Column<FriendlyURLEntryMappingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FriendlyURLEntryMappingTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FriendlyURLEntryMappingTable, Long>
		friendlyURLEntryMappingId = createColumn(
			"friendlyURLEntryMappingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FriendlyURLEntryMappingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryMappingTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryMappingTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryMappingTable, Long> friendlyURLEntryId =
		createColumn(
			"friendlyURLEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private FriendlyURLEntryMappingTable() {
		super("FriendlyURLEntryMapping", FriendlyURLEntryMappingTable::new);
	}

}