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
 * The table class for the &quot;CSDiagramEntry&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramEntry
 * @generated
 */
public class CSDiagramEntryTable extends BaseTable<CSDiagramEntryTable> {

	public static final CSDiagramEntryTable INSTANCE =
		new CSDiagramEntryTable();

	public final Column<CSDiagramEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CSDiagramEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CSDiagramEntryTable, Long> CSDiagramEntryId =
		createColumn(
			"CSDiagramEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CSDiagramEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, Long> CPDefinitionId =
		createColumn(
			"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, Long> CPInstanceId = createColumn(
		"CPInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, Long> CProductId = createColumn(
		"CProductId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, Boolean> diagram = createColumn(
		"diagram", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, Integer> quantity = createColumn(
		"quantity", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, String> sequence = createColumn(
		"sequence", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CSDiagramEntryTable, String> sku = createColumn(
		"sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CSDiagramEntryTable() {
		super("CSDiagramEntry", CSDiagramEntryTable::new);
	}

}