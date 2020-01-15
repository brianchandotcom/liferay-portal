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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the Users_Orgs.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class Users_OrgsTable extends Table<Users_OrgsTable> {

	public static final Users_OrgsTable INSTANCE = new Users_OrgsTable();

	public final Column<Users_OrgsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<Users_OrgsTable, Long> organizationId = createColumn(
		"organizationId", Long.class, Types.BIGINT);
	public final Column<Users_OrgsTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);

	private Users_OrgsTable() {
		super("Users_Orgs", Users_OrgsTable::new);
	}

}