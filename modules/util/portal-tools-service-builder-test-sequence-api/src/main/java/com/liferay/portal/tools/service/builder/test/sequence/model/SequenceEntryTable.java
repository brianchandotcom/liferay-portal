/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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