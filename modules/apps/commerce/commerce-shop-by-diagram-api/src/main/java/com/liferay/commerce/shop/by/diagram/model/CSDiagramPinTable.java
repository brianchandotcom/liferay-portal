/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CSDiagramPin&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramPin
 * @generated
 */
public class CSDiagramPinTable extends BaseTable<CSDiagramPinTable> {

	public static final CSDiagramPinTable INSTANCE = new CSDiagramPinTable();

	public final Column<CSDiagramPinTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CSDiagramPinTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CSDiagramPinTable, Long> CSDiagramPinId = createColumn(
		"CSDiagramPinId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CSDiagramPinTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramPinTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramPinTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CSDiagramPinTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CSDiagramPinTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CSDiagramPinTable, Long> CPDefinitionId = createColumn(
		"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramPinTable, Double> positionX = createColumn(
		"positionX", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CSDiagramPinTable, Double> positionY = createColumn(
		"positionY", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CSDiagramPinTable, String> sequence = createColumn(
		"sequence", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CSDiagramPinTable() {
		super("CSDiagramPin", CSDiagramPinTable::new);
	}

}