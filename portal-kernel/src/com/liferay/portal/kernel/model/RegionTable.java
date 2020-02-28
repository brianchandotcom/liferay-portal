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
 * The table class for the &quot;Region&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Region
 * @generated
 */
public class RegionTable extends BaseTable<RegionTable> {

	public static final RegionTable INSTANCE = new RegionTable();

	public final Column<RegionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RegionTable, Long> regionId = createColumn(
		"regionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RegionTable, Long> countryId = createColumn(
		"countryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegionTable, String> regionCode = createColumn(
		"regionCode", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RegionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RegionTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private RegionTable() {
		super("Region", RegionTable::new);
	}

}