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

package com.liferay.commerce.bom.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceBOMEntry&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMEntry
 * @generated
 */
public class CommerceBOMEntryTable extends BaseTable<CommerceBOMEntryTable> {

	public static final CommerceBOMEntryTable INSTANCE =
		new CommerceBOMEntryTable();

	public final Column<CommerceBOMEntryTable, Long> commerceBOMEntryId =
		createColumn(
			"commerceBOMEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceBOMEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, Integer> number = createColumn(
		"number_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, String> CPInstanceUuid =
		createColumn(
			"CPInstanceUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, Long> CProductId = createColumn(
		"CProductId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, Long> commerceBOMDefinitionId =
		createColumn(
			"commerceBOMDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, Double> positionX = createColumn(
		"positionX", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, Double> positionY = createColumn(
		"positionY", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMEntryTable, Double> radius = createColumn(
		"radius", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CommerceBOMEntryTable() {
		super("CommerceBOMEntry", CommerceBOMEntryTable::new);
	}

}