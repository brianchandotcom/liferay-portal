/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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