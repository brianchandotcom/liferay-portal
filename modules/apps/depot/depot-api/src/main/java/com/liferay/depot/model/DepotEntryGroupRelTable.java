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

package com.liferay.depot.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DepotEntryGroupRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryGroupRel
 * @generated
 */
public class DepotEntryGroupRelTable
	extends BaseTable<DepotEntryGroupRelTable> {

	public static final DepotEntryGroupRelTable INSTANCE =
		new DepotEntryGroupRelTable();

	public final Column<DepotEntryGroupRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DepotEntryGroupRelTable, Long> depotEntryGroupRelId =
		createColumn(
			"depotEntryGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DepotEntryGroupRelTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DepotEntryGroupRelTable, Long> depotEntryId =
		createColumn(
			"depotEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DepotEntryGroupRelTable, Boolean> searchable =
		createColumn(
			"searchable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<DepotEntryGroupRelTable, Long> toGroupId = createColumn(
		"toGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DepotEntryGroupRelTable() {
		super("DepotEntryGroupRel", DepotEntryGroupRelTable::new);
	}

}