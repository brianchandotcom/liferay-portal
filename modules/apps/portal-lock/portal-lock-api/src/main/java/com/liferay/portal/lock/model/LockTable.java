/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lock.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Lock_&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Lock
 * @generated
 */
public class LockTable extends BaseTable<LockTable> {

	public static final LockTable INSTANCE = new LockTable();

	public final Column<LockTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LockTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LockTable, Long> lockId = createColumn(
		"lockId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LockTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LockTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LockTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LockTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LockTable, String> className = createColumn(
		"className", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LockTable, String> key = createColumn(
		"key_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LockTable, String> owner = createColumn(
		"owner", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LockTable, Boolean> inheritable = createColumn(
		"inheritable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LockTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private LockTable() {
		super("Lock_", LockTable::new);
	}

}