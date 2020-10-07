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

package com.liferay.opensocial.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;OpenSocial_OAuthToken&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthToken
 * @generated
 */
public class OAuthTokenTable extends BaseTable<OAuthTokenTable> {

	public static final OAuthTokenTable INSTANCE = new OAuthTokenTable();

	public final Column<OAuthTokenTable, Long> oAuthTokenId = createColumn(
		"oAuthTokenId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<OAuthTokenTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, String> gadgetKey = createColumn(
		"gadgetKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, String> serviceName = createColumn(
		"serviceName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, Long> moduleId = createColumn(
		"moduleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, String> accessToken = createColumn(
		"accessToken", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, String> tokenName = createColumn(
		"tokenName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, String> tokenSecret = createColumn(
		"tokenSecret", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, String> sessionHandle = createColumn(
		"sessionHandle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthTokenTable, Long> expiration = createColumn(
		"expiration", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private OAuthTokenTable() {
		super("OpenSocial_OAuthToken", OAuthTokenTable::new);
	}

}