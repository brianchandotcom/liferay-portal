/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ReassociateEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ReassociateEntry
 * @generated
 */
public class ReassociateEntryTable extends BaseTable<ReassociateEntryTable> {

	public static final ReassociateEntryTable INSTANCE =
		new ReassociateEntryTable();

	public final Column<ReassociateEntryTable, Long> reassociateEntryId =
		createColumn(
			"reassociateEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ReassociateEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ReassociateEntryTable() {
		super("ReassociateEntry", ReassociateEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1453800390