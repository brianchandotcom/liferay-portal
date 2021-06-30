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
 * The table class for the &quot;CPDefinitionDiagramSetting&quot; database table.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSetting
 * @generated
 */
public class CPDefinitionDiagramSettingTable
	extends BaseTable<CPDefinitionDiagramSettingTable> {

	public static final CPDefinitionDiagramSettingTable INSTANCE =
		new CPDefinitionDiagramSettingTable();

	public final Column<CPDefinitionDiagramSettingTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingTable, Long>
		CPDefinitionDiagramSettingId = createColumn(
			"CPDefinitionDiagramSettingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDefinitionDiagramSettingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingTable, Long> CPDefinitionId =
		createColumn(
			"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingTable, Double> radius =
		createColumn("radius", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionDiagramSettingTable, String> color =
		createColumn("color", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CPDefinitionDiagramSettingTable() {
		super(
			"CPDefinitionDiagramSetting", CPDefinitionDiagramSettingTable::new);
	}

}