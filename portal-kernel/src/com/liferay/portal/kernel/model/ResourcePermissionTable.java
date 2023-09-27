/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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