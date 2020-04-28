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
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Groups_Orgs&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Group
 * @see Organization
 * @generated
 */
public class Groups_OrgsTable extends BaseTable<Groups_OrgsTable> {

	public static final Groups_OrgsTable INSTANCE = new Groups_OrgsTable();

	public final Column<Groups_OrgsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_OrgsTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_OrgsTable, Long> organizationId = createColumn(
		"organizationId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_OrgsTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_OrgsTable, Boolean> ctChangeType = createColumn(
		"ctChangeType", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private Groups_OrgsTable() {
		super("Groups_Orgs", Groups_OrgsTable::new);
	}

}