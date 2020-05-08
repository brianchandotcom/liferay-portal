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

package com.liferay.friendly.url.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;FriendlyURLEntryMapping&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryMapping
 * @generated
 */
public class FriendlyURLEntryMappingTable
	extends BaseTable<FriendlyURLEntryMappingTable> {

	public static final FriendlyURLEntryMappingTable INSTANCE =
		new FriendlyURLEntryMappingTable();

	public final Column<FriendlyURLEntryMappingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FriendlyURLEntryMappingTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FriendlyURLEntryMappingTable, Long>
		friendlyURLEntryMappingId = createColumn(
			"friendlyURLEntryMappingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FriendlyURLEntryMappingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryMappingTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryMappingTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryMappingTable, Long> friendlyURLEntryId =
		createColumn(
			"friendlyURLEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private FriendlyURLEntryMappingTable() {
		super("FriendlyURLEntryMapping", FriendlyURLEntryMappingTable::new);
	}

}