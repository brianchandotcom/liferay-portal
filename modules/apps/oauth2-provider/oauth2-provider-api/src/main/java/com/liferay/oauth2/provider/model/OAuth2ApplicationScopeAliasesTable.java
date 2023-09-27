/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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