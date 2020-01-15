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

import java.util.Date;

/**
 * The table class for the UserGroup.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupTable extends Table<UserGroupTable> {

	public static final UserGroupTable INSTANCE = new UserGroupTable();

	public final Column<UserGroupTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<UserGroupTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<UserGroupTable, String> externalReferenceCode =
		createColumn("externalReferenceCode", String.class, Types.VARCHAR);
	public final Column<UserGroupTable, Long> userGroupId = createColumn(
		"userGroupId", Long.class, Types.BIGINT);
	public final Column<UserGroupTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<UserGroupTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<UserGroupTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<UserGroupTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<UserGroupTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<UserGroupTable, Long> parentUserGroupId = createColumn(
		"parentUserGroupId", Long.class, Types.BIGINT);
	public final Column<UserGroupTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<UserGroupTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<UserGroupTable, Boolean> addedByLDAPImport =
		createColumn("addedByLDAPImport", Boolean.class, Types.BOOLEAN);

	private UserGroupTable() {
		super("UserGroup", UserGroupTable::new);
	}

}