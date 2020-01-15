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

package com.liferay.marketplace.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the Module.
 *
 * @author Ryan Park
 * @generated
 */
public class ModuleTable extends Table<ModuleTable> {

	public static final ModuleTable INSTANCE = new ModuleTable();

	public final Column<ModuleTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<ModuleTable, Long> moduleId = createColumn(
		"moduleId", Long.class, Types.BIGINT);
	public final Column<ModuleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<ModuleTable, Long> appId = createColumn(
		"appId", Long.class, Types.BIGINT);
	public final Column<ModuleTable, String> bundleSymbolicName = createColumn(
		"bundleSymbolicName", String.class, Types.VARCHAR);
	public final Column<ModuleTable, String> bundleVersion = createColumn(
		"bundleVersion", String.class, Types.VARCHAR);
	public final Column<ModuleTable, String> contextName = createColumn(
		"contextName", String.class, Types.VARCHAR);

	private ModuleTable() {
		super("Marketplace_Module", ModuleTable::new);
	}

}