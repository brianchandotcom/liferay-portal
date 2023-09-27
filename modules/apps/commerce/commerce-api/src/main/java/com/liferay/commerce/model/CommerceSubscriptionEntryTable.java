/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceSubscriptionEntry&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceSubscriptionEntry
 * @generated
 */
public class CommerceSubscriptionEntryTable
	extends BaseTable<CommerceSubscriptionEntryTable> {

	public static final CommerceSubscriptionEntryTable INSTANCE =
		new CommerceSubscriptionEntryTable();

	public final Column<CommerceSubscriptionEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceSubscriptionEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Long>
		commerceSubscriptionEntryId = createColumn(
			"commerceSubscriptionEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceSubscriptionEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, String> CPInstanceUuid =
		createColumn(
			"CPInstanceUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Long> CProductId =
		createColumn(
			"CProductId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Long>
		commerceOrderItemId = createColumn(
			"commerceOrderItemId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Integer>
		subscriptionLength = createColumn(
			"subscriptionLength", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, String>
		subscriptionType = createColumn(
			"subscriptionType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Clob>
		subscriptionTypeSettings = createColumn(
			"subscriptionTypeSettings", Clob.class, Types.CLOB,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Long> currentCycle =
		createColumn(
			"currentCycle", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Long>
		maxSubscriptionCycles = createColumn(
			"maxSubscriptionCycles", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Integer>
		subscriptionStatus = createColumn(
			"subscriptionStatus", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Date>
		lastIterationDate = createColumn(
			"lastIterationDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Date>
		nextIterationDate = createColumn(
			"nextIterationDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Date> startDate =
		createColumn(
			"startDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Integer>
		deliverySubscriptionLength = createColumn(
			"deliverySubscriptionLength", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, String>
		deliverySubscriptionType = createColumn(
			"deliverySubscriptionType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, String>
		deliverySubscriptionTypeSettings = createColumn(
			"deliverySubTypeSettings", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Long>
		deliveryCurrentCycle = createColumn(
			"deliveryCurrentCycle", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Long>
		deliveryMaxSubscriptionCycles = createColumn(
			"deliveryMaxSubscriptionCycles", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Integer>
		deliverySubscriptionStatus = createColumn(
			"deliverySubscriptionStatus", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Date>
		deliveryLastIterationDate = createColumn(
			"deliveryLastIterationDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Date>
		deliveryNextIterationDate = createColumn(
			"deliveryNextIterationDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceSubscriptionEntryTable, Date>
		deliveryStartDate = createColumn(
			"deliveryStartDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private CommerceSubscriptionEntryTable() {
		super("CommerceSubscriptionEntry", CommerceSubscriptionEntryTable::new);
	}

}