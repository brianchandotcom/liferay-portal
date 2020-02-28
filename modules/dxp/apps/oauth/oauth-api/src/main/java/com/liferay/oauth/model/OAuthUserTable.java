/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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