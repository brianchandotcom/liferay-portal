/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;RecentLayoutSetBranch&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutSetBranch
 * @generated
 */
public class RecentLayoutSetBranchTable
	extends BaseTable<RecentLayoutSetBranchTable> {

	public static final RecentLayoutSetBranchTable INSTANCE =
		new RecentLayoutSetBranchTable();

	public final Column<RecentLayoutSetBranchTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RecentLayoutSetBranchTable, Long>
		recentLayoutSetBranchId = createColumn(
			"recentLayoutSetBranchId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RecentLayoutSetBranchTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutSetBranchTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutSetBranchTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutSetBranchTable, Long> layoutSetBranchId =
		createColumn(
			"layoutSetBranchId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RecentLayoutSetBranchTable, Long> layoutSetId =
		createColumn(
			"layoutSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private RecentLayoutSetBranchTable() {
		super("RecentLayoutSetBranch", RecentLayoutSetBranchTable::new);
	}

}