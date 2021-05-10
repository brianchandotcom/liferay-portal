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

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;PortalPreferenceValue&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferenceValue
 * @generated
 */
public class PortalPreferenceValueTable
	extends BaseTable<PortalPreferenceValueTable> {

	public static final PortalPreferenceValueTable INSTANCE =
		new PortalPreferenceValueTable();

	public final Column<PortalPreferenceValueTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PortalPreferenceValueTable, Long>
		portalPreferenceValueId = createColumn(
			"portalPreferenceValueId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<PortalPreferenceValueTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PortalPreferenceValueTable, Long> portalPreferencesId =
		createColumn(
			"portalPreferencesId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<PortalPreferenceValueTable, Integer> index =
		createColumn(
			"index_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PortalPreferenceValueTable, String> key = createColumn(
		"key_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PortalPreferenceValueTable, Clob> largeValue =
		createColumn("largeValue", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<PortalPreferenceValueTable, String> namespace =
		createColumn(
			"namespace", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PortalPreferenceValueTable, String> smallValue =
		createColumn(
			"smallValue", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private PortalPreferenceValueTable() {
		super("PortalPreferenceValue", PortalPreferenceValueTable::new);
	}

}