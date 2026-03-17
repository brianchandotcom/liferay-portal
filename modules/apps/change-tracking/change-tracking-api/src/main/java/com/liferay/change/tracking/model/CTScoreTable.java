/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;CTScore&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTScore
 * @generated
 */
public class CTScoreTable extends BaseTable<CTScoreTable> {

	public static final CTScoreTable INSTANCE = new CTScoreTable();

	public final Column<CTScoreTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTScoreTable, Long> ctScoreId = createColumn(
		"ctScoreId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTScoreTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTScoreTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTScoreTable, Integer> score = createColumn(
		"score", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CTScoreTable() {
		super("CTScore", CTScoreTable::new);
	}

}
// SB-Hash:-131246946