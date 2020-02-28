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
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;OAuth2Application&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2Application
 * @generated
 */
public class OAuth2ApplicationTable extends BaseTable<OAuth2ApplicationTable> {

	public static final OAuth2ApplicationTable INSTANCE =
		new OAuth2ApplicationTable();

	public final Column<OAuth2ApplicationTable, Long> oAuth2ApplicationId =
		createColumn(
			"oAuth2ApplicationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<OAuth2ApplicationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, Long>
		oAuth2ApplicationScopeAliasesId = createColumn(
			"oA2AScopeAliasesId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> allowedGrantTypes =
		createColumn(
			"allowedGrantTypes", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, Long> clientCredentialUserId =
		createColumn(
			"clientCredentialUserId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String>
		clientCredentialUserName = createColumn(
			"clientCredentialUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> clientId = createColumn(
		"clientId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, Integer> clientProfile =
		createColumn(
			"clientProfile", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> clientSecret =
		createColumn(
			"clientSecret", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> features = createColumn(
		"features", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> homePageURL =
		createColumn(
			"homePageURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, Long> iconFileEntryId =
		createColumn(
			"iconFileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> privacyPolicyURL =
		createColumn(
			"privacyPolicyURL", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationTable, String> redirectURIs =
		createColumn(
			"redirectURIs", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private OAuth2ApplicationTable() {
		super("OAuth2Application", OAuth2ApplicationTable::new);
	}

}