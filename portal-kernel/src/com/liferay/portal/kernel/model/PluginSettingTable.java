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
 * The table class for the &quot;PluginSetting&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PluginSetting
 * @generated
 */
public class PluginSettingTable extends BaseTable<PluginSettingTable> {

	public static final PluginSettingTable INSTANCE = new PluginSettingTable();

	public final Column<PluginSettingTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PluginSettingTable, Long> pluginSettingId =
		createColumn(
			"pluginSettingId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PluginSettingTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PluginSettingTable, String> pluginId = createColumn(
		"pluginId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PluginSettingTable, String> pluginType = createColumn(
		"pluginType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PluginSettingTable, String> roles = createColumn(
		"roles", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PluginSettingTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private PluginSettingTable() {
		super("PluginSetting", PluginSettingTable::new);
	}

}