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

package com.liferay.commerce.product.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CChannelAccountEntryRel&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceChannelAccountEntryRel
 * @generated
 */
public class CommerceChannelAccountEntryRelTable
	extends BaseTable<CommerceChannelAccountEntryRelTable> {

	public static final CommerceChannelAccountEntryRelTable INSTANCE =
		new CommerceChannelAccountEntryRelTable();

	public final Column<CommerceChannelAccountEntryRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceChannelAccountEntryRelTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceChannelAccountEntryRelTable, Long>
		commerceChannelAccountEntryRelId = createColumn(
			"CChannelAccountEntryRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceChannelAccountEntryRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Long>
		accountEntryId = createColumn(
			"accountEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Long>
		commerceChannelId = createColumn(
			"commerceChannelId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Boolean>
		overrideEligibility = createColumn(
			"overrideEligibility", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceChannelAccountEntryRelTable, Integer> type =
		createColumn(
			"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CommerceChannelAccountEntryRelTable() {
		super(
			"CChannelAccountEntryRel",
			CommerceChannelAccountEntryRelTable::new);
	}

}