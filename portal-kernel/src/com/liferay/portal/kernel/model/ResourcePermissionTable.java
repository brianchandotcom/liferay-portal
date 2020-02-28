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
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ResourcePermission&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermission
 * @generated
 */
public class ResourcePermissionTable
	extends BaseTable<ResourcePermissionTable> {

	public static final ResourcePermissionTable INSTANCE =
		new ResourcePermissionTable();

	public final Column<ResourcePermissionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ResourcePermissionTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ResourcePermissionTable, Long> resourcePermissionId =
		createColumn(
			"resourcePermissionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ResourcePermissionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ResourcePermissionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ResourcePermissionTable, Integer> scope = createColumn(
		"scope", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ResourcePermissionTable, String> primKey = createColumn(
		"primKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ResourcePermissionTable, Long> primKeyId = createColumn(
		"primKeyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ResourcePermissionTable, Long> roleId = createColumn(
		"roleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ResourcePermissionTable, Long> ownerId = createColumn(
		"ownerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ResourcePermissionTable, Long> actionIds = createColumn(
		"actionIds", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ResourcePermissionTable, Boolean> viewActionId =
		createColumn(
			"viewActionId", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private ResourcePermissionTable() {
		super("ResourcePermission", ResourcePermissionTable::new);
	}

}