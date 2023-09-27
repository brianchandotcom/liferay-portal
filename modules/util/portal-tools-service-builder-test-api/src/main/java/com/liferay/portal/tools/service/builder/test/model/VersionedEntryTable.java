/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;VersionedEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntry
 * @generated
 */
public class VersionedEntryTable extends BaseTable<VersionedEntryTable> {

	public static final VersionedEntryTable INSTANCE =
		new VersionedEntryTable();

	public final Column<VersionedEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<VersionedEntryTable, Long> headId = createColumn(
		"headId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VersionedEntryTable, Boolean> head = createColumn(
		"head", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<VersionedEntryTable, Long> versionedEntryId =
		createColumn(
			"versionedEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<VersionedEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private VersionedEntryTable() {
		super("VersionedEntry", VersionedEntryTable::new);
	}

}