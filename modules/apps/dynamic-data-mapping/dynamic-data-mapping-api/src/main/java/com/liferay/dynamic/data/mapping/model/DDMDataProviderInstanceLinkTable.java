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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DDMDataProviderInstanceLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLink
 * @generated
 */
public class DDMDataProviderInstanceLinkTable
	extends BaseTable<DDMDataProviderInstanceLinkTable> {

	public static final DDMDataProviderInstanceLinkTable INSTANCE =
		new DDMDataProviderInstanceLinkTable();

	public final Column<DDMDataProviderInstanceLinkTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMDataProviderInstanceLinkTable, Long>
		dataProviderInstanceLinkId = createColumn(
			"dataProviderInstanceLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DDMDataProviderInstanceLinkTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceLinkTable, Long>
		dataProviderInstanceId = createColumn(
			"dataProviderInstanceId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceLinkTable, Long> structureId =
		createColumn(
			"structureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DDMDataProviderInstanceLinkTable() {
		super(
			"DDMDataProviderInstanceLink",
			DDMDataProviderInstanceLinkTable::new);
	}

}