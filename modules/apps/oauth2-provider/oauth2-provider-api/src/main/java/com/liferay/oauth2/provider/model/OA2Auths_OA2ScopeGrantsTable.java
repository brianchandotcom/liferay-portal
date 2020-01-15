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

import java.sql.Types;

/**
 * The table class for the OA2Auths_OA2ScopeGrants.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OA2Auths_OA2ScopeGrantsTable
	extends Table<OA2Auths_OA2ScopeGrantsTable> {

	public static final OA2Auths_OA2ScopeGrantsTable INSTANCE =
		new OA2Auths_OA2ScopeGrantsTable();

	public final Column<OA2Auths_OA2ScopeGrantsTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<OA2Auths_OA2ScopeGrantsTable, Long>
		oAuth2AuthorizationId = createColumn(
			"oAuth2AuthorizationId", Long.class, Types.BIGINT);
	public final Column<OA2Auths_OA2ScopeGrantsTable, Long> oAuth2ScopeGrantId =
		createColumn("oAuth2ScopeGrantId", Long.class, Types.BIGINT);

	private OA2Auths_OA2ScopeGrantsTable() {
		super("OA2Auths_OA2ScopeGrants", OA2Auths_OA2ScopeGrantsTable::new);
	}

}