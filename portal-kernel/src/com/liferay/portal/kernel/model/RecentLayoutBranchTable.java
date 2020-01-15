/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the RecentLayoutBranch.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RecentLayoutBranchTable extends Table<RecentLayoutBranchTable> {

	public static final RecentLayoutBranchTable INSTANCE =
		new RecentLayoutBranchTable();

	public final Column<RecentLayoutBranchTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<RecentLayoutBranchTable, Long> recentLayoutBranchId =
		createColumn("recentLayoutBranchId", Long.class, Types.BIGINT);
	public final Column<RecentLayoutBranchTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<RecentLayoutBranchTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<RecentLayoutBranchTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<RecentLayoutBranchTable, Long> layoutBranchId =
		createColumn("layoutBranchId", Long.class, Types.BIGINT);
	public final Column<RecentLayoutBranchTable, Long> layoutSetBranchId =
		createColumn("layoutSetBranchId", Long.class, Types.BIGINT);
	public final Column<RecentLayoutBranchTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT);

	private RecentLayoutBranchTable() {
		super("RecentLayoutBranch", RecentLayoutBranchTable::new);
	}

}