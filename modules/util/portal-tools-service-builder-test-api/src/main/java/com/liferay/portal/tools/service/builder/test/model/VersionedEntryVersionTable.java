/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;VersionedEntryVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryVersion
 * @generated
 */
public class VersionedEntryVersionTable
	extends BaseTable<VersionedEntryVersionTable> {

	public static final VersionedEntryVersionTable INSTANCE =
		new VersionedEntryVersionTable();

	public final Column<VersionedEntryVersionTable, Long>
		versionedEntryVersionId = createColumn(
			"versionedEntryVersionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<VersionedEntryVersionTable, Integer> version =
		createColumn(
			"version", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<VersionedEntryVersionTable, Long> versionedEntryId =
		createColumn(
			"versionedEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VersionedEntryVersionTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private VersionedEntryVersionTable() {
		super("VersionedEntryVersion", VersionedEntryVersionTable::new);
	}

}