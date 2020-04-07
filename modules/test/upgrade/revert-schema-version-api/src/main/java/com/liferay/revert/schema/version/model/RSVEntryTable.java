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

package com.liferay.revert.schema.version.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;RSVEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RSVEntry
 * @generated
 */
public class RSVEntryTable extends BaseTable<RSVEntryTable> {

	public static final RSVEntryTable INSTANCE = new RSVEntryTable();

	public final Column<RSVEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RSVEntryTable, Long> rsvEntryId = createColumn(
		"rsvEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RSVEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private RSVEntryTable() {
		super("RSVEntry", RSVEntryTable::new);
	}

}