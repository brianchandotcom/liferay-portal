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

package com.liferay.portal.search.tuning.rankings.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Ranking&quot; database table.
 *
 * @author Bryan Engler
 * @see Ranking
 * @generated
 */
public class RankingTable extends BaseTable<RankingTable> {

	public static final RankingTable INSTANCE = new RankingTable();

	public final Column<RankingTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RankingTable, Long> rankingId = createColumn(
		"rankingId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RankingTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RankingTable, String> json = createColumn(
		"json", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RankingTable() {
		super("Ranking", RankingTable::new);
	}

}