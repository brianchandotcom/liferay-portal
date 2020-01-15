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

package com.liferay.mobile.device.rules.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the MDRRule.
 *
 * @author Edward C. Han
 * @generated
 */
public class MDRRuleTable extends Table<MDRRuleTable> {

	public static final MDRRuleTable INSTANCE = new MDRRuleTable();

	public final Column<MDRRuleTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<MDRRuleTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<MDRRuleTable, Long> ruleId = createColumn(
		"ruleId", Long.class, Types.BIGINT);
	public final Column<MDRRuleTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<MDRRuleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<MDRRuleTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<MDRRuleTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<MDRRuleTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<MDRRuleTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<MDRRuleTable, Long> ruleGroupId = createColumn(
		"ruleGroupId", Long.class, Types.BIGINT);
	public final Column<MDRRuleTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<MDRRuleTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<MDRRuleTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR);
	public final Column<MDRRuleTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB);
	public final Column<MDRRuleTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);

	private MDRRuleTable() {
		super("MDRRule", MDRRuleTable::new);
	}

}