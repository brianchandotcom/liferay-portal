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

package com.liferay.commerce.order.rule.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceOrderRuleEntryRel&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryRel
 * @generated
 */
public class CommerceOrderRuleEntryRelTable
	extends BaseTable<CommerceOrderRuleEntryRelTable> {

	public static final CommerceOrderRuleEntryRelTable INSTANCE =
		new CommerceOrderRuleEntryRelTable();

	public final Column<CommerceOrderRuleEntryRelTable, Long>
		commerceOrderRuleEntryRelId = createColumn(
			"commerceOrderRuleEntryRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceOrderRuleEntryRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderRuleEntryRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderRuleEntryRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderRuleEntryRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderRuleEntryRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderRuleEntryRelTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderRuleEntryRelTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderRuleEntryRelTable, Long>
		commerceOrderRuleEntryId = createColumn(
			"commerceOrderRuleEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CommerceOrderRuleEntryRelTable() {
		super("CommerceOrderRuleEntryRel", CommerceOrderRuleEntryRelTable::new);
	}

}