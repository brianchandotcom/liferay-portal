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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the MDRRuleGroup.
 *
 * @author Edward C. Han
 * @generated
 */
public class MDRRuleGroupTable extends Table<MDRRuleGroupTable> {

	public static final MDRRuleGroupTable INSTANCE = new MDRRuleGroupTable();

	public final Column<MDRRuleGroupTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<MDRRuleGroupTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<MDRRuleGroupTable, Long> ruleGroupId = createColumn(
		"ruleGroupId", Long.class, Types.BIGINT);
	public final Column<MDRRuleGroupTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<MDRRuleGroupTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<MDRRuleGroupTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<MDRRuleGroupTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<MDRRuleGroupTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<MDRRuleGroupTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<MDRRuleGroupTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<MDRRuleGroupTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<MDRRuleGroupTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);

	private MDRRuleGroupTable() {
		super("MDRRuleGroup", MDRRuleGroupTable::new);
	}

}