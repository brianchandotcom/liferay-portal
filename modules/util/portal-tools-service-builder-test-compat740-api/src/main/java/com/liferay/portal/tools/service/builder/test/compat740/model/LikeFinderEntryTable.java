/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;LikeFinderEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LikeFinderEntry
 * @generated
 */
public class LikeFinderEntryTable extends BaseTable<LikeFinderEntryTable> {

	public static final LikeFinderEntryTable INSTANCE =
		new LikeFinderEntryTable();

	public final Column<LikeFinderEntryTable, Long> likeFinderEntryId =
		createColumn(
			"likeFinderEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LikeFinderEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LikeFinderEntryTable, Long> ownerId = createColumn(
		"ownerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LikeFinderEntryTable, Integer> ownerType = createColumn(
		"ownerType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<LikeFinderEntryTable, String> portletId = createColumn(
		"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private LikeFinderEntryTable() {
		super("LikeFinderEntry", LikeFinderEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-155610181