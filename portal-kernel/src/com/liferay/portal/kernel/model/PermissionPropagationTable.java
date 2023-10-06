/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;PermissionPropagation&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagation
 * @generated
 */
public class PermissionPropagationTable
	extends BaseTable<PermissionPropagationTable> {

	public static final PermissionPropagationTable INSTANCE =
		new PermissionPropagationTable();

	public final Column<PermissionPropagationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PermissionPropagationTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PermissionPropagationTable, Long>
		permissionPropagationId = createColumn(
			"permissionPropagationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<PermissionPropagationTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PermissionPropagationTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PermissionPropagationTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PermissionPropagationTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PermissionPropagationTable, Boolean> propagate =
		createColumn(
			"propagate", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private PermissionPropagationTable() {
		super("PermissionPropagation", PermissionPropagationTable::new);
	}

}