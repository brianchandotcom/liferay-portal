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

package com.liferay.commerce.shop.by.diagram.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPDefinitionDiagramEntry&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramEntry
 * @generated
 */
public class CPDefinitionDiagramEntryTable
	extends BaseTable<CPDefinitionDiagramEntryTable> {

	public static final CPDefinitionDiagramEntryTable INSTANCE =
		new CPDefinitionDiagramEntryTable();

	public final Column<CPDefinitionDiagramEntryTable, Long>
		CPDefinitionDiagramEntryId = createColumn(
			"CPDefinitionDiagramEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDefinitionDiagramEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Long>
		CPDefinitionDiagramSettingsId = createColumn(
			"CPDefinitionDiagramSettingsId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, String> CPInstanceUuid =
		createColumn(
			"CPInstanceUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Long> CProductId =
		createColumn(
			"CProductId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Integer> number =
		createColumn(
			"number_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Integer> quantity =
		createColumn(
			"quantity", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Double> positionX =
		createColumn(
			"positionX", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Double> positionY =
		createColumn(
			"positionY", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramEntryTable, Double> radius =
		createColumn("radius", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CPDefinitionDiagramEntryTable() {
		super("CPDefinitionDiagramEntry", CPDefinitionDiagramEntryTable::new);
	}

}