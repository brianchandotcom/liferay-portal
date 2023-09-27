/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

import java.sql.Types;

/**
 * The table class for the &quot;BigDecimalEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see BigDecimalEntry
 * @generated
 */
public class BigDecimalEntryTable extends BaseTable<BigDecimalEntryTable> {

	public static final BigDecimalEntryTable INSTANCE =
		new BigDecimalEntryTable();

	public final Column<BigDecimalEntryTable, Long> bigDecimalEntryId =
		createColumn(
			"bigDecimalEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<BigDecimalEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BigDecimalEntryTable, BigDecimal> bigDecimalValue =
		createColumn(
			"bigDecimalValue", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);

	private BigDecimalEntryTable() {
		super("BigDecimalEntry", BigDecimalEntryTable::new);
	}

}