/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;RenameFinderColumnEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RenameFinderColumnEntry
 * @generated
 */
public class RenameFinderColumnEntryTable
	extends BaseTable<RenameFinderColumnEntryTable> {

	public static final RenameFinderColumnEntryTable INSTANCE =
		new RenameFinderColumnEntryTable();

	public final Column<RenameFinderColumnEntryTable, Long>
		renameFinderColumnEntryId = createColumn(
			"renameFinderColumnEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RenameFinderColumnEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RenameFinderColumnEntryTable, String> columnToRename =
		createColumn(
			"columnToRename", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RenameFinderColumnEntryTable() {
		super("RenameFinderColumnEntry", RenameFinderColumnEntryTable::new);
	}

}