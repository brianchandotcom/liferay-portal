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

package com.liferay.portal.tools.service.builder.test.sequence.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;SequenceEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SequenceEntry
 * @generated
 */
public class SequenceEntryTable extends BaseTable<SequenceEntryTable> {

	public static final SequenceEntryTable INSTANCE = new SequenceEntryTable();

	public final Column<SequenceEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SequenceEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SequenceEntryTable, Long> sequenceEntryId =
		createColumn(
			"sequenceEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SequenceEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private SequenceEntryTable() {
		super("SequenceEntry", SequenceEntryTable::new);
	}

}