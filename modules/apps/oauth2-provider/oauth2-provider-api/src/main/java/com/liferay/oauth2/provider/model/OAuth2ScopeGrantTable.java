/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;OAuth2ScopeGrant&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrant
 * @generated
 */
public class OAuth2ScopeGrantTable extends BaseTable<OAuth2ScopeGrantTable> {

	public static final OAuth2ScopeGrantTable INSTANCE =
		new OAuth2ScopeGrantTable();

	public final Column<OAuth2ScopeGrantTable, Long> oAuth2ScopeGrantId =
		createColumn(
			"oAuth2ScopeGrantId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<OAuth2ScopeGrantTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuth2ScopeGrantTable, Long>
		oAuth2ApplicationScopeAliasesId = createColumn(
			"oA2AScopeAliasesId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<OAuth2ScopeGrantTable, String> applicationName =
		createColumn(
			"applicationName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<OAuth2ScopeGrantTable, String> bundleSymbolicName =
		createColumn(
			"bundleSymbolicName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<OAuth2ScopeGrantTable, String> scope = createColumn(
		"scope", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuth2ScopeGrantTable, Clob> scopeAliases =
		createColumn(
			"scopeAliases", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private OAuth2ScopeGrantTable() {
		super("OAuth2ScopeGrant", OAuth2ScopeGrantTable::new);
	}

}