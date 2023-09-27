/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;RecentLayoutBranch&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutBranch
 * @generated
 */
public class RecentLayoutBranchTable
	extends BaseTable<RecentLayoutBranchTable> {

	public static final RecentLayoutBranchTable INSTANCE =
		new RecentLayoutBranchTable();

	public final Column<RecentLayoutBranchTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RecentLayoutBranchTable, Long> recentLayoutBranchId =
		createColumn(
			"recentLayoutBranchId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RecentLayoutBranchTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutBranchTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutBranchTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutBranchTable, Long> layoutBranchId =
		createColumn(
			"layoutBranchId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutBranchTable, Long> layoutSetBranchId =
		createColumn(
			"layoutSetBranchId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutBranchTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private RecentLayoutBranchTable() {
		super("RecentLayoutBranch", RecentLayoutBranchTable::new);
	}

}