/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;RecentLayoutRevision&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutRevision
 * @generated
 */
public class RecentLayoutRevisionTable
	extends BaseTable<RecentLayoutRevisionTable> {

	public static final RecentLayoutRevisionTable INSTANCE =
		new RecentLayoutRevisionTable();

	public final Column<RecentLayoutRevisionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RecentLayoutRevisionTable, Long>
		recentLayoutRevisionId = createColumn(
			"recentLayoutRevisionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RecentLayoutRevisionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutRevisionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutRevisionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutRevisionTable, Long> layoutRevisionId =
		createColumn(
			"layoutRevisionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutRevisionTable, Long> layoutSetBranchId =
		createColumn(
			"layoutSetBranchId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutRevisionTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private RecentLayoutRevisionTable() {
		super("RecentLayoutRevision", RecentLayoutRevisionTable::new);
	}

}