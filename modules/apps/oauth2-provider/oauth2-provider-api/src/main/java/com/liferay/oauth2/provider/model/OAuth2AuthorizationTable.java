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

package com.liferay.oauth2.provider.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the OAuth2Authorization.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuth2AuthorizationTable extends Table<OAuth2AuthorizationTable> {

	public static final OAuth2AuthorizationTable INSTANCE =
		new OAuth2AuthorizationTable();

	public final Column<OAuth2AuthorizationTable, Long> oAuth2AuthorizationId =
		createColumn("oAuth2AuthorizationId", Long.class, Types.BIGINT);
	public final Column<OAuth2AuthorizationTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<OAuth2AuthorizationTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<OAuth2AuthorizationTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<OAuth2AuthorizationTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<OAuth2AuthorizationTable, Long> oAuth2ApplicationId =
		createColumn("oAuth2ApplicationId", Long.class, Types.BIGINT);
	public final Column<OAuth2AuthorizationTable, Long>
		oAuth2ApplicationScopeAliasesId = createColumn(
			"oA2AScopeAliasesId", Long.class, Types.BIGINT);
	public final Column<OAuth2AuthorizationTable, Clob> accessTokenContent =
		createColumn("accessTokenContent", Clob.class, Types.CLOB);
	public final Column<OAuth2AuthorizationTable, Long> accessTokenContentHash =
		createColumn("accessTokenContentHash", Long.class, Types.BIGINT);
	public final Column<OAuth2AuthorizationTable, Date> accessTokenCreateDate =
		createColumn("accessTokenCreateDate", Date.class, Types.TIMESTAMP);
	public final Column<OAuth2AuthorizationTable, Date>
		accessTokenExpirationDate = createColumn(
			"accessTokenExpirationDate", Date.class, Types.TIMESTAMP);
	public final Column<OAuth2AuthorizationTable, String> remoteHostInfo =
		createColumn("remoteHostInfo", String.class, Types.VARCHAR);
	public final Column<OAuth2AuthorizationTable, String> remoteIPInfo =
		createColumn("remoteIPInfo", String.class, Types.VARCHAR);
	public final Column<OAuth2AuthorizationTable, Clob> refreshTokenContent =
		createColumn("refreshTokenContent", Clob.class, Types.CLOB);
	public final Column<OAuth2AuthorizationTable, Long>
		refreshTokenContentHash = createColumn(
			"refreshTokenContentHash", Long.class, Types.BIGINT);
	public final Column<OAuth2AuthorizationTable, Date> refreshTokenCreateDate =
		createColumn("refreshTokenCreateDate", Date.class, Types.TIMESTAMP);
	public final Column<OAuth2AuthorizationTable, Date>
		refreshTokenExpirationDate = createColumn(
			"refreshTokenExpirationDate", Date.class, Types.TIMESTAMP);

	private OAuth2AuthorizationTable() {
		super("OAuth2Authorization", OAuth2AuthorizationTable::new);
	}

}