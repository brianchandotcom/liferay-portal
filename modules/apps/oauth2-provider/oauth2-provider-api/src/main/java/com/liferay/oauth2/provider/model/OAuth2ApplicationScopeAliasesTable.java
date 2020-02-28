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
 * The table class for the &quot;OAuth2ApplicationScopeAliases&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationScopeAliases
 * @generated
 */
public class OAuth2ApplicationScopeAliasesTable
	extends BaseTable<OAuth2ApplicationScopeAliasesTable> {

	public static final OAuth2ApplicationScopeAliasesTable INSTANCE =
		new OAuth2ApplicationScopeAliasesTable();

	public final Column<OAuth2ApplicationScopeAliasesTable, Long>
		oAuth2ApplicationScopeAliasesId = createColumn(
			"oA2AScopeAliasesId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<OAuth2ApplicationScopeAliasesTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationScopeAliasesTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationScopeAliasesTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationScopeAliasesTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuth2ApplicationScopeAliasesTable, Long>
		oAuth2ApplicationId = createColumn(
			"oAuth2ApplicationId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private OAuth2ApplicationScopeAliasesTable() {
		super(
			"OAuth2ApplicationScopeAliases",
			OAuth2ApplicationScopeAliasesTable::new);
	}

}