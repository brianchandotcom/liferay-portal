/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;TimestampTypeEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see TimestampTypeEntry
 * @generated
 */
public class TimestampTypeEntryTable
	extends BaseTable<TimestampTypeEntryTable> {

	public static final TimestampTypeEntryTable INSTANCE =
		new TimestampTypeEntryTable();

	public final Column<TimestampTypeEntryTable, Long> timestampTypeEntryId =
		createColumn(
			"timestampTypeEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<TimestampTypeEntryTable, Date> dateValue = createColumn(
		"dateValue", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private TimestampTypeEntryTable() {
		super("TimestampTypeEntry", TimestampTypeEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-799944676