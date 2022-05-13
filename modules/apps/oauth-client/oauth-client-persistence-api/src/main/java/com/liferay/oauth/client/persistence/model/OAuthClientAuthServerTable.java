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

package com.liferay.oauth.client.persistence.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;OAuthClientAuthServer&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientAuthServer
 * @generated
 */
public class OAuthClientAuthServerTable
	extends BaseTable<OAuthClientAuthServerTable> {

	public static final OAuthClientAuthServerTable INSTANCE =
		new OAuthClientAuthServerTable();

	public final Column<OAuthClientAuthServerTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<OAuthClientAuthServerTable, Long>
		oAuthClientAuthServerId = createColumn(
			"oAuthClientAuthServerId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<OAuthClientAuthServerTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthClientAuthServerTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthClientAuthServerTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthClientAuthServerTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthClientAuthServerTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthClientAuthServerTable, String> discoveryEndpoint =
		createColumn(
			"discoveryEndpoint", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<OAuthClientAuthServerTable, String> issuer =
		createColumn(
			"issuer", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthClientAuthServerTable, Clob> metadataJSON =
		createColumn(
			"metadataJSON", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<OAuthClientAuthServerTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private OAuthClientAuthServerTable() {
		super("OAuthClientAuthServer", OAuthClientAuthServerTable::new);
	}

}