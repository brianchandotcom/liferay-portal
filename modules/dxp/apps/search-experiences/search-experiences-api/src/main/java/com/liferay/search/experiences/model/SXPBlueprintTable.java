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

package com.liferay.search.experiences.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SXPBlueprint&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SXPBlueprint
 * @generated
 */
public class SXPBlueprintTable extends BaseTable<SXPBlueprintTable> {

	public static final SXPBlueprintTable INSTANCE = new SXPBlueprintTable();

	public final Column<SXPBlueprintTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SXPBlueprintTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, Long> sxpBlueprintId = createColumn(
		"sxpBlueprintId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SXPBlueprintTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, String> configurationJSON =
		createColumn(
			"configurationJSON", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SXPBlueprintTable, String> selectedElementsJSON =
		createColumn(
			"selectedElementsJSON", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private SXPBlueprintTable() {
		super("SXPBlueprint", SXPBlueprintTable::new);
	}

}