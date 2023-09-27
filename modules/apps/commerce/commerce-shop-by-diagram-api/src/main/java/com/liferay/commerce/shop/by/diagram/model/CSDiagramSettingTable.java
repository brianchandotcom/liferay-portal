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
 * The table class for the &quot;CSDiagramSetting&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramSetting
 * @generated
 */
public class CSDiagramSettingTable extends BaseTable<CSDiagramSettingTable> {

	public static final CSDiagramSettingTable INSTANCE =
		new CSDiagramSettingTable();

	public final Column<CSDiagramSettingTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CSDiagramSettingTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CSDiagramSettingTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, Long> CSDiagramSettingId =
		createColumn(
			"CSDiagramSettingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CSDiagramSettingTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, Long> CPAttachmentFileEntryId =
		createColumn(
			"CPAttachmentFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, Long> CPDefinitionId =
		createColumn(
			"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, String> color = createColumn(
		"color", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, Double> radius = createColumn(
		"radius", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CSDiagramSettingTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CSDiagramSettingTable() {
		super("CSDiagramSetting", CSDiagramSettingTable::new);
	}

}