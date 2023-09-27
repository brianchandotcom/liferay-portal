/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Marketplace_Module&quot; database table.
 *
 * @author Ryan Park
 * @see Module
 * @generated
 */
public class ModuleTable extends BaseTable<ModuleTable> {

	public static final ModuleTable INSTANCE = new ModuleTable();

	public final Column<ModuleTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ModuleTable, Long> moduleId = createColumn(
		"moduleId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ModuleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ModuleTable, Long> appId = createColumn(
		"appId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ModuleTable, String> bundleSymbolicName = createColumn(
		"bundleSymbolicName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ModuleTable, String> bundleVersion = createColumn(
		"bundleVersion", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ModuleTable, String> contextName = createColumn(
		"contextName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ModuleTable() {
		super("Marketplace_Module", ModuleTable::new);
	}

}