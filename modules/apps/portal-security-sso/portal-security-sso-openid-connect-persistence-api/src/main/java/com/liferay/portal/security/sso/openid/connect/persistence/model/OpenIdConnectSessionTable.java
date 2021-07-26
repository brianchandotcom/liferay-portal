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

package com.liferay.portal.security.sso.openid.connect.persistence.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;OpenIdConnectSession&quot; database table.
 *
 * @author Arthur Chan
 * @see OpenIdConnectSession
 * @generated
 */
public class OpenIdConnectSessionTable
	extends BaseTable<OpenIdConnectSessionTable> {

	public static final OpenIdConnectSessionTable INSTANCE =
		new OpenIdConnectSessionTable();

	public final Column<OpenIdConnectSessionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<OpenIdConnectSessionTable, Long>
		openIdConnectSessionId = createColumn(
			"openIdConnectSessionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<OpenIdConnectSessionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OpenIdConnectSessionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OpenIdConnectSessionTable, String> accessToken =
		createColumn(
			"accessToken", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OpenIdConnectSessionTable, String> idToken =
		createColumn(
			"idToken", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OpenIdConnectSessionTable, String> providerName =
		createColumn(
			"providerName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OpenIdConnectSessionTable, String> refreshToken =
		createColumn(
			"refreshToken", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private OpenIdConnectSessionTable() {
		super("OpenIdConnectSession", OpenIdConnectSessionTable::new);
	}

}