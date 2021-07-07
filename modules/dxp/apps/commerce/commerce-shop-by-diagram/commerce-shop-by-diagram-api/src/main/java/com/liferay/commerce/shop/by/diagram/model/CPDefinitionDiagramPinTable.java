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
 * The table class for the &quot;CPDefinitionDiagramPin&quot; database table.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramPin
 * @generated
 */
public class CPDefinitionDiagramPinTable
	extends BaseTable<CPDefinitionDiagramPinTable> {

	public static final CPDefinitionDiagramPinTable INSTANCE =
		new CPDefinitionDiagramPinTable();

	public final Column<CPDefinitionDiagramPinTable, Long>
		CPDefinitionDiagramPinId = createColumn(
			"CPDefinitionDiagramPinId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDefinitionDiagramPinTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramPinTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramPinTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramPinTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramPinTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramPinTable, Long> CPDefinitionId =
		createColumn(
			"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramPinTable, Integer> number =
		createColumn(
			"number_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramPinTable, Double> positionX =
		createColumn(
			"positionX", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramPinTable, Double> positionY =
		createColumn(
			"positionY", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CPDefinitionDiagramPinTable() {
		super("CPDefinitionDiagramPin", CPDefinitionDiagramPinTable::new);
	}

}