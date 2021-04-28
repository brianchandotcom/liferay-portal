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
 * The table class for the &quot;CPDefinitionDiagramSettings&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettings
 * @generated
 */
public class CPDefinitionDiagramSettingsTable
	extends BaseTable<CPDefinitionDiagramSettingsTable> {

	public static final CPDefinitionDiagramSettingsTable INSTANCE =
		new CPDefinitionDiagramSettingsTable();

	public final Column<CPDefinitionDiagramSettingsTable, Long>
		CPDefinitionDiagramSettingsId = createColumn(
			"CPDefinitionDiagramSettingsId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDefinitionDiagramSettingsTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingsTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingsTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingsTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingsTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingsTable, Long> CPDefinitionId =
		createColumn(
			"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingsTable, Long>
		assetCategoryId = createColumn(
			"assetCategoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingsTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CPDefinitionDiagramSettingsTable() {
		super(
			"CPDefinitionDiagramSettings",
			CPDefinitionDiagramSettingsTable::new);
	}

}