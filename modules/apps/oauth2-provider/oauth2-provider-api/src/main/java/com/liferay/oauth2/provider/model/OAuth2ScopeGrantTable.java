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

/**
 * The table class for the OAuth2ScopeGrant.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuth2ScopeGrantTable extends Table<OAuth2ScopeGrantTable> {

	public static final OAuth2ScopeGrantTable INSTANCE =
		new OAuth2ScopeGrantTable();

	public final Column<OAuth2ScopeGrantTable, Long> oAuth2ScopeGrantId =
		createColumn("oAuth2ScopeGrantId", Long.class, Types.BIGINT);
	public final Column<OAuth2ScopeGrantTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<OAuth2ScopeGrantTable, Long>
		oAuth2ApplicationScopeAliasesId = createColumn(
			"oA2AScopeAliasesId", Long.class, Types.BIGINT);
	public final Column<OAuth2ScopeGrantTable, String> applicationName =
		createColumn("applicationName", String.class, Types.VARCHAR);
	public final Column<OAuth2ScopeGrantTable, String> bundleSymbolicName =
		createColumn("bundleSymbolicName", String.class, Types.VARCHAR);
	public final Column<OAuth2ScopeGrantTable, String> scope = createColumn(
		"scope", String.class, Types.VARCHAR);
	public final Column<OAuth2ScopeGrantTable, Clob> scopeAliases =
		createColumn("scopeAliases", Clob.class, Types.CLOB);

	private OAuth2ScopeGrantTable() {
		super("OAuth2ScopeGrant", OAuth2ScopeGrantTable::new);
	}

}