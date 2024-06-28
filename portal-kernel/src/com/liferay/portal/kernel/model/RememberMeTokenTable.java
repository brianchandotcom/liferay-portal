/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;RememberMeToken&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RememberMeToken
 * @generated
 */
public class RememberMeTokenTable extends BaseTable<RememberMeTokenTable> {

	public static final RememberMeTokenTable INSTANCE =
		new RememberMeTokenTable();

	public final Column<RememberMeTokenTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RememberMeTokenTable, Long> rememberMeTokenId =
		createColumn(
			"rememberMeTokenId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RememberMeTokenTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RememberMeTokenTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RememberMeTokenTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RememberMeTokenTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RememberMeTokenTable, String> value = createColumn(
		"value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RememberMeTokenTable() {
		super("RememberMeToken", RememberMeTokenTable::new);
	}

}