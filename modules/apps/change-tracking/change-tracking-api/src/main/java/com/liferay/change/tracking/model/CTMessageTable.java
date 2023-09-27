/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;CTMessage&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTMessage
 * @generated
 */
public class CTMessageTable extends BaseTable<CTMessageTable> {

	public static final CTMessageTable INSTANCE = new CTMessageTable();

	public final Column<CTMessageTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTMessageTable, Long> ctMessageId = createColumn(
		"ctMessageId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTMessageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTMessageTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTMessageTable, Clob> messageContent = createColumn(
		"messageContent", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CTMessageTable() {
		super("CTMessage", CTMessageTable::new);
	}

}