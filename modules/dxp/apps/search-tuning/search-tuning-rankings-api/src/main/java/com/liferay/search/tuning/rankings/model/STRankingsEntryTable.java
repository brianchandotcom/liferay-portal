/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.tuning.rankings.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;STRankingsEntry&quot; database table.
 *
 * @author Bryan Engler
 * @see STRankingsEntry
 * @generated
 */
public class STRankingsEntryTable extends BaseTable<STRankingsEntryTable> {

	public static final STRankingsEntryTable INSTANCE =
		new STRankingsEntryTable();

	public final Column<STRankingsEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<STRankingsEntryTable, Long> STRankingsEntryId =
		createColumn(
			"STRankingsEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<STRankingsEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private STRankingsEntryTable() {
		super("STRankingsEntry", STRankingsEntryTable::new);
	}

}