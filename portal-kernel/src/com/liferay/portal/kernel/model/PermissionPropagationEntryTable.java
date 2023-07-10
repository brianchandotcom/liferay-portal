/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;PermissionPropagationEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationEntry
 * @generated
 */
public class PermissionPropagationEntryTable
	extends BaseTable<PermissionPropagationEntryTable> {

	public static final PermissionPropagationEntryTable INSTANCE =
		new PermissionPropagationEntryTable();

	public final Column<PermissionPropagationEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PermissionPropagationEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PermissionPropagationEntryTable, Long>
		permissionPropagationEntryId = createColumn(
			"permissionPropagationEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<PermissionPropagationEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PermissionPropagationEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PermissionPropagationEntryTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PermissionPropagationEntryTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PermissionPropagationEntryTable, Boolean> propagation =
		createColumn(
			"propagation", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private PermissionPropagationEntryTable() {
		super(
			"PermissionPropagationEntry", PermissionPropagationEntryTable::new);
	}

}