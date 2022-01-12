/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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