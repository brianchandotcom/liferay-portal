/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;OA2Auths_OA2ScopeGrants&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2Authorization
 * @see OAuth2ScopeGrant
 * @generated
 */
public class OA2Auths_OA2ScopeGrantsTable
	extends BaseTable<OA2Auths_OA2ScopeGrantsTable> {

	public static final OA2Auths_OA2ScopeGrantsTable INSTANCE =
		new OA2Auths_OA2ScopeGrantsTable();

	public final Column<OA2Auths_OA2ScopeGrantsTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<OA2Auths_OA2ScopeGrantsTable, Long>
		oAuth2AuthorizationId = createColumn(
			"oAuth2AuthorizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<OA2Auths_OA2ScopeGrantsTable, Long> oAuth2ScopeGrantId =
		createColumn(
			"oAuth2ScopeGrantId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);

	private OA2Auths_OA2ScopeGrantsTable() {
		super("OA2Auths_OA2ScopeGrants", OA2Auths_OA2ScopeGrantsTable::new);
	}

}