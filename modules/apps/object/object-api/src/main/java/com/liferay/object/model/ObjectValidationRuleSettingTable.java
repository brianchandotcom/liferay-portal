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

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectValidationRuleSetting&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectValidationRuleSetting
 * @generated
 */
public class ObjectValidationRuleSettingTable
	extends BaseTable<ObjectValidationRuleSettingTable> {

	public static final ObjectValidationRuleSettingTable INSTANCE =
		new ObjectValidationRuleSettingTable();

	public final Column<ObjectValidationRuleSettingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectValidationRuleSettingTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectValidationRuleSettingTable, Long>
		objectValidationRuleSettingId = createColumn(
			"objectValidationRuleSettingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectValidationRuleSettingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectValidationRuleSettingTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectValidationRuleSettingTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectValidationRuleSettingTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectValidationRuleSettingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectValidationRuleSettingTable, Long>
		objectValidationRuleId = createColumn(
			"objectValidationRuleId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectValidationRuleSettingTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectValidationRuleSettingTable, String> value =
		createColumn("value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ObjectValidationRuleSettingTable() {
		super(
			"ObjectValidationRuleSetting",
			ObjectValidationRuleSettingTable::new);
	}

}