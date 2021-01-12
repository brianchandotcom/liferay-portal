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

package com.liferay.oauth.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;OAuth_OAuthUser&quot; database table.
 *
 * @author Ivica Cardic
 * @see OAuthUser
 * @generated
 */
public class OAuthUserTable extends BaseTable<OAuthUserTable> {

	public static final OAuthUserTable INSTANCE = new OAuthUserTable();

	public final Column<OAuthUserTable, Long> oAuthUserId = createColumn(
		"oAuthUserId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<OAuthUserTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, Long> oAuthApplicationId = createColumn(
		"oAuthApplicationId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, String> accessToken = createColumn(
		"accessToken", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, String> accessSecret = createColumn(
		"accessSecret", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private OAuthUserTable() {
		super("OAuth_OAuthUser", OAuthUserTable::new);
	}

}