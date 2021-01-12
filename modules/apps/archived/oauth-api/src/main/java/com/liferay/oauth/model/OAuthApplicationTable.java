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
 * The table class for the &quot;OAuth_OAuthApplication&quot; database table.
 *
 * @author Ivica Cardic
 * @see OAuthApplication
 * @generated
 */
public class OAuthApplicationTable extends BaseTable<OAuthApplicationTable> {

	public static final OAuthApplicationTable INSTANCE =
		new OAuthApplicationTable();

	public final Column<OAuthApplicationTable, Long> oAuthApplicationId =
		createColumn(
			"oAuthApplicationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<OAuthApplicationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, String> consumerKey =
		createColumn(
			"consumerKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, String> consumerSecret =
		createColumn(
			"consumerSecret", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, Integer> accessLevel =
		createColumn(
			"accessLevel", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, Boolean> shareableAccessToken =
		createColumn(
			"shareableAccessToken", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, String> callbackURI =
		createColumn(
			"callbackURI", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthApplicationTable, String> websiteURL =
		createColumn(
			"websiteURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private OAuthApplicationTable() {
		super("OAuth_OAuthApplication", OAuthApplicationTable::new);
	}

}